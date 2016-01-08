package et.bo.oa.communicate.instant.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.communicate.instant.service.InstantMsg;
import et.bo.oa.communicate.instant.service.InstantMsgService;
import et.po.SysUser;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class InstantMsgServiceImpl implements InstantMsgService {

	private static ConcurrentMap<String, CopyOnWriteArrayList<InstantMsg>> msgs=new ConcurrentHashMap<String,CopyOnWriteArrayList<InstantMsg>>();
	private BaseDAO dao=null;
	public void sendMsg(List<String> receivers, String content, String sendUser) {
		// TODO Auto-generated method stub
		Iterator<String> i=receivers.iterator();
		while(i.hasNext())
		{
		String dest=i.next();
		InstantMsg im=new InstantMsg();
		im.setContents(content);
		im.setReceiveUser(dest);
		im.setSendTime(TimeUtil.getNowTime());
		im.setSendUser(sendUser);
		if(msgs.containsKey(dest))
		{
			CopyOnWriteArrayList<InstantMsg> results=msgs.get(dest);
			results.addIfAbsent(im);
			
		}
		CopyOnWriteArrayList<InstantMsg> cwal=new CopyOnWriteArrayList<InstantMsg>();
		cwal.addIfAbsent(im);
		msgs.putIfAbsent(dest,cwal);
		}
	}

	public List<InstantMsg> receiveMsgs(String receiveUser, boolean clean) {
		// TODO Auto-generated method stub
		List<InstantMsg> results=null;
		if(msgs.containsKey(receiveUser))
	{
		CopyOnWriteArrayList<InstantMsg> cl=msgs.get(receiveUser);
		results=new ArrayList<InstantMsg>(cl);
		if(clean)
		cl.clear();
		if(results==null)
			results=new ArrayList<InstantMsg>();
		return results;
	}
		return null;
	}
	public static void main(String[] arg0)
	{
		InstantMsgServiceImpl ims=new InstantMsgServiceImpl();
		List<String> s=new ArrayList<String>();
		s.add("a");
		s.add("b");
		s.add("c");
		ims.sendMsg(s,"aaaaa","zhao");
		ims.sendMsg(s,"bbbbb","zhao");
		ims.sendMsg(s,"ccccc","zhao");
		List<InstantMsg> l=ims.receiveMsgs("a",false);
		for(InstantMsg im:l)
		{
			
		}
		
	}

	public boolean hasMsg(String receiveUser) {
		// TODO Auto-generated method stub
		if(receiveUser==null)
			return false;
		if(!msgs.containsKey(receiveUser))
			return false;
		CopyOnWriteArrayList<InstantMsg> cl=msgs.get(receiveUser);
		if(cl==null)
			return false;
		if(cl.size()==0)
			return false;
		return true;
	}

	public IBaseDTO receiveMsg(String receiveUser, boolean clean) {
		// TODO Auto-generated method stub
		InstantMsg im=null;
		if(msgs.containsKey(receiveUser))
	{
		CopyOnWriteArrayList<InstantMsg> cl=msgs.get(receiveUser);
		
		InstantMsg temp=cl.get(0);
		im=new InstantMsg(temp);
		if(clean)
			cl.remove(temp);
	}
		IBaseDTO dto=new DynaBeanDTO();
		dto.set("sender",im.getSendUser());
		dto.set("sendTime",TimeUtil.getTheTimeStr(im.getSendTime()));
		dto.set("contents",im.getContents());
		return dto;
	}

	public List<IBaseDTO> userList() {
		// TODO Auto-generated method stub
		List<IBaseDTO> result=new CopyOnWriteArrayList<IBaseDTO>();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.ne("deleteMark","-1"));
		mq.setDetachedCriteria(dc);
		Object[] re=dao.findEntity(mq);
		for(int i=0,size=re.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			SysUser su=(SysUser)re[i];
			dto.set("id",su.getUserId());
			dto.set("name",su.getUserName());
			result.add(dto);
		}
		return result;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
