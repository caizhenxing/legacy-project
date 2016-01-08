/*
 * 创建日期 2004-12-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testInheritance extends testfather{
	public void aa()
	{
		super.aa();
		System.out.println("test Inheritance");
		
		
	}
	private testInheritance()
	{
	}
	public static testInheritance getin()
	{
		testInheritance t=new testInheritance();
		return t;
	}
	public static void main(String[] args) {
		testfather tf=testInheritance.getin();//new testInheritance();
		tf.aa();
	}
}
abstract class testfather
{
	public  void aa()
	{
		System.out.println("test father!");
	}
}