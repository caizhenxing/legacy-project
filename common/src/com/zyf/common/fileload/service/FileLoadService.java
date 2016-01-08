/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 10, 20071:35:58 PM
 * 文件名：FileLoadService.java
 * 制作者：zhaoyf
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
