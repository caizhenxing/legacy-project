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
