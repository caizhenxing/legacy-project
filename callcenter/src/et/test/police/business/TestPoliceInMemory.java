package et.test.police.business;

import java.util.Iterator;

import junit.framework.TestCase;
import et.bo.callcenter.business.impl.PoliceCallin;
import et.bo.pcc.policeinfo.PoliceInfoService;
import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import et.po.PoliceCallinInfo;
import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringContainer;

public class TestPoliceInMemory extends TestCase {
	/**
	 * Â¼ÈëÄÚ´æ²âÊÔ
	 * @param
	 * @version Oct 13, 2006
	 * @return
	 */
	public void testPoliceToMemory() throws Exception {
		SpringContainer gg = SpringContainer.getInstance();
		PoliceInfoService pis = (PoliceInfoService)gg.getBean("PoliceInfoService");
		KeyService ks = (KeyService) gg.getBean("KeyService");
		PoliceCallin pc = new PoliceCallin();
		et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().put("001", pc);
		
		et.bo.callcenter.business.impl.PoliceCallin pci = (et.bo.callcenter.business.impl.PoliceCallin)et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().get("001");
		Iterator it = pci.getCallinInfo().iterator();
		while(it.hasNext()){
			PoliceCallinInfo pcii = (PoliceCallinInfo)it.next();
			System.out.println(pcii.getId());
			System.out.println(pcii.getTagInfo());
			System.out.println(pcii.getQuInfo());
			System.out.println(pcii.getContent());
		}
		
		for (int i = 0; i < 5; i++) {
			//IBaseDTO dto = new DynaBeanDTO();
			PoliceCallInInfoInMemory pm = new PoliceCallInInfoInMemory();
			pm.setPcid("001");
			//dto.set("pcid", "001");
			pm.setId(ks.getNext("police_callin_info"));
			//dto.set("id", ks.getNext("police_callin_info"));
			pm.setTaginfo("taginfo"+i);
			//dto.set("taginfo", "taginfo"+i);
			pm.setQuinfo("quinfo"+i);
			//dto.set("quinfo", "quinfo"+i);
			pm.setContent("content"+i);
			//dto.set("content", "content"+i);
			pm.setRemark("remark"+i);
			//dto.set("remark", "remark"+i);
			pis.addPoliceCallInInfo(pm);
		}

		
		et.bo.callcenter.business.impl.PoliceCallin pci1 = (et.bo.callcenter.business.impl.PoliceCallin)et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().get("001");
		Iterator iter = pci1.getCallinInfo().iterator();
		while(iter.hasNext()){
			PoliceCallinInfo pcii = (PoliceCallinInfo)iter.next();
			System.out.println(pcii.getId());
			System.out.println(pcii.getTagInfo());
			System.out.println(pcii.getQuInfo());
			System.out.println(pcii.getContent());
		}
	}

}
