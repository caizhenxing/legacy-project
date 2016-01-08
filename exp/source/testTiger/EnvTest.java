/*
 * 创建日期 2004-12-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testTiger;
import java.lang.ProcessBuilder;
import java.io.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class EnvTest {
  public static void main(String args[]) {
  	
	System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
	try
	{
	
	String[] s={"java -help"};
	Process p =new ProcessBuilder(s).start();
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}catch(Exception e)
	{
	}
  }
}

