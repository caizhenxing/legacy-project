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
import et.bo.stat.service.CorpInfoBySeatService;
import et.bo.sys.common.MathUtil;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class CorpInfoBySeatImpl.
 * ��ҵ��Ϣ����ϯ������ͳ��
 * @author Wang Lichun
 */
public class CorpInfoBySeatImpl implements CorpInfoBySeatService {
	private BaseDAO dao;

	private KeyService ks;
	
	private ClassTreeService cts;

	private ProcSql procSql;

	private JFreeService chartService;

	/**
	 * Gets the chartService.
	 * The cahartService type is JFreeService.
	 * @return the chartService
	 */
	public JFreeService getChartService() {
		return chartService;
	}

	/**
	 * Sets the chartService.
	 * The cahartService type is JFreeService.
	 * @param chartService the new chartService
	 */
	public void setChartService(JFreeService chartService) {
		this.chartService = chartService;
	}

	/**
	 * Gets the proc sql.
	 * 
	 * @return the proc sql
	 */
	public ProcSql getProcSql() {
		return procSql;
	}

	/**
	 * Sets the proc sql.
	 * 
	 * @param procSql the new proc sql
	 */
	public void setProcSql(ProcSql procSql) {
		this.procSql = procSql;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao the new dao
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * Gets the ks.
	 * 
	 * @return the ks
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * Sets the ks.
	 * 
	 * @param ks the new ks
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	/**
	 * �����ϯԱ�б�.
	 * 
	 * @param sql the sql
	 * 
	 * @return the list
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
	
	/**
	 * �����ϯԱ�б�.
	 * 
	 * @param sql the sql
	 * 
	 * @return List<LabelValueBean>
	 */	
	public List<LabelValueBean> userLVQuery(String sql)
	{
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<LabelValueBean> list=new ArrayList<LabelValueBean>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				LabelValueBean su=new LabelValueBean();
				su.setLabel(rs.getString("user_id"));
				su.setValue(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/* 
	 * ��������ͳ��ͼ��
	 * param IBaseDTO dto
	 * return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		StatDateStr.setBeginEndTime(dto);
		// ����������������ô洢����
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_corpInfoBySeat");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("name").toString());
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
		properties.put("chartTitle", "ȫ����ϯԱ��ÿһ��ϯԱ������ҵ�������");
		// ����ͼ�ε��ض�����
		String yChartName="��ϯԱ������ҵ�������";
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

	/* ���ݲ�ѯ������ò�ѯ�б�
	 * param IBaseDTO dto
	 * return list
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
		StatDateStr.setBeginEndTime(dto);
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_typeChart");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add("");
		params.add("createtime");
		params.add("corp_rid");
		params.add(dto.get("name").toString());
		params.add("oper_corpinfo");
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
//		procSql.setProcedureName("proc_corpInfoBySeat");
//		params.add(dto.get("beginTime").toString());
//		params.add(dto.get("endTime").toString());
//		params.add(dto.get("name").toString());
//		procSql.setSqlvalues(params);
//		results = procSql.execute();
//		// �Դ洢���̷��صĵĽ�����зּ�
//		List<String> xAxis = getXaxis(results);
//		List<String> yAxis = getYaxis(results);
//		List<String> valueList = getValues(results, xAxis, yAxis);
//		Integer nums = 0;
//		if(xAxis.size()>0&&yAxis.size()>0) {
//			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
//				String tmp = i.next();
//				DynaBeanDTO r = new DynaBeanDTO();
//				if(dto.get("name").toString()!=null&&!dto.get("name").equals("")){
//					r.set("name",  cts.getLabelById(tmp));
//				}else {
//					r.set("name",  tmp);
//				}				
//				r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				nums += Integer.valueOf(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
//				r.set("nums", nums);
//				list.add(r);
//			}
//		}
		return list;
		
	}

	/**
	 * Gets the xaxis.
	 * ���X������
	 * @param result the List
	 * 
	 * @return the List
	 */
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

	/**
	 * Gets the yaxis.
	 * ���Y������
	 * @param result the List
	 * 
	 * @return the List
	 */
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
	
	/**
	 * Gets the value.
	 * ���ͳ�ƽ��
	 * @param result the List
	 * @param xAxis the List
	 * @param yAxis the List
	 * 
	 * @return the List
	 */
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

	/**
	 * Gets the values.
	 * ���ͳ�ƽ��
	 * @param result the List
	 * @param xAxis the List
	 * @param yAxis the List
	 * 
	 * @return the List
	 */
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

	/**
	 * Gets the cts.
	 * The cts type is ClassTreeService.
	 * @return the cts
	 */
	public ClassTreeService getCts() {
		return cts;
	}

	/**
	 * Sets the cts.
	 * The cts type is ClassTreeService.
	 * @param cts the new ClassTreeService
	 */
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}
