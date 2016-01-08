/**
 * 	@(#) IvrServiceImpl.java 2008-4-14 ����01:09:59
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
import java.util.Set;

import javax.sql.RowSet;

import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.IvrStatService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * @author chen gang
 * 
 */
public class IvrStatServiceImpl implements IvrStatService {
	private BaseDAO dao;
	private String keyModuleId="SYS_TREE_0000002781"; //���м����label Ϊ��IVR��Ŀ����ά�� ��id
	private KeyService ks;

	private ProcSql procSql;

	private JFreeService chartService;
	
	private Map ivrModule = new HashMap();
	
	public IvrStatServiceImpl(){
	
	}

	public JFreeService getChartService() {
		return chartService;
	}
	/**
	 * 
	 * @param id ���м����label Ϊ��IVR��Ŀ����ά�� ��id
 	 */
	public void initIvrModele()
	{
		try
		{
			String sql = "select label,nickName from base_tree where parent_id = '"+keyModuleId+"'";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			try {
				rs.beforeFirst();
				while (rs.next()) {
					ivrModule.put(rs.getString("nickName"), rs.getString("label"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		String qType = dto.get("qtype")==null?"":dto.get("qtype").toString().trim();
		if("ͳ�����ڴ���".equals(qType)||"ͳ������ʱ��".equals(qType))
		{
			procSql.setProcedureName("proc_ivrdataByDate");
		}
		else
		{
			procSql.setProcedureName("proc_ivrdata");
		}
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("qtype").toString());
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
		properties.put("chartTitle", "IVRģ�����ͳ��");
		// ����ͼ�ε��ض�����
		String yChartName="ͳ�ƴ���".equals(dto.get("qtype"))?"��������":"����ʱ��";
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "IVRģ��");
			properties.put("yChartName", yChartName);
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "IVRģ��");
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
		String qType = dto.get("qtype")==null?"":dto.get("qtype").toString().trim();
		if("ͳ�����ڴ���".equals(qType)||"ͳ������ʱ��".equals(qType))
		{
			procSql.setProcedureName("proc_ivrdataByDate");
		}
		else
		{
			procSql.setProcedureName("proc_ivrdata");
		}
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("qtype").toString());

		try {
			procSql.setSqlvalues(params);
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getStackTrace());
		}

		results = procSql.execute();
		// �Դ洢���̷��صĵĽ�����зּ�
		List<String> xAxis = getXaxis(results);
		List<String> yAxis = getYaxis(results);
		List<String> valueList = getValues(results, xAxis, yAxis);
		int num = 0;
		if(xAxis.size()>0&&yAxis.size()>0) {
			for (Iterator<String> i = xAxis.iterator(); i.hasNext();) {
				String tmp = i.next();
				DynaBeanDTO r = new DynaBeanDTO();
				/*��������*/
				int sum = Integer.parseInt(valueList.get(xAxis.indexOf(tmp) * yAxis.size()));
				num += sum;
				String snum =String.valueOf(num);
				if(snum.equals("")||snum==null){
					num = 0;
				}
				Set set = ivrModule.keySet();
				//System.out.println("ivr ..... "+set.contains(tmp));
				if(set.contains(tmp))
					r.set("IVRmodule", ivrModule.get(tmp));
				else
					r.set("IVRmodule", tmp);
				
				if(dto.get("qtype").toString().indexOf("����")!=-1){
					r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size())+"��");
					r.set("num", num+"��");
				}
				else{
					r.set("count", valueList.get(xAxis.indexOf(tmp) * yAxis.size())+"��");
					r.set("num", num+"��");
				}				
				
				list.add(r);			
			}
		}
		return list;
	}

	private List<String> getXaxis(List result) {
		List<String> xAxis = new ArrayList<String>();
		// ����X�����label����
		for (Iterator i = ((List) result.get(0)).iterator(); i.hasNext();) {
			Map xLabel = (Map) i.next();
			xAxis.add(xLabel.get("X").toString());
		}
		return xAxis;
	}

	private List<String> getYaxis(List result) {
		List<String> yAxis = new ArrayList<String>();
		// ����Y�����label����
		for (Iterator i = ((List) result.get(1)).iterator(); i.hasNext();) {
			Map yLabel = (Map) i.next();
			yAxis.add(yLabel.get("Y").toString());
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
			String value = record.get("X").toString();
			String module = record.get("Y").toString();

			String mons = record.get("Sum1").toString();

			valueList.set((xAxis.indexOf(value)) * yAxis.size()
					+ yAxis.indexOf(module), mons);
		}
		return valueList;
	}
}
