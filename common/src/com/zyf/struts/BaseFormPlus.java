/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 20074:39:56 PM
 * 文件名：FormPlus.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zyf.code.SysCodeModule;
import com.zyf.code.SysCodeSubSysMap;
import com.zyf.framework.web.BaseForm;
import com.zyf.tools.Tools;

/**
 * @author zhaoyf
 *
 */
public class BaseFormPlus extends BaseForm {
	private static Map sysCodes=new SysCodeSubSysMap();
	private String searchTag;
	private String popSelectType;
	public HttpServletRequest getRequest()
	{
		return request;
	}
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
	public String getProperties(String propertyname)
	{
		Object o=Tools.getProperty(this, propertyname);
		if(null==o)
		return null;
		return o.toString();
	}
	public  Map getSysCodes() {
		return sysCodes;
	}

	public  void setSysCodes(Map sysCodes) {
		BaseFormPlus.sysCodes = sysCodes;
	}
	public static Map getCodes(String key)
	{
		return(Map)sysCodes.get(key);
	}
	public BaseFormPlus()
	{
		super();
		//this.initSysCodes();
	}
	private void initSysCodes()
	{
		sysCodes=new SysCodeSubSysMap();
	}
	public SysCodeModule getSysCodes(String key)
	{
		SysCodeModule value=(SysCodeModule)sysCodes.get(key);
		if(value==null)
		{
			SysCodeModule scm=new SysCodeModule();
			scm.setSubSysCode(key.toString());
			value=scm;
			sysCodes.put(key, value);
		}
		 return value;
	}
}
