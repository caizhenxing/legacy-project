/*
 * �������� 2004-10-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package base;
import java.util.*;
import java.io.*;


/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
		v=o.findCode("6-7-9¥.tsv");
		Iterator i=v.iterator();
		while(i.hasNext())
		System.out.println(i.next().toString());
	}
}
