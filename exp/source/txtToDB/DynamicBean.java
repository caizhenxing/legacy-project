/*
 * �������� 2005-1-19
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package txtToDB;

import java.util.*;
/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class DynamicBean {
	private HashMap hm=new HashMap();
	
	public Object getVar(Object k)
	{
		if(!hm.containsKey(k))
		return null;
		return hm.get(k);
	}
	public Set getMember()
	{
		return hm.keySet();
	}
	public int getMemberSize()
	{
		return hm.size();
	}
	public void setValue(Object k,Object v)throws Exception
	{
		if(hm.containsKey(k))
		{
			hm.remove(k);
			hm.put(k,v);
		}
		else throw new Exception();
		//hm.put();
	}
	public static void main(String[] args) {
	}
}
