/*
 * �������� 2004-12-27
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

import java.util.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testMemory {

	/**
	 * 
	 */
	public testMemory() {
		super();
		// TODO �Զ����ɹ��캯�����
	}

	public static void main(String[] args) {
		HashMap hm=new HashMap(1000000);
		try
		{
		
		for(long i=0;i<422741;i++)
		hm.put(Long.toString(i),Long.toBinaryString(i));
		}catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			System.out.println(hm.size());
		}
		
	}
}
