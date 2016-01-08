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
import et.bo.sys.common.MathUtil;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class InquiryStatisticsForTypeServiceImpl implements InquiryStatisticsForTypeService {
	private BaseDAO dao;
	private ClassTreeService cts;
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
//		procSql.setProcedureName("proc_inquiryStatisticsByType");
//		ͳ�������м��롰�������
		procSql.setProcedureName("proc_inquiryStatisticsByType_addInquiryType");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		
//		������� Ӧ��ʹ��inquiryType��Ϊ�˲��޸�commonForm(form-bean)��ʹ�����е�serviceType
		String inquiryType = (String)dto.get("serviceType");
		if(inquiryType!=null&&!"".equals(inquiryType.trim()))
			params.add(" and dict_inquiry_type = '"+inquiryType+"' ");
		else
			params.add("");
		
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
		properties.put("chartTitle", "����������µĵ������");
		// ����ͼ�ε��ض�����
		String yChartName="�������";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "�������");
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
	/**
	 * 	set @src = 'select dict_inquiry_type, dbo.uf_getDateStr(begin_time) as begin_time from oper_inquiryinfo' +
		' where begin_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
	set @mask = 'inquiryStatisticsByType'
	set @columns = 'count|dict_inquiry_type|����'
	set @groupby = 'dict_inquiry_type'
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
//		������� Ӧ��ʹ��inquiryType��Ϊ�˲��޸�commonForm(form-bean)��ʹ�����е�serviceType
		String inquiryType = dto.get("serviceType").toString();
//		��������ʽ��ʾһ��
//		if(inquiryType!=null&&!"".equals(inquiryType.trim()))
//			params.add(" and dict_inquiry_type = '"+inquiryType+"' ");
//		else
			params.add(" ");
		params.add("begin_time");
		params.add("dict_inquiry_type");
//		��������ʽ��ʾ��ϸ
		if(inquiryType!=null&&!"".equals(inquiryType.trim()))
			params.add(inquiryType);
		else
			params.add("");
		
		params.add("oper_inquiryinfo");
		params.add("state");
		params.add("����,ԭʼ,����,����,����");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		
		// �Դ洢���̷��صĵĽ�����зּ�
		List lv = (List)results.get(0);
		DynaBeanDTO r = null;
		MathUtil mu = new MathUtil();
		for(int i=0; i<lv.size(); i++){
			r = new DynaBeanDTO();
			Map m = (Map)lv.get(i);
			String col1 = (String)m.get("col1");
			try{
				if(col1.indexOf("SYS_")!=-1)
					r.set("col1", cts.getLabelById(col1));
				else
					r.set("col1", col1);
				
			}catch(Exception e){
				r.set("col1", col1);
			}
			
			try{
				int i_ds = Integer.parseInt(m.get("����").toString());
				int i_ys = Integer.parseInt(m.get("ԭʼ").toString());
				int i_fb = Integer.parseInt(m.get("����").toString());
				int i_checked = Integer.parseInt(m.get("����").toString());
				int i_back = Integer.parseInt(m.get("����").toString());
				int i_rowSum = i_ds + i_ys + i_fb + i_checked+i_back;
				r.set("����", i_ds);
				r.set("ԭʼ", i_ys);
				r.set("����", i_fb);
				r.set("����", i_checked);
				r.set("����", i_back);
				r.set("rowSum", i_rowSum); //�л���
				//������ӻ�����
				int rArr[] = {i_ds,i_ys,i_fb,i_checked,i_rowSum,i_back};
				mu.addBits(rArr);
			
			}catch(Exception e){
				e.printStackTrace();
			}
	
			list.add(r);
		}
		
		//�л���
		r = new DynaBeanDTO();
		r.set("col1", "�ϼ�(��)");
		int colSums[] = mu.getSumArr();
		r.set("����", colSums[0]);
		r.set("ԭʼ", colSums[1]);
		r.set("����", colSums[2]);
		r.set("����", colSums[3]);
		r.set("����", colSums[5]);
		r.set("rowSum", colSums[4]);
		list.add(r);
		return list;
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// ���ô洢����ȡ��ͳ�ƽ��
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		procSql.setProcedureName("proc_inquiryStatisticsByType");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// �Դ洢���̷��صĵĽ�����зּ�
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);		
//		List<String> valueList = getValues(results, xAxis, yAxis);
////		System.out.println("xAxis size : "+xAxis.size());
////		System.out.println("yAxis size : "+yAxis.size());
////		System.out.println("valueList size : "+valueList.size());
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				
//				if(tmp!=null){
//					r.set("serviceType", cts.getLabelById(tmp));
//				}
//				r
//						.set("count", valueList.get(xAxis.indexOf(tmp)
//								* yAxis.size() ));
//				
//				list.add(r);
//			}
//		}
//		
//		return list;
		
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
			System.out.println((xAxis.indexOf(respondent)) * yAxis.size()+ yAxis.indexOf(processType));
		}
		return valueList;
	}

	/**
	 * @return cts
	 */
	public ClassTreeService getCts() {
		return cts;
	}

	/**
	 * @param cts Ҫ���õ� cts
	 */
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	/**
	 * ��ȡ ������� �б�
	 * @return
	 */
	public List getInquiryType(){
		return cts.getLabelVaList("inquiryTypes", false);
	}
	
}
