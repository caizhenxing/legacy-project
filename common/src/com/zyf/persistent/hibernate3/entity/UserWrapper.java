/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project material
 */
package com.zyf.persistent.hibernate3.entity;

import java.io.Serializable;

import com.zyf.core.User;
import com.zyf.persistent.hibernate3.usertype.UserWrapperType;

/**
 * 用以包装用户的对象, 目的是根据用户名得到中文名, 需要引用的实体做类似于以下的声明 : <p/><code>
 * private UserWrapper principal = new UserWrapper();
 * </code><p/> hbm 中声明类型 <p/><code>
 * type = "user_wrapper"  或 type = "com.zyf.persistent.hibernate3.usertype.UserWrapperType"
 * </code><p/>
 * 此类型由 {@link UserWrapperType} 负责解析
 * @see UserWrapperType
 * @since 2006-7-23
 * @author java2enterprise
 * @version $Id: UserWrapper.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 */
public class UserWrapper implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -1386749014932828974L;
	
	/** 用户名 */
	protected String username;
	
	/** 中文名 */
	protected String chineseName;

	public UserWrapper() {
	}

	public UserWrapper(String username, String chineseName) {
		this.username = username;
		this.chineseName = chineseName;
	}
	
	/**
	 * 使用 {@link User} 对象来构造 user wrapper
	 * @see User#getUsername()
	 * @see User#getChineseName()
	 * @param user the user object
	 */
	public UserWrapper(User user) {
		this.username = user.getUsername();
		this.chineseName = user.getChineseName();
	}
	
	/**
	 * @return the chineseName
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * @param chineseName the chineseName to set
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
