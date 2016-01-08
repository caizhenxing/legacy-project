package et.bo.sys.staff.staffParentInfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import et.bo.sys.staff.staffParentInfo.service.staffParentInfoService;
import et.po.StaffBasic;
import et.po.StaffParent;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class staffParentInfoImpl implements staffParentInfoService {

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

	public void addStaffParentInfo(IBaseDTO dto,String id) {
		// TODO Auto-generated method stub
		dao.saveEntity(createStaffParentInfo(dto,id));
	}

	
	private StaffParent createStaffParentInfo(IBaseDTO dto,String id) {
		StaffParent sp = new StaffParent();
		
		

		sp.setId(ks.getNext("staff_parent"));
		
		StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class, id);
		sp.setStaffBasic(sb);
		sp.setDictParentPolity(dto.get("dictParentPolity").toString());
		sp.setLinkTel(dto.get("linkTel").toString());
		sp.setParentConnection(dto.get("parentConnection").toString());
		sp.setParentName(dto.get("parentName").toString());
		sp.setRemark(dto.get("remark").toString());
		sp.setWork(dto.get("work").toString());

		return sp;
	}

	
	
	
	 public void delStaffParentInfo(String id) {
	 // TODO Auto-generated method stub
		 StaffParent sp = (StaffParent)dao.loadEntity(StaffParent.class, id);
		 dao.removeEntity(sp);
	 }


	 
	public IBaseDTO getStaffParentInfoInfo(String id) {
	 // TODO Auto-generated method stub
	 StaffParent sp = (StaffParent)dao.loadEntity(StaffParent.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("id", id);
	 
	 dto.set("dictParentPolity", sp.getDictParentPolity());
	 dto.set("linkTel", sp.getLinkTel());
	 dto.set("parentConnection", sp.getParentConnection());
	 dto.set("parentName", sp.getParentName());
	 dto.set("work", sp.getWork());
	 dto.set("remark", sp.getRemark());

	 return dto;
	 }

	
	
	public int getStaffParentInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateStaffParentInfo(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifyStaffParentInfo(dto));
	 return false;
	 }
		
	private StaffParent modifyStaffParentInfo(IBaseDTO dto){

		StaffParent sp = (StaffParent)dao.loadEntity(StaffParent.class, dto.get("id").toString());
		sp.setDictParentPolity(dto.get("dictParentPolity").toString());
		sp.setLinkTel(dto.get("linkTel").toString());
		sp.setParentConnection(dto.get("parentConnection").toString());
		sp.setParentName(dto.get("parentName").toString());
		sp.setRemark(dto.get("remark").toString());
		sp.setWork(dto.get("work").toString());

	 return sp;
	 }

	public List staffParentInfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		
		List list = new ArrayList();

		staffParentInfoHelp spih = new staffParentInfoHelp();
		Object[] result = (Object[]) dao.findEntity(spih.staffParentInfoQuery(dto, pi));
		num = dao.findEntitySize(spih.staffParentInfoQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			StaffParent sp = (StaffParent) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			 dbd.set("id", sp.getId());
			 dbd.set("dictParentPolity", sp.getDictParentPolity());
			 dbd.set("linkTel", sp.getLinkTel());
			 dbd.set("parentConnection", sp.getParentConnection());
			 dbd.set("parentName", sp.getParentName());
			 dbd.set("work", sp.getWork());
			 dbd.set("remark", sp.getRemark());
			list.add(dbd);
		}
		return list;
	}

	
}
