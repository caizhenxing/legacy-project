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
	public String a="û����������"; 
	        String hostname=""; 
	public void init () { 
	setBackground(Color.white); 
	setLayout(new GridBagLayout()); 
	GridBagConstraints GBC = new GridBagConstraints(); 
	LblPrompt = new Label("û����������"); 
	LblPrompt.setAlignment(Label.LEFT); 

	BtnConn = new Button("����"); 
	BtnClose = new Button("�Ͽ�"); 
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
	LblPrompt.setText("�������ӣ���ȴ�....."); 
	try{ 
	  aftp =new FtpClient(hostname); 
	  aftp.login(uid,pwd); 
	  aftp.binary(); 
	  showFileContents(); 
	} 
	catch(FtpLoginException e){ 
	a="��Ȩ��������:"+hostname+"����!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	catch (IOException e){ 
	a="��������:"+hostname+"ʧ��!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	catch(SecurityException e) 
	{ 
	a="��Ȩ��������:"+hostname+"����!"; 
	LblPrompt.setText(a); 
	return false; 
	} 
	LblPrompt.setText("��������:"+hostname+"�ɹ�!"); 
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
	LblPrompt.setText("�������ӣ���ȴ�....."); 
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
	LblPrompt.setText("������"+hostname+"�����ѶϿ�!"); 
	return true; 
	} 
	return super.action(evt,obj); 
	} 
	public boolean sendFile(String filepathname){ 
	boolean result=true; 
	if (aftp != null) 
	{ 
	LblPrompt.setText("����ճ���ļ�,�����ĵȴ�...."); 

	String  contentperline; 
	try{ 
	a="ճ���ɹ�!"; 
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
	  a = "ճ��ʧ��!"; 
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
