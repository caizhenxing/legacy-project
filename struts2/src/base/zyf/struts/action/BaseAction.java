/**
 * 
 * 项目名称：struts2
 * 制作时间：May 4, 20092:35:41 PM
 * 包名：base.zyf.struts.action
 * 文件名：BaseAction.java
 * 制作者：zhaoyifei
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
	 * 描述：
	 * 属性名：serialVersionUID
	 * 属性类型：long
	 */
	private static final long serialVersionUID = 5891618466929199497L;
	
	/**
	 * 代码信息
	 */
	private static SysCodeSubSysMap sysCodes = new SysCodeSubSysMap();
	/**
	 * 分页信息
	 */
	private PageInfo pageInfo = new PageInfo();
	
	/**
	 * 业务id
	 */
	protected String oid = null;
	/**
     * 保存页面的检索条件
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
		 * 根据查询条件的 key 得到查询条件的值, 查找不到时返回 null
		 * @param key the condition key
		 * @return condition value
		 */
		public Object getConditionValue(String key) {
			Condition condition = (Condition) getConditions().get(key);
			return condition == null ? null : condition.getValue();
		}
		
		/**
		 * 根据查询条件的 key 得到查询条件的值, 并将其填从到 list 中返回, list 中有0 或 1 个元素,
		 * 这个方法是为 extend combo 提供的, 用于 extend combo 查询时回显值
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
