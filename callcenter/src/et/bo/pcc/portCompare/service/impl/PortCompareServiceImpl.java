package et.bo.pcc.portCompare.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.pcc.callinFirewall.service.impl.CallinFirewallHelp;
import et.bo.pcc.portCompare.service.PortCompareService;
import et.po.PoliceCallinFirewall;
import et.po.PortCompare;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;

public class PortCompareServiceImpl implements PortCompareService{

	static Logger log = Logger.getLogger(PortCompareServiceImpl.class.getName());
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	private ClassTreeService cts;
	
	private int num = 0;
	
	public static HashMap hashmap = new HashMap();
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public HashMap getIpByPort() {
		// TODO Auto-generated method stub
		if(hashmap.isEmpty()){
			PortCompareHelp pch = new PortCompareHelp();
			Object[] result = (Object[])dao.findEntity(pch.portAndIpQuery());
//			log.error("port num is :"+result.length);
			for(int i=0,size=result.length;i<size;i++){
				PortCompare pc = (PortCompare)result[i];
				hashmap.put(pc.getPhysicsPort(), pc.getIp());	
			}		
		}	
		return hashmap;
	}
	
	public HashMap flushGetIpByPort() {
		// TODO Auto-generated method stub	
			PortCompareHelp pch = new PortCompareHelp();
			Object[] result = (Object[])dao.findEntity(pch.portAndIpQuery());
//			log.error("port num is :"+result.length);
//			System.out.println("flush ************************");
			for(int i=0,size=result.length;i<size;i++){
				PortCompare pc = (PortCompare)result[i];
				hashmap.put(pc.getPhysicsPort(), pc.getIp());	
			}			
		return hashmap;
	}
	
	public void addPortCompare(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createPortCompare(dto));
	}

	private PortCompare createPortCompare(IBaseDTO dto){
		PortCompare pc = new PortCompare();
		pc.setId(ks.getNext("Port_Compare"));
		pc.setPhysicsPort(dto.get("physicsPort").toString());
		pc.setLogicPort(dto.get("logicPort").toString());
		pc.setIp(dto.get("ip").toString());
		pc.setAddDate(TimeUtil.getNowTime());
		return pc;
	}
	
	public void delPortCompare(String id) {
		// TODO Auto-generated method stub
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, id);
		dao.removeEntity(pc);
	}

	public IBaseDTO getPortCompareInfo(String id) {
		// TODO Auto-generated method stub
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", pc.getId());
		dto.set("physicsPort", pc.getPhysicsPort());
		dto.set("logicPort", pc.getLogicPort());
		dto.set("ip", pc.getIp());
		return dto;
	}

	public int getPortCompareSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean isHaveSamePort(String port) {
		// TODO Auto-generated method stub
		PortCompareHelp pch = new PortCompareHelp();
		int i=dao.findEntitySize(pch.isHaveSamePort(port));
		if(i!=0){
			return true;
		}else{
			return false;
		}
		
	}

	public List portCompareQuery() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PortCompareHelp pch = new PortCompareHelp();
		Object[] result = (Object[]) dao.findEntity(pch.portCompareQuery());
		num = dao.findEntitySize(pch.portCompareQuery());
		for (int i = 0, size = result.length; i < size; i++) {
			PortCompare pc = (PortCompare) result[i];
			list.add(protCompareToDynaBeanDTO(pc));
		}
		return list;
	}
	
	private DynaBeanDTO protCompareToDynaBeanDTO(PortCompare pc){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pc.getId());
		dbd.set("physicsPort", pc.getPhysicsPort());
		dbd.set("logicPort", pc.getLogicPort());
		dbd.set("ip", pc.getIp());
		long b = System.currentTimeMillis();
		Map map = CardInfo.getCardMap();
		CardInfo cardInfo = null;
		if(map.containsKey(pc.getPhysicsPort())){
			cardInfo =(CardInfo)map.get(pc.getPhysicsPort());
			if(cardInfo.getPortType()==null){
				dbd.set("portType", "");
			}else if(cardInfo.getPortType().equals("0")){
				dbd.set("portType", cts.getvaluebyNickName("portType_in"));
				dbd.set("PortInOrOut", "0");
			}else if(cardInfo.getPortType().equals("1")){
				dbd.set("portType", cts.getvaluebyNickName("portType_out"));
				dbd.set("PortInOrOut", "1");
			}else{
				dbd.set("portType", "");
			}
			
			if(cardInfo.getState()==null){
				dbd.set("state", "");
			}else if(cardInfo.getState().equals("0")){
				dbd.set("state", cts.getvaluebyNickName("portState_unInstall"));
			}else if(cardInfo.getState().equals("1")){
				dbd.set("state", cts.getvaluebyNickName("portState_damage"));
			}else if(cardInfo.getState().equals("2")){
				dbd.set("state", cts.getvaluebyNickName("portState_close"));
			}else if(cardInfo.getState().equals("3")){
				dbd.set("state", cts.getvaluebyNickName("portState_normal"));
			}else{
				dbd.set("state", "");
			}		
		}else{
			dbd.set("portType", "");
//			dbd.set("state", cts.getvaluebyNickName("portState_close"));
			dbd.set("state", "");
		}
//		System.out.println("Ê±¼ä");
//		System.out.println(System.currentTimeMillis()-b);
//		Iterator it = CardInfo.getCardMap().entrySet().iterator();
//		while(it.hasNext()){
//			CardInfo ci = (CardInfo)it.next();
//			if(pc.getLogicPort().equals(ci.getLogicPort())){
//				cardInfo=ci;
//				break;
//			}
//		}
//		if(cardInfo==null){
//			dbd.set("portType", "");
//			dbd.set("state", "");
//		}else{
//			dbd.set("portType", cardInfo.getPortType());
//			dbd.set("state", cardInfo.getState());
//		}
		return dbd;
	}

	public boolean updatePortCompare(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PortCompareHelp pch = new PortCompareHelp();
	    int i=0;
	    System.out.println(i);
		i = dao.findEntitySize(pch.HaveSameIp(dto.get("physicsPort").toString(), dto.get("ip").toString()));
		System.out.println(i);
		if(i!=0){
			return true;
		}
		if(hashmap.containsKey(dto.get("physicsPort").toString())){
			System.out.println(dto.get("physicsPort").toString());
			log.info("--------------------------------");
			String ip = (String)hashmap.get(dto.get("physicsPort").toString());
			log.info("**************************");
//			pc.setIp(dto.get("ip").toString());
			hashmap.put(dto.get("physicsPort").toString(), ip);
		}
		dao.saveEntity(modifyPortCompare(dto));
		return false;
	}
	
	private PortCompare modifyPortCompare(IBaseDTO dto){
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, dto.get("id").toString());
//		pc.setId(dto.get("id").toString());
		pc.setPhysicsPort(dto.get("physicsPort").toString());
		pc.setLogicPort(dto.get("logicPort").toString());
		pc.setIp(dto.get("ip").toString());
		return pc;
	}
	public boolean isHaveSameIp(String ip) {
		// TODO Auto-generated method stub
		PortCompareHelp pch = new PortCompareHelp();
		int i = dao.findEntitySize(pch.isHaveSameIp(ip));
		if(i!=0){
			return true;
		}else{
			return false;
		}
	}
	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}
