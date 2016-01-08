/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.priceinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 市场价格
 * @author  荆玉琢
 * @version 1.0, 2008-03-29//
 * @see
 */
public interface PriceinfoService {
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
	 * 获得受理工号列表
	 */
	public List userQuery();
	
	
	/**
	 * @describe 查询市场价格列表
	 * @param
	 * @return List
	 */ 
	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe 查询市场价格列表forExcel
	 * @param
	 * @return List
	 */ 
	public List<List<String>> operPriceinfoExcelQuery(IBaseDTO dto);
	
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
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	/**
	 * 获取screen的价格看板数据
	 */
	public List screenList();
}
