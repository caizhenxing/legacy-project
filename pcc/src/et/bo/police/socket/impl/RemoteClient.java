package et.bo.police.socket.impl;

import java.io.*;
import java.net.*;

public class RemoteClient {
    protected String hostIp;
    protected int hostPort;
    protected BufferedReader socketReader;
    protected PrintWriter socketWriter;

    public RemoteClient(String aHostIp, int aHostPort) {
        hostIp = aHostIp;
        hostPort = aHostPort;
    }
    public static void main(String[] args) {
    	long lBegin = System.currentTimeMillis();
    	System.out.println("waiting......\r\n");
    	for(int i=0;i<=100000;i++){
    		//RemoteFileClient remoteFileClient = new RemoteFileClient("127.0.0.1", 12000);
//    		RemoteClient remoteFileClient = new RemoteClient("192.168.1.3", 12000);
//    		RemoteClient remoteFileClient = new RemoteClient("192.168.1.7", 12000);
    		RemoteClient remoteFileClient = new RemoteClient("127.0.0.1", 12000);
    		
            remoteFileClient.setUpConnection();
            
            StringBuffer sb = new StringBuffer();
            //String fileContents =remoteFileClient.getFile("C:\\TCPACHIP.LOG");
//            String fileContents =remoteFileClient.getFile("c:\\Setup.ini");
            String s =remoteFileClient.sendString("hello world@!");            
            sb.append(i+"----------------------------"+s+"\r\n");
            remoteFileClient.tearDownConnection();
            System.out.println(sb.toString());          
    	}
    	long lEnd = System.currentTimeMillis();
    	System.out.println(lEnd - lBegin);
    }
    public void setUpConnection() {
    	try {
            Socket client = new Socket(hostIp, hostPort);

            socketReader = new BufferedReader(
            		   new InputStreamReader(client.getInputStream()));
            socketWriter = new PrintWriter(client.getOutputStream());

        } catch (UnknownHostException e) {
            System.out.println("Error setting up socket connection: unknown host at " + hostIp + ":" + hostPort);
        } catch (IOException e) {
            System.out.println("Error setting up socket connection: " + e);
        }
    }
//ÒµÎñÂß¼­
    public String getFile(String fileNameToGet) {
    	StringBuffer fileLines = new StringBuffer();
        try {
            socketWriter.println(fileNameToGet);
            socketWriter.flush();

            String line = null;
            while ((line = socketReader.readLine()) != null)
                fileLines.append(line + "\n");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + fileNameToGet);
        }

        return fileLines.toString();
    }
    public String sendString(String content) {
    	StringBuffer fileLines = new StringBuffer();
    	try {
            socketWriter.println(content);
            socketWriter.flush();
            String line = null;
            while ((line = socketReader.readLine()) != null)
                fileLines.append(line + "\r\n");
//            System.out.println("000000000000000000000\r\n");
//            System.out.println(fileLines.toString());
//            System.out.println("000000000000000000000\r\n");
    	} catch (Exception e) {
//            System.out.println("Error reading from file: " + fileNameToGet);
        }
        return fileLines.toString();
    }
    public void tearDownConnection() {
    	try {
//    		if(socketWriter!=null)
            socketWriter.close();
//    		if(socketReader!=null)
            socketReader.close();
        } catch (IOException e) {
            System.out.println("Error tearing down socket connection: " + e);
        }
    }
}

