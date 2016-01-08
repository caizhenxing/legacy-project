/**
 * 
 * 制作时间：2007-8-21上午10:20:33
 * 文件名：CommonTableViewService.java
 * 制作者：zhaoyifie
 * 
 */
package base.zyf.web.crud.service;

import java.util.List;

/**
 * @author zhaoyifei
 *
 */
public interface CommonTableViewService{

	String SERVICE_NAME = "common.ViewRows";
	/**
	 * 
	 * 功能描述 得到用户定制的显示列
	 * @param userId 用户id
	 * @param c 表名（持久化类名）
	 * @return 列名列表
	 * 2007-8-22上午10:51:14
	 */
	public List getRows(String userId,String pageId);
	
	/**
	 * 
	 * 功能描述 得到用户定制的排序条件
	 * @param userId 用户id
	 * @param c 表名（持久化类名）
	 * @param asc 排序方式 asc，desc
	 * @return 列名列表
	 * 2007-8-22上午10:51:14
	 */
	public List getRows(String userId,String pageId,String asc);
	/**
	 * 
	 * 功能描述 得到用户定制的排序条件
	 * @param userId 用户id
	 * @param c 表名（持久化类名）
	 * @param asc 是否排序条件
	 * @return 列名列表
	 * 2007-8-22上午10:51:14
	 */
	public List getRows(String userId, String pageId, boolean asc);
	
	/**
	 * 
	 * 功能描述 所有可以显示的列名
	 * @param c 表名（持久化类名）
	 * @return 列名列表
	 * 2007-8-22上午10:54:05
	 */
	public List getRows(String pageId);
	
	/**
	 * 
	 * 功能描述 得到列的数据字典
	 * @param c
	 * @param rowName
	 * @return
	 * 2007-8-22上午11:06:12
	 */
	public String getRowsDict(String pageId,String rowName);
	
	/**
	 * 
	 * 功能描述 保存
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids,String userId,String pageId);
	
	/**
	 * 
	 * 功能描述 保存
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids,String userId,String pageId,String asc);
	
	/**
	 * 
	 * 功能描述 查询画面个性化定制数据
	 * @param pageId 页面id
	 * @param user 用户
	 * @return <code>"'aa','bb','dd'"</code>
	 * Nov 28, 2007 4:57:48 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public String getSearchTags(String pageId,String user);
	
	/**
	 * 
	 * 功能描述 保存
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids, String userId, String pageId,boolean asc);
	
	/**
	 * 
	 * 功能描述 是否有设置项
	 * @return
	 * Jan 9, 2008 1:27:23 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public boolean isSetIsNull();
}
