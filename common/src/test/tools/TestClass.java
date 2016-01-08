/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 27, 20072:48:48 PM
 * 文件名：TestClass.java
 * 制作者：zhaoyf
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
	 * 功能描述
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
