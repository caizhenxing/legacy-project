package et.bo.callcenter.message;

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
	 * ȫ��ˢ�£�����2������
	 */
	public void refreshIccAndOperator();
	/*
	 * �洢cclog��policeCallin��Police_callin_info
	 */
	/*
	 * ˢ�¹��ػ�����ϯԱ :physicsPort and OperatorNum
	 * ���ػ���icc 
	 */
	public void refreshIccOperator(String physicsPort,String OperatorNum);
	public void saveInfo(String id);
	public void callRe(String inPort,String result);
	public void printArg(String s[]);
	
	public void oosign(String op,String s);
	public void ooseat(String op,String s);
	public void oocall(String op,String s);
}
