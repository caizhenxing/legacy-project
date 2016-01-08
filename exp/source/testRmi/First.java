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
import java.rmi.server.*;
public class First {

	public static void main(String[] args) {
		try
		{
			String[] bindings=Naming.list("");
			for(int i=0;i<bindings.length;i++)
			{
				System.out.println(bindings[i]);
				
			}
		}catch(Exception s)
		{
			s.printStackTrace();
		}
	}
}
