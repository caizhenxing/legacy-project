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
import et.bo.stat.service.MedicInfoByStateService;
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
public class MedicInfoByStateImpl implements MedicInfoByStateService {
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
		procSql.setProcedureName("proc_medicInfoByState");
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
		properties.put("chartTitle", "全部座席员和每一座席员受理的各审核状态下的医疗信息数量");
		// 各种图形的特定属性
		String yChartName="各审核状态下的医疗信息数量";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "服务次数");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "服务次数");
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
		procSql.setProcedureName("proc_medicInfoByState");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("name").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		int j = 0;
		if(xAxis.size()>0&&yAxis.size()>0) {
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				
				if(!"".equals(dto.get("name"))) {
					r.set("name", dto.get("name"));
					try
					{
						r.set("count", cts.getLabelByNickName(tmp));
					}
					catch(Exception e)
					{
						System.out.println("err MedicInfoByStateImpl base_tree 里无 nickName="+tmp+"的字段");
						//e.printStackTrace();
					}
					r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				} else{
					r.set("name", tmp);
					try {
						r.set("count", cts.getLabelByNickName(yAxis.get(j)));
					} catch (RuntimeException e) {
						r.set("count","0");
					}
					r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				}
				
	//			r.set("name", tmp);
	//			r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				list.add(r);
			}
		}
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
