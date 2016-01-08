/**
 * 	@(#)PostOperServiceImpl.java   Nov 29, 2006 8:53:21 AM
 *	 。 
 *	 
 */
package et.bo.forum.postOper.service.impl;

import et.bo.forum.moduleManager.service.ModuleManagerService;
import et.bo.forum.point.service.PointService;
import et.bo.forum.postOper.service.PostOperService;
import et.po.ForumCollection;
import et.po.ForumPostContent;
import et.po.ForumPosts;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 * @version Nov 29, 2006
 * @see
 */
public class PostOperServiceImpl implements PostOperService {
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	private PointService pointService = null;
	
	private ModuleManagerService moduleManagerService = null;
	//默认点击数，帖子
	private String DEFAULT_CLICKTIMES = "0";
	//删除主帖
	private String DEL_THE_MAIN_POSTS = "delmain";
	//删除子帖
	private String DEL_THE_IN_POSTS = "delin";

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

	/* (non-Javadoc)
	 * @see et.bo.forum.postOper.service.PostOperService#answerPosts(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean answerPosts(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String id = ks.getNext("forum_posts");
		dto.set("id", id);
		dao.saveEntity(anPosts(dto));
		dao.saveEntity(answerPostsContent(dto));
		moduleManagerService.addAnswerTimes(dto.get("itemid").toString());
		//修改帖子时间
		updateModPostTime(dto);
		flag = true;
		return flag;
	}
	//将回帖信息存入数据库
	private ForumPosts anPosts(IBaseDTO dto){
		ForumPosts fp = new ForumPosts();
		String id = (String)dto.get("id");
		fp.setId(id);
		fp.setItemId((String)dto.get("itemid"));
		fp.setUserkey((String)dto.get("userkey"));
		fp.setParentId((String)dto.get("parentid"));
		fp.setClickTimes(DEFAULT_CLICKTIMES);
		fp.setTitle((String)dto.get("title"));
		//fp.setTitleDescribe((String)dto.get("titledescribe"));
		fp.setPostAt(TimeUtil.getNowTime());
		fp.setIpFrom((String)dto.get("ipfrom"));
		//fp.setModTime(TimeUtil.getNowTime());
		return fp;
	}
	//将回帖信息存入数据库
	private ForumPostContent answerPostsContent(IBaseDTO dto){
		ForumPostContent fpc = new ForumPostContent();
		String id = (String)dto.get("id");
		fpc.setId(id);
		fpc.setContent((String)dto.get("content"));
		return fpc;
	}
	//修改帖子时间
	private void updateModPostTime(IBaseDTO dto){
		String parentid = (String)dto.get("parentid");
		ForumPosts fp = (ForumPosts)dao.loadEntity(ForumPosts.class, parentid);
		fp.setModTime(TimeUtil.getNowTime());
		dao.updateEntity(fp);
	}
	

	/* (non-Javadoc)
	 * @see et.bo.forum.postOper.service.PostOperService#delPosts(java.lang.String)
	 */
	public String delPosts(String postsid) {
		// TODO Auto-generated method stub
		String delPosts = DEL_THE_IN_POSTS;
		ForumPosts fp = (ForumPosts)dao.loadEntity(ForumPosts.class, postsid);
		ForumPostContent fpc = (ForumPostContent)dao.loadEntity(ForumPostContent.class, postsid);
		String parentid = fp.getParentId();
		if (parentid.equals(postsid)) {
			OperSearch os = new OperSearch();
			Object[] postTemp = dao.findEntity(os.postsList(postsid));
			Object[] contentTemp = dao.findEntity(os.postsContentList(postsid));
			for(int i=0,size=postTemp.length;i<size;i++){
				ForumPosts fps = (ForumPosts)postTemp[i];
				dao.removeEntity(fps);
			}
			for(int j=0,jsize=contentTemp.length;j<jsize;j++){
				ForumPostContent fpcs = (ForumPostContent)contentTemp[j];
				dao.removeEntity(fpcs);
			}
			delPosts = DEL_THE_MAIN_POSTS;
			return delPosts;
		}
		dao.removeEntity(fp);
		dao.removeEntity(fpc);
		return delPosts;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postOper.service.PostOperService#editPosts(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean editPosts(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.postOper.service.PostOperService#sendPosts(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean sendPosts(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String id = ks.getNext("forum_posts");
		dto.set("id", id);
		dao.saveEntity(savePosts(dto));
		dao.saveEntity(savePostsContent(dto));
		moduleManagerService.addPostNum(dto.get("itemid").toString());
		flag = true;
		return flag;
	}
	//将帖子信息存入数据库
	private ForumPosts savePosts(IBaseDTO dto){
		ForumPosts fp = new ForumPosts();
		String id = (String)dto.get("id");
		fp.setId(id);
		fp.setItemId((String)dto.get("itemid"));
		fp.setUserkey((String)dto.get("userkey"));
		fp.setParentId(id);
		fp.setClickTimes(DEFAULT_CLICKTIMES);
		fp.setTitle((String)dto.get("title"));
		fp.setTitleDescribe((String)dto.get("titledescribe"));
		fp.setPostAt(TimeUtil.getNowTime());
		fp.setIpFrom((String)dto.get("ipfrom"));
		fp.setModTime(TimeUtil.getNowTime());
		return fp;
	}
	//将帖子内容信息存入数据库
	private ForumPostContent savePostsContent(IBaseDTO dto){
		ForumPostContent fpc = new ForumPostContent();
		String id = (String)dto.get("id");
		fpc.setId(id);
		fpc.setContent((String)dto.get("content"));
		return fpc;
	}
	
	//将帖子放入收藏夹(将信息存入数据库)
	public void addCollection(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(saveCollection(dto));
	}
	
	private ForumCollection saveCollection(IBaseDTO dto){
		ForumCollection fc = new ForumCollection();
		fc.setId(ks.getNext("forum_collection"));
		fc.setUId((String)dto.get("username"));
		String postsid = (String)dto.get("postsid");
		fc.setPostsId(postsid);
		ForumPosts fp = (ForumPosts)dao.loadEntity(ForumPosts.class, postsid);
		fc.setCollName(fp.getTitle());
		fc.setCollTime(TimeUtil.getNowTime());
		return fc;
	}

	public ModuleManagerService getModuleManagerService() {
		return moduleManagerService;
	}

	public void setModuleManagerService(ModuleManagerService moduleManagerService) {
		this.moduleManagerService = moduleManagerService;
	}

	public PointService getPointService() {
		return pointService;
	}

	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}

}
