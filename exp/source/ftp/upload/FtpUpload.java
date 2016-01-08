package ftp.upload;

import java.io.FileInputStream;

import org.apache.commons.net.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

public class FtpUpload {

	 String ftpHostname="192.168.1.146";   //ftp主机地址
	 String ftpUser="ftpuser";  //用户名
	 String ftpPwd="ftpuser";   //密码
	 String ftpDir="/";  //ftp目录
	 String fnewname="",newname="log.txt";
	public boolean upload()
	{
		FTPClient ftp=new FTPClient();
		try {
			  ftp.connect(ftpHostname);
			  ftp.login(ftpUser,ftpPwd);
			  ftp.changeWorkingDirectory(ftpDir);
			  ftp.makeDirectory("newtest");
			  ftp.changeWorkingDirectory("/newtest");
			  ftp.setFileType(FTP.BINARY_FILE_TYPE); //以BINARY格式传送文件
			  fnewname="F:/bak/zhaoyifei/e/log.txt"; //这里需要给出的是文件所在本地机器的根目录路径，也就是myFile.saveAs的路径

			  FileInputStream fis=new FileInputStream(fnewname);
			  ftp.storeFile(newname,fis);   //存储文件到ftp的/file目录中。
			  fis.close();
			 } catch(Exception e) {
			  e.printStackTrace();
			  return false;
			 }
		return true;
	}
	public static void main(String[] arg0)
	{
		FtpUpload fu=new FtpUpload();
		System.out.println(fu.upload());
	}
}
