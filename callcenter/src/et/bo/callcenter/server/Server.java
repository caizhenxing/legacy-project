package et.bo.callcenter.server;

/*
 * 处理服务器方面的功能。
 * 接收从client端来的字符串(事件)并动作和从应用来的串给客户端(命令)并发送。
 *
	socket 服务的封装。
 * @author:辜晓峰
 * @version:1.0
 */

public interface Server {
//	--------常量 ---------
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	
	/*
	 * 接收从client端来的字符串(事件)，并进行相应的动作。
	 * @param s 从客户端接收到的串。
	 * @return 返回动作的结果,0 成功,非0　失败。
	 * //其实这个接口是没有用途的。因为服务器接受字符串是被动的。
	 * 但是接收后要有一系列处理情况。但是这个应该是socket那个类调用此类的。
	 */
	
	String event(String s);
	
	/*
	 * 得到客户端的ip,icc的ip.
	 */
//	String getCliIp();
	
	/*
	 * 从应用来的串给客户端(命令),并发送给客户端。
	 * @param cmd 命令。
	 * @param args 命令参数。
	 * @return 发送给client端的命令串。
	 */
	String command(String ip,String strContent);
	
	//	-------------connect------------------
	/*
	 * 与业务的数据库是否通
	 * 返回：0成功，其它不成功。
	 */

	String isDbConnect();
	/*
	 * 与座席的连接socket是否连通
	 * 返回：0成功，其它不成功。
	 */
	String isOperatorConnect(String ip);
	/*
	 * 定期发送给座席和工控机一些命令,与呼叫中心的业务逻辑有关
	 * 比如定期清理以下内存里面的东东.定期发送一些命令等等.
	 */
	void sendMsgSchema();
	/*
	 * 主要是运行socket
	 */
	void runServer();
}
