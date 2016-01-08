package et.bo.oa.checkwork.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.checkwork.service.AbsenceServiceI;
import et.po.CheckworkAbsence;
import et.po.CheckworkInfo;
import et.po.EmployeeInfo;
import et.po.SysDepartment;
import excellence.common.classtree.ClassTreeService;
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
 * ȱ�ڹ��� Service
 * 
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-10
 * 
 */
public class AbsenceServiceImpl implements AbsenceServiceI {

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private ClassTreeService departTree = null;

	private Log logger = LogFactory.getLog(AbsenceServiceImpl.class);

	private int num = 0;
	
	private String typeAbsence = "";

	public AbsenceServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> ���ȱ������ </p>
	 */
	public void addAbsence(IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createAbsenceObject(infoDTO));
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of addAbsence is : "
					+ e.getMessage());
		}
	}

	/**
	 * <p> ��Ӳ�ǩ���� </p>
	 */
	public void addResign(IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createResignObject(infoDTO));
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of addResign is : "
					+ e.getMessage());
		}
	}

	/**
	 * <p> ȱ�ڼ�¼��ѯ </p>
	 */
	public Object[] selectAbsenseList(IBaseDTO infoDTO, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] absenceObjs = new Object[0];
		try {
			SreachService sreachService = new SreachService();
			if (!("select".equals(infoDTO.get("absenceType").toString()))){
				typeAbsence = infoDTO.get("absenceType").toString();
			}

			Object[] objs = this.dao.findEntity(sreachService.selectAbsenseListQuery(infoDTO, pageInfo));

			num = this.dao.findEntitySize(sreachService.selectAbsenseListQuery(infoDTO, pageInfo));

			if (null != objs && 0 < objs.length) {
				absenceObjs = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					CheckworkAbsence resignObject = (CheckworkAbsence) objs[i];
					absenceObjs[i] = workAbsenceToDyna(resignObject);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of selectResign is : "
					+ e.getMessage());
			e.printStackTrace();
		}
		return absenceObjs;
	}

	/**
	 * <p> ����û��б� </p>
	 * 
	 * @return���û���Ϣ��id��name��
	 */
	public Object[] getUserList(String page, IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		Object[] userList = null;
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
			if ("selectEmployee".equals(page)) {
				EmployeeInfo info = new EmployeeInfo();
				info.setDepartment(infoDTO.get("departList") == null ? ""
						: infoDTO.get("departList").toString());
				dc.add(Example.create(info));
			}
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			if (null != objs && 0 < objs.length) {
				userList = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					EmployeeInfo employeeInfo = (EmployeeInfo) objs[i];
					userList[i] = employeeInfoToDyna(employeeInfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return userList;
	}

	/**
	 * </p> PO to DTO <p>
	 * 
	 * @description��CheckworkAbsence to DynaBeanDTO
	 * @return
	 */
	private DynaBeanDTO workAbsenceToDyna(CheckworkAbsence checkwork) {
		DynaBeanDTO dynaDTO = new DynaBeanDTO();
		
		
		dynaDTO.set("department", getDepartName(checkwork.getEmployeeDepart()));
		dynaDTO.set("name", getEmpolyeeInfo("id",checkwork.getEmployeeId()));
		dynaDTO.set("startDate", TimeUtil.getTheTimeStr(checkwork
				.getBeginTime(), "yyyy-MM-dd HH:mm"));
		dynaDTO.set("sTime", checkwork.getSTime());
		dynaDTO.set("endDate", TimeUtil.getTheTimeStr(checkwork.getEndTime(),
				"yyyy-MM-dd HH:mm"));
		dynaDTO.set("date", TimeUtil.getTheTimeStr(checkwork
				.getAbsenceDate(), "yyyy-MM-dd HH:mm"));
		dynaDTO.set("eTime", checkwork.getETime());
		dynaDTO.set("time", checkwork.getAbsenceTime());
		dynaDTO.set("absenceType", checkwork.getAbsenceType());
		String title =checkwork.getAbsenceReason();
		if (checkwork.getAbsenceReason().length()>15)
		 {
		     title = title.substring(0,14)+"...";
		 }
		dynaDTO.set("reason", title);
		return dynaDTO;
	}

	/**
	 * </p> PO to DTO <p>
	 * 
	 * @description��EmployeeInfo to DynaBeanDTO
	 * @param employeeInfo
	 * @return
	 */
	private DynaBeanDTO employeeInfoToDyna(EmployeeInfo employeeInfo) {
		DynaBeanDTO dynaDTO = new DynaBeanDTO();
		dynaDTO.set("employeeId", employeeInfo.getName());
		dynaDTO.set("name", employeeInfo.getName());
		return dynaDTO;
	}

	
	/** 
	 * <p> ����CheckworkAbsenceʵ�� </p>
	 * 
	 * @description��ȱ�����ʵ��
	 * @return��CheckworkAbsenceʵ��
	 */
	private CheckworkAbsence createAbsenceObject(IBaseDTO dto) {
		CheckworkAbsence checkWorkAbsence = new CheckworkAbsence();
		checkWorkAbsence.setId(keyService.getNext("checkwork_absence"));
		checkWorkAbsence.setUserId(dto.get("userId").toString());
		checkWorkAbsence.setEmployeeId(dto.get("absenceUser") == null ? ""
				: dto.get("absenceUser").toString());
		checkWorkAbsence.setEmployeeDepart(dto.get("department") == null ? ""
				: dto.get("department").toString());
		checkWorkAbsence.setAbsenceType(dto.get("absenceType") == null ? ""
				: dto.get("absenceType").toString());
		checkWorkAbsence.setAbsenceDate(TimeUtil.getNowTime());
		if(dto.get("sTime").toString().equals("")){
			dto.set("sTime", "00:00:00");
		}
		if(dto.get("eTime").toString().equals("")){
			dto.set("eTime", "00:00:00");
		}
		String s = dto.get("startDate").toString()+" "+dto.get("sTime").toString();
		String e = dto.get("endDate").toString()+" "+dto.get("eTime").toString();
		checkWorkAbsence.setBeginTime(TimeUtil.getTimeByStr(s, "yyyy-MM-dd HH:mm"));
		checkWorkAbsence.setEndTime(TimeUtil.getTimeByStr(e, "yyyy-MM-dd HH:mm"));
		checkWorkAbsence.setAbsenceReason(dto.get("reason") == null ? "" : dto
				.get("reason").toString());
		checkWorkAbsence.setSignDate(TimeUtil.getNowTime());
		return checkWorkAbsence;
	}

	/**
	 * <p> ����CheckworkAbsenceʵ�� </p>
	 * 
	 * @description����ǩ���ʵ��
	 * @return��CheckworkAbsenceʵ��
	 */
	private CheckworkInfo createResignObject(IBaseDTO dto) {
		CheckworkInfo resignObject = new CheckworkInfo();
		resignObject.setId(keyService.getNext("checkwork_info"));
		resignObject.setUserId(dto.get("userId").toString());
		resignObject.setEmployeeId(dto.get("absenceUser") == null ? "" : dto
				.get("absenceUser").toString());
		resignObject.setRepairTime(dto.get("repairTime") == null ? "" : dto
				.get("repairTime").toString());
		resignObject.setRepairDate(TimeUtil.getTimeByStr(dto.get("repairDate")
				.toString(), "yyyy-MM-dd"));
		resignObject.setSignMark("Y");// ��ǩ
		resignObject.setDepartId(dto.get("department") == null ? "" : dto
				.get("department").toString());
		resignObject.setRemark(dto.get("repairReason") == null ? ""
				: dto.get("repairReason").toString());
		resignObject.setSignDate(TimeUtil.getNowTime());
		return resignObject;
	}

	
	/**
	 *  <p> ���Ա���������� </p> 
	 * 
	 * @param property����ѯ����
	 * @param checkwork��CheckWorkAbsenceʵ��
	 * @return��EmployeeInfoʵ��
	 */
	private String getEmpolyeeInfo(String property, String value) {
		EmployeeInfo employee = new EmployeeInfo();
		String name = "";
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		dc.add(Restrictions.eq(property, value));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		Object[] objs = this.dao.findEntity(myQuery);
		if (objs.length != 0) {
			employee = (EmployeeInfo) objs[0];
			name = employee.getName();
		}
		return name;
	}

	
	/**
	 * <p> ��ò������� </p>
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
	 * <p> ���Сʱ�� </p>
	 * 
	 * @return
	 */
	public List gethour() {
		List<LabelValueBean> hourList = new ArrayList<LabelValueBean>();
		for (int i = 1; i < 25; i++) {
			LabelValueBean hourBean = new LabelValueBean();
			hourBean.setLabel(Integer.toString(i));
			hourBean.setValue(Integer.toString(i));
			hourList.add(hourBean);
		}
		return hourList;
	}

	
	/**
	 * @describe ������״̬�б�
	 * @param
	 * @return
	 * 
	 */
	public List getOutStateList(){
		List list = new ArrayList();
		OutStateSreachHelp oss = new OutStateSreachHelp();
		Object[] objs = dao.findEntity(oss.getOutStateSreach());
		for(int i=0,size=objs.length;i<size;i++){
			CheckworkAbsence ca = (CheckworkAbsence)objs[i];
			list.add(workAbsenceToDyna(ca));
		}
		return list;
	}
	/**
	 * @describe ������������״̬�б�
	 * @param
	 * @return
	 * 
	 */
	public List getAllOutStateList(){
		List list = new ArrayList();
		OutStateSreachHelp oss = new OutStateSreachHelp();
		Object[] objs = dao.findEntity(oss.getAllOutStateSreach());
		for(int i=0,size=objs.length;i<size;i++){
			CheckworkAbsence ca = (CheckworkAbsence)objs[i];
			list.add(workAbsenceToDyna(ca));
		}
		return list;
	}
	
	public int getAbsenceSize() {
		return num;
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

	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}

	public String getAbsenceType() {
		// TODO Auto-generated method stub
		return typeAbsence;
	}

}
