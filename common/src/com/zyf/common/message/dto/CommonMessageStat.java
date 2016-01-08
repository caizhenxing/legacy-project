/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.message.dto;

import com.zyf.common.systemcode.SystemCode;
import com.zyf.persistent.BaseDto;

/**
 * ��Ϣ��ͳ����: ״̬�� �� ͳ����ֵ
 * ������Ϣ״̬ͳ�ƣ�δ�Ķ� / ���Ķ� ��
 * ������Ϣ״̬ͳ�ƣ��ݸ� / �ѷ��� / ɾ��
 * @since 2006-10-23
 * @author pillarliu 
 * @version $Id: CommonMessageStat.java,v 1.3 2007/12/17 11:02:39 lanxg Exp $
 */
public class CommonMessageStat  extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	
	/**״̬��ֹ�loadװ��*/
	private SystemCode status;
	
	/**״̬����*/
	private String code;
	
	/**ͳ�Ƶó�����ֵ*/
	private Integer num;

	
	public Integer getNum() {
		if(num == null){
			return new Integer(0);
		}
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public SystemCode getStatus() {
		return status;
	}

	public void setStatus(SystemCode status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
