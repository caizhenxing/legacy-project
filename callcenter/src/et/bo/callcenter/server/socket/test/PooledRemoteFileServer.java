package et.bo.callcenter.server.socket.test;
import java.io.*;
import java.net.*;
public class PooledRemoteFileServer {
	protected int maxConnections;
    protected int listenPort=12000;
    protected ServerSocket serverSocket;

    public PooledRemoteFileServer(int aListenPort, int maxConnections) {
        listenPort = aListenPort;
        this.maxConnections = maxConnections;
    }
    public static void main(String[] args) {
    	PooledRemoteFileServer server = new PooledRemoteFileServer(12000, 5);
        server.setUpHandlers();
        server.acceptConnections();
    }
    public void setUpHandlers() {
    	for (int i = 0; i < maxConnections; i++) {
    		System.out.println("this is +"+i+"handler.---------------------");
            PooledConnectionHandler currentHandler = new PooledConnectionHandler();
            new Thread(currentHandler, "Handler " + i).start();
        }
    }
    public void acceptConnections() {
    	try {
            ServerSocket server = new ServerSocket(listenPort,4000);
            Socket incomingConnection = null;
            while (true) {
                incomingConnection = server.accept();
                handleConnection(incomingConnection);
            }
        } catch (BindException e) {
        System.out.println("Unable to bind to port " + listenPort);
        } catch (IOException e) {
        System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
    }
    protected void handleConnection(Socket incomingConnection) {
    	try{
    		PooledConnectionHandler.processRequest(incomingConnection);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
}