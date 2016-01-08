/*
 * @(#)MoneyService.java	 2008-05-12
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.stat.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.jfree.chart.JFreeChart;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.MoneyService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author nie
 */
public class MoneyServiceImpl implements MoneyService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;
	private BasicDataSource bds = null;
	public BasicDataSource getBds() {
		return bds;
	}
	public void setBds(BasicDataSource bds) {
		this.bds = bds;
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

	public JFreeChart statistic(IBaseDTO dto) {
		
		JFreeChart chart = null;
		// 根据输入的条件调用存储过程
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_money");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("price").toString());
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
		properties.put("chartTitle", "使用情况统计");
		// 各种图形的特定属性
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "栏目分类");
			properties.put("yChartName", "使用情况");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "栏目分类");
			properties.put("yChartName", "使用情况");
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
		StatDateStr.setBeginEndTime(dto);
		String tel = null;
		int num = 0;
		float money = 0;
		float price = 0;
		int num1 = 0;
		float curLen; //数据库　touch_keeptime 是毫秒　用它变秒　touch_keeptime/1000
		float numLen = 0;
		float num2 = 0;
		if(dto.get("price").equals("")){
			price = (float)0.2;
		}else{
			price = Float.parseFloat(dto.get("price").toString());
		}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String outTelNum = dto.get("outTelNum")==null?"":dto.get("outTelNum").toString().trim();
			rs = stmt.executeQuery("select count(phone_num), phone_num,sum(CONVERT(integer,touch_keeptime)) from cc_talk where respondent_type = 'OUTINGAGENT' and touch_begintime >= '"+dto.get("beginTime").toString()+"' and touch_begintime <= '"+dto.get("endTime").toString()+"' and phone_num like '%"+outTelNum+"%' group by phone_num");
			while(rs.next()){
				DynaBeanDTO r = new DynaBeanDTO();
				curLen = rs.getFloat(3)/1000;
				tel = rs.getString(2); //获取外呼电话号码
				num = rs.getInt(1); //获取通话次数
				money = curLen*price;  //获取钱数
				
				r.set("tel", tel);
				r.set("num", num);
				r.set("money", money);
				r.set("sum_touchKeeptime", curLen);
				num1 += num;
				num2 += money;
				numLen += curLen;
				r.set("num1", num1);
				r.set("num2", num2);
				r.set("numLen", numLen);
				list.add(r);
				
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return list;
		// TODO Auto-generated method stub
		/*List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_money");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("price").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);

		for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
			String tmp = i.next();
			DynaBeanDTO r = new DynaBeanDTO();
			r.set("X", tmp);
			r.set("Sum1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
	
			list.add(r);
		}*/
//		上边都是没用的，太烦人了
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		DynaBeanDTO r = new DynaBeanDTO();
//		float f = getTimeLength(dto.get("beginTime").toString(),dto.get("endTime").toString());
//		float f2 = f*(Float.valueOf(dto.get("price").toString()));
//		r.set("a", f);
//		r.set("b", f2);
//		list.add(r);
//		return list;
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
			String X = record.get("X").toString();
			String Y = record.get("Y").toString();
			String value = record.get("Sum1").toString();
			valueList.set((xAxis.indexOf(X)) * yAxis.size()
					+ yAxis.indexOf(Y), value);
		}
		return valueList;
	}
	/**
	 * 执行SQL语句
	 * @param sql
	 * @return i 返回int
	 */
	public float getTimeLength(String begintime, String endtime) {
		
		float f = 0;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select sum(Convert(int,touch_keeptime)) from cc_talk where post_type='trunk' and process_type='1' and touch_endtime >= '"+begintime+"' and touch_endtime <= '"+endtime+"'");
			if(rs.next()){
				f = rs.getFloat(1);
				f = f/1000/60;
			}

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return f;

	}
}
