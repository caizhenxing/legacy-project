/**
 * 	@(#)TestClient.java   2006-12-11 ÏÂÎç12:41:22
 *	 ¡£ 
 *	 
 */
package et.test.callcenter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

 /**
 * @author ddddd
 * @version 2006-12-11
 * @see
 */
public class TestClient {
	private int port=12000;
    private String host="192.168.1.3";
	Socket socket=null;
	
	/**
	 * @param
	 * @version 2006-12-11
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestClient tc=new TestClient();
		tc.getSeri();
	}
	public void getSeri()
	{
		Queue<String> cms=new LinkedList<String>();
		try {
			socket=new Socket(host,port);
			socket.setSoTimeout(3000);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		while (true) {
			StringBuilder sb = new StringBuilder();
			InputStream is = null;
			try {
				
					is = socket.getInputStream();
				
				int i;
				while ((i = is.read()) != 59 && i != -1) {
					sb.append((char) i);
				}
				if (i == -1)
					continue;
			} catch (IOException e) {

			}
			String mm = sb.toString();
			if(!"".equals(mm))
			{
			System.out.println(mm);
			cms.add(mm + ";");
			}
		}		
        
	}
}
