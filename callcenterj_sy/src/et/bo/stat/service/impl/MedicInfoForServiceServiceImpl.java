/**
 * 	@(#) TelServiceImpl.java 2008-6-26 11:01
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
import et.bo.stat.service.MedicInfoForServiceService;
import et.bo.stat.service.ProductPriceForTypeService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class MedicInfoForServiceServiceImpl implements MedicInfoForServiceService {
	private BaseDAO dao;
	
	private ClassTreeService cts;
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
//		procSql.setProcedureName("proc_medicInfoByServiceType");
//		统计条件中加入“座席工号”
		procSql.setProcedureName("proc_medicInfoByServiceType_addAgentId");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		
		String agentId = dto.get("agentId").toString();
		if(agentId!=null&&!"".equals(agentId.trim()))
			params.add(" and book_rid = '"+agentId+"' ");
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
		properties.put("chartTitle", "各服务类型下的医疗信息条数和用户数量");
		// 各种图形的特定属性
		String yChartName="信息条数、用户数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "服务类型");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "服务类型");
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
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		
		String agentId = dto.get("agentId").toString();
		if(agentId!=null&&!"".equals(agentId.trim()))
			params.add(" and book_rid = '"+agentId+"' ");
		else
			params.add("");
		
		params.add("create_time");
		params.add("expert");
		params.add(dto.get("name").toString());
		params.add("oper_book_medicinfo");
		params.add("dict_service_type");
		params.add("SYS_TREE_0000000714,SYS_TREE_0000000715");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		
		List lv = (List)results.get(0);
		DynaBeanDTO r = null;
		MathUtil mu = new MathUtil();
		for(int i=0; i<lv.size(); i++)
		{
			r = new DynaBeanDTO();
			Map m = (Map)lv.get(i);
			r.set("name", m.get("col1"));
			try
			{
				int i_1 = Integer.parseInt(m.get("SYS_TREE_0000000714").toString());
				int i_2 = Integer.parseInt(m.get("SYS_TREE_0000000715").toString());
				int i_rowSum = i_1 + i_2 ;
				r.set("count", i_1);
				r.set("count1", i_2);
				r.set("rowCount", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_1,i_2,i_rowSum};
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
		r.set("name", "合计(列)");
		int colSums[] = mu.getSumArr();
		r.set("count", colSums[0]);
		r.set("count1", colSums[1]);
		r.set("rowCount", colSums[2]);
		list.add(r);
		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);	
//		List<String> valueList = getValues(results, xAxis, yAxis);
////		System.out.println("value size : "+valueList.size());
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				if(tmp!=null){
//					r.set("serviceType", cts.getLabelById(tmp));
//				}
//				r.set("infoCount", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				r
//						.set("userCount", valueList.get(xAxis.indexOf(tmp)
//								* yAxis.size() + 1));
//				
//				list.add(r);
//			}
//		}
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
			
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}

	/**
	 * @return cts
	 */
	public ClassTreeService getCts() {
		return cts;
	}

	/**
	 * @param cts 要设置的 cts
	 */
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
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
