/**
 * 	@(#)PostQueryService.java   2006-11-22 下午04:00:03
 *	 。 
 *	 
 */
package et.bo.forum.postQuery.service;

import java.util.List;

import excellence.common.page.PageInfo;

/**
 * @author Administrator
 * @version 2006-11-22
 * @see
 */
public interface PostQueryService {
	/**
	  * @describe 最新帖子查询
	  * @param
	  * @version 2006-11-28
	  * @return
	  */
	public List bestNewPost();
	/**
	 * @describe 版主推荐
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List forumHostGroom();
	/**
	 * @describe 网友推荐
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List netfriendGroom();
	/**
	 * @describe 回复十大
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List answerTopTen();
	/**
	 * @describe 发贴排行榜
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List sendPostRank();
	/**
	 * @describe 积分排行榜
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List pointRand();
	/**
	 * @describe 我的发帖
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List mySendPost(String userId,PageInfo pageInfo);
	/**
	 * @describe 我的回贴
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List myAnswerPost(String userId, PageInfo pageInfo);
	/**
	 * @describe 我的收藏
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
    public List mySavePost(String userId, PageInfo pageInfo);
    /**
     * 收藏夹帖子删除
     * @param
     * @version 2006-12-5
     * @return
     */
    public void delSavePost(String id);
    /**
	 * @describe 帖子搜寻
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
     public List postSearch(String postTitle);
     /**
      * @describe 帖子数量
      * @param
      * @version 2006-11-30
      * @return
      */
     public int getSizeNum();
}
