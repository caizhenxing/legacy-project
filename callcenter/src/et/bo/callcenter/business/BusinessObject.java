package et.bo.callcenter.business;

import java.util.Map;

/**
 * 
 * @author guxiaofeng
 *ҵ������
 */
public interface BusinessObject{
//	--------���� ---------
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	
	String VALID_OK="0";
	String VALID_BLACKLIST="1";
	String VALID_FAIL="2";
	//	--------���� over---------
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
	 * ��ϯԱ��֤
	 * �������û������ţ�������
	 * ���أ�0�ɹ����������ɹ���
	 */
	String isOperator(String u,String p);

	/*
	 * �õ����ػ���ip
	 */
	String getCardPcIp();
	/*
	 * �õ����˿ڶ�Ӧ��ip
	 */
	String getPortLinkedIp(String port);
	//-------------bo��PoliceCallin------------------------
	/*
	 * id is ҵ��ı�ʶ
	 */
	void setPcInstance(String id);
	/*
	 * ���ñ�ʶ
	 */
	void setPcFuzzNum(String id,String fuzzNum);
	/*
	 * ȡ����֤����
	 */
	int getPcNum(String id);
	/*
	 * ������֤����
	 * i is ��֤������
	 */
	void setPcNum(String id,int i);
	/*
	 * ����ҵ����
	 */
	void setPcValidInfo(String id,String validInfo);
	/*
	 * ������ϯԱ
	 */
	void setPcOperatorNum(String id,String operatorNum);
	/*
	 * �洢��Ϣ�����ݿ�,������id�Ƴ�
	 */
	String savePc(String id);
//	-------------bo��PoliceCallin over------------------------
	
	String saveCclog(String id);
	String saveAll(String id);
	/*
	 * ��֤��ϯ���û���¼
	 */
	String validUser(String user,String password);
	
	public boolean insertValue(String pocid);
}
