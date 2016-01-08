package testIO.dataStream;

import java.io.*;


public abstract class LEFilter extends DumpFilter {

  // The use of DataInputStream here is a little forced.
  // It would be more natural (though more complicated)
  // to read the bytes and manually convert them to an int
  LittleEndianInputStream lin;

  public LEFilter(LittleEndianInputStream lin) {
    super(lin);
    this.lin = lin;
  }
  
  public int available() throws IOException {
    return (buf.length - index) + lin.available();
  }
  
}
