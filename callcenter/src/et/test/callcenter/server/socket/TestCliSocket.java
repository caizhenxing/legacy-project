package et.test.callcenter.server.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import et.test.callcenter.server.TestConsts;

public class TestCliSocket {
	protected String hostIp;
    protected int hostPort;
    private Socket socket;
    public TestCliSocket(){
    	
    }
	public TestCliSocket(String hostIp,int hostPort){
		this.hostIp=hostIp;
		this.hostPort=hostPort;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCliSocket tcs = new TestCliSocket("192.168.1.2",12000);
		tcs.setUpConnect();
		for(int i=0;i<TestConsts.CMD.length;i++){
			tcs.doBussness(TestConsts.CMD[i]);
			//
			try{
				Thread.sleep(2000);
			}catch(Exception e){			
			}
			//
		}
		
	}
	private void setUpConnect(){
		try{
			socket = new Socket(this.hostIp,this.hostPort);
		}catch (UnknownHostException e) {
	          System.out.println("Error setting up socket connection: unknown host at " + hostIp + ":" + hostPort);
	      } catch (IOException e) {
	          System.out.println("Error setting up socket connection: " + e);
	      }
		
	}
	private void doBussness(String s){
		try {
			//字符流
			BufferedReader socketReader = new BufferedReader(
          		   new InputStreamReader(socket.getInputStream()));
			PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());
			
			//字节流，缓冲
			BufferedInputStream bis = 
    			new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream bos = 
    			new BufferedOutputStream(socket.getOutputStream());
			
    		//数据流，缓冲
            DataInputStream dis = 
            	new DataInputStream(
            			new BufferedInputStream(socket.getInputStream())
            			);
            DataOutputStream dos = 
            	new DataOutputStream(
            			new BufferedOutputStream(socket.getOutputStream())
            			);
            //
            bos.write(s.getBytes());
            bos.flush();
            
            //
      } catch (UnknownHostException e) {
          System.out.println("Error setting up socket connection: unknown host at " + hostIp + ":" + hostPort);
      } catch (IOException e) {
          System.out.println("Error setting up socket connection: " + e);
      }
	}
}
