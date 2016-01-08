/*
 * 创建日期 2004-12-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package inheriance;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class Child implements Mother {

	/* （非 Javadoc）
	 * @see inheriance.Mother#getS()
	 */
	public String getS() {
		// TODO 自动生成方法存根
		return "aaaaa";
	}
	public static Mother getMother()
	{
		return new Child();
	}
	public static void main(String[] args) {
		Mother m=Child.getMother();
		System.out.print(m.getS());
	}
}
