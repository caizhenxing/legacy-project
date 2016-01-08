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
import et.bo.stat.service.HZCaseInfoStatisticsByStateService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>���ﰸ������ȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ�������action</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class HZCaseInfoStatisticsByStateImpl implements HZCaseInfoStatisticsByStateService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;

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
	/**
	 * ����dto����Ĳ���ֵ��ִ��ͳ�ƣ������ظ���ͳ�ƽ�����ɵ�JFreeChart����
	 * ��ʾȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µ�ͳ��ͼ
	 * @param dto
	 * @return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_HZCaseInfoStatisticsByState");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		String agentNum = (String)dto.get("agentNum");
		if(agentNum == null )
		{
			agentNum = "";
		}
		else
		{
			agentNum = agentNum.trim();
		}
		params.add(agentNum);
//		params.add(dto.get("condition").toString());
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
		properties.put("chartTitle", "ȫ����ÿһ��ϯԱ��������״̬�µİ�������ͳ��");
		// ����ͼ�ε��ض�����
//		String yChartName="count".equals(dto.get("condition"))?"�绰����":"ͨ��ʱ��";
		String yChartName="��������";
		String xV = "";
		String yV = "";
		if("".equals(agentNum))
		{
			xV = "״̬";
			yV = "����";
		}
		else
		{
			xV = "����";
			yV = "״̬";
		}
		/*
		If (��ϯԱ��.equals(����)){
			X: ״̬��Y������������Z����ֵ
		}else{
			X:���ڣ�Y��״̬��Z����ֵ
		}
		*/
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", xV);
			properties.put("yChartName", yV);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", xV);
			properties.put("yChartName", yV);
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
	 	set @src = 'select state,dbo.uf_getDateStr(case_time) as oper_time from oper_caseinfo
		where dict_case_type=''HZCase''' + 
			' and case_rid=''' + ltrim(rtrim(@seat)) + '''' +
			' and case_time between cast(''' + @beginTime + ''' as datetime) and cast(''' + @endTime + ''' as datetime)' + 
			' and case_rid = ''' + @seat + ''''
	 */
	/**
	 * ����DTO����Ĳ���ֵ��ִ��ͳ�ƣ�����ͳ�ƽ�����д������ɷ��ϱ����ʽ���б�
	 * ��ʾȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ������� ��ʾ �б���Ϣ
	 * @param dto
	 * @return List
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
//		***************list����
//		 TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		String andSql = " and dict_case_type='HZCase' ";


		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(andSql);
		params.add("case_time");
		String temp=dto.get("agentNum").toString();
		params.add("case_rid");
		params.add(temp);
		params.add("oper_caseinfo");
		params.add("state");
		params.add("����,ԭʼ,����,����");
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
				int i_rowSum = i_ds + i_ys + i_fb + i_checked;
				r.set("����", i_ds);
				r.set("ԭʼ", i_ys);
				r.set("����", i_fb);
				r.set("����", i_checked);
				r.set("rowSum", i_rowSum); //�л���
				//������ӻ�����
				int rArr[] = {i_ds,i_ys,i_fb,i_checked,i_rowSum};
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
		r.set("rowSum", colSums[4]);
		list.add(r);
		return list;
		// TODO Auto-generated method stub
//		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
//		// ���ô洢����ȡ��ͳ�ƽ��
//		List<String> params = new ArrayList<String>();
//		List results = new ArrayList();
//		//proc_HZCaseInfoStatisticsByState
////		procSql.setProcedureName("proc_HZCaseInfoStatisticsByState");
////		params.add(dto.get("beginTime").toString());
////		params.add(dto.get("endTime").toString());
//		String agentNum = (String)dto.get("agentNum");
//		
//		if (!agentNum.equals("")) {
//			procSql.setProcedureName("proc_HZCaseInfoStatisticsMultiHasSeat");
//			params.add(dto.get("beginTime").toString());
//			params.add(dto.get("endTime").toString());
//			params.add(agentNum);
//		}else{
//			procSql.setProcedureName("proc_HZCaseInfoStatisticsMultiNoSeat");
//			params.add(dto.get("beginTime").toString());
//			params.add(dto.get("endTime").toString());
//		}
//		
//
//		
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		
//		if (!dto.get("agentNum").toString().equals("")) {
//			for (Iterator i = ((List) results.get(0)).iterator(); i.hasNext();) {
//				Map xLabel = (Map)i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("xtype", xLabel.get("case_date").toString());
//				r.set("yuanshi", xLabel.get("yuanshi").toString());
//				r.set("daishen", xLabel.get("daishen").toString());
//				r.set("bohui", xLabel.get("bohui").toString());
//				r.set("yishen", xLabel.get("yishen").toString());
//				r.set("fabu", xLabel.get("fabu").toString());
//				list.add(r);
//			}
//		}else{
//			for (Iterator i = ((List) results.get(0)).iterator(); i.hasNext();) {
//				Map xLabel = (Map)i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				r.set("xtype", xLabel.get("id").toString());
//				r.set("yuanshi", xLabel.get("yuanshi").toString());
//				r.set("daishen", xLabel.get("daishen").toString());
//				r.set("bohui", xLabel.get("bohui").toString());
//				r.set("yishen", xLabel.get("yishen").toString());
//				r.set("fabu", xLabel.get("fabu").toString());
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
			valueList.set((xAxis.indexOf(respondent)) * yAxis.size()
					+ yAxis.indexOf(processType), value);
		}
		return valueList;
	}
}
