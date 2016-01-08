/**
 * 	@(#)TestSpringContainer.java   2006-12-4 ÏÂÎç01:49:27
 *	 ¡£ 
 *	 
 */
package test.client;

import java.io.File;

import excellence.framework.base.container.SpringContainer;

 /**
 * @author ddddd
 * @version 2006-12-4
 * @see
 */
public class TestSpringContainer {

	private static String configPath="/et/config/spring/";
	private static String configFile="/et/config/spring/applicationContext.xml";
	
	/**
	 * @param
	 * @version 2006-12-4
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = SpringContainer.class.getResource(configFile).getPath();
		path = path.substring(1, path.length());

		// for linux or unix
		if (!path.substring(0, 1).equals("/")
				&& !path.substring(1, 2).equals(":")) {

			path = "/" + path;
		}

		File file = new File(path);
		File dir = file.getParentFile();
		String[] files = dir.list();

		for (int i = 0, n = files.length; i < n; i++) {
			String filea=files[i];
			String type=filea.substring(filea.lastIndexOf(".")+1);
			if(!type.toLowerCase().equals("xml"))
				continue;
			files[i] = configPath + files[i];
			System.out.println(files[i].toString());
		}
	}

}
