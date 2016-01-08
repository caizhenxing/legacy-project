package et.bo.sys.staff.workUnitInfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import et.bo.sys.staff.workUnitInfo.service.workUnitInfoService;
import et.po.WorkUnitInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class workUnitInfoServiceImpl implements workUnitInfoService {

	// static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	private BaseDAO dao = null;

	private KeyService ks = null;
	

	private int num = 0;

	
	public static HashMap hashmap = new HashMap();

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

	public void addWorkUnitInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createWorkUnitInfo(dto));
	}

	
	private WorkUnitInfo createWorkUnitInfo(IBaseDTO dto) {
		WorkUnitInfo wui = new WorkUnitInfo();
		
		

		wui.setId(ks.getNext("work_unit_info"));

		wui.setCompanyAddress(dto.get("companyAddress").toString());
		wui.setCompanyName(dto.get("companyName").toString());
		wui.setCompanyTel(dto.get("companyTel").toString());
		wui.setDictCompanyType(dto.get("dictCompanyType").toString());
		wui.setFax(dto.get("fax").toString());
		wui.setHelpsign(dto.get("helpsign").toString());
		wui.setHomepage(dto.get("homepage").toString());
		wui.setPost(dto.get("post").toString());
		wui.setRemark(dto.get("remark").toString());

		return wui;
	}

	
	
	
	 public void delWorkUnitInfo(String id) {
	 // TODO Auto-generated method stub
		 WorkUnitInfo wui = (WorkUnitInfo)dao.loadEntity(WorkUnitInfo.class, id);
		 dao.removeEntity(wui);
	 }


	 
	public IBaseDTO getWorkUnitInfoInfo(String id) {
	 // TODO Auto-generated method stub
		WorkUnitInfo wui = (WorkUnitInfo)dao.loadEntity(WorkUnitInfo.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("id", id);
	 dto.set("companyAddress", wui.getCompanyAddress());
	 dto.set("companyName", wui.getCompanyName());
	 dto.set("companyTel", wui.getCompanyTel());
	 
	 dto.set("dictCompanyType", wui.getDictCompanyType());
	 dto.set("fax", wui.getFax());
	 dto.set("helpsign", wui.getHelpsign());
	 dto.set("homepage", wui.getHomepage());
	 dto.set("post", wui.getPost());
	 dto.set("remark", wui.getRemark());
	
	 return dto;
	 }

	
	
	public int getWorkUnitInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}


	 public boolean updateWorkUnitInfo(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifyWorkUnitInfo(dto));
	 return false;
	 }
		
	private WorkUnitInfo modifyWorkUnitInfo(IBaseDTO dto){
		WorkUnitInfo wui = (WorkUnitInfo)dao.loadEntity(WorkUnitInfo.class, dto.get("id").toString());
		wui.setCompanyAddress(dto.get("companyAddress").toString());
		wui.setCompanyName(dto.get("companyName").toString());
		wui.setCompanyTel(dto.get("companyTel").toString());
		wui.setDictCompanyType(dto.get("dictCompanyType").toString());
		wui.setFax(dto.get("fax").toString());
		wui.setHelpsign(dto.get("helpsign").toString());
		wui.setHomepage(dto.get("homepage").toString());
		wui.setPost(dto.get("post").toString());
		wui.setRemark(dto.get("remark").toString());
	 return wui;
	 }

	public List workUnitInfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		workUnitInfoHelp iah = new workUnitInfoHelp();
		Object[] result = (Object[]) dao.findEntity(iah.workUnitInfo(dto, pi));
		num = dao.findEntitySize(iah.workUnitInfo(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			WorkUnitInfo wui = (WorkUnitInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
		
			 dbd.set("id", wui.getId());
			 dbd.set("companyAddress", wui.getCompanyAddress());
			 dbd.set("companyName", wui.getCompanyName());
			 dbd.set("companyTel", wui.getCompanyTel());
			 
			 dbd.set("dictCompanyType", wui.getDictCompanyType());
			 dbd.set("fax", wui.getFax());
			 dbd.set("helpsign", wui.getHelpsign());
			 dbd.set("homepage", wui.getHomepage());
			 dbd.set("post", wui.getPost());
			 dbd.set("remark", wui.getRemark());

			list.add(dbd);
		}
		return list;
	}


	
}
