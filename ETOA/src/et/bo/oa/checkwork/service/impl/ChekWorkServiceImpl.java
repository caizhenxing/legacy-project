package et.bo.oa.checkwork.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import et.bo.oa.checkwork.service.CheckWorkServiceI;
import et.po.CheckworkAbsence;
import et.po.CheckworkInfo;
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

/**
 * 
 * 考勤管理 Service
 * 
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-09
 * 
 */
public class ChekWorkServiceImpl implements CheckWorkServiceI {

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private int num = 0;
	
	private String duty = "8:30";
//	
	private String offduty = "5:00";
	
	private Log logger = LogFactory.getLog(ChekWorkServiceImpl.class);

	public ChekWorkServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * 获得员工基本信息数据集合
	 * 
	 * 
	 * @param property：查询集合
	 * @param departmentId
	 * @return：EmployeeInfo实例数组
	 */
	private MyQuery getEmpolyeeInfoObjs(IBaseDTO infoDTO, PageInfo pageInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		if(!infoDTO.get("department").toString().equals("y")){
			dc.add(Restrictions.eq("department", infoDTO.get("department").toString()));
		}
		dc.add(Restrictions.eq("delSign", "1"));
		dc.add(Restrictions.eq("isLeave", "1"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}
	/**
	 * 
	 * 获得员工基本属性
	 * 
	 * 
	 * @param property：查询属性
	 * @param checkwork：CheckWorkAbsence实例
	 * @return：EmployeeInfo实例
	 */
	private EmployeeInfo getEmpolyeeInfo(String property, String value) {
//		System.out.println("有人................"+property+"  "+value); 
		EmployeeInfo employee = new EmployeeInfo();
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		dc.add(Restrictions.eq(property, value));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		Object[] objs = this.dao.findEntity(myQuery);
//		System.out.println(objs.length);
		if (objs.length != 0) {
			employee = (EmployeeInfo) objs[0];
		}
		return employee;
	}
	

	/**
	 * <p> 获得查询记录集 </p>
	 */
	public Object[] seletCheckList(IBaseDTO infoDTO,PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] employeeInfoObjs = new Object[1];
		//判断传近来的是个人还是部门查询  ture为部门考勤查询  false为个人考勤查询
		if(infoDTO.get("repairUser").toString().equals("y")){
			employeeInfoObjs = dao.findEntity(getEmpolyeeInfoObjs(infoDTO, pageInfo));
			num = dao.findEntitySize(getEmpolyeeInfoObjs(infoDTO, pageInfo));//为人数
		}else{	
			employeeInfoObjs[0] = getEmpolyeeInfo("id",infoDTO.get("repairUser").toString());
			num = employeeInfoObjs.length;//为人数
		}
			SreachService sreachService = new SreachService();		
			Object[] objs = this.dao.findEntity(sreachService.seletCheckListQuery(infoDTO, pageInfo));
			Object[] checkWorkObjs  = new Object[employeeInfoObjs.length];
			Calendar ca = Calendar.getInstance();
//			CheckworkTimeId ct = getCheckworkTime();
//			String duty = ct.getOndutyTime();
//			String offduty = ct.getOffdutyTime();
			// employeeInfoObjs   有多少人就循环多少次
			for(int i=0,personSize=employeeInfoObjs.length;i<personSize;i++){
				int cz = 0;        //迟到
				int zt = 0;        //早退
				int weiqiandao = 0;//未签到
				int qj = 0;        //请假
				int wc = 0;        //外出
				int cc = 0;        //出差
				EmployeeInfo ei = (EmployeeInfo)employeeInfoObjs[i];
				qj = dao.findEntitySize(sreachService.getNumByEmployeeId(infoDTO, ei.getId(), "1"));
				wc = dao.findEntitySize(sreachService.getNumByEmployeeId(infoDTO, ei.getId(), "2"));
				cc = dao.findEntitySize(sreachService.getNumByEmployeeId(infoDTO, ei.getId(), "3"));
				int cis = 0;
				Date startDate = TimeUtil.getTimeByStr(infoDTO.get("startT").toString(),"yyyy-MM-dd");
				Date endDate = TimeUtil.getTimeByStr(infoDTO.get("endT").toString(),"yyyy-MM-dd");				
            //从开始日期到结束日期循环判断迟到,早退,未签到情况
			while(startDate.compareTo(endDate)<=0){//计算缺勤次数循环
			int flag = 0;
			cis++;
			for(int j=0,size=objs.length;j<size;j++){
				CheckworkInfo ci = (CheckworkInfo)objs[j]; 
				if(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(ci.getCheckDate()), "yyyy-MM-dd").compareTo(startDate)==0&&ci.getEmployeeId().equals(ei.getId())){
					//判断
					flag++;
					
					if(ci.getBeginTime().equals("")||ci.getBeginTime()==null){
						weiqiandao++;
					}//判断迟到
					else if(TimeUtil.getTimeByStr(ci.getBeginTime(),"HH:mm").compareTo(TimeUtil.getTimeByStr(duty,"HH:mm"))>0){
						cz++;
					}
					if(ci.getEndTime().toString().equals("")||ci.getBeginTime()==null){
						weiqiandao++;
					}//判断早退
					else if(TimeUtil.getTimeByStr(ci.getEndTime(),"HH:mm").compareTo(TimeUtil.getTimeByStr(offduty,"HH:mm"))<0){
						zt++;
					}
					break;
				}
			}
			if(flag==0){
				weiqiandao=weiqiandao+2;
			}
//			System.out.println(ei.getName()+"循环第"+cis+"次"+"  "+cz+"  "+zt+"  "+weiqiandao);
			ca.setTime(startDate);
			ca.add(ca.DATE, 1);
			startDate = ca.getTime();
		}
			checkWorkObjs[i] = workInfoToDyna(ei,cz,zt,weiqiandao,qj,wc,cc,infoDTO.get("startT").toString(),infoDTO.get("endT").toString());
	}	
		return checkWorkObjs;
	}
	

	/**
	 * 
	 * PO to DTO
	 * 
	 * 
	 * @description：CheckworkInfo to DynaBeanDTO
	 * @param checkwork
	 * @return
	 */
	 private DynaBeanDTO workInfoToDyna(EmployeeInfo ei,int cz,int zt,int weiqiandao, int qj, int wc, int cc,String startT,String endT) {
		DynaBeanDTO dynaDTO = new DynaBeanDTO();
		dynaDTO.set("cz", Integer.toString(cz));
		dynaDTO.set("zt", Integer.toString(zt));
		dynaDTO.set("weiqiandao", Integer.toString(weiqiandao));
		dynaDTO.set("qj", Integer.toString(qj));
		dynaDTO.set("wc", Integer.toString(wc));
		dynaDTO.set("cc", Integer.toString(cc));
		dynaDTO.set("repairUser", ei.getName());
		dynaDTO.set("employeeId", ei.getId());
		dynaDTO.set("startT", startT);
		dynaDTO.set("endT", endT);
//		System.out.println(cz+"  "+zt+"  "+weiqiandao+"  "+dynaDTO.get("repairUser").toString());
		dynaDTO.set("department", getDepartName(ei.getDepartment()));
		return dynaDTO;
	}
	 /**
		 * 
		 * PO to DTO
		 * 
		 * 
		 * @description：CheckworkInfo to DynaBeanDTO
		 * @param checkwork
		 * @return
		 */
		 private DynaBeanDTO workInfoToDyna(String date,String cz,String later,String zt,String early,String repairTime) {
			DynaBeanDTO dynaDTO = new DynaBeanDTO();
			dynaDTO.set("checkDate",date);
			dynaDTO.set("week", getWeekOfDate(TimeUtil.getTimeByStr(date, "yyyy-MM-dd")));
			dynaDTO.set("cz", cz);
			dynaDTO.set("later",later);
			dynaDTO.set("zt", zt);
			dynaDTO.set("early",early);
			dynaDTO.set("repairTime", repairTime);
			return dynaDTO;
		}
		 /**
			 * <p> 根据时间取星期 </p>
			 * 
			 * @param id
			 * @return
			 */	 
		 private String getWeekOfDate(Date dt){
			  String[] weekDays={"7","1","2","3","4","5","6"};
			  Calendar cal=Calendar.getInstance();
			  cal.setTime(dt);
			  
			  int w=cal.get(Calendar.DAY_OF_WEEK)-1;
			  if(w<0)w=0;
			  return weekDays[w];
			    
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
	 * <p> 获得查询记录数 </p>
	 * @return：查询记录数
	 */
	public int getCheckListSize() {
		// TODO Auto-generated method stub
		return num;
	}
	/**
	 * @describe 取得人明列表LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
	public  List<LabelValueBean> getNameList(){
		List l = new ArrayList();
		SreachService sreachService = new SreachService();
		Object[] result = (Object[]) dao.findEntity(sreachService.getNameListQuery());
		for(int i = 0,size = result.length;i<size;i++){
			EmployeeInfo ei = (EmployeeInfo) result[i];
			l.add(new LabelValueBean(ei.getName(),ei.getId()));
		}
		return l;
	}
	
	public List seletLaterOrEarlyCheckList(IBaseDTO infoDTO){
		Object[] employeeInfoObjs = new Object[1];
	    employeeInfoObjs[0] = getEmpolyeeInfo("id",infoDTO.get("employeeId").toString());
	    SreachService sreachService = new SreachService();		
		Object[] objs = this.dao.findEntity(sreachService.seletLaterOrEarlyCheckListQuery(infoDTO));
		Object[] repairObjs = dao.findEntity(sreachService.seletRepairCheckListQuery(infoDTO));
		List list = new ArrayList();
			Calendar ca = Calendar.getInstance();
			EmployeeInfo ei = (EmployeeInfo)employeeInfoObjs[0];
			int cis = 0;
			System.out.println(infoDTO.get("endT").toString());
			Date startDate = TimeUtil.getTimeByStr(infoDTO.get("startT").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(infoDTO.get("endT").toString(),"yyyy-MM-dd");
		//从数据库取得考勤时间	
//			CheckworkTimeId ct = getCheckworkTime();
//			String duty = ct.getOndutyTime();
//			String offduty = ct.getOffdutyTime();
        //从开始日期到结束日期循环判断迟到,早退,未签到情况
		while(startDate.compareTo(endDate)<=0){//计算缺勤次数循环
		int flag = 0;
		int flagSign = 0; 
//		int cz = 0;        //迟到
//		int zt = 0;        //早退
		int weiqiandao = 0;//未签到
		String morning = "";
		String afternoon = "";
		String later = "";
		String early = "";
		String nosign = "";
		cis++;
		for(int i=0,size=objs.length;i<size;i++){
			CheckworkInfo ci = (CheckworkInfo)objs[i]; 
			if(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(ci.getCheckDate()), "yyyy-MM-dd").compareTo(startDate)==0&&ci.getEmployeeId().equals(ei.getId())){
				//判断
				flag++;
				
				if(ci.getBeginTime().equals("")||ci.getBeginTime()==null){
//					morning = NOSIGN;
//					later = CheckworkTimeParameter.NOSIGN;
					later = "et.oa.checkwork.nosign";
					flagSign++;
				}//判断迟到
				else if(TimeUtil.getTimeByStr(ci.getBeginTime(),"HH:mm").compareTo(TimeUtil.getTimeByStr(duty,"HH:mm"))>0){
					morning = ci.getBeginTime();
//					later = CheckworkTimeParameter.LATER;
					later = "et.oa.checkwork.later";
					flagSign++;
				}else{
					morning = ci.getBeginTime();
				}
				if(ci.getEndTime().toString().equals("")||ci.getBeginTime()==null){
//					afternoon = NOSIGN;
//					early = CheckworkTimeParameter.NOSIGN;
					early = "et.oa.checkwork.early";
					flagSign++;
				}//判断早退
				else if(TimeUtil.getTimeByStr(ci.getEndTime(),"HH:mm").compareTo(TimeUtil.getTimeByStr(offduty,"HH:mm"))<0){
					afternoon = ci.getEndTime();
//					early = CheckworkTimeParameter.EARLY;
					early = "et.oa.checkwork.early";
					flagSign++;
				}else{
					afternoon = ci.getEndTime();
				}
				break;
			}
		}
		if(flag==0){
			StringBuffer repairInfo = new StringBuffer();
			for(int j=0,size=repairObjs.length;j<size;j++){
				CheckworkInfo reci = (CheckworkInfo)repairObjs[j];
				if(reci.getRepairDate().compareTo(startDate)==0){
					repairInfo.append(reci.getRepairTime());
					repairInfo.append("  ");
				}
			}
			String repairTime = repairInfo.toString();
			list.add(workInfoToDyna(TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"),morning,"et.oa.checkwork.nosign",afternoon,"et.oa.checkwork.nosign",repairTime));
		}
		if(flagSign>0){
			StringBuffer repairInfo = new StringBuffer();
			for(int j=0,size=repairObjs.length;j<size;j++){
				CheckworkInfo reci = (CheckworkInfo)repairObjs[j];
				if(reci.getRepairDate().compareTo(startDate)==0){
					repairInfo.append(reci.getRepairTime());
					repairInfo.append("  ");
				}
			}
			String repairTime = repairInfo.toString();
			list.add(workInfoToDyna(TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"),morning,later,afternoon,early,repairTime));
		}		
//		System.out.println(ei.getName()+"循环第"+cis+"次"+"  "+cz+"  "+zt+"  "+weiqiandao);
		ca.setTime(startDate);
		ca.add(ca.DATE, 1);
		startDate = ca.getTime();
	}
//		checkWorkObjs[i] = workInfoToDyna(ei,cz,zt,weiqiandao);*/
    
	return list;	
//	return checkWorkObjs;
	}
	/**
	 * 
	 */
    public List seletWaichuList(IBaseDTO infoDTO){
    	SreachService ss = new SreachService();
    	Object[] objs = dao.findEntity(ss.getNumByEmployeeId(infoDTO, infoDTO.get("repairUser").toString(), "1"));
    	List list = new ArrayList();
    	for(int i=0,size=objs.length;i<size;i++){
    		CheckworkAbsence ca = (CheckworkAbsence)objs[i];
    		list.add(CaToDynaBeanDTO(ca));
    	}
    	return list;
    }
	
	public List selecQingjiaList(IBaseDTO infoDTO){
		SreachService ss = new SreachService();
    	Object[] objs = dao.findEntity(ss.getNumByEmployeeId(infoDTO, infoDTO.get("repairUser").toString(), "2"));
    	List list = new ArrayList();
    	for(int i=0,size=objs.length;i<size;i++){
    		CheckworkAbsence ca = (CheckworkAbsence)objs[i];
    		list.add(CaToDynaBeanDTO(ca));
    	}
    	return list;
	}
	
	public List selectChuchaiList(IBaseDTO infoDTO){
		SreachService ss = new SreachService();
    	Object[] objs = dao.findEntity(ss.getNumByEmployeeId(infoDTO, infoDTO.get("repairUser").toString(), "3"));
    	List list = new ArrayList();
    	for(int i=0,size=objs.length;i<size;i++){
    		CheckworkAbsence ca = (CheckworkAbsence)objs[i];
    		list.add(CaToDynaBeanDTO(ca));
    	}
    	return list;
	}
	
	private DynaBeanDTO CaToDynaBeanDTO(CheckworkAbsence ca){
		DynaBeanDTO dynaDTO = new DynaBeanDTO();
		dynaDTO.set("department", getDepartName(ca.getEmployeeDepart()));
		dynaDTO.set("repairUser", getEmpolyeeInfo("id",ca.getEmployeeId()).getName());
		dynaDTO.set("startDate", TimeUtil.getTheTimeStr(ca
				.getBeginTime(), "yyyy-MM-dd"));
		dynaDTO.set("sTime", ca.getSTime());
		dynaDTO.set("endDate", TimeUtil.getTheTimeStr(ca.getEndTime(),
				"yyyy-MM-dd"));
		dynaDTO.set("checkDate", TimeUtil.getTheTimeStr(ca
				.getAbsenceDate(), "yyyy-MM-dd"));
		dynaDTO.set("eTime", ca.getETime());
		dynaDTO.set("time", ca.getAbsenceTime());
		dynaDTO.set("reason", ca.getAbsenceReason());
		return dynaDTO;
	}
	
	private List selectEarlyOrLaterList(IBaseDTO infoDTO, List list){
		SreachService ss = new SreachService();
		Object[] repairObjs = dao.findEntity(ss.seletRepairCheckListQuery(infoDTO));
//		for(int i=0,size=objs.length;i<size;i++ ){
//			CheckworkInfo ci = (CheckworkInfo)objs[i];
//			Iterator iter = list.iterator();
//			while(iter.hasNext()){
//				DynaBeanDTO dbd = iter.next();
//				ciInlist.get
//				if(ci.getCheckDate())
//			}
//		}
		return list;		
	}
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKeyService() {
		return keyService;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}
}
