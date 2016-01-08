package et.bo.forum.common.io.basic;

/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ¹¼Ð¡·å
 * @version 1.0
 */
//: OutFile.java
// Shorthand class for opening an output file
// for data storage.

import java.io.*;

public class OutFile extends DataOutputStream {
  public OutFile(String filename)
    throws IOException {
    super(
      new BufferedOutputStream(
        new FileOutputStream(filename)));
  }
  public OutFile(String filename,boolean append)
    throws IOException {
    super(
      new BufferedOutputStream(
        new FileOutputStream(filename,append)));
  }
  public OutFile(File file)
    throws IOException {
    this(file.getPath());
  }
} ///:~
