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
import et.bo.stat.service.SadStatisticsByTypeService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author wanglichun
 * 
 */
public class SadStatisticsByTypeServiceImpl implements SadStatisticsByTypeService {
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
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_sadStatisticsByType");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("agentId"));
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		/**
		 * 把TreeType 转换成 供求类型
		 */
		List<String> xAxis1=new ArrayList<String>();
		for(int i=0;i<xAxis.size();i++){
			xAxis1.add(cts.getLabelById(xAxis.get(i)));
		}
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
		properties.put("chartTitle", "各供求类型下的产品供求数量");
		// 各种图形的特定属性
		String yChartName="供求条数";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "供求类型");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis1);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "供求类型");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis1);
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
	 * 获得受理工号列表
	 */
	public List userQuery(String hql) {
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os =dao.findEntity(mq);
		List<SysUser> list=new ArrayList<SysUser>();
		for(int i=0; i<os.length; i++)
		{
			SysUser u = (SysUser)os[i];
			list.add(u);
		}
		return list;
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
		params.add(" and sad_rid is not null");
		params.add("sad_time");
		params.add("sad_rid");
		params.add((String)dto.get("agentId"));
		params.add("oper_sadinfo");
		params.add("dict_sad_type");
		params.add("SYS_TREE_0000000622,SYS_TREE_0000000623,SYS_TREE_0000000624,SYS_TREE_0000000625");
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
				int i_1 = Integer.parseInt(m.get("SYS_TREE_0000000622").toString());
				int i_2 = Integer.parseInt(m.get("SYS_TREE_0000000623").toString());
				int i_3 = Integer.parseInt(m.get("SYS_TREE_0000000624").toString());
				int i_4 = Integer.parseInt(m.get("SYS_TREE_0000000625").toString());
				int i_rowSum = i_1 + i_2 + i_3 + i_4;
				r.set("count", i_1);
				r.set("count1", i_2);
				r.set("count2", i_3);
				r.set("count3", i_4);
				r.set("rowCount", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_1,i_2,i_3,i_4,i_rowSum};
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
		r.set("name", "合计(条)");
		int colSums[] = mu.getSumArr();
		r.set("count", colSums[0]);
		r.set("count1", colSums[1]);
		r.set("count2", colSums[2]);
		r.set("count3", colSums[3]);
		r.set("rowCount", colSums[4]);
		list.add(r);
//		procSql.setProcedureName("proc_sadStatisticsByType");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add((String)dto.get("agentId"));
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
//				if(tmp!=null){		
//					r.set("serviceType", cts.getLabelById(tmp));
//				}
//				r.set("count", valueList.get(xAxis.indexOf(tmp)	* yAxis.size() ));
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
//			System.out.println((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType));
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
}
