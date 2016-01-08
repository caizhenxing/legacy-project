/**
 * 	@(#) TelServiceImpl.java 2008-4-11 下午01:09:59
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.HZCaseInfoStatisticsByStateService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>会诊案例库下全部座席员和每一座席员受理的各审核状态下的案例数量action</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class HZCaseInfoStatisticsByStateImpl implements HZCaseInfoStatisticsByStateService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;

	/**
	 * 获得受理工号列表
	 */
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
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
	/**
	 * 根据dto对象的参数值，执行统计，并返回根据统计结果生成的JFreeChart对象
	 * 显示全部座席员和每一座席员受理的各审核状态下的统计图
	 * @param dto
	 * @return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_HZCaseInfoStatisticsByState");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String agentNum = (String)dto.get("agentNum");
		if(agentNum == null )
		{
			agentNum = "";
		}
		else
		{
			agentNum = agentNum.trim();
		}
		params.add(agentNum);
//		params.add(dto.get("condition").toString());
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
		properties.put("chartTitle", "全部或每一座席员受理各审核状态下的案例数量统计");
		// 各种图形的特定属性
//		String yChartName="count".equals(dto.get("condition"))?"电话数量":"通话时长";
		String yChartName="案例数量";
		String xV = "";
		String yV = "";
		if("".equals(agentNum))
		{
			xV = "状态";
			yV = "案例";
		}
		else
		{
			xV = "日期";
			yV = "状态";
		}
		/*
		If (座席员号.equals(“”)){
			X: 状态，Y：“案例”，Z：数值
		}else{
			X:日期，Y：状态，Z：数值
		}
		*/
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", xV);
			properties.put("yChartName", yV);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", xV);
			properties.put("yChartName", yV);
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
	 	set @src = 'select state,dbo.uf_getDateStr(case_time) as oper_time from oper_caseinfo
		where dict_case_type=''HZCase''' + 
			' and case_rid=''' + ltrim(rtrim(@seat)) + '''' +
			' and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)' + 
			' and case_rid = ''' + @seat + ''''
	 */
	/**
	 * 根据DTO对象的参数值，执行统计，并将统计结果进行处理，生成符合报表格式的列表
	 * 显示全部座席员和每一座席员受理的各审核状态下的案例数量 显示 列表信息
	 * @param dto
	 * @return List
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		***************list代码
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='HZCase' ";


		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("agentNum").toString();
		params.add("case_rid");
		params.add(temp);
		params.add("oper_caseinfo");
		params.add("state");
		params.add("待审,原始,发布,已审");
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
				int i_rowSum = i_ds + i_ys + i_fb + i_checked;
				r.set("待审", i_ds);
				r.set("原始", i_ys);
				r.set("发布", i_fb);
				r.set("已审", i_checked);
				r.set("rowSum", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_ds,i_ys,i_fb,i_checked,i_rowSum};
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
		r.set("rowSum", colSums[4]);
		list.add(r);
		return list;
		// TODO Auto-generated method stub
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		//proc_HZCaseInfoStatisticsByState
////		procSql.setProcedureName("proc_HZCaseInfoStatisticsByState");
////		params.add(dto.get("beginTime").toString());
////		params.add(dto.get("endTime").toString());
//		String agentNum = (String)dto.get("agentNum");
//		
//		if (!agentNum.equals("")) {
//			procSql.setProcedureName("proc_HZCaseInfoStatisticsMultiHasSeat");
//			params.add(dto.get("beginTime").toString());
//			params.add(dto.get("endTime").toString());
//			params.add(agentNum);
//		}else{
//			procSql.setProcedureName("proc_HZCaseInfoStatisticsMultiNoSeat");
//			params.add(dto.get("beginTime").toString());
//			params.add(dto.get("endTime").toString());
//		}
//		
//
//		
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		
//		if (!dto.get("agentNum").toString().equals("")) {
//			for (Iterator i = ((List) results.get(0)).iterator(); i.hasNext();) {
//				Map xLabel = (Map)i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("xtype", xLabel.get("case_date").toString());
//				r.set("yuanshi", xLabel.get("yuanshi").toString());
//				r.set("daishen", xLabel.get("daishen").toString());
//				r.set("bohui", xLabel.get("bohui").toString());
//				r.set("yishen", xLabel.get("yishen").toString());
//				r.set("fabu", xLabel.get("fabu").toString());
//				list.add(r);
//			}
//		}else{
//			for (Iterator i = ((List) results.get(0)).iterator(); i.hasNext();) {
//				Map xLabel = (Map)i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("xtype", xLabel.get("id").toString());
//				r.set("yuanshi", xLabel.get("yuanshi").toString());
//				r.set("daishen", xLabel.get("daishen").toString());
//				r.set("bohui", xLabel.get("bohui").toString());
//				r.set("yishen", xLabel.get("yishen").toString());
//				r.set("fabu", xLabel.get("fabu").toString());
//				list.add(r);
//			}
//		}
//
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
			String respondent = record.get("xaxis").toString();
			String processType = record.get("yaxis").toString();
			String value = record.get("value").toString();
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
