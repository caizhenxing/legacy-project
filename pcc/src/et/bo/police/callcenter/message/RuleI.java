package et.bo.police.callcenter.message;

import et.bo.police.callcenter.server.EventAction;

public interface RuleI {
	/*
	 * �����¼��Ͳ�����
	 * ���ص���RuleImpl���������趨��
	 */
	RuleI action(String rule,String[] args);
	//	String getResult();
}
