/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：ExcellenceBase
 * 制作时间：May 18, 200911:27:27 AM
 * 包名：excellence.common.socket
 * 文件名：SocketServeri.java
 * 制作者：Administrator
 * @version 1.0
 */
package excellence.common.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Administrator
 * @version 1.0
 */
public class SocketServeri extends Thread {
	private ServerSocket ss;
	private Socket socket;
	private static DataOutputStream ccDataout;
	private static DataInputStream ccDatain;

	public SocketServeri() {
		try {
			ss = new ServerSocket(5000);
			socket = ss.accept();
			System.out.println(socket);
			ccDataout = new DataOutputStream(socket.getOutputStream());
			ccDatain = new DataInputStream(socket.getInputStream());
			ccDataout.writeBytes("c2s_xlogin:1");
		} catch (IOException e) {
		}
	}
	 public void readServer()
	    {
	        try
	        {
	        	
	        	while (true)
	        	{
	        	String line = this.ccDatain.readUTF();
	        	
	        	System.out.println(line);
	        	
	        	}
	        }
	        catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        catch (Exception e) {
	            // TODO: handle exception
	        }
	    } 
	 public void run()
	    {
	       
	        	this.readServer();
	        
	        
	    }
	/**
	 * 功能描述
	 * 
	 * @param args
	 *            May 18, 2009 11:27:27 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketServeri ssi = new SocketServeri();
		ssi.run();
	}

}
