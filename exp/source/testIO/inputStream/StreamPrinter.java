package testIO.inputStream;

import java.io.*;

public class StreamPrinter {

  InputStream theInput;

  public static void main(String[] args) {
    StreamPrinter sp = new StreamPrinter(System.in);
    sp.print();
  }

  public StreamPrinter(InputStream in) {
    theInput = in;
  }

  public void print() {

    try {
      while (true) {
        int datum = theInput.read();
        if (datum  == -1) break;
        System.out.println(datum);
      }
    }
    catch (IOException e) {
      System.err.println("Couldn't read from System.in!");
    }

  }

}
