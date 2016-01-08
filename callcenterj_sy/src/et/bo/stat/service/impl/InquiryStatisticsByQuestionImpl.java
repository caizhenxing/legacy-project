/**
 * 	@(#) TelServiceImpl.java 2008-6-26 11:01
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.stat.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.InquiryStatisticsByQuestionService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author dengwei
 * 
 */
public class InquiryStatisticsByQuestionImpl implements InquiryStatisticsByQuestionService {
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

		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_inquiryStatisticsByQuestion");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String question = dto.get("question")==null?"":dto.get("question").toString().trim();
		params.add(question);
//		params.add(dto.get("name").toString());
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
		properties.put("chartTitle", "ÿһ��������Ļش�����");
		// ����ͼ�ε��ض�����
		String yChartName="�ش�����";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "��������");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "��������");
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
	 	set @src = 'select question, dbo.uf_getDateStr(create_time) as create_time from oper_inquiry_result' +
		' where create_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		 TODO Auto-generated method stub
		StatDateStr.setBeginEndTime(dto);
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// ���ô洢����ȡ��ͳ�ƽ��
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		String agent = (String)dto.get("agentId");
//		String andSql = " ";
//		if(agent!=null&&!"".equals(agent.trim()))
//		{
//			andSql += " and rid = '"+agent+"' ";
//		}
//
//		procSql.setProcedureName("proc_typeChart");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add(andSql);
//		params.add("create_time");
//		params.add("question");
//		params.add("");
//		params.add("oper_inquiry_result");
//		params.add("question_type");
//		params.add("��ѡ,��ѡ,�ʴ�");
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		
//		// �Դ洢���̷��صĵĽ�����зּ�
//		List lv = (List)results.get(0);
//		DynaBeanDTO r = null;
//		MathUtil mu = new MathUtil();
//		for(int i=0; i<lv.size(); i++)
//		{
//			r = new DynaBeanDTO();
//			Map m = (Map)lv.get(i);
//			r.set("col1", m.get("col1"));
//			try
//			{
//				int i_ds = Integer.parseInt(m.get("����").toString());
//				int i_ys = Integer.parseInt(m.get("ԭʼ").toString());
//				int i_fb = Integer.parseInt(m.get("����").toString());
//				int i_checked = Integer.parseInt(m.get("����").toString());
//				int i_rowSum = i_ds + i_ys + i_fb + i_checked;
//				r.set("����", i_ds);
//				r.set("ԭʼ", i_ys);
//				r.set("����", i_fb);
//				r.set("����", i_checked);
//				r.set("rowSum", i_rowSum); //�л���
//				//������ӻ�����
//				int rArr[] = {i_ds,i_ys,i_fb,i_checked,i_rowSum};
//				mu.addBits(rArr);
//			
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//	
//			list.add(r);
//		}
//		
//		//�л���
//		r = new DynaBeanDTO();
//		r.set("col1", "�ϼ�(��)");
//		int colSums[] = mu.getSumArr();
//		r.set("����", colSums[0]);
//		r.set("ԭʼ", colSums[1]);
//		r.set("����", colSums[2]);
//		r.set("����", colSums[3]);
//		r.set("rowSum", colSums[4]);
//		list.add(r);
//		return list;
		
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		String beginTime = dto.get("beginTime").toString();
		String endTime = dto.get("endTime").toString();
		String question = dto.get("question")==null?"":dto.get("question").toString().trim();

		String querySql = " select question, count(*) sumNum from oper_inquiry_result " +
			" where CONVERT(varchar, create_time, 120 ) between '"+beginTime+"'  and '"+endTime + "' and question like '%"+question+"%' and Actor is not null group by question ";
		// TODO Auto-generated method stub
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		DynaBeanDTO r = null;
		int sumCol = 0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				
				r = new DynaBeanDTO();
				r.set("question", rs.getString("question"));
				sumCol += rs.getInt("sumNum");
				r.set("sumNum", rs.getInt("sumNum"));
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		r = new DynaBeanDTO();
		r.set("question", "�ϼ�");
		r.set("sumNum", sumCol);
		list.add(r);
		return list;
		
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// ����X�����label����
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
		// ����Y�����label����
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
		// ����X��Y�����Label������������
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
