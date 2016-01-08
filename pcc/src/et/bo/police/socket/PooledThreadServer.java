package et.bo.police.socket;

import java.net.Socket;

public interface PooledThreadServer {
	/*
	 *建立起服务器端口的通讯。
	 */
	public void setUp();
	/*
	 * 接受从客户端来的消息。
	 */
	public String receiveContent(Socket incomingConnection);
	/*
	 * 发送服务器端的消息。
	 */
	public String sendContent(String ip,String content);
	
}
