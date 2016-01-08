/*
 * @(#)MessagesServiceImpl.java	 2008-05-06
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.messages.service.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.messages.messageLoop.MessageInfo;
import et.bo.messages.messageLoop.MessageLoop;
import et.bo.messages.service.MessagesService;
import et.bo.messages.show.MessageCollection;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperMessages;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
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
 * ��Ϣ����
 * </p>
 * 
 * @author nie
 */

public class MessagesServiceImpl implements MessagesService {

	BaseDAO dao = null;

	private List callbackList = null;

	private int num = 0;
	
	private int adminnum = 0;

	private ClassTreeService cts = null;

	public KeyService ks = null;

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
	 * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List messagesQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		MessagesHelp h = new MessagesHelp();
		Object[] result = (Object[]) dao.findEntity(h.messagesQuery(dto, pi));
		num = dao.findEntitySize(h.messagesSizeQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			OperMessages cq = (OperMessages) result[i];
			list.add(messagesToDynaBeanDTO(cq));
		}
		return list;
	}

	/**
	 * �õ�δ��ȡ����Ϣ return List<MessageInfo>
	 */
	public void appendAllNonReadMsg() {
		MessagesHelp h = new MessagesHelp();
		Object[] result = (Object[]) dao.findEntity(h.nonReadmessagesQuery());
		for (int i = 0; i < result.length; i++) {
			OperMessages om = (OperMessages) result[i];
			MessageInfo mi = new MessageInfo();
			mi.setMessage_id(om.getMessageId());
			mi.setDict_read_flag(om.getDictReadFlag());
			mi.setMessage_content(om.getMessageContent());
			mi.setReceive_id(om.getReceiveId());
			mi.setSend_id(om.getSendId());
			mi.setSend_name(om.getSendName());
			mi.setSend_time(om.getSendTime());
			MessageLoop.addMsgInfo(mi);
		}

	}

	/**
	 * ��ѯ������ po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO messagesToDynaBeanDTO(OperMessages po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("message_id", po.getMessageId());
		dto.set("send_id", po.getSendId());
		dto.set("send_name", po.getSendName());
		dto.set("receive_id", po.getReceiveId());
		if(po.getMessageContent()!=null){
			if(po.getMessageContent().length()<19)
				dto.set("message_content", po.getMessageContent());
			else
				dto.set("message_content", po.getMessageContent().substring(0, 18)+"...");
		}
//		dto.set("message_content", po.getMessageContent());

		String date = DateFormat.getDateInstance().format(po.getSendTime());
		dto.set("send_time", date);
		String flag = "";
		if (po.getDictReadFlag().equals("1")) {
			flag = "�Ѷ�";
		} else {
			flag = "δ��";
		}
		dto.set("dict_read_flag", flag);

		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getMessagesSize() {

		return num;

	}

	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO���� ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id
	 *            ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getMessagesInfo(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("message_id", po.getMessageId());
		dto.set("send_id", po.getSendId());
		dto.set("send_name", po.getSendName());
		//��������Ϣ
		String receiveId = po.getReceiveId();
		dto.set("receive_id", receiveId);
		dto.set("message_content", po.getMessageContent());

		String date = DateFormat.getDateInstance().format(po.getSendTime());
		dto.set("send_time", date);
		String flag = "";
		if (po.getDictReadFlag().equals("1")) {
			flag = "�Ѷ�";
		} else {
			flag = "δ��";
		}
		dto.set("dict_read_flag", flag);

		po.setDictReadFlag("1");// ����Ѷ�
		dao.saveEntity(po);
		
		//ɾ���������е���Ϣ
		MessageCollection.m_common_instance.remove(receiveId);
		
		return dto;
	}

	/**
	 * �޸����ݡ� �޸�ĳ����¼�����ݡ�
	 * 
	 * @param dto
	 *            Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateMessages(IBaseDTO dto) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, dto
				.get("message_id").toString());// ���������޸ı�
		po.setMessageContent(dto.get("message_content").toString());// ֻ�޸�����

		dao.saveEntity(po);
		return false;

	}

	/**
	 * ɾ�����ݡ� ɾ��ĳ����¼��
	 * 
	 * @param id
	 *            Ҫɾ�����ݵı�ʶ
	 */
	public void delMessages(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);
		// ������Ϣ�˵�id��Ϣ
		String receiveId = po.getReceiveId();
		dao.removeEntity(po);
		
		//ɾ���������е���Ϣ
		MessageCollection.m_common_instance.remove(receiveId);

	}

	/**
	 * ���ɾ���� ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * 
	 * @param id
	 *            Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);// ���������޸ı�
		/*
		 * po.setIsDelete("1"); //"1"����ɾ������˼
		 */
		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * 
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addMessages(IBaseDTO dto) {
		String receiveId = dto.get("receive_id").toString();
		String receiveName = dto.get("receive_name").toString();
//		System.out.println("receive_id is "+receiveId);
//		System.out.println("receive_name is "+receiveName);
		String[] rId = receiveId.split(",");
		if(rId != null && rId.length > 0) {
			for(int i=0; i<rId.length; i++){
				OperMessages po = new OperMessages();
				String msgId = ks.getNext("oper_messages");
				Date curDate = TimeUtil.getNowTime();
				po.setMessageId(msgId);
				po.setSendId(dto.get("send_id").toString());
				po.setSendName(dto.get("send_name").toString());
				// �����˵�id��Ϣ���������Ƕ��ٵ���ϯԱ���յ���Ϣ
				
				po.setReceiveId(rId[i]);
				po.setMessageContent(dto.get("message_content").toString());
				po.setSendTime(curDate);
				po.setDictReadFlag("0");
	
				dao.saveEntity(po);
				
				//������Ϣ���ݼ��뵽������
				Map<String,Object> m = new HashMap<String,Object>();
				m.put(SysStaticParameter.COMMON_MESSAGE, po);
				MessageCollection.m_common_instance.put(rId[i], m);
			}
		}

	}
	
	
	/**
	 * ������ݡ� �����ݿ������һ����¼��
	 * 
	 * @param dto
	 *            �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void backMessages(IBaseDTO dto) {

		OperMessages po = new OperMessages();
		String msgId = ks.getNext("oper_messages");
		Date curDate = TimeUtil.getNowTime();
		po.setMessageId(msgId);
		po.setSendId(dto.get("send_id").toString());
		po.setSendName(dto.get("send_name").toString());
		// �����˵�id��Ϣ���������Ƕ��ٵ���ϯԱ���յ���Ϣ
		String receiveId = dto.get("receive_id").toString();
		po.setReceiveId(receiveId);
		po.setMessageContent(dto.get("message_content_back").toString());
		po.setSendTime(curDate);
		po.setDictReadFlag("0");

		dao.saveEntity(po);
		
		//������Ϣ���ݼ��뵽������
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.COMMON_MESSAGE, po);
		MessageCollection.m_common_instance.put(receiveId, m);

	}

	/**
	 * 0 δ�� 1 �Ѷ�
	 */
	public boolean isHaveMessage(String user) {
		// TODO Auto-generated method stub

		boolean flag = false;

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);
		dc.add(Restrictions.eq("receiveId", user));
		dc.add(Restrictions.eq("dictReadFlag", "0"));
		mq.setDetachedCriteria(dc);

		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0; i < result.length; i++) {
			OperMessages om = (OperMessages) result[i];
			String id = om.getMessageId();

			dao
					.execute("update oper_messages set dict_read_flag = '1' where message_id = '"
							+ id + "'");

			flag = true;
		}
		
		return flag;
	}

	public String getStateCount(String user) {
		String count = "0";
		String auditing = "";
		boolean is_admin = false;
		String sql = "select auditing,role_id from sys_user where user_id = '"
				+ user + "'";
		try {
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			if (rs.next()) {
				auditing = rs.getString(1);
				String role = rs.getString(2);
				if (role != null && role.equals("administrator")) {
					is_admin = true;
				}
			}

			if (auditing != null && !auditing.equals("")) {// ����й���ԱȨ��
				String[] auditings = auditing.split(",");
				String str = "";
				for (int i = 0; i < auditings.length; i++) {
					str += "'" + auditings[i] + "',";
				}
				str = str.substring(0, str.length() - 1);
				sql = "SELECT count(*) FROM oper_flow where is_read = '0' and power in ("
						+ str + ")";
			}
			if (is_admin) {// ����ǳ�������ͣ���ȫ������
				sql = "SELECT count(*) FROM oper_flow where is_read = '0'";
			}

			rs = dao.getRowSetByJDBCsql(sql);
			if (rs.next()) {
				count = rs.getString(1);
			}
			if (count == null || count.equalsIgnoreCase("null")) {
				count = "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// ��Ա�б�Ϊ��ѯҳ���ṩ����
	public List<LabelValueBean> getUserList() {
		List<LabelValueBean> uList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_AGENT_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		LabelValueBean bean = null;
		SysUser su = new SysUser();
		for (int i = 0; i < os.length; i++) {
			bean = new LabelValueBean();
			su = (SysUser) os[i];
			try {
				bean.setLabel(su.getUserName());
				bean.setValue(su.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			uList.add(bean);
		}

		return uList;
	}

	public List messagesAdminQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		MessagesHelp h = new MessagesHelp();
		Object[] result = (Object[]) dao.findEntity(h.messagesAdminQuery(dto, pi));
		adminnum = dao.findEntitySize(h.messagesAdminSizeQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperMessages cq = (OperMessages) result[i];
			list.add(messagesToDynaBeanDTO(cq));
		}
		return list;
	}

	public int getMessagesAdminSize() {
		// TODO Auto-generated method stub
		return adminnum;
	}

	public void delAllMessages(String[] str) {
		// TODO Auto-generated method stub
		if(str!=null){
			if(str.length>0){
				for (int i = 0; i < str.length; i++) {
					String messageId = str[i];
					delMessages(messageId);
				}
			}
		}
	}

	public Map messageList() {
		// TODO Auto-generated method stub
		Map m = new HashMap();
		List<OperMessages> l = new ArrayList<OperMessages>();
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperMessages.class);
		dc.addOrder(Order.desc("sendTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperMessages cq = (OperMessages) result[i];
			l.add(cq);
			m.put("id", cq.getMessageId());
			m.put("cell",l);
		}
		
		return m;

	}
}
