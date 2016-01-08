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
public class CasCounter {
	private SimulatedCAS value;

	public int getValue() {
		return value.getValue();
	}
	public CasCounter()
	{
		value=new SimulatedCAS();
	}
	public int increment() {
		int oldValue = value.getValue();
		while (value.compareAndSwap(oldValue, oldValue + 1) != oldValue)
			oldValue = value.getValue();
		return oldValue + 1;
	}
	public static void main(String args[]) {
		CasCounter cc=new CasCounter();
		System.out.println(cc.getValue());
		System.out.println(cc.increment());
	}
}

