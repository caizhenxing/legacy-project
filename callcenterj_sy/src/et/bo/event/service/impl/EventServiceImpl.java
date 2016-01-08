

package et.bo.event.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSet;

import et.bo.event.service.EventService;
import et.po.OperEvent;
import et.po.OperEventResult;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>�ͻ�����</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class EventServiceImpl implements EventService {
	
	BaseDAO dao = null;
	private int num = 0;

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
	 * ������ݡ�
	 * �����ݿ������һ����¼��
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addEvent(IBaseDTO dto) {
		
		OperEvent po = new OperEvent();
		
		po.setId(ks.getNext("oper_event"));
		po.setTopic((String)dto.get("topic"));
		po.setContents((String)dto.get("contents"));
		po.setEventdate(TimeUtil.getTimeByStr((String)dto.get("eventdate"), "yyyy-MM-dd"));
		po.setPrincipal((String)dto.get("principal"));
		po.setActor((String)dto.get("actor"));
		po.setAdduser((String)dto.get("adduser"));
		po.setAddtime(TimeUtil.getNowTime());

		dao.saveEntity(po);
	}


	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List eventQuery(IBaseDTO dto, PageInfo pi) {
		
		List list = new ArrayList();
		EventHelp h = new EventHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.eventQuery(dto, pi));
		num = dao.findEntitySize(h.eventQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperEvent po = (OperEvent) result[i];
			list.add(eventToDynaBeanDTO(po));
		}
		return list;
	}
	/**
	 * ��ѯ�б����� po ת dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO eventToDynaBeanDTO(OperEvent po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("topic", po.getTopic());
		String contents = po.getContents();
		if(contents != null && contents.length() > 15){
			contents = contents.substring(0, 15) + "��";
		}
		dto.set("contents", contents);
		dto.set("principal", po.getPrincipal());
		dto.set("actor", po.getActor());
		dto.set("eventdate", TimeUtil.getTheTimeStr(po.getEventdate(), "yyyy-MM-dd"));

		return dto;
	}
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getEventSize() {
		return num;
	}
	
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getEventInfo(String id) {
		
		OperEvent po = (OperEvent)dao.loadEntity(OperEvent.class,id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("topic", po.getTopic());
		dto.set("contents", po.getContents());
		dto.set("principal", po.getPrincipal());
		dto.set("actor", po.getActor());
		dto.set("eventdate", TimeUtil.getTheTimeStr(po.getEventdate(), "yyyy-MM-dd"));
		
		Set set = po.getOperEventResults();
		Iterator iterator = set.iterator();
		List list = new ArrayList();
		while(iterator.hasNext()){
			OperEventResult po2 = (OperEventResult)iterator.next();
			DynaBeanDTO dto2 = new DynaBeanDTO();
			dto2.set("id", po2.getId());
			dto2.set("linkman", po2.getLinkman());
			dto2.set("feedback", po2.getFeedback());
			dto2.set("feedback_date", TimeUtil.getTheTimeStr(po2.getFeedbackDate(), "yyyy-MM-dd"));
			list.add(dto2);
		}
		dto.set("list", list);
		
		return dto;
		
	}
	
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateEvent(IBaseDTO dto) {
		
		OperEvent po = (OperEvent)dao.loadEntity(OperEvent.class,(String)dto.get("id"));//���������޸ı�
		
		po.setTopic((String)dto.get("topic"));
		po.setContents((String)dto.get("contents"));
		po.setEventdate(TimeUtil.getTimeByStr((String)dto.get("eventdate"), "yyyy-MM-dd"));
		po.setPrincipal((String)dto.get("principal"));
		po.setActor((String)dto.get("actor"));

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
	public void delEvent(String id) {
		
		OperEvent cq = (OperEvent)dao.loadEntity(OperEvent.class,id);
		dao.removeEntity(cq);
		
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

}
