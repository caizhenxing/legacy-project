/*
 * 创建日期 2004-10-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package base;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.util.Vector;




public class StringToken
{
  public Vector vec=new Vector();
  int i=0;
  //int length=0;

  public  StringToken(String source,String tok)
  {
	String s=source;
	int len=s.length();
	char to=(new String(tok)).charAt(0);
	char[] cRow=s.toCharArray();
	int p=0;
	for (int i=0;i<len;i++)
	{ //p指针
	  if (cRow[i]==to)
	  {
		String s1=new String(cRow,p,i-p);
		vec.addElement(s1);
		p=i+1;//next
		if (i==len-1){break;}
	  }

	}
	//length=vec.size();
  }


  public boolean hasMoreTokens()
  {
	if(i==vec.size())
	  return false;
	return true;
  }

  public String nextElement()
  {
	String temp=vec.get(i).toString();
	i++;
	return temp;

  }

}