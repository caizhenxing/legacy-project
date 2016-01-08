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
import et.bo.stat.service.CorpInfoByServiceTypeService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class CorpInfoByServiceTypeServiceImpl.
 * 企业信息库之信息类型量统计
 * @author Wang Lichun
 */
public class CorpInfoByServiceTypeServiceImpl implements CorpInfoByServiceTypeService {
	private BaseDAO dao;

	private KeyService ks;

	private ClassTreeService cts;
	private ProcSql procSql;

	private JFreeService chartService;

	/**
	 * Gets the charService.
	 * The charService type is JFreeService.
	 * @return the charService
	 */
	public JFreeService getChartService() {
		return chartService;
	}

	/**
	 * Sets the charService.
	 * The charService type is JFreeService.
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
	 * @param dao the new BaseDAO
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
	 * 生成统计图表
	 * param IBaseDTO dto
	 * return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_corpInfoByServiceType");
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
		properties.put("chartTitle", "各服务类型下的企业信息统计");
		// 各种图形的特定属性
		String yChartName="信息和用户数量";
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
	
	/**
	 * 获得坐席员列表
	 * params String sql
	 * return List		
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
	
	
	/** 
	 * 获得统计结果列表
	 * params IBaseDTO dto
	 * return List
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("");
		params.add("createtime");
		params.add("corp_rid");
		params.add(dto.get("agentId").toString());
		params.add("oper_corpinfo");
		params.add("dict_service_type");
		params.add("SYS_TREE_0000000717,SYS_TREE_0000000718,SYS_TREE_0000000719,SYS_TREE_0000000720,SYS_TREE_0000000741");
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
				int i_1 = Integer.parseInt(m.get("SYS_TREE_0000000717").toString());
				int i_2 = Integer.parseInt(m.get("SYS_TREE_0000000718").toString());
				int i_3 = Integer.parseInt(m.get("SYS_TREE_0000000719").toString());
				int i_4 = Integer.parseInt(m.get("SYS_TREE_0000000720").toString());
				int i_5 = Integer.parseInt(m.get("SYS_TREE_0000000741").toString());
				int i_rowSum = i_1 + i_2 + i_3 + i_4+ i_5;
				r.set("count", i_1);
				r.set("count1", i_2);
				r.set("count2", i_3);
				r.set("count3", i_4);
				r.set("count4", i_5);
				r.set("rowCount", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_1 , i_2 , i_3 , i_4, i_5,i_rowSum};
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
		r.set("name", "合计(个)");
		int colSums[] = mu.getSumArr();
		r.set("count", colSums[0]);
		r.set("count1", colSums[1]);
		r.set("count2", colSums[2]);
		r.set("count3", colSums[3]);
		r.set("count4", colSums[4]);
		r.set("rowCount", colSums[5]);
		list.add(r);
//		procSql.setProcedureName("proc_corpInfoByServiceType");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);		
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("serviceType", cts.getLabelById(tmp));
////				r.set("serviceType", tmp);
//				r.set("count1", valueList.get(xAxis.indexOf(tmp)	* yAxis.size()));
//				r.set("count2", valueList.get(xAxis.indexOf(tmp)	* yAxis.size()));
//				list.add(r);
//			}
//		}
		return list;
		
	}

	/**
	 * Gets the xaxis.
	 * 获得X轴
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
	 * 获得Y轴
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
	 * 获得统计结果
	 * @param result the List
	 * @param xAxis the List
	 * @param yAxis the List
	 * 
	 * @return the List
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType), value);
			//System.out.println((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType));
		}
		return valueList;
	}

	/**
	 * Gets the cts.
	 * The cts type is ClassTreeService.
	 * @return cts
	 */
	public ClassTreeService getCts() {
		return cts;
	}

	/**
	 * Sets the cts.
	 * The cts type is ClassTreeService.
	 * @param cts 要设置的 cts
	 */
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}
