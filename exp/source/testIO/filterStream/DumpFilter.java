package testIO.filterStream;

import java.io.*;


public abstract class DumpFilter extends FilterInputStream {

  // This is really an array of unsigned bytes
  protected int[] buf = new int[0];
  protected int index = 0;
  
  public DumpFilter(InputStream in) {
    super(in);
  }

  public int read() throws IOException {
  
    int result;
    if (index < buf.length) {
      result = buf[index];
      index++;
    }  // end if
    else {
      try {
        this.fill();
        // fill is required to put at least one byte 
        // in the buffer or throw an EOF or IOException
        result = buf[0];
        index = 1;
      }
      catch (EOFException e) {
        result = -1;
      }
    }  // end else
    
    return result;
    
  }

  protected abstract void fill() throws IOException;
  
  public int read(byte[] data, int offset, int length) throws IOException {
  
    if (data == null) {
      throw new NullPointerException();
    } 
    else if ((offset < 0) || (length < 0) 
     || ((offset + length) > data.length) || ((offset + length) < 0)) {
      throw new ArrayIndexOutOfBoundsException();
    } 
    else if (length == 0) {
      return 0;
    }

    // check for end of stream
    int datum = this.read();
    if (datum == -1) {
      return -1;
    }
    
    data[offset] = (byte) datum;

    int bytesRead = 1;
    try {
      for (; bytesRead < length ; bytesRead++) {
      
        datum = this.read();
        
        // in case of end of stream, return as much as we've got,
        //  then wait for the next call to read to return -1
        if (datum == -1) break;
        data[offset + bytesRead] = (byte) datum;
      }
    }
    catch (IOException e) {
      // return what's already in the data array
    }
    
    return bytesRead;   
    
  }
  
  public int available() throws IOException {
    return buf.length - index;
  }
  
  public long skip(long bytesToSkip) throws IOException {
  
    long bytesSkipped = 0;
    for (; bytesSkipped < bytesToSkip; bytesSkipped++) {
      int c = this.read();
      if (c == -1) break;
    }
    return bytesSkipped;
    
  }

  public synchronized void mark(int readlimit) {}

  public synchronized void reset() throws IOException {
    throw new IOException("marking not supported");
  }

  public boolean markSupported() {
    return false;
  }
  
}
