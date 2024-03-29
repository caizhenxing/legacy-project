/**
 * 
 */
package com.zyf.common.fileload;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.power.vfs.FileObject;
import com.power.vfs.FileObjectManager;
import com.zyf.common.fileupload.VfsUploadFile;
import com.zyf.exception.UnexpectedException;

/**
 * @author liangwei
 *
 */
public class EmDocFileLoadBean extends VfsUploadFile {
	 /** 当这个实体是一个刚刚上传的文件时, 这个属性就表示那个文件 */
    private FormFile formFile;
    
    /** 当这个实体是一个已经上传的文件时, 这个属性就表示那个文件 */
    private FileObject fileObject;
    
    /** 这个实体表示的文件名称 */
    private String fileName;
    
    /** 这个实体表示的文件描述 */
    private String fileDesc;
    
    /** 这个实体表示的顺序号 */
    private Long sortNo;
    
    /** 这个实体表示的备注 */
    private String memo;
    
    /** 这个实体表示的<code>MIME</code>类型 */
    private String contentType;
    
    /** 这个实体表示的以字节计算的大小 */
    private int fileSize = -1;
    
    /**表示文件的后缀名，用于前端显示相应类型图标：可以从contentType判断，目前采用截取文件名"."后字符*/
    private String suffName = "";
    
    /**删除标记*/
    private String delFlg = "0";

    public EmDocFileLoadBean() {
    	super();
    }
    
    public EmDocFileLoadBean(FileObject fileObject) {
    	super(fileObject);
    }
    
    public FileObject getFileObject() {
        return this.fileObject;
    }

    public FormFile getFormFile() {
        return formFile;
    }

    public void setFormFile(FormFile formFile) {
        this.formFile = formFile;
    }
    
    public String getFileName() {
        String fn = null;
        if (formFile != null) {
            fn = formFile.getFileName();
        } else if (fileObject != null) {
            fn = fileObject.getFileName();
        }
        
        if (StringUtils.isBlank(fn)) {
            fn = fileName;
        }
        
        return fn;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        if (contentType != null) {
            return contentType;
        }

        if (fileObject != null) {
            contentType = fileObject.getType();
        } else if (formFile != null) {
            contentType = formFile.getContentType();
        }
        
        return contentType;
    }

    public String getContentType(ServletContext sc) {
        String mime = sc.getMimeType(getFileName());
        return (mime == null ? "application/oct-stream" : mime);
    }
    
    public int getFileSize() {
        if (fileSize != -1) {
            return fileSize;
        }
        
        if (formFile != null) {
            fileSize = formFile.getFileSize();
        } else if (fileObject != null) {
            Long l = (Long) fileObject.getProperty(FileObjectManager.KEY_SIZE);
            fileSize = l.intValue();
        }
        
        return fileSize;
    }

    public boolean isUploadFile() {
        return formFile != null && StringUtils.isNotBlank(formFile.getFileName()) && formFile.getFileSize() > 0;
    }

    public InputStream getContent() {
        InputStream in = null;
        if (isUploadFile()) {
            try {
                in = formFile.getInputStream();
            } catch (Exception e) {
                throw new UnexpectedException("读取上传文件[" + getFileName() + "]时错误", e);
            }
        }

        return in;
    }

    public void destory() {
        if (isUploadFile()) {
            formFile.destroy();
        }
    }

	public String getSuffName() {
		if(getFileName().indexOf(".")>0){
			suffName = getFileName().split("\\.")[1].toLowerCase();
		}
		return suffName;
	}

	public void setSuffName(String suffName) {
		this.suffName = suffName;
	}

	/**
	 * @return the fileDesc
	 */
	public String getFileDesc() {
		return fileDesc;
	}

	/**
	 * @param fileDesc the fileDesc to set
	 */
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
}
