/* 包    名：et.bo.stat.service.impl
 * 文 件 名：FocusCaseInfoUserImpl.java
 * 注释时间：2008-8-28 13:52:55
 * 版权所有：沈阳市卓越科技有限公司。
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
import et.bo.stat.service.FocusCaseInfoUserService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * The Class FocusCaseInfoUserImpl.
 * 
 * @author Wang Lichun
 */
public class FocusCaseInfoUserImpl implements FocusCaseInfoUserService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;

	/**
	 * Gets the charService.
	 * The charService type is ClassTreeService.
	 * @return the charService
	 */
	public JFreeService getChartService() {
		return chartService;
	}

	/**
	 * Sets the charService.
	 * The charService type is ClassTreeService.
	 * @param chartService the new charService
	 */
	public void setChartService(JFreeService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Gets the proc sql.
	 * 
	 * @return the proc sql
	 */
	public ProcSql getProcSql() {
		return procSql;
	}

	/**
	 * Sets the proc sql.
	 * 
	 * @param procSql the new proc sql
	 */
	public void setProcSql(ProcSql procSql) {
		this.procSql = procSql;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao the new dao
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * Gets the ks.
	 * The ks type is KeyService.
	 * @return the ks
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * Sets the ks.
	 * The ks type is KeyService.
	 * @param ks the new ks
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * 获得统计图表
	 * params IBaseDTO dto
	 * return JFreeChart 
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		StatDateStr.setBeginEndTime(dto);
		procSql.setProcedureName("proc_focusCaseInfoStatisticsBySeat");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
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
		properties.put("chartTitle", "座席员受理的案例数量统计");
		// 各种图形的特定属性
//		String yChartName="count".equals(dto.get("condition"))?"电话数量":"通话时长";
		String yChartName="案例数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
//			properties.put("xChartName", "座席工号");
			properties.put("xChartName", "受理工号");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
//			properties.put("xChartName", "座席工号");
			properties.put("xChartName", "受理工号");
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

	/*
		set @src = 'select case_rid, case_time from oper_caseinfo
		where dict_case_type=''FocusCase'''
		set @mask = 'focusCaseInfoStatisticsBySeat'
		set @columns = 'count|case_rid|案例'
		set @groupby = 'case_rid'
	 */
	/**
	 * 获得统计结果
	 * params IBaseDTO dto
	 * return 
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='FocusCase'";


		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("userId").toString();
		params.add("case_rid");
		params.add(temp);
		params.add("oper_caseinfo");
		params.add("state");
		params.add("待审,原始,发布,已审,驳回");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		
		// 对存储过程返回的的结果进行分拣
		List lv = (List)results.get(0);
		DynaBeanDTO r = null;
		MathUtil mu = new MathUtil();
		for(int i=0; i<lv.size(); i++)
		{
			r = new DynaBeanDTO();
			Map m = (Map)lv.get(i);
			r.set("col1", m.get("col1"));
			try
			{
				int i_ds = Integer.parseInt(m.get("待审").toString());
				int i_ys = Integer.parseInt(m.get("原始").toString());
				int i_fb = Integer.parseInt(m.get("发布").toString());
				int i_checked = Integer.parseInt(m.get("已审").toString());
				int i_back = Integer.parseInt(m.get("驳回").toString());
				int i_rowSum = i_ds + i_ys + i_fb + i_checked+i_back;
				r.set("待审", i_ds);
				r.set("原始", i_ys);
				r.set("发布", i_fb);
				r.set("已审", i_checked);
				r.set("驳回", i_back);
				r.set("rowSum", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_ds,i_ys,i_fb,i_checked,i_rowSum,i_back};
				mu.addBits(rArr);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			list.add(r);
		}
		//列汇总
		r = new DynaBeanDTO();
		r.set("col1", "合计(例)");
		int colSums[] = mu.getSumArr();
		r.set("待审", colSums[0]);
		r.set("原始", colSums[1]);
		r.set("发布", colSums[2]);
		r.set("已审", colSums[3]);
		r.set("驳回", colSums[5]);
		r.set("rowSum", colSums[4]);
		list.add(r);
		return list;
		// TODO Auto-generated method stub
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_focusCaseInfoStatisticsBySeat");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		Integer nums = 0;
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("id", tmp);
//				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				nums += Integer.valueOf(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				r.set("nums", nums);
//				list.add(r);
//			}
//		}
//		return list;
	}

	/**
	 * Gets the xaxis.
	 * 
	 * @param result the result
	 * 
	 * @return the xaxis
	 */
	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get("xaxis").toString());
		}
		return xAxis;
	}

	/**
	 * Gets the yaxis.
	 * 
	 * @param result the result
	 * 
	 * @return the yaxis
	 */
	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// 处理Y坐标的label数组
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("yaxis").toString());
		}
		return yAxis;
	}

	/**
	 * Gets the values.
	 * 
	 * @param result the result
	 * @param xAxis the x axis
	 * @param yAxis the y axis
	 * 
	 * @return the values
	 */
	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis) {
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
