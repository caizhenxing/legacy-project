/**
 * 	@(#)SearchServiceImpl.java   2006-12-21 œ¬ŒÁ01:09:16
 *	 °£ 
 *	 
 */
package et.bo.forum.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.forum.search.service.SearchService;
import et.po.ForumPosts;
import et.po.ForumTopic;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author “∂∆÷¡¡
 * @version 2006-12-21
 * @see
 */
public class SearchServiceImpl implements SearchService {

	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private int num = 0;

	public List postListQuery(IBaseDTO dto, PageInfo pageInfo){
		List list = new ArrayList();
		SearchHelp sh = new SearchHelp();
		Object[] result = (Object[])dao.findEntity(sh.postListQuery(dto, pageInfo));
		num = dao.findEntitySize(sh.postListQuery(dto, pageInfo));
		for(int i=0,size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();			
			dbd.set("id", fp.getId());
			dbd.set("userkey", fp.getUserkey());
			dbd.set("moduleName", getModuleNameById(fp.getItemId()));	
			dbd.set("title", fp.getTitle());
			dbd.set("postAt", fp.getPostAt());
			dbd.set("clickTimes", fp.getClickTimes());
			dbd.set("answerTimes","0");
			
			list.add(dbd);
		}
		return list;
	}
	
	private String getModuleNameById(String id){
		ForumTopic ft = (ForumTopic)dao.loadEntity(ForumTopic.class, id);
		if(ft!=null){
			return ft.getTopicTitle();
		}else{
			return "";
		}
	}
	
	public int getSize(){
		return num;
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
