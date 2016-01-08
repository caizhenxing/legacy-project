package et.bo.oa.assissant.hr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.assissant.hr.service.HRService;
import et.po.EmployeeInfo;
import et.po.SysDepartment;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class HRServiceImpl implements HRService{
    int num = 0;
	
	private BaseDAO dao=null;
    
    private KeyService ks = null;

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

	public boolean addHrInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(createHrInfo(dto));
		flag=true;
		return flag;
	}
	
	private EmployeeInfo createHrInfo(IBaseDTO dto){
		EmployeeInfo ei = new EmployeeInfo();
		ei.setId(ks.getNext("EMPLOYEE_INFO"));
		ei.setName(dto.get("name")==null?"":dto.get("name").toString());
		ei.setAge(dto.get("age")==null?0:Integer.parseInt(dto.get("age").toString()));
		ei.setSex(dto.get("sex")==null?"":dto.get("sex").toString());
		ei.setBirth(dto.get("birth")==null?TimeUtil.getTimeByStr("1900-0-0","yyyy-MM-dd"):TimeUtil.getTimeByStr(dto.get("birth").toString(),"yyyy-MM-dd"));
		
		ei.setNation(dto.get("nation")==null?"":dto.get("nation").toString());
		ei.setBlighty(dto.get("blighty")==null?"":dto.get("blighty").toString());
		ei.setPolity(dto.get("polity")==null?"":dto.get("polity").toString());
		ei.setMarriage(dto.get("marriage")==null?"":dto.get("marriage").toString());
		ei.setLover(dto.get("lover")==null?"":dto.get("lover").toString());
		ei.setCode(dto.get("code")==null?"":dto.get("code").toString());
		ei.setMobile(dto.get("moblie")==null?"":dto.get("moblie").toString());
		ei.setHomePhone(dto.get("homePhone")==null?"":dto.get("homePhone").toString());
		ei.setCompanyPhone(dto.get("companyPhone")==null?"":dto.get("companyPhone").toString());
		ei.setHomeAddr(dto.get("homeAddr")==null?"":dto.get("homeAddr").toString());
		ei.setCompanyAddr(dto.get("companyAddr")==null?"":dto.get("companyAddr").toString());
		ei.setPostCode(dto.get("postCode")==null?"":dto.get("postCode").toString());
		ei.setStudyLevel(dto.get("studyLevel")==null?"":dto.get("studyLevel").toString());
		ei.setAlmaMater(dto.get("almaMater")==null?"":dto.get("almaMater").toString());
		ei.setDepartment(dto.get("department")==null?"":dto.get("department").toString());
		ei.setStation(dto.get("station")==null?"":dto.get("station").toString());
		ei.setRecode(dto.get("recode")==null?"":dto.get("recode").toString());
		ei.setDelSign(("delSign")==null?"":dto.get("delSign").toString());
		ei.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		ei.setEmployeeId((String)dto.get("employeeId"));
		ei.setEmployeePhoto((String)dto.get("employeePhoto"));
		ei.setIsLeave((String)dto.get("isLeave"));
		if(dto.get("beginWorkTime")!=null)
		ei.setBeginWorkTime(TimeUtil.getTimeByStr((String)dto.get("beginWorkTime"),"yyyy-MM-dd"));
		if((String)dto.get("endWorkTime")!=null)
		ei.setEndWorkTime(TimeUtil.getTimeByStr((String)dto.get("endWorkTime"),"yyyy-MM-dd"));
		ei.setEndWorkRemark((String)dto.get("endWorkRemark"));
		
		return ei;
	}

	public boolean updateHrInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
	    boolean flag = false;
	    dao.saveEntity(updateEmployeeInfo(dto));
	    flag = true;
		return flag;
	}
	
	private EmployeeInfo updateEmployeeInfo(IBaseDTO dto){
		EmployeeInfo ei = new EmployeeInfo();
		ei.setId(dto.get("id").toString());
		ei.setName(dto.get("name")==null?"":dto.get("name").toString());
		ei.setAge(dto.get("age")==null?0:Integer.parseInt(dto.get("age").toString()));
		ei.setSex(dto.get("sex")==null?"":dto.get("sex").toString());
		ei.setBirth(dto.get("birth")==null?TimeUtil.getTimeByStr("1900-0-0","yyyy-MM-dd"):TimeUtil.getTimeByStr(dto.get("birth").toString(),"yyyy-MM-dd"));
		
		ei.setNation(dto.get("nation")==null?"":dto.get("nation").toString());
		ei.setBlighty(dto.get("blighty")==null?"":dto.get("blighty").toString());
		ei.setPolity(dto.get("polity")==null?"":dto.get("polity").toString());
		ei.setMarriage(dto.get("marriage")==null?"":dto.get("marriage").toString());
		ei.setLover(dto.get("lover")==null?"":dto.get("lover").toString());
		ei.setCode(dto.get("code")==null?"":dto.get("code").toString());
		ei.setMobile(dto.get("moblie")==null?"":dto.get("moblie").toString());
		ei.setHomePhone(dto.get("homePhone")==null?"":dto.get("homePhone").toString());
		ei.setCompanyPhone(dto.get("companyPhone")==null?"":dto.get("companyPhone").toString());
		ei.setHomeAddr(dto.get("homeAddr")==null?"":dto.get("homeAddr").toString());
		ei.setCompanyAddr(dto.get("companyAddr")==null?"":dto.get("companyAddr").toString());
		ei.setPostCode(dto.get("postCode")==null?"":dto.get("postCode").toString());
		ei.setStudyLevel(dto.get("studyLevel")==null?"":dto.get("studyLevel").toString());
		ei.setAlmaMater(dto.get("almaMater")==null?"":dto.get("almaMater").toString());
		ei.setDepartment(dto.get("department")==null?"":dto.get("department").toString());
		ei.setStation(dto.get("station")==null?"":dto.get("station").toString());
		ei.setRecode(dto.get("recode")==null?"":dto.get("recode").toString());
		ei.setDelSign(("delSign")==null?"":dto.get("delSign").toString());
		ei.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		ei.setEmployeeId((String)dto.get("employeeId"));
		ei.setEmployeePhoto((String)dto.get("employeePhoto"));
		ei.setIsLeave((String)dto.get("isLeave"));
		if(!dto.get("beginWorkTime").toString().equals(""))
		ei.setBeginWorkTime(TimeUtil.getTimeByStr((String)dto.get("beginWorkTime"),"yyyy-MM-dd"));
		if(!dto.get("endWorkTime").toString().equals(""))
		ei.setEndWorkTime(TimeUtil.getTimeByStr((String)dto.get("endWorkTime"),"yyyy-MM-dd"));
		ei.setEndWorkRemark((String)dto.get("endWorkRemark"));
		
		return ei;		
	}

	public boolean deleteHrInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			EmployeeInfo ei =(EmployeeInfo)dao.loadEntity(EmployeeInfo.class,dto.get("id").toString());
			ei.setDelSign("y".toUpperCase());
			dao.updateEntity(ei);
			flag = true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public int getHrSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List findHrInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		HRSearch hs = new HRSearch();
		Object[] result = (Object[])dao.findEntity(hs.searchHrOperInfo(dto,pi));
		int s = dao.findEntitySize(hs.searchHrOperInfo(dto,pi));
		num = s;
		
		for(int i=0,size=result.length;i<size;i++){
			EmployeeInfo ei =(EmployeeInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("id",ei.getId());
			dbd.set("name",ei.getName());
			
			dbd.set("sex",ei.getSex());
			dbd.set("birth",TimeUtil.getTheTimeStr(ei.getBirth(),"yyyy-MM-dd"));
			dbd.set("department",getDepartName(ei.getDepartment()));
			dbd.set("station",ei.getStation());
			l.add(dbd);			
		}
		return l;
	}

	public IBaseDTO getHrInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		EmployeeInfo ei = (EmployeeInfo)dao.loadEntity(EmployeeInfo.class,id);
		dto.set("id",ei.getId());
		dto.set("name",ei.getName());
		dto.set("age",ei.getAge());
		dto.set("sex",ei.getSex());
		dto.set("birth",TimeUtil.getTheTimeStr(ei.getBirth(),"yyyy-MM-dd"));
		dto.set("nation",ei.getName());
		dto.set("blighty",ei.getBlighty());
		dto.set("ploity",ei.getPolity());
		dto.set("marriage",ei.getMarriage());
		dto.set("lover",ei.getLover());
		dto.set("code",ei.getCode());
		dto.set("moblie",ei.getMobile());
		dto.set("homePhone",ei.getHomePhone());
		dto.set("companyPhone",ei.getCompanyPhone());
		dto.set("homeAddr",ei.getHomeAddr());
		dto.set("companyAddr",ei.getCompanyAddr());
		dto.set("postCode",ei.getPostCode());
		dto.set("studyLevel",ei.getStudyLevel());
		dto.set("almaMater",ei.getAlmaMater());
		dto.set("department",getDepartName(ei.getDepartment()));
		dto.set("station",ei.getStation());
		dto.set("recode",ei.getRecode());
		dto.set("remark",ei.getRemark());
		dto.set("employeeId", ei.getEmployeeId());
		dto.set("employeePhoto",ei.getEmployeePhoto());
		dto.set("isLeave",ei.getIsLeave());
		dto.set("beginWorkTime",ei.getBeginWorkTime());
		dto.set("endWorkTime",ei.getEndWorkTime());
		dto.set("endWorkRemark",ei.getEndWorkRemark());
		return dto;
	}

	public void updatePhoto(String id, String path) {
		// TODO Auto-generated method stub
		EmployeeInfo ei = (EmployeeInfo)dao.loadEntity(EmployeeInfo.class,id);
		ei.setEmployeePhoto(path);
		dao.saveEntity(ei);
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

}
