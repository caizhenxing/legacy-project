/*
 * �������� 2004-12-13
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
public class testExec {
	//String bak="exp jc/jc@ORCL_10.5.31.108 owner=jc  buffer=65536 file=";
	//String name="e:/bak.dmp";
	public static void main(String[] args) {
		testExec t=new testExec();
		try {
			
				  Process process = Runtime.getRuntime().exec("cmd.exe start  http://www.yahoo.com");  //��¼��վ
				  //Process process1 = Runtime.getRuntime().exec("cmd.exe  /c  start  ping 10.144.98.100");  //����Ping����
				  //Process p=Runtime.getRuntime().exec("cmd.exe /c "+t.bak+t.name);
				}catch (Exception  e)
				{
					e.printStackTrace();
					} 
	}
}
