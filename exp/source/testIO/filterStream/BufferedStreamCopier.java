package testIO.filterStream;

import java.io.*;


public class BufferedStreamCopier {

  public static void main(String[] args) {

    try {
      copy(System.in, System.out);
    }
    catch (IOException e) {
      System.err.println(e);
    }
    
  }

  public static void copy(InputStream in, OutputStream out) 
   throws IOException {

    // do not allow other threads to read from the
    // input or write to the output while copying is
    // taking place
    
    synchronized (in) {
      synchronized (out) {
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bout = new BufferedOutputStream(out);
  
        while (true) {
          int datum = bin.read();
          if (datum == -1) break;
          bout.write(datum);
        }
  
        bout.flush();

      }
    }
  }

}
