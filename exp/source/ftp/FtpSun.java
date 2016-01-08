package ftp;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpSun {

	String server="192.168.1.146";//输入的FTP服务器的IP地址
	String user="ftpuser";//登录FTP服务器的用户名
	String password="ftpuser";//登录FTP服务器的用户名的口令
	String path="/newtest";//FTP服务器上的路径
	 String filename="";
	//1）显示FTP服务器上的文件 

	void ftpList_actionPerformed(ActionEvent e) {
	
	    try {
	    FtpClient ftpClient=new FtpClient();//创建FtpClient对象
	    ftpClient.openServer(server);//连接FTP服务器
	    ftpClient.login(user, password);//登录FTP服务器
	       if (path.length()!=0) ftpClient.cd(path);
	    TelnetInputStream is=ftpClient.list();
	    int c;
	    while ((c=is.read())!=-1) {
	  System.out.print((char) c);}
	                is.close();
	                ftpClient.closeServer();//退出FTP服务器
	         } catch (IOException ex) {;}
	  }

	//2）从FTP服务器上下传一个文件

	 void getButton_actionPerformed(ActionEvent e) {
	 
	   try {
	    FtpClient ftpClient=new FtpClient();
	    ftpClient.openServer(server);
	    ftpClient.login(user, password);
	       if (path.length()!=0) ftpClient.cd(path);
	    ftpClient.binary();
	    TelnetInputStream is=ftpClient.get(filename);
	    File file_out=new File(filename);
	       FileOutputStream os=new 
	       FileOutputStream(file_out);
	       byte[] bytes=new byte[1024];
	    int c;
	    while ((c=is.read(bytes))!=-1) {
	       os.write(bytes,0,c);
	    }
	       is.close();
	       os.close();
	       ftpClient.closeServer();
	    } catch (IOException ex) {;}
	  }


	//3）向FTP服务器上上传一个文件
	 void putButton_actionPerformed(ActionEvent e) {
	 
	   try {
	    FtpClient ftpClient=new FtpClient();
	    ftpClient.openServer(server);
	    ftpClient.login(user, password);
	       if (path.length()!=0) ftpClient.cd(path);
	    ftpClient.binary();
	    TelnetOutputStream os=ftpClient.put(filename);
	    File file_in=new File(filename);
	       FileInputStream is=new FileInputStream(file_in);
	       byte[] bytes=new byte[1024];
	    int c;
	    while ((c=is.read(bytes))!=-1){
	 os.write(bytes,0,c);}
	       is.close();
	       os.close();
	       ftpClient.closeServer();
	    } catch (IOException ex) {;}
	  }
	 public static void main(String[] arg0)
	 {
		 FtpSun fs=new FtpSun();
		 //fs.ftpList_actionPerformed(new ActionEvent());
	 }
	}



