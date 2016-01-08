/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 11, 20092:35:56 PM
 * ������base.zyf.web.uploadfile
 * �ļ�����UploadFileBean.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.web.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * strtus2�д���struts1�е�FormFile������������
 * @author zhaoyifei
 * @version 1.0
 */
public class UploadFileBean {

	private File uploadFile;
	private String uploadFileContentType;
	private String uploadFileFileName;
	/**
	 * @return the uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * @return the uploadFileContentType
	 */
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	/**
	 * @param uploadFileContentType the uploadFileContentType to set
	 */
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	/**
	 * @return the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	/**
	 * @param uploadFileFileName the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public InputStream getInputStream() throws FileNotFoundException
	{
		return new FileInputStream(this.uploadFile);
		
	}
}
