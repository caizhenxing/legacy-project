package et.bo.police.callcenter;

public interface OperatorI {
	/*
	 * ǩ��/��״̬-ǩ��
	 */
	String IO_IN="1";
	/*
	 * ǩ��/��״̬-ǩ��
	 */
	String IO_OUT="0" ;
	/*
	 * ����״̬[��ϯ����ϯ�����塢ͨ��]-��ϯ
	 */
	String WORK_LEAVE="0";
	/*
	 * ����״̬[��ϯ����ϯ�����塢ͨ��]-��ϯ
	 */
	String WORK_IN="1";
	/*
	 * ����״̬[��ϯ����ϯ�����塢ͨ��]-����
	 */
	String WORK_RING="2";
	/*
	 * ����״̬[��ϯ����ϯ�����塢ͨ��]-ͨ��
	 */
	String WORK_TEL="3";
	//-----------------------------	
	/*
	 * ����״̬[��ϯ����ϯ�����塢ͨ��]-�ȴ�����
	 */
	String WORK_WAIT="4";
//	-----------------------------
	/*
	 * ���⹤��
	 */
	String OPERATOR_NUM="***";
	/*
	 * �ָ����
	 */
	String DELIM1="#";
	String DELIM2="!";
	String BLANK=" ";
	int PHONE_NUM_FORMAT_LEN=15;
	
}
