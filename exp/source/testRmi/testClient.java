/*
 * 创建日期 2005-1-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testRmi;

/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.rmi.*;
public class testClient {

	public static void main(String[] args) 
	{

		try
		{
			String[] bindings=Naming.list("");
			for(int i=0;i<bindings.length;i++)
			{
				System.out.println(bindings[i]);
				Test c1=(Test)Naming.lookup(bindings[i]);
				System.out.println(c1.getD());
			}
			
		}catch(Exception re)
		{
			re.printStackTrace();
		}
	}
}
