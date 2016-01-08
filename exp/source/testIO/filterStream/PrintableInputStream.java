package testIO.filterStream;

import java.io.*;


public class PrintableInputStream extends FilterInputStream {

  public PrintableInputStream(InputStream in) {
    super(in);
  }

  public int read() throws IOException {
  
    int b = in.read();
    // printing, ASCII characters
    if (b >= 32 && b <= 126) return b;
		// carriage return, linefeed, tab, and end of file
    else if (b == 10 || b == 13 || b == 9 || b == -1) return b;
    // non-printing characters
    else return '?'; 

  }

  public int read(byte[] data, int offset, int length) throws IOException {
  
    int result = in.read(data, offset, length);
    for (int i = offset; i < offset+result; i++) {
      // do nothing with the printing characters
		  // carriage return, linefeed, tab, and end of file
      if (data[i] == 10 || data[i] == 13 || data[i] == 9 || data[i] == -1) ;
      // non-printing characters
      else if (data[i] < 32 || data[i] > 126) data[i] = (byte) '?';
    }
    return result;
    
  }

  // test the stream 
  public static void main (String[] args) throws IOException {
	
	  for (int i = 0; i < args.length; i++) {
		  FileInputStream fin = new FileInputStream(args[i]);
			PrintableInputStream pin = new PrintableInputStream(fin);
			while (true) {
			  int c = pin.read();
				if (c == -1) break;
				System.out.write(c);
			}
			
		}
	
	}

}
