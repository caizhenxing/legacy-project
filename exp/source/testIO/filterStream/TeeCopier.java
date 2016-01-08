package testIO.filterStream;
import java.io.*;
import com.macfaq.io.*;

public class TeeCopier {

  public static void main(String[] args) {

    if (args.length != 3) {
      System.out.println("Usage: java TeeCopier infile outfile1 outfile2");   
      return;
    }

    try {
      FileInputStream fin = new FileInputStream(args[0]);
      FileOutputStream fout1 = new FileOutputStream(args[1]);
      FileOutputStream fout2 = new FileOutputStream(args[2]);
      TeeOutputStream tout = new TeeOutputStream(fout1, fout2);
      BufferedStreamCopier.copy(fin, tout);
      fin.close();
      tout.close();
    }
    catch (IOException e) {
      System.err.println(e);    
    }

  }

}
