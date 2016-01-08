package et.bo.oa.resource.service;


import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> ��Դ����ӿ� </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-13
 * 
 */
public interface ResourceServiceI {

	/**
	 * <p> �����Դ��Ϣ </p>
	 * @param dto
	 */
	public void addResource(IBaseDTO dto);
	
	/**
	 * <p> ���������Ϣ </p>
	 * @param dto
	 */
	public void addApply(IBaseDTO dto);
	
	/**
	 * <p> ��ѯ��Դʹ����� </p>
	 * @param dto
	 * @return
	 */
	public Object[] searchResourceUse(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> ��ѯ��Դ������Ϣ </p>
	 * @param dto
	 * @return
	 */
	public Object[] searhResourceInfo(IBaseDTO dto,PageInfo pageInfo);
	
	/**
	 * <p> ����û��б� </p>
	 * @return���û���Ϣ��id��name��
	 */
	public Object[] getUserList(String page,IBaseDTO infoDTO);
	
	/**
	 * <p> ��Դ���� </p>
	 * @param dto
	 */
	public void approvceResource(IBaseDTO dto);
	
	/**
	 * <p> ������Դ������Ϣ </p>
	 * @param dto
	 */
	public void updateResourceInfo(IBaseDTO dto);
	
	/**
	 * <p> ɾ����Դ������Ϣ </p>
	 * @param dto
	 */
	public void deleteResourceInfo(String id);
	
	/**
	 * <p> �����Դ�ض����� </p>
	 * @param String����Դ����ֵ
	 * @return
	 */
	public DynaBeanDTO getResourceValue(String property,String value);
	
	/**
	 * <p> �����Դ������Ϣ </p>
	 * @param property
	 * @param value
	 * @return
	 */
	public DynaBeanDTO getResourceUse(String property,String value);
	
	public int getResourceSize();
	
	/**
	 * <p> �����Դ�б� </p>
	 * @param ResourceType
	 * @return
	 */
	public List getResourceList(String ResourceType,String key);
	/**
	 * @describe ȡ�û�������LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
    public  List<LabelValueBean> getLabelList();
    /**
	 * @describe �Ƿ�����ͬ����Դ��
	 * @param
	 * @return boolean
	 * 
	 */
    public  boolean haveSameResourceName(String resourceName);
	
}
