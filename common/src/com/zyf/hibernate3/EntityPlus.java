/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-9-20����03:24:39
 * �ļ�����EntityPlus.java
 * �����ߣ�zhaoyf
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
	 
	// ���ӱ�ǩȨ������rwCtrlType ʹ�õ�ǰ���ݷ���Ȩ�ޣ��õ�����ֵ 
	private int rwCtrlType;

	public int getRwCtrlType() {
		String key;
		key=this.getId();
		System.out.println("��ѯIDΪ��"+key);
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
