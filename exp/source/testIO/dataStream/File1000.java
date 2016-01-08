package testIO.dataStream;
import java.io.*;


public class File1000 {

  public static void main(String args[]) {
  
    DataOutputStream dos = null;
  
    try {
      dos = new DataOutputStream(new FileOutputStream("1000.dat"));
      for (int i = 1; i <= 1000; i++) {
        dos.writeInt(i);
      }    
    }
    catch (IOException e) {
      System.err.println(e);
    }
    finally {
      try { if (dos != null) dos.close(); }
      catch (IOException e) {}    
    }
  
  }

}
