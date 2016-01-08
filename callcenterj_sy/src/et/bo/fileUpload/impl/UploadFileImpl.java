package et.bo.fileUpload.impl;

import et.bo.fileUpload.UploadFileService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;

public class UploadFileImpl implements UploadFileService {
	private BaseDAO dao = null;
	private KeyService	ks = null;
	public String addFile(String oldName, String filePath, String newName,
			String type) {
		// TODO Auto-generated method stub
		String id = ks.getNext("uploadFile");
		String insertSql = "INSERT INTO uploadFileTbl(id ,oldName ,filePath,newName,type) values ('"+id+"','"+oldName+"','"+filePath+"','"+newName+"','"+type+"')";
		dao.execute(insertSql);
		return id;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	
}
