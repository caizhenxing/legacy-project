package testIO.inputStream;

import java.util.*;
import java.io.*;

public class RandomInputStream extends InputStream {

  private transient Random generator = new Random();

  public int read() {

    int result = generator.nextInt() % 256;
    if (result < 0) result = -result;
    return result;

  }

  public int read(byte[] data, int offset, int length) 
   throws IOException {

    byte[] temp = new byte[length];
    generator.nextBytes(temp);
    System.arraycopy(temp, 0, data, offset, length);
    return length;

  }

  public int read(byte[] data) throws IOException {

    generator.nextBytes(data);
    return data.length;

  }

  public long skip(long bytesToSkip) throws IOException {
  
    // It's all random so skipping has no effect
    return bytesToSkip;
  
  }
  
}
