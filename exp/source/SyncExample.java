/*
 * 创建日期 2004-10-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SyncExample {
	private static Object lockObject=new Object();
	private static int x;
	private static int y;
	  private static class Thread1 extends Thread { 
		public void run() { 
		  synchronized (lockObject) {
			x = y = 0;
			System.out.println(x);
		  }
		}
	  }

	  private static class Thread2 extends Thread { 
		public void run() { 
		  synchronized (lockObject) {
			x = y = 1;
			System.out.println(y);
		  }
		}
	  }

	  public static void main(String[] args) {
		new Thread1().run();
		new Thread2().run();
	  }

}
