package ftp;

/*
*******************************************************************************************************

 Filename:  ftp.java
 Author:   leetsing(elove)
 Create date: 2004-08-30
 Use:   connect to FTP server,then upload and download file
 Modify date: 2004-09-05 add to upload file
     2004-09-13 add to download file
 Copy right:  Magisky Media Technology Co.,Ltd.

*******************************************************************************************************
*/
//import cz.dhl.io.*;
//import cz.dhl.ftp.*;
import sun.net.ftp.*; 
import sun.net.*;
import java.applet.*; 
import java.io.*;
import java.io.IOException;
import java.util.StringTokenizer;
import sun.net.ftp.FtpClient;
import java.util.ArrayList;

public class Ftp  extends Applet
{
	public String hostname = "192.168.1.146";
	public int port = 21;
	public String uid = "ftpuser";
	public String pwd = "ftpuser";
	public String RWFileDir = "D:/DownLoads/";//文件目录
	
	
 FtpClient aftp; 
 DataOutputStream outputs ; 
 TelnetInputStream ins; 
 TelnetOutputStream outs;

 int ch; 
 public String a; 

 private String path = "/";

 public static void main(String[] args) 
 {
 

  //连接ftp服务器
  Ftp ft = new  Ftp();
  ft.connect(ft.RWFileDir,ft.hostname,ft.port,ft.uid,ft.pwd); 

  //下载文件
  if (ft.aftp != null){
   try {
    ft.getNameList(ft.RWFileDir);
   }catch(IOException e) 
   { 
    System.out.println("下载文件出错："+e);
   } 
  }


  //上传文件
  if (ft.aftp != null){
   String sdir = ft.RWFileDir;
   File fdir = new File(sdir);
   String FileName = "";
  String[] fsl=fdir.list();
  int len=fsl.length;
   for(int i=0;i<len;i++) 
   {FileName = sdir + fsl[i];
   
    ft.uploadFile(ft.RWFileDir,FileName);
   }
   //System.out.println("成功上传的文件：");
   //ft.showFileContents("subunsubfromsp\\"); 
  }

  //删除subunsubfromsp目录下已经上传的文件文件
//  ft.deleFile(RWFileDir);

  //断开服务器连接
  ft.stop(ft.RWFileDir);

 }

 public FtpClient connect(String RWFileDir,String hostname,int port,String uid,String pwd) 
 {
  this.hostname = hostname;
  System.out.println("正在连接"+hostname+"，请等待.....");
  try{ 
   aftp = new FtpClient(hostname,port); 
   aftp.login(uid,pwd); 
   aftp.binary();
   //aftp.openPortDataConnection();
   a = "连接主机:"+hostname+"成功!";
   System.out.println(a); 
  } 
  catch(FtpLoginException e){ 
   a="登陆主机:"+hostname+"失败!请检查用户名或密码是否正确："+e; 
   System.out.println(a);
   //return false; 
  } 
  catch (IOException e){ 
   a="连接主机:"+hostname+"失败!请检查端口是否正确："+e; 
   System.out.println(a);
   //return false; 
  } 
  catch(SecurityException e) 
  { 
   a="无权限与主机:"+hostname+"连接!请检查是否有访问权限："+e; 
   System.out.println(a);
   //return false; 
  } 
  
  log(RWFileDir,a);
  return aftp; 
 } 

 public void stop(String RWFileDir) 
 { 
  String message = "";
  try { 
   if(aftp!=null){
    aftp.closeServer(); 
    message = "与主机"+hostname+"连接已断开!";
    System.out.println(message); 
    log(RWFileDir,message);
   }
  } 
  catch(IOException e) 
  { 
   message = "与主机"+hostname+"断开连接失败!"+e;
   System.out.println(message); 
   log(RWFileDir,message);
  } 
 } 


 public boolean downloadFile(String RWFileDir,String filepathname){ 
  boolean result=true; 
  String message = "";
  if (aftp != null) 
  { 
   System.out.println("正在下载文件"+filepathname+",请等待...."); 
   String badfile = filepathname.substring(filepathname.length()-4,filepathname.length());
   String badlog = filepathname.substring(filepathname.length()-7,filepathname.length());
   String baddir = "";
   if ((badfile.compareTo(".bad") != 0) && (badlog.compareTo(".badlog") != 0)){
    baddir = "subunsubtosp\\";
   }
   else{
    baddir = "bad\\";
   }
   String strdir = "subunsubtosp\\";
   //System.out.println(RWFileDir + baddir + filepathname);
   try{ 
    //FtpClient fc=new FtpClient("192.168.0.56",2121);
    //fc.login("lee","lee");
    int ch;
    File fi = new File(RWFileDir + baddir + filepathname);
    //aftp.cd(strdir);
    RandomAccessFile getFile = new RandomAccessFile(fi,"rw");
    getFile.seek(0);
    TelnetInputStream fget=aftp.get(strdir+filepathname);
    DataInputStream puts = new DataInputStream(fget);
    while ((ch = puts.read()) >= 0) {
     getFile.write(ch);
     
    }
    //s.delete();
    
    fget.close();
    getFile.close();
    //fc.closeServer();

    message = "下载"+filepathname+"文件到"+baddir +"目录成功!";
    System.out.println(message); 
    log(RWFileDir,message);
   } 
   catch(IOException e){ 
    message = "下载"+filepathname+"文件到"+baddir +"目录失败!"+e; 
    System.out.println(message); 
    log(RWFileDir,message);
    result = false ; 

   } 
  } 
  else{ 
   result = false; 
  } 
  return result; 
 } 


 public boolean uploadFile(String RWFileDir,String filepathname){ 
  boolean result=true; 
  String message = "";
  if (aftp != null) 
  { 
   System.out.println("正在上传文件"+filepathname+",请等待...."); 

   try{ 
    String fg =new  String("/DownLoads/"); 
    int index = filepathname.lastIndexOf(fg); 
    String filename = filepathname.substring(index+1); 
    File localFile = new File(filepathname) ;
      
    RandomAccessFile sendFile = new RandomAccessFile(filepathname,"r"); 
    // 
    sendFile.seek(0); 
    //改名上传temp_
    filename = filename.substring(0,15)+"temp_"+filename.substring(15,filename.length());
    outs = aftp.put(filename); 
    outputs = new DataOutputStream(outs); 
    while (sendFile.getFilePointer() < sendFile.length() ) 
    { 
     ch = sendFile.read(); 
     outputs.write(ch); 
    } 
    
    rename(filename.substring(15,filename.length()),filename.substring(20,filename.length()));
    outs.close(); 
    sendFile.close(); 
    
    message = "上传"+filepathname+"文件成功!";
    System.out.println(message); 
    log(RWFileDir,message);
   } 
   catch(IOException e){ 
    message = "上传"+filepathname+"文件失败!"+e;
    e.printStackTrace();
    log(RWFileDir,message);
    result = false ; 

   } 
  } 
  else{ 
   result = false; 
  } 
  return result; 
 } 

 public void rename(String oldName,String newName){
  
   //aftp.renameTo(oldName,newName);
   File Old = new File(oldName); //oldName
   File New = new File(newName); //newName
   //aftp.renameTo(New);
   //boolean Old.renameTo(File newName);
   //System.out.println(Old);
   //System.out.println(New);
  
 }

 public static void deleFile(String RWFileDir) { 
 //try { 
  //取得ReadFile目录下的txt文件
  String sdir = RWFileDir + "subunsubfromsp\\";
  File fdir = new File(sdir);
  String FileName = "";
  int j = fdir.list().length;
  String[] fsl=fdir.list();
  
  System.out.println(sdir+"目录下要删除的文件数："+fsl.length);
  File  file;
  for(int i=0;i<j;i++) 
  {
   //删除subunsubfromsp中的txt文件
   FileName = RWFileDir + "subunsubfromsp\\" + (fdir.list())[i];
   file = new  File(FileName);
   file.delete();
   System.out.println("已经成功删除"+FileName+"文件！");
  }
 //} 
 //catch (IOException e) { 
 // System.out.println("删除txt文件错误!");
 // e.printStackTrace();
 //}
 }


 public void showFileContents(String strdir) 
 { 
  StringBuffer buf = new StringBuffer();
  try { 
   aftp.cd(strdir);
   ins= aftp.list(); 
   while ((ch=ins.read())>=0){ 
    buf.append((char)ch); 
   } 
   
   System.out.println(buf.toString()); 

   ins.close(); 
        } 
  catch(IOException e) 
  { 
  } 
 }

 

 // 返回当前目录的所有文件及文件夹

 public ArrayList getFileList() throws IOException {
  BufferedReader dr = new BufferedReader(new InputStreamReader(aftp.list()));
  ArrayList al = new ArrayList();
  String s = "";
  while ( (s = dr.readLine()) != null) {
  al.add(s);
  }
  return al;
 }

 public void setPath(String path) throws IOException {
  if (aftp == null)
  this.path = path;
  else {
   aftp.cd(path);
  }
 }
 
 // 返回当前目录的文件名称

 public ArrayList getNameList(String RWFileDir) throws IOException {
  
  BufferedReader dr = new BufferedReader(new InputStreamReader(aftp.nameList("subunsubtosp\\")));
  ArrayList al = new ArrayList();
  String s = "";
  while ( (s = dr.readLine()) != null) {
   al.add(s);
   s = s.substring(13,s.length());
   isFile(s);
   downloadFile(RWFileDir,s);
   //String strFileDelF = aftp.nameList("subunsubtosp\\");
   File fileDelF=new File(s);
   fileDelF.delete();
  }
  return al;
  //System.out.println(al.add(s));
 }

 // 判断一行文件信息是否为目录

 public boolean isDir(String line) {
  return ( (String) parseLine(line).get(0)).indexOf("d") != -1;
 }

 public boolean isFile(String line) {
  return!isDir(line);
 }

 // 处理getFileList取得的行信息

 private ArrayList parseLine(String line) {
  ArrayList s1 = new ArrayList();
  StringTokenizer st = new StringTokenizer(line, " ");
  while (st.hasMoreTokens()) {
   s1.add(st.nextToken());
  }
   return s1;
 }

 //写消息日志
 public static void log(String RWFileDir,String msg)
 {
  String message = "";
  try {
   java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   java.text.DateFormat dflog = new java.text.SimpleDateFormat("yyyyMMdd");
   java.util.Date date = new java.util.Date() ;
   String datestr = df.format(new java.util.Date()) ;
   String datelog = dflog.format(new java.util.Date()) ;
   //String datelog = datestr.substring(0,10);
   //datelog = datelog.replace('-',' ');

   //按日期每天生成一个日志文件
   FileWriter fwl = new FileWriter(RWFileDir + "CMSSftp"+datelog+".log",true);
   PrintWriter outl = new PrintWriter(fwl);
   outl.println(datestr + "  " + msg);
   outl.close();
   fwl.close();
  }catch (IOException e) { 
   message = "写log文件错误!"+e;
   e.printStackTrace();
   log(RWFileDir,message);
   System.out.println(message);
  }
 }
}


