package et.bo.police.socket;

import java.net.Socket;

public interface PooledThreadServer {
	/*
	 *������������˿ڵ�ͨѶ��
	 */
	public void setUp();
	/*
	 * ���ܴӿͻ���������Ϣ��
	 */
	public String receiveContent(Socket incomingConnection);
	/*
	 * ���ͷ������˵���Ϣ��
	 */
	public String sendContent(String ip,String content);
	
}
