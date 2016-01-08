package et.test.callcenter.pcc.portCompare;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import et.bo.pcc.portCompare.service.PortCompareService;
import excellence.framework.base.container.SpringContainer;

public class TestPortCompare extends TestCase {
	static Logger log = Logger.getLogger(TestPortCompare.class.getName()); 
	public void testPortCompare() throws Exception {
	SpringContainer container = SpringContainer.getInstance();
//	KeyService ks = (KeyService) container.getBean("KeyService");	
//	BusinessObject bo = (BusinessObject) container.getBean("BusinessObjectService");
	PortCompareService ps = (PortCompareService)container.getBean("PortCompareService");
    //测试黑名单
	
//	String pnum = "23996718";
//	String flag = bo.blacklist(pnum);
//	String message = "";
//	if(flag.equals("0")){
//		message = "在电话黑名单内";
//	}else{
//		message = "不在电话黑名单内";
//	}
//	log.info("返回值为: "+flag+" "+pnum+" "+message);
//	
//	String ip=bo.getPortLinkedIp("001");
//	log.info("ip地址为："+ip);
	HashMap map= ps.getIpByPort();
	String ip=(String)map.get("001");
	log.info("ip地址为："+ip);
 }
}
