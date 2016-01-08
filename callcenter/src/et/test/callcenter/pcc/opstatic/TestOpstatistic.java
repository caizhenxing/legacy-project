package et.test.callcenter.pcc.opstatic;

import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;
import et.bo.pcc.policeinfo.PoliceCallInService;
import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringContainer;
import junit.framework.TestCase;

public class TestOpstatistic extends TestCase {
	public void testPoliceCallIn() throws Exception {
		SpringContainer container = SpringContainer.getInstance();
		PoliceCallInService pcs = (PoliceCallInService) container
		.getBean("PoliceCallInService");
	   	OperatorStatisticService os = (OperatorStatisticService)container.getBean("OperatorStatisticService");
	   	KeyService ks = (KeyService) container.getBean("KeyService");
    	os.addSignIn("yepl");   
    	os.addSetting("yepl");
//    	os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//    	os.addOutSetting("yepl"); 
    	os.addSignOut("yepl");
	   	System.out.println("..................OK................");
	}
}
