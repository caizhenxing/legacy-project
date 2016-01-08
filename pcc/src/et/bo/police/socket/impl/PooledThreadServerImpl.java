package et.bo.police.socket.impl;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import ocelot.common.classtree.ClassTreeService;
import ocelot.framework.base.container.SpringContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.Constants;
import et.bo.police.socket.PooledThreadServer;
import et.bo.testing.MyLog;

public class PooledThreadServerImpl implements PooledThreadServer{
	private ServerSocket serverSocket;
    private ClassTreeService cts;
    //private static Log log = LogFactory.getLog(PooledThreadServerImpl.class);
    private static Log log = LogFactory.getLog(PooledThreadServerImpl.class);
    //以下需要被赋值
    private int maxConnections;
    private int listenPort;
    public PooledThreadServerImpl(){
    }
    public PooledThreadServerImpl(int aListenPort, int maxConnections) {
        listenPort = aListenPort;
        this.maxConnections = maxConnections;
    }
    public void setUp(){
    	this.maxConnections =	Integer.parseInt(cts.getvaluebyNickName(Constants.MAX_SOCKET_THREAD));
    	this.listenPort     =	Integer.parseInt(cts.getvaluebyNickName(Constants.SERVER_SOCKET_PORT));
    	log.debug(this.maxConnections);
    	log.debug(this.listenPort);
    	setUpHandlers();
    	acceptConnections();
    }
    public static void main(String[] args) {
//    	PooledThreadServer server = new PooledThreadServer(12000, 5);
//        server.setUpHandlers();
//        server.acceptConnections();
    	new MyLog();
    	SpringContainer sc = SpringContainer.getInstance();
    	PooledThreadServer pts =(PooledThreadServer)sc.getBean("PooledThreadServer");   
    	pts.setUp();
    }
    private void setUpHandlers(){
    	log.debug("----------------------\r\n");
    	log.debug("setUpHandlers");
    	log.debug("----------------------\r\n");
    	for (int i = 0; i < maxConnections; i++) {
    		log.debug("this is +"+i+"handler.---------------------");
    		PooledThreadConnectionHandler currentHandler = new PooledThreadConnectionHandler();
            new Thread(currentHandler, "Handler " + i).start();
        }
    }
    private void acceptConnections() {
    	log.debug("----------------------\r\n");
    	log.debug("acceptConnections");
    	log.debug("----------------------\r\n");
    	try {
            ServerSocket server = new ServerSocket(listenPort,8);
            Socket incomingConnection = null;
            while (true) {
                incomingConnection = server.accept();
                receiveContent(incomingConnection);
            }
        } catch (BindException e) {
        	log.error("Unable to bind to port " + listenPort);
        } catch (IOException e) {
        	log.error("Unable to instantiate a ServerSocket on port: " + listenPort);
        }catch(Exception e){
        	e.printStackTrace();
        	log.error("unexpected error!");
        }
    }
    
    public String receiveContent(Socket incomingConnection) {    	
    	PooledThreadConnectionHandler.processRequest(incomingConnection);
    		//and do sth.PooledThreadConnectionHandler
    		return null;
    }
    
    public String sendContent(String ip,String content) {
    	StaticConnectionHandler.processResponse(ip, content); 
    	return null;
    }
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}