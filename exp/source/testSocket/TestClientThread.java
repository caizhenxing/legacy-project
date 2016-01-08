package hl.bo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClientThread extends Thread {

	private Socket s=null;
	private BufferedReader in;
	public TestClientThread(Socket s)
	{
		
		this.s=s;
		System.out.println(s.getInetAddress().getHostName());
		try {
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			//tmb.setOut(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		start();
	}
	public void run()
	{
		while(true)
		{
			try {
				String str=in.readLine();
				//System.out.println(str);
				if(str==null)
					break;
				System.out.println(str);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				try {
					s.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
			}
		}
		
	}
}
