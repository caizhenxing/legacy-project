package et.test.callcenter.tmp1;

import java.io.*;
import java.net.*;

public class RemoteFileServer {
    protected int listenPort = 12000;
    public static void main(String[] args) {
    	RemoteFileServer server = new RemoteFileServer();
        server.acceptConnections();
    }
    public void acceptConnections() {
    	try {
            ServerSocket server = new ServerSocket(listenPort, 5);
            Socket incomingConnection = null;
            while (true) {
                incomingConnection = server.accept();
                handleConnection(incomingConnection);
            }
        } catch (BindException e) {
        System.out.println("Unable to bind to port " + listenPort);
        } catch (IOException e) {
        System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }
    }
    //ÒµÎñÂß¼­
    public void handleConnection(Socket incomingConnection) {
    	new Thread(new ConnectionHandler(incomingConnection)).start();

    }
}