/*
 * �������� 2004-12-24
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package jc.xtgl;

import java.util.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class TimeTools {

	/**
	 * 
	 */
	public TimeTools() {
		super();
		// TODO �Զ����ɹ��캯�����
	}
	public static boolean between(int begin, int end, int date)
	{
		if(begin<0||end<0||date<0)
		return false;
		if(begin>31||end>31||date>31)
		return false;
		if(end<begin)
		{
			return (date<end);
		}
		return(date>=begin&&date<end);
	}
	public static void main(String[] args) {
		TimeTools tt=new TimeTools();
		System.out.print(tt.between(12,12,12));
	}
}
