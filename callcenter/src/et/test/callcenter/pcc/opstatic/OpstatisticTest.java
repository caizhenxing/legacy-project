package et.test.callcenter.pcc.opstatic;

import org.apache.log4j.Logger;

import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;
import excellence.framework.base.container.SpringContainer;

public class OpstatisticTest {
    static Logger log = Logger.getLogger(OpstatisticTest.class.getName()); 
    public static void main(String[] args) {
    	SpringContainer s=SpringContainer.getInstance();
    	OperatorStatisticService os = (OperatorStatisticService)s.getBean("OperatorStatisticService");
//    	os.addSignIn("yepl");   
//    	os.addSetting("yepl");
//    	os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//        os.addAnswerPhone("yepl");
//        os.addDisconnectPhone("yepl");
//    	os.addOutSetting("yepl");       
        os.addSignOut("Ã‚");
    	log.info("..............success..............");
	}
}
