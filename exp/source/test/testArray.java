/*
 * �������� 2004-12-23
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Arrays;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testArray {

	/**
	 * 
	 */
	public testArray() {
		super();
		// TODO �Զ����ɹ��캯�����
	}
	
	public static void main(String[] args) {
		Vector v=new Vector();
		for(int i=0;i<100;i++)
		v.add(new String(Integer.toString(i)));
		Arrays.sort(v.toArray());
		Iterator i=v.iterator();
		while(i.hasNext())
		System.out.println(i.next().toString());
	}
}
