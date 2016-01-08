/*
 * �������� 2004-12-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testTiger;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
