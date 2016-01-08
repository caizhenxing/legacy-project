package testIO.fileStream;
import java.io.*;
import com.macfaq.io.*;


public class FileCopier {

  public static void main(String[] args) {

    if (args.length != 2) {
      System.err.println("Usage: java FileCopier infile outfile");
			return;
    }
    try {
      copy(args[0], args[1]);
    }
    catch (IOException e) {
      System.err.println(e);
    }

  }

  public static void copy(String inFile, String outFile) 
   throws IOException {

    FileInputStream fin = null;
    FileOutputStream fout = null;
    
    try {
      fin  = new FileInputStream(inFile);
      fout = new FileOutputStream(outFile);
      StreamCopier.copy(fin, fout);
    }
    finally {
      try {
        if (fin != null) fin.close();
      }
      catch (IOException e) {}
      try {
        if (fout != null) fout.close();
       }
      catch (IOException e) {}
    }

  }

}
