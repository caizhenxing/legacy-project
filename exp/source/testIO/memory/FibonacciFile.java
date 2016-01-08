package testIO.memory;
import java.io.*;


public class FibonacciFile {

  public static void main(String args[]) {

    String outputFile = "fibonacci.dat";
    if (args.length > 0) outputFile = args[0];
    int howMany;
    try {
      howMany = Integer.parseInt(args[1]);
    }
    catch (Exception e) {
      howMany = 20;
    }
     
    try {
      // so that the buffer doesn't have to be resized
      // we calculate in advance the size of the necessary byte array
      ByteArrayOutputStream bout = new ByteArrayOutputStream(howMany*4);
      DataOutputStream dout = new DataOutputStream(bout);
  
      // first two Fibonacci numbers must be given
      // to start the process
      int f1 = 1;
      int f2 = 1;
      dout.writeInt(f1);
      dout.writeInt(f2);
  
      // now calculate the rest
      for (int i = 2; i < howMany; i++) {
        int temp = f2;
        f2 = f2 + f1;
        f1 = temp;
        dout.writeInt(f2);
      }
  
      FileOutputStream fout = new FileOutputStream(outputFile);
      fout.write(bout.toByteArray());
      fout.flush();
      fout.close();
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }
  
}
