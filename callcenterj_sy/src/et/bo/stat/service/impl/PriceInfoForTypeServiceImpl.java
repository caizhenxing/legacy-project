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
import et.bo.stat.service.PriceInfoForTypeService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wang lichun
 * 
 */
public class PriceInfoForTypeServiceImpl implements PriceInfoForTypeService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;
	
	private ClassTreeService cts = new ClassTreeServiceImpl();
	
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
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
		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_productPriceStatisticsByType");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("productName"));
		
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
		properties.put("chartTitle", "ÿһ�۸������µļ۸�����ͳ��");
		// ����ͼ�ε��ض�����
//		String yChartName="count".equals(dto.get("condition"))?"�绰����":"ͨ��ʱ��";
		String yChartName="�۸�����";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "�۸�����");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "�۸�����");
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
		procSql.setProcedureName("[proc_productPriceStatisticsMultiByType]");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add((String)dto.get("productName"));
		procSql.setSqlvalues(params);
		results = procSql.execute();
		List rList = (List)results.get(0);
		DynaBeanDTO r = null;
		for(int i=0; i<rList.size(); i++)
		{
			r = new DynaBeanDTO();
			Map m = (Map)rList.get(i);
			r.set("oTime", m.get("oTime"));
			//'buyPrice'', '+ --�չ���
	        //''tradePrice'', '+ --������
	        //''retailPrice'' '+ --���ۼ�
			r.set("buyPrice", getSubStr(""+m.get("buyPrice")));
			r.set("tradePrice", getSubStr(""+m.get("tradePrice")));
			r.set("retailPrice", getSubStr(""+m.get("retailPrice")));
			list.add(r);
			
		}
		// �Դ洢���̷��صĵĽ�����зּ�
		//List<String> xAxis = getXaxis(results);
		//List<String> yAxis = getYaxis(results);
		//List<String> valueList = getValues(results, xAxis, yAxis);
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				
//				r.set("date", tmp);
//				
//				if(yAxis.contains("�չ���")){
//					r.set("count1", valueList.get(xAxis.indexOf(tmp)*yAxis.size()));
//					if(yAxis.contains("������")){
//						r.set("count2", valueList.get(xAxis.indexOf(tmp)*yAxis.size()+1));
//						if(yAxis.contains("���ۼ�"))
//							r.set("count3", valueList.get(xAxis.indexOf(tmp)*yAxis.size()+2));
//						else
//							r.set("count3", "0");
//					} else{
//						if(yAxis.contains("���ۼ�")){
//							r.set("count2", "0");
//							r.set("count3", valueList.get(xAxis.indexOf(tmp)*yAxis.size()+1));
//						} else{
//							r.set("count2", "0");
//							r.set("count3", "0");
//						}
//					}
//				} else{
//					if(yAxis.contains("������")){
//						if(yAxis.contains("���ۼ�")){
//							r.set("count1", "0");
//							r.set("count2", valueList.get(xAxis.indexOf(tmp)*yAxis.size()));
//							r.set("count3", valueList.get(xAxis.indexOf(tmp)*yAxis.size()+1));
//						} else{
//							r.set("count1", "0");
//							r.set("count2", valueList.get(xAxis.indexOf(tmp)*yAxis.size()));
//							r.set("count3", "0");
//						}
//					} else{
//						if(yAxis.contains("���ۼ�")){
//							r.set("count1", "0");
//							r.set("count2", "0");
//							r.set("count3", valueList.get(xAxis.indexOf(tmp)*yAxis.size()));
//						} else{
//							r.set("count1", "0");
//							r.set("count2", "0");
//							r.set("count3", "0");
//						}
//					}
//				}
				
	//			if(xAxis.indexOf(tmp) * yAxis.size() < valueList.size())
	//				r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
	//			else
	//				r.set("count1",	"0");
	//			
	//			if(xAxis.indexOf(tmp)
	//					* yAxis.size() + xAxis.size() < valueList.size())
	//				r.set("count2", valueList.get(xAxis.indexOf(tmp)
	//					* yAxis.size() + xAxis.size()));
	//			else
	//				r.set("count2", "0");
	//			
	//			if(xAxis.indexOf(tmp)
	//					* yAxis.size() + 2*xAxis.size() < valueList.size())
	//				r.set("count3", valueList.get(xAxis.indexOf(tmp)
	//					* yAxis.size() + 2*xAxis.size()));
	//			else
	//				r.set("count3", "0");
				
//				list.add(r);
//			}
//		}
		return list;
	}
	private String getSubStr(String num)
	{
		if(num==null)
			return null;
		else
		{
			int index = num.indexOf(".");
			if(index != -1)
			{
				int length = num.length();
				if((length-index-1)>2)
				{
					return num.substring(0,index+3);
				}
			}
			return num;
		}
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
			String label = "";
			Object o = yLabel.get("yaxis");
			if(o != null)
				label= cts.getLabelById(yLabel.get("yaxis").toString());
			
			yAxis.add(label);
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
			String processType = cts.getLabelById(record.get("yaxis").toString());
			String value = record.get("value").toString();
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
//			valueList.add(value);
		}
		for(int j=0; j<valueList.size(); j++) {
//			System.out.println("valueList["+j+"] is"+valueList.get(j));
		}
		return valueList;
	}
}
