package et.bo.forum.common.io.basic;

/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ¹¼Ð¡·å
 * @version 1.0
 */

//: PrintFile.java
// Shorthand class for opening an output file
// for human-readable output.

import java.io.*;

public class PrintFile extends PrintStream {
  public PrintFile(String filename)
    throws IOException {
    super(
      new BufferedOutputStream(
        new FileOutputStream(filename)));
  }
  public PrintFile(String filename,boolean append)
    throws IOException {
    super(
      new BufferedOutputStream(
        new FileOutputStream(filename)));
  }
  public PrintFile(File file)
    throws IOException {
    this(file.getPath());
  }
} ///:~
