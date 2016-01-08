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
import et.bo.stat.service.EffectCaseInfoPropertyStatService;
import et.bo.sys.common.MathUtil;
import et.bo.sys.common.SysStaticParameter;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author chen gang
 * 
 */
public class EffectCaseInfoPropertyStatImpl implements EffectCaseInfoPropertyStatService {
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

	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		StatDateStr.setBeginEndTime(dto);
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
//		procSql.setProcedureName("proc_effectCaseInfoStatisticsByProperty");
//		统计条件中加入“案例属性”
		procSql.setProcedureName("proc_effectCaseInfoStatisticsByProperty_addCaseAttr5");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("agentId"));
//		String type = dto.get("condition").toString();
//		if("class1".equals(type))
//			type = "大类";
//		else if("class2".equals(type))
//			type = "小类";
//		else if("class3".equals(type))
//			type = "种类";
//		
//		params.add(type);
		
		String caseAttr5 = (String)dto.get("caseAttr5");
		if(caseAttr5!=null&&!"".equals(caseAttr5.trim()))			
			params.add(" and case_attr5 ='"+caseAttr5+"' ");
		else
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
		properties.put("chartTitle", "每一案例属性下的案例数量统计");
		// 各种图形的特定属性
//		String yChartName="count".equals(dto.get("condition"))?"电话数量":"通话时长";
		String yChartName="案例数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "案例类别");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "案例数量");
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
	/**
	 * set @src = 'select case_attr5, addtime from oper_caseinfo
						where dict_case_type=''effectCase'''+
						' and addtime  between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='effectCase'";
		String agent = (String)dto.get("agentId");

		if(agent!=null&&!"".equals(agent))
		{
			andSql += " and case_rid = '"+agent+"'";
		}

		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		params.add("case_attr5");
		
		String caseAttr5 = (String)dto.get("caseAttr5");
		if(caseAttr5!=null&&!"".equals(caseAttr5.trim()))			
			params.add(caseAttr5);
		else
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
//		// TODO Auto-generated method stub
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_effectCaseInfoStatisticsByProperty");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add((String)dto.get("agentId"));
////		String type = dto.get("condition").toString();
////		if("class1".equals(type))
////			type = "大类";
////		else if("class2".equals(type))
////			type = "小类";
////		else if("class3".equals(type))
////			type = "种类";
////		
////		params.add(type);
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("condition", tmp);
//				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//	//			r
//	//					.set("type2", valueList.get(xAxis.indexOf(tmp)
//	//							* yAxis.size() + 1));
//	//			r.set("type3", new Integer(Integer.parseInt(valueList.get(xAxis
//	//					.indexOf(tmp)
//	//					* yAxis.size()))
//	//					+ Integer.parseInt(valueList.get(xAxis.indexOf(tmp)
//	//							* yAxis.size() + 1))));
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
	 * 人员列表为查询页面提供数据
	*/
	public List<LabelValueBean> getUserList()
	{
		List<LabelValueBean> uList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_AGENT_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		LabelValueBean bean = null;
		SysUser su = new SysUser();
		for(int i=0; i<os.length; i++)
		{
			bean = new LabelValueBean();
			su = (SysUser)os[i];
			try
			{
			bean.setLabel(su.getUserName());
			bean.setValue(su.getUserId());
			}catch(Exception e){e.printStackTrace();}
			uList.add(bean);
		}
	
		return uList;
	}
}
