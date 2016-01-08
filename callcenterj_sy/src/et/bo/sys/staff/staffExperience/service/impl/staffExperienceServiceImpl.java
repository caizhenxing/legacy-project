package et.bo.sys.staff.staffExperience.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import et.bo.sys.staff.staffExperience.service.staffExperienceService;
import et.po.StaffBasic;
import et.po.StaffExperience;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class staffExperienceServiceImpl implements staffExperienceService {

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

	public void addStaffExperience(IBaseDTO dto,String id) {
		// TODO Auto-generated method stub
		dao.saveEntity(createStaffExperience(dto,id));
	}

	
	private StaffExperience createStaffExperience(IBaseDTO dto,String id) {
		StaffExperience se = new StaffExperience();
		
		

		se.setId(ks.getNext("staff_experience"));
		
		StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class, id);
		
		se.setStaffBasic(sb);
		se.setAddress(dto.get("address").toString());
		se.setBeginTime(dto.get("beginTime")==""?null:TimeUtil.getTimeByStr( dto.get("beginTime").toString(),"yyyy-MM-dd"));
		se.setEndTime(dto.get("endTime")==""?null:TimeUtil.getTimeByStr( dto.get("endTime").toString(),"yyyy-MM-dd"));
		se.setCompany(dto.get("company").toString());
		se.setDictExperienceType(dto.get("dictExperienceType").toString());
		se.setDuty(dto.get("duty").toString());
		se.setMessage(dto.get("message").toString());
		se.setProveMan(dto.get("proveMan").toString());
		
		
		return se;
	}

	
	
	
	 public void delStaffExperience(String id) {
	 // TODO Auto-generated method stub
		 StaffExperience u = (StaffExperience)dao.loadEntity(StaffExperience.class, id);
	 dao.removeEntity(u);
	 }


	 
	public IBaseDTO getStaffExperienceInfo(String id) {
	 // TODO Auto-generated method stub
	StaffExperience se = (StaffExperience)dao.loadEntity(StaffExperience.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("id", se.getId());
	 dto.set("address", se.getAddress());
	 dto.set("beginTime",TimeUtil.getTheTimeStr(se.getBeginTime(),"yyyy-MM-dd"));
	 dto.set("endTime", TimeUtil.getTheTimeStr(se.getEndTime(),"yyyy-MM-dd"));
	 dto.set("company", se.getCompany());
	 dto.set("dictExperienceType", se.getDictExperienceType());
	 dto.set("duty", se.getDuty());
	 dto.set("message", se.getMessage());
	 dto.set("proveMan", se.getProveMan());

	 return dto;
	 }

	
	
	public int getStaffExperienceSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateStaffExperience(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifyStaffExperience(dto));
	 return false;
	 }
		
	private StaffExperience modifyStaffExperience(IBaseDTO dto){

		StaffExperience se = (StaffExperience)dao.loadEntity(StaffExperience.class, dto.get("id").toString());
		se.setAddress(dto.get("address").toString());
		se.setBeginTime(dto.get("beginTime")==""?null:TimeUtil.getTimeByStr( dto.get("beginTime").toString(),"yyyy-MM-dd"));
		se.setEndTime(dto.get("endTime")==""?null:TimeUtil.getTimeByStr( dto.get("endTime").toString(),"yyyy-MM-dd"));
		se.setCompany(dto.get("company").toString());
		se.setDictExperienceType(dto.get("dictExperienceType").toString());
		se.setDuty(dto.get("duty").toString());
		se.setMessage(dto.get("message").toString());
		se.setProveMan(dto.get("proveMan").toString());
	 return se;
	 }

	public List staffExperienceQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		
		List list = new ArrayList();

		staffExperienceHelp seq = new staffExperienceHelp();
		Object[] result = (Object[]) dao.findEntity(seq.staffExperienceQuery(dto, pi));
		num = dao.findEntitySize(seq.staffExperienceQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			StaffExperience se = (StaffExperience) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			
			dbd.set("id", se.getId());
			dbd.set("address", se.getAddress());
			dbd.set("beginTime",TimeUtil.getTheTimeStr(se.getBeginTime(),"yyyy-MM-dd"));
			dbd.set("endTime", TimeUtil.getTheTimeStr(se.getEndTime(),"yyyy-MM-dd"));
			dbd.set("company", se.getCompany());
			
			if(se.getDictExperienceType().equals("work"))
			{
				dbd.set("dictExperienceType","工作" );
				
			}
			if(se.getDictExperienceType().equals("teach"))
			{
				
				dbd.set("dictExperienceType", "教育");
			}
			
			
			dbd.set("duty", se.getDuty());
			dbd.set("message", se.getMessage());
			dbd.set("proveMan", se.getProveMan());


			list.add(dbd);
		}
		return list;
	}



	
}
