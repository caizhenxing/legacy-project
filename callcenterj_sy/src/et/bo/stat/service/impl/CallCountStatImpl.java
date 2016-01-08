/*
 * @(#)PriceService.java	 2008-04-14
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.stat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.CallCountStatService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author nie
 */
public class CallCountStatImpl implements CallCountStatService {
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

	public JFreeChart statistic(String chartType, String dateType) {
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy��MM��dd��");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy��MM��");
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy��");
		JFreeChart chart = null;
		// ����������������ô洢����
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
//		String dateType = dto.get("dateType").toString();
		if("day".equals(dateType))
			procSql.setProcedureName("proc_statisticsDay");
		else if("month".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonth");
		else if("year".equals(dateType))
			procSql.setProcedureName("proc_statisticsYear");
		
		else if("colday".equals(dateType))
			procSql.setProcedureName("proc_statisticsDayByType");
		else if("colmonth".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonthByType");
		else if("colyear".equals(dateType))
			procSql.setProcedureName("proc_statisticsYearByType");
		
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		// ����JFreeChart���������
		Map<String, Object> properties = new HashMap<String, Object>();
//		String chartType = dto.get("chartType").toString();
//		if ("on".equals(dto.get("is3d"))) {
			chartType += "3d";
//		}
		// ����ͼ�ε�ͨ������
		properties.put("chartType", chartType);
		
		if(dateType.equals("day"))
			properties.put("chartTitle", "ʱ����ѯ����ͳ��-- "+sdfMonth.format(TimeUtil.getNowTime())+" ÿ����ѯ��");
		if(dateType.equals("month"))
			properties.put("chartTitle", "ʱ����ѯ����ͳ��-- "+sdfYear.format(TimeUtil.getNowTime())+" ÿ����ѯ��");
		if(dateType.equals("year"))
			properties.put("chartTitle", "ʱ����ѯ����ͳ��-- "+"������ѯ��");
		if(dateType.equals("colday"))
			properties.put("chartTitle", "��Ŀ��ѯ����ͳ��-- "+sdfDay.format(TimeUtil.getNowTime())+" ��Ŀ��ѯ��");
		if(dateType.equals("colmonth"))
			properties.put("chartTitle", "��Ŀ��ѯ����ͳ��-- "+sdfMonth.format(TimeUtil.getNowTime())+" ��Ŀ��ѯ��");
		if(dateType.equals("colyear"))
			properties.put("chartTitle", "��Ŀ��ѯ����ͳ��-- "+sdfYear.format(TimeUtil.getNowTime())+" ��Ŀ��ѯ��");
		
		// ����ͼ�ε��ض�����
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			
			if("day".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  ����ѯ��");
			else if("month".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  ����ѯ��");
			else if("year".equals(dateType))
				properties.put("xChartName", "������ѯ��");
			
			else if("colday".equals(dateType))
				properties.put("xChartName", sdfDay.format(TimeUtil.getNowTime())+"  ��Ŀ��ѯ��");
			else if("colmonth".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  ��Ŀ��ѯ��");
			else if("colyear".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  ��Ŀ��ѯ��");

			properties.put("yChartName", "��ѯ����");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
			
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			
			if("day".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime()));
			else if("month".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime()));
			else if("year".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime()));
			
			else if("colday".equals(dateType))
				properties.put("xChartName", sdfDay.format(TimeUtil.getNowTime())+"  ��ѯ��Ŀ����");
			else if("colmonth".equals(dateType))
				properties.put("xChartName", sdfMonth.format(TimeUtil.getNowTime())+"  ��ѯ��Ŀ����");
			else if("colyear".equals(dateType))
				properties.put("xChartName", sdfYear.format(TimeUtil.getNowTime())+"  ��ѯ��Ŀ����");
	
			properties.put("yChartName", "��ѯ����");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.PIE) >= 0) {
			properties.put("pieTextValues", xAxis);
		}
		// ����JFreeChart����
		chart = chartService.createJFreeChart(valueList, properties);
		return chart;
	}

	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		String dateType = dto.get("dateType").toString();
		if("day".equals(dateType))
			procSql.setProcedureName("proc_statisticsDay");
		else if("month".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonth");
		else if("year".equals(dateType))
			procSql.setProcedureName("proc_statisticsYear");
		
		else if("colday".equals(dateType))
			procSql.setProcedureName("proc_statisticsDayByType");
		else if("colmonth".equals(dateType))
			procSql.setProcedureName("proc_statisticsMonthByType");
		else if("colyear".equals(dateType))
			procSql.setProcedureName("proc_statisticsYearByType");
		
//		procSql.setProcedureName("proc_priceinfo");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		if(xAxis.size()>0&&yAxis.size()>0)
		{
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("date", tmp);
				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
		
				list.add(r);
			}
		}
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// ����X�����label����
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get("xaxis").toString());
		}
		return xAxis;
	}

	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// ����Y�����label����
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("yaxis").toString());
		}
		return yAxis;
	}

	private List<String> getValues(List result, List<String> xAxis,
			List<String> yAxis) {
		// ����X��Y�����Label������������
		List<String> valueList = new ArrayList<String>();
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();
			String X = record.get("xaxis").toString();
			String Y = record.get("yaxis").toString();
			String value = record.get("value").toString();
			valueList.set((xAxis.indexOf(X)) * yAxis.size()
					+ yAxis.indexOf(Y), value);
		}
		return valueList;
	}
}
