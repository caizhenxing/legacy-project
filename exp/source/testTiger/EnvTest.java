/*
 * �������� 2004-12-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testTiger;
import java.lang.ProcessBuilder;
import java.io.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class EnvTest {
  public static void main(String args[]) {
  	
	System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
	try
	{
	
	String[] s={"java -help"};
	Process p =new ProcessBuilder(s).start();
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}catch(Exception e)
	{
	}
  }
}

