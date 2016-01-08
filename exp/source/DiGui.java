/*
 * 创建日期 2004-11-1
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.io.*;

class DiGui 
{
 static void getDir(String strPath) throws Exception
 {
  try
  {
   File f=new File(strPath);
   if(f.isDirectory())
   {
	File[] fList=f.listFiles();
	for(int j=0;j<fList.length;j++)
	{
	 if(fList[j].isDirectory())
	 {
	  System.out.println(fList[j].getPath());
	  getDir(fList[j].getPath()); //在getDir函数里面又调用了getDir函数本身       
	 }
	}
	for(int j=0;j<fList.length;j++)
	{

	 if(fList[j].isFile())
	 {
	  System.out.println(fList[j].getPath());
	 }

	}
   }
  }
  catch(Exception e)
  {
   System.out.println("Error： " + e);
  }
 
 } 
 
 public static void main(String[] args) 
 {
  String strPath="D:\\eclipse\\workspace\\ccb_dms";
  System.out.println(strPath);

  try
  {
   getDir(strPath);
  }
  catch(Exception e)
  {
  
  }
 }
}



