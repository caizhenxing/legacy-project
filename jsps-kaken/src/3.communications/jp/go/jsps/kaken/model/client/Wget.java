package jp.go.jsps.kaken.model.client;

/*
 * This is a simply wget.
 * You can test your URL
 *
 * $Log: Wget.java,v $
 * Revision 1.1  2007/06/28 02:07:51  administrator
 * *** empty log message ***
 *
 * Revision 1.4  2006/07/19 05:10:37  sai
 * *** empty log message ***
 *
 * Revision 1.1  2006/02/07 01:32:47  xiongtp
 * *** empty log message ***
 *
 * Revision 1.1  2004/07/20 00:48:42  terakado
 * *** empty log message ***
 *
 * Revision 1.1  2004/03/31 08:04:25  takano
 * *** empty log message ***
 *
 */
 
import java.io.IOException;
import java.io.EOFException;
import java.io.DataInputStream;
import java.io.PrintStream;
 
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
 
/**
 * UNIXプラットフォームに存在する「wget」コマンドのJava版。
 * ID RCSfile="$RCSfile: Wget.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:51 $"
 */
public class Wget {
  
  static final PrintStream out = System.out;
  static final String commandName = Wget.class.getName();
  static int count;
  static boolean verb;
  static boolean output = true;
  
  
  /**
   * メインエントリ。
   * Usage : Wget [-v] [-np] ＜url＞
   * @param args
   * @throws IOException
   */
  public static void main(String args[]) throws IOException {
    try {
      while (args[0].charAt(0) == '-') {
        String argument = args[0].substring(1);
        if (argument.equals("v")) {
          verb = true;
        } else if (argument.equals("np")) {
          output = false;
        }
        args[0] = args[1];
        if (args.length > 2) {
          args[1] = args[2];
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      out.println( "USAGE: " + commandName + " [-v] [-np] <url>" );
      out.println( "\t-v  : verbose all action." );
      out.println( "\t-np : don't print the data from the URL." );
      System.exit( 1 );
    }
 
    URLConnection url = (new URL(args[0])).openConnection();
 
    if (url instanceof HttpURLConnection) {
      readHttpURL((HttpURLConnection) url);
    } else {
      readURL(url);
    }
  }
  
  
  /**
   * 
   * @param url
   * @throws IOException
   */
  private static final void readURL(URLConnection url) throws IOException {
    DataInputStream in = new DataInputStream(url.getInputStream());
    printHeader(url);
 
    try {
      while (true) {
        writeChar((char) in.readUnsignedByte());
      }
    } catch (EOFException e) {
      if (output) verbose("\n");
      verbose(commandName +
              ": Read " + count +
              " bytes from " + url.getURL());
    } catch (IOException e) {
      out.println( e + ": " + e.getMessage());
      if (output) verbose("\n");
      verbose(commandName +
              ": Read " + count +
              " bytes from " + url.getURL());
    }
    System.exit(0);
  }
  
  
  /**
   * 
   * @param url
   * @throws IOException
   */
  private static final void readHttpURL(HttpURLConnection url) 
    throws IOException {
 
    long before, after;
 
    url.setAllowUserInteraction (true);
    verbose(commandName + ": Contacting the URL ...");
    url.connect();
    verbose(commandName + ": Connect. Waiting for reply ...");
    before = System.currentTimeMillis();
    DataInputStream in = new DataInputStream(url.getInputStream());
    after = System.currentTimeMillis();
    verbose(commandName + ": The reply takes " + 
            ((int) (after - before) / 1000) + " seconds");
 
    before = System.currentTimeMillis();
 
 
    try {
      if (url.getResponseCode() != HttpURLConnection.HTTP_OK) {
        out.println(commandName + ": " + url.getResponseMessage());
      } else {
        printHeader(url);
        while (true) {
          writeChar((char) in.readUnsignedByte());
        }
      }
    } catch (EOFException e) {
      after = System.currentTimeMillis();
      int milliSeconds = (int) (after-before);
      if (output) verbose("\n");
      verbose(commandName +
              ": Read " + count +
              " bytes from " + url.getURL());
      verbose(commandName + ": HTTP/1.0 " + url.getResponseCode() +
              " " + url.getResponseMessage());
      url.disconnect();
 
      verbose(commandName + ": It takes " + (milliSeconds/1000) + 
              " seconds" + " (at " + round(count/(float) milliSeconds) + 
              " K/sec).");
      if (url.usingProxy()) {
        verbose(commandName + ": This URL uses a proxy");
      }
    } catch (IOException e) {
      out.println( e + ": " + e.getMessage());
      if (output) verbose("\n");
      verbose(commandName +
              ": I/O Error : Read " + count +
              " bytes from " + url.getURL());
      out.println(commandName + ": I/O Error " + url.getResponseMessage());
    }
    System.exit(0);
  }
  
  
  /**
   * 
   * @param url
   */
  private static final void printHeader(URLConnection url) {
    verbose(Wget.class.getName() + ": Content-Length   : " + 
            url.getContentLength() );
    verbose(Wget.class.getName() + ": Content-Type     : " + 
            url.getContentType() );
    if (url.getContentEncoding() != null)
      verbose(Wget.class.getName() + ": Content-Encoding : " + 
              url.getContentEncoding() );
    if (output) verbose("");
  }
  
  
  /**
   * 
   * @param c
   */
  private final static void writeChar(char c) {
    if (output) out.print(c);
    count++;
  }
  
  /**
   * 
   * @param s
   */
  private static final void verbose(String s) {
    if (verb) out.println( s );
  }
  
  /**
   * 
   * @param f
   * @return
   */
  private static final float round(float f) {
    return Math.round(f * 100) / (float) 100;
  }
  
  
}

      