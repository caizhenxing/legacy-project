package et.bo.police.callcenter.server;

public interface EventAction {
	/*
	 * �����¼��Ͳ�����
	 * ���ص���EventActionImpl���������趨��
	 */
	EventAction action(String rule,String[] args);
	
}
