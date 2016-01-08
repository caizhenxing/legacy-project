package et.test.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Store;

import junit.framework.TestCase;
import et.bo.oa.communicate.email.service.impl.OperOuterEmail;

public class TestEmailInfo extends TestCase {
	
	Folder inbox;

	Store store;
	
	public void testSendEmail(){
		OperOuterEmail operOuterEmail = new OperOuterEmail();
		try {
			List l = new ArrayList();
			operOuterEmail.sendOutEmail("smtp.163.com", "lgstar888", "bystar888", "zhaoyifei1@tom.com", "lgstar888@163.com", "ÄãºÃaaaaaaaa", "<DIV>ÄãºÃaaaaaaaaa</DIV>",l);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	

}
