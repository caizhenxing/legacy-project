/**
 * 	@(#)ModuleManagerServiceImpl.java   2006-12-11 œ¬ŒÁ03:14:03
 *	 °£ 
 *	 
 */
package et.bo.forum.moduleManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.forum.moduleManager.service.ModuleManagerService;
import et.po.ForumTopic;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author “∂∆÷¡¡
 * @version 2006-12-11
 * @see
 */
public class ModuleManagerServiceImpl implements ModuleManagerService {
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;

	private KeyService ks = null;
	
	private int num = 0;
	
	/* (non-Javadoc)
	 * @see et.bo.forum.moduleManager.service.ModuleManagerService#addModule(excellence.framework.base.dto.IBaseDTO)
	 */
	public void addModule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createModule(dto));
	}
	
	private ForumTopic createModule(IBaseDTO dto){
		ForumTopic ft = new ForumTopic();
		ft.setId(ks.getNext("ForumTopic"));
		ft.setTopicTitle(dto.get("topicTitle").toString());
		if(dto.get("forumSort").toString().equals("parent")){
			ft.setParentId("1");
		}else{
			ft.setParentId(dto.get("parentId").toString());
		}		
		ft.setTopicPhoto(dto.get("topicPhoto").toString());
		ft.setTopicDescribe(dto.get("topicDescribe").toString());
		ft.setAnswerTimes("0");
		ft.setPostNum("0");
		ft.setIsSys("1");
		ft.setIsAllowVisitorSendpost("N");
		ft.setHost(dto.get("host").toString());
		return ft;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.moduleManager.service.ModuleManagerService#deleteModule(java.lang.String)
	 */
	public void deleteModule(String id) {
		// TODO Auto-generated method stub
		ForumTopic ft = (ForumTopic) dao.loadEntity(ForumTopic.class, id);
		dao.removeEntity(ft);
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.moduleManager.service.ModuleManagerService#getSize()
	 */
	public int getSize() {
		// TODO Auto-generated method stub
		return num;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.moduleManager.service.ModuleManagerService#moduleList()
	 */
	public HashMap moduleList() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		HashMap hashmap = new HashMap();
		Object[] result = new Object[0];
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		result = (Object[])dao.findEntity(mmh.moduleQuery());
		for(int i=0,size=result.length;i<size;i++){
			ForumTopic ft = (ForumTopic)result[i];
			List moduleList = new ArrayList();
			Object[] moduleResult = (Object[])dao.findEntity(mmh.moduleQuery(ft.getId()));
			for(int j=0,jsize=moduleResult.length;j<jsize;j++){
				ForumTopic module = (ForumTopic)moduleResult[j];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", module.getId());
				dbd.set("topicTitle", module.getTopicTitle());
				moduleList.add(dbd);
			}
			hashmap.put(ft.getTopicTitle(), moduleList);
		}
		return hashmap;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.moduleManager.service.ModuleManagerService#updateModule(excellence.framework.base.dto.IBaseDTO)
	 */
	public void updateModule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		ForumTopic ft = (ForumTopic) dao.loadEntity(ForumTopic.class, dto.get("id").toString());
		ft.setTopicTitle(dto.get("topicTitle").toString());
		ft.setTopicDescribe(dto.get("topicDescribe").toString());
		ft.setParentId(dto.get("parentId").toString());
		ft.setTopicPhoto(dto.get("topicPhoto").toString());
		dao.saveEntity(ft);
	}
	
	public IBaseDTO getModuleInfo(String id){
		ForumTopic ft = (ForumTopic) dao.loadEntity(ForumTopic.class, id);
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", ft.getId());
		dbd.set("topicTitle", ft.getTopicTitle());
		dbd.set("topicDescribe", ft.getTopicDescribe());
		dbd.set("topicPhoto", ft.getTopicPhoto());
		dbd.set("parentId", ft.getParentId());
		return dbd;
	}
	
	public List<LabelValueBean> getModuleValueBean(){
		List list = new ArrayList();
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		Object[] result = (Object[])dao.findEntity(mmh.moduleLabelValue());
		for(int i=0,size=result.length;i<size;i++){
			ForumTopic ft = (ForumTopic)result[i];
			LabelValueBean label = new LabelValueBean();
			label.setLabel(ft.getTopicTitle());
			label.setValue(ft.getId());
			list.add(label);
		}
		return list;
	}
	
	public List<LabelValueBean> getAllModuleValueBean(){
		List list = new ArrayList();
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		Object[] result = (Object[])dao.findEntity(mmh.moduleLabelValue());
		for(int i=0,size=result.length;i<size;i++){
			ForumTopic ft = (ForumTopic)result[i];
			list.add(new LabelValueBean("-|"+ft.getTopicTitle(),ft.getId()));
			Object[] childrenModule = (Object[])dao.findEntity(mmh.moduleQuery(ft.getId()));
			for(int j=0,jsize=childrenModule.length;j<jsize;j++){
				ForumTopic ftc = (ForumTopic)childrenModule[j];
				list.add(new LabelValueBean("-|-|"+ftc.getTopicTitle(),ftc.getId()));
			}
		}
		return list;
	}
	
	public boolean isModuleExist(String moduleName){
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		int i = dao.findEntitySize(mmh.searchModuleName(moduleName));
		if(i!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public String getIdByModuleName(String moduleName){
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		Object[] result= (Object[])dao.findEntity(mmh.searchModuleName(moduleName));
		System.out.println(result.length);
		if(result.length==0){
			return "NULL";
		}else{
			ForumTopic ft = (ForumTopic)result[0];
		    return ft.getId();
		}
		
	}
	
	public void addAnswerTimes(String id){
		ForumTopic ft = (ForumTopic) dao.loadEntity(ForumTopic.class, id);
		int times = Integer.parseInt(ft.getAnswerTimes())+1;
		ft.setAnswerTimes(String.valueOf(times));
		ft.setUpdateTime(TimeUtil.getNowTime());
		dao.saveEntity(ft);
	}
	
	public void addPostNum(String id){
		ForumTopic ft = (ForumTopic) dao.loadEntity(ForumTopic.class, id);
		int times = Integer.parseInt(ft.getPostNum())+1;
		ft.setPostNum(String.valueOf(times));
		ft.setUpdateTime(TimeUtil.getNowTime());
		dao.saveEntity(ft);
	}
	
	public boolean updateIsModuleExist(String moduleName,String id){
		ModuleManagerHelp mmh = new ModuleManagerHelp();
		int i = dao.findEntitySize(mmh.searchModuleName(moduleName,id));
		if(i!=0){
			return true;
		}else{
			return false;
		}
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

}
