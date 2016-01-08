package et.bo.sys.db.impl;

import java.io.IOException;
import java.util.Date;

import et.bo.sys.db.DBBak;

public class DBBakImpl implements DBBak {

	private String userid="ETforum";
	private String password="ETforum";
	private String sid="orcl";
	private String owner="ETforum";
	private String filepath="f:\\数据库备份\\";
	public void dayExecute() {
		// TODO Auto-generated method stub
		this.week();
	}

	public void monthExecute() {
		// TODO Auto-generated method stub
		this.month();
	}

	public void weekExecute() {
		// TODO Auto-generated method stub

	}

	public void yearExecute() {
		// TODO Auto-generated method stub

	}
	private void week()
	{
		String bak="exp "+userid+"/"+password+"@"+sid+" owner="+owner+"  buffer=65536 file=";
		StringBuilder cmd=new StringBuilder();
		cmd.append(bak);
		cmd.append(this.filepath);
		//Integer.toString(this.day)
		cmd.append("w");
		cmd.append(Integer.toString(new Date().getMinutes()));
		try
		{
			
		Process process = Runtime.getRuntime().exec(cmd.toString());
		}catch(IOException ioe)
		{

		}
	}	
	private void month()
	{
		String bak="exp "+userid+"/"+password+"@"+sid+" owner="+owner+"  buffer=65536 file=";
		StringBuffer cmd=new StringBuffer();
		cmd.append(bak);
		cmd.append(this.filepath);
		//Integer.toString(this.day)
		cmd.append("m");
		cmd.append(Integer.toString(new Date().getMonth()));
		
		try
		{
		
		Process process = Runtime.getRuntime().exec(cmd.toString());
		}catch(IOException ioe)
		{

		}
	}
	public static void main(String[] arg0)
	{
		DBBakImpl db=new DBBakImpl();
		db.dayExecute();
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
