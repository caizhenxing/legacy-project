package testIO.dataStream;
import java.io.*;

public class UnsignedShortReader {

  public static void main(String[] args) {
  
    for (int i = 0; i < args.length; i++) {
      
      try {
        FileInputStream fin = new FileInputStream(args[i]);
        // now that we know the file exists, print its name
        System.out.println("-----------" + args[i] + "-----------");
        DataInputStream din = new DataInputStream(fin);
        while (true) {
          int theNumber = din.readUnsignedShort();
          System.out.println(theNumber);               
        }  // end while        
      } // end try
      catch (EOFException e) {
        // normal termination
      }
      catch (IOException e) {
        // abnormal termination
        System.err.println(e);
      }
      
    }  // end for
    
  }  // end main

}  // end UnsignedShortReader
