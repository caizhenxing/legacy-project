package et.bo.callcenter.business.nouse;

public interface EventAction {
	/*
	 * �����¼��Ͳ�����
	 * ���ص���EventActionImpl���������趨��
	 */
	EventAction action(String rule,String[] args);
	
}
