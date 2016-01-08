package et.test;

import java.util.Calendar;

public class TestThread {
	public void runThread()
	{
		Thread t = new Thread(new RunnableImpl());
		t.start();
		System.out.println(".............");
	}
	public void runThread2()
	{
		boolean b = true;
		while(b)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(":;;;;;;;;;;;;;;;;;;;;;;;");
	}
	private class RunnableImpl implements Runnable
	{
		public void run()
		{
			long d = new java.util.Date().getTime();
			while(true)
			{
				if((new java.util.Date().getTime()-d)>10000)
				{
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args)
	{
		//TestThread tt = new TestThread();
		//tt.runThread2();
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.SECOND));
	}
}
