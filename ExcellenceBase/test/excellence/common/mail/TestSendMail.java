/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-17下午02:53:13
 * 包名：excellence.common.mail
 * 文件名：TestSendMail.java
 * 制作者：Administrator
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
		smb.setMsg("测试，附件 dfdafdasfafd");
		smb.setSubject("测试邮件1");
		sm.sendMail(smb);
		
		
	}

}
