/**
 * 	@(#) TelServiceImpl.java 2008-6-26 11:01
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.FocusInfoAllEditorService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class FocusInfoAllEditorImpl implements FocusInfoAllEditorService {
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
		procSql.setProcedureName("proc_focusInfoAllEditor");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
//		params.add(dto.get("name").toString());
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
		properties.put("chartTitle", "全部责任编辑和各编辑被领导批示过的产品数量");
		// 各种图形的特定属性
		String yChartName="全部责任编辑和各编辑被领导批示过的产品数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "责任编辑");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "责任编辑");
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
		StatDateStr.setBeginEndTime(dto);
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		String name=dto.get("name").toString();
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_typeChart");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add("");
//		params.add("create_time");
//		params.add("charge_editor");
//		params.add(name);
//		params.add("oper_focusinfo");
//		params.add("state");
//		params.add("初稿,一审,二审,三审,发布");
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		List lv = (List)results.get(0);
//		DynaBeanDTO r = null;
//		MathUtil mu = new MathUtil();
//		for(int i=0; i<lv.size(); i++)
//		{
//			r = new DynaBeanDTO();
//			Map m = (Map)lv.get(i);
//			r.set("name", m.get("col1"));
//			try
//			{
//				int i_1 = Integer.parseInt(m.get("初稿").toString());
//				int i_2 = Integer.parseInt(m.get("一审").toString());
//				int i_3 = Integer.parseInt(m.get("二审").toString());
//				int i_4 = Integer.parseInt(m.get("三审").toString());
//				int i_5 = Integer.parseInt(m.get("发布").toString());
//				int i_rowSum = i_1+i_2+i_3+i_4+i_5;
//				r.set("count", i_1);
//				r.set("count1", i_2);
//				r.set("count2", i_3);
//				r.set("count3", i_4);
//				r.set("count4", i_5);
//				r.set("rowCount", i_rowSum); //行汇总
//
//				int rArr[] = {i_1,i_2,i_3,i_4,i_5,i_rowSum};
//				mu.addBits(rArr);
//			
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	
//			list.add(r);
//		}
//
//		r = new DynaBeanDTO();
//		r.set("name", "合计(期)");
//		int colSums[] = mu.getSumArr();
//		r.set("count", colSums[0]);
//		r.set("count1", colSums[1]);
//		r.set("count2", colSums[2]);
//		r.set("count3", colSums[3]);
//		r.set("count4", colSums[4]);
//		r.set("rowCount", colSums[5]);
//		list.add(r);
//
//		return list;
		/*2009-2-6
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("[proc_typeChart]");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("");
		params.add("create_time");
		params.add("charge_editor");
		params.add(dto.get("name").toString());
		params.add("oper_focusinfo");
		params.add("dict_focus_type");
		params.add("市场版,实事版");		
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
			int sc=Integer.parseInt(map.get("市场版").toString());
			int ss=Integer.parseInt(map.get("实事版").toString());
			r.set("name",map.get("col1"));
			r.set("count", map.get("市场版"));			
			r.set("count1",map.get("实事版"));
			r.set("rowCount",ss+sc);
			int [] aa={sc,ss,sc+ss};
			mu.addBits(aa);
			list.add(r);
		}
		r=new DynaBeanDTO();
		int [] column=mu.getSumArr();
		r.set("name","合计（版）");
		r.set("count",column[0]);			
		r.set("count1",column[1]);
		r.set("rowCount",column[2]);
		list.add(r);
		*/
//		******************************************************************************

		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();		
		String beginTime =(String)dto.get("beginTime");
		String endTime =(String)dto.get("endTime");		
		String chargeEditor = (String)dto.get("name");
		if(chargeEditor!=null&&!"".equals(chargeEditor.trim())){
			String sql="select charge_editor,create_time,( case when len(lead_instruction)>0 then 1 else 0 end) as lead_count from oper_focusinfo where charge_editor='"+chargeEditor+"' ";
			
			if(beginTime!=null&&!"".equals(beginTime.trim()))	
				sql+=" and create_time >= '"+beginTime+"' ";
			if(endTime!=null&&!"".equals(endTime.trim()))
				sql+=" and create_time <= '"+addoneDate(endTime)+"' ";
//			System.out.println("sql = "+sql);
			RowSet rs= dao.getRowSetByJDBCsql(sql);
			try{
				if(rs!=null){
					int i_count=0;
					while(rs.next()){
//						System.out.println("charge_editor = "+rs.getString("charge_editor"));
						DynaBeanDTO dbdto=new DynaBeanDTO();
						String create_time = rs.getString("create_time");
						if(create_time!=null&&create_time.length()>19)
							create_time = create_time.substring(0,19);
						
						dbdto.set("name",create_time);
						String lead_count = rs.getString("lead_count");
						dbdto.set("count",lead_count);
						try{
							i_count+=Integer.parseInt(lead_count);
						}catch(Exception e){}
						list.add(dbdto);
					}
					DynaBeanDTO dbdto=new DynaBeanDTO();
					dbdto.set("name","合计");
					dbdto.set("count",i_count);

					list.add(dbdto);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			String sql="select charge_editor,sum( case when len(lead_instruction)>0 then 1 else 0 end) lead_count from oper_focusinfo where 0=0 ";
			if(beginTime!=null&&!"".equals(beginTime.trim()))	
				sql+=" and create_time >= '"+beginTime+"' ";
			if(endTime!=null&&!"".equals(endTime.trim()))
				sql+=" and create_time <= '"+addoneDate(endTime)+"' ";
			
			sql+=" group by charge_editor ";
//			System.out.println("sql = "+sql);
			RowSet rs= dao.getRowSetByJDBCsql(sql);
			try{
				if(rs!=null){
					int i_count=0;
					while(rs.next()){
						DynaBeanDTO dbdto=new DynaBeanDTO();						
						dbdto.set("name",rs.getString("charge_editor"));
						String lead_count = rs.getString("lead_count");
						dbdto.set("count",lead_count);
						try{
							i_count+=Integer.parseInt(lead_count);
						}catch(Exception e){}
						list.add(dbdto);
					}
					DynaBeanDTO dbdto=new DynaBeanDTO();
					dbdto.set("name","合计");
					dbdto.set("count",i_count);

					list.add(dbdto);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			
			try {

					xAxis.add(xLabel.get("xaxis").toString());
				
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
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
	
	private List getValue(List result, List<String> xAxis,
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

	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis) {
		// 根据X、Y坐标的Label处理数据数组
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			if(record.get("xaxis")!=null){
				String respondent = record.get("xaxis").toString();
				String processType = record.get("yaxis").toString();
				String value = record.get("value").toString();
				valueList.set((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType), value);
			}
			
		}
		return valueList;
	}
	
//	日期增加一天
	private String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}
}
