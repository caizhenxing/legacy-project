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
/**
 * Creates ten threads to search for the maximum value of a large matrix.
 * Each thread searches one portion of the matrix.
 */

public class TenThreads extends Thread{
	private static class WorkerThread extends Thread {
			int max = Integer.MIN_VALUE;
			int[] ourArray;

			public WorkerThread(int[] ourArray) {
				this.ourArray = ourArray;
			}

			// Find the maximum value in our particular piece of the array
			public void run() {
				for (int i = 0; i < ourArray.length; i++) 
					max = Math.max(max, ourArray[i]);                
			}

			public int getMax() {
				return max;
			}
		}

		public static void main(String[] args) {
			WorkerThread[] threads = new WorkerThread[10];
			int[][] bigMatrix = getBigHairyMatrix();
			int max = Integer.MIN_VALUE;
        
			// Give each thread a slice of the matrix to work with
			for (int i=0; i < 10; i++) {
				threads[i] = new WorkerThread(bigMatrix[i]);
				threads[i].start();
			}

			// Wait for each thread to finish
			try {
				for (int i=0; i < 10; i++) {
					threads[i].join();
					max = Math.max(max, threads[i].getMax());
				}
			}
			catch (InterruptedException e) {
				// fall through
			}

			System.out.println("Maximum value was " + max);
		}

}
