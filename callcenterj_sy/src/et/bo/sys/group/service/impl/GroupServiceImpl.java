package et.bo.sys.group.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.group.service.GroupService;
import et.po.SysGroup;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class GroupServiceImpl implements GroupService {
	
	int num = 0;
	
	private BaseDAO dao=null;
    
    private KeyService ks = null;
    
	/**
	 * 
	 * �������Ϣ
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean addGroupInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag=false;
		GroupSearch groupSearch = new GroupSearch();
		int result = dao.findEntitySize(groupSearch.seachGroupExist(dto));       
		if(result!=0){
			return flag;
		}
        dao.saveEntity(createGroupInfo(dto));
		flag=true;
		return flag;
	}
	
	private SysGroup createGroupInfo(IBaseDTO dto){
		SysGroup sysGroup=new SysGroup();
		sysGroup.setId(ks.getNext("SYS_Group"));
		sysGroup.setName(dto.get("name")==null?"":dto.get("name").toString());
//		sysGroup.setDelMark(dto.get("delMark")==null?"":dto.get("delMark").toString());
		sysGroup.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
//		sysGroup.setIsSys("0");
		sysGroup.setIsSys(dto.get("isSys").toString());
		sysGroup.setDelMark(GroupService.NOT_DEL_MARK);
		return sysGroup;
	}
	
	/**
	 * 
	 * �޸�����Ϣ
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean updateGroupInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(updateSysGroupInfo(dto));
		flag = true;
		return flag;
	}
	
	private SysGroup updateSysGroupInfo(IBaseDTO dto){
		SysGroup sysGroup = (SysGroup)dao.loadEntity(SysGroup.class, dto.get("id").toString());
		sysGroup.setName(dto.get("name")==null?"":dto.get("name").toString());
//		sysGroup.setDelMark(dto.get("delMark")==null?"":dto.get("delMark").toString());
		sysGroup.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		sysGroup.setIsSys(dto.get("isSys").toString());
		sysGroup.setDelMark(GroupService.NOT_DEL_MARK);
		return sysGroup;
	}
	
	/**
	 * 
	 * ɾ������Ϣ
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public boolean deleteGroupInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SysGroup sysGroup = (SysGroup)dao.loadEntity(SysGroup.class,dto.get("id").toString());
//		if(sysGroup.getIsSys().equals("1")){
//			return flag;
//		}
//		sysGroup.setDelMark("y".toUpperCase());
		sysGroup.setDelMark(GroupService.DEL_MARK);
		dao.updateEntity(sysGroup);
		flag = true;
		return flag;
	}
		
	/**
	 * 
	 * �õ���ʾ����
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public int getGroupSize() {
		// TODO Auto-generated method stub
	   return num;
	}
	 /**
	 * 
	 * ����������ѯ����Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public List findGroupInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		GroupSearch groupSearch = new GroupSearch();

		Object[] result = (Object[])dao.findEntity(groupSearch.searchGroupOperInfo(dto,pi));
		int s = dao.findEntitySize(groupSearch.searchGroupOperInfo(dto,pi));
		num = s;
	
		for(int i=0,size=result.length;i<size;i++){
			SysGroup sysGroup = (SysGroup)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("id",sysGroup.getId());
			dbd.set("name",sysGroup.getName());
			dbd.set("isSys",sysGroup.getIsSys());
			dbd.set("remark",sysGroup.getRemark());
			
			l.add(dbd);
		}
		return l;
	}
	
	/**
	 * 
	 * ͨ��ID��ʾ������Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public IBaseDTO getGroupInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		SysGroup sysGroup = (SysGroup)dao.loadEntity(SysGroup.class,id);
		dto.set("id",sysGroup.getId());
		dto.set("name",sysGroup.getName());
		dto.set("delMark",sysGroup.getDelMark());
		dto.set("remark",sysGroup.getRemark());
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
