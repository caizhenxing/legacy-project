/**
 * 沈阳卓越科技有限公司
 * 2008-4-7
 */
package et.bo.callcenter.callinfirewall.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang feng
 * 
 */
public interface CallinFirewallService {
	/**
	 * @describe 增加规则
	 * @param
	 * @return void
	 */
	public void addRule(IBaseDTO dto);

	/**
	 * @describe 修改规则
	 * @param
	 * @return void
	 */
	public void updateRule(IBaseDTO dto);

	/**
	 * @describe 删除规则
	 * @param
	 * @return void
	 */
	public void delRule(IBaseDTO dto);

	/**
	 * @describe 根据主键Id取得规则信息
	 * @param
	 * @return DynaBeanDTO
	 */
	public IBaseDTO getRuleInfo(String id);

	/**
	 * @describe 查询规则
	 * @param
	 * @return List
	 */
	public List ruleQuery(IBaseDTO dto, PageInfo pi);

	/**
	 * @describe 得到规则条数
	 * @param
	 * @return int
	 */
	public int getRuleSize();

	/**
	 * @describe 查询来电号码是否在电话黑名单内
	 * @param String
	 *            phoneNum(电话号码)
	 * @return boolean 在电话黑名单内返回ture 不在返回false
	 */
	public boolean IfInBlacklist(String phoneNum);

	/**
	 * @describe 判断录入号码是否存在
	 * @param String
	 *            phoneNum(电话号码)
	 * @return boolean 存在为true 不存在为false
	 */
	public boolean ifHaveSameNum(String phoneNum);
}
