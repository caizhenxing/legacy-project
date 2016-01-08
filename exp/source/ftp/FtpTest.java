package ftp;

import java.io.IOException;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.*;

public class FtpTest {

	private static FTPClient ftp_ = new FTPClient();
	 
	 public static void main(String[] args) {
	  try {
	   ftp_.connect("192.168.1.146");
	   System.out.println("Connecting...");
	   ftp_.login("ftpuser", "ftpuser");
	   System.out.println("Connetcted");
	  } catch (SocketException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }

	  try {
	   FTPFile[] files = null;
	   FTPListParseEngine engine;
	   ftp_.changeWorkingDirectory("/");
	   files = ftp_.listFiles();
	   System.out.println("Directory is " + ftp_.printWorkingDirectory());

	   System.out.println(files[0].getName());
	   System.out.println(files[0].isDirectory());
	   System.out.println(files[0].getRawListing());
	   System.out.println("***********************");
	   ftp_.changeWorkingDirectory("/pub");
	   files = ftp_.listFiles();
	   System.out.println("Directory is " + ftp_.printWorkingDirectory());

	   System.out.println(files[1].getName());
	   System.out.println(files[1].isDirectory());
	   System.out.println(files[1].getSize());
	   System.out.println(files[1].getTimestamp().getTime());
	   
	   DateFormat dateFormat =
	    new SimpleDateFormat("yyyy-MM-dd hh:mm");
	   System.out.println(dateFormat.format(files[1].getTimestamp().getTime()));
	   int idx = files[1].getRawListing().indexOf(" ");
	   System.out.println(files[1].getRawListing().substring(0,idx--));
	  } catch (IOException e1) {
	   // TODO Auto-generated catch block
	   e1.printStackTrace();
	  }

	 }
}
