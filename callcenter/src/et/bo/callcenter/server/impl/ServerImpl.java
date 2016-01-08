package et.bo.callcenter.server.impl;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.server.Server;
import et.bo.callcenter.server.ServerSocketService;
import et.bo.callcenter.server.socket.implpool.PooledThreadServer;
import excellence.common.classtree.ClassTreeService;
/**
 * 
 */
public class ServerImpl implements Server {
	/*
	 * 注入
	 */
	private ClassTreeService cts;
	private ServerSocketService sss;
	
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public void setSss(ServerSocketService sss) {
		this.sss = sss;
	}

	public ServerImpl(){
		// TODO Auto-generated constructor stub
	}

	public String command(String ip,String strContent) {
		return sss.sendContent(ip, strContent);
	}

	public String event(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	/*
	 * 得到event的ip
	 */
	public String getCliIp(){
		String ip =	cts.getvaluebyNickName(ConstantsI.CLIENT_IP_LIST);
		return ip;
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.server.Server#isDbConnect()
	 */
	public String isDbConnect(){
		
		return Server.SUCCEED;
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.server.Server#isOperatorConnect(java.lang.String)
	 */
	public String isOperatorConnect(String ip){
		return Server.SUCCEED;
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.server.Server#sendMsgSchema()
	 */
	public void sendMsgSchema(){
		// todo sth
	}

	public void runServer() {
		//
		sss.runServerSocket();
	}
}
