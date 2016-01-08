package et.bo.callcenter.message;

import java.util.List;

import et.po.CcRule;
/*
 * 在数据库中进行验证规约的正确性。
 */
public interface RuleInfo {
	public CcRule getCcRule(String key);
	public void reload();
	public List getRuleList();
}
