package et.bo.sys.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.role.service.RoleService;
import et.po.SysRole;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class RoleServiceImpl implements RoleService {
	
	int num = 0;
	
	private BaseDAO dao=null;
    
    private KeyService ks = null;
	/**
	 * 
	 * 添加角色信息
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean addRoleInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag=false;
		RoleSearch roleSearch = new RoleSearch();
		int result = dao.findEntitySize(roleSearch.seachRoleExist(dto));       
		if(result!=0)
		{
			return flag;
		}
        dao.saveEntity(createRoleInfo(dto));
		flag=true;
		return flag;
	}
	private SysRole createRoleInfo(IBaseDTO dto){
		SysRole sysRole=new SysRole();
		sysRole.setId(ks.getNext("SYS_ROLE"));
		sysRole.setName(dto.get("name")==null?"":dto.get("name").toString());
		
		sysRole.setDeleteMark(dto.get("delMark")==null?"":dto.get("delMark").toString());
		sysRole.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		sysRole.setIsSys("0");
		return sysRole;
	}
	/**
	 * 
	 * 修改角色信息
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean updateRoleInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(updateSysRoleInfo(dto));
		flag = true;
		return flag;
	}
	
	private SysRole updateSysRoleInfo(IBaseDTO dto){
		SysRole sysRole = (SysRole)dao.loadEntity(SysRole.class, dto.get("id").toString());
		sysRole.setName(dto.get("name")==null?"":dto.get("name").toString());
		sysRole.setDeleteMark(dto.get("delMark")==null?"":dto.get("delMark").toString());
		sysRole.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		return sysRole;
	}
	/**
	 * 
	 * 删除角色信息
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean deleteRoleInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SysRole sysRole = (SysRole)dao.loadEntity(SysRole.class,dto.get("id").toString());
		if(sysRole.getIsSys().equals("1")){
			return flag;
		}
		sysRole.setDeleteMark("y".toUpperCase());
		dao.updateEntity(sysRole);
		flag = true;
		return flag;
	}
	/**
	 * 
	 * 得到显示条数
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public int getRoleSize() {
		// TODO Auto-generated method stub
	   return num;
	}
	 /**
	 * 
	 * 根据条件查询角色信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public List findRoleInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		RoleSearch roleSearch = new RoleSearch();

		Object[] result = (Object[])dao.findEntity(roleSearch.searchRoleOperInfo(dto,pi));
		int s = dao.findEntitySize(roleSearch.searchRoleOperInfo(dto,pi));
		num = s;
	
		for(int i=0,size=result.length;i<size;i++){
			SysRole sysRole = (SysRole)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("id",sysRole.getId());
			dbd.set("name",sysRole.getName());
			dbd.set("delMark",sysRole.getDeleteMark());
			dbd.set("remark",sysRole.getRemark());
			l.add(dbd);
		}
		return l;
	}
	/**
	 * 
	 * 通过ID显示所有信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public IBaseDTO getRoleInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		SysRole sysRole = (SysRole)dao.loadEntity(SysRole.class,id);
		dto.set("id",sysRole.getId());
		dto.set("name",sysRole.getName());
		dto.set("delMark",sysRole.getDeleteMark());
		dto.set("remark",sysRole.getRemark());
		return dto;
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

}
