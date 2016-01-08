/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 27, 20074:35:48 PM
 * �ļ�����SysCodeModuleMap.java
 * �����ߣ�zhaoyf
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
public class SysCodeModuleMap extends HashMap implements Map {

	private String subSysCode;
	public String getSubSysCode() {
		return subSysCode;
	}
	public void setSubSysCode(String subSysCode) {
		this.subSysCode = subSysCode;
	}
	public Object get(Object key) {
		// TODO Auto-generated method stub
		ComboSupportList value=(ComboSupportList)super.get(key);
		if(value==null)
		{
			ComboSupportList csl=getBasicCodeService().getComboList(subSysCode,key.toString());
			value=csl;
			this.put(key, value);
			
		}
		return value;
	}
	/**
	 * 
	 * ��������
	 * 
	 * @return Sep 28, 20079:23:12 PM
	 */
	private BasicCodeService getBasicCodeService() {
		return (BasicCodeService) ServiceProvider.getService(BasicCodeService.SERVICE_NAME);
	}
}
