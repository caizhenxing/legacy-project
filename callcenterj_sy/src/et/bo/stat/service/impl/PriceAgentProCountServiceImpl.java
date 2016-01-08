/**
 * 	@(#) TelServiceImpl.java 2008-4-11 下午01:09:59
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
import et.bo.stat.service.PriceAgentProCountService;
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
 * @author dengwei
 * 
 */
public class PriceAgentProCountServiceImpl implements PriceAgentProCountService {
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
		
		procSql.setProcedureName("proc_productPriceStatisticsBySeat_multi");//proc_generalCaseInfoStatisticsBySeat
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("agentId"));
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
		properties.put("chartTitle", "每一座席员受理的产品供求数量统计");
		// 各种图形的特定属性
		String xChartName="工号";
		String yChartName="受理数量（单位：个）";
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
		params.add("state");
		params.add("原始,待审,驳回,已审,发布");
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
				int i_1 = Integer.parseInt(m.get("原始").toString());
				int i_2 = Integer.parseInt(m.get("待审").toString());
				int i_3 = Integer.parseInt(m.get("驳回").toString());
				int i_4 = Integer.parseInt(m.get("已审").toString());
				int i_5 = Integer.parseInt(m.get("发布").toString());
				int i_rowSum = i_1 + i_2 + i_3 + i_4+ i_5;
				r.set("count", i_1);
				r.set("count1", i_2);
				r.set("count2", i_3);
				r.set("count3", i_4);
				r.set("count4", i_5);
				r.set("rowCount", i_rowSum); //行汇总
				//将行相加汇总列
				int rArr[] = {i_1,i_2,i_3,i_4,i_5,i_rowSum};
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
		r.set("count4", colSums[4]);
		r.set("rowCount", colSums[5]);
		list.add(r);
//		procSql.setProcedureName("proc_productPriceStatisticsBySeat_multi");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		//System.out.println(dto.get("beginTime").toString()+"##"+dto.get("endTime").toString());
//		params.add((String)dto.get("agentId"));
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		List viewList = (List)results.get(0);
//		int size = viewList.size();
//		int num1 = 0;
//		int num2 = 0;
//		int num3 = 0;
//		int num4 = 0;
//		int num5 = 0;
//		for(int i=0; i<size; i++)
//		{
//			DynaBeanDTO r = new DynaBeanDTO();
//			//System.out.println(viewList.get(i).getClass());
//			
//			Map m = (Map)viewList.get(i);
//			
//			r.set("id", m.get("id"));
//			r.set("case_date", m.get("case_date"));
//			r.set("yuanshi", m.get("yuanshi"));
//			r.set("daishen", m.get("daishen"));
//			r.set("bohui", m.get("bohui"));
//			r.set("yishen", m.get("yishen"));
//			r.set("fabu", m.get("fabu"));
//			
//			int sum1 = Integer.parseInt(m.get("yuanshi").toString());
//			int sum2 = Integer.parseInt(m.get("daishen").toString());
//			int sum3 = Integer.parseInt(m.get("bohui").toString());
//			int sum4 = Integer.parseInt(m.get("yishen").toString());
//			int sum5 = Integer.parseInt(m.get("fabu").toString());
//			num1 += sum1;
//			num2 += sum2;
//			num3 += sum3;
//			num4 += sum4;
//			num5 += sum5;
//			r.set("num1", num1);
//			r.set("num2", num2);
//			r.set("num3", num3);
//			r.set("num4", num4);
//			r.set("num5", num5);
//			list.add(r);
//		}
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// 处理X坐标的label数组
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			try
			{
				//####################
				xAxis.add(xLabel.get("xaxis").toString());
			}
			catch(Exception e)
			{
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
