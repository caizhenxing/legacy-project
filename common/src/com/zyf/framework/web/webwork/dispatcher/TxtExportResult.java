/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.web.webwork.dispatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.zyf.framework.transfer.meta.FileType;
import com.zyf.framework.transfer.web.webwork.TxtExportable;
import com.zyf.framework.util.IOUtils;

/**
 * @since 2005-11-17
 * @author 王政
 * @version $Id: TxtExportResult.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class TxtExportResult extends WebWorkResultSupport {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -1566605049280546269L;
	
	private static Log logger = LogFactory.getLog(TxtExportResult.class);
	
	private String documentName;
	
	private String contentDisposition;
	
	/**
	 * 
	 * @see com.opensymphony.webwork.dispatcher.WebWorkResultSupport#doExecute(java.lang.String, com.opensymphony.xwork.ActionInvocation)
	 */
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		Action action = invocation.getAction();
		
		if (! TxtExportable.class.isInstance(action)) {
			throw new RuntimeException(" To use  " + TxtExportResult.class + " , your action must implements " + TxtExportable.class);
		}
		
		TxtExportable exportable = (TxtExportable) action;
		Assert.notNull(exportable.getContents(), " exportable.getContents() is requried. ");

		
		// 是否有数据需要导出
		boolean transferRowsExist = exportable.getContents() != null && exportable.getContents().length > 0;
			
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
                
        if (StringUtils.isBlank(documentName)) {
        	documentName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + FileType.TXT_FILE_SUFFIX;
        } else {
        	documentName = conditionalParse(documentName, invocation) + FileType.TXT_FILE_SUFFIX;
        }
        
        if (StringUtils.isBlank(contentDisposition)) {
        	contentDisposition = "attachment";
        } else {
        	contentDisposition = conditionalParse(contentDisposition, invocation);
        }
        
        //为客户端下载指定默认的下载文件名称
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", contentDisposition + ";filename=\"" + documentName + "\"");         
        
		OutputStream out = response.getOutputStream();
		
		if (! "contype".equals(request.getHeader("User-Agent")) && transferRowsExist) {				
			try {
				IOUtils.writeBytes(exportable.getContents(), out, 1024);
			} catch (IOException e) {
            	logger.error(" Error writing transfer export output ", e);
                throw new ServletException(e.getMessage(), e);
            }
		} else {
            // Code to handle "contype" request from IE
            try {               
                response.setContentLength(0);
                out.close();
            } catch (IOException e) {
            	logger.error(" Error writing transfer export output ", e);
                throw new ServletException(e.getMessage(), e);
            }
		}
		
	}

	/**
	 * @return Returns the documentName.
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName The documentName to set.
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return Returns the contentDisposition.
	 */
	public String getContentDisposition() {
		return contentDisposition;
	}

	/**
	 * @param contentDisposition The contentDisposition to set.
	 */
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}



}
