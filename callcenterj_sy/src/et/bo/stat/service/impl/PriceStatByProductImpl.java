/**
 * 	@(#) TelServiceImpl.java 2008-4-11 ����01:09:59
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
import et.bo.stat.service.PriceStatByProductService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author chen gang
 * 
 */
public class PriceStatByProductImpl implements PriceStatByProductService {
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

		procSql.setProcedureName("proc_productPriceStatisticsByProduct_old");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("dictProductType1"));
		params.add((String)dto.get("dictProductType2"));
//		String type = dto.get("condition").toString();
//		if("class1".equals(type))
//			type = "����";
//		else if("class2".equals(type))
//			type = "С��";
//		else if("class3".equals(type))
//			type = "����";
//		
//		params.add(type);
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
		properties.put("chartTitle", "����Ʒ�ļ۸�����");
		// ����ͼ�ε��ض�����
//		String yChartName="count".equals(dto.get("condition"))?"�绰����":"ͨ��ʱ��";
		String yChartName="�۸�����";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "��Ʒ����");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "��Ʒ����");
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
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String type1 = (String)dto.get("dictProductType1") != null ?(String)dto.get("dictProductType1"):"";
		String type2 = (String)dto.get("dictProductType2") != null ?(String)dto.get("dictProductType2"):"";
		String andSql = "";
		if(!"".equals(type1))
		{
			if(!"".equals(type2))
			{
				andSql = " and dict_product_type1 = '"+type1+"' and dict_product_type2 = '"+type2+"'";
			}
			else
			{
				andSql = " and dict_product_type1 = '"+type1+"'";
			}
		}
		else
		{
			if(!"".equals(type2))
			{
				andSql = " and dict_product_type2 = '"+type2+"'";
			}
		}
		System.out.println(andSql);
		params.add(andSql);
		params.add("oper_time");
		params.add("product_name");
		params.add((String)dto.get("productName"));
		params.add("oper_priceinfo");
		params.add("dict_price_type");
		params.add("SYS_TREE_0000000627,SYS_TREE_0000000628,SYS_TREE_0000000629");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		List l = (List)results.get(0);
		DynaBeanDTO r = null;
		MathUtil mu = new MathUtil();
		for(int i=0; i<l.size(); i++)
		{
			Map mv = (Map)l.get(i);
			r = new DynaBeanDTO();
			r.set("col1", mv.get("col1"));
			r.set("SYS_TREE_0000000627", mv.get("SYS_TREE_0000000627")); //�չ���
			r.set("SYS_TREE_0000000628", mv.get("SYS_TREE_0000000628")); //������
			r.set("SYS_TREE_0000000629", mv.get("SYS_TREE_0000000629")); //���ۼ�
			int i_1 = Integer.parseInt(mv.get("SYS_TREE_0000000627").toString());
			int i_2 = Integer.parseInt(mv.get("SYS_TREE_0000000628").toString());
			int i_3= Integer.parseInt(mv.get("SYS_TREE_0000000629").toString());
			int i_rowSum = i_1 + i_2 + i_3;
			r.set("rowCount", i_rowSum);
			int rArr[] = {i_1,i_2,i_3,i_rowSum};
			mu.addBits(rArr);
			list.add(r);
		}
		r = new DynaBeanDTO();
		r.set("col1", "�ϼ�(��)");
		int colSums[] = mu.getSumArr();
		r.set("SYS_TREE_0000000627", colSums[0]);
		r.set("SYS_TREE_0000000628", colSums[1]);
		r.set("SYS_TREE_0000000629", colSums[2]);
		r.set("rowCount", colSums[3]);
		list.add(r);
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// ����X�����label����
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			
			Object o = xLabel.get("xaxis");
			if(o != null)
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
