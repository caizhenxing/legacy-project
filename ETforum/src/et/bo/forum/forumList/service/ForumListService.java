/**
 * 	@(#)ForumListService.java   2006-11-22 ����03:53:43
 *	 �� 
 *	 
 */
package et.bo.forum.forumList.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Ҷ����
 * @version 2006-11-22
 * @see
 */
public interface ForumListService {
	/**
	 * ǰ̨ģ���ѯ�б�
	 * @param
	 * @version 2006-11-23
	 * @return List
	 */
	public HashMap moduleQuery(String moduleId);
	/**
	 * ��ϸ�����б�
	 * @param
	 * @version 2006-11-24
	 * @return List
	 */
	public List itemQuery(String itemId);
	/**
	 * �����б��ѯ
	 * @param 
	 * @version 2006-11-23
	 * @return List
	 */
	public List postListQuery(String itemId, PageInfo pi);
	/**
	 * ���Ӳ�ѯ
	 * @param
	 * @version 2006-11-23
	 * @return IBaseDTO
	 */
	public IBaseDTO postQuery(String postId);
	/**
	 * �õ���¼����
	 * @param
	 * @version 2006-11-23
	 * @return int
	 */
	public int getSizeNum();
	
	/**
	 * �����б�
	 * @param
	 * @version Nov 29, 2006
	 * @return
	 */
	public List answerList(String itemId,String postid, PageInfo pi);
	/**
	 * ������
	 * @param
	 * @version Nov 29, 2006
	 * @return 
	 */
	public int getAnswerNum();
	/**
	 * ������Ա�б�
	 * @param
	 * @version 2006-11-23
	 * @return List
	 */
	public List<String> onlineUserList(String forumId);
	/**
	 * ����ͳ��
	 * @param
	 * @version 2006-11-23
	 * @return
	 */
	public String onlineUserNumber();
	
	/**
	 * �������Ӻŵõ�������Ϣ
	 * @param
	 * @version Nov 30, 2006
	 * @return
	 */
	public String getPostsTitle(String postid);
}
