package et.bo.police.callcenter.message.event;

public interface EventHelperService {
	/*
	 * ˢ����ϯ����״̬
	 * 
	 */
	public void refreshOperatorPanel();
	/*
	 * ˢ�¹��ػ�����ϯ״̬
	 * ���ػ���icc
	 */
	public void refreshIccOperatorState();
	
	/*
	 * �洢cclog��policeCallin��Police_callin_info
	 */
	public void saveInfo(String id);
}
