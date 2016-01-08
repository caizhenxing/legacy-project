package testIO.memory;
import java.io.*;

public class FibonacciWriter extends Thread {

  DataOutputStream theOutput;
  int howMany;

  public FibonacciWriter(OutputStream out, int howMany) 
   throws IOException {
    theOutput = new DataOutputStream(out);
    this.howMany = howMany;
  }

  public FibonacciWriter(OutputStream out) throws IOException {
    this(out, Integer.MAX_VALUE);
  }


  public void run() {

    try {
      int f1 = 1;
      int f2 = 1;
      theOutput.writeInt(f1);
      theOutput.writeInt(f2);
  
      // now calculate the rest
      for (int i = 2; i < howMany; i++) {
        int temp = f2;
        f2 = f2 + f1;
        f1 = temp;
       if (f2 < 0) { // overflow
         break;
       }
        theOutput.writeInt(f2);
      }
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }

}
