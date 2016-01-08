/**
 * 	@(#) medicinfoPServiceImpl.java 2008-4-14 下午01:09:59
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.MedicinfoPService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author jingyuzhuo
 * 
 */
public class MedicinfoPServiceImpl implements MedicinfoPService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;

	public JFreeService getChartService() {
		return chartService;
	}

	public void setChartService(JFreeService chartService) {
		this.chartService = chartService;
	}

	public ProcSql getProcSql() {
		return procSql;
	}

	public void setProcSql(ProcSql procSql) {
		this.procSql = procSql;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String statType = dto.get("statType").toString();
		String str = null;
		String xChartName = null;
		procSql.setProcedureName("proc_medicinfo");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("statType").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results, statType);
		List<String> yAxis = getYaxis(results, statType);
		List<String> valueList = getValues(results, xAxis, yAxis, statType);
		// 定制JFreeChart对象的属性
		Map<String, Object> properties = new HashMap<String, Object>();

		// 判断用什么类型查询
		if (statType.equals("userType")) {
			str = "用户使用率统计";
			xChartName = "";
		} else if (statType.equals("trackType")) {
			str = "跟踪服务统计";
			xChartName = "";
		} else if (statType.equals("gonghaoType")) {
			str = "工号统计";
			xChartName = "";
		}

		String chartType = dto.get("chartType").toString();
		if ("on".equals(dto.get("is3d"))) {
			chartType += "3d";
		}
		// 各种图形的通用属性
		properties.put("chartType", chartType);
		properties.put("chartTitle", str);
		// 各种图形的特定属性
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "模块");
			properties.put("yChartName", "待定");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "模块");
			properties.put("yChartName", "待定");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.PIE) >= 0) {
			properties.put("pieTextValues", xAxis);
		}
		// 生成JFreeChart对象
		chart = chartService.createJFreeChart(valueList, properties);
		return chart;
	}

	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String statType = dto.get("statType").toString();
		procSql.setProcedureName("proc_medicinfo");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("statType").toString());

		try {
			procSql.setSqlvalues(params);
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getStackTrace());
		}

		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results, statType);
		List<String> yAxis = getYaxis(results, statType);
		List<String> valueList = getValues(results, xAxis, yAxis, statType);
		if(xAxis.size()>0&&yAxis.size()>0) {
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
	
				for (Iterator<String> j = yAxis.iterator(); j.hasNext();) {
					String tmp1 = j.next();
					DynaBeanDTO r = new DynaBeanDTO();
					r.set("Xaxes", tmp);
					r.set("Yaxes", tmp1);
					r.set("XYsum", valueList.get(xAxis.indexOf(tmp) * yAxis.size()
							+ yAxis.indexOf(tmp1)));
	
					list.add(r);
				}
			}
		}
		return list;
	}

	private List<String> getXaxis(List result, String statType) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组

		String strX = null;
		if (statType.equals("userType"))
			strX = "Utime";
		else if (statType.equals("trackType"))
			strX = "ySum";
		else if (statType.equals("gonghaoType"))
			strX = "Gtime";

		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get(strX).toString());
		}
		return xAxis;
	}

	private List<String> getYaxis(List result, String statType) {
		List<String> yAxis = new ArrayList<String>();

		String strY = null;
		if (statType.equals("userType"))
			strY = "UserName";
		else if (statType.equals("trackType"))
			strY = "serverValue";
		else if (statType.equals("gonghaoType"))
			strY = "gonghao";

		// 处理Y坐标的label数组
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();

			yAxis.add(yLabel.get(strY).toString());
		}
		return yAxis;
	}

	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis, String statType) {
		// 根据X、Y坐标的Label处理数据数组
		List<String> valueList = new ArrayList<String>();

		String strX = null;
		if (statType.equals("userType"))
			strX = "Utime";
		else if (statType.equals("trackType"))
			strX = "ySum";
		else if (statType.equals("gonghaoType"))
			strX = "Gtime";

		String strY = null;
		if (statType.equals("userType"))
			strY = "UserName";
		else if (statType.equals("trackType"))
			strY = "serverValue";
		else if (statType.equals("gonghaoType"))
			strY = "gonghao";

		String strV = null;
		if (statType.equals("userType"))
			strV = "userNum";
		else if (statType.equals("trackType"))
			strV = "xSum";
		else if (statType.equals("gonghaoType"))
			strV = "gonghaoSum";

		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			String respondent = record.get(strX).toString();
			String processType = record.get(strY).toString();
			String value = record.get(strV).toString();
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
