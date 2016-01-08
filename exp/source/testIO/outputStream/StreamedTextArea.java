package testIO.outputStream;

import java.awt.*;
import java.io.*;


public class StreamedTextArea extends TextArea {

  OutputStream theOutput = new TextAreaOutputStream();

  public StreamedTextArea() {
    this("", 0, 0, SCROLLBARS_BOTH);
  }

  public StreamedTextArea(String text) {
    this(text, 0, 0, SCROLLBARS_BOTH);
  } 

  public StreamedTextArea(int rows, int columns) {
    this("", rows, columns, SCROLLBARS_BOTH);
  }

  public StreamedTextArea(String text, int rows, int columns) {
    this(text, rows, columns, SCROLLBARS_BOTH);
  }

  public StreamedTextArea(String text, int rows, int columns, int scrollbars) {

    super(text, rows, columns, scrollbars);
    setEditable(false);

  }

  public OutputStream getOutputStream() {
    return theOutput;
  }

  class TextAreaOutputStream extends OutputStream {

    public synchronized void write(int b) {

      // recall that the int should really just be a byte
      b &= 0x000000FF;

      // must convert byte to a char in order to append it
      char c = (char) b;
      append(String.valueOf(c));

    }

    public synchronized void write(byte[] b, int offset, int length) {

      append(new String(b, offset, length));

    }

  }

}
