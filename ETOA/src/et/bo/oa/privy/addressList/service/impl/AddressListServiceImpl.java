/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressList.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.privy.addressList.service.AddressListService;
import et.po.AddresslistInfo;
import et.po.EmployeeInfo;
import et.po.SysDepartment;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AddressListServiceImpl implements AddressListService {
    
	static Logger logger = Logger.getLogger(AddressListServiceImpl.class.getName());
	
	int num = 0;
	
    private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	public boolean addAddressListInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
//		boolean flag = false;
		try {
			dao.saveEntity(createAddressListInfo(dto));
			return true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		flag = true;
//		return flag; 
	}
	
	private AddresslistInfo createAddressListInfo (IBaseDTO dto){
		AddresslistInfo ai = new AddresslistInfo();
		ai.setId(ks.getNext("AddressList_Info"));
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
		ai.setSysUser(sys);
		ai.setSort(dto.get("sort").toString());
		ai.setName(dto.get("name").toString());
		ai.setAppellation(dto.get("appellation").toString());
		ai.setCompany(dto.get("company").toString());
		ai.setStation(dto.get("station").toString());
		ai.setPersonalEmail(dto.get("personalEmail").toString());
		ai.setBusinessEmail(dto.get("businessEmail").toString());
		ai.setOtherEmail(dto.get("otherEmail").toString());
		ai.setPersonalPhone(dto.get("personalPhone").toString());
		ai.setBusinessPhone(dto.get("businessPhone").toString());
		ai.setFax(dto.get("fax").toString());
		ai.setMobile(dto.get("mobile").toString());
		ai.setBeepPager(dto.get("beepPager").toString());
		ai.setOtherPhone(dto.get("otherPhone").toString());
		ai.setBusinessAddress(dto.get("businessAddress").toString());
		ai.setBusinessPost(dto.get("businessPost").toString());
		ai.setPersonalAddress(dto.get("personalAddress").toString());
		if(dto.get("birthday")==""){
		    ai.setBirthday(TimeUtil.getTimeByStr(""));
		}else{
			ai.setBirthday(TimeUtil.getTimeByStr(dto.get("birthday").toString(), "yyyy-MM-dd"));
		}
		ai.setPersonalPage(dto.get("personalPage").toString());
		ai.setCompanyPage(dto.get("companyPage").toString());
		ai.setRemark(dto.get("remark").toString());
		ai.setSign(dto.get("sign").toString());	
		return ai;
	}

	public boolean deleteAddressListInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		AddresslistInfo ai = (AddresslistInfo)dao.loadEntity(AddresslistInfo.class, dto.get("id").toString());
		dao.removeEntity(ai);
		flag = true;
		return flag;
	}

	public List findAddressListInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
//		System.out.println(sys.getUserId()+"++++++++++++++++++++++++");
		AddressListSearch addressListSearch = new AddressListSearch();
		Object[] result = (Object[]) dao.findEntity(addressListSearch.searchAddressListInfo(dto, pi ,sys));
		int s = dao.findEntitySize(addressListSearch.searchAddressListInfo(dto, pi ,sys));
		num = s;
		for(int i = 0,size = result.length;i<size;i++){
			AddresslistInfo ai = (AddresslistInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ai.getId());
			dbd.set("name", ai.getName());
			dbd.set("personalEmail", ai.getPersonalEmail());
			dbd.set("personalPhone", ai.getPersonalPhone());
			dbd.set("fax", ai.getFax());
			dbd.set("mobile", ai.getMobile());
			dbd.set("sign", ai.getSign());
//			System.out.println(dbd.get("sign"));
			l.add(dbd);
		}
		return l;
	}
	
	public List findCompanyAddressListInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		AddressListSearch addressListSearch = new AddressListSearch();
		Object[] result = (Object[]) dao.findEntity(addressListSearch.searchCompanyAddressListInfo(dto, pi));
		int s = dao.findEntitySize(addressListSearch.searchCompanyAddressListInfo(dto, pi));
		num = s;
		for(int i = 0,size = result.length;i<size;i++){
			EmployeeInfo ei = (EmployeeInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ei.getId());
			dbd.set("name", ei.getName());
			dbd.set("mobile", ei.getMobile());
			dbd.set("companyPhone", ei.getCompanyPhone());
//			dbd.set("fax", ei.getFax());
			dbd.set("department", getDepartName(ei.getDepartment()));
//			dbd.set("sign", ei.getSign());
			l.add(dbd);
		}
		return l;
	}

	public int getAddressListSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public IBaseDTO getAddressListInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		AddresslistInfo ai = (AddresslistInfo)dao.loadEntity(AddresslistInfo.class, id);
		dto.set("id", ai.getId());
		dto.set("sort", ai.getSort());
		dto.set("name", ai.getName());
		dto.set("appellation", ai.getAppellation());
		dto.set("company", ai.getCompany());
		dto.set("station", ai.getStation());
		dto.set("personalEmail", ai.getPersonalEmail());
		dto.set("businessEmail", ai.getBusinessEmail());
		dto.set("otherEmail", ai.getOtherEmail());
		dto.set("personalPhone", ai.getPersonalPhone());
		dto.set("businessPhone", ai.getBusinessPhone());
		dto.set("fax", ai.getFax());
		dto.set("mobile", ai.getMobile());
		dto.set("beepPage",ai.getBeepPager());
		dto.set("otherPhone", ai.getOtherPhone());
		dto.set("businessAddress", ai.getBusinessAddress());
		dto.set("businessPost", ai.getBusinessPost());
		dto.set("personalAddress", ai.getPersonalAddress());
		dto.set("personalPost", ai.getPersonalPost());		
		dto.set("birthday", TimeUtil.getTheTimeStr(ai.getBirthday()));
		dto.set("personalPage", ai.getPersonalPage());
		dto.set("companyPage", ai.getCompanyPage());
		dto.set("remark", ai.getRemark());
		dto.set("sign", ai.getSign());
		dto.set("companyPage", ai.getCompanyPage());
		dto.set("personalPage", ai.getPersonalPage());
		
		return dto;
	}

	public boolean updateAddressListInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(modifyAddressListInfo(dto));
		flag = true;
		return flag;
	}
	
	private AddresslistInfo modifyAddressListInfo(IBaseDTO dto){
		AddresslistInfo ai = new AddresslistInfo();
		ai.setId(dto.get("id").toString());
		SysUser sys = (SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
		ai.setSysUser(sys);
		ai.setSort(dto.get("sort").toString());
		ai.setName(dto.get("name").toString());
		ai.setAppellation(dto.get("appellation").toString());
		ai.setCompany(dto.get("company").toString());
		ai.setStation(dto.get("station").toString());
		ai.setPersonalEmail(dto.get("personalEmail").toString());
		ai.setBusinessEmail(dto.get("businessEmail").toString());
		ai.setOtherEmail(dto.get("otherEmail").toString());
		ai.setPersonalPhone(dto.get("personalPhone").toString());
		ai.setBusinessPhone(dto.get("businessPhone").toString());
		ai.setFax(dto.get("fax").toString());
		ai.setMobile(dto.get("mobile").toString());
		ai.setBeepPager(dto.get("beepPager").toString());
		ai.setOtherPhone(dto.get("otherPhone").toString());
		ai.setBusinessAddress(dto.get("businessAddress").toString());
		ai.setBusinessPost(dto.get("businessPost").toString());
		ai.setPersonalAddress(dto.get("personalAddress").toString());
        System.out.println("11111111111111111");
		if(dto.get("birthday")==""){
		    ai.setBirthday(TimeUtil.getTimeByStr(""));
		}else{
			ai.setBirthday(TimeUtil.getTimeByStr(dto.get("birthday").toString(), "yyyy-MM-dd"));
		}
		ai.setRemark(dto.get("remark").toString());
		ai.setSign(dto.get("sign").toString());	
		return ai;
	}
	
	/**
	 * <p> 获得部门名称 </p>
	 * 
	 * @param id
	 * @return
	 */
	private String getDepartName(String id) {
		String depart = "";
		DetachedCriteria dc = DetachedCriteria.forClass(SysDepartment.class);
		dc.add(Restrictions.eq("id",id));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);

		Object[] objs = this.dao.findEntity(myQuery);
		if (null != objs && 0 < objs.length) {
			SysDepartment dps = (SysDepartment) objs[0];
			depart = dps.getName();
		}
		return depart;
	}
	
	/**
	 * @describe  留给张风
	 * @param
	 * @return
	 * 
	 */
    public List getBoxList() {
        //TODO 需要写出方法的具体实现

        return null;
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
