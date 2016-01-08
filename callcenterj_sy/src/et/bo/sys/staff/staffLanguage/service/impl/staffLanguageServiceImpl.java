package et.bo.sys.staff.staffLanguage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import et.bo.sys.staff.staffLanguage.service.staffLanguageService;
import et.po.StaffBasic;
import et.po.StaffLanguage;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class staffLanguageServiceImpl implements staffLanguageService {

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

	public void addStaffLanguage(IBaseDTO dto,String id) {
		// TODO Auto-generated method stub
		dao.saveEntity(createStaffLanguage(dto,id));
	}

	
	private StaffLanguage createStaffLanguage(IBaseDTO dto,String id) {
		StaffLanguage sl = new StaffLanguage();

		sl.setId(ks.getNext("staff_language"));
		StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class,id);
		sl.setStaffBasic(sb);
		sl.setDictLanguageDegree(dto.get("dictLanguageDegree").toString());
		sl.setDictLanguageType(dto.get("dictLanguageType").toString());

		return sl;
	}

	
	
	
	 public void delStaffLanguage(String id) {
	 // TODO Auto-generated method stub
		 StaffLanguage sl = (StaffLanguage)dao.loadEntity(StaffLanguage.class, id);
		 dao.removeEntity(sl);
	 }


	 
	public IBaseDTO getStaffLanguageInfo(String id) {
	 // TODO Auto-generated method stub
		StaffLanguage sl = (StaffLanguage)dao.loadEntity(StaffLanguage.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("id", id);
	 dto.set("dictLanguageDegree", sl.getDictLanguageDegree());
	 dto.set("dictLanguageType", sl.getDictLanguageType());
	 return dto;
	 }

	
	
	public int getStaffLanguageSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	 public boolean updateStaffLanguage(IBaseDTO dto) {
	 // TODO Auto-generated method stub
	 dao.saveEntity(modifyStaffLanguage(dto));
	 return false;
	 }
		
	private StaffLanguage modifyStaffLanguage(IBaseDTO dto){

	StaffLanguage sl = (StaffLanguage)dao.loadEntity(StaffLanguage.class, dto.get("id").toString());
	sl.setDictLanguageDegree(dto.get("dictLanguageDegree").toString());
	sl.setDictLanguageType(dto.get("dictLanguageType").toString());
	
	 return sl;
	 }

	public List staffLanguageQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		
		List list = new ArrayList();

		staffLanguageHelp iah = new staffLanguageHelp();
		Object[] result = (Object[]) dao.findEntity(iah.StaffLanguageQuery(dto, pi));
		num = dao.findEntitySize(iah.StaffLanguageQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			StaffLanguage sl= (StaffLanguage) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("id", sl.getId());
			
			if(sl.getDictLanguageDegree().equals("3"))
			{
				dbd.set("dictLanguageDegree", "3¼¶");
			}
			if(sl.getDictLanguageDegree().equals("4"))
			{
				dbd.set("dictLanguageDegree", "4¼¶");
			}
			
			if(sl.getDictLanguageType().equals("english"))
			{
				dbd.set("dictLanguageType", "Ó¢Óï");
			}
			if(sl.getDictLanguageType().equals("japanese"))
			{
				dbd.set("dictLanguageType","ÈÕÓï");
			}
			
			
			
			list.add(dbd);
		}
		return list;
	}

	
}
