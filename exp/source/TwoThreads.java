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
public class TwoThreads {
	public volatile static int count=0;
	public static class Thread1 extends Thread {
			public void run() {
				for(;count<500;count++)
				System.out.print(" "+"t1-"+count+"");
				//System.out.println("A");
				
			}
		}

		public static class Thread2 extends Thread {
			public void run() {
				for(;count<500;count++)
				System.out.print(" "+"t2-"+count+"");
				//System.out.println("1");
				
			}
		}

		public static void main(String[] args) {
			new Thread1().start();
			new Thread2().start();
			
			
		}

}
