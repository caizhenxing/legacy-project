/**
 * 	@(#)PostQueryServiceImpl.java   2006-11-29 ÏÂÎç04:31:54
 *	 ¡£ 
 *	 
 */
package et.bo.forum.postQuery.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.forum.postQuery.service.PostQueryService;
import et.po.ForumCollection;
import et.po.ForumPosts;
import et.po.ForumTopic;
import et.po.ForumTopten;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author Administrator
 * @version 2006-11-29
 * @see
 */
public class PostQueryServiceImpl implements PostQueryService {
	
	int num = 0;
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;

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

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#answerTopTen()
	 */
	public List answerTopTen() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		String type = cts.getvaluebyNickName("forum_query_answerTopTen");
		Object[] result = (Object[])dao.findEntity(pqh.topTenQuery(type));
		for(int i=0, size=result.length;i<size;i++){
			ForumTopten ft = (ForumTopten)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ft.getId());
			dbd.set("postsId", ft.getPostsId());
			dbd.set("postTitle", ft.getPostTitle());
			dbd.set("clickCount", ft.getClickCount());
			dbd.set("repostCount", ft.getRepostCount());
			dbd.set("areaId", ft.getAreaId());
			dbd.set("topicId", ft.getTopicId());
			list.add(dbd);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#bestNewPost()
	 */
	public List bestNewPost() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
//		String type = cts.getvaluebyNickName("forum_query_bestNewPost");
		Object[] result = (Object[])dao.findEntity(pqh.bestNewPostList());
		for(int i=0, size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fp.getId());
			dbd.set("userkey", fp.getUserkey());
			dbd.set("moduleName", getModuleNameById(fp.getItemId()));	
			dbd.set("title", fp.getTitle());
			dbd.set("postAt", fp.getPostAt());
			dbd.set("clickTimes", fp.getClickTimes());
			dbd.set("answerTimes","0");
			dbd.set("itemId", fp.getItemId());
//			dbd.set("clickCount", fp.getClickCount());
//			dbd.set("repostCount", fp.getRepostCount());
//			dbd.set("areaId", fp.getAreaId());
//			dbd.set("topicId", fp.getTopicId());
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

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#forumHostGroom()
	 */
	public List forumHostGroom() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		String type = cts.getvaluebyNickName("forum_query_forumHostGroom");
		Object[] result = (Object[])dao.findEntity(pqh.topTenQuery(type));
		for(int i=0, size=result.length;i<size;i++){
			ForumTopten ft = (ForumTopten)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ft.getId());
			dbd.set("postsId", ft.getPostsId());
			dbd.set("postTitle", ft.getPostTitle());
			dbd.set("clickCount", ft.getClickCount());
			dbd.set("repostCount", ft.getRepostCount());
			dbd.set("areaId", ft.getAreaId());
			dbd.set("topicId", ft.getTopicId());
			list.add(dbd);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#myAnswerPost()
	 */
	public List myAnswerPost(String userId, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		Object[] result = (Object[])dao.findEntity(pqh.myAnswerPostQuery(userId, pageInfo));
		num = dao.findEntitySize(pqh.myAnswerPostQuery(userId, pageInfo));
		for(int i=0, size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fp.getId());
			dbd.set("title", fp.getTitle());
			dbd.set("postAt", fp.getPostAt());
			dbd.set("userkey", fp.getUserkey());
			dbd.set("itemid",fp.getItemId());
			list.add(dbd);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#mySavePost()
	 */
	public List mySavePost(String userId, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		Object[] result = (Object[])dao.findEntity(pqh.mySavePostQuery(userId, pageInfo));
		num = dao.findEntitySize(pqh.mySavePostQuery(userId, pageInfo));
		for(int i=0, size=result.length;i<size;i++){
			ForumCollection fp = (ForumCollection)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fp.getId());
			dbd.set("collName", fp.getCollName());
			dbd.set("collTime", fp.getCollTime());
			list.add(dbd);
		}
		return list;
	}
	
	public void delSavePost(String id){
		ForumCollection fc = (ForumCollection)dao.loadEntity(ForumCollection.class, id);
		if(fc!=null){
			dao.removeEntity(fc);
		}
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#mySendPost()
	 */
	public List mySendPost(String userId, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		Object[] result = (Object[])dao.findEntity(pqh.mySendPostQuery(userId, pageInfo));
		num = dao.findEntitySize(pqh.mySendPostQuery(userId, pageInfo));
		for(int i=0, size=result.length;i<size;i++){
			ForumPosts fp = (ForumPosts)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fp.getId());
			dbd.set("itemId", fp.getItemId());
			dbd.set("title", fp.getTitle());
			dbd.set("postAt", fp.getPostAt());
			dbd.set("userkey", fp.getUserkey());
			list.add(dbd);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#netfriendGroom()
	 */
	public List netfriendGroom() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		String type = cts.getvaluebyNickName("forum_query_netfriendGroom");
		Object[] result = (Object[])dao.findEntity(pqh.topTenQuery(type));
		for(int i=0, size=result.length;i<size;i++){
			ForumTopten ft = (ForumTopten)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ft.getId());
			dbd.set("postsId", ft.getPostsId());
			dbd.set("postTitle", ft.getPostTitle());
			dbd.set("clickCount", ft.getClickCount());
			dbd.set("repostCount", ft.getRepostCount());
			dbd.set("areaId", ft.getAreaId());
			dbd.set("topicId", ft.getTopicId());
			list.add(dbd);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#pointRand()
	 */
	public List pointRand() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		String type = cts.getvaluebyNickName("forum_query_pointRand");
		Object[] result = (Object[])dao.findEntity(pqh.topTenQuery(type));
		for(int i=0, size=result.length;i<size;i++){
			ForumTopten ft = (ForumTopten)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ft.getId());
			dbd.set("postsId", ft.getPostsId());
			dbd.set("postTitle", ft.getPostTitle());
			dbd.set("clickCount", ft.getClickCount());
			dbd.set("repostCount", ft.getRepostCount());
			dbd.set("areaId", ft.getAreaId());
			dbd.set("topicId", ft.getTopicId());
			list.add(dbd);
		}
		return list;
	}
	
	public int getSizeNum(){
		return num;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#postSearch()
	 */
	public List postSearch(String postTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postQuery.service.PostQueryService#sendPostRank()
	 */
	public List sendPostRank() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PostQueryHelp pqh = new PostQueryHelp();
		String type = cts.getvaluebyNickName("forum_query_sendPostRand");
		Object[] result = (Object[])dao.findEntity(pqh.topTenQuery(type));
		for(int i=0, size=result.length;i<size;i++){
			ForumTopten ft = (ForumTopten)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", ft.getId());
			dbd.set("postsId", ft.getPostsId());
			dbd.set("postTitle", ft.getPostTitle());
			dbd.set("clickCount", ft.getClickCount());
			dbd.set("repostCount", ft.getRepostCount());
			dbd.set("areaId", ft.getAreaId());
			dbd.set("topicId", ft.getTopicId());
			list.add(dbd);
		}
		return list;
	}

}
