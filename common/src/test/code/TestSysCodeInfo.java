/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-9-7����02:24:20
 * �ļ�����TestSysCodeInfo.java
 * �����ߣ�zhaoyf
 * 
 */
package test.code;

import java.util.ResourceBundle;

import com.zyf.tools.MessageInfo;

/**
 * @author zhaoyf
 *
 */
public class TestSysCodeInfo {

	/**
	 * ��������
	 * @param args
	 * 2007-9-7����02:24:20
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=null;
		ResourceBundle rb=ResourceBundle.getBundle("message");
		s = rb.getString("HR_I004_R_0");//new String("\u65b0\u589e\u6210\u529f, \u60a8\u73b0\u5728\u53ef\u4ee5\u7ee7\u7eed\u4fee\u6539\u8fd9\u6761\u8bb0\u5f55.".getBytes("ISO-8859-1"),"GBK");
		System.out.println(s);
		System.out.println(MessageInfo.factory().getMessage("HR_I021_A_0","aa","bb"));
	}

}
