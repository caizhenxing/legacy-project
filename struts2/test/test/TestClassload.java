/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 31, 200912:58:09 PM
 * ������test
 * �ļ�����TestClassload.java
 * �����ߣ�Administrator
 * @version 1.0
 */
package test;

/**
 * 
 * @author Administrator
 * @version 1.0
 */
public class TestClassload {

	/**
	 * ��������
	 * @param args
	 * May 31, 2009 12:58:09 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String[] s = "com.cc.sys.db.SysGroup-id-name".split("-");
			Class cl = Class.forName(s[0]);//ClassLoader.getSystemClassLoader().loadClass(s[0]);
			System.out.print(cl.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
