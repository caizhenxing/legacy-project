import java.util.Properties;
import java.io.*;
/*
 * �������� 2004-9-8
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

public class test {

	public static void main(String[] arg0)
	{
		Properties p=new Properties();
		String path="e:/file/ccb.txt";
		File f=new File(path);
		try
		{
			FileInputStream fi=new FileInputStream(f);
			p.load(fi);
			System.out.println(p.getProperty("1"));
			System.out.println(p.getProperty("2"));
			System.out.println(p.getProperty("3"));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
