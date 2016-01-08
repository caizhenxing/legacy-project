/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Sep 26, 20074:14:08 PM
 * 文件名：TestTools.java
 * 制作者：zhaoyf
 * 
 */
package test.tools;

import java.text.DecimalFormat;


/**
 * @author zhaoyf
 *
 */
public class TestTools {

	/**
	 * 功能描述
	 * @param args
	 * Sep 26, 20074:14:08 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		TestObject to=new TestObject();
//		Tools.setProperty(to,"aa.a","a");
//		System.out.println(Tools.getProperty(to,"aa.a"));
//		File f=new File("d:/zhaoyifei");
//		f.mkdir();
		
		System.out.println(new DecimalFormat("#,###.##").format(new Long(3411212)));
//		List l=Tools.split("aa$bb$cc$dd$$$dd$","\\$");
//		for(int i=0,size=l.size();i<size;i++)
//		{
//			System.out.println(l.get(i).toString());
//			l.remove(i);
//		}
//		System.out.println("aaaa\"".replaceAll("\"","\\\\\""));
//		System.out.println("\\\\\"");
	}

}
