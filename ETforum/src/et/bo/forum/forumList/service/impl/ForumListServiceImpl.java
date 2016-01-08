package et.bo.forum.forumList.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import et.bo.forum.common.ReplaceString;
import et.bo.forum.forumList.service.ForumListService;
import et.po.ForumPostContent;
import et.po.ForumPosts;
import et.po.ForumTopic;
import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class ForumListServiceImpl implements ForumListService {
	
    private BaseDAO dao = null;
	
	private int num = 0;
	
	private int ANSWER_COUNT = 0;

	public HashMap moduleQuery(String moduleId) {
		// TODO Auto-generated method stub
		HashMap hashmap = new HashMap();
		Object[] result = new Object[0];
		ForumListHelp flh = new ForumListHelp();
		if(moduleId.equals("1")){
			result = (Object[])dao.findEntity(flh.moduleQuery(moduleId));
		}else{
			result = (Object[])dao.findEntity(flh.parentModuleQuery(moduleId));
		}
		
		for(int i=0,size=result.length;i<size;i++){
			ForumTopic ft = (ForumTopic)result[i];
			List itemList = new ArrayList();
			Object[] itemResult = (Object[])dao.findEntity(flh.moduleQuery(ft.getId()));
			for(int j=0,jsize=itemResult.length;j<jsize;j++){
				ForumTopic smitem = (ForumTopic)itemResult[j];
				itemList.add(ItemToDTO(smitem));
			}
//			list.add(SysModuleToDTO(sm));
			//System.out.println(itemList.size());
			hashmap.put(ft.getTopicTitle(), itemList);
		}
		return hashmap;
	}
	
//	private DynaBeanDTO SysModuleToDTO(SysModule sm){
//		DynaBeanDTO dbd = new DynaBeanDTO();
//		dbd.set("id", sm.getId());
//		dbd.set("name", sm.getName());
//		dbd.set("remark", sm.getRemarks());
//		return dbd;
//	}
	
	public List itemQuery(String itemId){
		List list = new ArrayList();
		ForumListHelp flh = new ForumListHelp();
		Object[] result = (Object[])dao.findEntity(flh.moduleQuery(itemId));
		for(int i=0,size=result.length;i<size;i++){
			ForumTopic ft = (ForumTopic)result[i];
			List itemList = new ArrayList();
			Object[] itemResult = (Object[])dao.findEntity(flh.moduleQuery(ft.getId()));
			for(int j=0,jsize=itemResult.length;j<jsize;j++){
				ForumTopic smitem = (ForumTopic)itemResult[j];
				itemList.add(ItemToDTO(ft));
			}
			list.add(itemList);
		}
		return list;
	}
	
	private DynaBeanDTO ItemToDTO(ForumTopic ft){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", ft.getId());
		dbd.set("name", ft.getTopicTitle());
		dbd.set("topicDescribe", ft.getTopicDescribe());
		dbd.set("answerTimes", ft.getAnswerTimes());
		dbd.set("postNum", ft.getPostNum());
		dbd.set("updateTime", ft.getUpdateTime());
		dbd.set("host", ft.getHost());
		return dbd;
	}

	public List<String> onlineUserList(String forumId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String onlineUserNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public List postListQuery(String itemId, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		ForumListHelp flh = new ForumListHelp();
		Object[] result = new Object[0];
		try {
			result = (Object[])dao.findEntity(flh.postListQuery(itemId, pi));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		num = dao.findEntitySize(flh.postListQuery(itemId, pi));
		System.out.println(num);
		for(int i=0,size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			list.add(forumPostToDTO(fp));
		}
		return list;
	}
	
	private DynaBeanDTO forumPostToDTO(ForumPosts fp){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", fp.getId());
		dbd.set("userkey", fp.getUserkey());
		dbd.set("title", fp.getTitle());
		dbd.set("postAt", fp.getPostAt());
		dbd.set("clickTimes", fp.getClickTimes());
		return dbd;
	}
	
	public int getSizeNum(){
		return num;
	}

	public IBaseDTO postQuery(String postId) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public List answerList(String itemId, String postid, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		ForumListHelp flh = new ForumListHelp();
		Object[] result = null;
		try{
			result = (Object[])dao.findEntity(flh.answerList(itemId, postid, pi));
		}catch(Exception e){
			e.printStackTrace();
		}
		ANSWER_COUNT = dao.findEntitySize(flh.answerList(itemId, postid, pi));
		for(int i=0,size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fp.getId());
			dbd.set("title", fp.getTitle());
			dbd.set("postat", fp.getPostAt());
			dbd.set("userkey", fp.getUserkey());
			dbd.set("ip", fp.getIpFrom());
			ForumPostContent fpc = (ForumPostContent)dao.loadEntity(ForumPostContent.class, fp.getId());
			ReplaceString r = new ReplaceString();
			dbd.set("content", fpc.getContent()==null?"":r.ReplaceStr(fpc.getContent(), ""));
			//得到用户信息
			Object[] forumUser = null;
			try{
				forumUser = (Object[])dao.findEntity(flh.forumUserInfo(fp.getUserkey()));
			}catch(Exception e){
				e.printStackTrace();
			}
			for(int j=0,ssize=forumUser.length;j<ssize;j++){
				ForumUserInfo fui = (ForumUserInfo)forumUser[j];
				dbd.set("name", fui.getName());
				dbd.set("regtime", fui.getRegisterDate());
			}
			list.add(dbd);
		}
		return list;
	}

	public int getAnswerNum() {
		// TODO Auto-generated method stub
		return ANSWER_COUNT;
	}

	public String getPostsTitle(String postid) {
		// TODO Auto-generated method stub
		String title = "";
		ForumListHelp flh = new ForumListHelp();
		Object[] result = null;
		result = (Object[])dao.findEntity(flh.titleByPostid(postid));
		ForumPosts fp = (ForumPosts)result[0];
		title = fp.getTitle();
		return title;
	}

}
