package et.bo.callcenter.server.socket.implpool;

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
