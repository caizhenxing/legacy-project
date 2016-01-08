
package ocelot.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

/**
 * @author zhao yifei
 * @date 2006-08-16
 * 项目中的参数
 */
public class Constants {

	static  Properties p=new Properties();
	/**
	 * 返回关键字的值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key)
	{
		if(p.containsKey(key))
			return p.getProperty(key);
		else
			return null;
	}
	/**
	 * 读入路径下所有.xml\.property的文件
	 * @param path
	 */
	public static void setFilePath(String path)
	{
		File f=new File(path);
		
		try {
			if(f.isFile())
			{
				String type=f.getName().substring(f.getName().lastIndexOf("."), f.getName().length());
				if(type.equals("xml"))
					p.loadFromXML(new FileInputStream(f));
				else
					p.load(new FileInputStream(f));
			}
			else
			{
				File[] fs=f.listFiles();
				for(int i=0,size=fs.length;i<size;i++)
				{
					File ft=fs[i];
					
					if(ft.isFile())
					{
						String type=ft.getName().substring(ft.getName().lastIndexOf(".")+1, ft.getName().length());
						if(type.equals("xml"))
							p.loadFromXML(new FileInputStream(ft));
						else
							p.load(new FileInputStream(ft));
					}
				}
			}
			
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg0)
	{
		Constants.setFilePath("D:/newworkplace/ETOA/src/et/config/parameter");
		System.out.println(Constants.getProperty("email_upload_in_subpath"));
		System.out.println(Constants.getProperty("employee_photo_webpath"));
	}
}
