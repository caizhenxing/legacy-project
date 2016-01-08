package et.test.callcenter.tmp;

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
            ServerSocket server = new ServerSocket(listenPort,2000);
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
    	try {
            OutputStream outputToSocket = incomingConnection.getOutputStream();
            InputStream inputFromSocket = incomingConnection.getInputStream();

            BufferedReader streamReader =
                new BufferedReader(new InputStreamReader(inputFromSocket));

            FileReader fileReader = new FileReader(new File(streamReader.readLine()));

            BufferedReader bufferedFileReader = new BufferedReader(fileReader);
            PrintWriter streamWriter =
                new PrintWriter(incomingConnection.getOutputStream());
            String line = null;
            while ((line = bufferedFileReader.readLine()) != null) {
                streamWriter.println(line);
            }

            fileReader.close();
            streamWriter.close();
            streamReader.close();
            incomingConnection.close();
        } catch (Exception e) {
            System.out.println("Error handling a client: " + e);
        }

    }
}