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
public class testServer {

	public static void main(String[] args) {
		try
		{
			System.out.println("constructing");
			ProductImpl p1=new ProductImpl("ss");
			ProductImpl p2=new ProductImpl("aa");
			System.out.println("Binding server");
			Naming.rebind("p1",p1);
			Naming.rebind("p2",p2);
			System.out.println("ok!");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
