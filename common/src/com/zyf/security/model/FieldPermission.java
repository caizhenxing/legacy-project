/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�qware
 * ����ʱ�䣺2007-11-27����10:22:38
 * ������com.zyf.security.model
 * �ļ�����FieldPermission.java
 * �����ߣ�yushn
 * @version 1.0
 */
package com.zyf.security.model;

import java.io.Serializable;

/**
 * ������Ȩ������
 * 
 * @author yushn
 * @version 1.0
 */
public class FieldPermission implements Serializable{
	/**
	 * ��ȡ��������
	 * �ο�{@link RWCtrlType}
	 */
	private int rwCtrlType;
	public FieldPermission()
	{
		
	}
	public FieldPermission(int rwCtrlType)
	{
		this.rwCtrlType = rwCtrlType;
	}

	public int getRwCtrlType() {
		return rwCtrlType;
	}
	public void setRwCtrlType(int rwCtrlType) {
		this.rwCtrlType = rwCtrlType;
	}
}
