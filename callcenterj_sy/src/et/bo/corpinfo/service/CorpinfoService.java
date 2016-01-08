/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.corpinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe 企业库信
 * @author  荆玉琢
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface CorpinfoService {
	/**
	 * @describe 添加企业库信
	 * @param
	 * @return void
	 */ 
	public void addOperCorpinfo(IBaseDTO dto);
	/**
	 * 获得专家列表
	 * @param sql
	 * @return List
	 */
	public List exportQuery(String sql);
	/**
	 * 获得座席员列表
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);
	/**
	 * @describe 修改企业库信
	 * @param
	 * @return void
	 */ 
	public boolean updateOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe 删除企业库信
	 * @param
	 * @return void
	 */ 
	public void delOperCorpinfo(String id);
	
	
	/**
	 * @describe 查询企业库信列表
	 * @param
	 * @return List
	 */ 
	public List operCorpinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getOperCorpinfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getOperCorpinfo(String id);
	
	/**
	 * 状态转换
	 * @param state
	 * @return
	 */
	public String changeState(String state);
	
	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
		
}
