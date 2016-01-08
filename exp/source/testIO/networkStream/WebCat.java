package testIO.networkStream;
import java.net.*;
import java.io.*;
import com.macfaq.io.*;


public class WebCat {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Usage: java WebCat url1 url2 ...");
      return;
    }

    for (int i = 0; i < args.length; i++) {
      if (i > 0 && i < args.length) {
        System.out.println();
        System.out.println("----------------------");
        System.out.println();
      }
      System.out.println(args[i] + ":");
      try {
        URL u = new URL(args[i]);
        URLConnection uc = u.openConnection();
        uc.connect();
        InputStream in = uc.getInputStream();
       StreamCopier.copy(in, System.out);
        in.close();
      }
      catch (IOException e) {
        System.err.println(e);
      }      
    } // end for

  }

}
