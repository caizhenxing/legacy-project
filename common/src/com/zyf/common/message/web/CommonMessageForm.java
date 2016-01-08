/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.message.web;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zyf.common.CommonServiceLocator;
import com.zyf.common.message.dto.CommonMessage;
import com.zyf.common.message.dto.CommonMessageAccept;
import com.zyf.web.BaseActionForm;

/**
 * ��Ϣ form bean
 * @since 2006-9-29
 * @author pillarliu 
 * @version $Id: CommonMessageForm.java,v 1.3 2007/12/17 11:02:39 lanxg Exp $
 */
public class CommonMessageForm  extends BaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private CommonMessage message;
	
	private CommonMessageAccept[] acceptPersons = new CommonMessageAccept[0];
	
	protected String PARAM_OID = "oid";
	
	/**�����б�list*/
	private Collection results = Collections.EMPTY_LIST;
	
	/** ���ص��ļ��� */
	private String fileName;
	
	/** ��״̬��ѯ*/
	private String status;
	
	/**��״̬ͳ��*/
	private List messageStatus;
	
	/**Ϊ�˱����������⣺�����������*/
	private String personName;
	/***/
	private String personChinese;
		
	public String getStatus() {
		return status;
	}

	public void setStatus(String type) {
		this.status = type;
	}

	public CommonMessage getMessage() {		
		if (message == null) {
			String activeOid = request.getParameter(PARAM_OID);
			if(StringUtils.isNotBlank(activeOid)){
				message = CommonServiceLocator.getMessageService().load(activeOid);
			}else{
				message = CommonMessage.prototype();
			}
		}		
		return message;
	}
	
	public void setMessage(CommonMessage coal){
		this.message = coal;
	}

	public CommonMessageAccept[] getAcceptPersons(){
		return acceptPersons;
	}	
	public void setAcceptPersons(CommonMessageAccept[] truck){
		this.acceptPersons = truck;
	}
	public CommonMessageAccept getAcceptPersons(int index) 
		throws IndexOutOfBoundsException {
		CommonMessageAccept item = acceptPersons[index];
		return item;
	}
	public void setAcceptPersons(int index, CommonMessageAccept item)
		throws IndexOutOfBoundsException {
		this.acceptPersons[index] = item;
	}
		
	/**
	 * @return the results
	 */
	public Collection getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(Collection results) {
		this.results = results;
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(List messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getPersonChinese() {
		return personChinese;
	}

	public void setPersonChinese(String personChinese) {
		this.personChinese = personChinese;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}
