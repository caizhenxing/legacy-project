/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�ExcellenceBase
 * ����ʱ�䣺2009-3-13����11:28:14
 * ������excellence.common.socket
 * �ļ�����SocketServerTest.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package excellence.common.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class SocketServerTest {

	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public void server()
	{
	try
	{
	ss = new ServerSocket(5000);
	socket = ss.accept();
	System.out.println(socket);
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	out = new PrintWriter(socket.getOutputStream(),true);
	while (socket.isConnected())
	{
	
	
	

	String line = in.readLine();
	System.out.println(line);
	out.println("you input is :" + line);
	
	}
	out.close();
	in.close();
	socket.close();
	}
	catch (IOException e){
		
	}finally
	{
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

	/**
	 * ��������
	 * @param args
	 * 2009-3-13 ����11:28:14
	 * @version 1.0
	 * @author zhaoyifei
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketServerTest sst = new SocketServerTest();
		sst.server();
	}

}
