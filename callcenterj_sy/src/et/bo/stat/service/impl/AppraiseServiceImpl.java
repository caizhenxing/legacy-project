/**
 * 	@(#) IvrServiceImpl.java 2008-4-14 下午01:09:59
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
import et.bo.stat.service.AppraiseService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * @author chen gang
 * 
 */
public class AppraiseServiceImpl implements AppraiseService {
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
		StatDateStr.setBeginEndTime(dto);
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("pro_appraise");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("qtype").toString());
		params.add(dto.get("qitem").toString());
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
		properties.put("chartTitle", "用户评价统计");
		// 各种图形的特定属性
		String xChartName="agent".equals(dto.get("qtype"))?"座席员统计":"专家统计";
		String appContent = dto.get("qitem").toString();
		String yChartName = "";
		if("1".equals(appContent))
			yChartName="“满意”评次数";
		if("2".equals(appContent))
			yChartName="“基本满意”评次数";
		if("3".equals(appContent))
			yChartName="“不满意”评次数";
//		if("4".equals(appContent))
//			yChartName="“不好”评次数";
//		if("5".equals(appContent))
//			yChartName="“差”评次数";
		
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
	public List userQuery()
	{
		String sql = "select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'";
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
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
		StatDateStr.setBeginEndTime(dto);
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// 调用存储过程取得统计结果
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("pro_appraise");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add(dto.get("qtype").toString());
//		params.add(dto.get("qitem").toString());
//
//		try {
//			procSql.setSqlvalues(params);
//		} catch (Exception e) {
//			// TODO: handle exception
////			System.out.println(e.getStackTrace());
//		}
//
//		results = procSql.execute();
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		int num = 0;
//		if(xAxis.size()>0&&yAxis.size()>0)
//		{
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//	
//				
//					DynaBeanDTO r = new DynaBeanDTO();
//					r.set("appraiseObj", tmp);
//					r.set("appraiseContent", yAxis.get(0));
//					r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//					
//					int sum = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//					num += sum;
//					r.set("num", num);
//					
//					list.add(r);
//				
//	
//			}
//		}
//		return list;
		String type = dto.get("qtype")==null?"":dto.get("qtype").toString();
		if("".equals(type.trim()))
		{
			type = "agent";
		}
		
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("[proc_typeChart]");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("and type = '"+type+"'");
		params.add("appraiseTime");
		params.add("appraiseNum");
		params.add(dto.get("agentNum").toString());
		params.add("cc_userAppraiseInfo");
		params.add("userAppraise");
		params.add("1,2,3");		
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
			int sc=Integer.parseInt(map.get("1").toString());
			int ss=Integer.parseInt(map.get("2").toString());
			int sd=Integer.parseInt(map.get("3").toString());
			r.set("name",map.get("col1"));
			r.set("count", map.get("1"));			
			r.set("count1",map.get("2"));
			r.set("count2",map.get("3"));
			r.set("rowCount",ss+sc+sd);
			int [] aa={sc,ss,sd,sc+ss+sd};
			mu.addBits(aa);
			list.add(r);
		}
		r=new DynaBeanDTO();
		int [] column=mu.getSumArr();
		r.set("name","合计（次）");
		r.set("count",column[0]);			
		r.set("count1",column[1]);
		r.set("count2",column[2]);
		r.set("rowCount",column[3]);
		list.add(r);
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
			String value = record.get("X").toString();
			String module = record.get("Y").toString();

			String mons = record.get("Sum1").toString();

			valueList.set((xAxis.indexOf(value)) * yAxis.size()
					+ yAxis.indexOf(module), mons);
		}
		return valueList;
	}
}
