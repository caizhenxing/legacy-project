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
public class SimulatedCAS {
	private int value;
    
	public synchronized int getValue() { return value; }
    
	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		if (value == expectedValue) 
			value = newValue;
		return value;
	}
}
