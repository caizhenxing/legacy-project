package ftp;

import java.applet.Applet;
import sun.net.ftp.*; 
import sun.net.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.applet.*; 
import java.io.*; 

public class FtpApplet extends Applet {

	FtpClient aftp; 
	DataOutputStream outputs ; 
	TelnetInputStream ins; 
	TelnetOutputStream outs; 
	TextArea lsArea; 
	Label    LblPrompt; 
	Button   BtnConn; 
	Button   BtnClose; 
	TextField  TxtUID; 
	TextField  TxtPWD; 
	TextField  TxtHost; 
	int ch; 
	public String a="没有连接主机"; 
	        String hostname=""; 
	public void init () { 
	setBackground(Color.white); 
	setLayout(new GridBagLayout()); 
	GridBagConstraints GBC = new GridBagConstraints(); 
	LblPrompt = new Label("没有连接主机"); 
	LblPrompt.setAlignment(Label.LEFT); 

	BtnConn = new Button("连接"); 
	BtnClose = new Button("断开"); 
	BtnClose.enable(false); 
	TxtUID = new TextField("",15); 
	TxtPWD = new TextField("",15); 
	TxtPWD.setEchoCharacter('*'); 
	TxtHost = new TextField("",20); 
	Label LblUID = new Label("User ID:"); 
	Label LblPWD = new Label("PWD:"); 
	Label LblHost = new Label("Host:"); 

	lsArea = new TextArea(30,80); 
	lsArea.setEditable(false); 

	GBC.gridwidth= GridBagConstraints.REMAINDER; 
	GBC.fill     = GridBagConstraints.HORIZONTAL; 
	((GridBagLayout)getLayout()).setConstraints(LblPrompt,GBC); 
	add(LblPrompt); 

	GBC.gridwidth=1; 
	((GridBagLayout)getLayout()).setConstraints(LblHost,GBC); 
	add(LblHost); 
	GBC.gridwidth=GridBagConstraints.REMAINDER; 
	((GridBagLayout)getLayout()).setConstraints(TxtHost,GBC); 
	add(TxtHost); 

	GBC.gridwidth=1; 
	((GridBagLayout)getLayout()).setConstraints(LblUID,GBC); 
	add(LblUID); 
	GBC.gridwidth=1; 
	((GridBagLayout)getLayout()).setConstraints(TxtUID,GBC); 
	add(TxtUID); 

	GBC.gridwidth=1; 
	((GridBagLayout)getLayout()).setConstraints(LblPWD,GBC); 
	add(LblPWD); 
	GBC.gridwidth=1; 
	((GridBagLayout)getLayout()).setConstraints(TxtPWD,GBC); 
	add(TxtPWD); 

	GBC.gridwidth=1; 
	GBC.weightx=2; 
	((GridBagLayout)getLayout()).setConstraints(BtnConn,GBC); 
	add(BtnConn); 
	GBC.gridwidth=GridBagConstraints.REMAINDER; 

	((GridBagLayout)getLayout()).setConstraints(BtnClose,GBC); 
	add(BtnClose); 

	GBC.gridwidth=GridBagConstraints.REMAINDER; 
	GBC.fill     = GridBagConstraints.HORIZONTAL; 
	((GridBagLayout)getLayout()).setConstraints(lsArea,GBC); 
	add(lsArea); 
	        } 

	public boolean connect(String hostname, String uid,String pwd) 
	{ 
	                this.hostname = hostname; 
	LblPrompt.setText("正在连接，请等待....."); 
	try{ 
	  aftp =new FtpClient(hostname); 
	  aftp.login(uid,pwd); 
	  aftp.binary(); 
	  showFileContents(); 
	} 
	catch(FtpLoginException e){ 
	a="无权限与主机:"+hostname+"连接!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	catch (IOException e){ 
	a="连接主机:"+hostname+"失败!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	catch(SecurityException e) 
	{ 
	a="无权限与主机:"+hostname+"连接!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	LblPrompt.setText("连接主机:"+hostname+"成功!"); 
	return true; 
	} 

	public void stop() 
	{ 
	try 
	{ 
	aftp.closeServer(); 
	} 
	catch(IOException e) 
	{ 
	} 
	} 

	public void paint(Graphics g){ 
	} 

	public boolean action(Event evt,Object obj) 
	{ 
	if (evt.target == BtnConn) 
	{ 
	LblPrompt.setText("正在连接，请等待....."); 
	if (connect(TxtHost.getText(),TxtUID.getText(),TxtPWD.getText())) 
	{ 
	BtnConn.setEnabled(false); 
	BtnClose.setEnabled(true); 
	} 
	return true; 
	} 
	if (evt.target == BtnClose) 
	{ 
	stop(); 
	BtnConn.enable(true); 
	BtnClose.enable(false); 
	LblPrompt.setText("与主机"+hostname+"连接已断开!"); 
	return true; 
	} 
	return super.action(evt,obj); 
	} 
	public boolean sendFile(String filepathname){ 
	boolean result=true; 
	if (aftp != null) 
	{ 
	LblPrompt.setText("正在粘贴文件,请耐心等待...."); 

	String  contentperline; 
	try{ 
	a="粘贴成功!"; 
	String fg =new  String("\\"); 
	int index = filepathname.lastIndexOf(fg); 
	String filename = filepathname.substring(index+1); 
	File localFile ; 
	localFile = new File(filepathname) ; 
	RandomAccessFile sendFile = new RandomAccessFile(filepathname,"r"); 
//	 
	sendFile.seek(0); 
	outs = aftp.put(filename); 
	outputs = new DataOutputStream(outs); 
	while (sendFile.getFilePointer() < sendFile.length() ) 
	{ 
	  ch = sendFile.read(); 
	  outputs.write(ch); 
	} 
	outs.close(); 
	sendFile.close(); 
	} 
	catch(IOException e){ 
	  a = "粘贴失败!"; 
	  result = false ; 

	} 
	LblPrompt.setText(a); 
	showFileContents(); 
	} 
	else{ 
	result = false; 
	} 
	return result; 
	} 

	public void showFileContents() 
	{ 
	StringBuffer buf = new StringBuffer(); 
	lsArea.setText(""); 
	try 
	{ 
	ins= aftp.list(); 
	while ((ch=ins.read())>=0){ 
	  buf.append((char)ch); 
	} 
	    lsArea.appendText(buf.toString()); 
	ins.close(); 
	        } 
	catch(IOException e) 
	{ 
	} 
	} 
	        public static void main(String args[]){ 
	             Frame f = new Frame("FTP Client"); 
	             f.addWindowListener(new WindowAdapter(){ 
	               public void windowClosing(WindowEvent e){ 
	                   System.exit(0); 
	               } 

	             }); 
	             FtpApplet ftp = new  FtpApplet(); 
	             ftp.init(); 
	             ftp.start(); 
	             f.add(ftp); 
	             f.pack(); 
	             f.setVisible(true); 
	        } 
}
