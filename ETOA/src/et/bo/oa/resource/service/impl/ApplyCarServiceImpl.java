package et.bo.oa.resource.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import et.bo.oa.resource.service.ResourceServiceI;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * <p> 车辆申请审批Service实现 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-17
 * 
 */
public class ApplyCarServiceImpl implements ResourceServiceI {

	private Log logger = LogFactory.getLog(ApplyCarServiceImpl.class);

	private BaseDAO dao = null;

	private KeyService keyService = null;

	private static int sizeNum = 0;
	
	public ApplyCarServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addResource(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}

	public void addApply(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}

	public Object[] searchResourceUse(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] searhResourceInfo(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getUserList(String page, IBaseDTO infoDTO) {
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

	public int getResourceSize() {
		// TODO Auto-generated method stub
		return sizeNum;
	}

	public List getResourceList(String ResourceType, String key) {
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
	 
	 public  boolean haveSameResourceName(String resourceId){
		 return true;
	 }

}
