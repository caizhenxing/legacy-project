package hl.bo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	public static void main(String[] arg0)
	{
		Socket socket = null;
		
		try {
			InetAddress addr=InetAddress.getByName("192.168.1.81");
			socket=new Socket(addr,8088);
			
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			
			while(true)
			{
				
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				
				String s=br.readLine();
				
				out.println("white"+": "+s);
				String str=in.readLine();
				
				System.out.println(str);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
