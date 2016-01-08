package et.bo.sys.staff.staffHortation.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import et.bo.sys.staff.staffHortation.service.staffHortationService;
import et.po.StaffBasic;
import et.po.StaffHortation;

import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class staffHortationServiceImpl implements staffHortationService {

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

	public void addStaffHortation(IBaseDTO dto,String id) {
		// TODO Auto-generated method stub
		dao.saveEntity(createStaffHortation(dto,id));
	}

	
	private StaffHortation createStaffHortation(IBaseDTO dto,String  id) {
		StaffHortation sh = new StaffHortation();
		
		sh.setId(ks.getNext("staff_hortation"));
		StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class,id);
		sh.setStaffBasic(sb);
		sh.setHortationInfo(dto.get("hortationInfo").toString());
		sh.setHortationTime(dto.get("hortationTime")==""?null:TimeUtil.getTimeByStr(dto.get("hortationTime").toString(),"yyyy-MM-dd"));
		sh.setHortationType(dto.get("hortationType").toString());
		sh.setRemark(dto.get("remark").toString());
	
		return sh;
	}

	
	
	
	 public void delStaffHortation(String id) {
	 // TODO Auto-generated method stub
	 StaffHortation sh = (StaffHortation)dao.loadEntity(StaffHortation.class, id);
	 dao.removeEntity(sh);
	 }


	 
	public IBaseDTO getStaffHortationInfo(String id) {
	 // TODO Auto-generated method stub
	StaffHortation sh = (StaffHortation)dao.loadEntity(StaffHortation.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 
	 dto.set("id", id);
	 dto.set("hortationInfo", sh.getHortationInfo());
	 dto.set("hortationTime", TimeUtil.getTheTimeStr(sh.getHortationTime(),"yyyy-MM-dd"));
	 dto.set("hortationType", sh.getHortationType());
	 return dto;
	 }

	
	
	public int getStaffHortationSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateStaffHortation(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifyStaffHortation(dto));
	 return false;
	 }
		
	private StaffHortation modifyStaffHortation(IBaseDTO dto){
	
	StaffHortation sh = (StaffHortation)dao.loadEntity(StaffHortation.class, dto.get("id").toString());
	sh.setHortationInfo(dto.get("hortationInfo").toString());
	sh.setHortationTime(dto.get("hortationTime")==""?null:TimeUtil.getTimeByStr(dto.get("hortationTime").toString(),"yyyy-MM-dd"));
	sh.setHortationType(dto.get("hortationType").toString());
	sh.setRemark(dto.get("remark").toString());
	 return sh;
	 }

	public List StaffHortationQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		
		List list = new ArrayList();

		staffHortationHelp shh = new staffHortationHelp();
		Object[] result = (Object[]) dao.findEntity(shh.staffHortationQuery(dto, pi));
		num = dao.findEntitySize(shh.staffHortationQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			StaffHortation sh = (StaffHortation) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", sh.getId());
			dbd.set("hortationInfo", sh.getHortationInfo());
			dbd.set("hortationTime", TimeUtil.getTheTimeStr(sh.getHortationTime(),"yyyy-MM-dd"));
			dbd.set("hortationType", sh.getHortationType());
			list.add(dbd);
		}
		return list;
	}

	
}
