/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：qware
 * 制作时间：2007-11-27上午10:22:38
 * 包名：com.zyf.security.model
 * 文件名：FieldPermission.java
 * 制作者：yushn
 * @version 1.0
 */
package com.zyf.security.model;

import java.io.Serializable;

/**
 * 数据项权限属性
 * 
 * @author yushn
 * @version 1.0
 */
public class FieldPermission implements Serializable{
	/**
	 * 读取控制类型
	 * 参考{@link RWCtrlType}
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
