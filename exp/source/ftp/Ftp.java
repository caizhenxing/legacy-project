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
	public String RWFileDir = "D:/DownLoads/";//�ļ�Ŀ¼
	
	
 FtpClient aftp; 
 DataOutputStream outputs ; 
 TelnetInputStream ins; 
 TelnetOutputStream outs;

 int ch; 
 public String a; 

 private String path = "/";

 public static void main(String[] args) 
 {
 

  //����ftp������
  Ftp ft = new  Ftp();
  ft.connect(ft.RWFileDir,ft.hostname,ft.port,ft.uid,ft.pwd); 

  //�����ļ�
  if (ft.aftp != null){
   try {
    ft.getNameList(ft.RWFileDir);
   }catch(IOException e) 
   { 
    System.out.println("�����ļ�����"+e);
   } 
  }


  //�ϴ��ļ�
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
   //System.out.println("�ɹ��ϴ����ļ���");
   //ft.showFileContents("subunsubfromsp\\"); 
  }

  //ɾ��subunsubfromspĿ¼���Ѿ��ϴ����ļ��ļ�
//  ft.deleFile(RWFileDir);

  //�Ͽ�����������
  ft.stop(ft.RWFileDir);

 }

 public FtpClient connect(String RWFileDir,String hostname,int port,String uid,String pwd) 
 {
  this.hostname = hostname;
  System.out.println("��������"+hostname+"����ȴ�.....");
  try{ 
   aftp = new FtpClient(hostname,port); 
   aftp.login(uid,pwd); 
   aftp.binary();
   //aftp.openPortDataConnection();
   a = "��������:"+hostname+"�ɹ�!";
   System.out.println(a); 
  } 
  catch(FtpLoginException e){ 
   a="��½����:"+hostname+"ʧ��!�����û����������Ƿ���ȷ��"+e; 
   System.out.println(a);
   //return false; 
  } 
  catch (IOException e){ 
   a="��������:"+hostname+"ʧ��!����˿��Ƿ���ȷ��"+e; 
   System.out.println(a);
   //return false; 
  } 
  catch(SecurityException e) 
  { 
   a="��Ȩ��������:"+hostname+"����!�����Ƿ��з���Ȩ�ޣ�"+e; 
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
    message = "������"+hostname+"�����ѶϿ�!";
    System.out.println(message); 
    log(RWFileDir,message);
   }
  } 
  catch(IOException e) 
  { 
   message = "������"+hostname+"�Ͽ�����ʧ��!"+e;
   System.out.println(message); 
   log(RWFileDir,message);
  } 
 } 


 public boolean downloadFile(String RWFileDir,String filepathname){ 
  boolean result=true; 
  String message = "";
  if (aftp != null) 
  { 
   System.out.println("���������ļ�"+filepathname+",��ȴ�...."); 
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

    message = "����"+filepathname+"�ļ���"+baddir +"Ŀ¼�ɹ�!";
    System.out.println(message); 
    log(RWFileDir,message);
   } 
   catch(IOException e){ 
    message = "����"+filepathname+"�ļ���"+baddir +"Ŀ¼ʧ��!"+e; 
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
   System.out.println("�����ϴ��ļ�"+filepathname+",��ȴ�...."); 

   try{ 
    String fg =new  String("/DownLoads/"); 
    int index = filepathname.lastIndexOf(fg); 
    String filename = filepathname.substring(index+1); 
    File localFile = new File(filepathname) ;
      
    RandomAccessFile sendFile = new RandomAccessFile(filepathname,"r"); 
    // 
    sendFile.seek(0); 
    //�����ϴ�temp_
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
    
    message = "�ϴ�"+filepathname+"�ļ��ɹ�!";
    System.out.println(message); 
    log(RWFileDir,message);
   } 
   catch(IOException e){ 
    message = "�ϴ�"+filepathname+"�ļ�ʧ��!"+e;
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
  //ȡ��ReadFileĿ¼�µ�txt�ļ�
  String sdir = RWFileDir + "subunsubfromsp\\";
  File fdir = new File(sdir);
  String FileName = "";
  int j = fdir.list().length;
  String[] fsl=fdir.list();
  
  System.out.println(sdir+"Ŀ¼��Ҫɾ�����ļ�����"+fsl.length);
  File  file;
  for(int i=0;i<j;i++) 
  {
   //ɾ��subunsubfromsp�е�txt�ļ�
   FileName = RWFileDir + "subunsubfromsp\\" + (fdir.list())[i];
   file = new  File(FileName);
   file.delete();
   System.out.println("�Ѿ��ɹ�ɾ��"+FileName+"�ļ���");
  }
 //} 
 //catch (IOException e) { 
 // System.out.println("ɾ��txt�ļ�����!");
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

 

 // ���ص�ǰĿ¼�������ļ����ļ���

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
 
 // ���ص�ǰĿ¼���ļ�����

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

 // �ж�һ���ļ���Ϣ�Ƿ�ΪĿ¼

 public boolean isDir(String line) {
  return ( (String) parseLine(line).get(0)).indexOf("d") != -1;
 }

 public boolean isFile(String line) {
  return!isDir(line);
 }

 // ����getFileListȡ�õ�����Ϣ

 private ArrayList parseLine(String line) {
  ArrayList s1 = new ArrayList();
  StringTokenizer st = new StringTokenizer(line, " ");
  while (st.hasMoreTokens()) {
   s1.add(st.nextToken());
  }
   return s1;
 }

 //д��Ϣ��־
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

   //������ÿ������һ����־�ļ�
   FileWriter fwl = new FileWriter(RWFileDir + "CMSSftp"+datelog+".log",true);
   PrintWriter outl = new PrintWriter(fwl);
   outl.println(datestr + "  " + msg);
   outl.close();
   fwl.close();
  }catch (IOException e) { 
   message = "дlog�ļ�����!"+e;
   e.printStackTrace();
   log(RWFileDir,message);
   System.out.println(message);
  }
 }
}


