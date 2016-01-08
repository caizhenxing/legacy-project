/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 4, 20092:35:41 PM
 * ������base.zyf.struts.action
 * �ļ�����BaseAction.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import base.zyf.common.code.SysCodeSubSysMap;
import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.spring.SpringRunningContainer;
import base.zyf.web.condition.Condition;
import base.zyf.web.crud.service.ViewBean;
import base.zyf.web.page.PageInfo;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	/**
	 * session
	 */
	protected Map<String, Object> sessionAttri;
	/**
	 * HttpServletRequest
	 */
	protected HttpServletRequest request;
    /**
     * HttpServletResponse
     */
	protected HttpServletResponse response;
	/**
	 * ������
	 * ��������serialVersionUID
	 * �������ͣ�long
	 */
	private static final long serialVersionUID = 5891618466929199497L;
	
	/**
	 * ������Ϣ
	 */
	private static SysCodeSubSysMap sysCodes = new SysCodeSubSysMap();
	/**
	 * ��ҳ��Ϣ
	 */
	private PageInfo pageInfo = new PageInfo();
	
	/**
	 * ҵ��id
	 */
	protected String oid = null;
	/**
     * ����ҳ��ļ�������
     */
    private Map<String,Condition> conditions = new LinkedHashMap<String,Condition>();
   
    private ViewBean vb;
    
    
	/**
	 * @return the vb
	 */
	public ViewBean getVb() {
		return vb;
	}
	/**
	 * @param vb the vb to set
	 */
	public void setVb(ViewBean vb) {
		this.vb = vb;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	 /**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Map<String,Condition> getConditions() {
	        return conditions;
	    }


	    public void setConditions(Map<String,Condition> conditions) {
	        this.conditions = conditions;
	    }
	    
	    public void setConditions(String propertyName, Condition condition) {
	        this.conditions.put(propertyName, condition);
	    }
	    /**
		 * ���ݲ�ѯ������ key �õ���ѯ������ֵ, ���Ҳ���ʱ���� null
		 * @param key the condition key
		 * @return condition value
		 */
		public Object getConditionValue(String key) {
			Condition condition = (Condition) getConditions().get(key);
			return condition == null ? null : condition.getValue();
		}
		
		/**
		 * ���ݲ�ѯ������ key �õ���ѯ������ֵ, ��������ӵ� list �з���, list ����0 �� 1 ��Ԫ��,
		 * ���������Ϊ extend combo �ṩ��, ���� extend combo ��ѯʱ����ֵ
		 * @param key the condition key
		 * @return list fill with condition value
		 */
		public List getConditionValues(String key) {
			List list = new ArrayList(1);
			Object value = getConditionValue(key);
			if (value != null) {
				list.add(getConditionValue(key));			
			}
			return list;
		}
	
	
	private String searchTag;
	private String popSelectType;
	
	public String getPopSelectType() {
		return popSelectType;
	}
	public void setPopSelectType(String popSelectType) {
		this.popSelectType = popSelectType;
	}
	public String getSearchTag() {
		return searchTag;
	}
	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	
	private ClassTreeService getClassTreeService() {
		return (ClassTreeService) SpringRunningContainer
				.getService(ClassTreeService.SERVICE_NAME);
	}
	/**
	 * @return the sysCodes
	 */
	public SysCodeSubSysMap getSysCodes() {
		return sysCodes;
	}
	/**
	 * @param sysCodes the sysCodes to set
	 */
	public void setSysCodes(SysCodeSubSysMap sysCodes) {
		BaseAction.sysCodes = sysCodes;
	}
	public void setSession(Map<String, Object> arg0) {
		this.sessionAttri = arg0;
		
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		
	}
}
