/*
 * @(#)MessagesService.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */


package et.bo.messages.service;

import java.util.List;
import java.util.Map;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>消息管理</p>.
 * 
 * @author nie
 */

public interface MessagesService {

	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。.
	 * 
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * 
	 * @return 数据的list
	 */
	public List messagesQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。.
	 * 
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * 
	 * @return 数据的list
	 */
	public List messagesAdminQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。.
	 * 
	 * @return 得到list的条数
	 */
	public int getMessagesSize(); 
	
	/**
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。.
	 * 
	 * @return 得到list的条数
	 */
	public int getMessagesAdminSize(); 
	
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * 
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * 
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getMessagesInfo(String id);
	
	/**
	 * 修改数据。
	 * 修改某条记录的内容。.
	 * 
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * 
	 * @return boolean
	 */
	public boolean updateMessages(IBaseDTO dto);
	
	/**
	 * 删除数据。
	 * 删除某条记录。.
	 * 
	 * @param id 要删除数据的标识
	 */
	public void delMessages(String id);
	
	/**
	 * 标记删除。
	 * 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。.
	 * 
	 * @param id 要标记删除数据的标识
	 * 
	 * @return true, if checks if is delete
	 */
	public boolean isDelete(String id);
	
	/**
	 * 添加数据。
	 * 向数据库中添加一条记录。.
	 * 
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addMessages(IBaseDTO dto);
	
	/**
	 * 回复数据
	 * 向数据库中添加一条记录。.
	 * 
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void backMessages(IBaseDTO dto);
	
	/**
	 * 检查是否有新的短消息.
	 * 
	 * @param user the user
	 * 
	 * @return true 有，false 没有
	 */
	public boolean isHaveMessage(String user);
	
	/**
	 * 取得没有处理的审核条目.
	 * 
	 * @param user the user
	 * 
	 * @return true 有，false 没有
	 */
	public String getStateCount(String user);
    /**
     * 得到未读取的消息
     * return List<MessageInfo>
     */
	void appendAllNonReadMsg();
	/**
	 * 人员列表为查询页面提供数据
	 * 
	 * @return the list< label value bean>
	 */
	public List<LabelValueBean> getUserList();
	
	/**
	 * 删除短消息（全选删除）
	 * @param str
	 */
	void delAllMessages(String[] str);
	
	//////////////////////////////zhang feng add test flexgrid///////////////////////////////////////
	
	Map messageList();
	
	///////////////////////////////////////////

}
