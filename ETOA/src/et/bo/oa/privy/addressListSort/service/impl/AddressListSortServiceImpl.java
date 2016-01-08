package et.bo.oa.privy.addressListSort.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.oa.privy.addressListSort.service.AddressListSortService;
import et.po.AddresslistInfo;
import et.po.AddresslistsortInfo;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class AddressListSortServiceImpl implements AddressListSortService {

    int num = 0;
	
    private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	public boolean addAddressListSortInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(createAddressListSortInfo(dto));
		flag = true;
		return flag;
	}
	
	private AddresslistsortInfo createAddressListSortInfo(IBaseDTO dto){
		AddresslistsortInfo ai = new AddresslistsortInfo();
		ai.setId(ks.getNext("AddressListSort_Info"));
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		ai.setSysUser(sys);
		ai.setSortName(dto.get("sortName").toString());
		ai.setSortExplain(dto.get("sortExplain").toString());
		ai.setSortMark(dto.get("sortMark").toString());
		return ai;
	}

	public boolean deleteAddressListSortInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		AddresslistsortInfo ai = (AddresslistsortInfo)dao.loadEntity(AddresslistsortInfo.class, dto.get("id").toString());		
		//是否可以删除
		if(ifCanDelete(ai.getId())==flag){
			return flag;
		}
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
	private boolean ifCanDelete(String id){
		AddressListSortSearch addressListSortSearch = new AddressListSortSearch();
		int s = dao.findEntitySize(addressListSortSearch.searchAddressListIsHavaSort(id));
		if(s>0){
			return false;
		}else{
			return true;	
		}
	}

	public List findAddressListSortInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		AddressListSortSearch addressListSortSearch = new AddressListSortSearch();
		Object[] result = (Object[]) dao.findEntity(addressListSortSearch.searchAddressListSortInfo(dto, pi, sys));
		int s = dao.findEntitySize(addressListSortSearch.searchAddressListSortInfo(dto, pi, sys));
		num = s;
		for(int i = 0,size = result.length;i<size;i++){
			AddresslistsortInfo ai = (AddresslistsortInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
		    dbd.set("id", ai.getId());
		    dbd.set("sortName", ai.getSortName());
		    dbd.set("sortExplain", ai.getSortExplain());
		    dbd.set("sortMark", ai.getSortMark());
			l.add(dbd);
		}
		return l;
		
	}

	public IBaseDTO getAddressListSortInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		AddresslistsortInfo ai = (AddresslistsortInfo)dao.loadEntity(AddresslistsortInfo.class, id);
		dto.set("id", ai.getId());
		dto.set("sortName", ai.getSortName());
		dto.set("sortExplain", ai.getSortExplain());
		dto.set("sortMark", ai.getSortMark());
		return dto;
	}

	public int getAddressListSortSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean updateAddressListSortInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(modifyAddressListSortInfo(dto));
		flag = true;
		return flag;
	}
	
	private AddresslistsortInfo modifyAddressListSortInfo(IBaseDTO dto){
		AddresslistsortInfo ai = new AddresslistsortInfo();
		ai.setId(dto.get("id").toString());
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
		ai.setSysUser(sys);
		ai.setSortName(dto.get("sortName").toString());
		ai.setSortExplain(dto.get("sortExplain").toString());
		ai.setSortMark(dto.get("sortMark").toString());
		return ai;
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
	
	public List<LabelValueBean> getLabelList(String userId, String sign) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		AddressListSortSearch addressListSortSearch = new AddressListSortSearch();
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class, userId);
		Object[] result = (Object[]) dao.findEntity(addressListSortSearch.searchLabelList(sys,sign));
		for(int i = 0,size = result.length;i<size;i++){
			AddresslistsortInfo ai = (AddresslistsortInfo) result[i];
			l.add(new LabelValueBean(ai.getSortName(),ai.getId()));
		}
        return l;
	}
	
	public String getSortNameById (String Id){
		AddresslistsortInfo ai = (AddresslistsortInfo)dao.loadEntity(AddresslistsortInfo.class, Id);
		return ai.getSortName();
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
