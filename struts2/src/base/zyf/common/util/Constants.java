package base.zyf.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * @author zhao yifei
 * @date 2009-05-21 项目中的参数
 */
public class Constants {

	private static final String classpath = "/conf/parameter";
	
	
	
	private static Properties sysParameter = null;

	/**
	 * 返回关键字的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if(sysParameter == null)
		{
			init();
		}
		if (sysParameter.containsKey(key))
			return sysParameter.getProperty(key);
		else
			return null;
	}

	/**
	 * 读入路径下所有.xml\.property的文件
	 * 
	 * @param path
	 */
	public static void init() {
		sysParameter = new Properties();
		String path = Constants.class.getResource(classpath).getPath();
		path = path.substring(1, path.length());

		// for linux or unix
		if (!path.substring(0, 1).equals("/")
				&& !path.substring(1, 2).equals(":")) {

			path = "/" + path;
		}

		File f = new File(path);

		try {
			if (f.isFile()) {
				String type = f.getName().substring(
						f.getName().lastIndexOf("."), f.getName().length());
				if (type.equals("xml"))
					sysParameter.loadFromXML(new FileInputStream(f));
				else
					sysParameter.load(new FileInputStream(f));
			} else {
				File[] fs = f.listFiles();
				for (int i = 0, size = fs.length; i < size; i++) {
					File ft = fs[i];

					if (ft.isFile()) {
						String type = ft.getName().substring(
								ft.getName().lastIndexOf(".") + 1,
								ft.getName().length());
						if (type.equals("xml"))
							sysParameter.loadFromXML(new FileInputStream(ft));
						else
							sysParameter.load(new FileInputStream(ft));
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

	public static void main(String[] arg0) {
		Constants.init();
		System.out.println(Constants.getProperty("email_upload_in_subpath"));
		System.out.println(Constants.getProperty("employee_photo_webpath"));
	}

	/**
	 * @return the sysParameter
	 */
	public static Properties getSysParameter() {
		if(sysParameter == null)
		{
			init();
		}
		return sysParameter;
	}

}
