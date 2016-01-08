/**
 * 	@(#)NewsInfoService.java   2007-1-16 ����10:45:50
 *	 �� 
 *	 
 */
package et.bo.newsInfo.service;

import java.util.List;

/**
 * @describe
 * @author Ҷ����
 * @version 2007-1-16
 * @see
 */
public interface NewsInfoService {
	/**
	 * �õ�������Ϣ
	 * @describe
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public List getNewsList(String type);
	/**
	 * ѡ��������ʽ
	 * @describe
	 * @param
	 * @version 2007-1-18
	 * @return
	 */
	public List getNewsSelectList(String type, String claasType);
	/**
	 * ȡ��������ʾ�б�
	 * @describe
	 * @param id ��������id
	 * @version 2007-1-24
	 * @return
	 */
	public List getNewsStyle(String id);
	/**
	 * ȡ�������ַ���
	 * @describe
	 * @param id ��������id
	 * @version 2007-1-24
	 * @return
	 */
	public String getNewsString(String newsSort, String styleId);
	/**
	 * ȡ�������ַ���
	 * @describe
	 * @param id ��������id
	 * @version 2007-1-24
	 * @return
	 */
	public String getNewsString();

}
