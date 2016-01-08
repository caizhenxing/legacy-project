

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
 * <p>客户管理</p>
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
	 * 添加数据。
	 * 向数据库中添加一条记录。
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
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
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
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
	 * 查询列表方法的 po 转 dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO eventToDynaBeanDTO(OperEvent po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("topic", po.getTopic());
		String contents = po.getContents();
		if(contents != null && contents.length() > 15){
			contents = contents.substring(0, 15) + "…";
		}
		dto.set("contents", contents);
		dto.set("principal", po.getPrincipal());
		dto.set("actor", po.getActor());
		dto.set("eventdate", TimeUtil.getTheTimeStr(po.getEventdate(), "yyyy-MM-dd"));

		return dto;
	}
	/**
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getEventSize() {
		return num;
	}
	
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
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
	 * 修改数据。
	 * 修改某条记录的内容。
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateEvent(IBaseDTO dto) {
		
		OperEvent po = (OperEvent)dao.loadEntity(OperEvent.class,(String)dto.get("id"));//根据主键修改表
		
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
	 * 删除数据。
	 * 删除某条记录。
	 * @param id 要删除数据的标识
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
