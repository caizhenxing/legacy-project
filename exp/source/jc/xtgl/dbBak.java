/*
 * �������� 2004-12-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package jc.xtgl;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class dbBak {
	String bak="exp jc/jc@ORCL_10.5.31.108 owner=jc  buffer=65536 file=";
	String filePath="e:\\db_bak\\";	String fileType=".dmp";
	
		public void exeBak(String filename)
		{
			
			try 
			{
				Process p=Runtime.getRuntime().exec("cmd.exe /c "+this.bak+this.filePath+filename+this.fileType);
			}catch (Exception  e)
			{
				e.printStackTrace();
			} 
		}
		public static void main(String[] args)
		{
			dbBak d=new dbBak();
			d.exeBak("aa");
		}
}
