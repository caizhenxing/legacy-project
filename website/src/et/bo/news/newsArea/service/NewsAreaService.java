/**
 * 	@(#)NewsAreaService.java   2007-1-23 下午02:50:06
 *	 。 
 *	 
 */
package et.bo.news.newsArea.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * @describe
 * @author 叶浦亮
 * @version 2007-1-23
 * @see
 */
public interface NewsAreaService {
	/**
	 * @describe 区域添加
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaAdd(IBaseDTO dto);
	/**
	 * @describe0 区域修改
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaUpdate(IBaseDTO dto);
	/**
	 * @describe 区域删除
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public void areaDel(String id);
	/**
	 * @describe 区域列表
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public List areaList(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe 取得条数
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public int getSize();
	/**
	 * @describe 取得区域详细信息
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public IBaseDTO getAreaInfo(String id);
	/**
	 * @describe 区域下拉列表
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getAreaList();

}
