package et.bo.callcenter.message;

import java.util.List;

import et.po.CcRule;
/*
 * �����ݿ��н�����֤��Լ����ȷ�ԡ�
 */
public interface RuleInfo {
	public CcRule getCcRule(String key);
	public void reload();
	public List getRuleList();
}
