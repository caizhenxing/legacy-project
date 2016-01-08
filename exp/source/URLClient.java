/*
 * 创建日期 2004-10-21
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
import java.net.*;


public class URLClient {

	protected URLConnection connection;


	public String getDocumentAt(String urlString) {
		StringBuffer document = new StringBuffer();
			try {
				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = null;
				while ((line = reader.readLine()) != null)
				  document.append(line + "\n");
				reader.close();
			} catch (MalformedURLException e) {
				System.out.println("Unable to connect to URL: " + urlString);
			} catch (IOException e) {
				System.out.println("IOException when connecting to URL: " + urlString);
			}
			return document.toString();

	}
	public static void main(String[] args) {
		URLClient client = new URLClient();
		String yahoo = client.getDocumentAt("http://www.yahoo.com");
		System.out.println(yahoo);
	}

}
