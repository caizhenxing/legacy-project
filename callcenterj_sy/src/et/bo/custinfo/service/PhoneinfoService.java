package et.bo.custinfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface PhoneinfoService
{
	/**
   * 查询数据列表,返回记录的list。 取得查询列表数据。
   * @param dto
   * 数据传输对象
   * @param pi
   * 页面信息
   * @return 数据的list
   */
	public List phoneinfoQuery(IBaseDTO dto, PageInfo pi);
	public List phoneinfoQuery2(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 得到电话列表的信息
	 * @return
	 */
	public int phoneSize();

	/**
   * 查询数据列表,返回全部记录的list。 取得查询列表数据。
   * @param dto
   * 数据传输对象
   * @param pi
   * 页面信息
   * @return 数据的list
   */
	public List phoneinfoAllQuery();

	/**
   * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
   * 取得某条数据的详细信息。
   * @param id
   * 取得excellence.framework.base.dto.IBaseDTO的标识
   * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
   */
	public IBaseDTO getPhoneinfo(String id);

	/**
   * 修改数据。 修改某条记录的内容。
   * @param dto
   * 要更新的的excellence.framework.base.dto.IBaseDTO对象
   * @return boolean
   */
	public boolean updatePhoneinfo(IBaseDTO dto);
	public boolean updateLinkManinfo(IBaseDTO dto);

	/**
   * 删除数据。 删除某条记录。
   * @param id
   * 要删除数据的标识
   */
	public void delPhoneinfo(String id);

	/**
   * 添加数据。 向数据库中添加一条记录。
   * @param dto
   * 新数据的excellence.framework.base.dto.IBaseDTO对象
   */
	public void addPhoneinfo(IBaseDTO dto);
	public void addLinkManinfo(IBaseDTO dto);
	
	/**
	 * 专家类别信息
	 * @param expertType
	 * @return
	 */
	public String getExpertList(String expertType);
	
	/**
	 * 联络员批量添加
	 * @param excelPath
	 * @return
	 */
	public boolean addCountPhoneInfo(String excelPath);
	
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */
	public List userQuery(String sql);

}
