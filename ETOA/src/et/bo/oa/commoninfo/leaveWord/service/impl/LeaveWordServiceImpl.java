package et.bo.oa.commoninfo.leaveWord.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.oa.commoninfo.leaveWord.service.LeaveWordService;
import et.bo.oa.privy.addressListSort.service.impl.AddressListSortSearch;
import et.po.AddresslistsortInfo;
import et.po.LeavewordInfo;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class LeaveWordServiceImpl implements LeaveWordService {

    int num = 0;
	
    private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	public boolean addLeaveWordInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(createLeaveWordInfo(dto));
		flag = true;
		return flag;
	}
	
	private LeavewordInfo createLeaveWordInfo(IBaseDTO dto){
		LeavewordInfo li = new LeavewordInfo();
		li.setId(ks.getNext("Leaveword_Info"));
		//SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		//ai.setSysUser(sys);
		li.setUserId(dto.get("userId").toString());
		li.setName(dto.get("name").toString());
		li.setLeaveDate(TimeUtil.getNowTime());
		li.setTitle(dto.get("title").toString());
		li.setContent(dto.get("content").toString());
		return li;
	}

	public boolean deleteLeaveWordInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		LeavewordInfo ai = (LeavewordInfo)dao.loadEntity(LeavewordInfo.class, dto.get("id").toString());		
//		if(ifCanDelete(ai.getId())==flag){
//			return flag;
//		}
		dao.removeEntity(ai);
		flag = true;
		return flag;
	}
	/**
	 * @describe 种类是否可以删除
	 * @param
	 * @return   boolean
	 * 
	 */
//	private boolean ifCanDelete(String id){
//		LeaveWordInfoSearch addressListSortSearch = new LeaveWordInfoSearch();
//		int s = dao.findEntitySize(addressListSortSearch.searchAddressListIsHavaSort(id));
//		if(s>0){
//			return false;
//		}else{
//			return true;	
//		}
//	}

	public List findLeaveWordInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
//		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		LeaveWordSearch leaveWordSearch = new LeaveWordSearch();
		Object[] result = (Object[]) dao.findEntity(leaveWordSearch.searchLeaveWordInfo(dto, pi));
		int s = dao.findEntitySize(leaveWordSearch.searchLeaveWordInfo(dto, pi));
		num = s;
		for(int i = 0,size = result.length;i<size;i++){
			LeavewordInfo li = (LeavewordInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
		    dbd.set("id", li.getId());
		    dbd.set("name", li.getName());
		    dbd.set("leaveDate", TimeUtil.getTheTimeStr(li.getLeaveDate(), "yyyy-MM-dd HH:mm:ss"));
		    dbd.set("title", li.getTitle());
			l.add(dbd);
		}
		return l;
		
	}
	
	public List findSeeLeaveWordInfo(IBaseDTO dto,PageInfo pi){
//		 TODO Auto-generated method stub
		List l = new ArrayList();
		System.out.println("/findSee");
//		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		LeaveWordSearch leaveWordSearch = new LeaveWordSearch();
		Object[] result = (Object[]) dao.findEntity(leaveWordSearch.searchSeeLeaveWordInfo(dto, pi));
		int s = dao.findEntitySize(leaveWordSearch.searchSeeLeaveWordInfo(dto, pi));
		num = s;
		
		for(int i = 0,size = result.length;i<size;i++){
			LeavewordInfo li = (LeavewordInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
		    dbd.set("id", li.getId());
		    dbd.set("name", li.getName());
		    dbd.set("leaveDate", TimeUtil.getTheTimeStr(li.getLeaveDate(), "yyyy-MM-dd HH:mm:ss"));
		    dbd.set("title", li.getTitle());
		    dbd.set("content", li.getContent());
			l.add(dbd);
		}
		return l;
	}

	public IBaseDTO getLeaveWordInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		LeavewordInfo li = (LeavewordInfo)dao.loadEntity(LeavewordInfo.class, id);
		dto.set("id", li.getId());
		dto.set("name", li.getName());
		dto.set("leaveDate", TimeUtil.getTheTimeStr(li.getLeaveDate(), "yyyy-MM-dd HH:mm:ss"));
		dto.set("title", li.getTitle());
		dto.set("content",li.getContent().toString());
		return dto;
	}

	public int getLeaveWordSize() {
		// TODO Auto-generated method stub
		return num;
	}

//	public boolean updateAddressListSortInfo(IBaseDTO dto) {
//		// TODO Auto-generated method stub
//		boolean flag = false;
//		dao.saveEntity(modifyLeaveWordInfo(dto));
//		flag = true;
//		return flag;
//	}
	
	private LeavewordInfo modifyLeaveWordInfo(IBaseDTO dto){
		LeavewordInfo li = new LeavewordInfo();
		li.setId(dto.get("id").toString());
//		SysUser sys = (SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
//		li.setSysUser(sys);
		li.setUserId(dto.get("userId").toString());
		li.setName(dto.get("name").toString());
		li.setLeaveDate(TimeUtil.getNowTime());
		li.setTitle(dto.get("title").toString());
		li.setContent(dto.get("content").toString());
		return li;
	}
	
	public boolean isHaveSameName(IBaseDTO dto) {
		// TODO Auto-generated method stub
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		AddressListSortSearch addressListSortSearch = new AddressListSortSearch();
		int s = dao.findEntitySize(addressListSortSearch.searchIsHaveSameNameInfo(dto,sys));
		if(s>0){
			return true;
		}else{
			return false;	
		}
		
	}
	
//	public List<LabelValueBean> getLabelList(String userId, String sign) {
//		// TODO Auto-generated method stub
//		List l = new ArrayList();
//		AddressListSortSearch addressListSortSearch = new AddressListSortSearch();
//		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, userId);
//		Object[] result = (Object[]) dao.findEntity(addressListSortSearch.searchLabelList(sys,sign));
//		for(int i = 0,size = result.length;i<size;i++){
//			AddresslistsortInfo ai = (AddresslistsortInfo) result[i];
//			l.add(new LabelValueBean(ai.getSortName(),ai.getId()));
//		}
//        return l;
//	}
	
//	public String getSortNameById (String Id){
//		AddresslistsortInfo ai = (AddresslistsortInfo)dao.loadEntity(AddresslistsortInfo.class, Id);
//		return ai.getSortName();
//	}

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
