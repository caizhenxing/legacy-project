package et.test.callcenter.police;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.BusinessObject;
import et.bo.callcenter.business.impl.PoliceCallin;
import et.bo.pcc.cclog.service.CclogService;
import et.bo.pcc.policeinfo.PoliceCallInService;
import et.po.PoliceCallinInfo;
import excellence.common.key.KeyService;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class TestPoliceCallIn extends TestCase {

	/**
	 * 录入主从表信息
	 * 
	 * @param
	 * @version Oct 11, 2006
	 * @return
	 */
//	public void testPoliceCallIn() throws Exception {
//		SpringContainer gg = SpringContainer.getInstance();
//		PoliceCallInService pcs = (PoliceCallInService) gg
//				.getBean("PoliceCallInService");
//		KeyService ks = (KeyService) gg.getBean("KeyService");
//		IBaseDTO dto = new DynaBeanDTO();
//		String policeinid = ks.getNext("police_callin");
//		dto.set("id", policeinid);
//		//dto.set("fuzzNo", "001");
//		//dto.set("isvalidin", "001");
//		//dto.set("passvalidnum", 2);
//		//dto.set("operator", "operator");
//		PoliceCallin pci = new PoliceCallin();
//		pci.setId(policeinid);
//		//pci.setFuzzNo("fuzzNo");
//		//pci.setIsValidIn("001");
//		//pci.setPassValidNum(new Integer(2));
//		//pci.setOperator("operator");
//		Set set = new HashSet();
//		for (int i = 0; i < 5; i++) {
//			PoliceCallinInfo pcii = new PoliceCallinInfo();
//			String policecallinid = ks.getNext("police_callin_info");
//			pcii.setId(policecallinid);
//			pcii.setPoliceCallin(pci);
//			String s = "000" + i;
//			System.out.println(s);
//			pcii.setTagInfo(s);
//			pcii.setQuInfo(s);
//			pcii.setContent(s);
//			pcii.setRemark(s);
//			set.add(pcii);
//		}
//		pcs.addPoliceCallInInfo(dto, set);
//		System.out.println("录入成功");
//	}
	
	public void testPoliceCall() throws Exception {
		SpringContainer gg = SpringContainer.getInstance();
		BusinessObject bo = (BusinessObject)gg.getBean("BusinessObjectService");
		KeyService ks = (KeyService) gg.getBean("KeyService");
		//PoliceCallInService pcs = (PoliceCallInService)gg.getBean("PoliceCallInService");
		//CclogService ccs = (CclogService)gg.getBean("CclogService");
		et.bo.callcenter.business.impl.PoliceCallin pci = new et.bo.callcenter.business.impl.PoliceCallin();
		ConnectInfo cci = new ConnectInfo();
		
		//String pcid = ks.getNext("police_callin");
		String pcid = "CC_LOG_0000000021";
		cci.setId(pcid);
		pci.setId(pcid);
		pci.setFuzzNum("888");
		pci.setValidInfo("2");
		pci.setNum(0);
		pci.setOperatorNum("***");
		ConnectInfo.getCurConn().put(pcid, cci);
		//pci.setFuzzNum("0");
		et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().put(pcid, pci);
		
		ConnectInfo connectInfo=null;
		
		bo.saveAll(pcid);
		
		
//		Iterator it = ConnectInfo.getCurConn().entrySet().iterator();
//		while(it.hasNext()){
//			ConnectInfo ci = (ConnectInfo)it.next();
//			if(id.equals(ci.getId())){
//				connectInfo=ci;
//				break;
//			}
//		}
		//Iterator it = ConnectInfo.getCurConn().keySet().iterator();
		//while(it.hasNext()){
			//String port =(String)it.next();
			//ConnectInfo ci=(ConnectInfo)ConnectInfo.getCurConn().get(port);
			//if(pcid.equals(ci.getId())){
				//connectInfo=ci;
				//break;
			//}
		//}
		//ccs.addCclog(connectInfo);
		
		
		
		
		
		
		
		
		//bo.savePc(pcid);
//		PoliceCallin policeCallin = null;
//		if(!PoliceCallin.getCallinIdMap().containsKey(pcid)){
//			//log.debug("输入信息");
//		}else{
//			policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(pcid);
//		}
//		//System.out.println("pid  "+id);
//		//System.out.println("id  "+policeCallin.getId());
//		IBaseDTO policeCallInDTO = new DynaBeanDTO();
//		policeCallInDTO.set("id", pcid);
//		policeCallInDTO.set("fuzzNo", policeCallin.getFuzzNum());
//		//log.debug("警号.."+policeCallin.getFuzzNum());
//		policeCallInDTO.set("isvalidin", "2");
//		//log.debug("输入信息111111111"+policeCallin.getFuzzNum());
//		policeCallInDTO.set("passvalidnum", (""+policeCallin.getNum())==null?"0":(""+policeCallin.getNum()));
//		policeCallInDTO.set("operator", policeCallin.getOperatorNum());
//		
//		et.po.PoliceCallin pc = new et.po.PoliceCallin();
//		pc.setId(pcid);
//		//pc.setFuzzNo("888");
//		pc.setFuzzNo(policeCallin.getFuzzNum());
//		pc.setPassValidNum(new Integer((""+policeCallin.getNum())==null?"0":(""+policeCallin.getNum())));
//		pc.setIsValidIn("2");
//		pc.setOperator("***");
//		List l = policeCallin.getCallinInfo();
//		Iterator iter = l.iterator();
//		Set set = new HashSet();
//		while (iter.hasNext()) {
//			PoliceCallinInfo pcii = (PoliceCallinInfo)iter.next();
//			String policecallinid = ks.getNext("police_callin_info");
//			pcii.setId(policecallinid);
//			pcii.setPoliceCallin(pc);
//			pcii.setTagInfo(pcii.getTagInfo());
//			pcii.setQuInfo(pcii.getQuInfo());
//			pcii.setContent(pcii.getContent());
//			pcii.setRemark(pcii.getRemark());
//			set.add(pcii);
//		}
//		if (pcs.addPoliceCallInInfo(policeCallInDTO, set)) {
//			System.out.println("成功");
//			//return "成功";
//		} else {
//			System.out.println("失败");
//			//return FAIL_DEFAULT;
//		}
	}
}
