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
import et.bo.stat.service.CorpInfoByStateService;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class CorpInfoByStateImpl.
 * ��ҵ��Ϣ����ϯ�����ͳ��
 * @author Wang Lichun
 */
public class CorpInfoByStateImpl implements CorpInfoByStateService {
	private BaseDAO dao;

	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;
	
	private ClassTreeService cts = new ClassTreeServiceImpl();

	/**
	 * Gets the charService.
	 * The charService type is ClassTreeService.
	 * @return the charService
	 */
	public JFreeService getChartService() {
		return chartService;
	}

	/**
	 * Sets the charService.
	 * The charService type is ClassTreeService.
	 * @param chartService the new charService
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
	 * �����ϯ�б�
	 * params String sql
	 * return List
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
	 * ����ͳ��ͼ��
	 * params IBaseDTO dto
	 * return JFreeChart
	 */
	public JFreeChart statistic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		JFreeChart chart = null;
		// ����������������ô洢����
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_corpInfoByState");
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
		properties.put("chartTitle", "ȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µ���ҵ��Ϣ����");
		// ����ͼ�ε��ض�����
		String yChartName="��ϯԱ����ĸ����״̬�µ���ҵ��Ϣ����";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "��Ϣ����");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "��Ϣ����");
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
	 * ���ͳ���б�
	 * params IBaseDTo dto
	 * return List<DynaBeanDTO>
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		StatDateStr.setBeginEndTime(dto);
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_corpInfoByState");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("name").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
//		int j = 0;
		if(xAxis.size()>0&&yAxis.size()>0) {
			
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();		
				if(!"".equals(dto.get("name"))) {
					DynaBeanDTO r = new DynaBeanDTO();
					r.set("name", dto.get("name"));
					
					r.set("count", tmp);
					r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
					list.add(r);
				} else{
					for(int k=0;k<yAxis.size();k++){
						DynaBeanDTO r = new DynaBeanDTO();
						r.set("name", tmp);
						r.set("count", yAxis.get(k));						
						r.set("count1", valueList.get(xAxis.indexOf(tmp) * yAxis.size()+ yAxis.indexOf(yAxis.get(k))));
						list.add(r);
					}
				}			
				
			}
			
		}
		return list;
		
	}

	/**
	 * Gets the xaxis.
	 * ���X��
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
	 * ���Y��
	 * @param result the List
	 * 
	 * @return the List<String>
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
	 * ��ý��  
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
	 * ��ý��
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
}
