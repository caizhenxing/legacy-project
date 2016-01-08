package et.bo.oa.resource.service.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import et.bo.oa.resource.service.ResourceServiceI;
import et.po.ResourceInfo;
import et.po.ResourceUse;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> 资源管理 实现 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-13
 * 
 */
public class ResourceServiceImpl implements ResourceServiceI {

	private Log logger = LogFactory.getLog(ResourceServiceImpl.class);

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private int sizeNum = 0;

	public ResourceServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 添加资源信息 </p>
	 * 
	 * @param dto
	 */
	public void addResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {
			this.dao.saveEntity(createResourceInfo(dto));
		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of addResource is : "
					+ e.getMessage());
		}
	}

	/**
	 * <p> 添加申请信息 </p>
	 * 
	 * @param dto
	 */
	public void addApply(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of addApply is : "
					+ e.getMessage());
		}
	}

	/**
	 * <p> 查询资源使用情况 </p>
	 * 
	 * @param dto
	 * @return
	 */
	public Object[] searchResourceUse(IBaseDTO dto,PageInfo pageInfo) {
		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of searchResourceUse is : "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * <p> 查询资源基本信息 </p>
	 * 
	 * @param dto
	 * @return
	 */
	public Object[] searhResourceInfo(IBaseDTO dto,PageInfo pageInfo) {
		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
			logger.debug(e);
			System.out.println("The Exception of searhResourceInfo is : "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * <p> 创建 ResourceInfo 实例 </p>
	 * 
	 * @param dto
	 * @return
	 */
	private ResourceInfo createResourceInfo(IBaseDTO dto) {
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setId(keyService.getNext("resource_info"));
		resourceInfo.setResourceType(dto.get("resourceType") == null ? "" : dto
				.get("resourceType").toString());
		resourceInfo.setResourceState(dto.get("resourceThing") == null ? ""
				: dto.get("resourceThing").toString());
		resourceInfo.setPrincipalId(dto.get("resourcePrincipal") == null ? ""
				: dto.get("resourcePrincipal").toString());
		resourceInfo.setRemark(dto.get("resourceRemark") == null ? "" : dto
				.get("resourceRemark").toString());
		return resourceInfo;
	}

	/**
	 * <p> 创建 ResourceUse 实例 </p>
	 * 
	 * @param dto
	 * @return
	 */
	private ResourceUse createResourceUse(IBaseDTO dto) {
		ResourceUse resourceUse = new ResourceUse();
		resourceUse.setId(keyService.getNext("resource_info"));
//		resourceUse.setResourceType(dto.get("resourceType") == null ? "" : dto
//				.get("resourceType").toString());
		resourceUse.setResourceState(dto.get("resourceThing") == null ? ""
				: dto.get("resourceThing").toString());
		resourceUse.setPrincipalName(dto.get("resourcePrincipal") == null ? ""
				: dto.get("resourcePrincipal").toString());
		resourceUse.setRemark(dto.get("resourceRemark") == null ? "" : dto
				.get("resourceRemark").toString());
		return resourceUse;
	}
	
	/**
	 * <p> PO to DTO </p>
	 * 
	 * @description : ResourceInfo to DTO
	 * @return
	 */
	private DynaBeanDTO resourceInfoToDyna(ResourceInfo resourceInfo) {
		DynaBeanDTO beanDTO = new DynaBeanDTO();
		beanDTO.set("resourceId", resourceInfo.getId());
		beanDTO.set("resourceType", resourceInfo.getResourceType());
		beanDTO.set("resourceThing", resourceInfo.getResourceState());
		beanDTO.set("resourcePrincipal", resourceInfo.getPrincipalId());
		beanDTO.set("resourceRemark", resourceInfo.getRemark());
		return beanDTO;
	}

	public  boolean haveSameResourceName(String resourceId){
		return true;
	}
	
	public int getResourceSize() {
		return sizeNum;
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

	public Object[] getUserList(String page, IBaseDTO infoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getResourceList(String ResourceType,String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateResourceInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}

	public void deleteResourceInfo(String id) {
		// TODO Auto-generated method stub
		
	}

	public DynaBeanDTO getResourceValue(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public DynaBeanDTO getResourceUse(String property, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void approvceResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}
	 public  List<LabelValueBean> getLabelList(){
		 return null;
	 }

}
