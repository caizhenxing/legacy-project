/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.linkmanPriceinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 市场价格
 * @author  荆玉琢
 * @version 1.0, 2008-03-29//
 * @see
 */
public interface LinkmanPriceinfoService {
	/**
	 * @describe 添加市场价格
	 * @param
	 * @return void
	 */ 
	public void addOperPriceinfoSad(IBaseDTO dto);
	/**
	 * @describe 修改市场价格
	 * @param
	 * @return void
	 */ 
	public boolean updateOperPriceinfo(IBaseDTO dto);
	/**
	 * @describe 删除市场价格
	 * @param
	 * @return void
	 */ 
	public void delOperPriceinfo(String id);
	
	
	/**
	 * @describe 查询市场价格列表
	 * @param
	 * @return List
	 */ 
	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getOperPriceinfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getOperPriceinfo(String id);
	
//	 人员列表为查询页面提供数据
	public List<LabelValueBean> getUserList();
	
}
