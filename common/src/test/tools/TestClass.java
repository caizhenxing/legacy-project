/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 27, 20072:48:48 PM
 * �ļ�����TestClass.java
 * �����ߣ�zhaoyf
 * 
 */
package test.tools;

import com.zyf.code.SysCodeInfo;
/**
 * @author zhaoyf
 *
 */
public class TestClass {

	/**
	 * ��������
	 * @param args
	 * Oct 27, 2007 2:48:48 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class c=Class.forName(SysCodeInfo.class.getName());
			System.out.println(c.getName());
			
//			Field[] fs=c.getDeclaredFields();
//			for(int i=0;i<fs.length;i++)
//			{
//				Field f=fs[i];
//				System.out.println();
//			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
