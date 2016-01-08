/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-9-6
 * 文件名：CommonBasicCodeInitForm.java
 * 制作者：李鹤
 * 
 */
package com.zyf.common.basiccode.web;

import com.zyf.struts.BaseFormPlus;

public class CommonBasicCodeInitForm extends BaseFormPlus {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 操作结果信息
	 */
	private String message;

	/**
	 * 页面活性控制
	 */
	private String flag[];

	public String[] getFlag() {
		return flag;
	}

	public void setFlag(String flag[]) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
