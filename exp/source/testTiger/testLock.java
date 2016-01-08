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
public class testLock extends Thread{
	int count;
	public testLock(int i)
	{
		this.count=i;
	}
	public void run()
	{
		long st=System.currentTimeMillis();
		System.out.print("thread: ");
		
		lock l=new lock();

		l.setC(count);
		try
		{
			l.wait(1000);
		}catch(InterruptedException ie)
		{
			
		}finally
		{
		
		l.setB(count+10);
		System.out.print("  thread time:  ");
		System.out.println(System.currentTimeMillis()-st);
		}
	}
	public static void main(String[] args) {
		long st=System.currentTimeMillis();
		testLock t1=new testLock(1);
		testLock t2=new testLock(2);
		testLock t3=new testLock(3);
		testLock t4=new testLock(4);
		testLock t5=new testLock(5);
		testLock t6=new testLock(6);
		testLock t7=new testLock(7);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		System.out.print("total time:");
		System.out.println(System.currentTimeMillis()-st);
	}
}
