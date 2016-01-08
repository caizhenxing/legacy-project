package ocelot.common.ftp.impl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import ocelot.common.ftp.FtpService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class FtpServiceImpl implements FtpService {

	private static Log log = LogFactory.getLog(FtpServiceImpl.class);
	private String userName=null;
	private String passWord=null;
	private String url=null;
	private String path=null;
	
	public void setUserName(String name) {
		// TODO Auto-generated method stub
		userName=name;
	}

	public void setPassWord(String pw) {
		// TODO Auto-generated method stub
		passWord=pw;
	}

	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url=url;
	}

	public void setPath(String path) {
		// TODO Auto-generated method stub
		this.path=path;
	}

	public void uploadFile(String name, InputStream fis) {
		// TODO Auto-generated method stub
		FTPClient ftp=new FTPClient();
		try {
			  int i=url.lastIndexOf(":");
			  if(i==-1)
			  ftp.connect(url);
			  else
			  {
				  String com=url.substring(i+1,url.length());
				  ftp.connect(url.substring(0,i),Integer.parseInt(com));
				  
			  }
			  ftp.login(userName,passWord);
			  changedir(path,ftp);
			  
			  
			  ftp.setFileType(FTP.BINARY_FILE_TYPE); //以BINARY格式传送文件
			 ftp.setBufferSize(4096);
			  ftp.storeFile(name,fis);   //存储文件到ftp的/file目录中。
			  fis.close();
			  //ftp.logout();
			  ftp.disconnect();
			 } catch(Exception e) {
			  log.error(this,e);
			  
			 }
	}
	
	private void changedir(String path,FTPClient ftp) throws IOException
	{
		List<String> v=new ArrayList<String>();
		StringTokenizer st= new StringTokenizer(path,"/");
		while( st.hasMoreTokens()){
		String sTemp=(String)st.nextElement();
		
		v.add(sTemp);
		}
		for(int i=0,size=v.size();i<size;i++)
		{
			String subpath=v.get(i);
				if(!ftp.changeWorkingDirectory(subpath))
				  {
					  ftp.makeDirectory(subpath);
					  ftp.changeWorkingDirectory(subpath);
				  }
		}
	}
	public static void main(String[] arg0)
	{
		File f=new File("d:\\a.zip");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FtpServiceImpl fsi=new FtpServiceImpl();
		//fsi.setPassWord("topglory2005well");
		fsi.setUrl("218.25.68.138");
		fsi.setUserName("upload");
		fsi.setPassWord("upload123456");
		//fsi.setPath("aa");
		//fsi.uploadFile("aaa.zip",fis);
		fsi.changePath("aaa/bbb/ccc");
		//fsi.download("aa.zip",fis);
		//fsi.removeFile("aa.zip");
	}

	public void removeFile(String namePath) {
		// TODO Auto-generated method stub
		FTPClient ftp=new FTPClient();
		try {
			  int i=url.lastIndexOf(":");
			  if(i==-1)
			  ftp.connect(url);
			  else
			  {
				  String com=url.substring(i+1,url.length());
				  ftp.connect(url.substring(0,i),Integer.parseInt(com));
			  }
			  ftp.login(userName,passWord);
			  int o=namePath.lastIndexOf("/");
			  String name=null;
			  if(o!=-1)
			  {
			  String path=namePath.substring(0,o);
			  name=namePath.substring(o+1,namePath.length());
			  changedir(path,ftp);
			  }
			  else
				  name=namePath;
			  ftp.deleteFile(name);
			 
			  //ftp.logout();
			  ftp.disconnect();
			 } catch(Exception e) {
			  log.error(this,e);
			  
			 }
	}

	public void  download(String namePath,OutputStream os) {
		// TODO Auto-generated method stub
		FTPClient ftp=new FTPClient();
		try {
			  int i=url.lastIndexOf(":");
			  if(i==-1)
			  ftp.connect(url);
			  else
			  {
				  String com=url.substring(i+1,url.length());
				  ftp.connect(url.substring(0,i),Integer.parseInt(com));
			  }
			  ftp.login(userName,passWord);
			  int o=namePath.lastIndexOf("/");
			  String name=null;
			  if(o!=-1)
			  {
			  String path=namePath.substring(0,o);
			  name=namePath.substring(o+1,namePath.length());
			  changedir(path,ftp);
			  }
			  else
				  name=namePath;
			  ftp.retrieveFile(name,os);
			 
			 
			  
			 ftp.disconnect();
			 
			 } catch(Exception e) {
			  log.error(this,e);
			  
			 }
	}

	public String changePath(String path) {
		// TODO Auto-generated method stub
		FTPClient ftp=new FTPClient();
		try {
			  int i=url.lastIndexOf(":");
			  if(i==-1)
			  ftp.connect(url);
			  else
			  {
				  String com=url.substring(i+1,url.length());
				  ftp.connect(url.substring(0,i),Integer.parseInt(com));
				  
			  }
			  ftp.login(userName,passWord);
			  changedir(path,ftp);
			  
			 
			  //ftp.logout();
			  ftp.disconnect();
			  
			 } catch(Exception e) {
			  log.error(this,e);
			  
			 }
		return path;
	}
	
}
