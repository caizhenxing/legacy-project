package testIO.dataStream;
import java.io.*;

public class Echo {

  public static void main(String[] args) {

    try {
      DataInputStream din = new DataInputStream(System.in);
      while (true) {
        String theLine = din.readLine();
        if (theLine == null) break;  // end of stream
        if (theLine.equals(".")) break; // . on line by itself
        System.out.println(theLine);      
      }    
    }
    catch (IOException e) {
      System.err.println(e);   
    }

  }

}
