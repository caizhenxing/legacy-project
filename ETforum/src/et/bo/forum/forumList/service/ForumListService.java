/**
 * 	@(#)ForumListService.java   2006-11-22 下午03:53:43
 *	 。 
 *	 
 */
package et.bo.forum.forumList.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 叶浦亮
 * @version 2006-11-22
 * @see
 */
public interface ForumListService {
	/**
	 * 前台模块查询列表
	 * @param
	 * @version 2006-11-23
	 * @return List
	 */
	public HashMap moduleQuery(String moduleId);
	/**
	 * 详细区域列表
	 * @param
	 * @version 2006-11-24
	 * @return List
	 */
	public List itemQuery(String itemId);
	/**
	 * 帖子列表查询
	 * @param 
	 * @version 2006-11-23
	 * @return List
	 */
	public List postListQuery(String itemId, PageInfo pi);
	/**
	 * 帖子查询
	 * @param
	 * @version 2006-11-23
	 * @return IBaseDTO
	 */
	public IBaseDTO postQuery(String postId);
	/**
	 * 得到记录条数
	 * @param
	 * @version 2006-11-23
	 * @return int
	 */
	public int getSizeNum();
	
	/**
	 * 回帖列表
	 * @param
	 * @version Nov 29, 2006
	 * @return
	 */
	public List answerList(String itemId,String postid, PageInfo pi);
	/**
	 * 回帖数
	 * @param
	 * @version Nov 29, 2006
	 * @return 
	 */
	public int getAnswerNum();
	/**
	 * 在线人员列表
	 * @param
	 * @version 2006-11-23
	 * @return List
	 */
	public List<String> onlineUserList(String forumId);
	/**
	 * 人数统计
	 * @param
	 * @version 2006-11-23
	 * @return
	 */
	public String onlineUserNumber();
	
	/**
	 * 根据帖子号得到标题信息
	 * @param
	 * @version Nov 30, 2006
	 * @return
	 */
	public String getPostsTitle(String postid);
}
