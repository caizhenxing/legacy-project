package testIO.networkStream;
import java.net.*;
import java.io.*;
import com.macfaq.io.*;

public class MailClient {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Usage: java MailClient username@host.com");
      return;
    }
    
    try {
      URL u = new URL("mailto:" + args[0]);
      URLConnection uc = u.openConnection();
      uc.setDoOutput(true);
      uc.connect();
      OutputStream out = uc.getOutputStream();
      StreamCopier.copy(System.in, out);
      out.close();
    }
    catch (IOException e) {
      System.err.println(e);
    }      

  }

}
