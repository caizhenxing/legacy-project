package et.bo.police;

public interface PoliceInfo{
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	//-------------validate------------------
	/*
	 * ������Ϣ��������
	 * �������绰����
	 * ���أ�0�ɹ����������ɹ���
	 */
	String blacklist(String phoneNum);
	/*
	 * ��Ա��֤
	 * ���������ţ�������
	 * ���أ�0�ɹ����������ɹ���
	 */
	String isPolice(String fuzzNum,String pass);
	/*
	 * ��������������ݿ��Ƿ�ͨ
	 * ���أ�0�ɹ����������ɹ���
	 */
//	-------------connect------------------
	String isDbConnect();
	/*
	 * ����ϯ������socket�Ƿ���ͨ
	 * ���أ�0�ɹ����������ɹ���
	 */
	String isOperatorConnect(String ip);
	/*
	 * �õ����ػ���ip
	 */
	String getCardPcIp();
	
}
