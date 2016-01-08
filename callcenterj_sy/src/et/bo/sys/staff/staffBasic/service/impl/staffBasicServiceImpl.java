package et.bo.sys.staff.staffBasic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;




import et.bo.sys.staff.staffBasic.service.staffBasicService;
import et.po.StaffBasic;
import et.po.StaffOtherInfo;
import et.po.WorkUnitInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class staffBasicServiceImpl implements staffBasicService {

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

	public void addStaffBasic(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
		String id = ks.getNext("staff_basic");
		
		dao.saveEntity(createStaffBasic(dto,id));
		dao.saveEntity(createStaffOtherInfo(dto,id));
	}

	
	private StaffBasic createStaffBasic(IBaseDTO dto,String id) {
		StaffBasic sb = new StaffBasic();
		
		
		
		sb.setStaffId(id);
		
		if(!dto.get("BBirthday").equals(""))
		{		
			sb.setBBirthday(TimeUtil.getTimeByStr(dto.get("BBirthday").toString(),"yyyy-MM-dd"));
		}
		WorkUnitInfo wui = (WorkUnitInfo)dao.loadEntity(WorkUnitInfo.class, dto.get("companyName").toString());

		sb.setWorkUnitInfo(wui);
		
		
		sb.setBAge(dto.get("BAge").toString());
		
		sb.setBCtSchoolAge(dto.get("BCtSchoolAge").toString());
		sb.setBDictCity(dto.get("BDictCity").toString());
		sb.setBDictCountry(dto.get("BDictCountry").toString());
		sb.setBDictDepartment(dto.get("BDictDepartment").toString());
		sb.setBDictDistrict(dto.get("BDictDistrict").toString());
		sb.setBDictDuty(dto.get("BDictDuty").toString());
		sb.setBDictIsMarry(dto.get("BDictIsMarry").toString());
		sb.setBDictPaperType(dto.get("BDictPaperType").toString());
		sb.setBDictPolity(dto.get("BDictPolity").toString());
		sb.setBDictProvince(dto.get("BDictProvince").toString());
		sb.setBHealthState(dto.get("BHealthState").toString());
		sb.setBInterest(dto.get("BInterest").toString());
		sb.setBMarriage(dto.get("BMarriage").toString());
		sb.setBNation(dto.get("BNation").toString());
		sb.setBNationAt(dto.get("BNationAt").toString());
		sb.setBPaperNum(dto.get("BPaperNum").toString());
		sb.setBPhotoPath(dto.get("BPhotoPath").toString());
		sb.setBStaffName(dto.get("BStaffName").toString());
		sb.setBStaffNickname(dto.get("BStaffNickname").toString());
		sb.setBStaffNum(dto.get("BStaffNum").toString());
		sb.setBStaffSex(dto.get("BStaffSex").toString());
		sb.setBGrade(dto.get("BGrade").toString());
		
		sb.setCAddress(dto.get("CAddress").toString());
		sb.setCFaxNum(dto.get("CFaxNum").toString());
		sb.setCPostalcode(dto.get("CPostalcode").toString());
		sb.setDictIsBeginwork(dto.get("dictIsBeginwork").toString());
		sb.setLinkExtNum(dto.get("linkExtNum").toString());
		sb.setLinkHomeNum(dto.get("linkHomeNum").toString());
		sb.setLinkHomepage(dto.get("linkHomepage").toString());
		sb.setLinkMobileNum(dto.get("linkMobileNum").toString());
		sb.setLinkMsn(dto.get("linkMsn").toString());
		sb.setLinkQq(dto.get("linkQq").toString());
		sb.setRemark(dto.get("remark").toString());
		
		return sb;
	}
	
	private StaffOtherInfo createStaffOtherInfo(IBaseDTO dto,String id) {
		StaffOtherInfo soi = new StaffOtherInfo();
		
		soi.setStaffId(id);
		
		soi.setAApproveOrgan(dto.get("AApproveOrgan").toString());
		soi.setADictTechniclName(dto.get("ADictTechniclName").toString());
		soi.setADictUseWorkState(dto.get("ADictUseWorkState").toString());
		soi.setAEnterTime(dto.get("AEnterTime")==""?null:(TimeUtil.getTimeByStr(dto.get("AEnterTime").toString(),"yyyy-MM-dd")));
		soi.setAOutTime(dto.get("AOutTime")==""?null:(TimeUtil.getTimeByStr(dto.get("AOutTime").toString(),"yyyy-MM-dd")));
		soi.setAOutWhy(dto.get("AOutWhy").toString());
		soi.setAStudyTitle(dto.get("AStudyTitle").toString());
		soi.setRemark(dto.get("remark").toString());
		
		
		
	
	
		return soi;
	}
	
	
	
	 public void delStaffBasic(String id) {
	 // TODO Auto-generated method stub
		 StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class, id);
	 dao.removeEntity(sb);
	 }


	 
	public IBaseDTO getStaffBasicInfo(String id) {
	 // TODO Auto-generated method stub
	StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class, id);
	StaffOtherInfo soi = (StaffOtherInfo)dao.loadEntity(StaffOtherInfo.class, id);
	IBaseDTO dto = new DynaBeanDTO();
	
	 
			 dto.set("staffId", sb.getStaffId());
//			 dto.set("workUnitInfo", sb.getWorkUnitInfo().getId());
			 dto.set("BStaffNum", sb.getBStaffNum());
			 dto.set("BPhotoPath", sb.getBPhotoPath());
			 dto.set("BStaffName", sb.getBStaffName());
			 dto.set("BStaffNickname", sb.getBStaffNickname());
			 dto.set("BStaffSex", (sb.getBStaffSex()=="1"?"男":"女"));
			 dto.set("BDictCountry", sb.getBDictCountry());
			 dto.set("BDictProvince", sb.getBDictProvince());
			 dto.set("BDictCity", sb.getBDictCity());
			 dto.set("BDictDistrict", sb.getBDictDistrict());
			 dto.set("BCtSchoolAge", sb.getBCtSchoolAge());
			 dto.set("BBirthday",TimeUtil.getTheTimeStr(sb.getBBirthday(), "yyyy-MM-dd"));
			 dto.set("BNation", sb.getBNation());
			 dto.set("BAge", sb.getBAge());
			 dto.set("BNationAt", sb.getBNationAt());
			 dto.set("BDictPolity", sb.getBDictPolity());
			 dto.set("BDictPaperType", sb.getBDictPaperType());
			 dto.set("BPaperNum", sb.getBPaperNum());
			 dto.set("BDictIsMarry", sb.getBDictIsMarry());
			 dto.set("BMarriage", sb.getBMarriage());
			 dto.set("BInterest", sb.getBInterest());
			 dto.set("BHealthState", sb.getBHealthState());
			 dto.set("BDictDepartment", sb.getBDictDepartment());
			 dto.set("BDictDuty", sb.getBDictDuty());
			 dto.set("linkMobileNum", sb.getLinkMobileNum());
			 dto.set("linkHomeNum", sb.getLinkHomeNum());
			 dto.set("linkExtNum", sb.getLinkExtNum());
			 dto.set("linkQq", sb.getLinkQq());
			 dto.set("linkMsn", sb.getLinkMsn());
			 dto.set("linkHomepage", sb.getLinkHomepage());
			 dto.set("CAddress", sb.getCAddress());
			 dto.set("CPostalcode", sb.getCPostalcode());
			 dto.set("CFaxNum", sb.getCFaxNum());
			 
//			 System.out.println(sb.getDictIsBeginwork() +" **************************");
			 
			 dto.set("dictIsBeginwork", sb.getDictIsBeginwork());
			 dto.set("BGrade", sb.getBGrade());
			 dto.set("remark", sb.getRemark());
			
	 
			 dto.set("AStudyTitle", soi.getAStudyTitle());
			 dto.set("ADictTechniclName", soi.getADictTechniclName());
			 dto.set("ADictUseWorkState", soi.getADictUseWorkState());
			 
			 dto.set("AApproveOrgan", soi.getAApproveOrgan());
			 dto.set("AOutTime",TimeUtil.getTheTimeStr(soi.getAOutTime(), "yyyy-MM-dd"));
			 dto.set("AEnterTime", TimeUtil.getTheTimeStr(soi.getAEnterTime(), "yyyy-MM-dd"));
			 dto.set("AOutWhy", soi.getAOutWhy());

	 return dto;
	 }

	
	
	public int getStaffBasicSize() {
		// TODO Auto-generated method stub
		return num;
	}

	
	
	 public boolean updateStaffBasic(IBaseDTO dto) {
	 // TODO Auto-generated method stub
		 
	 dao.updateEntity(modifyStaffBasic(dto));
	 
	 dao.updateEntity(modifyStaffOtherInfo(dto));
	 return false;
	 }
		
	private StaffBasic modifyStaffBasic(IBaseDTO dto){

		StaffBasic sb = (StaffBasic)dao.loadEntity(StaffBasic.class, dto.get("staffId").toString());
	
		WorkUnitInfo wui  = (WorkUnitInfo)dao.loadEntity(WorkUnitInfo.class, dto.get("companyName").toString());
		
		
		sb.setWorkUnitInfo(wui);
		sb.setBAge(dto.get("BAge").toString());
		sb.setBBirthday(TimeUtil.getTimeByStr(dto.get("BBirthday").toString(),"yyyy-MM-dd"));
		sb.setBCtSchoolAge(dto.get("BCtSchoolAge").toString());
		sb.setBDictCity(dto.get("BDictCity").toString());
		sb.setBDictCountry(dto.get("BDictCountry").toString());
		sb.setBDictDepartment(dto.get("BDictDepartment").toString());
		sb.setBDictDistrict(dto.get("BDictDistrict").toString());
		sb.setBDictDuty(dto.get("BDictDuty").toString());
		sb.setBDictIsMarry(dto.get("BDictIsMarry").toString());
		sb.setBDictPaperType(dto.get("BDictPaperType").toString());
		sb.setBDictPolity(dto.get("BDictPolity").toString());
		sb.setBDictProvince(dto.get("BDictProvince").toString());
		sb.setBHealthState(dto.get("BHealthState").toString());
		sb.setBInterest(dto.get("BInterest").toString());
		sb.setBMarriage(dto.get("BMarriage").toString());
		sb.setBNation(dto.get("BNation").toString());
		sb.setBNationAt(dto.get("BNationAt").toString());
		sb.setBPaperNum(dto.get("BPaperNum").toString());
		sb.setBPhotoPath(dto.get("BPhotoPath").toString());
		sb.setBStaffName(dto.get("BStaffName").toString());
		sb.setBStaffNickname(dto.get("BStaffNickname").toString());
		sb.setBStaffNum(dto.get("BStaffNum").toString());
		sb.setBStaffSex(dto.get("BStaffSex").toString());
		sb.setCAddress(dto.get("CAddress").toString());
		sb.setCFaxNum(dto.get("CFaxNum").toString());
		sb.setCPostalcode(dto.get("CPostalcode").toString());
		sb.setDictIsBeginwork(dto.get("dictIsBeginwork").toString());
		sb.setLinkExtNum(dto.get("linkExtNum").toString());
		sb.setLinkHomeNum(dto.get("linkHomeNum").toString());
		sb.setLinkHomepage(dto.get("linkHomepage").toString());
		sb.setLinkMobileNum(dto.get("linkMobileNum").toString());
		sb.setLinkMsn(dto.get("linkMsn").toString());
		sb.setLinkQq(dto.get("linkQq").toString());
		sb.setRemark(dto.get("remark").toString());
		sb.setBGrade(dto.get("BGrade").toString());

	 return sb;
	 }
	
	private StaffOtherInfo modifyStaffOtherInfo(IBaseDTO dto){

		StaffOtherInfo soi = (StaffOtherInfo)dao.loadEntity(StaffOtherInfo.class, dto.get("staffId").toString());
		
		
		soi.setAApproveOrgan(dto.get("AApproveOrgan").toString());
		soi.setADictTechniclName(dto.get("ADictTechniclName").toString());
		soi.setADictUseWorkState(dto.get("ADictUseWorkState").toString());
		soi.setAEnterTime(TimeUtil.getTimeByStr(dto.get("AEnterTime").toString(),"yyyy-MM-dd"));
		soi.setAOutTime(TimeUtil.getTimeByStr(dto.get("AOutTime").toString(),"yyyy-MM-dd"));
		soi.setAOutWhy(dto.get("AOutWhy").toString());
		soi.setAStudyTitle(dto.get("AStudyTitle").toString());
		soi.setRemark(dto.get("remark").toString());
		
		 return soi;
	}
	
	
	
	
	

	public List staffBasicQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		staffBasicHelp sbh = new staffBasicHelp();
		
		Object[] result = (Object[]) dao.findEntity(sbh.staffBasicQuery(dto, pi));
		num = dao.findEntitySize(sbh.staffBasicQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			StaffBasic sb = (StaffBasic) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			
			dbd.set("staffId", sb.getStaffId());
			dbd.set("BStaffName", sb.getBStaffName());
			dbd.set("BStaffNickname", sb.getBStaffNickname());
			
			if(sb.getDictIsBeginwork()!=null)
			{
			
				if(sb.getDictIsBeginwork().equals("yes"))
				{
					dbd.set("dictIsBeginwork","在职");
				}
				else
				{
					dbd.set("dictIsBeginwork","不在职");
				}
			}
//	    	dbd.set("dictIsBeginwork", sb.getDictIsBeginwork()=="1"?"在职":"不在职");
			dbd.set("linkMobileNum", sb.getLinkMobileNum());
			list.add(dbd);
		}
		return list;
	}
	
	
	
	
	
	public List getWorkUnitInfo()
	{
		List list = new ArrayList();

		String id = null;
		String value = null;
		
		staffBasicHelp sbh = new staffBasicHelp();
		
		Object[] result = (Object[]) dao.findEntity(sbh.getWorkUnitInfo());
		num = dao.findEntitySize(sbh.getWorkUnitInfo());
		for (int i = 0, size = result.length; i < size; i++) {
			WorkUnitInfo wui = (WorkUnitInfo) result[i];
			
			id = wui.getId();
			value = wui.getCompanyName();

			list.add(new LabelValueBean(value,id));
			
		}
		return list;
	}
	
	public List getdepartmentList()
	{
		
		List list = new ArrayList();

		String id = null;
		String value = null;
		
		staffBasicHelp sbh = new staffBasicHelp();
		
		Object[] result = (Object[]) dao.findEntity(sbh.getWorkUnitInfo());
		num = dao.findEntitySize(sbh.getWorkUnitInfo());
		for (int i = 0, size = result.length; i < size; i++) {
			WorkUnitInfo wui = (WorkUnitInfo) result[i];
			
			id = wui.getId();
			value = wui.getCompanyName();

			list.add(new LabelValueBean(value,id));
			
		}
		return list;
		
	}
	
	
	public List getDutyList()
	{
		
		List list = new ArrayList();

		String id = null;
		String value = null;
		
		staffBasicHelp sbh = new staffBasicHelp();
		
		Object[] result = (Object[]) dao.findEntity(sbh.getWorkUnitInfo());
		num = dao.findEntitySize(sbh.getWorkUnitInfo());
		for (int i = 0, size = result.length; i < size; i++) {
			WorkUnitInfo wui = (WorkUnitInfo) result[i];
			
			id = wui.getId();
			value = wui.getCompanyName();

			list.add(new LabelValueBean(value,id));
			
		}
		return list;
		
	}
	
}
