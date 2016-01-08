/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 * @author  jaray wei
 * 
 * @version 06/04/19
 * @since   1.0
 */
package et.bo.sys.group.service.impl;


import java.util.ArrayList;
import java.util.List;

import ocelot.common.key.KeyService;
import ocelot.common.page.PageInfo;
import ocelot.framework.base.dao.BaseDAO;
import ocelot.framework.base.dto.IBaseDTO;
import ocelot.framework.base.dto.impl.DynaBeanDTO;
import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;


import et.bo.common.ListValueService;
import et.bo.sys.group.service.GroupService;
import et.po.SysGroup;

public class GroupServiceImpl implements GroupService {

private BaseDAO dao=null;
	
	private KeyService ks = null;
	
	private ListValueService listValueService=null;
	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	private SysGroup createPoByDto(IBaseDTO dto)
	{
		SysGroup sgp =new SysGroup();
		sgp.setId(null !=dto.get("id") && !"".equals(dto.get("id"))?dto.get("id").toString():ks.getNext("SYS_GROUP"));
		sgp.setDelMark(null !=dto.get("freezeMark") && !"".equals(dto.get("freezeMark"))?dto.get("freezeMark").toString():"1");
		sgp.setName(null !=dto.get("name")?dto.get("name").toString():"");
		sgp.setRemark(null !=dto.get("remark")?dto.get("remark").toString():"");
		return sgp;
	}
	
	private IBaseDTO createDtoByPo(SysGroup sgp)
	{
		DynaBeanDTO dto =new DynaBeanDTO();
		dto.set("id",sgp.getId());
		dto.set("name",sgp.getName());
		dto.set("delMark",sgp.getDelMark());
		dto.set("remark",sgp.getRemark());
		
		return dto;
	}
	public void insertGroup(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		
//		try {
//			boolean flag =this.listValueService.findFByT(SysGroup.class,"name",(String)dto.get("name"),"t");
//			if(!flag)
//			{
//				SysGroup sgp =(SysGroup)createPoByDto(dto);
//				this.dao.saveEntity(sgp);
//			}
//			else
//			{
//				throw new AppException("");
//			}
//		} catch (AppException e) {
//			// TODO: handle exception
//			
//			
//		}
		
		boolean flag =this.listValueService.checkDup(SysGroup.class,"name",(String)dto.get("name"));
		if(!flag)
		{
			SysGroup sgp =(SysGroup)createPoByDto(dto);
			this.dao.saveEntity(sgp);
		}
		
		
	}

	public boolean exist(String groupId) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,groupId);
		if(null==sgp)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void deleteGroup(String id) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,id);
		if(null!=sgp)
		{			
			sgp.setDelMark("-1");
			System.out.println("id "+sgp.getId());
			System.out.println("name "+sgp.getName());
			System.out.println("remark "+sgp.getRemark());
			System.out.println("del mark "+sgp.getDelMark());
			this.dao.updateEntity(sgp);
		}
	}

	public void freezeGroup(String id) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,id);
		if(null!=sgp)
		{
			sgp.setDelMark("0");
			this.dao.updateEntity(sgp);
		}
	}

	public void thawGroup(String id) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,id);
		if(null!=sgp)
		{
			sgp.setDelMark("1");
			this.dao.updateEntity(sgp);
		}
	}

	public void updateGroup(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		this.dao.updateEntity(createPoByDto(dto));
	}

	public List<IBaseDTO> listGroup(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		GroupHelp gh =new GroupHelp();
		Object [] o =this.dao.findEntity(gh.listGroupMQ(dto,pi));
//		System.out.println("o length "+o.length);
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =createDtoByPo((SysGroup)oo);
				l.add(tdto);
			}
			return l;
		}
		return l;
	}

	public int listGroupSize(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		GroupHelp gh =new GroupHelp();
//		MyQuery mq =gh.listGroupMQ(dto);
		MyQuery mq =gh.listGroupMQ(dto,pi);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public IBaseDTO uniqueGroup(String id) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,id);
		if(null!=sgp)
		{
			IBaseDTO dto =createDtoByPo(sgp);
			return dto;
		}
		return null;
	}

	public Object uniqueGroupPo(String id) {
		//TODO 需要写出方法的具体实现
		SysGroup sgp =(SysGroup)this.dao.loadEntity(SysGroup.class,id);
		if(null!=sgp)
		{
			return sgp;
		}
		return null;
	}

	public void test()
	{
		MyQuery mq =new MyQueryImpl();
		String hql ="from SysGroup t";
		mq.setHql(hql);
		Object [] o =this.dao.findEntity(mq);
		System.out.println(o.length);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

	/**
	 * @return Returns the listValueService.
	 */
	public ListValueService getListValueService() {
		return listValueService;
	}

	/**
	 * @param listValueService The listValueService to set.
	 */
	public void setListValueService(ListValueService listValueService) {
		this.listValueService = listValueService;
	}

}
