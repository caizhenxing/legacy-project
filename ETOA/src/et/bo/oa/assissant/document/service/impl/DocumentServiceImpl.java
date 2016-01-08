package et.bo.oa.assissant.document.service.impl;

import java.util.ArrayList;
import java.util.List;


import et.bo.oa.assissant.document.config.GetFileName;
import et.bo.oa.assissant.document.service.DocumentService;
import et.bo.sys.role.service.impl.RoleSearch;
import et.po.FileInfo;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class DocumentServiceImpl implements DocumentService {
	int num = 0;
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;

	public boolean addDocInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
	    DocumentSearch documentSearch = new DocumentSearch();
		int result = dao.findEntitySize(documentSearch.seachDocExist(dto));       
		if(result!=0)
		{
			return flag;
		}
		dao.saveEntity(createDocInfo(dto));
		flag = true;
		return flag;
	}
	
	private FileInfo createDocInfo(IBaseDTO dto){
		FileInfo fi = new FileInfo();
		fi.setId(ks.getNext("FILE_INFO"));
		fi.setFolderId(dto.get("folderId")==null?"":dto.get("folderId").toString());
		
		fi.setFolderType(dto.get("folderType")==null?"":dto.get("folderType").toString());
		fi.setFolderName(dto.get("folderName")==null?"":dto.get("folderName").toString());
		fi.setFolderCode(dto.get("folderCode")==null?"":dto.get("folderCode").toString());
		fi.setFolderWord(fi.getId());
//		fi.setCreateTime(dto.get("createTime")==null?TimeUtil.getTheTimeStr("1900-0-0","yyyy-mm-dd"):TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yyyy-mm-dd"));
//		fi.setUpdateTime(dto.get("updateTime")==null?"":TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yyyy-mm-dd"));
		fi.setCreateTime(TimeUtil.getNowTime());
		fi.setUpdateTime(TimeUtil.getNowTime());
		fi.setFolderVersion("1.0");
		fi.setFolderSign("W");
		fi.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		
		return fi;
	}
	
	public boolean addConfenceDocInfo(IBaseDTO dto){
		boolean flag = false;
		DocumentSearch documentSearch = new DocumentSearch();
		int result = dao.findEntitySize(documentSearch.seachDocExist(dto));
		if(result!=0)
		{
			return flag;
		}
		dao.saveEntity(createConfenceDocInfo(dto));
		flag = true;
		return flag;
	}
	
	private FileInfo createConfenceDocInfo(IBaseDTO dto){
		FileInfo fi = new FileInfo();
		fi.setId(ks.getNext("FILE_INFO"));
		fi.setFolderId(dto.get("folderId")==null?"":dto.get("folderId").toString());
		
		fi.setFolderType(dto.get("folderType")==null?"":dto.get("folderType").toString());
		fi.setFolderName(dto.get("folderName")==null?"":dto.get("folderName").toString());
		fi.setFolderCode(dto.get("folderCode")==null?"":dto.get("folderCode").toString());
		fi.setFolderWord(fi.getId());
//		fi.setCreateTime(dto.get("createTime")==null?TimeUtil.getTheTimeStr("1900-0-0","yyyy-mm-dd"):TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yyyy-mm-dd"));
//		fi.setUpdateTime(dto.get("updateTime")==null?"":TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yyyy-mm-dd"));
		fi.setCreateTime(TimeUtil.getNowTime());
		fi.setUpdateTime(TimeUtil.getNowTime());
		fi.setFolderVersion("1.0");
		fi.setFolderSign("Y");
		fi.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		
		return fi;
	}

	public boolean updateDocInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dao.saveEntity(updateFileInfo(dto));
		flag = true;
		return flag;
	}
	
	private FileInfo updateFileInfo(IBaseDTO dto){
		FileInfo fi = new FileInfo();
		FileInfo fileInfo = (FileInfo)dao.loadEntity(FileInfo.class,dto.get("id").toString());
		fi.setId(ks.getNext("FILE_INFO"));
		fi.setFolderId(dto.get("folderId")==null?"":dto.get("folderId").toString());
		fi.setFolderCode(fileInfo.getFolderCode());
//		fi.setFolderName(dto.get("folderName")==null?"":dto.get("folderName").toString());
//		fi.setFolderType(dto.get("folderType")==null?"":dto.get("folderType").toString());
		fi.setFolderName(fileInfo.getFolderName());
		fi.setFolderType(fileInfo.getFolderType());
		fi.setFolderWord(fileInfo.getId());
		fi.setCreateTime(fileInfo.getCreateTime());
		fi.setUpdateTime(TimeUtil.getNowTime());
		fi.setFolderSign("W");
		fi.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		if(dto.get("folderVersion").toString().equals("1")){
		double ver = Double.parseDouble(fileInfo.getFolderVersion().toString())+1.0;
		fi.setFolderVersion(Double.toString(ver));
		}else{
	       String ever=GetFileName.getExtension(fileInfo.getFolderVersion().toString());
	       String hver=GetFileName.trimExtension(fileInfo.getFolderVersion().toString());
	       int v = Integer.parseInt(ever)+1;
	       String version =hver+"."+v;
	       fi.setFolderVersion(version);
		}
//		updateDocSign(dto);
		return fi;
	}
	
	public boolean shenpiDocInfoSign(IBaseDTO dto){
		boolean flag = false;
		FileInfo fi = shenpiFileInfoSign(dto);
		dao.saveEntity(fi);
		
//		if(fi.getFolderVersion().equals("1.0"))
		if(fi.getFolderSign().equals("Y"))
		{
			if(!fi.getFolderVersion().equals("1.0"))
		   {
			
		    flag = shenpiUpdate(fi.getFolderWord());
		   }
		}
		flag = true;		
		return flag;
	}
	
	private FileInfo shenpiFileInfoSign(IBaseDTO dto){
		FileInfo fi = new FileInfo();
		fi.setId(dto.get("id")==null?"":dto.get("id").toString());
		
		fi.setFolderId(dto.get("folderId")==null?"":dto.get("folderId").toString());
		fi.setFolderCode(dto.get("folderCode")==null?"":dto.get("folderCode").toString());
		fi.setFolderName(dto.get("folderName")==null?"":dto.get("folderName").toString());
		fi.setFolderType(dto.get("folderType")==null?"":dto.get("folderType").toString());
		fi.setFolderWord(dto.get("folderWord")==null?"":dto.get("folderWord").toString());
		fi.setCreateTime(dto.get("createTime")==null?TimeUtil.getTimeByStr("1900-01-01","yy-MM-dd"):TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yy-MM-dd"));
		fi.setUpdateTime(dto.get("updateTime")==null?TimeUtil.getTimeByStr("1900-01-01","yy-MM-dd"):TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yy-MM-dd"));
		fi.setFolderSign(dto.get("folderSign")==null?"NULL":dto.get("folderSign").toString());
		fi.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
		fi.setFolderVersion(dto.get("folderVersion")==null?"":dto.get("folderVersion").toString());
		return fi;
	}
	
	private boolean shenpiUpdate(String id){
		boolean flag = false;
        dao.saveEntity(shenpiUpdateSign(id));
        flag = true;
        return flag;
	}
	
	private FileInfo shenpiUpdateSign(String id){
		FileInfo fi = (FileInfo)dao.loadEntity(FileInfo.class,id);
		fi.setFolderSign("N".toString());
		
		return fi;
	}
	
	public boolean shenpiDoc(IBaseDTO dto){
		boolean flag = false;
		dao.saveEntity(shenpi4DocInfo(dto));
		flag = true;
		return flag;
	}
	
	private FileInfo shenpi4DocInfo(IBaseDTO dto){
		FileInfo fileInfo = (FileInfo)dao.loadEntity(FileInfo.class,dto.get("id").toString());
		fileInfo.setFolderSign(dto.get("folderSign").toString());
		return fileInfo;
	}
	
	private boolean deleteUpdate(String id){
		boolean flag = false;
        dao.saveEntity(deleteUpdateSign(id));
        flag = true;
        return flag;
	}
	
	private FileInfo deleteUpdateSign(String id){
		FileInfo fi = (FileInfo)dao.loadEntity(FileInfo.class,id);
		fi.setFolderSign("Y");
		
		return fi;
	}

	public boolean deleteDocInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
	    FileInfo fi = (FileInfo)dao.loadEntity(FileInfo.class,dto.get("id").toString());
	    String id = fi.getFolderWord();
	    String sign = fi.getFolderSign();
	    dao.removeEntity(fi);
	    
	    if(sign.equals("Y")){
			
		    flag = deleteUpdate(id);
			if(flag==false){
				return flag;
			}
		}
	    flag = true;
		return flag;
	}
	
	public boolean deleteDocInfo4(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
	    FileInfo fi = (FileInfo)dao.loadEntity(FileInfo.class,dto.get("id").toString());
	    dao.removeEntity(fi);
	    flag = true;
		return flag;
	}

	public int getDocSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List findDocInfo(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		DocumentSearch ds = new DocumentSearch();
		Object[] result = (Object[])dao.findEntity(ds.searchDocOperInfo(dto,pi));
		
		int s = dao.findEntitySize(ds.searchDocOperInfo(dto,pi));
		
		num = s;
		for(int i=0,size=result.length;i<size;i++){
			FileInfo fi = (FileInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id",fi.getId());
			dbd.set("folderCode",fi.getFolderCode());
			dbd.set("folderType",fi.getFolderType());
			dbd.set("folderName",fi.getFolderName());
			dbd.set("createTime",fi.getCreateTime());
			dbd.set("folderId", fi.getFolderId());
			
			dbd.set("folderVersion",fi.getFolderVersion());
			l.add(dbd);
		}
		return l;
	}
	
	public List findDocInfo2(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		DocumentSearch ds = new DocumentSearch();
		Object[] result = (Object[])dao.findEntity(ds.searchDocOperInfo2(dto,pi));
		
		int s = dao.findEntitySize(ds.searchDocOperInfo2(dto,pi));
		
		num = s;
		for(int i=0,size=result.length;i<size;i++){
			FileInfo fi = (FileInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id",fi.getId());
			dbd.set("folderId", fi.getFolderId());
			dbd.set("folderCode",fi.getFolderCode());
			dbd.set("folderType",fi.getFolderType());
			dbd.set("folderName",fi.getFolderName());
			dbd.set("createTime",fi.getCreateTime());
			dbd.set("folderVersion",fi.getFolderVersion());
			dbd.set("folderSign",fi.getFolderSign());
			l.add(dbd);
		}
		return l;
	}
	
	public List findDocInfo4(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		DocumentSearch ds = new DocumentSearch();
		Object[] result = (Object[])dao.findEntity(ds.searchDocOperInfo4(dto,pi));
		
		int s = dao.findEntitySize(ds.searchDocOperInfo4(dto,pi));
		
		num = s;
		for(int i=0,size=result.length;i<size;i++){
			FileInfo fi = (FileInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id",fi.getId());
			dbd.set("folderCode",fi.getFolderCode());
			dbd.set("folderId", fi.getFolderId());
			dbd.set("folderType",fi.getFolderType());
			dbd.set("folderName",fi.getFolderName());
			dbd.set("createTime",fi.getCreateTime());
			dbd.set("folderVersion",fi.getFolderVersion());
			dbd.set("folderSign",fi.getFolderSign());
			l.add(dbd);
		}
		return l;
	}

	public IBaseDTO getDocInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		
		FileInfo fi = (FileInfo)dao.loadEntity(FileInfo.class,id);
		dto.set("id",fi.getId());
		dto.set("folderType",fi.getFolderType());
		dto.set("folderId", fi.getFolderId());
		dto.set("folderName",fi.getFolderName());
		dto.set("folderCode",fi.getFolderCode());
		dto.set("folderWord",fi.getFolderWord());
		dto.set("createTime",fi.getCreateTime());
		dto.set("updateTime",fi.getUpdateTime());
		dto.set("folderVersion",fi.getFolderVersion());		
		dto.set("folderSign",fi.getFolderSign());
		dto.set("remark",fi.getRemark());
		return dto;
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
    public static void main(String[] args) {
		String ff="1111.9";
		int u= ff.lastIndexOf('.');
		
	}
}
