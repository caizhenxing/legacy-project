package et.test;

import java.net.*;
import java.io.*;

public class Server
{
	private Socket socket;
	
	
	class Accept extends Thread
	{
		Socket s;
		public Accept(Socket s)
		{
			this.s = s;
		}
		public void run()
		{
			try{
				while(true)
				{
					StringBuffer sb = new StringBuffer();
					DataInputStream is= new DataInputStream(new BufferedInputStream(s.getInputStream()));
					String inputLine = "";
					int i;
					//System.out.println("server prepare accept msg");
          while((i=is.read()) != 59) {
   
                if(i == -1)
	{
	    
                    continue;
              }
	  sb.append((char)i);
            }
          if(sb.toString().indexOf("NETTST")==-1)
          {
          	System.out.println("服务器收到信息为"+sb.toString());
        	}
			System.out.println("服务器收到信息为"+sb.toString());
        	//System.out.println("server has accept msg");
    			this.sleep(2000);
        	//is.close();
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	class Send extends Thread
	{
		Socket s;
		OutputStream os = null;
		public Send(Socket s)
		{
			this.s = s;
		}
		public void run()
		{

			try
			{
				sleep(5000);
				int i = 0;
				
				while(true)
				{
					System.out.println("服务器发命令00000000000000!!");
					os=s.getOutputStream();
					System.out.println("服务器发命令!!");
					//if(i==0)
					//os.write("CMD_OPENSESSION:192.168.1.12,2555;".getBytes());
					if(i==1)
					os.write("CMD_STARTMONITOR:8912;".getBytes());
					if(i==2)
					os.write("CMD_STARTMONITOR:8913;".getBytes());
					////CMD_MAKECALL:beginport,otherNum;
					if(i==3)
					{
						os.write("CMD_MAKECALL:8912,8913;".getBytes());
					}
					if(i==4)
					{
						os.write("CMD_STOPMONITOR:8912;".getBytes());
					}
					if(i==5)
					{
						os.write("CMD_STOPMONITOR:8913;".getBytes());
					}
					if(i==6)
					{
						os.write("CMD_CLOSESESSION:;".getBytes());
					}
					System.out.println(i);
					i++;
					os.flush();
					sleep(3000);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void start()
	{
		try
		{
		ServerSocket sSocket=new ServerSocket(12008);
		socket =sSocket.accept();
		Send s = new Send(socket);
		//Accept a = new Accept(socket);
		s.start();
		//a.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
  public static void main(String args[])
  {
     Server s = new Server();
     s.start();
  }
}
