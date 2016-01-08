/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 27, 20074:34:22 PM
 * 文件名：SysCodeSubSysMap.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyf
 *
 */
public class SysCodeSubSysMap extends HashMap implements Map {

	public Object get(Object key) {
		// TODO Auto-generated method stub
		SysCodeModuleMap value=(SysCodeModuleMap)super.get(key);
		if(value==null)
		{
			SysCodeModuleMap scm=new SysCodeModuleMap();
			scm.setSubSysCode(key.toString());
			value=scm;
			this.put(key, value);
		}
		 return value;
	}

	
}
