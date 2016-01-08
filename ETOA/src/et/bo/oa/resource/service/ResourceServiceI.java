package et.bo.oa.resource.service;


import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> 资源管理接口 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-13
 * 
 */
public interface ResourceServiceI {

	/**
	 * <p> 添加资源信息 </p>
	 * @param dto
	 */
	public void addResource(IBaseDTO dto);
	
	/**
	 * <p> 添加申请信息 </p>
	 * @param dto
	 */
	public void addApply(IBaseDTO dto);
	
	/**
	 * <p> 查询资源使用情况 </p>
	 * @param dto
	 * @return
	 */
	public Object[] searchResourceUse(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> 查询资源基本信息 </p>
	 * @param dto
	 * @return
	 */
	public Object[] searhResourceInfo(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> 获得用户列表 </p>
	 * @return：用户信息（id，name）
	 */
	public Object[] getUserList(String page,IBaseDTO infoDTO);
	
	/**
	 * <p> 资源审批 </p>
	 * @param dto
	 */
	public void approvceResource(IBaseDTO dto);
	
	/**
	 * <p> 更新资源基本信息 </p>
	 * @param dto
	 */
	public void updateResourceInfo(IBaseDTO dto);
	
	/**
	 * <p> 删除资源基本信息 </p>
	 * @param dto
	 */
	public void deleteResourceInfo(String id);
	
	/**
	 * <p> 获得资源特定属性 </p>
	 * @param String：资源属性值
	 * @return
	 */
	public DynaBeanDTO getResourceValue(String property,String value);
	
	/**
	 * <p> 获得资源申请信息 </p>
	 * @param property
	 * @param value
	 * @return
	 */
	public DynaBeanDTO getResourceUse(String property,String value);
	
	public int getResourceSize();
	
	/**
	 * <p> 获得资源列表 </p>
	 * @param ResourceType
	 * @return
	 */
	public List getResourceList(String ResourceType,String key);
	/**
	 * @describe 取得会议室名LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
    public  List<LabelValueBean> getLabelList();
    /**
	 * @describe 是否有相同的资源名
	 * @param
	 * @return boolean
	 * 
	 */
    public  boolean haveSameResourceName(String resourceName);
	
}
