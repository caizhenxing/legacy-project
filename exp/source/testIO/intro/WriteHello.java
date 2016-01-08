package testIO.intro;
import java.io.*;


public class WriteHello {

  public static void main(String[] args) throws IOException {
  
    byte[] hello = {72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33, 10, 13};
    System.out.write(hello);

  }
  
}