/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Nov 1, 200711:23:33 AM
 * �ļ�����TestMessageFormat.java
 * �����ߣ�zhaoyf
 * 
 */
package test.tools;

import java.text.MessageFormat;

/**
 * @author zhaoyf
 *
 */
public class TestMessageFormat {

	/**
	 * ��������
	 * @param args
	 * Nov 1, 2007 11:23:33 AM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String s="'			<input type=\"hidden\" name=\"step\" value={0}<c:out value=\"${theForm.step}\"/>{0} />";
		String s="'{'{0}.{1}}";
		System.out.println(MessageFormat.format(s,new Object[]{"a","n"}));
//		ListJspCreateByXml ljcx=new ListJspCreateByXml();
//		ljcx.createJsp("d:\\zhaoyifei\\list\\listJsp.xml");
	}

}
