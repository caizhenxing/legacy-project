/*
 * �������� 2004-12-27
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testDouble {

	/**
	 * 
	 */
	public testDouble() {
		super();
		// TODO �Զ����ɹ��캯�����
	}
	Double d=new Double(2);
	public Double getD()
	{
		return d;
	}
	public static void main(String[] args) {
		//Double d=new Double(2);
		testDouble td=new testDouble();
		double d=td.getD().doubleValue();
		System.out.println(td.getD().doubleValue());
		System.out.println(Math.atan(d));
	}
}
