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
import et.bo.stat.service.CaseService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author dengwei
 * 
 */
public class CaseServiceImpl implements CaseService {
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
		StatDateStr.setBeginEndTime(dto);
		// 根据输入的条件调用存储过程
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
//		procSql.setProcedureName("proc_generalCaseInfoStatisticsByProperty");
//		统计条件中加入“座席工号”
		procSql.setProcedureName("proc_generalCaseInfoStatisticsByProperty_addAgentNum");
		
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
//		StringBuffer sb=new StringBuffer();
//		sb.append(dto.get("caseAttr1").toString());
//		sb.append(dto.get("caseAttr2").toString());
//		sb.append(dto.get("caseAttr3").toString());
//		params.add(sb.toString());
		String temp=dto.get("caseType").toString();
		if("big".equals(temp))	params.add("大类");
		if("small".equals(temp))	params.add("小类");
		//if("kinds".equals(temp))	params.add("种类");
		
		String caseAttr3 = dto.get("caseAttr3").toString();
		if (!caseAttr3.equals("")) {
			params.add(caseAttr3);
		}
		
		String agentNum = (String)dto.get("agentNum");
		if(agentNum!=null&&!"".equals(agentNum.trim())){
			params.add(" and case_rid = '"+agentNum+"' ");
		}else 
			params.add("");
		
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
		properties.put("chartTitle", "案件统计");
		// 各种图形的特定属性
		String xChartName="".equals(dto.get("caseAttr1"))?"大类/小类/种类":"时间";
		String yChartName="案例数量（单位：个）";
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
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		StatDateStr.setBeginEndTime(dto);
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='putong'";
		//种类
		String caseAttr3 = dto.get("caseAttr3").toString();
		if (!caseAttr3.equals("")&&!"0".equals(caseAttr3)) {
			andSql = andSql + " and case_attr3 = '"+caseAttr3+"'";
		}

		String agentNum = dto.get("agentNum").toString();
		if(agentNum!=null&&!"".equals(agentNum.trim())){
			andSql += " and case_rid = '"+agentNum+"' ";
		}
		
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("caseType").toString();
		if("big".equals(temp))	params.add("case_attr1");
		else if("small".equals(temp))	params.add("case_attr2");		
		params.add("");
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
	
}
