package testIO.filterStream;

import java.io.*;


public class HexFilter extends DumpFilter {

  protected int numRead = 0;
  protected int breakAfter = 24;
  protected int ratio = 3; // number of bytes of output per byte of input

  public HexFilter(InputStream in) {
    super(in);
  }

  protected void fill() throws IOException {
  
    buf = new int[ratio];
    int datum = in.read();
    this.numRead++;    
    if (datum == -1) {
      // let read() handle end of stream
      throw new EOFException();
    }

    String hex = Integer.toHexString(datum);
    if (datum < 16) { // add a leading zero
      hex = '0' + hex;
    }
    
    for (int i = 0; i < hex.length(); i++) {
      buf[i] = hex.charAt(i);
    }
    if (numRead < breakAfter) {
      buf[buf.length - 1] = ' ';
    }
    else {
      buf[buf.length - 1] = '\n';
      numRead = 0;
    }
  
  }
  
  public int available() throws IOException {
    return (buf.length - index) + ratio * in.available();
  }
  
  // With some extra effort, you could provide more efficient
  // implementations of these methods. You could even support
  // marking and resetting
  /*
    public int read(byte[] data, int offset, int length) throws IOException {}  
    public long skip(long bytesToSkip) throws IOException {}  
    public synchronized void mark(int readlimit) {}  
    public synchronized void reset() throws IOException {}  
    public boolean markSupported() {}
  */
  
}
