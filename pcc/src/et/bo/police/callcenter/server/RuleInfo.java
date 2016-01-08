package et.bo.police.callcenter.server;

import java.util.List;

import et.po.CcRule;

public interface RuleInfo {
	public CcRule getCcRule(String key);
	public void reload();
	public List getRuleList();
}
