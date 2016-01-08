/*
 * 创建日期 2004-12-1
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
public class Test2 extends Test1 {
	{
		System.out.println("1");
	}
	
	Test2() {
		System.out.println("--2--");
		//System.out.print(Math.round(11.5));
		
	}
	public int a(String s)
	{
		s="1111";
		System.out.println(s);
		System.out.println(s.hashCode());
		return s.hashCode();
	}

	static {
		System.out.println("3");
	}

	{
		System.out.println("4--");
	}

	public static void main(String[] args) {
		/*short a=1;a+=1;
		//a=(short) (a+1);
		String s=new String("2222");
		System.out.println();
		System.out.println(s.hashCode());
		int i=new Test2().a(s);
		System.out.println(i);
		System.out.println(s);*/
		
	}
}
class Test1 {
	{
		System.out.println("7");
	}

	Test1() {
		System.out.println("5");
	}

	static {
		System.out.println("6");
	}
	

}


