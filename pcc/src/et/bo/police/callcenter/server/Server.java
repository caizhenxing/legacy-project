package et.bo.police.callcenter.server;

/*
 * �������������Ĺ��ܡ�
 * ���մ�client�������ַ���(�¼�)�������ʹ�Ӧ�����Ĵ����ͻ���(����)�����͡�
 * @author:������
 * @version:1.0
 */

public interface Server {
	/*
	 * ���մ�client�������ַ���(�¼�)����������Ӧ�Ķ�����
	 * @param s �ӿͻ��˽��յ��Ĵ���
	 * @return ���ض����Ľ��,0 �ɹ�,��0��ʧ�ܡ�
	 * //��ʵ����ӿ���û����;�ġ���Ϊ�����������ַ����Ǳ����ġ�
	 * ���ǽ��պ�Ҫ��һϵ�д���������������Ӧ����socket�Ǹ�����ô���ġ�
	 */
	public String event(String s);
	/*
	 * ��Ӧ�����Ĵ����ͻ���(����),�����͸��ͻ��ˡ�
	 * @param cmd ���
	 * @param args ���������
	 * @return ���͸�client�˵������
	 */
	public String command(String ip,String strContent);
}
