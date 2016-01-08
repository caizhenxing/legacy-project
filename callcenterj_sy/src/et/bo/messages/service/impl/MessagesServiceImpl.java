/*
 * @(#)MessagesServiceImpl.java	 2008-05-06
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * 消息管理
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
	 * 查询数据列表,返回记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
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
	 * 得到未读取的消息 return List<MessageInfo>
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
	 * 查询方法的 po 转 dto
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
			flag = "已读";
		} else {
			flag = "未读";
		}
		dto.set("dict_read_flag", flag);

		return dto;
	}

	/**
	 * 查询数据列表的条数。 取得问题查询列表的条数。
	 * 
	 * @return 得到list的条数
	 */
	public int getMessagesSize() {

		return num;

	}

	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象 取得某条数据的详细信息。
	 * 
	 * @param id
	 *            取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getMessagesInfo(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("message_id", po.getMessageId());
		dto.set("send_id", po.getSendId());
		dto.set("send_name", po.getSendName());
		//接收人信息
		String receiveId = po.getReceiveId();
		dto.set("receive_id", receiveId);
		dto.set("message_content", po.getMessageContent());

		String date = DateFormat.getDateInstance().format(po.getSendTime());
		dto.set("send_time", date);
		String flag = "";
		if (po.getDictReadFlag().equals("1")) {
			flag = "已读";
		} else {
			flag = "未读";
		}
		dto.set("dict_read_flag", flag);

		po.setDictReadFlag("1");// 标记已读
		dao.saveEntity(po);
		
		//删除掉集合中的信息
		MessageCollection.m_common_instance.remove(receiveId);
		
		return dto;
	}

	/**
	 * 修改数据。 修改某条记录的内容。
	 * 
	 * @param dto
	 *            要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateMessages(IBaseDTO dto) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, dto
				.get("message_id").toString());// 根据主键修改表
		po.setMessageContent(dto.get("message_content").toString());// 只修改内容

		dao.saveEntity(po);
		return false;

	}

	/**
	 * 删除数据。 删除某条记录。
	 * 
	 * @param id
	 *            要删除数据的标识
	 */
	public void delMessages(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);
		// 接收消息人的id信息
		String receiveId = po.getReceiveId();
		dao.removeEntity(po);
		
		//删除掉集合中的信息
		MessageCollection.m_common_instance.remove(receiveId);

	}

	/**
	 * 标记删除。 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
	 * 
	 * @param id
	 *            要标记删除数据的标识
	 */
	public boolean isDelete(String id) {

		OperMessages po = (OperMessages) dao.loadEntity(OperMessages.class, id);// 根据主键修改表
		/*
		 * po.setIsDelete("1"); //"1"是已删除的意思
		 */
		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
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
				// 接收人的id信息，即工号是多少的座席员接收到消息
				
				po.setReceiveId(rId[i]);
				po.setMessageContent(dto.get("message_content").toString());
				po.setSendTime(curDate);
				po.setDictReadFlag("0");
	
				dao.saveEntity(po);
				
				//将短消息内容加入到集合中
				Map<String,Object> m = new HashMap<String,Object>();
				m.put(SysStaticParameter.COMMON_MESSAGE, po);
				MessageCollection.m_common_instance.put(rId[i], m);
			}
		}

	}
	
	
	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void backMessages(IBaseDTO dto) {

		OperMessages po = new OperMessages();
		String msgId = ks.getNext("oper_messages");
		Date curDate = TimeUtil.getNowTime();
		po.setMessageId(msgId);
		po.setSendId(dto.get("send_id").toString());
		po.setSendName(dto.get("send_name").toString());
		// 接收人的id信息，即工号是多少的座席员接收到消息
		String receiveId = dto.get("receive_id").toString();
		po.setReceiveId(receiveId);
		po.setMessageContent(dto.get("message_content_back").toString());
		po.setSendTime(curDate);
		po.setDictReadFlag("0");

		dao.saveEntity(po);
		
		//将短消息内容加入到集合中
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.COMMON_MESSAGE, po);
		MessageCollection.m_common_instance.put(receiveId, m);

	}

	/**
	 * 0 未读 1 已读
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

			if (auditing != null && !auditing.equals("")) {// 如果有管理员权限
				String[] auditings = auditing.split(",");
				String str = "";
				for (int i = 0; i < auditings.length; i++) {
					str += "'" + auditings[i] + "',";
				}
				str = str.substring(0, str.length() - 1);
				sql = "SELECT count(*) FROM oper_flow where is_read = '0' and power in ("
						+ str + ")";
			}
			if (is_admin) {// 如果是超级管理就，就全部都查
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

	// 人员列表为查询页面提供数据
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
