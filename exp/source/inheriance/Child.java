/*
 * �������� 2004-12-27
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package inheriance;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class Child implements Mother {

	/* ���� Javadoc��
	 * @see inheriance.Mother#getS()
	 */
	public String getS() {
		// TODO �Զ����ɷ������
		return "aaaaa";
	}
	public static Mother getMother()
	{
		return new Child();
	}
	public static void main(String[] args) {
		Mother m=Child.getMother();
		System.out.print(m.getS());
	}
}
