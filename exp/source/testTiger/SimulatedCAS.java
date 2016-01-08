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
public class SimulatedCAS {
	private int value;
    
	public synchronized int getValue() { return value; }
    
	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		if (value == expectedValue) 
			value = newValue;
		return value;
	}
}
