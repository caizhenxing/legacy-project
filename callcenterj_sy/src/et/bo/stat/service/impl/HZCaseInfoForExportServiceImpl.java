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
import et.bo.stat.service.HZCaseInfoFroExportService;
import et.bo.sys.common.MathUtil;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author wanglichun
 * 
 */
public class HZCaseInfoForExportServiceImpl implements HZCaseInfoFroExportService {
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
		procSql.setProcedureName("proc_HZCaseInfoStatisticsByExpert");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String caseExpert=dto.get("caseExpert").toString();
		if(caseExpert!=null&&!caseExpert.equals("")){
			params.add(caseExpert);
		}
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
		properties.put("chartTitle", "ר��������ͳ��");
		// ����ͼ�ε��ض�����
		String yChartName="������";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "����ר��");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "����ר��");
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
	/*
	'select case_expert, dbo.uf_getDateStr(case_time) as case_time from oper_caseinfo
		where dict_case_type=''HZCase'' 
			and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime) '
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='HZCase'";


		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("caseExpert").toString();
		params.add("case_expert");
		params.add(temp);
		params.add("oper_caseinfo");
		params.add("state");
		params.add("����,ԭʼ,����,����,����");
		procSql.setSqlvalues(params);
		results = procSql.execute();
		
		// �Դ洢���̷��صĵĽ�����зּ�
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
			
			}
			catch(Exception e)
			{
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
//		procSql.setProcedureName("proc_HZCaseInfoStatisticsByExpert");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		String caseExpert=dto.get("caseExpert").toString();
//		if(caseExpert.equals("0")){
//			caseExpert="";
//		}
//		if(caseExpert!=null){
//			params.add(caseExpert);
//		}
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// �Դ洢���̷��صĵĽ�����зּ�
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		Map map=getValue(results, xAxis, yAxis);	
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = yAxis.iterator(); i.hasNext();) {
//				String export = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("export", export);
//				r.set("count", map.get(export));				
//				list.add(r);
//			}
//		}
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
	
	private List getValue1(List result, List<String> xAxis,
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

	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// ����Y�����label����
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("yaxis").toString());
		}
		return yAxis;
	}
	
	private Map getValue(List result, List<String> xAxis,
			List<String> yAxis) {
		Map re=new HashMap();
		for(int i=0;i<yAxis.size();i++){
			String export=yAxis.get(i);
			
			List list=(List)result.get(2);
			int num=0;
			for(int k=0;k<list.size();k++){
				Map record=(Map)list.get(k);
				
				if(export.equals(record.get("yaxis").toString())){
					num+=Integer.parseInt(record.get("value").toString());
				}
			}
			re.put(export, num);
		}
		return re;
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
		}
		return valueList;
	}
}
