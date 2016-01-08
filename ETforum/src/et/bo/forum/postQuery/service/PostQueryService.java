/**
 * 	@(#)PostQueryService.java   2006-11-22 ����04:00:03
 *	 �� 
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
	  * @describe �������Ӳ�ѯ
	  * @param
	  * @version 2006-11-28
	  * @return
	  */
	public List bestNewPost();
	/**
	 * @describe �����Ƽ�
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List forumHostGroom();
	/**
	 * @describe �����Ƽ�
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List netfriendGroom();
	/**
	 * @describe �ظ�ʮ��
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List answerTopTen();
	/**
	 * @describe �������а�
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List sendPostRank();
	/**
	 * @describe �������а�
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List pointRand();
	/**
	 * @describe �ҵķ���
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List mySendPost(String userId,PageInfo pageInfo);
	/**
	 * @describe �ҵĻ���
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public List myAnswerPost(String userId, PageInfo pageInfo);
	/**
	 * @describe �ҵ��ղ�
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
    public List mySavePost(String userId, PageInfo pageInfo);
    /**
     * �ղؼ�����ɾ��
     * @param
     * @version 2006-12-5
     * @return
     */
    public void delSavePost(String id);
    /**
	 * @describe ������Ѱ
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
     public List postSearch(String postTitle);
     /**
      * @describe ��������
      * @param
      * @version 2006-11-30
      * @return
      */
     public int getSizeNum();
}
