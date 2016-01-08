package testIO.fileStream;
import java.io.*;
import com.macfaq.io.*;


public class FileTyper {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println("Usage: java FileTyper file1 file2 ...");
      return;
    }
    
    for (int i = 0; i < args.length; i++) {
      try {
        typeFile(args[i]);
        if (i+1 < args.length) { // more files to type
          System.out.println("\n------------------------------------");
        }   
      }
      catch (IOException e) {
        System.err.println(e);
      }
    }

  }

  public static void typeFile(String filename) throws IOException {

    FileInputStream fin = new FileInputStream(filename);
    StreamCopier.copy(fin, System.out);
    fin.close();

  }

}
