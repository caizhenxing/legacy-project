package et.bo.oa.resource.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.resource.service.ResourceServiceI;
import et.po.EmployeeInfo;
import et.po.ResourceInfo;
import et.po.ResourceUse;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p> 会议室管理 Serivce </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-14
 * 
 */
public class MeetingServiceImpl implements ResourceServiceI {

	private Log logger = LogFactory.getLog(MeetingServiceImpl.class);

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private int sizeNum = 0;
	
	public MeetingServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 会议室添加 </p>
	 */
	public void addResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createMeetingInfo(dto));
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception's MeetingServiceImpl of addResource is : "
					+ e.getMessage());
		}
	}

	/**
	 * <p> 会议室申请 </p>
	 */
	public void addApply(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createApplyMeeting(dto));
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}

	/**
	 * <p> 查询会议室使用情况 </p>
	 */
	public Object[] searchResourceUse(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] meetingObjects = new Object[0];
		try {
			SreachService sreachService = new SreachService();
			ResourceInfo ri = (ResourceInfo) dao.loadEntity(ResourceInfo.class, dto.get("meetingNames").toString());
			Object[] objs = this.dao.findEntity(sreachService.searchResourceUseMyQuery(dto, pageInfo ,ri));
			sizeNum = this.dao.findEntitySize(sreachService.searchResourceUseMyQuery(dto, pageInfo ,ri));
			
			if (null != objs && 0 < objs.length) {
				meetingObjects = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					ResourceUse meetingUse = (ResourceUse) objs[i];
					meetingObjects[i] = ResourceUsetoDTO(meetingUse);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return meetingObjects;
	}

	/**
	 * <p> 查询会议室信息 </p>
	 */
	public Object[] searhResourceInfo(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] meetingInfo = new Object[0];
		try {
			SreachService sreachService = new SreachService();
			
			Object[] objs = this.dao.findEntity(sreachService.searhResourceInfoMyQuery(dto, pageInfo));
			sizeNum = this.dao.findEntitySize(sreachService.searhResourceInfoMyQuery(dto, pageInfo));
			
			if (null != objs && 0 < objs.length) {
				meetingInfo = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					ResourceInfo meetingUse = (ResourceInfo) objs[i];
					meetingInfo[i] = ResourceInfoToDTO(meetingUse);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
			e.printStackTrace();
		}
		return meetingInfo;
	}

	/**
	 * <p> 获得用户列表 </p>
	 * @return：用户信息（id，name）
	 */
	public Object[] getUserList(String page,IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		Object[] userList = null;
		try {
			SreachService sreachService = new SreachService();
			Object[] objs = this.dao.findEntity(sreachService.getUserListMyQuery(page, infoDTO));
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
	 * <p> 更新资源基本信息 </p>
	 * @param dto
	 */
	public void updateResourceInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			dao.updateEntity(updateMeetingInfo(dto));
		} catch (Exception e) {
			logger.debug(e);
			
			e.printStackTrace();
		}
		
	}

	/**
	 * <p> 删除资源基本信息 </p>
	 * @param dto
	 */
	public void deleteResourceInfo(String meetingId) {

		try {
			ResourceInfo resourceInfo = (ResourceInfo)dao.loadEntity(ResourceInfo.class,meetingId);
			this.dao.removeEntity(resourceInfo);
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}
	
	
	/**
	 * <p>获得资源列表</p>
	 */
	public List getResourceList(String ResourceType,String key) {
		// TODO Auto-generated method stub
		List<LabelValueBean> resourceList = new ArrayList<LabelValueBean>();
		try {
			ResourceInfo resource = new ResourceInfo();
			resource.setResourceType(ResourceType);
			DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
			dc.add(Example.create(resource));
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			
			if (null != objs && 0 < objs.length) {
				ResourceInfo res = new ResourceInfo();
				for (int i = 0; i < objs.length; i++) {
					LabelValueBean resourceInfo = new LabelValueBean();
					res = (ResourceInfo)objs[i];
					resourceInfo.setValue(res.getId());
					resourceInfo.setLabel(res.getResourceName());
					resourceList.add(resourceInfo);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return resourceList;
	}
	
	/**
	 * <p> 获得会议室资源特定属性 </p>
	 */
	public DynaBeanDTO getResourceValue(String property, String value) {
		DynaBeanDTO porpDTO = new DynaBeanDTO();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
			dc.add(Restrictions.eq(property,value));
			
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			
			if (null != objs && 0 < objs.length) {
				ResourceInfo porp = (ResourceInfo)objs[0];
				porpDTO = ResourceUpdateInfoToDTO(porp);
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return porpDTO;
	}

	public DynaBeanDTO getResourceUse(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void approvceResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * </p> PO to DTO <p>
	 * 
	 * @description：EmployeeInfo to DynaBeanDTO
	 * @param employeeInfo
	 * @return
	 */
	private DynaBeanDTO employeeInfoToDyna(EmployeeInfo employeeInfo){
		DynaBeanDTO dynaDTO = new DynaBeanDTO();
		dynaDTO.set("employeeId",employeeInfo.getName());
		dynaDTO.set("name",employeeInfo.getName());
		return dynaDTO;
	}
	
	/**
	 * </p> PO to DTO <p>
	 * 
	 * @description：ResourceUse to DynaBeanDTO
	 * @return
	 */
	private DynaBeanDTO ResourceUsetoDTO(ResourceUse resourceUse){
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("name",resourceUse.getResourceInfo().getResourceName());
		dto.set("useDate",TimeUtil.getTheTimeStr(resourceUse.getDateArea(), "yyyy-MM-dd"));
		dto.set("timeArea",resourceUse.getTimeArea());
		dto.set("applyUser",resourceUse.getUserName());
		if("0".equals(resourceUse.getResourceState())){
			dto.set("state","未审批");
		}else if("1".equals(resourceUse.getResourceState())){
			dto.set("state","待审批");
		}else{
			dto.set("state","已批准");
		}
		dto.set("principalName",resourceUse.getPrincipalName());
		return dto;
	}
	
	/**
	 * <p> 会议室查询页DTO </p>
	 * @param info
	 * @return
	 */
	private DynaBeanDTO ResourceInfoToDTO(ResourceInfo info){
		StringBuffer meetingThing = new StringBuffer();
		StringBuffer meetingRemark = new StringBuffer();
		
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("meetingId",info.getId());
		dto.set("meetingName",info.getResourceName());
		if(info.getResourceState().length() > 10){
			meetingThing.append(info.getResourceState().substring(0,10)).append("……");
		}else{
			meetingThing.append(info.getResourceState());
		}
		
		if(info.getRemark().length() > 6){
			meetingRemark.append(info.getRemark().substring(0,6)).append("……");
		}else{
			meetingRemark.append(info.getRemark());
		}
		dto.set("meetingThing",meetingThing.toString());
		dto.set("meetingPrincipal", info.getPrincipalId());
		dto.set("meetingRemark",meetingRemark.toString());
		return dto;
	}
	
	/**
	 * <p> 会议室信息更新DTO </p>
	 * @param info
	 * @return
	 */
	private DynaBeanDTO ResourceUpdateInfoToDTO(ResourceInfo info){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("meetingId",info.getId());
		dto.set("meetingName",info.getResourceName());
		dto.set("meetingThing",info.getResourceState());
		dto.set("meetingPrincipal", info.getPrincipalId());
		dto.set("meetingRemark",info.getRemark());
		return dto;
	}
	
	/**
	 * <p> 创建 ResourceInfo 实例 </p>
	 * @description : 会议室信息添加
	 * @param dto
	 * @return
	 */
	private ResourceInfo createMeetingInfo(IBaseDTO dto) {
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setId(keyService.getNext("resource_info"));
		resourceInfo.setResourceType("40");
		resourceInfo.setResourceName(dto.get("meetingName") == null ? ""
				: dto.get("meetingName").toString());
		resourceInfo.setResourceState(dto.get("meetingThing") == null ? ""
				: dto.get("meetingThing").toString());
		resourceInfo.setPrincipalId(dto.get("meetingPrincipal") == null ? ""
				: dto.get("meetingPrincipal").toString());
		resourceInfo.setCreateDate(TimeUtil.getNowTime());
		resourceInfo.setRemark(dto.get("meetingRemark") == null ? "" : dto
				.get("meetingRemark").toString());
		return resourceInfo;
	}
	
	/**
	 * <p> 创建 ResourceInfo 更新实例 </p>
	 * @param dto
	 * @return
	 */
	private ResourceInfo updateMeetingInfo(IBaseDTO dto) {
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setId(dto.get("meetingId").toString());
		resourceInfo.setResourceType("40");
		resourceInfo.setResourceName(dto.get("meetingName") == null ? ""
				: dto.get("meetingName").toString());
		resourceInfo.setResourceState(dto.get("meetingThing") == null ? ""
				: dto.get("meetingThing").toString());
		resourceInfo.setPrincipalId(dto.get("meetingPrincipal") == null ? ""
				: dto.get("meetingPrincipal").toString());
		resourceInfo.setCreateDate(TimeUtil.getNowTime());
		resourceInfo.setRemark(dto.get("meetingRemark") == null ? "" : dto
				.get("meetingRemark").toString());
		return resourceInfo;
	}
	

	/**
	 * <p> 创建 ResourceUse 实例 </p>
	 * @description : 会议室申请
	 * @param dto
	 * @return
	 */
	private ResourceUse createApplyMeeting(IBaseDTO dto) {
		ResourceUse resourceUse = new ResourceUse();
		resourceUse.setId(keyService.getNext("resource_use"));
		resourceUse.setResourceInfo(getResourceInfo(dto.get("meetingName").toString()));
		resourceUse.setDateArea(TimeUtil.getTimeByStr(dto
				.get("useDate").toString(), "yyyy-MM-dd"));
		resourceUse.setTimeArea(dto.get("startHour").toString() + " ---- " + dto.get("endHour").toString());
		resourceUse.setUserName(dto.get("applyUser") == null ? ""
				: dto.get("applyUser").toString());
		resourceUse.setResourceState("1");// 0-未审批；1-等待审批；2-已批准
		resourceUse.setRemark(dto.get("applyReason") == null ? ""
				: dto.get("applyReason").toString());
		resourceUse.setApplyState("40");
		return resourceUse;
	}
	
	
	/**
	 * <p> 获得ResourceInfo实例 </p>
	 * @param meetingId：资源Id
	 * @return
	 */
	private ResourceInfo getResourceInfo(String meetingId){
		ResourceInfo resourceInfo = new ResourceInfo();
		try {

			DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
			dc.add(Restrictions.eq("id", meetingId));
			
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			if(objs.length != 0){
				resourceInfo = (ResourceInfo)objs[0];
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return resourceInfo;
	}
	
	 public  List<LabelValueBean> getLabelList(){
		 MeetingSearchHelp msh = new MeetingSearchHelp();
		 Object[] objs = dao.findEntity(msh.searchMeetingRoom());
		 List l = new ArrayList();
		 for(int i=0,size=objs.length;i<size;i++){
			 ResourceInfo ri = (ResourceInfo)objs[i];
			 DynaBeanDTO dbd = new DynaBeanDTO();
			 l.add(new LabelValueBean(ri.getResourceName(),ri.getId()));			 
		 }
		 return l;
	 }
	 
	 public  boolean haveSameResourceName(String resourceName){
		 MeetingSearchHelp msh = new MeetingSearchHelp();
		 int i = dao.findEntitySize(msh.searchSameResourceName(resourceName));
		 if(i>0){
			 return true;
		 }else{
			 return false;
		 }	 
		}
	/* set / get Method */
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

	public int getResourceSize() {
		// TODO Auto-generated method stub
		return sizeNum;
	}
}
