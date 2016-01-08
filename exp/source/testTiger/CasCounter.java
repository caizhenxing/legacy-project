/*
 * 创建日期 2004-12-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testTiger;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CasCounter {
	private SimulatedCAS value;

	public int getValue() {
		return value.getValue();
	}
	public CasCounter()
	{
		value=new SimulatedCAS();
	}
	public int increment() {
		int oldValue = value.getValue();
		while (value.compareAndSwap(oldValue, oldValue + 1) != oldValue)
			oldValue = value.getValue();
		return oldValue + 1;
	}
	public static void main(String args[]) {
		CasCounter cc=new CasCounter();
		System.out.println(cc.getValue());
		System.out.println(cc.increment());
	}
}

