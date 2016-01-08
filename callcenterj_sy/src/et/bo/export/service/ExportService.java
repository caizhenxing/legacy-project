/*
 * @(#)CallbackService.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */


package et.bo.export.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>专家管理</p>
 * 
 * @version 2008-07-21
 * @author 王立春
 */

public interface ExportService {
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List exportQuery(IBaseDTO dto, PageInfo pi);
	public List exportQuery2();
	/**
	 * 查询数据列表的条数。
	 * 取得查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getExportSize(); 
	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象
	 * 取得某条数据的详细信息。
	 * @param id 取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getExportInfo(String id);
	/**
	 * 修改数据。
	 * 修改某条记录的内容。
	 * @param dto 要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateExport(IBaseDTO dto);
	/**
	 * 删除数据。
	 * 删除某条记录。
	 * @param id 要删除数据的标识
	 */
	public void delExport(String id);
	/**
	 * 添加数据。
	 * 向数据库中添加一条记录。
	 * @param dto 新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addExport(IBaseDTO dto);
	


}
