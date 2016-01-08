/*
 * �������� 2004-12-21
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package jc.interface1;

import java.io.*;
import java.util.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class StreamToken
{
	StreamTokenizer st;
	Vector v=new Vector();
//	public StreamToken(InputStream is)
//	{
//		st=new StreamTokenizer(is);
//	}
	public StreamToken(Reader r)
	{
		st=new StreamTokenizer(r);
	}
	public Vector analyseStream(char[] token)
	{
		for(int i=0;i<token.length;i++)
		{
			st.ordinaryChar(token[i]);
		}
		this.analyse();
		return v;
	}
	private void analyse()
	{
		try {
			  while(st.nextToken() !=
				StreamTokenizer.TT_EOF) {
				String s;
				switch(st.ttype) 
				{
				  case StreamTokenizer.TT_EOL:
					s = new String("EOL");
					break;
				  case StreamTokenizer.TT_NUMBER:
					s = Double.toString(st.nval);
					break;
				  case StreamTokenizer.TT_WORD:
					s = st.sval; // Already a String
					break;
				  default: // single character in ttype
					s = String.valueOf((char)st.ttype);
				}
				
				v.add(s);
				System.out.println(s);
			  }
			  System.out.println(v.size());
			} catch(IOException e) {
			  System.err.println(
				"st.nextToken() unsuccessful");
			}
	}
}
