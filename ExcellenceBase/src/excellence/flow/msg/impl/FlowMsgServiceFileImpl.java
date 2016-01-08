package excellence.flow.msg.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import excellence.flow.msg.FlowMsgService;
import excellence.flow.msg.MsgBean;


public class FlowMsgServiceFileImpl implements FlowMsgService {

	private String path=null;
	private static int number=1;
	public FlowMsgServiceFileImpl()
	{
		path="d:/downloads/";
	}
	public List<MsgBean> receiveMsg(String dest, boolean del) {
		// TODO Auto-generated method stub
		List<MsgBean> l=new ArrayList<MsgBean>();
		String dir=path+dest;
		File file=new File(dir);
		File[] files=file.listFiles();
		if(files==null)
			return l;
		for(int i=0,size=files.length;i<size;i++)
		{
			File f=files[i];
			try {
				
				FileInputStream is=new FileInputStream(f);
				byte[] temp=new byte[is.available()];
				is.read(temp);
				String xml=new String(temp);
				is.close();
				if(del)
				f.delete();
				MsgBean mb=new MsgBean();
				mb.setDest(dest);
				mb.setMsg(xml);
				l.add(mb);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return l;
	}

	public void sendMsg(MsgBean mb) {
		// TODO Auto-generated method stub
		String dest=mb.getDest();
		String dir=path+dest;
		File file=new File(dir);
		if(!file.exists())
			file.mkdir();
		File file1=new File(dir+"/"+Integer.toString(number++)+".oo");
		try {
			byte[] a=mb.getMsg().getBytes();
			file1.createNewFile();
			FileOutputStream fos=new FileOutputStream(file1);
			fos.write(a);
			fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] arg0)
	{
		FlowMsgServiceFileImpl f=new FlowMsgServiceFileImpl();
		MsgBean mb=new MsgBean();
		mb.setDest("aa");
		mb.setMsg("aa");
		f.sendMsg(mb);
		List<MsgBean> l=f.receiveMsg("aaaa",false);
		for(int i=0,size=l.size();i<size;i++)
		{
			MsgBean mb1=l.get(i);
			System.out.println(mb1.getDest());
			System.out.println(mb1.getMsg());
		}
	}
}
