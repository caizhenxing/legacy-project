/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.common;
/**
 * �б���ʾ��
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class MathUtil {
	int initArr[]   =   null; 
	public MathUtil()
	{
		initArr = new int[100];
	}
	
	public void addBits(int[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			initArr[i] = arr[i]+initArr[i];
		}
	}
	public int[] getSumArr()
	{
		return initArr;
	}
}
