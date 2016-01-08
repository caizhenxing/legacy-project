/*
 * 创建日期 2004-12-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testTiger;

//import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class lock {
	int critical;
	int b;
	Lock lock=new ReentrantLock();
	public void setC(int c)
	{
		
		lock.lock();
		this.critical=c;
		lock.unlock();
		System.out.print(critical);

	}
	public void setB(int B)
	{
		
		lock.lock();
		this.b=B;
		lock.unlock();
		System.out.print(b);

	}
}
