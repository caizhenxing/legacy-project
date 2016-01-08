/**
 * 沈阳卓越科技有限公司版权所有 项目名称：ExcellenceBase
 * 制作时间：2009-3-13下午12:39:42 包名：excellence.common.socket
 * 文件名：SocketClientTest.java 制作者：zhaoyifei
 * 
 * @version 1.0
 */
package excellence.common.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author zhaoyifei
 * @version 1.0 软电话(客户端)必须和调度客户端(服务端)长连，如果中间断开，需要做重连的动作。
 *          每条消息内部值之间用空格分隔。 客户端使用端口9000连接到本机的服务端
 */
public class SocketClientTest extends Thread
{
	private String flag = "";
	private static  Socket                          socket;
    private static  DataInputStream         datain;
    private static DataOutputStream dataout;
    String                          msg1 = "0020 user password";
    String                          msg2 = "0001 13901234567";
    String                          msg3 = "0030 13901234567";
    
    private static  Socket ccSocket;
    private static  DataOutputStream ccDataout;
    private static  DataInputStream ccDatain;

    //这个方法供你程序有来电时调用
    public void sendCallin2Server(String phone)
    {
        try
        {
            dataout.writeUTF("0030 "+phone);
            sleep(1000*60);
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
    public void readCcServer()
    {
    	try
        {
        	
        	while (true)
        	{
        	
        	String ccLine = this.ccDatain.readLine();
        	String num;
        	System.out.println(ccLine);
        	if(ccLine.split(" ")[0].equals("0001"))
        	{
        		num = ccLine.split(" ")[1];
        		sendCallin2Server(num);
        		System.out.println(num);
        	}
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
  
    public void readServer()
    {
        try
        {
        	
        	while (true)
        	{
        	String line = this.datain.readUTF();
        	
        	System.out.println(line);
        	if(line.split(" ")[0].equals("0020"))
        	{
        		String user = line.split(" ")[1];
        		String password = line.split(" ")[2];
        	}
        	if(line.split(" ")[0].equals("0001"))
        	{
        	String num = line.split(" ")[1];
        	String cmd = "c2s_xxcall:2,"+num;
        	ccDataout.writeBytes(cmd);
        	System.out.println(cmd);
        	}
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
    public void sendLogin2Server()
    {
        try
        {
            dataout.writeUTF("0020 "+"1111 "+"1234");
            sleep(1000*60);
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
    public SocketClientTest()
    {
        try
        {
            socket = new Socket("localhost", 9000);
            datain = new DataInputStream(socket.getInputStream());
            dataout = new DataOutputStream(socket.getOutputStream());
            ccSocket = new Socket("192.168.1.21",12000);
            ccDataout = new DataOutputStream(ccSocket.getOutputStream());
            ccDatain =  new DataInputStream(ccSocket.getInputStream());
            ccDataout.writeBytes("c2s_xlogin:1");
        }
        catch (IOException e)
        {
        }
    }

    public void run()
    {
        if(this.flag.equals("cc"))
        {
        	this.readCcServer();
        }else
        {
        	this.readServer();
        }
        
    }

    /**
     * 功能描述
     * 
     * @param args
     *            2009-3-13 下午12:39:42
     * @version 1.0
     * @author Administrator
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        SocketClientTest sctcc = new SocketClientTest();
        //sct.sendCallin2Server("13817703850");
        sctcc.flag = "cc";
        sctcc.run();
        SocketClientTest sct = new SocketClientTest();
        sct.run();
    }

}
