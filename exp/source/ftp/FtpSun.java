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

	String server="192.168.1.146";//�����FTP��������IP��ַ
	String user="ftpuser";//��¼FTP���������û���
	String password="ftpuser";//��¼FTP���������û����Ŀ���
	String path="/newtest";//FTP�������ϵ�·��
	 String filename="";
	//1����ʾFTP�������ϵ��ļ� 

	void ftpList_actionPerformed(ActionEvent e) {
	
	    try {
	    FtpClient ftpClient=new FtpClient();//����FtpClient����
	    ftpClient.openServer(server);//����FTP������
	    ftpClient.login(user, password);//��¼FTP������
	       if (path.length()!=0) ftpClient.cd(path);
	    TelnetInputStream is=ftpClient.list();
	    int c;
	    while ((c=is.read())!=-1) {
	  System.out.print((char) c);}
	                is.close();
	                ftpClient.closeServer();//�˳�FTP������
	         } catch (IOException ex) {;}
	  }

	//2����FTP���������´�һ���ļ�

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


	//3����FTP���������ϴ�һ���ļ�
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



