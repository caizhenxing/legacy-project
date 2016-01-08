/**
 * 	@(#) TelServiceImpl.java 2008-6-26 11:01
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
import et.bo.stat.service.HZCaseInfoFroExportService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class HZCaseInfoForExportServiceImpl implements HZCaseInfoFroExportService {
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
		procSql.setProcedureName("proc_HZCaseInfoStatisticsByExpert");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String caseExpert=dto.get("caseExpert").toString();
		if(caseExpert!=null&&!caseExpert.equals("")){
			params.add(caseExpert);
		}
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
		properties.put("chartTitle", "专家受理案例统计");
		// 各种图形的特定属性
		String yChartName="案例数";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "受理专家");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "受理专家");
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
	'select case_expert, dbo.uf_getDateStr(case_time) as case_time from oper_caseinfo
		where dict_case_type=''HZCase'' 
			and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime) '
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='HZCase'";


		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("caseExpert").toString();
		params.add("case_expert");
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
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_HZCaseInfoStatisticsByExpert");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		String caseExpert=dto.get("caseExpert").toString();
//		if(caseExpert.equals("0")){
//			caseExpert="";
//		}
//		if(caseExpert!=null){
//			params.add(caseExpert);
//		}
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		Map map=getValue(results, xAxis, yAxis);	
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = yAxis.iterator(); i.hasNext();) {
//				String export = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("export", export);
//				r.set("count", map.get(export));				
//				list.add(r);
//			}
//		}
//		return list;
		
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
	
	private List getValue1(List result, List<String> xAxis,
			List<String> yAxis) {
		List results=new ArrayList();
		List<String> l=new ArrayList<String>();
		for(int i=0;i<xAxis.size();i++){
			String export=xAxis.get(i);
			
			l.add(export);
			List list=(List)result.get(2);
			int num=0;
			int num1=0;
			
			for(int k=0;k<list.size();k++){
				Map record=(Map)list.get(k);
				
				String y=yAxis.get(0);
				String z=yAxis.get(1);				
				if(export.equals(record.get("xaxis").toString())&&y.equals(record.get("yxis"))){
					num=Integer.parseInt(record.get("value").toString());					
					l.add(num+"");
				}
					num1=Integer.parseInt(record.get("value").toString());						
					l.add(num1+"");
					
				}					
			
			results.add(l);
		}
		return results;
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
	
	private Map getValue(List result, List<String> xAxis,
			List<String> yAxis) {
		Map re=new HashMap();
		for(int i=0;i<yAxis.size();i++){
			String export=yAxis.get(i);
			
			List list=(List)result.get(2);
			int num=0;
			for(int k=0;k<list.size();k++){
				Map record=(Map)list.get(k);
				
				if(export.equals(record.get("yaxis").toString())){
					num+=Integer.parseInt(record.get("value").toString());
				}
			}
			re.put(export, num);
		}
		return re;
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
			String respondent = record.get("xaxis").toString();
			String processType = record.get("yaxis").toString();
			String value = record.get("value").toString();
			
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
