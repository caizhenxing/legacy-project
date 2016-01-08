/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 27, 20073:25:12 PM
 * 文件名：SysCodeModule.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.code;

import java.util.HashMap;
import java.util.Map;

import com.zyf.common.dict.service.BasicCodeService;
import com.zyf.container.ServiceProvider;
import com.zyf.web.view.ComboSupportList;

/**
 * @author zhaoyf
 *
 */
public class SysCodeModule {

	
	private String subSysCode;
	public String getSubSysCode() {
		return subSysCode;
	}
	public void setSubSysCode(String subSysCode) {
		this.subSysCode = subSysCode;
	}
	private Map sysCodes=new HashMap();
	
	public Map getSysCodes() {
		return sysCodes;
	}
	public void setSysCodes(Map sysCodes) {
		this.sysCodes = sysCodes;
	}
	public ComboSupportList getSysCodes(String key) {
		// TODO Auto-generated method stub
		ComboSupportList value=(ComboSupportList)sysCodes.get(key);
		if(value==null)
		{
			ComboSupportList csl=getBasicCodeService().getComboList(subSysCode,key.toString());
			value=csl;
			this.sysCodes.put(key, value);
			
		}
		return value;
	}
	/**
	 * 
	 * 功能描述
	 * 
	 * @return Sep 28, 20079:23:12 PM
	 */
	private BasicCodeService getBasicCodeService() {
		return (BasicCodeService) ServiceProvider.getService(BasicCodeService.SERVICE_NAME);
	}
}
