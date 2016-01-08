/*
 * 创建日期 2004-10-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
//Java 锁定合并了一种互斥形式。每次只有一个线程可以持有锁。锁用于保护代码块或整个方法，必须记住是锁的身份保护了代码块，而不是代码块本身，这一点很重要。一个锁可以保护许多代码块或方法。 
//
//反之，仅仅因为代码块由锁保护并不表示两个线程不能同时执行该代码块。它只表示如果两个线程正在等待相同的锁，则它们不能同时执行该代码。 
//
//在以下示例中，两个线程可以同时不受限制地执行 setLastAccess() 中的 synchronized 块，因为每个线程有一个不同的 thingie 值。因此，synchronized 代码块受到两个正在执行的线程中不同锁的保护

public class SyncExample1 {
	public static class Thingie {

		private Date lastAccess;

		public synchronized void setLastAccess(Date date) {
		  this.lastAccess = date;
		}
	  }

	  public static class MyThread extends Thread { 
		private Thingie thingie;

		public MyThread(Thingie thingie) {
		  this.thingie = thingie;
		}

		public void run() {
		  thingie.setLastAccess(new Date());
		}
	  }

	public static void main(String[] args)  { 
		Thingie thingie1 = new Thingie(), 
		  thingie2 = new Thingie();

		new MyThread(thingie1).start();
		new MyThread(thingie2).start();
	  }

}
