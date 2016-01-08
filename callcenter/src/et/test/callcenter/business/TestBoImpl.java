package et.test.callcenter.business;

import org.apache.log4j.Logger;

import et.bo.callcenter.business.BusinessObject;
import excellence.framework.base.container.SpringContainer;
import junit.framework.TestCase;

public class TestBoImpl extends TestCase {
	static Logger log = Logger.getLogger(TestBoImpl.class.getName()); 
	public void testBoImpl() throws Exception {
	SpringContainer container = SpringContainer.getInstance();
//	KeyService ks = (KeyService) container.getBean("KeyService");	
	BusinessObject bo = (BusinessObject) container.getBean("BusinessObjectService");
    //测试黑名单
	
	String pnum = "23996718";
	String flag = bo.blacklist(pnum);
	String message = "";
	if(flag.equals("0")){
		message = "在电话黑名单内";
	}else{
		message = "不在电话黑名单内";
	}
	log.info("返回值为: "+flag+" "+pnum+" "+message);
	
	String ip=bo.getPortLinkedIp("9");
//	System.out.println();
	log.info("ip地址为："+ip);
 }
}
