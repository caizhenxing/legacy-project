/**
 * 	@(#)FormatService.java   2007-1-22 上午10:12:48
 *	 。 
 *	 
 */
package et.bo.format.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe
 * @author 叶浦亮
 * @version 2007-1-22
 * @see
 */
public interface FormatService {
	/**
	 * @describe 样式添加
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatAdd(IBaseDTO dto);
	/**
	 * @describe0 样式修改
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatUpdate(IBaseDTO dto);
	/**
	 * @describe 样式删除
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void formatDel(String id);
	/**
	 * @describe 样式列表
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public List formatList(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe 取得条数
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public int getSize();
	/**
	 * @describe 取得样式详细信息
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public IBaseDTO getFormatInfo(String id);
	/**
	 * @describe 样式下拉列表
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getStyleList();
}
