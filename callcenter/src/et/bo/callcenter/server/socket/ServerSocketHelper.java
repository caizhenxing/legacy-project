package et.bo.callcenter.server.socket;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.OperatorState;
import et.bo.common.testing.MyLog4;
import et.bo.common.testing.MyLog5;

//import et.bo.callcenter.server.impl.ServerSocketImpl;

public class ServerSocketHelper {	
	private static Log log = LogFactory.getLog(ServerSocketHelper.class);
	/*
	 * key is ip,value is ConnectionHandler
	 */
	private static Map<String,ConnectionHandler>  socketPool= new ConcurrentHashMap<String,ConnectionHandler>();
	public static String icc_ip  =null;
	//常量
	private final String ESTATE="ESTATE";
	/*
	 * 工控机
	 */
	private final String ESTATE_ICC="0";
	/*
	 * 工控机，0不可用，1可用。
	 */
	private final String ESTATE_ICC_STATE_0="0";
	private final String ESTATE_ICC_STATE_1="1";
	
	public ServerSocketHelper(){
	}
	public void acceptConnections() {
		new TestNet().start();
    	try {
            ServerSocket server = 
            	new ServerSocket(ConstsSocketI.PORT,
            		ConstsSocketI.INIT_SOCKET);
            while (true) {
            	Socket incomingConnection = server.accept(); 
//            	incomingConnection.setSoTimeout(5000);
                handleConnection(incomingConnection);
            }
        } catch (BindException e) {
        	log.error("Unable to bind to port " + ConstsSocketI.PORT);
        } catch (IOException e) {
        	log.error("Unable to instantiate a ServerSocket on port: " + ConstsSocketI.PORT);
        }catch(Exception e){
        	e.printStackTrace();
        	log.error("unexpected error!");
        }
    }
	/*
	 * 接收信息，处理部分是抽象方法
	 */
	private void handleConnection(Socket socket){
		//1:判断ip是否在socketPool中,如果在，这停止相关的connectionHandler。然后再启动新的线程。如果不在，之间启动相关线程。
		String ip=socket.getInetAddress().getHostAddress();
		if(socketPool.containsKey(ip)){
			socketPool.get(ip).setConnection(false);
		}
		ConnectionHandler ch=new ConnectionHandler(socket);
		MyLog4.info("网络连接:"+ch.getSocketIp()+" hashcode "+ch.getSocket().hashCode());
		new Thread(ch).start();
		socketPool.put(ip, ch);
	}
	class TestNet extends Thread{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				TestNet.sleep(25000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//cts.getvaluebyNickName(ConstantsI.CLIENT_IP_LIST);
			while(true)
			{
				try {
					TestNet.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.checkNet();				
			}
		}
		private void checkNet(){
			//MyLog4.info("m.size is: "+m.size());
			Iterator<String> iterator=socketPool.keySet().iterator();
			while(iterator.hasNext()){
				String ip=iterator.next();			
				ConnectionHandler chTmp=socketPool.get(ip);
				
				boolean conn=chTmp.testNet()&& chTmp.getSocket().isConnected();
				if(!conn){
					iterator.remove();
					chTmp.setConnection(false);
					MyLog4.info("网络断开:"+chTmp.getSocketIp()+" hashcode "+chTmp.getSocket().hashCode());
					//
					if(!ip.equals(icc_ip)){//座席ip
						//tell icc 该座席不可用。
						doSth(ip);
					}else{
						sendCmdFromNet(ESTATE,new String[]{ESTATE_ICC,ESTATE_ICC_STATE_0});
					}
				}
			}
		}		
	}
	//
	private void doSth(String ip){
//		refresh class
		try
		{
		OperatorState os1 = OperatorState.getOperatorState(ip);
		String on = os1.getOperatorNum();
		os1.setCurTime(new Date());
		os1.setIp(ip);
		os1.setState(os1.SIGNOUT);
//		OperatorState.getOperatorStateMap().remove(on);
		//
		CardState cs1=new CardState();
		cs1.setCurTime(new Date());
		cs1.setOperatorNum(on);
		cs1.setState(cs1.INNER_SIGNOUT);
//		通过ip找到port from cardinfo
		Iterator it=CardInfo.getCardMap().keySet().iterator();
		String physicsPort=null;
		while(it.hasNext()){
			String port = (String)it.next();
			CardInfo ci= (CardInfo)CardInfo.getCardMap().get(port);
			if(ip.equals(ci.getIp()))physicsPort=ci.getPhysicsPort();
		}
		cs1.setPhysicsPort(physicsPort);
		cs1.removeInnerActive(cs1);
		//refresh pannle
		//this.eventHelper.refreshIccAndOperator();
		sendMsg(icc_ip,"SETPRT:"+physicsPort+",0;");
		MyLog5.info("SETPRT:"+physicsPort+",0;"+"__ip is:"+ip);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * 发送命令
	 * ip，对方的ip地址
	 * cmdName，发送的命令名称
	 * cmdArg，发送的参数
	 */
	private String sendCmdFromNet(String cmdName,String[]cmdArg){
		String result=null;
		sendAllMsg(cmdName+":"+cmdArg[0]+","+cmdArg[1]+";");
		System.err.println(cmdName+":"+cmdArg[0]+","+cmdArg[1]+";");
		return result;
	}
	private void sendAllMsg(String msg){
		Iterator<String> iterator=socketPool.keySet().iterator();
		while(iterator.hasNext()){
			String ip=iterator.next();			
			ConnectionHandler chTmp=socketPool.get(ip);
			Socket socket=chTmp.getSocket();
			chTmp.sendMsg(socket, msg);
		}		
	}
	public void sendMsg(String ip,String msg){
		ConnectionHandler chTmp=socketPool.get(ip);
		if(chTmp==null) return;
		Socket socket =chTmp.getSocket();
		if(socket==null) return;
		chTmp.sendMsg(socket, msg);
	}
}
