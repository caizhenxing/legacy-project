package testIO.networkStream;
import java.net.*;
import java.io.*;

public class HelloServer {

  public final static int defaultPort = 2345;

  public static void main(String[] args) {
  
    int port = defaultPort;
    
    try {
      port = Integer.parseInt(args[0]);
    }
    catch (Exception e) {
    }
    if (port <= 0 || port >= 65536) port = defaultPort;
    
    try {
      ServerSocket ss = new ServerSocket(port);
      while (true) {
        try {
          Socket s = ss.accept();
          
          String response = "Hello " + s.getInetAddress() + " on port " 
           + s.getPort() + "\r\n";
          response += "This is " + s.getLocalAddress() + " on port " 
           + s.getLocalPort() + "\r\n";
          OutputStream out = s.getOutputStream();
          out.write(response.getBytes());
          out.flush();
          s.close();
        }
        catch (IOException e) {
        }
      }
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }
  
}
