/*
 * �������� 2004-12-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testTiger;

//import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
