package testIO.networkStream;
import java.net.*;
import java.io.*;
import com.macfaq.io.*;


public class URLTyper {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Usage: java URLTyper url1 url2 ...");
      return;
    }

    for (int i = 0; i < args.length; i++) {
      if (args.length > 1) { 
        System.out.println(args[i] + ":");
      }   
      try {
        URL u = new URL(args[i]);
        InputStream in = u.openStream();
        StreamCopier.copy(in, System.out);
        in.close();
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
