/*
 * �������� 2004-12-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testTiger;


import java.util.*;

import org.omg.PortableInterceptor.Current;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testForIn
{
	public static void main(String[] args)
	{

		Queue queue = new LinkedList();
		queue.offer("One");
		queue.offer("Two");
		queue.offer("Three");
		queue.offer("Four");
		//Concurrent c=new Current();
		System.out.println("Head of queue is: " + queue.contains("One"));

	

	}

}

