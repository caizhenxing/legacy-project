/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 31, 20071:20:24 PM
 * �ļ�����AutoCreateFile.java
 * �����ߣ�zhaoyf
 * 
 */
package com.zyf.tools.auto.create;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyf
 *
 */
public abstract class AutoCreateFile {

	protected List files=new ArrayList();
	protected abstract void readMetaData();
	protected abstract void findFiles(String name);
	public abstract void createJsp(String name);
	
	protected String realPath;
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	public List getFiles() {
		return files;
	}
	public void setFiles(List files) {
		this.files = files;
	}
	public void addFile(String file)
	{
		this.files.add(file);
	}
}
