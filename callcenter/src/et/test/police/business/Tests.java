package et.test.police.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.BusinessObject;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.container.SpringRunningContainer;

public class Tests {

	/**
	 * @param
	 * @version Oct 16, 2006
	 * @return
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringContainer sc=SpringContainer.getInstance();
		BusinessObject pi = 
			(BusinessObject)SpringRunningContainer.getInstance().getBean("BusinessObjectService");
		et.bo.callcenter.business.impl.PoliceCallin pci = new et.bo.callcenter.business.impl.PoliceCallin();
		ConnectInfo cci = new ConnectInfo();
		String pcid = "CC_LOG_0000000166";
		cci.setId(pcid);
		pci.setId(pcid);
		pci.setFuzzNum("888");
		pci.setNum(0);
		pci.setValidInfo("2");
		pci.setOperatorNum("***");
		Set s=new HashSet();
		pci.setCallinInfo(new ArrayList());
		ConnectInfo.getCurConn().put(pcid, cci);
		//pci.setFuzzNum("0");
		et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().put(pcid, pci);
		//pi.saveAll(pcid);
		pi.savePc(pcid);
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		pci = new et.bo.callcenter.business.impl.PoliceCallin();
//		cci = new ConnectInfo();
//		String pccid = "CC_LOG_0000000161";
//		cci.setId(pccid);
//		pci.setId(pccid);
//		pci.setFuzzNum("888");
//		pci.setNum(0);
//		pci.setValidInfo("2");
//		pci.setOperatorNum("***");
//		ConnectInfo.getCurConn().put(pcid, cci);
//		//pci.setFuzzNum("0");
//		et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().put(pcid, pci);
//		pi.saveAll(pcid);
	}

}
