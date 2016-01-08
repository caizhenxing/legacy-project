/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 1, 20074:07:21 PM
 * 文件名：TestEntry.java
 * 制作者：zhaoyf
 * 
 */
package test.tools.auto.create;

import com.zyf.tools.auto.create.entry.EntryJspCreateByXml;

/**
 * @author zhaoyf
 *
 */
public class TestEntry {

	/**
	 * 功能描述
	 * @param args
	 * Nov 1, 2007 4:07:21 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntryJspCreateByXml ejcx=new EntryJspCreateByXml();
		ejcx.createJsp("d:\\zhaoyifei\\entry\\entryJsp.xml");
	}

}
