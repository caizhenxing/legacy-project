package testIO.filterStream;

import java.io.*;
import java.util.*;


public class MultiOutputStream extends FilterOutputStream {

  Vector streams = new Vector();

  public MultiOutputStream(OutputStream out) {
    super(out);
    streams.addElement(out);
  }
  
  public synchronized void addOutputStream(OutputStream out) {
    streams.addElement(out);
  }

  public synchronized void write(int b) throws IOException {
  
    for (Enumeration e = streams.elements(); e.hasMoreElements();) {
      OutputStream out = (OutputStream) e.nextElement();
      out.write(b);
    }

  }

  public synchronized void write(byte[] data, int offset, int length) 
   throws IOException {

    for (Enumeration e = streams.elements(); e.hasMoreElements();) {
      OutputStream out = (OutputStream) e.nextElement();
      out.write(data, offset, length);
    }
    
  }

  public synchronized void flush() throws IOException {

    for (Enumeration e = streams.elements(); e.hasMoreElements();) {
      OutputStream out = (OutputStream) e.nextElement();
      out.flush();
    }

  }
  
  public synchronized void close() throws IOException {

    for (Enumeration e = streams.elements(); e.hasMoreElements();) {
      OutputStream out = (OutputStream) e.nextElement();
      out.close();
    }

  }

}
