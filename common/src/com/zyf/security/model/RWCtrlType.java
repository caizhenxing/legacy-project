/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：qware
 * 制作时间：2007-11-20下午06:17:32
 * 包名：com.zyf.security.model
 * 文件名：FieldPermissionType.java
 * 制作者：yushn
 * @version 1.0
 */
package com.zyf.security.model;

/**
 * 读写控制类型
 * @author yushn
 * @version 1.0
 */
public interface RWCtrlType {
	/**
	 * 不可见
	 */
	int SIGHTLESS = 0;
	/**
	 * 只读
	 */
	int READ_ONLY = 1;
	/**
	 * 可编辑
	 */
	int EDIT = 2;
}
