/**
 * 	@(#)FileManagerServiceImpl.java   2006-9-4 ÏÂÎç05:28:05
 *	 ¡£ 
 *	 
 */
package et.bo.oa.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import et.bo.oa.file.service.FileManagerService;
import et.po.FileManager;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author yifeizhao
 * @version 2006-9-4
 * @see
 */
public class FileManagerServiceImpl implements FileManagerService {

	private ClassTreeService cts=null;
	private BaseDAO dao=null;
	
	private KeyService ks = null;
	
	FileManagerHelper fmh=new FileManagerHelper();
	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#addFile(excellence.framework.base.dto.IBaseDTO)
	 */
	public void addFile(IBaseDTO dto) {
		// TODO Auto-generated method stub
		FileManager fm=new FileManager();
		dto.loadValue(fm);
		fm.setId(ks.getNext("FILE_MANAGER"));
		dao.saveEntity(fm);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#checkFile(java.lang.String, java.lang.String, boolean)
	 */
	public void checkFile(String id, String user, boolean pass) {
		// TODO Auto-generated method stub
		FileManager fm=(FileManager)dao.loadEntity(FileManager.class, id);
		if(fm==null)
			return ;
		if(pass)
		{
			fm.setFileCheckMan(user);
			fm.setIsAvailable("1");
			dao.saveEntity(fm);
		}
		else
			delete(fm);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#delFile(java.lang.String)
	 */
	public void delFile(String id) {
		// TODO Auto-generated method stub
		FileManager fm=(FileManager)dao.loadEntity(FileManager.class, id);
		if(fm==null)
			return ;
		Object[] o=dao.findEntity(fmh.createEditionQuery(fm.getName()));
		for(int i=0,size=o.length;i<size;i++)
		{
			delete((FileManager)o[i]);
		}
		FileManager fm1=(FileManager)dao.loadEntity(FileManager.class, fm.getParentId());
		if(fm1==null)
			return;
		dao.removeEntity(fm1);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#download(java.io.OutputStream, java.lang.String)
	 */
	public void download(HttpServletResponse response, String id) {
		// TODO Auto-generated method stub
		FileManager fm=(FileManager)dao.loadEntity(FileManager.class, id);
		if(fm==null)
			return ;
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+fm.getFileName()+"."+fm.getFileType()+"\"");
        
        try {
			ServletOutputStream sos=response.getOutputStream();
			InputStream is=new FileInputStream(new File(getPath(fm)));
			byte[] buffer=new byte[1024*10];
			int off=0;
			while(is.available()>off)
			{
				is.read(buffer, off, 1024*10);
				sos.write(buffer);
				off+=1024*10;
			}
			is.close();
			sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#loadFileInfo(java.lang.String, java.lang.String)
	 */
	public IBaseDTO loadFileInfo(String id, String user) {
		// TODO Auto-generated method stub
		
		return null;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#loadFolders()
	 */
	public TreeControlI loadFolders() {
		// TODO Auto-generated method stub
		return cts.getTree(cts.ROOT,true);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.file.service.FileManagerService#resumeFile(java.lang.String)
	 */
	public void resumeFile(String id) {
		// TODO Auto-generated method stub
		FileManager fm=(FileManager)dao.loadEntity(FileManager.class, id);
		if(fm==null)
			return ;
		Object[] o=dao.findEntity(fmh.createEditionQuery(fm.getName()));
		if(o.length<2)
			return;
		FileManager fm1=(FileManager)o[1];
		fm1.setIsAvailable("1");
		dao.saveEntity(fm1);
		delete(fm);
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
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

	public void accredit(IBaseDTO dto) {
		// TODO Auto-generated method stub
		
	}
	private void delete(FileManager fm)
	{
		
	}
	private String getPath(FileManager fm)
	{
		StringBuffer path=new StringBuffer();
		return path.toString();
	}
}
