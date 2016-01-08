/*
 * �������� 2004-10-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
