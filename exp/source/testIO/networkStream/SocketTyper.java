package testIO.networkStream;
import java.net.*;
import java.io.*;
import com.macfaq.io.*;


public class SocketTyper {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Usage: java SocketTyper url1 url2 ...");
      return;
    }

    for (int i = 0; i < args.length; i++) {
      if (args.length > 1) { 
        System.out.println(args[i] + ":");
      }   
      try {
        URL u = new URL(args[i]);
        if (!u.getProtocol().equalsIgnoreCase("http")) {
          System.err.println("Sorry, " + u.getProtocol() + " is not yet supported.");
          break;
        }
        
        String host = u.getHost();
        int port = u.getPort();
        String file = u.getFile();
        // default port
        if (port <= 0) port = 80;
        
        Socket s = new Socket(host, port);
        String request = "GET " + file + " HTTP/1.0\r\n"
        + "User-Agent: MechaMozilla\r\nAccept: text/*\r\n\r\n";
        // This next line is problematic on non-ASCII systems
        byte[] b = request.getBytes();
        
        OutputStream out = s.getOutputStream();
        InputStream in = s.getInputStream();
        out.write(b);
        out.flush();
        
        StreamCopier.copy(in, System.out);
        in.close();
        out.close();
        s.close();
      }
      catch (MalformedURLException e) {
        System.err.println(e);
      } 
      catch (IOException e) {
        System.err.println(e);      
      } 
      
    }

  }

}
