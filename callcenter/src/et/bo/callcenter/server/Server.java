package et.bo.callcenter.server;

/*
 * �������������Ĺ��ܡ�
 * ���մ�client�������ַ���(�¼�)�������ʹ�Ӧ�����Ĵ����ͻ���(����)�����͡�
 *
	socket ����ķ�װ��
 * @author:������
 * @version:1.0
 */

public interface Server {
//	--------���� ---------
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	
	/*
	 * ���մ�client�������ַ���(�¼�)����������Ӧ�Ķ�����
	 * @param s �ӿͻ��˽��յ��Ĵ���
	 * @return ���ض����Ľ��,0 �ɹ�,��0��ʧ�ܡ�
	 * //��ʵ����ӿ���û����;�ġ���Ϊ�����������ַ����Ǳ����ġ�
	 * ���ǽ��պ�Ҫ��һϵ�д���������������Ӧ����socket�Ǹ�����ô���ġ�
	 */
	
	String event(String s);
	
	/*
	 * �õ��ͻ��˵�ip,icc��ip.
	 */
//	String getCliIp();
	
	/*
	 * ��Ӧ�����Ĵ����ͻ���(����),�����͸��ͻ��ˡ�
	 * @param cmd ���
	 * @param args ���������
	 * @return ���͸�client�˵������
	 */
	String command(String ip,String strContent);
	
	//	-------------connect------------------
	/*
	 * ��ҵ������ݿ��Ƿ�ͨ
	 * ���أ�0�ɹ����������ɹ���
	 */

	String isDbConnect();
	/*
	 * ����ϯ������socket�Ƿ���ͨ
	 * ���أ�0�ɹ����������ɹ���
	 */
	String isOperatorConnect(String ip);
	/*
	 * ���ڷ��͸���ϯ�͹��ػ�һЩ����,��������ĵ�ҵ���߼��й�
	 * ���綨�����������ڴ�����Ķ���.���ڷ���һЩ����ȵ�.
	 */
	void sendMsgSchema();
	/*
	 * ��Ҫ������socket
	 */
	void runServer();
}
