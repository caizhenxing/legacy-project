

package et.bo.eventResult.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.eventResult.service.EventResultService;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCustinfo;
import et.po.OperEvent;
import et.po.OperEventResult;
import et.po.OperEventResultView;
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
 * <p>�ͻ�����</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class EventResultServiceImpl implements EventResultService {
	
	BaseDAO dao = null;
	private int num = 0;

	public KeyService ks = null;
	
	public ClassTreeService cts = null;
	
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

	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addEventResult(IBaseDTO dto) {
		System.out.println("cust_id is "+dto.get("cust_id"));
		System.out.println("linkman is "+dto.get("linkman"));
		String cust_id = dto.get("cust_id").toString();
		if(!"".equals(cust_id)) {
			String[] array = cust_id.split(",");
			for(int i=0,size=array.length; i<size; i++) {
				OperEventResult po = new OperEventResult();
				
				po.setId(ks.getNext("oper_eventResult"));
				po.setOperEvent(new OperEvent((String)dto.get("event_id")));
				po.setLinkmanId((String)dto.get("linkman_id"));
				po.setLinkman(array[i]);
				po.setFeedback((String)dto.get("feedback"));
				po.setFeedbackDate(TimeUtil.getTimeByStr((String)dto.get("feedback_date"), "yyyy-MM-dd"));
				po.setAdduser((String)dto.get("adduser"));
				po.setAddtime(TimeUtil.getNowTime());
		
				dao.saveEntity(po);
			}
		}else{
		
			OperEventResult po = new OperEventResult();
			
			po.setId(ks.getNext("oper_eventResult"));
			po.setOperEvent(new OperEvent((String)dto.get("event_id")));
			po.setLinkmanId((String)dto.get("linkman_id"));
			po.setFeedback((String)dto.get("feedback"));
			po.setFeedbackDate(TimeUtil.getTimeByStr((String)dto.get("feedback_date"), "yyyy-MM-dd"));
			po.setAdduser((String)dto.get("adduser"));
			po.setAddtime(TimeUtil.getNowTime());
	
			dao.saveEntity(po);
		}
	}


	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List eventResultQuery(IBaseDTO dto, PageInfo pi) {
		
		List list = new ArrayList();
		EventResultHelp h = new EventResultHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.eventResultQuery(dto, pi));
		num = dao.findEntitySize(h.eventResultQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperEventResultView po = (OperEventResultView) result[i];
			list.add(eventResultToDynaBeanDTO(po));
		}
		return list;
	}
	/**
	 * ��ѯ�б����� po ת dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO eventResultToDynaBeanDTO(OperEventResultView po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("topic", po.getTopic());
		String feedback = po.getFeedback();
		if(feedback != null && feedback.length() > 15){
			feedback = feedback.substring(0, 15);
		}
		dto.set("feedback", feedback);
		dto.set("principal", po.getPrincipal());
		dto.set("linkman_id", po.getLinkmanId());
		
		String linkman = po.getLinkman();
		if(linkman != null && !"".equals(linkman)){
			EventResultHelp erh = new EventResultHelp();
			Object[] result = dao.findEntity(erh.linkmanQuery(linkman));
			if(result != null && result.length > 0){
				OperCustinfo oc = (OperCustinfo)result[0];
				dto.set("linkman", oc.getCustName());
			}
		} else
			dto.set("linkman", "");
		dto.set("feedback_date", TimeUtil.getTheTimeStr(po.getFeedbackDate(), "yyyy-MM-dd"));

		return dto;
	}
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getEventResultSize() {
		return num;
	}
	
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getEventResultInfo(String id) {
		
		OperEventResult po = (OperEventResult)dao.loadEntity(OperEventResult.class,id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		String linkman = po.getLinkman();
		if(linkman != null && !"".equals(linkman)){
			EventResultHelp erh = new EventResultHelp();
			Object[] result = dao.findEntity(erh.linkmanQuery(linkman));
			if(result != null && result.length > 0){
				OperCustinfo oc = (OperCustinfo)result[0];
				dto.set("linkman", oc.getCustName());
			}
		} else
			dto.set("linkman", "");
		dto.set("feedback_date", TimeUtil.getTheTimeStr(po.getFeedbackDate(), "yyyy-MM-dd"));
		dto.set("feedback", po.getFeedback());

		return dto;
		
	}
	
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateEventResult(IBaseDTO dto) {
		
		OperEventResult po = (OperEventResult)dao.loadEntity(OperEventResult.class,(String)dto.get("id"));//���������޸ı�
		
		po.setLinkmanId((String)dto.get("linkman_id"));
		po.setFeedbackDate(TimeUtil.getTimeByStr((String)dto.get("feedback_date"), "yyyy-MM-dd"));
		po.setFeedback((String)dto.get("feedback"));

		try{
			dao.saveEntity(po);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delEventResult(String id) {
		
		OperEventResult po = (OperEventResult)dao.loadEntity(OperEventResult.class,id);
		dao.removeEntity(po);
		
	}
	
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

//	 ��Ա�б�Ϊ��ѯҳ���ṩ����
	public List<LabelValueBean> getUserList() {
		List<LabelValueBean> uList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_LINK_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] os = dao.findEntity(mq);
		LabelValueBean bean = null;
		OperCustinfo su = new OperCustinfo();
		for (int i = 0; i < os.length; i++) {
			bean = new LabelValueBean();
			su = (OperCustinfo) os[i];
			try {
				bean.setLabel(su.getCustName());
				bean.setValue(su.getCustId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			uList.add(bean);
		}

		return uList;
	}

}
