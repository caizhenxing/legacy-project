package et.bo.callcenter.server.socket.implpool;

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
    	for(int i=0;i<=1;i++){
    		//RemoteFileClient remoteFileClient = new RemoteFileClient("127.0.0.1", 12000);
//    		RemoteClient remoteFileClient = new RemoteClient("192.168.1.3", 12000);
//    		RemoteClient remoteFileClient = new RemoteClient("192.168.1.7", 12000);
    		RemoteClient remoteFileClient = new RemoteClient("127.0.0.1", 12000);
    		
            remoteFileClient.setUpConnection();
            
            StringBuffer sb = new StringBuffer();
            String ss="PTSTAT:0#0#1#3!1#0#1#3!2#0#1#3!3#0#1#3!4#0#1#3!5#0#1#3!6#1#0#3!7#2#0#3!8#0#0#0!9#0#0#0!10#0#0#0!11#0#0#0!12#0#0#0!13#0#0#0!14#0#0#0!15#0#0#0!";
            sb.append(ss);
            //String fileContents =remoteFileClient.getFile("C:\\TCPACHIP.LOG");
//            String fileContents =remoteFileClient.getFile("c:\\Setup.ini");
            String s =remoteFileClient.sendString(ss);            
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
//    	StringBuffer fileLines = new StringBuffer();
    	try {
            socketWriter.println(content);
            socketWriter.flush();
            
    	} catch (Exception e) {
//            System.out.println("Error reading from file: " + fileNameToGet);
        }
        return null;
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

