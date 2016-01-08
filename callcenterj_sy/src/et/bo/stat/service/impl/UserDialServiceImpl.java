/**
 * 	@(#) TelServiceImpl.java 2008-4-11 下午01:09:59
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.UserDialService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author dengwei
 * 
 */
public class UserDialServiceImpl implements UserDialService {
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

		procSql.setProcedureName("proc_userPhoneStatistics");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("telnum").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();

		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		// 定制JFreeChart对象的属性
		Map<String, Object> properties = new HashMap<String, Object>();
		String chartType = dto.get("chartType").toString();
		if ("on".equals(dto.get("is3d"))) {
			chartType += "3d";
		}
		// 各种图形的通用属性
		properties.put("chartType", chartType);
		properties.put("chartTitle", "用户拨打统计");
		// 各种图形的特定属性
		String xChartName = "".equals(dto.get("telnum")) ? "电话号码" : "日期";
		String yChartName = "电话数量 通话时长";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", xChartName);
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", xChartName);
			properties.put("yChartName", yChartName);
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
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		StatDateStr.setBeginEndTime(dto);
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_userPhoneStatistics");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add(dto.get("telnum").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		int num1 = 0;
//		int num2 = 0;
//		if (xAxis.size() > 0 && yAxis.size() > 0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("name", tmp);
//				r.set("type1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				r.set("type2", valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1));
//				r.set("type3", new Integer(Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()))
//						+ Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1))));
//				
//				/*计算总量*/
//				int sum1 = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				int sum2 = sum1 + Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1));
//				
//				num1 += sum1;
//				num2 += sum2;
//				r.set("num1", num1);
//				r.set("num2", num2);
//				list.add(r);
//			}
//		}
//		Collections.reverse(list);
		//return list;
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_userPhoneStatisticsNew");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("telnum").toString());	
		procSql.setSqlvalues(params);
		results = procSql.execute();
		List list1=(List)results.get(0);
		int num=0;
		int num1=0;
		DynaBeanDTO r=null;
		MathUtil mu = new MathUtil();
		for(int k=0;k<list1.size();k++){
			Map map=(Map)list1.get(k);
			r = new DynaBeanDTO();
			String telCount = map.get("telCount")==null?"0":map.get("telCount").toString();
			String sumLen = map.get("sumLen")==null?"0":map.get("sumLen").toString();
			int sc=Integer.parseInt(telCount);
			int ss=(int)Float.parseFloat(sumLen);
			r.set("name",map.get("xcol"));
			r.set("count", sc);			
			r.set("count1",ss);
			int [] aa={sc,ss};
			mu.addBits(aa);
			list.add(r);
		}
		r=new DynaBeanDTO();
		int [] column=mu.getSumArr();
		r.set("name","合计");
		r.set("count",column[0]);			
		r.set("count1",column[1]);
		list.add(r);
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get("xaxis").toString());
		}
		return xAxis;
	}

	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// 处理Y坐标的label数组
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("yaxis").toString());
		}
		return yAxis;
	}

	private List<String> getValues(List result, List<String> xAxis, List<String> yAxis) {
		// 根据X、Y坐标的Label处理数据数组
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			String respondent = record.get("xaxis").toString();
			String processType = record.get("yaxis").toString();
			String value = record.get("value").toString();
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size() + yAxis.indexOf(processType), value);
		}
		return valueList;
	}
	

}
