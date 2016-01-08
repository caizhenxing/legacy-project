package et.bo.oa.resource.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.resource.service.ResourceServiceI;
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
 *<p> 车辆管理实现 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-17
 * 
 */
public class CarServiceImpl implements ResourceServiceI {

	private Log logger = LogFactory.getLog(CarServiceImpl.class);

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private static int sizeNum = 0;
	
	public CarServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 添加车辆基本信息 </p>
	 */
	public void addResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createCarInfo(dto));
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}

	/**
	 * <p> 车辆申请信息添加 </p>
	 */
	public void addApply(IBaseDTO dto) {
		// TODO Auto-generated method stub

		try {
			this.dao.saveEntity(createApplyObject(dto));
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}

	/**
	 * <p> 获得车辆申请信息 </p>
	 */
	public Object[] searchResourceUse(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] infos = new Object[0];
		try {
			SreachService sreachService = new SreachService();
		
			Object[] objs = this.dao.findEntity(sreachService.getCarApplyMyQuery(dto, pageInfo));
			sizeNum = this.dao.findEntitySize(sreachService.getCarApplyMyQuery(dto, pageInfo));
			System.out.println(sizeNum);
			if (null != objs && 0 < objs.length) {
				infos = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					ResourceUse meetingUse = (ResourceUse) objs[i];
					infos[i] = ResourceUseToDTO(meetingUse);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
			e.printStackTrace();
		}
		
		return infos;
	}

	/**
	 * <p> 查询车辆基本信息 </p>
	 */
	public Object[] searhResourceInfo(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] infos = new Object[0];
		try {
			SreachService sreachService = new SreachService();
			
			Object[] objs = this.dao.findEntity(sreachService.getCarInfoMyQuery(dto, pageInfo));
			sizeNum = this.dao.findEntitySize(sreachService.getCarInfoMyQuery(dto, pageInfo));
			if (null != objs && 0 < objs.length) {
				infos = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					ResourceInfo meetingUse = (ResourceInfo) objs[i];
					infos[i] = ResourceInfoToDTO(meetingUse);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		
		return infos;
	}

	/**
	 * 
	 */
	public Object[] getUserList(String page, IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * <p> 车辆信息更新操作 </p>
	 */
	public void updateResourceInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			this.dao.updateEntity(updateCarInfo(dto));
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}

	/**
	 * <p> 删除车辆基本信息 </p>
	 */
	public void deleteResourceInfo(String id) {
		// TODO Auto-generated method stub
		try {
			ResourceInfo resourceInfo = (ResourceInfo)dao.loadEntity(ResourceInfo.class,id);
			this.dao.removeEntity(resourceInfo);
		} catch (Exception e) {
			logger.debug(e);
			
		}
	}

	public int getResourceSize() {
		// TODO Auto-generated method stub
		return sizeNum;
	}

	/**
	 * <p> 获得车辆<Id－code>List </p>
	 */
	public List getResourceList(String resourceType,String key) {
		// TODO Auto-generated method stub
		List resourceList = new ArrayList();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
			dc.add(Restrictions.eq("resourceType",resourceType));

			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			sizeNum = this.dao.findEntitySize(myQuery);
			
			if (null != objs && 0 < objs.length) {
				ResourceInfo res = new ResourceInfo();
				for (int i = 0; i < objs.length; i++) {
					LabelValueBean resourceInfo = new LabelValueBean();
					res = (ResourceInfo)objs[i];
					if(!("all".equals(key))){
						resourceInfo.setValue(res.getId());
						resourceInfo.setLabel(res.getResourceCode());
						resourceList.add(resourceInfo);
					}else{
						resourceList.add(res);
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return resourceList;
	}

	/**
	 * <p> 获得资源特定属性 </p>
	 */
	public DynaBeanDTO getResourceValue(String property,String value) {
		// TODO Auto-generated method stub
		DynaBeanDTO porpDTO = new DynaBeanDTO();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
			dc.add(Restrictions.eq(property,value));
			
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			
			if (null != objs && 0 < objs.length) {
				ResourceInfo porp = (ResourceInfo)objs[0];
				porpDTO = ResourceInfoToDTO(porp);
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return porpDTO;
	}
	
	/**
	 * <p> 获得资源申请信息 </p>
	 */
	public DynaBeanDTO getResourceUse(String property, String value) {
		// TODO Auto-generated method stub
		DynaBeanDTO porpDTO = new DynaBeanDTO();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(ResourceUse.class);
			dc.add(Restrictions.eq(property,value));
			
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			
			if (null != objs && 0 < objs.length) {
				ResourceUse porp = (ResourceUse)objs[0];
				porpDTO = ResourceUseToDTO(porp);
			}
		} catch (Exception e) {
			logger.debug(e);
			
		}
		return porpDTO;
	}
	
	/**
	 * <p> 资源审批 </p>
	 */
	public void approvceResource(IBaseDTO dto) {
		try {
			dao.saveEntity(approvceCar(dto));
		} catch (Exception e) {
			logger.debug(e);			
		}		
	}
	/**
	 * <p> 审批 <p>
	 */
	private ResourceUse approvceCar(IBaseDTO dto){
		ResourceUse ru = new ResourceUse();
		ru.setId(dto.get("id").toString());
		ru.setUserName(dto.get("applyUser").toString());
		ru.setRemark(dto.get("applyReason").toString());
		ru.setDateArea(TimeUtil.getTimeByStr(dto
				.get("startDate").toString(), "yyyy-MM-dd"));
		ru.setDateEnd(TimeUtil.getTimeByStr(dto
				.get("endDate").toString(), "yyyy-MM-dd"));
		ru.setAppointName(dto.get("operUser").toString());
//		System.out.println("  code is : "+dto.get("code").toString());
		ru.setResourceInfo(getResouceInfo("resourceCode",dto.get("code").toString()));
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		ResourceInfo ri = (ResourceInfo)dao.loadEntity(ResourceInfo.class, dto.get("code").toString());
//		ru.setResourceInfo(ri);
		ru.setResourceState(dto.get("approveType").toString());
		ru.setPrincipalName(dto.get("principalUser").toString());
		ru.setApplyState("39");
		return ru;
	}
	/**
	 * <p> 创建申请信息实例 </p>
	 * @param dto
	 * @return
	 */
	private ResourceUse createApplyObject(IBaseDTO dto){
		ResourceUse resourceUse = new ResourceUse();
		resourceUse.setId(keyService.getNext("resource_use"));
		resourceUse.setDateArea(TimeUtil.getTimeByStr(dto
				.get("startDate").toString(), "yyyy-MM-dd"));
		resourceUse.setDateEnd(TimeUtil.getTimeByStr(dto
				.get("endDate").toString(), "yyyy-MM-dd"));
		resourceUse.setUserName(dto.get("applyUser") == null ? "":dto.get("applyUser").toString());
		resourceUse.setResourceInfo(getResouceInfo("id",dto.get("code").toString()));
		resourceUse.setRemark(dto.get("applyReason") == null ? "":dto.get("applyReason").toString());
		resourceUse.setAppointName(dto.get("operUser") == null ? "":dto.get("operUser").toString());
		resourceUse.setResourceState("1");// 等待审批
		resourceUse.setApplyState("39");
		return resourceUse;
	}
	
	/**
	 * <p> 车辆信息添加 </p>
	 * @param dto
	 * @return
	 */
	private ResourceInfo createCarInfo(IBaseDTO dto) {
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setId(keyService.getNext("resource_info"));
		resourceInfo.setResourceCode(dto.get("carCode") == null ? "" : dto.get("carCode").toString());
		resourceInfo.setResourceType("39");
		resourceInfo.setResourceName(dto.get("carName") == null ? ""
				: dto.get("carName").toString());
		resourceInfo.setResourceState(dto.get("carThing") == null ? ""
				: dto.get("carThing").toString());
		resourceInfo.setPrincipalId(dto.get("operUser") == null ? ""
				: dto.get("operUser").toString());
		resourceInfo.setCreateDate(TimeUtil.getNowTime());
		resourceInfo.setRemark(dto.get("carRemark") == null ? "" : dto
				.get("carRemark").toString());
		return resourceInfo;
	}
	
	/**
	 * <p> 车辆信息更新实例 </p>
	 * @param dto
	 * @return
	 */
	private ResourceInfo updateCarInfo(IBaseDTO dto) {
		ResourceInfo resourceInfo = new ResourceInfo();

		resourceInfo.setId(dto.get("carId") == null ? "" : dto.get("carId").toString());
		resourceInfo.setResourceCode(dto.get("carCode") == null ? "" : dto.get("carCode").toString());
		resourceInfo.setResourceType("39");
		resourceInfo.setResourceName(dto.get("carName") == null ? ""
				: dto.get("carName").toString());
		resourceInfo.setResourceState(dto.get("carThing") == null ? ""
				: dto.get("carThing").toString());
		resourceInfo.setPrincipalId(dto.get("operUser") == null ? ""
				: dto.get("operUser").toString());
		resourceInfo.setCreateDate(TimeUtil.getNowTime());
		resourceInfo.setRemark(dto.get("carRemark") == null ? "" : dto
				.get("carRemark").toString());
		return resourceInfo;
	}
	
	/**
	 * <p> 获得ResourceInfo 实例 </p>
	 * @param column：字段
	 * @param key：值
	 * @return
	 */
	private ResourceInfo getResouceInfo(String column,String key){
		ResourceInfo info = new ResourceInfo();
		DetachedCriteria dc = DetachedCriteria.forClass(ResourceInfo.class);
		dc.add(Restrictions.eq(column,key));
		
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		Object[] objs = this.dao.findEntity(myQuery);
		if (null != objs && 0 < objs.length) {
			info = (ResourceInfo)objs[0];
		}
		return info;
	}

	/**
	 * <p> 创建 车辆信息DTO </p>
	 * @param info
	 * @return
	 */
	private DynaBeanDTO ResourceInfoToDTO(ResourceInfo info){
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("carId",info.getId());
		dto.set("carCode",info.getResourceCode());
		dto.set("carName",info.getResourceName());
		dto.set("carThing",info.getResourceState());
		dto.set("operUser",info.getPrincipalId());
		dto.set("carRemark",info.getRemark());
		return dto;
	}
	
	/**
	 * <p> 创建 车辆申请信息DTO </p>
	 * @param info
	 * @return
	 */
	private DynaBeanDTO ResourceUseToDTO(ResourceUse info){
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id",info.getId());
		dto.set("startDate",TimeUtil.getTheTimeStr(info.getDateArea(),"yyyy-MM-dd"));
		dto.set("endDate",TimeUtil.getTheTimeStr(info.getDateEnd(),"yyyy-MM-dd"));
		if("0".equals(info.getResourceState())){
			dto.set("state","未审批");
		}else if("1".equals(info.getResourceState())){
			dto.set("state","等待审批");
		}else{
			dto.set("state","已批准");
		}
		dto.set("applyReason",info.getRemark());
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo = info.getResourceInfo();
		String code = resourceInfo.getResourceCode();
		String carInfo = resourceInfo.getRemark();
		dto.set("code",code);
		dto.set("carInfo",carInfo);
		dto.set("applyUser", info.getUserName());
		dto.set("operUser",info.getAppointName());
		dto.set("carRemark",info.getRemark());
		return dto;
	}
	
	 public  List<LabelValueBean> getLabelList(){
		 return null;
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
	
	public  boolean haveSameResourceName(String resourceCode){
		 MeetingSearchHelp msh = new MeetingSearchHelp();
		 int i = dao.findEntitySize(msh.searchSameCarCode(resourceCode));
		 if(i>0){
			 return true;
		 }else{
			 return false;
		 }	 
	}
}
