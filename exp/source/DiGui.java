/*
 * �������� 2004-11-1
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
	  getDir(fList[j].getPath()); //��getDir���������ֵ�����getDir��������       
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
   System.out.println("Error�� " + e);
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



