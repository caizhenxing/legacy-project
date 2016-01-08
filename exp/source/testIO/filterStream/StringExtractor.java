package testIO.filterStream;
import com.macfaq.io.*;
import java.io.*;


public class StringExtractor {

  public static void main(String[] args) {
    
    if (args.length < 1) {
      System.out.println("Usage: java StringExtractor inFile");
      return;
    }
    try {
      FileInputStream fin = new FileInputStream(args[0]);
      OutputStream out;
      if (args.length >= 2) {
        out = new FileOutputStream(args[1]);
      }
      else out = System.out;
      
      // Here's where the output stream is chained
      // to the ASCII output stream
      PrintableOutputStream pout = new PrintableOutputStream(out);
      int b;
      while ((b = fin.read()) != -1) pout.write(b);
      // Alternately
      // StreamCopier.copy(fin, pout);
    }
    catch (FileNotFoundException e) {
      System.out.println("Usage: java StringExtractor inFile outFile");  
    }
    catch (IOException e) {
      System.err.println(e);  
    }
  
  }

}
