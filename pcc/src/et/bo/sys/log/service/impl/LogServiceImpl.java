package et.bo.sys.log.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ocelot.common.key.KeyService;
import ocelot.common.page.PageInfo;
import ocelot.common.util.time.TimeUtil;
import ocelot.framework.base.dao.BaseDAO;
import ocelot.framework.base.dto.IBaseDTO;
import ocelot.framework.base.dto.impl.DynaBeanDTO;
import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.sys.log.service.LogService;
import et.po.SysLog;
import et.po.SysModule;
import et.po.SysUser;

public class LogServiceImpl implements LogService {

private BaseDAO dao=null;
	
	private KeyService ks = null;
	
	private IBaseDTO createDtoByPo(SysLog sl)
	{
		DynaBeanDTO dto =new DynaBeanDTO();
		dto.set("id",sl.getId());
		dto.set("sysUser",sl.getSysUser().getUserId());		
		dto.set("sysModule",sl.getModu());
		dto.set("dt",null !=sl.getDt()?TimeUtil.getTheTimeStr(sl.getDt(),"yyyy-MM-dd"):"0000-00-00");
		dto.set("actorType",sl.getActorType());
		dto.set("remark",sl.getRemark());
		dto.set("ip",sl.getIp());
		return dto;
	}
	

	public List<IBaseDTO> listLog(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		LogHelp lh =new LogHelp();
		Object [] o =this.dao.findEntity(lh.listLogMQ(dto,pi));
//		System.out.println("o length "+o.length);
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =createDtoByPo((SysLog)oo);
				l.add(tdto);
//				System.out.println(l.size());
			}
			return l;
		}
		return l;
	}

	public int listLogSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		LogHelp lh =new LogHelp();
		MyQuery mq =lh.listLogMQ(dto);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public void deleteLog(String id) {
		// TODO Auto-generated method stub
		
	}

	public void deleteLog(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}

	public void test()
	{
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysLog.class);
		dc.add(Expression.ge("dt",TimeUtil.getTimeByStr("2000-01-01","yyyy-MM-dd")));
		SysUser su =new SysUser();
		su.setUserId("1");
		dc.add(Expression.eq("sysUser",su));
		mq.setDetachedCriteria(dc);		
		Object [] o =this.dao.findEntity(mq);
		if(null !=o)
		{
			System.out.println(o.length);
		}
		
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


	public void addLog(List logs) {
		// TODO Auto-generated method stub
		
		Iterator<IBaseDTO> i=logs.iterator();
		while(i.hasNext())
		{
			IBaseDTO dto=i.next();
			SysLog sl=new SysLog();
			dto.loadValue(sl);
			sl.setId(ks.getNext("SYS_LOG"));
			//sl.setDt(TimeUtil.getNowTime());
			sl.setSysUser((SysUser)dao.loadEntity(SysUser.class,(String)dto.get("user")));
			
			dao.saveEntity(sl);
		}
		dao.flush();
	}

}
