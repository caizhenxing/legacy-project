package et.test.test;

//Server
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

class Server extends JFrame implements  ActionListener,Runnable
{
	JPanel p=new JPanel();
	JTextArea jta=new JTextArea();
	JScrollPane jp=new JScrollPane(jta);
	JTextField jtf=new JTextField(20);
	JButton b=new JButton("send");
	ServerSocket server=null;
	Socket s=null;
	BufferedWriter bw=null;
	BufferedReader br=null;
	Thread t=new Thread(this);
	Server()
	{
		this.MyFrame();
		this.MyServer();
		
	}
	void MyFrame()
	{
		b.addActionListener(this);
		p.add(jtf);
		p.add(b);
		this.add(jta);
		this.add(p,BorderLayout.SOUTH);
		this.setTitle("聊天程序服务器端  端口号：1234");
		this.setBounds(120,120,400,400);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			bw.write(jtf.getText()+"\n");
			bw.flush();
			jtf.setText("");
		}
		catch(Exception et)
		{
			et.printStackTrace();
		}
	}
	void MyServer()
	{
		try
		{
			server=new ServerSocket(1234);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			t.start();	
		}
		catch(Exception et)
		{
			et.printStackTrace();
		}
	}
	public void run()
	{
		while(true)
		{
			try
			{
				jta.append("客户端说："+br.readLine()+"\n");
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
	
	}
	public static void main(String[] args)
	{
		new Server();
	}
} 

