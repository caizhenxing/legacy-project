/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Nov 10, 20074:14:00 PM
 * �ļ�����FileLoadServiceImpl.java
 * �����ߣ�zhaoyf
 * 
 */
package com.zyf.common.fileload.service.impl;

import java.io.OutputStream;
import java.util.Iterator;

import com.zyf.common.fileload.FileLoadBean;
import com.zyf.common.fileload.service.FileLoadService;
import com.zyf.common.fileupload.VfsUploadFile;
import com.zyf.common.fileupload.VfsUploadFiles;

/**
 * @author zhaoyf
 *
 */
public class FileLoadServiceImpl implements FileLoadService {

	/* (non-Javadoc)
	 * @see com.zyf.common.fileload.service.impl.FileLoadService#uploadFile(com.zyf.common.fileload.FileLoadBean)
	 */
	public String uploadFile(FileLoadBean flb) {
		// TODO Auto-generated method stub
		VfsUploadFiles f = new VfsUploadFiles(flb.getAbsPath());
		if(null!=flb.getFiles()){				
			VfsUploadFile[] vfsfiles = new VfsUploadFile[flb.getFiles().size()];
			int index= 0;
			for(Iterator it = flb.getFiles().iterator();it.hasNext();)
			{
				VfsUploadFile file = (VfsUploadFile)it.next();   //��ȡ�ϴ��ļ���ֵ
				vfsfiles[index++]=file;
				
			}
			f.setFiles(vfsfiles);
			f.write();
		}		
		return null;
	}

	public String download(FileLoadBean flb,OutputStream os) {
		// TODO Auto-generated method stub
		return null;
	}

	public String remove(FileLoadBean flb) {
		// TODO Auto-generated method stub
		return null;
	}

}
