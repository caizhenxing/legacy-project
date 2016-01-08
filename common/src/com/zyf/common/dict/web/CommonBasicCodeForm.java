/**
 * 
 * 制作时间：2007-8-16下午03:35:15
 * 工程名：comoon
 * 文件名：com.zyf.common.dict.webHrBasiccodeForm.java
 * 制作者：zhaoyifei
 * 
 */
package com.zyf.common.dict.web;

import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.struts.BaseFormPlus;


/**
 * @author zhaoyifei
 *
 */
public class CommonBasicCodeForm extends BaseFormPlus {

	private CommonBasicCode bean;
	private String root;
	private String parent;
	private String parentCode;

	public CommonBasicCode getBean() {
		if(bean==null)
			bean=new CommonBasicCode();
		return bean;
	}

	public void setBean(CommonBasicCode bean) {
		this.bean = bean;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public CommonBasicCode getRootNode() {
		return bean;
	}
	
	void rootNode(CommonBasicCode bean) {
		this.bean = bean;
	}

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
}
