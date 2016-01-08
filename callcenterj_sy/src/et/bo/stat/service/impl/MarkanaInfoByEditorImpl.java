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
import et.bo.stat.service.MarkanaInfoByEditorService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class MarkanaInfoByEditorImpl implements MarkanaInfoByEditorService {
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
		procSql.setProcedureName("proc_markanaInfoByEditor");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("name").toString());
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
		properties.put("chartTitle", "全部责任编辑和各编辑的产品数量");
		// 各种图形的特定属性
		String yChartName="全部责任编辑和各编辑的产品数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "责任编辑");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "责任编辑");
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
		procSql.setProcedureName("proc_markanaInfoByEditor");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("name").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		Integer nums = 0;
		if(xAxis.size()>0&&yAxis.size()>0) {
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("name", tmp);
				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				nums += Integer.valueOf(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				r.set("nums", nums);
				list.add(r);
			}
		}
		return list;
		
	}
	/**
	 * 根据DTO对象的参数值，执行统计，并将统计结果进行处理，生成符合报表格式的列表
	 * @param dto
	 * @return
	 */
	public List<DynaBeanDTO> queryUnite(IBaseDTO dto)
	{
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("");
		params.add("create_time");
		params.add("charge_editor");
		params.add(dto.get("name").toString());
		params.add("oper_markanainfo");
		params.add("dict_comment_type");
		params.add("年评,季评,月评,周评");
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
			r.set("name", m.get("col1"));
			try
			{
				int i_1 = Integer.parseInt(m.get("年评").toString());
				int i_2 = Integer.parseInt(m.get("季评").toString());
				int i_3 = Integer.parseInt(m.get("月评").toString());
				int i_4 = Integer.parseInt(m.get("周评").toString());
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
		r.set("name", "合计(例)");
		int colSums[] = mu.getSumArr();
		r.set("count", colSums[0]);
		r.set("count1", colSums[1]);
		r.set("count2", colSums[2]);
		r.set("count3", colSums[3]);
		r.set("rowCount", colSums[4]);
		list.add(r);
		return list;
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
