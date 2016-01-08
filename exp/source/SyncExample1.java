/*
 * �������� 2004-10-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.util.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
//Java �����ϲ���һ�ֻ�����ʽ��ÿ��ֻ��һ���߳̿��Գ������������ڱ������������������������ס��������ݱ����˴���飬�����Ǵ���鱾����һ�����Ҫ��һ�������Ա����������򷽷��� 
//
//��֮��������Ϊ�������������������ʾ�����̲߳���ͬʱִ�иô���顣��ֻ��ʾ��������߳����ڵȴ���ͬ�����������ǲ���ͬʱִ�иô��롣 
//
//������ʾ���У������߳̿���ͬʱ�������Ƶ�ִ�� setLastAccess() �е� synchronized �飬��Ϊÿ���߳���һ����ͬ�� thingie ֵ����ˣ�synchronized ������ܵ���������ִ�е��߳��в�ͬ���ı���

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
