/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-9-20下午03:24:39
 * 文件名：EntityPlus.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.hibernate3;

import java.util.HashMap;
import java.util.Map;

import com.zyf.framework.persistent.Entity;
import com.zyf.security.SecurityContextInfo;

/**
 * @author zhaoyf
 *
 */
public abstract class EntityPlus extends Entity {

	 //private Map dict=new HashMap();
	 
	// 增加标签权限属性rwCtrlType 使用当前数据访问权限，得到属性值 
	private int rwCtrlType;

	public int getRwCtrlType() {
		String key;
		key=this.getId();
		System.out.println("查询ID为："+key);
		try{
			Integer integer=(Integer)(SecurityContextInfo.getRwCtrlTypeMap().get(key));
			rwCtrlType= integer.intValue();
		}catch(Exception e){
//			e.printStackTrace();
			rwCtrlType=2;
		}
		return rwCtrlType;
	}
	
	public void setRwCtrlType(int rwCtrlType) {
		this.rwCtrlType = rwCtrlType;
	}

//	public Map getDict() {
//		return dict;
//	}
//	public void setDict(Map dict) {
//		this.dict = dict;
//	}
	
}
