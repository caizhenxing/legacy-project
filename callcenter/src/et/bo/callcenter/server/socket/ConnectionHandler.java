package et.bo.callcenter.server.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.message.EventExcute;
import et.bo.callcenter.message.impl.EventExcuteImpl;
import et.bo.common.ConstantsCommonI;
import et.bo.common.testing.MyLog;
import et.bo.common.testing.MyLog3;
import et.bo.common.testing.MyLog4;
import et.bo.common.testing.MyLog5;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

public class ConnectionHandler implements Runnable {
	private static Log log = LogFactory.getLog(ConnectionHandler.class);
	private boolean isConnection=true;
	private Socket socket;
	//
	private String socketIp="";
	/*
	 * 注入
	 */
	private static EventExcute ee=new EventExcuteImpl();//(EventExcute)(SpringRunningContainer.getInstance().getBean("EventExcuteService"));
//	private EventExcute ee=(EventExcute)(SpringRunningContainer.getInstance().getBean("EventExcuteService"));
	public ConnectionHandler() {
//		ee=(EventExcute)(SpringRunningContainer.getInstance().getBean("EventExcuteService"));
	}
   public ConnectionHandler(Socket aSocketToHandle) {
      socket = aSocketToHandle;
      socketIp=socket.getInetAddress().getHostAddress();      
   }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		EventExcute ee=(EventExcute)(SpringRunningContainer.getInstance().getBean("EventExcuteService"));
	}
	public void run() {
		connectionHandler(socket);
	}
	private void connectionHandler(Socket incomingConnection){
    	try {
    		String ip = incomingConnection.getInetAddress().getHostAddress();
    		BufferedInputStream bis = 
    			new BufferedInputStream(incomingConnection.getInputStream());
            /*DataInputStream dis = 
            	new DataInputStream(
            			new BufferedInputStream(incomingConnection.getInputStream())
            			);*/
    		StringBuffer sb=new StringBuffer();
            while(isConnection){
            	int i ;
            	while(isConnection & (i=bis.read())!=ConstantsI.INT_SEMICOLON){
            		//System.out.println("************&&&&&&&&&");
            		if(i==-1) continue;
//            		if(isConnection==false)break;
                	sb.append((char)i);
                }
            	if(isConnection==false){
            		this.socket.close();
            		break;
            	}
            	sb.append(ConstantsI.SEMICOLON);
            	String s=sb.toString();
            	if(s.indexOf("NETTST:;")==-1)
        		MyLog3.info("cmd is "+s+"--- and ip is"+this.socketIp+" hashcode is"+this.socket.hashCode());
            	dealWith(sb.toString(),ip);
            	sb=new StringBuffer();
            	
            }
        }catch (IOException e) {
            
            e.printStackTrace();
            this.isConnection=false;
           
            MyLog4.info("网络问题,中断线程 "+socketIp);
        }
        MyLog4.info("线程停止 "+socketIp);
	}
	public void dealWith(String s,String ip){
		if(s.indexOf("NETTST:;")!=-1)return;
		//MyLog3.info("cmd is "+s+"--- and ip is"+ip);
		ee.excute(s, ip);
	}
	
	public boolean testNet(){
		
		try {
			
			sendString(this.socket,"NETTST:;");
			this.isConnection=true;
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			///e.printStackTrace();
			this.isConnection=false;
			
			return false;
		}
	}
	/*
	 * 
	 */
	private void sendString(Socket socket,String s) throws IOException{
		try {
			BufferedOutputStream bos = 
    			new BufferedOutputStream(socket.getOutputStream());
			bos.write(s.getBytes());
			if(s.indexOf("NETTST:;")==-1)
				MyLog5.info("send msg <"+s+"> to ip <"+socket.getInetAddress().getHostAddress()+"> hashcode <"+socket.hashCode()+">");
				
			bos.flush();
        }catch (IOException e) {
            throw e;
        }
	}
	public void sendMsg(Socket socket,String s){		
			try {
				this.sendString(socket, s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void setEe(EventExcute ee) {
		this.ee = ee;
	}
	public boolean isConnection() {
		return isConnection;
	}
	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getSocketIp() {
		return socketIp;
	}
	public void setSocketIp(String socketIp) {
		this.socketIp = socketIp;
	}
	
}
