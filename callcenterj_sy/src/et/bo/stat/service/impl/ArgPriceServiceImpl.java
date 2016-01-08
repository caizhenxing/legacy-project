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
import et.bo.stat.service.ArgPriceService;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author dengwei
 * 
 */
public class ArgPriceServiceImpl implements ArgPriceService {
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
		
		procSql.setProcedureName("proc_PRICESTATISTICS");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("custAddr").toString());//产品
		StringBuffer sb=new StringBuffer();
		sb.append(dto.get("dictProductType1").toString());
		sb.append(dto.get("dict_product_type2_span").toString());
		sb.append(dto.get("product_name_span").toString());
		params.add(sb.toString());//产地
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
		properties.put("chartTitle", "农产品价格库统计");
		// 各种图形的特定属性
		String yChartName="均价 最高价 最低价";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "时间");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "时间");
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
		StatDateStr.setBeginEndTime(dto);
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// 调用存储过程取得统计结果
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		procSql.setProcedureName("proc_PRICESTATISTICS");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("custAddr").toString());//产品
		StringBuffer sb=new StringBuffer();
		sb.append(dto.get("dictProductType1").toString());
		sb.append(dto.get("dict_product_type2_span").toString());
		sb.append(dto.get("product_name_span").toString());
		params.add(sb.toString());//产地
		params.add("");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		
//		测试代码
		/*procSql.setProcedureName("proc_PRICESTATISTICS");
		params.add("2002-01-01");
		params.add("2012-01-01");
		params.add("家畜牛类牛肉");
		params.add("鞍山");
		params.add("");
		procSql.setSqlvalues(params);*/
		
		// 对存储过程返回的的结果进行分拣
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		if(xAxis.size()>0&&yAxis.size()>0)
		{
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
//				System.out.println("tmp: "+tmp);
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("name", tmp);
				r.set("type1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				System.out.println("type1: "+r.get("type1"));
				r
						.set("type2", valueList.get(xAxis.indexOf(tmp)
								* yAxis.size() + 1));
//				System.out.println("type2: "+r.get("type2"));
				r.set("type3", new Integer(Integer.parseInt(valueList.get(xAxis
						.indexOf(tmp)
						* yAxis.size()))
						+ Integer.parseInt(valueList.get(xAxis.indexOf(tmp)
								* yAxis.size() + 1))));
//				System.out.println("type3: "+r.get("type3"));
				list.add(r);
			}
		}
//		System.out.println("list.size: "+list.size());
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
