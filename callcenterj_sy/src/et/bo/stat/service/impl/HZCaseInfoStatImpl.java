/**
 * 	@(#) TelServiceImpl.java 2008-4-11 ����01:09:59
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
import et.bo.stat.service.HZCaseInfoStatService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author chen gang
 * 
 */
public class HZCaseInfoStatImpl implements HZCaseInfoStatService {
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
//		procSql.setProcedureName("proc_HZCaseInfoStatisticsByProperty");
//		ͳ�������м��롰��ϯ���š�
		procSql.setProcedureName("proc_HZCaseInfoStatisticsByProperty_addAgentNum");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		
		String type = dto.get("condition").toString();
		if("big".equals(type))
			type = "����";
		else if("small".equals(type))
			type = "С��";
		
		params.add(type);
		String caseAttr3 = dto.get("caseAttr3").toString();
		if (!caseAttr3.equals("")) {
			params.add(caseAttr3);
		}
		
		String agentNum = (String)dto.get("agentNum");
		if(agentNum!=null&&!"".equals(agentNum.trim())){
			params.add(" and case_rid = '"+agentNum+"' ");
		}else 
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
		properties.put("chartTitle", "ÿһ���������µİ�������ͳ��");
		// ����ͼ�ε��ض�����
//		String yChartName="count".equals(dto.get("condition"))?"�绰����":"ͨ��ʱ��";
		String yChartName="��������";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "�������");
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
	 		--���Դ���ݵ����
		if @type='����'
			set @src = 'select case_attr1 as case_attr, case_time from oper_caseinfo
				where dict_case_type=''HZCase''
				and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
		if @type='С��'
			set @src = 'select case_attr2 as case_attr, case_time from oper_caseinfo
				where dict_case_type=''HZCase''
				and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
		if @type='����'
			set @src = 'select case_attr3 as case_attr, case_time from oper_caseinfo
				where dict_case_type=''HZCase''
				and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)'
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='HZCase' ";
		String temp=dto.get("condition").toString();
		String type = "";
		if("big".equals(temp))	type = "case_attr1";
		if("small".equals(temp))	type = "case_attr2";
		String caseAttr3 = dto.get("caseAttr3").toString();
		if (caseAttr3!=null&&!caseAttr3.equals("")&&!"0".equals(caseAttr3)) {
			andSql = andSql + " and case_attr3 = '"+caseAttr3+"'";
		}
		
		String agentNum = dto.get("agentNum").toString();
		if(agentNum!=null&&!"".equals(agentNum.trim())){
			andSql += " and case_rid = '"+agentNum+"' ";
		}
		
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		params.add(type);
		params.add("");
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
//		
//		procSql.setProcedureName("proc_HZCaseInfoStatisticsByProperty");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		String temp=dto.get("condition").toString();
//		if("big".equals(temp))	params.add("����");
//		if("small".equals(temp))	params.add("С��");
//		String caseAttr3 = dto.get("caseAttr3").toString();
//		if (!caseAttr3.equals("")) {
//			params.add(caseAttr3);
//		}
//		
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		
//		// �Դ洢���̷��صĵĽ�����зּ�
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		if(xAxis.size()>0&&yAxis.size()>0)
//		{
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("name", tmp);
//				r.set("type1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				list.add(r);
//			}
//		}
//		
////		procSql.setProcedureName("proc_HZCaseInfoStatisticsByProperty");
////		params.add(dto.get("beginTime").toString());
////		params.add(dto.get("endTime").toString());
////		
////		String type = dto.get("condition").toString();
////		if("class1".equals(type))
////			type = "����";
////		else if("class2".equals(type))
////			type = "С��";
////		else if("class3".equals(type))
////			type = "����";
////		
////		params.add(type);
////		procSql.setSqlvalues(params);
////		results = procSql.execute();
////		// �Դ洢���̷��صĵĽ�����зּ�
////		List<String> xAxis = getXaxis(results);
////		List<String> yAxis = getYaxis(results);
////		List<String> valueList = getValues(results, xAxis, yAxis);
////		if(xAxis.size()>0&&yAxis.size()>0) {
////			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
////				String tmp = i.next();
////				DynaBeanDTO r = new DynaBeanDTO();
////				r.set("condition", tmp);
////				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
////	//			r
////	//					.set("type2", valueList.get(xAxis.indexOf(tmp)
////	//							* yAxis.size() + 1));
////	//			r.set("type3", new Integer(Integer.parseInt(valueList.get(xAxis
////	//					.indexOf(tmp)
////	//					* yAxis.size()))
////	//					+ Integer.parseInt(valueList.get(xAxis.indexOf(tmp)
////	//							* yAxis.size() + 1))));
////				list.add(r);
////			}
////		}
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}

	/**
	 * ����������б�
	 */
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}	
	
}
