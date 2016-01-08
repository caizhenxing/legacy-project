/*
 * 创建日期 2005-1-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package txtToDB;

import java.util.*;
import java.io.*;
import org.apache.log4j.*;
/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class Parser {
	
	private String token;
	private ArrayList temp;
	private ArrayList result;
	static Logger l = Logger.getLogger(Parser.class);
	static int defaultLine=80;
	public Parser(String t)
	{
		PropertyConfigurator.configure("e:\\test.properties");
		temp=new ArrayList();
		result=new ArrayList();
		token=new String(t);
		//Collections.addAll(token,t);
		
	}
	public void readFile(Reader r)
	{
		this.readFile(r,0,defaultLine);
	}
	public void readFile(Reader re ,int beginLine,int Linesize)
	{
		BufferedReader r=new BufferedReader(re);
		try
		{
		int i=0;
		r.reset();
		while(beginLine--!=0)
		{
			r.readLine();
		}
		while(i<Linesize)
		{
			String s=r.readLine();
			if(s==null) break;
			temp.add(s);
			i++;
		}
		
		}catch(IOException e)
		{
			e.printStackTrace();
			l.error(e.toString());
		}
		
	}
	
	public List getResult()
	{
		return this.getResult(this.result.size());
	}
	public List getResult(int size)
	{
		return this.result.subList(0,size);
	}
	public void parseStream()
	{
		ArrayList onebean=new ArrayList();
		Iterator it=this.temp.iterator();
		while(it.hasNext())
		{
			DynamicBean db=new DynamicBean();
			String s=(String)it.next();
			StringTokenizer str=new StringTokenizer(s,this.token);
			while(str.hasMoreTokens())
			onebean.add(str.nextElement());
			
		}
		
		
		
	}
	public static void main(String[] args) {
	}
}
