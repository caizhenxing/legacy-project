package et.bo.callcenter.server.impl;

import java.net.Socket;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.message.EventExcute;
import et.bo.callcenter.server.ServerSocketService;
import et.bo.callcenter.server.socket.ConnectionHandler;
import et.bo.callcenter.server.socket.ServerSocketHelper;
import et.bo.common.ConstantsCommonI;

public class ServerSocketImpl
		implements ServerSocketService {
	private static Log log = LogFactory.getLog(ServerSocketService.class);
	/*
	 * ×¢Èë
	 */
//	private ServerSocketHelper ssh;
	private ConnectionHandler chs=new ConnectionHandler();
	private ServerSocketHelper ssh=new ServerSocketHelper();
	public ServerSocketImpl(){
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.server.ServerSocketService#getCurLink()
	 */
//	public Map getCurLink(){
//		return chs.refreshLinkedMap();
//	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.server.ServerSocketService#runServerSocket()
 */
	public void runServerSocket() {
		log.debug(ConstantsCommonI.TEST_RETURN);
		log.debug(ConstantsCommonI.TEST_LINE);
		log.debug("begin runServer");
		log.debug(ConstantsCommonI.TEST_RETURN);
		log.debug(ConstantsCommonI.TEST_LINE);
//		acceptConnections();
//		new ServerSocketHelper().acceptConnections();
		ssh.acceptConnections();
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.server.ServerSocketService#sendContent(java.lang.String, java.lang.String)
 */
	public String sendContent(String ip, String msg) {
		ssh.sendMsg(ip, msg);
		return null;
	}
	
	public void setSsh(ServerSocketHelper ssh) {
		this.ssh = ssh;
	}
	public void setChs(ConnectionHandler chs) {
		this.chs = chs;
	}
	public ConnectionHandler getChs() {
		return chs;
	}
	public ServerSocketHelper getSsh() {
		return ssh;
	}

}
