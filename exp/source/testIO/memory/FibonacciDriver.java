package testIO.memory;
import java.io.*;


public class FibonacciDriver {

  public static void main (String[] args) {

    int howMany;
    try {
      howMany = Integer.parseInt(args[0]);
    }
    catch (Exception e) {
      howMany = 20;
    }

    try {
      PipedOutputStream pout = new PipedOutputStream();
      PipedInputStream pin = new PipedInputStream(pout);

      FibonacciWriter fw = new FibonacciWriter(pout, howMany);
      FibonacciReader fr = new FibonacciReader(pin);
      fw.start();
      fr.start();
    }
    catch (IOException e) {
      System.err.println(e);
    }

  } 

}
