/*
 * @(#)UseService.java	 2008-04-14
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
import et.bo.stat.service.UseService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author nie
 */
public class UseServiceImpl implements UseService {
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
		
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_use");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("address").toString());
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
		properties.put("chartTitle", "使用情况统计");
		// 各种图形的特定属性
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "栏目分类");
			properties.put("yChartName", "使用情况");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "栏目分类");
			properties.put("yChartName", "使用情况");
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
		procSql.setProcedureName("proc_use");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("address").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		int num = 0;
		if(xAxis.size()>0&&yAxis.size()>0) {
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("X", tmp);
				r.set("Sum1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				
				/*计算总数*/
				int sum = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				num += sum;
				r.set("num", num);
				list.add(r);
			}
		}
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get("X").toString());
		}
		return xAxis;
	}

	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// 处理Y坐标的label数组
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("Y").toString());
		}
		return yAxis;
	}

	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis) {
		// 根据X、Y坐标的Label处理数据数组
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			String X = record.get("X").toString();
			String Y = record.get("Y").toString();
			String value = record.get("Sum1").toString();
			valueList.set((xAxis.indexOf(X)) * yAxis.size()
					+ yAxis.indexOf(Y), value);
		}
		return valueList;
	}
}
