/*
 * 创建日期 2004-10-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package base;
import java.util.*;
import java.io.*;


/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class operate {
	String fileName=new String();
	
	String filePath="e:/zhaoyifei/";	public	Vector findCode(String jzqbh)
		{
			Vector code=new Vector();
			File file=new File(filePath+jzqbh);
			byte[] a=new byte[8092];
			try
			{
				FileInputStream fs=new FileInputStream(file);
				fs.read(a);
			}catch(FileNotFoundException fne)
			{
				
			}catch(IOException ioe)
			{
				
			}
			String source=new String(a);
			StringToken st= new StringToken(source,"\r\n");
			while( st.hasMoreTokens())
			  {
				String sTemp=(String)st.nextElement();
				code.add(sTemp);
			  }
			return code;
		}
	public static void main(String[] arg0)
	{
		operate o=new operate();
		Vector v=new Vector();
		v=o.findCode("6-7-9楼.tsv");
		Iterator i=v.iterator();
		while(i.hasNext())
		System.out.println(i.next().toString());
	}
}
