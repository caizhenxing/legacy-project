package testIO.dataStream;
import java.io.*;

public class ReaderEcho {

  public static void main(String[] args) {

    try {
      BufferedReader din = new BufferedReader(new 
       InputStreamReader(System.in), 1);
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
