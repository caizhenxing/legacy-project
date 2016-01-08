package et.bo.callcenter.portCompare.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import et.bo.callcenter.portCompare.service.PortCompareService;
import et.po.PortCompare;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class PortCompareImpl implements PortCompareService{

	static Logger log = Logger.getLogger(PortCompareImpl.class.getName());
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	private ClassTreeService cts;
	
	private int num = 0;
	
	public static HashMap hashmap = new HashMap();
	
	public static HashMap innerPortMap = new HashMap();
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * 根据IP得到端口
	 */
	public HashMap getIpByPort() {
		// TODO Auto-generated method stub
		if(hashmap.isEmpty()){
			PortCompareHelp pch = new PortCompareHelp();
			Object[] result = (Object[])dao.findEntity(pch.portAndIpQuery());
//			log.error("port num is :"+result.length);
			for(int i=0,size=result.length;i<size;i++){
				PortCompare pc = (PortCompare)result[i];
				hashmap.put(pc.getExtensionNum(), pc.getIp());	
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
				hashmap.put(pc.getExtensionNum(), pc.getIp());	
			}			
		return hashmap;
	}
	
	public void addPortCompare(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createPortCompare(dto));
	}

	private PortCompare createPortCompare(IBaseDTO dto){
		PortCompare pc = new PortCompare();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		pc.setId(ks.getNext("Port_Compare"));
		pc.setExtensionNum(dto.get("seatNum").toString());
		pc.setLogicPort(dto.get("logicPort").toString());
		pc.setIp(dto.get("ip").toString());

		Calendar cal = Calendar.getInstance();
		pc.setAddDate(sdf.format(cal.getTime()));
		return pc;
	}
	
	public void delPortCompare(String id) {
		// TODO Auto-generated method stub
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, id);
		dao.removeEntity(pc);
	}

	/**
	 * 得到某个端口详细信息
	 */
	public IBaseDTO getPortCompareInfo(String id) {
		// TODO Auto-generated method stub
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", pc.getId());
		dto.set("seatNum", pc.getExtensionNum());
		dto.set("logicPort", pc.getLogicPort());
		dto.set("ip", pc.getIp());
		return dto;
	}

	public int getPortCompareSize() {
		// TODO Auto-generated method stub
		return num;
	}

	/**
	 * 查询端口是否已经存在
	 */
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

	/**
	 * 查询端口对照信息列表
	 */
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
		dbd.set("seatNum", pc.getExtensionNum());
		dbd.set("logicPort", pc.getLogicPort());
		dbd.set("ip", pc.getIp());
		long b = System.currentTimeMillis();
		Map map = new HashMap();
		try {
			//map = cis.getPortInfo();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
//		CardInfo cardInfo = null;
		if(map.containsKey(pc.getExtensionNum())){
			String state =(String)map.get(pc.getExtensionNum());
			if(state.equals("0")){
//				dbd.set("portType", cts.getvaluebyNickName("portType_in"));
				dbd.set("PortInOrOut", "0");
			}else if(state.equals("1")){
//				dbd.set("portType", cts.getvaluebyNickName("portType_out"));
				dbd.set("PortInOrOut", "1");
			}else{
				dbd.set("portType", "");
			}
			
//			if(cardInfo.getState()==null){
//				dbd.set("state", "");
//			}else if(cardInfo.getState().equals("0")){
//				dbd.set("state", cts.getvaluebyNickName("portState_unInstall"));
//			}else if(cardInfo.getState().equals("1")){
//				dbd.set("state", cts.getvaluebyNickName("portState_damage"));
//			}else if(cardInfo.getState().equals("2")){
//				dbd.set("state", cts.getvaluebyNickName("portState_close"));
//			}else if(cardInfo.getState().equals("3")){
//				dbd.set("state", cts.getvaluebyNickName("portState_normal"));
//			}else{
//				dbd.set("state", "");
//			}		
		}else{
			dbd.set("portType", "");
//			dbd.set("state", cts.getvaluebyNickName("portState_close"));
			dbd.set("state", "");
		}
//		System.out.println("时间");
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

	/**
	 * 修改端口对照信息
	 */
	public boolean updatePortCompare(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PortCompareHelp pch = new PortCompareHelp();
	    int i=0;
	    System.out.println(i);
		i = dao.findEntitySize(pch.HaveSameIp(dto.get("seatNum").toString(), dto.get("ip").toString()));
		System.out.println(i);
		if(i!=0){
			return true;
		}
		if(hashmap.containsKey(dto.get("seatNum").toString())){
			System.out.println(dto.get("seatNum").toString());
			log.info("--------------------------------");
			String ip = (String)hashmap.get(dto.get("seatNum").toString());
			log.info("**************************");
//			pc.setIp(dto.get("ip").toString());
			hashmap.put(dto.get("seatNum").toString(), ip);
		}
		dao.saveEntity(modifyPortCompare(dto));
		return false;
	}
	
	private PortCompare modifyPortCompare(IBaseDTO dto){
		PortCompare pc = (PortCompare)dao.loadEntity(PortCompare.class, dto.get("id").toString());
//		pc.setId(dto.get("id").toString());
		pc.setExtensionNum(dto.get("seatNum").toString());
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
	
	public String getIpByPort(String port){
		if(innerPortMap.isEmpty()||innerPortMap==null){getIpByPort();}
		if(innerPortMap.containsKey(port)){
			String ip = innerPortMap.get(port).toString();
			return ip;
		}else{
			return "";
		}
	}
	
	public String getPortByIp(String ip){
		if(innerPortMap.isEmpty()||innerPortMap==null){getUserPortMap();}
			Iterator it = innerPortMap.keySet().iterator();
			boolean flag = false;
			String port = "";
			while(it.hasNext()){
				String key = (String)it.next();
				if(ip.equals(innerPortMap.get(key).toString())){
					port = key;
					break;
				}
			}
			return port;
	}
	
	public HashMap getInnerPortMap(){
		if(innerPortMap==null||innerPortMap.isEmpty())getUserPortMap();
		return innerPortMap;
	}
	
	private HashMap getUserPortMap(){
			PortCompareHelp pch = new PortCompareHelp();
			Object[] result = (Object[])dao.findEntity(pch.getInnerPort());
//			log.error("port num is :"+result.length);
			for(int i=0,size=result.length;i<size;i++){
				PortCompare pc = (PortCompare)result[i];
				innerPortMap.put(pc.getExtensionNum(), pc.getIp());	
			}
			return innerPortMap;
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