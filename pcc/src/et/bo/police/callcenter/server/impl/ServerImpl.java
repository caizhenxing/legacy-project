package et.bo.police.callcenter.server.impl;

import et.bo.police.callcenter.server.Server;
import et.bo.police.socket.PooledThreadServer;
/**
 * 
 */
public class ServerImpl implements Server {
	private PooledThreadServer pts=null;
	public ServerImpl() {
		// TODO Auto-generated constructor stub
	}

	public String command(String ip,String strContent) {
		return pts.sendContent(ip, strContent);
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

}
