package et.bo.callcenter.server;

import java.util.Map;

public interface ServerSocketService {
	/*
	 * 服务器端发送给客户端的消息。
	 * 参数：ip-客户端的ip.
	 * 		msg 消息内容.
	 */
	public String sendContent(String ip,String msg);
	/*
	 * 得到当前的长连接的集合
	 */
//	public Map getCurLink();
	/*
	 * 运行ServerSocket
	 */
	public void runServerSocket();
}
