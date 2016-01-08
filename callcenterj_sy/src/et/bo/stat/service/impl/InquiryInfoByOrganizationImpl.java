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
import et.bo.stat.service.InquiryInfoByOrganizationService;
import et.bo.sys.common.MathUtil;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class InquiryInfoByOrganizationImpl implements InquiryInfoByOrganizationService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;
	
	private ClassTreeService cts = new ClassTreeServiceImpl();

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
//		procSql.setProcedureName("proc_inquiryStatisticsByOrganiztion");
//		统计条件中加入“发起机构”
		procSql.setProcedureName("proc_inquiryStatisticsByOrganiztion_addOrganiztion");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
//		params.add(dto.get("name").toString());
		
//		发起机构 应该使用organiztion但为了不修改commonForm(form-bean)而使用已有的serviceType
		String organiztion = (String)dto.get("serviceType");
		if(organiztion!=null&&!"".equals(organiztion.trim()))
			params.add(" and organiztion = '"+organiztion+"' ");
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
		properties.put("chartTitle", "各机构发起的调查次数");
		// 各种图形的特定属性
		String yChartName="各机构发起的调查次数";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "调查次数");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "调查次数");
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
	 * 	set @src = 'select organiztion, dbo.uf_getDateStr(begin_time) as begin_time from oper_inquiryinfo' +
		' where begin_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
	set @mask = 'organiztionStatisticsByType'
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
//		发起机构 应该使用organiztion但为了不修改commonForm(form-bean)而使用已有的serviceType
		String organiztion = dto.get("serviceType").toString();
//		以正常型式显示一行
//		if(organiztion!=null&&!"".equals(organiztion.trim()))
//			params.add(" and organiztion = '"+organiztion+"' ");
//		else
			params.add(" ");
		
		params.add("begin_time");
		params.add("organiztion");
//		以日期型式显示详细
		if(organiztion!=null&&!"".equals(organiztion.trim()))
			params.add(organiztion);
		else		
			params.add("");
		params.add("oper_inquiryinfo");
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
//		procSql.setProcedureName("proc_inquiryStatisticsByOrganiztion");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
////		params.add(dto.get("name").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// 对存储过程返回的的结果进行分拣
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		int j = 0;
//		if(xAxis.size()>0&&yAxis.size()>0)
//		{
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				
//	//			if(!"".equals(dto.get("name"))) {
//	//				r.set("name", dto.get("name"));
//	//				
//	//				r.set("count", cts.getLabelByNickName(tmp));
//	//				r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//	//			} else{
//	//				r.set("name", tmp);
//	//				r.set("count", cts.getLabelByNickName(yAxis.get(j)));
//	//				r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//	//			}
//				
//				r.set("name", tmp);
//				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
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
			
			try {
				if(xLabel!=null&&xLabel.get("xaxis")!=null){
					xAxis.add(xLabel.get("xaxis").toString());
				}
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
			if(yLabel!=null){
				yAxis.add(yLabel.get("yaxis").toString());
			}
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
}
