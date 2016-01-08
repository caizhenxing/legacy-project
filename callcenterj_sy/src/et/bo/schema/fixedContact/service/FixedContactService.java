/**
 * @(#)FixedContactService.java	 2008-06-10
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.schema.fixedContact.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>
 * 固定联络员管理
 * </p>
 * @version 2008-06-10
 * @author 王默
 */

public interface FixedContactService
{
	/**
   * 查询数据列表,返回记录的list。 取得查询列表数据。
   * @param dto
   * 数据传输对象
   * @param pi
   * 页面信息
   * @return 数据的list
   */
	public List fixedContactQuery(IBaseDTO dto, PageInfo pi);

	/**
   * 查询数据列表,返回全部记录的list。 取得查询列表数据。
   * @param dto
   * 数据传输对象
   * @param pi
   * 页面信息
   * @return 数据的list
   */
	public List fixedContactAllQuery();

	/**
   * 查询数据列表的条数。 取得查询列表的条数。
   * @return 得到list的条数
   */
	public int getFixedContactSize();

	/**
   * 根据客户ID查询数据列表,返回该客户的“问题”的list。 取得查询问题列表数据。
   * @return 回访数据的list
   */
	public List getQuestionList();

	/**
   * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
   * 取得某条数据的详细信息。
   * @param id
   * 取得excellence.framework.base.dto.IBaseDTO的标识
   * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
   */
	public IBaseDTO getFixedContactInfo(String id);

	/**
   * 根据电话号码取得一条数据的excellence.framework.base.dto.IBaseDTO对象
   * 根据电话号码查询用户宅电，办公电话，手机，取得某客户的详细信息。
   * @param id
   * 取得excellence.framework.base.dto.IBaseDTO的标识
   * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
   */
	public IBaseDTO getopenwinInfo(String tel);

	/**
   * 修改数据。 修改某条记录的内容。
   * @param dto
   * 要更新的的excellence.framework.base.dto.IBaseDTO对象
   * @return boolean
   */
	public boolean updateFixedContact(IBaseDTO dto);

	/**
   * 删除数据。 删除某条记录。
   * @param id
   * 要删除数据的标识
   */
	public void delFixedContact(String id);

	/**
   * 标记删除。
   * 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
   * @param id
   * 要标记删除数据的标识
   */
	public boolean isDelete(String id);

	/**
   * 添加数据。 向数据库中添加一条记录。
   * @param dto
   * 新数据的excellence.framework.base.dto.IBaseDTO对象
   */
	public void addFixedContact(IBaseDTO dto);

	/**
   * 取得新客户记录的ID
   * @return custID
   */
	public String getCustId();

	/**
   * 修改上传的图片文件在数据中的保存路径.
   * @param id
   * the id
   * @param path
   * the path
   */
	public void savePicture(String path);
}
