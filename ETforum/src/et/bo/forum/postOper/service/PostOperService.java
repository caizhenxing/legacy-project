/**
 * 	@(#)PostOperService.java   2006-11-22 下午03:59:03
 *	 。 
 *	 
 */
package et.bo.forum.postOper.service;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 * @version 2006-11-22
 * @see
 */
public interface PostOperService {
	
	/**
	 * 发帖，将帖子信息存入数据库
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true 发帖成功 false 发帖失败
	 */
	public boolean sendPosts(IBaseDTO dto);
	
	/**
	 * 回帖，将回帖信息存入数据库
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true 回帖成功 false 回帖失败
	 */
	public boolean answerPosts(IBaseDTO dto);
	
	/**
	 * 编缉帖子，修改帖子信息
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true 编缉成功 false 编缉失败
	 */
	public boolean editPosts(IBaseDTO dto);
	
	/**
	 * 删除帖子，删除数据记录
	 * @param id String 主帖或者跟帖id
	 * @version Nov 29, 2006
	 * @return boolean true 删除成功 false 删除失败
	 */
	public String delPosts(String postsid);
	
	/**
	 * 加入收藏夹
	 * @param id String 主帖或者跟帖id
	 * @version Dec 4, 2006
	 * @return
	 */
	public void addCollection(IBaseDTO dto);

}
