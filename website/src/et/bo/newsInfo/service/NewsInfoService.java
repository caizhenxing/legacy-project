/**
 * 	@(#)NewsInfoService.java   2007-1-16 上午10:45:50
 *	 。 
 *	 
 */
package et.bo.newsInfo.service;

import java.util.List;

/**
 * @describe
 * @author 叶浦亮
 * @version 2007-1-16
 * @see
 */
public interface NewsInfoService {
	/**
	 * 得到新闻信息
	 * @describe
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public List getNewsList(String type);
	/**
	 * 选择新闻样式
	 * @describe
	 * @param
	 * @version 2007-1-18
	 * @return
	 */
	public List getNewsSelectList(String type, String claasType);
	/**
	 * 取得新闻显示列表
	 * @describe
	 * @param id 新闻区域id
	 * @version 2007-1-24
	 * @return
	 */
	public List getNewsStyle(String id);
	/**
	 * 取得新闻字符串
	 * @describe
	 * @param id 新闻区域id
	 * @version 2007-1-24
	 * @return
	 */
	public String getNewsString(String newsSort, String styleId);
	/**
	 * 取得新闻字符串
	 * @describe
	 * @param id 新闻区域id
	 * @version 2007-1-24
	 * @return
	 */
	public String getNewsString();

}
