/**
 * 	@(#) TelServiceImpl.java 2008-6-26 11:01
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
import et.bo.stat.service.InquiryStatisticsForTypeService;
import et.bo.stat.service.MedicInfoByUserService;
import et.bo.stat.service.MedicInfoForNavigatorService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class MedicInfoByUserServiceImpl implements MedicInfoByUserService {
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
		JFreeChart chart = null;
		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_medicInfoByUser");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
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
		properties.put("chartTitle", "���û�����ҽ�Ʒ������");
		// ����ͼ�ε��ض�����
		String yChartName="��ѯ����";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "�û�����");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "�������");
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
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("");
		params.add("create_time");
		params.add("cust_name");
		params.add(dto.get("custName").toString());
		params.add("oper_medicinfo");
		params.add("state");
		params.add("ԭʼ,����,����,����,����");
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
				int i_1 = Integer.parseInt(m.get("ԭʼ").toString());
				int i_2 = Integer.parseInt(m.get("����").toString());
				int i_3 = Integer.parseInt(m.get("����").toString());
				int i_4 = Integer.parseInt(m.get("����").toString());
				int i_5 = Integer.parseInt(m.get("����").toString());
				int i_rowSum = i_1 + i_2 + i_3 + i_4+ i_5;
				r.set("count", i_1);
				r.set("count1", i_2);
				r.set("count2", i_3);
				r.set("count3", i_4);
				r.set("count4", i_5);
				r.set("rowCount", i_rowSum); //�л���
				//������ӻ�����
				int rArr[] = {i_1,i_2,i_3,i_4,i_5,i_rowSum};
				mu.addBits(rArr);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			list.add(r);
		}
		//�л���
		r = new DynaBeanDTO();
		r.set("name", "�ϼ�(��)");
		int colSums[] = mu.getSumArr();
		r.set("count", colSums[0]);
		r.set("count1", colSums[1]);
		r.set("count2", colSums[2]);
		r.set("count3", colSums[3]);
		r.set("count4", colSums[4]);
		r.set("rowCount", colSums[5]);
		list.add(r);
		// �Դ洢���̷��صĵĽ�����зּ�
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);		
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("name", tmp);
//				r.set("count", valueList.get(xAxis.indexOf(tmp)	* yAxis.size() ));			
//				list.add(r);
//			}
//		}
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
			String respondent = record.get("xaxis").toString();
			String processType = record.get("yaxis").toString();
			String value = record.get("value").toString();			
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType), value);
//			System.out.println((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType));
		}
		return valueList;
	}
}
