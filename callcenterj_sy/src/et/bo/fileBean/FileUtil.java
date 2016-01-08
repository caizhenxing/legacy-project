/**
 * 	@(#)FileUtil.java   2006-9-12 下午02:49:09
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.fileBean;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


 /**
 * @author zhaoyifei
 * @version 2006-9-12
 * @see
 */
public class FileUtil{
	private static Log log = LogFactory.getLog(FileUtil.class);
	public long newFile(String path,String name,InputStream is)
	{
		long size=0;
		StringBuffer sb=new StringBuffer();
		sb.append(path);
		if(!path.endsWith("/"))
			sb.append("/");
		sb.append(name);
		File f=new File(sb.toString());
		if(f.exists())
			return -1;
		try {
			if(!f.createNewFile())
				return -1;
			OutputStream os=new FileOutputStream(f);
			byte[] buffer=new byte[1024*10];
			int len=is.available();
			
			while(0<len)
			{
				int count=is.read(buffer);
				os.write(buffer);
				size+=count;
				len=is.available();
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
	public long readFile(String pathname,OutputStream os)
	{
		long size=0;
		File sf=new File(pathname);
		if(!sf.exists())
			return -1;
		try {
			InputStream is=new FileInputStream(sf);
			byte[] buffer=new byte[1024*10];
			int len=is.available();
			
			while(0<len)
			{
				int count=is.read(buffer);
				os.write(buffer);
				size+=count;
				len=is.available();
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}
	public long copy(String source,String target)
	{
		long size=0;
		File sf=new File(source);
		File tf=new File(target);
		if(!sf.exists())
			return -1;
		if(tf.exists())
			return -1;		
			
		try {
			tf.createNewFile();
			InputStream is=new FileInputStream(sf);
			OutputStream os=new FileOutputStream(tf);
			byte[] buffer=new byte[1024*10];
			int len=is.available();
			
			while(0<len)
			{
				int count=is.read(buffer);
				os.write(buffer);
				size+=count;
				len=is.available();
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return -1;
		}
		 
		return size;
	}
	public long removeFile(String path)
	{
		long size=0;
	
		if(path.endsWith("/"))
			path=path.substring(0,path.length()-1);
				
		File sor=new File(path);
		if(!sor.exists())
			return 0;
		if(sor.isFile())
		if(sor.delete())
				return 1;
		if(sor.isDirectory())
		{
			Stack<File> q=new Stack<File>();
			
			q.push(sor);
			while(!q.isEmpty())
			{
				File temp=q.peek();
				
				
				if(temp.isFile())
				{
					temp.delete();
					size++;
					q.pop();
					continue;
				}
				File[] files=temp.listFiles();
				if(files.length==0)
				{
					temp.delete();
					q.pop();
					size++;
					continue;
				}
				for(int i=0,size1=files.length;i<size1;i++)
				{
					q.push(files[i]);
				}
			}
			sor.delete();
		}
		
		return size;
	}
	public List<FileBean> getFolders(String path)
	{
		List<FileBean> folders=new ArrayList<FileBean>();
		Queue<File> q=new LinkedList<File>();
		File root=new File(path);
		q.add(root);
		while(!q.isEmpty())
		{
			File temp=q.peek();
			q.poll();
			FileBean fb=new FileBean();
			fb.setId(temp.getPath());
			fb.setFolder(temp.isDirectory());
			fb.setName(temp.getName());
			fb.setParentId(temp.getParentFile().getPath());
			folders.add(fb);
			if(temp.isFile())
				continue;
			File[] files=temp.listFiles();
			for(int i=0,size=files.length;i<size;i++)
			{
				q.add(files[i]);
			}
		}
		return folders;
	}
	public void writeToFile(String fileName,String s,boolean append){
		try{
			FileOutputStream fos=new FileOutputStream(fileName,append);		
			Writer out
			   = new BufferedWriter(new OutputStreamWriter(fos));
			out.write(s);
			out.flush();
			out.close();
		}catch (FileNotFoundException e) {
            log.error("Could not find requested file on the server.");
        } catch (IOException e) {
            log.error("Error handling a client: " + e);
        }
	}
	public static void main(String[] arg0)
	{
		FileUtil fu=new FileUtil();
		List<FileBean> l=fu.getFolders("d:\\voicemail\\");
		Iterator<FileBean> i=l.iterator();
		while(i.hasNext())
		{
			System.out.println(i.next().getId());
		}
		/*File f=new File("E:/大城小爱.mp3");
		
		try {
			f.createNewFile();
			OutputStream os=new FileOutputStream(f);
			FileUtil fu=new FileUtil();
			System.out.println(fu.readFile("e:/mu/大城小爱.mp3", os));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		FileUtil fu=new FileUtil();
//		//fu.copy("e:/mu/一千零一夜.mp3", "e:/一千零一夜.mp3");
//		fu.removeFile("e:/aa/");
		//FileUtil fu=new FileUtil();
		//fu.writeToFile("c:/forTest.txt", "hello world", true);		
	}
}
