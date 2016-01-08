/*
 * @(#)PriceService.java	 2008-04-14
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.stat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.CallCountStatService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author nie
 */
public class CallCountStatImpl implements CallCountStatService {
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

	public JFreeChart statistic(String chartType, String dateType) {
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy年MM月");
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy年");
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
//		String dateType = dto.get("dateType").toString();
		if("day".equals(dateType))
			procSql.setProcedureName("proc_statisticsDay");
		else if("month".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonth");
		else if("year".equals(dateType))
			procSql.setProcedureName("proc_statisticsYear");
		
		else if("colday".equals(dateType))
			procSql.setProcedureName("proc_statisticsDayByType");
		else if("colmonth".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonthByType");
		else if("colyear".equals(dateType))
			procSql.setProcedureName("proc_statisticsYearByType");
		
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		// 定制JFreeChart对象的属性
		Map<String, Object> properties = new HashMap<String, Object>();
//		String chartType = dto.get("chartType").toString();
//		if ("on".equals(dto.get("is3d"))) {
			chartType += "3d";
//		}
		// 各种图形的通用属性
		properties.put("chartType", chartType);
		
		if(dateType.equals("day"))
			properties.put("chartTitle", "时段咨询总量统计-- "+sdfMonth.format(TimeUtil.getNowTime())+" 每日咨询量");
		if(dateType.equals("month"))
			properties.put("chartTitle", "时段咨询总量统计-- "+sdfYear.format(TimeUtil.getNowTime())+" 每月咨询量");
		if(dateType.equals("year"))
			properties.put("chartTitle", "时段咨询总量统计-- "+"历年咨询量");
		if(dateType.equals("colday"))
			properties.put("chartTitle", "栏目咨询总量统计-- "+sdfDay.format(TimeUtil.getNowTime())+" 栏目咨询量");
		if(dateType.equals("colmonth"))
			properties.put("chartTitle", "栏目咨询总量统计-- "+sdfMonth.format(TimeUtil.getNowTime())+" 栏目咨询量");
		if(dateType.equals("colyear"))
			properties.put("chartTitle", "栏目咨询总量统计-- "+sdfYear.format(TimeUtil.getNowTime())+" 栏目咨询量");
		
		// 各种图形的特定属性
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			
			if("day".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  日咨询量");
			else if("month".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  月咨询量");
			else if("year".equals(dateType))
				properties.put("xChartName", "历年咨询量");
			
			else if("colday".equals(dateType))
				properties.put("xChartName", sdfDay.format(TimeUtil.getNowTime())+"  栏目咨询量");
			else if("colmonth".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  栏目咨询量");
			else if("colyear".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  栏目咨询量");

			properties.put("yChartName", "咨询数量");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
			
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			
			if("day".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime()));
			else if("month".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime()));
			else if("year".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime()));
			
			else if("colday".equals(dateType))
				properties.put("xChartName", sdfDay.format(TimeUtil.getNowTime())+"  咨询栏目名称");
			else if("colmonth".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  咨询栏目名称");
			else if("colyear".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  咨询栏目名称");
	
			properties.put("yChartName", "咨询数量");
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
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		String dateType = dto.get("dateType").toString();
		if("day".equals(dateType))
			procSql.setProcedureName("proc_statisticsDay");
		else if("month".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonth");
		else if("year".equals(dateType))
			procSql.setProcedureName("proc_statisticsYear");
		
		else if("colday".equals(dateType))
			procSql.setProcedureName("proc_statisticsDayByType");
		else if("colmonth".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonthByType");
		else if("colyear".equals(dateType))
			procSql.setProcedureName("proc_statisticsYearByType");
		
//		procSql.setProcedureName("proc_priceinfo");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		if(xAxis.size()>0&&yAxis.size()>0)
		{
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("date", tmp);
				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
		
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

	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis) {
		// 根据X、Y坐标的Label处理数据数组
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			String X = record.get("xaxis").toString();
			String Y = record.get("yaxis").toString();
			String value = record.get("value").toString();
			valueList.set((xAxis.indexOf(X)) * yAxis.size()
					+ yAxis.indexOf(Y), value);
		}
		return valueList;
	}
}
