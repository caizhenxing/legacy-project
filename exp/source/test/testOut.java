/*
 * �������� 2005-4-20
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
public class testOut {

	public static void main(String[] args) {
		char[] a={
			'1',
			'2',
			'3',
			'4'
		};
		List l=new ArrayList();
		l.add(a);
		System.out.println(l.toArray());
		System.out.println(a);
	}
}
