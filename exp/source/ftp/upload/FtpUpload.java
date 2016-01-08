package ftp.upload;

import java.io.FileInputStream;

import org.apache.commons.net.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

public class FtpUpload {

	 String ftpHostname="192.168.1.146";   //ftp������ַ
	 String ftpUser="ftpuser";  //�û���
	 String ftpPwd="ftpuser";   //����
	 String ftpDir="/";  //ftpĿ¼
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
			  ftp.setFileType(FTP.BINARY_FILE_TYPE); //��BINARY��ʽ�����ļ�
			  fnewname="F:/bak/zhaoyifei/e/log.txt"; //������Ҫ���������ļ����ڱ��ػ����ĸ�Ŀ¼·����Ҳ����myFile.saveAs��·��

			  FileInputStream fis=new FileInputStream(fnewname);
			  ftp.storeFile(newname,fis);   //�洢�ļ���ftp��/fileĿ¼�С�
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
