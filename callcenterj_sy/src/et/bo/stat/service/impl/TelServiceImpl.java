/**
 * 	@(#) TelServiceImpl.java 2008-4-11 ����01:09:59
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.TelService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author liangyunfeng modifier wangwenquan
 * 
 */
public class TelServiceImpl implements TelService {
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
		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_telephone");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("condition").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		// ����JFreeChart���������
		Map<String, Object> properties = new HashMap<String, Object>();
		String chartType = dto.get("chartType").toString();
		if ("on".equals(dto.get("is3d"))) {
			chartType += "3d";
		}
		// ����ͼ�ε�ͨ������
		properties.put("chartType", chartType);
		properties.put("chartTitle", "������ͳ��");
		// ����ͼ�ε��ض�����
		String yChartName = "count".equals(dto.get("condition")) ? "�绰����" : "ͨ��ʱ��";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "��ϯԱ");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "��ϯԱ");
			properties.put("yChartName", yChartName);
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
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_telephone");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("condition").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		if (xAxis.size() > 0 && yAxis.size() > 0) {

			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				r.set("name", tmp);
				r.set("type1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				r.set("type2", valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1));
				r.set("type3", new Integer(Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size())) + Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1))));
				
				/*��������*/
				int sum1 = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				int sum2 = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size() + 1));
				int sum3 = sum1+sum2;
				
				num1 += sum1;
				num2 += sum2;
				num3 += sum3;
				r.set("num1", num1);
				r.set("num2", num2);
				r.set("num3", num3);
				
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

	private List<String> getValues(List result, List<String> xAxis, List<String> yAxis) {
		// ����X��Y�����Label������������
		List<String> valueList = new ArrayList<String>();
		//
		for (int i = 0; i < xAxis.size() * yAxis.size(); i++) {
			valueList.add("0");
		}
		for (Iterator i = ((List) result.get(2)).iterator(); i.hasNext();) {
			Map record = (Map) i.next();

			String respondent = record.get("respondent").toString();
			String processType = record.get("teltype").toString();
			String value = record.get("statValue").toString();
			// ###
			// System.out.println(valueList.size()+":"+(xAxis.indexOf(respondent)) * yAxis.size()+yAxis.indexOf(processType));
			// System.out.println("respondent
			// is:"+respondent+":(xAxis.indexOf(respondent)):"+(xAxis.indexOf(respondent))+"#yAxis.size():"+yAxis.size()+"#yAxis.indexOf(processType):"+yAxis.indexOf(processType)+":processType:"+processType);
			// System.out.println("######_");
			// ###
			if ((xAxis.indexOf(respondent)) * yAxis.size() + yAxis.indexOf(processType) >= 0) {
				valueList.set((xAxis.indexOf(respondent)) * yAxis.size() + yAxis.indexOf(processType), value);
			}

		}
		return valueList;
	}

}
