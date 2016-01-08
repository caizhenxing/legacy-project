package et.test.callcenter.pcc.portCompare;

import org.apache.log4j.Logger;

import et.bo.common.testing.MyTesting;
import et.bo.pcc.portCompare.service.PortCompareService;

public class TestPort extends MyTesting {
//	static Logger log = Logger.getLogger(TestPortCompare.class.getName());
	public static void main(String[] args) {
		TestPort tp = new TestPort();
		tp.testing();
		PortCompareService pcs = (PortCompareService)tp.springContainer.getBean("PortCompareService");
		pcs.getIpByPort().get(1);
	}
}
