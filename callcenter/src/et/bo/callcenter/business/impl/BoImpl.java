package et.bo.callcenter.business.impl;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstantsI;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.BusinessObject;
import et.bo.common.testing.MyLog;
import et.bo.pcc.callinFirewall.service.CallinFirewallService;
import et.bo.pcc.cclog.service.CclogService;
import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;
import et.bo.pcc.policeinfo.PoliceCallInService;
import et.bo.pcc.policeinfo.PoliceInfoService;
import et.bo.pcc.policeinfo.impl.InfoSearch;
import et.bo.pcc.portCompare.service.PortCompareService;
import et.bo.sys.user.service.UserService;
import et.po.PoliceCallinInfo;
import et.po.PoliceinfoTemp;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;

public class BoImpl implements BusinessObject {
	private static Log log = LogFactory.getLog(BoImpl.class);
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#blacklist(java.lang.String)
 */
	private CallinFirewallService callinFirewallService = null;
	//警员是否存在
	private PoliceInfoService policeInfoService = null;
	
	//警员呼叫信息录入数据库
	private PoliceCallInService policeCallInService = null;
	
    private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	private UserService userService = null;
	
	private CclogService cclogService = null;
	
	private PortCompareService portCompareService = null;
	
	private ClassTreeService cts;
	
	private OperatorStatisticService operatorStatisticService = null; 
	
	public String blacklist(String phoneNum) {
		// TODO Auto-generated method stub
		Boolean flag = callinFirewallService.IfInBlacklist(phoneNum);
		if(flag){
			return FAIL_DEFAULT;//电话不在黑名单内
		}else{
			return SUCCEED;//电话在黑名单内
		}
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#getCardPcIp()
 */
	public String getCardPcIp() {
		// TODO Auto-generated method stub
		String ip=	cts.getvaluebyNickName(ConstantsI.CLIENT_IP);
		log.debug("public String getCardPcIp()"+ip);
		return ip;
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#getPcNum(java.lang.String)
 */
	public int getPcNum(String id) {
		/*
		 * 此类需要更改
		 */
		// TODO Auto-generated method stub
//		et.po.PoliceCallin pc = (et.po.PoliceCallin)dao.loadEntity(et.po.PoliceCallin.class, id);
//		int validNum = pc.getPassValidNum().intValue();
//		return validNum;
		if(!PoliceCallin.getCallinIdMap().containsKey(id))return 0;
		PoliceCallin pc1 = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		pc1.setNum(pc1.getNum());
		return pc1.getNum();
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#getPortLinkedIp(java.lang.String)
 */
	public String getPortLinkedIp(String port) {
		// TODO Auto-generated method stub
	    String ip =(String)portCompareService.getIpByPort().get(port);
	    if(ip!=null){
	    	return ip;   	
	    }else{
	    	ip = (String)portCompareService.flushGetIpByPort().get(port);
	    	if(ip!=null){
	    		return ip;
	    	}else{
	    		return FAIL_DEFAULT;
	    	} 	
	    }
		
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#isOperator(java.lang.String, java.lang.String)
 */
	public String isOperator(String u, String p) {
		// TODO Auto-generated method stub
		if (userService.check(u, p)) {
			return SUCCEED;
		}else{
			return FAIL_DEFAULT;
		}
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#isPolice(java.lang.String, java.lang.String)
 */
	public String isPolice(String fuzzNum, String pass) {
		// TODO Auto-generated method stub
		if (policeInfoService.checkPoliceNum(fuzzNum, pass)) {
			return SUCCEED;
		}else{
			return FAIL_DEFAULT;
		}
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#savePoliceCallin(java.lang.String)
 */
	public String savePc(String id) {
		// TODO Auto-generated method stub
		PoliceCallin policeCallin = null;
		if(!PoliceCallin.getCallinIdMap().containsKey(id)){
			log.debug("输入信息");
		}else{
			policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		}

		//IBaseDTO policeCallInDTO = new DynaBeanDTO();
		//policeCallInDTO.set("id", id);
		//policeCallInDTO.set("fuzzNo", policeCallin.getFuzzNum());
		//policeCallInDTO.set("isvalidin", policeCallin.getValidInfo());
		//policeCallInDTO.set("passvalidnum", (""+policeCallin.getNum())==null?"0":(""+policeCallin.getNum()));
		//policeCallInDTO.set("operator", policeCallin.getOperatorNum());
		
		//System.out.println("************************************************************************");
		//System.out.println("id1: "+id);
		//System.out.println("id2: "+policeCallin.getFuzzNum());
		//System.out.println("id3: "+policeCallin.getValidInfo());
		//System.out.println("id4: "+policeCallin.getNum());
		//System.out.println("id5: "+policeCallin.getOperatorNum());
		//System.out.println("id6: "+policeCallin.getCallinInfo().size());
		//System.out.println("************************************************************************");
		et.bo.callcenter.business.impl.PoliceCallin pci = new et.bo.callcenter.business.impl.PoliceCallin();
		//String pcid = "CC_LOG_0000000166";
		pci.setId(policeCallin.getId());
		pci.setFuzzNum(policeCallin.getFuzzNum());
		pci.setNum(policeCallin.getNum());
		pci.setValidInfo(policeCallin.getValidInfo());
		pci.setOperatorNum(policeCallin.getOperatorNum());
		pci.setCallinInfo(policeCallin.getCallinInfo());
		if (policeCallInService.addPoliceCallInInfo(pci)) {
			return SUCCEED;
		} else {
			return FAIL_DEFAULT;
		}
		
		
		//et.po.PoliceCallin pc = new et.po.PoliceCallin();
		//pc.setId(id);
		//pc.setFuzzNo(policeCallin.getFuzzNum());
		//pc.setPassValidNum(new Integer((""+policeCallin.getNum())==null?"0":(""+policeCallin.getNum())));
		//pc.setIsValidIn(policeCallin.getValidInfo());
		//pc.setOperator(policeCallin.getOperatorNum());
		//List l = policeCallin.getCallinInfo();
		//Iterator it = l.iterator();
		//Set set = new HashSet();
		//while (it.hasNext()) {
			//PoliceCallinInfo pcii = (PoliceCallinInfo)it.next();
			//String policecallinid = ks.getNext("police_callin_info");
			//pcii.setId(policecallinid);
			//pcii.setPoliceCallin(pc);
			//pcii.setTagInfo(pcii.getTagInfo());
			//pcii.setQuInfo(pcii.getQuInfo());
			//pcii.setContent(pcii.getContent());
			//pcii.setRemark(pcii.getRemark());
			//set.add(pcii);
		//}

	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#setPcFuzzNum(java.lang.String, java.lang.String)
 */
	public void setPcFuzzNum(String id, String fuzzNum) {
		// TODO Auto-generated method stub
		if(!PoliceCallin.getCallinIdMap().containsKey(id)){
			
		}else{
			PoliceCallin policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
			policeCallin.setFuzzNum(fuzzNum);
		}
		//PoliceCallin.getCallinIdMap().put(id, policeCallin);
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#setPcInstance(java.lang.String)
 */
	public void setPcInstance(String id) {
		// TODO Auto-generated method stub
		PoliceCallin pc = new PoliceCallin();
		pc.setId(id);
		PoliceCallin.getCallinIdMap().put(id, pc);
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#setPcNum(java.lang.String, int)
 */
	public void setPcNum(String id, int i) {
		// TODO Auto-generated method stub
		PoliceCallin policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		policeCallin.setNum(i);
		//PoliceCallin.getCallinIdMap().put(id, policeCallin);
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#setPcOperatorNum(java.lang.String, java.lang.String)
 */
	public void setPcOperatorNum(String id, String operatorNum) {
		// TODO Auto-generated method stub
		PoliceCallin policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		policeCallin.setOperatorNum(operatorNum);
		//PoliceCallin.getCallinIdMap().put(id, policeCallin);
	}
/*
 * (non-Javadoc)
 * @see et.bo.callcenter.business.BusinessObject#setPcValidInfo(java.lang.String, java.lang.String)
 */
	public void setPcValidInfo(String id, String validInfo) {
		// TODO Auto-generated method stub
		PoliceCallin policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		policeCallin.setValidInfo(validInfo);
		//PoliceCallin.getCallinIdMap().put(id, policeCallin);
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.business.BusinessObject#saveAll(java.lang.String)
	 */
	public String saveAll(String id) {
		try {
			if(saveAnswerPhone(id)==null){
//				System.out.println(saveAnswerPhone(id));
//				System.out.println("添加失败.................");
				return null;
			}else{
//				System.out.println(saveAnswerPhone(id));
//				System.out.println("添加接通.................");
				saveDisconnectPhone(id);
//				System.out.println("添加挂断.................");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		saveCclog(id);
		savePc(id);
		et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap().remove(id);
		ConnectInfo.getCurConn().remove(id);
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see et.bo.callcenter.business.BusinessObject#saveCclog(java.lang.String)
	 */
	public String saveCclog(String id) {
		
		if(id==null) return null;
		ConnectInfo connectInfo=null;
//		Iterator it = ConnectInfo.getCurConn().entrySet().iterator();
//		while(it.hasNext()){
//			ConnectInfo ci = (ConnectInfo)it.next();
//			if(id.equals(ci.getId())){
//				connectInfo=ci;
//				break;
//			}
//		}
		
		Iterator it = ConnectInfo.getCurConn().keySet().iterator();
		while(it.hasNext()){
			String port =(String)it.next();
			ConnectInfo ci=(ConnectInfo)ConnectInfo.getCurConn().get(port);
			if(id.equals(ci.getId())){
				connectInfo=ci;
				break;
			}
		}
		//
		if(connectInfo==null) return null;
		MyLog.info("begin cclogService.addCclog(connectInfo);");
		MyLog.info("id: "+id);
		
		cclogService.addCclog(connectInfo);
		MyLog.info("end cclogService.addCclog(connectInfo);");
		//
		return null;
	}
	/**
	 * 添加接听电话时间
	 */
	private String saveAnswerPhone(String id){
		String operator = null;
		Date date = null;
		if(getOperatorId(id)!=null){
//			log.info("operatorId   is   :"+operator);
		    operator = getOperatorId(id);
//		    log.info("operatorId   is   :"+operator);
		}else{
			return "on";
		}
		if(getAnswerPhoneDate(id)!=null){
//			log.info("operator   date   :"+TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss"));
			date = getAnswerPhoneDate(id);
		}else{
			return "dn";
		}
//		System.out.println("操作员 ： "+operator+"  "+" 时间 "+TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss"));
		if(date!=null&&operator!=null){
//			log.debug("输入信息");
			operatorStatisticService.addAnswerPhone(operator, date);
			return SUCCEED;
		}else{
			return "error";	
		}		
	}
	/**
	 * 添加挂断电话时间
	 */
	private String saveDisconnectPhone(String id){
		String operator = null;
		Date date = null;
		if(getOperatorId(id)!=null){
		    operator = getOperatorId(id);
		}else{
			return null;
		}
		if(getDisconnectPhoneDate(id)!=null){
			date = getDisconnectPhoneDate(id);
		}else{
			return null;
		}
		if(date!=null&&operator!=null){
			operatorStatisticService.addDisconnectPhone(operator, date);
			return SUCCEED;
		}else{
			return null;	
		}
	}
	/**
	 * 得到操作员ID
	 */
	private String getOperatorId(String id){
		PoliceCallin policeCallin = null;
		if(!PoliceCallin.getCallinIdMap().containsKey(id)){
//			log.debug("输入信息");
			return "idn";
		}else{
			policeCallin = (PoliceCallin)PoliceCallin.getCallinIdMap().get(id);
		}
		if(policeCallin.getOperatorNum()==null){return null;}
//		log.info("操作员为："+"   "+policeCallin.getOperatorNum());
		String operator = policeCallin.getOperatorNum();
		return operator;
	}
	/**
	 * 得到接听时间
	 */
	private Date getAnswerPhoneDate(String id){
		if(id==null) return null;
		ConnectInfo connectInfo=null;
		Iterator it = ConnectInfo.getCurConn().keySet().iterator();
		while(it.hasNext()){
			String port =(String)it.next();
			ConnectInfo ci=(ConnectInfo)ConnectInfo.getCurConn().get(port);
//			System.out.println(connectInfo.getOperatorPort());
			if(id.equals(ci.getId())){
				connectInfo=ci;
				break;
			}
		}
		//
		if(connectInfo==null) return null;
		if(connectInfo.getOperateTime()==null) return null;
		Date date = connectInfo.getOperateTime();
		return date;
	}
	/**
	 * 得到挂断时间
	 */
	private Date getDisconnectPhoneDate(String id){
		if(id==null) return null;
		ConnectInfo connectInfo=null;
		Iterator it = ConnectInfo.getCurConn().keySet().iterator();
		while(it.hasNext()){
			String port =(String)it.next();
			ConnectInfo ci=(ConnectInfo)ConnectInfo.getCurConn().get(port);
			if(id.equals(ci.getId())){
				connectInfo=ci;
//				System.out.println(connectInfo.getOperatorPort());
				break;
			}
		}
		//
		if(connectInfo==null) return null;
		if(connectInfo.getEndTime()==null) return null;
		Date date = connectInfo.getEndTime();
		return date;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public CallinFirewallService getCallinFirewallService() {
		return callinFirewallService;
	}
	public void setCallinFirewallService(CallinFirewallService callinFirewallService) {
		this.callinFirewallService = callinFirewallService;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	public PoliceInfoService getPoliceInfoService() {
		return policeInfoService;
	}
	public void setPoliceInfoService(PoliceInfoService policeInfoService) {
		this.policeInfoService = policeInfoService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public CclogService getCclogService() {
		return cclogService;
	}
	public void setCclogService(CclogService cclogService) {
		this.cclogService = cclogService;
	}
	public PortCompareService getPortCompareService() {
		return portCompareService;
	}
	public void setPortCompareService(PortCompareService portCompareService) {
		this.portCompareService = portCompareService;
	}
	public PoliceCallInService getPoliceCallInService() {
		return policeCallInService;
	}
	public void setPoliceCallInService(PoliceCallInService policeCallInService) {
		this.policeCallInService = policeCallInService;
	}
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}	
	private UserService us;
	
	public String validUser(String user,String password){
		boolean b=us.check(user, password);
		if(b){
			return BusinessObject.SUCCEED;
		}else {
			return BusinessObject.FAIL_DEFAULT;
		}
	}
	public void setUs(UserService us) {
		this.us = us;
	}
	public OperatorStatisticService getOperatorStatisticService() {
		return operatorStatisticService;
	}
	public void setOperatorStatisticService(
			OperatorStatisticService operatorStatisticService) {
		this.operatorStatisticService = operatorStatisticService;
	}
	public boolean insertValue(String pocid) {
		// TODO Auto-generated method stub
		InfoSearch is = new InfoSearch();
		et.po.PoliceCallin pci = (et.po.PoliceCallin)dao.loadEntity(et.po.PoliceCallin.class, pocid);
		if (pci==null) {
			return false;
		}
		Object[] result = (Object[]) dao.findEntity(is.searchInfoByY(pocid));
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceinfoTemp pt = (PoliceinfoTemp)result[i];
			PoliceCallinInfo pcii = new PoliceCallinInfo();
			pcii.setId(pt.getId());
			pcii.setPoliceCallin(pci);
			pcii.setTagInfo(pt.getTagInfo());
			pcii.setQuInfo(pt.getQuInfo());
			pcii.setContent(pt.getContent());
			pcii.setRemark(pt.getRemark());
			dao.saveEntity(pcii);
			dao.removeEntity(pt);
		}
		return true;
	}
}