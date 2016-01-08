/*
 * 创建日期 2004-12-27
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
public class testDouble {

	/**
	 * 
	 */
	public testDouble() {
		super();
		// TODO 自动生成构造函数存根
	}
	Double d=new Double(2);
	public Double getD()
	{
		return d;
	}
	public static void main(String[] args) {
		//Double d=new Double(2);
		testDouble td=new testDouble();
		double d=td.getD().doubleValue();
		System.out.println(td.getD().doubleValue());
		System.out.println(Math.atan(d));
	}
}
