/**
 * 
 * 项目名称：struts2
 * 制作时间：May 22, 20093:05:21 PM
 * 包名：base.zyf.web.crud.action
 * 文件名：CommonAction.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.web.crud.action;

import base.zyf.struts.action.BaseAction;
import base.zyf.web.crud.service.CommonService;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 通用的action,可以进行基本的增删改查,实现了基本的增删改查函数,
 * @author zhaoyifei
 * @version 1.0
 */
public class CommonAction extends BaseAction implements ModelDriven, Preparable{

	private Class entityClass = null;
	private Object entityObject = null;
	private String pageId = null;
	protected CommonService cs = null;
	
	
	protected String RESULT_UPDATE_SUCCESS = "updateS";
	
	protected String RESULT_UPDATE_ERROR = "updateE";
	
	protected String RESULT_DELETE_SUCCESS = "deleteS";
	
	protected String RESULT_DELETE_ERROR = "deleteE";
	
	protected String RESULT_LIST_SUCCESS = "listS";
	
	protected String RESULT_LIST_ERROR = "listE";
	
	protected String RESULT_LOAD_SUCCESS = "loadS";
	
	protected String RESULT_LOAD_ERROR = "loadE";
	
	protected String RESULT_ENTRY = "entryS";

	/**
	 * @return the cs
	 */
	public CommonService getCs() {
		return cs;
	}

	/**
	 * @param cs the cs to set
	 */
	public void setCs(CommonService cs) {
		this.cs = cs;
	}
	
	/**
	 * 
	 * 功能描述 修改一条记录函数
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String update()
	{
		cs.saveOrUpdate(this.entityObject);
		
		return RESULT_UPDATE_SUCCESS;
	}
	/**
	 * 
	 * 功能描述 物理删除一条记录函数
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String delete()
	{
		cs.delete(this.entityObject);
		
		return RESULT_DELETE_SUCCESS;
	}
	/**
	 * 
	 * 功能描述 逻辑删除一条记录函数
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String deleteLogic()
	{
		cs.deleteLogic(this.entityObject);
		
		return RESULT_DELETE_SUCCESS;
	}
	/**
	 * 
	 * 功能描述 根据条件查询记录函数
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String list()
	{
		this.setVb(cs.fetchAll(this.entityClass, pageId));
		
		return RESULT_LIST_SUCCESS;
	}
	/**
	 * 
	 * 功能描述 查询一条记录函数
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String load()
	{
		return RESULT_LOAD_SUCCESS;
	}
	/**
	 * 
	 * 功能描述 进入entry页
	 * @return
	 * May 22, 2009 3:13:22 PM
	 * @version 1.0
	 * @author 赵一非
	 */
	public String entry()
	{
		return RESULT_ENTRY;
	}

	/**
	 * @return the entityClass
	 */
	public Class getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}
	public void setEntityClass(String classpath) {
		try {
			this.entityClass = Class.forName(classpath);
		} catch (ClassNotFoundException e) {
			this.entityClass = null;
		}
	}

	public void prepare() throws Exception {
		if (this.oid == null) {
			if(this.entityClass != null)
				entityObject = this.entityClass.newInstance();
		} else {
			entityObject = cs.load(this.entityClass, oid);
		}
	}

	public Object getModel() {
		return entityObject;
	}

	

	/**
	 * @return the entityObject
	 */
	public Object getEntityObject() {
		return entityObject;
	}

	/**
	 * @param entityObject the entityObject to set
	 */
	public void setEntityObject(Object entityObject) {
		this.entityObject = entityObject;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}
