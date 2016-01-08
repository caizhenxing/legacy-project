package et.test.police.business;

import et.bo.pcc.policeinfo.PoliceInfoService;
import excellence.framework.base.container.SpringContainer;
import junit.framework.TestCase;

public class TestUserAndPass extends TestCase {
	
	public void testUserAndPass(){
		SpringContainer gg = SpringContainer.getInstance();
		PoliceInfoService pis = (PoliceInfoService)gg.getBean("PoliceInfoService");
		String username = "888";
		String password = "888";
		pis.checkPoliceNum(username, password);
	}

}
