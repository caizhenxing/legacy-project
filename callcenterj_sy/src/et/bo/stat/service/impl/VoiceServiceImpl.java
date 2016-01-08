/*
 * @(#)UseService.java	 2008-04-14
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.stat.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jfree.chart.JFreeChart;

import et.bo.common.proc.ProcSql;
import et.bo.jfree.service.JFreeService;
import et.bo.jfree.service.impl.JFreeImpl;
import et.bo.stat.service.VoiceService;
import et.bo.sys.common.MathUtil;
import et.po.BaseTree;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author chen gang
 */
public class VoiceServiceImpl implements VoiceService {
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
		
		JFreeChart chart = null;
		List results = new ArrayList();
		StatDateStr.setBeginEndTime(dto);
		String beginTime = dto.get("beginTime").toString();
		String endTime = dto.get("endTime").toString();
		String telNum = dto.get("telNum").toString();
		
		List<Map> xlist = new ArrayList<Map>();
		List<Map> ylist = new ArrayList<Map>();
		List<Map> vlist = new ArrayList<Map>();
		
		Session session = dao.getConnSession();
		Connection conn = session.connection();
		Statement st = null;
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		try {
			st = conn.createStatement();
//			String sql = "select caller, count(*) from cc_voiceLeave " +
//							" where beginTime >= '"+ beginTime +"'" +
//							" and beginTime <= '"+ endTime +"' group by caller";
			sql.append("select caller, count(*) from cc_voiceLeave");
			sql.append(" where beginTime >= '"+ beginTime +"'");
			sql.append(" and beginTime <= '"+ endTime +"'");
			if(!"".equals(telNum))
				sql.append(" and caller like '%"+ telNum +"%'");
			sql.append(" group by caller");
			rs = st.executeQuery(sql.toString());
			while(rs.next()) {
				String x = rs.getString(1);
				String y = "���Դ���";
				String c = rs.getString(2);
				Map xmap = new HashMap();
				xmap.put("X", x);
				xlist.add(xmap);
				
				Map vmap = new HashMap();
				vmap.put("X", x);
				vmap.put("Y", y);
				vmap.put("Sum1", c);
				vlist.add(vmap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Map ymap = new HashMap();
			ymap.put("Y", "���Դ���");
			ylist.add(ymap);
			results.add(xlist);
			results.add(ylist);
			results.add(vlist);
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*	// ����������������ô洢����
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		procSql.setProcedureName("proc_use");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("address").toString());
		procSql.setSqlvalues(params);
		results = procSql.execute();
		*/
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
		properties.put("chartTitle", "��������ͳ��");
		// ����ͼ�ε��ض�����
		if (chartType.indexOf(JFreeImpl.BAR) >= 0) {
			properties.put("xChartName", "�绰����");
			properties.put("yChartName", "���Դ���");
			properties.put("xChartValues", xAxis);
			properties.put("yChartValues", yAxis);
			properties.put("chartValues", valueList);
		} else if (chartType.indexOf(JFreeImpl.LINE) >= 0) {
			properties.put("xChartName", "�绰����");
			properties.put("yChartName", "���Դ���");
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
		String menu;
		int    dbNum;//�㲥
		int    dzNum;//����
		int    tdNum;//�˶�
		int    lvNum;//��������
		StatDateStr.setBeginEndTime(dto);
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		// ���ô洢����ȡ��ͳ�ƽ��
		List<String> params = new ArrayList<String>();
		List results = new ArrayList();
		//������ѯ����

		procSql.setProcedureName("proc_voiceLeaveNew");
		params.add(dto.get("beginTime").toString());
		params.add(dto.get("endTime").toString());
		params.add(dto.get("menu").toString());
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
			r.set("menu", m.get("menu"));
			try
			{
				int db = Integer.parseInt(m.get("dbNum").toString()); 
				int dz = Integer.parseInt(m.get("dzNum").toString());
				int td = Integer.parseInt(m.get("tdNum").toString());
				int	leave = Integer.parseInt(m.get("lvNum").toString());
				r.set("dbNum", db);
				r.set("dzNum", dz);
				r.set("tdNum", td);
				r.set("lvNum", leave);
				//������ӻ�����
				int rArr[] = {db,dz,td,leave};
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
		r.set("menu", "�ϼ�(��)");
		int colSums[] = mu.getSumArr();
		r.set("dbNum", colSums[0]);
		r.set("dzNum", colSums[1]);
		r.set("tdNum", colSums[2]);
		r.set("lvNum", colSums[3]);
		list.add(r);
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
			String X = record.get("X").toString();
			String Y = record.get("Y").toString();
			String value = record.get("Sum1").toString();
			valueList.set((xAxis.indexOf(X)) * yAxis.size()
					+ yAxis.indexOf(Y), value);
		}
		return valueList;
	}
	
	/**
	 * ����menu�б��û�ѡ��
	 * @param
	 * @return List<LabelValueBean>
	 */
	public List<LabelValueBean> menuList()
	{
		String hql = "from BaseTree where parentId = 'SYS_TREE_0000001923'";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		List<LabelValueBean> menuList = new ArrayList<LabelValueBean>();
		LabelValueBean lv = null;
		for(int i=0; i<o.length; i++)
		{
			BaseTree bt = (BaseTree)o[i];
			lv = new LabelValueBean();
			lv.setLabel(bt.getLabel());
			lv.setValue(bt.getNickName());
			menuList.add(lv);
		}
		return menuList;
	}
}
