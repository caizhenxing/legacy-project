/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�ExcellenceBase
 * ����ʱ�䣺2009-3-17����02:53:13
 * ������excellence.common.mail
 * �ļ�����TestSendMail.java
 * �����ߣ�Administrator
 * @version 1.0
 */
package excellence.common.mail;

import junit.framework.TestCase;

/**
 * 
 * @author Administrator
 * @version 1.0
 */
public class TestSendMail extends TestCase {

	/**
	 * Test method for {@link excellence.common.mail.SendMail#sendMail(excellence.common.mail.SendMailBean)}.
	 */
	public void testSendMail() {
		
		SendMailService sm = new SendMail();
		sm.setEmail("zhaoyifei1@tom.com");
		sm.setHostName("smtp.tom.com");
		sm.setUser("zhaoyifei1@tom.com");
		sm.setPassword("222141");
		SendMailBean smb = new SendMailBean();
		smb.addAttachments("d:/aa.txt");
		smb.addAttachments("d:/bb.txt");
		smb.addAttachments("d:/cc.txt");
		//smb.addBccs("zhaoyifei1@tom.com");
		//smb.addCc("zhaoyifei1@tom.com");
		smb.addToAddr("zhaoyf@sy-et.com");
		smb.setFromAddr("zhaoyifei1@tom.com");
		smb.setMsg("���ԣ����� dfdafdasfafd");
		smb.setSubject("�����ʼ�1");
		sm.sendMail(smb);
		
		
	}

}
