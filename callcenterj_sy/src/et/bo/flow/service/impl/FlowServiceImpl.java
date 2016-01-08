/*
 * @(#)FlowServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.flow.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.flow.service.FlowService;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperFlow;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * �ͻ�����
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class FlowServiceImpl implements FlowService {

	// ���״̬Ϊ�Ѷ�
	private static final String STATE_READED = "READED";
	// ���Ϊδ��
	private static final String STATE_NOTREAD = "NOTREAD";

	BaseDAO dao = null;
	private int num = 0;

	private BasicDataSource bds = null;
	public KeyService ks = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public BasicDataSource getBds() {
		return bds;
	}

	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * ���ݴ����ж���˱����Ƿ���ڸ���˼�¼�� ���ݴ����ѡ��״̬���жϲ������Ρ� ��������򷵻ص�ǰ��¼״̬����������ڣ��������˼�¼��
	 * 
	 * @param type
	 *            ��Ϣ�������
	 * @param entry_id
	 *            Ҫ��˼�¼��ID��ʲôʲô
	 * @param nowstate
	 *            ����ĵ�ǰ״̬��ʲô
	 * @param subid
	 *            �ύ��ID
	 */
	public void addOrUpdateFlow(String type, String entry_id, String nowstate,
			String subid, String autding) {
		if (nowstate == null) {
			nowstate = "ԭʼ";
		}
		String power = type;
		if (type.equals("����׷�ٿ�") || type.equals("�г�������")) {
			if (nowstate.equals("ԭʼ")) {
				nowstate = "����";
			}
			if (nowstate.equals("һ�󲵻�") || nowstate.equals("����")) {
				// power += "һ��";
			} else if (nowstate.equals("���󲵻�") || nowstate.equals("һ��")) {
				// power += "����";
				power += "һ��";
			} else if (nowstate.equals("���󲵻�") || nowstate.equals("����")) {
				// power += "����";
				power += "����";
			}
		}
		// �������ԭʼ״̬���������һ���Ĵ���
		if (!nowstate.equals("ԭʼ") && !nowstate.equals("����")) {

			OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, entry_id);
			// ���entry_id�õ��Ķ���Ϊ�����ʾ�������ύ�Ĳ���
			if (po == null) {

				po = new OperFlow();
				po.setFlowId(entry_id);
				po.setType(type);
				po.setPower(power);
				po.setSubmitId(subid);// �״��ύ��
				// zhang feng update
				po.setSubmitIdEnd(autding);// ���Ҳ������ύ����˭
				po.setSubmitTime(TimeUtil.getNowTime());// �״��ύʱ��
				po.setSubmitTimeEnd(TimeUtil.getNowTime());// ����ύʱ��
				po.setState(nowstate);
				po.setIsRead(STATE_NOTREAD);
				dao.saveEntity(po);

			} else {
				// �����ֵ��������޸Ĳ���
				if (!nowstate.equals(po.getState())) {// ֻ�е�״̬�ı�ʱ���Ų�����˱�
					po.setPower(power);

					po.setSubmitId(subid);// �״��ύ��
					// zhang feng update
					po.setSubmitIdEnd(autding);// �ύ��˭���д���
					po.setSubmitTimeEnd(TimeUtil.getNowTime());
					po.setState(nowstate);
					po.setIsRead(STATE_NOTREAD);
					dao.saveEntity(po);
				}
			}
		}
	}

	/**
	 * ��������жϾ������Ȩ�޵���Ա����Ϣ
	 * 
	 * @param type
	 *            Ҫ��˵�������ƣ�����ͨ������
	 * @return
	 */
	// private String getSubidByType(String type) {
	// String resultType = "";
	// MyQuery mq = new MyQueryImpl();
	// DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
	// dc.add(Restrictions.like("auditing", "%" + type + "%"));
	// mq.setDetachedCriteria(dc);
	// Object[] result = dao.findEntity(mq);
	// for (int i = 0, size = result.length; i < size; i++) {
	// SysUser su = (SysUser) result[i];
	// resultType = su.getUserId();
	// }
	// return resultType;
	// }
	// public String subid2power(String subid) {
	// String auditing = "";
	// String sql = "select auditing,role_id from sys_user where user_id = '"
	// + subid + "'";
	// try {
	// RowSet rs = dao.getRowSetByJDBCsql(sql);
	// if (rs.next()) {
	// auditing = rs.getString(1);
	// String role_id = rs.getString(2);
	// if (role_id != null && role_id.equals("administrator")) {
	// auditing = "administrator";
	// }
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return auditing;
	// }
	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * 
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addFlow(IBaseDTO dto) {

		OperFlow po = new OperFlow();

		po.setFlowId(ks.getNext("oper_flow"));
		po.setSubmitId(dto.get("submit_id").toString());
		po.setSubmitTime(TimeUtil.getTimeByStr(dto.get("submit_time")
				.toString(), "yyyy-MM-dd"));

		dao.saveEntity(po);

	}

	/**
	 * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List flowQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		FlowHelp h = new FlowHelp();

		Object[] result = (Object[]) dao.findEntity(h.flowQuery(dto, pi));
		num = dao.findEntitySize(h.flowQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperFlow po = (OperFlow) result[i];
			list.add(flowToDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * ��ѯ�б����� po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO flowToDynaBeanDTO(OperFlow po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("flow_id", po.getFlowId());
		dto.set("type", po.getType());
		dto.set("submit_id", po.getSubmitId());
		dto.set("submit_time", TimeUtil.getTheTimeStr(po.getSubmitTime(),
				"yyyy-MM-dd HH:mm:ss"));
		dto.set("submit_id_end", po.getSubmitIdEnd());
		dto.set("submit_time_end", TimeUtil.getTheTimeStr(
				po.getSubmitTimeEnd(), "yyyy-MM-dd HH:mm:ss"));
		dto.set("state", po.getState());

		String is_read = po.getIsRead();
		if (is_read != null && is_read.equals("1")) {
			is_read = "<font color=\"#0000FF\">�Ѳ���</font>";
		} else {
			is_read = "<font color=\"#FF0000\">δ����</font>";
		}
		dto.set("is_read", is_read);
		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getFlowSize() {
		return num;
	}

	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO���� ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id
	 *            ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getFlowInfo(String id) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		// dto.set("flow_id", po.getFlowId());
		// dto.set("type_id", po.getTypeId());
		// dto.set("entry_id", po.getEntryId());
		// dto.set("submit_id", po.getSubmitId());
		// dto.set("accept_id", po.getAcceptId());
		// dto.set("dict_flow_state", po.getDictFlowState());
		// dto.set("remark",po.getRemark());
		//		
		// String submit_time =
		// DateFormat.getDateInstance().format(po.getSubmitTime());
		// dto.set("submit_time", submit_time);
		//		
		// String handle_time =
		// DateFormat.getDateInstance().format(po.getHandleTime());
		// dto.set("handle_time", handle_time);

		return dto;

	}

	/**
	 * �޸����ݡ� �޸�ĳ����¼�����ݡ�
	 * 
	 * @param dto
	 *            Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateFlow(IBaseDTO dto) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, dto.get(
				"flow_id").toString());// ���������޸ı�

		String type_id = dto.get("type_id").toString();
		String entry_id = dto.get("entry_id").toString();
		String dict_flow_state = dto.get("dict_flow_state").toString();

		String table = "";
		String field = "";
		String idName = "";
		// ����type_id�ж���Դ���ĸ���Ҫ�޸��ĸ�����ĸ��ֶ�
		if (type_id.equals("oper_caseinfo_putong")
				|| type_id.equals("oper_caseinfo_FocusCase")
				|| type_id.equals("oper_caseinfo_HZCase")
				|| type_id.equals("oper_caseinfo_effectCase")) { // ����ǰ������

			table = "oper_caseinfo";
			field = "dict_case_state";
			idName = "case_id";

		} else if (type_id.equals("oper_priceinfo")) { // �۸��
			table = "oper_priceinfo";
			field = "dict_case_state";
			idName = "price_id";
		} else if (type_id.equals("oper_sadinfo")) { // �����
			table = "oper_sadinfo";
			field = "dict_sad_state";
			idName = "sad_id";
		} else if (type_id.equals("oper_medicinfo")) { // ҽ��
			table = "oper_medicinfo";
			field = "dict_state";
			idName = "id";
		} else if (type_id.equals("oper_book_medicinfo")) { // ԤԼҽ��
			table = "oper_book_medicinfo";
			field = "dict_state";
			idName = "id";
		} else if (type_id.equals("oper_corpinfo")) { // ��ҵ
			table = "oper_corpinfo";
			field = "dict_sate";
			idName = "id";
		} else if (type_id.equals("oper_focusinfo")) { // ����׷��
			table = "oper_focusinfo";
			field = "dict_focus_state";
			idName = "focus_id";
		} else if (type_id.equals("oper_markanainfo")) { // �г�����
			table = "oper_markanainfo";
			field = "dict_markana_state";
			idName = "markana_id";
		}

		String sql = "update " + table + " set " + field + " = '"
				+ dict_flow_state + "' where " + idName + " = '" + entry_id
				+ "'";
		executeUpdate(sql);
		// ֻ�б�ע��״̬���������ǿ����޸ĵ�
		// po.setTypeId(type_id);
		// po.setEntryId(entry_id);
		// po.setSubmitId(dto.get("submit_id").toString());
		// po.setSubmitTime(TimeUtil.getTimeByStr(dto.get("submit_time").toString(),
		// "yyyy-MM-dd"));
		// po.setAcceptId(dto.get("accept_id").toString());
		// //po.setHandleTime(TimeUtil.getTimeByStr(dto.get("handle_time").toString(),
		// "yyyy-MM-dd"));
		// po.setDictFlowState(dict_flow_state);
		// po.setRemark(dto.get("remark").toString());

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ɾ�����ݡ� ɾ��ĳ����¼��
	 * 
	 * @param id
	 *            Ҫɾ�����ݵı�ʶ
	 */
	public void delFlow(String id) {

		OperFlow cq = (OperFlow) dao.loadEntity(OperFlow.class, id);
		dao.removeEntity(cq);

	}

	/**
	 * ���ɾ���� ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * 
	 * @param id
	 *            Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, id);// ���������޸ı�
		// po.setIsDelete("1"); //"1"����ɾ������˼

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * ִ��SQL���
	 * 
	 * @param sql
	 * @return i ����int
	 */
	public int executeUpdate(String sql) {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int i = stmt.executeUpdate(sql);

			return i;

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return 0;

	}

	// �û��б�ģ��Ϊ select�ṩ����
	public List<LabelValueBean> getUserList() {
		List<LabelValueBean> bList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_AGENT_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		LabelValueBean bean = null;
		SysUser su = null;
		Object[] arrs = dao.findEntity(mq);
		for (int i = 0; i < arrs.length; i++) {
			su = (SysUser) arrs[i];
			bean = new LabelValueBean();
			bean.setLabel(su.getUserName());
			bean.setValue(su.getUserId());
			bList.add(bean);
		}
		return bList;
	}

	/**
	 * zhang feng add �жϴ�����¼�Ƿ��Ѿ���������������򲻽��д���
	 */
	public String isRead(String operId) {
		String result = "";
		OperFlow of = (OperFlow) dao.loadEntity(OperFlow.class, operId);
		result = of.getIsRead();
		return result;
	}

	/**
	 * zhang feng add ����־״̬�ı�Ϊ�Ѷ�״̬
	 */
	public void modifyRead(String operId) {
		OperFlow of = (OperFlow) dao.loadEntity(OperFlow.class, operId);
		of.setIsRead("1");
		dao.saveEntity(of);
	}
}
