/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：qware
 * 制作时间：2007-11-27上午10:22:38
 * 包名：com.zyf.security.model
 * 文件名：Role.java
 * 制作者：yushn
 * @version 1.0
 */
package com.zyf.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 * @author yushn
 * @version 1.0
 */
public class Role implements Serializable{
	/**
	 * 角色名称
	 */
	String name;
	/**
	 * 角色标识代码,用来唯一识别一个角色
	 */
	String code;

}
