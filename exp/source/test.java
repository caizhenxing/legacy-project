import java.util.Properties;
import java.io.*;
/*
 * 创建日期 2004-9-8
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
