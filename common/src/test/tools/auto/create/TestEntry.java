/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Nov 1, 20074:07:21 PM
 * �ļ�����TestEntry.java
 * �����ߣ�zhaoyf
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
	 * ��������
	 * @param args
	 * Nov 1, 2007 4:07:21 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntryJspCreateByXml ejcx=new EntryJspCreateByXml();
		ejcx.createJsp("d:\\zhaoyifei\\entry\\entryJsp.xml");
	}

}
