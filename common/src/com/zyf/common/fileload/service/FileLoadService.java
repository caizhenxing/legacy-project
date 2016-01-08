/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Nov 10, 20071:35:58 PM
 * �ļ�����FileLoadService.java
 * �����ߣ�zhaoyf
 * 
 */
package com.zyf.common.fileload.service;

import java.io.OutputStream;

import com.zyf.common.fileload.FileLoadBean;

/**
 * @author zhaoyf
 *
 */
public interface FileLoadService {

	public static final String SERVICE_NAME = "common.FileLoadService";
	
	public String uploadFile(FileLoadBean flb);
	
	public String remove(FileLoadBean flb);
	
	public String download(FileLoadBean flb,OutputStream os);
}
