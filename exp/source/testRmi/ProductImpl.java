/*
 * �������� 2005-1-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testRmi;

/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.rmi.*;
import java.rmi.server.*;
public class ProductImpl extends UnicastRemoteObject
implements Test
{

	private String name;
	public ProductImpl(String s)throws RemoteException
	{
		name=s;
	}
	public static void main(String[] args) {
	}
	/* ���� Javadoc��
	 * @see testRmi.Test#getD()
	 */
	public String getD() throws RemoteException {
		// TODO �Զ����ɷ������
		return "class is " +this.getClass().getName();
	}
}
