package et.bo.callcenter.message;

import et.bo.callcenter.business.nouse.EventAction;

public interface EventI {
	/*
	 * �����¼��Ͳ�����
	 * ���ص���RuleImpl���������趨��
	 */
	String action(String rule,String[] args);
	String getResult();
}
