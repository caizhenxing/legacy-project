package testIO.fileStream;
import java.io.*;

public class DoubleInput {


  public static void main(String[] args) {

    try {
      InputStream in1 = new FileInputStream(args[0]);
      InputStream in2 = new FileInputStream(args[0]);
    
      while (true) {
        int datum = in1.read();
        if (datum  == -1) break;
        System.out.println("in1: \t" + datum);
        datum = in2.read();
        if (datum  == -1) break;
        System.out.println("in2: \t" + datum);
      }
      in1.close();
      in2.close();
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }

}
