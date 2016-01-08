package et.test.callcenter.tmp2;

import java.io.*;
import java.net.*;

public class RemoteFileClient {
    protected String hostIp;
    protected int hostPort;
    protected BufferedReader socketReader;
    protected PrintWriter socketWriter;

    public RemoteFileClient(String aHostIp, int aHostPort) {
        hostIp = aHostIp;
        hostPort = aHostPort;
    }
    public static void main(String[] args) {
    	long lBegin = System.currentTimeMillis();
    	System.out.println("waiting......\r\n");
    	for(int i=0;i<=100000;i++){
    		RemoteFileClient remoteFileClient = new RemoteFileClient("127.0.0.1", 12000);
    		//RemoteFileClient remoteFileClient = new RemoteFileClient("192.168.1.3", 12000);
    		//RemoteFileClient remoteFileClient = new RemoteFileClient("192.168.1.7", 12000);
            remoteFileClient.setUpConnection();
            StringBuffer sb = new StringBuffer();
            //String fileContents =remoteFileClient.getFile("C:\\TCPACHIP.LOG");
            String fileContents =remoteFileClient.getFile("c:\\a.txt");
            
            sb.append(fileContents);
            sb.append(System.currentTimeMillis()+"\r\n");
            sb.append(i+"----------------------------\r\n");
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

    public void tearDownConnection() {
    	try {
            socketWriter.close();
            socketReader.close();
        } catch (IOException e) {
            System.out.println("Error tearing down socket connection: " + e);
        }
    }
}

