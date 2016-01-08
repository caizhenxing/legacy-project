package et.bo.callcenter.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.business.BusinessObject;

public class CardInfo {
	private static Log log = LogFactory.getLog(CardInfo.class);
	/*
	 * 物理端口:语音系统的物理端口号。
	 */
	private String physicsPort;
	/*
	 * 逻辑端口:语音系统的逻辑端口。
	 */
	private String logicPort;
	/*
	 * 端口类型:端口的类型。0-内线端口；1-外线端口。
	 */
	private String portType;
	/*
	 * 状态,该端口的当前状态。0-未安装；1-损坏；2-关闭；3-正常。
	 */
	private String state;
	/*
	 * ip映射,对于外线端口，应该映射成工控机，对于内线接口，应该映射成座席员端口。
	 */
	private String ip;
	//-------------注入
	private static BusinessObject bo=null;
	
	//卡信息类的集合。通过物理端口迅速的找到相关的东东。在ｃａｒｄＩｎｆｏ初始化的时候就有了。
	private static Map cardMap = new HashMap();// key is physicsPort,value is CardInfo
	
	private static List innerList = new ArrayList();//即卡上的所有内线的CardInfo的集合。
	private static List innerIpList = new ArrayList();//即卡上的所有内线的ip的集合。作为批量发送信息给座席用。
	private static List innerPortList = new ArrayList();//即卡上的所有内线的physicsPort的集合。作为给工控机器传送信息用。
	//---------------------------------------------
	public final String PORT_TYPE_INNER="0";//内线
	public final String PORT_TYPE_OUTER="1";//外线
	
	public final String STATE_UNINSTALL="0";//未安装
	public final String STATE_ERROR="1";//损坏
	public final String STATE_CLOSE="2";//关闭
	public final String STATE_WELL="3";//正常
	//	---------------------------------------------	
	/*
	 * list之中的每一个元素是：CardInfo，ｌｉｓｔｓｉｚｅ是所有的卡
	 * 此时没有ｉｐ
	 * 初始化,从工控机和数据库（ip）取出信息
	 */
	
	public static void initMap(List list){
		
		if(list==null)return;
		cardMap =new HashMap();
		innerList= new ArrayList();
		Map tmpMap = new HashMap();
		log.debug("list.size() is:"+list.size());
		for(int i=0;i<list.size();i++){
			
			CardInfo ci = (CardInfo)list.get(i);
			String port=ci.getPhysicsPort();
			
			ci.setIp(bo.getPortLinkedIp(port));
			
			cardMap.put(ci.getPhysicsPort(), ci);
			//
			tmpMap.put(port, new CardState(port));
			
			//
			if(ci.PORT_TYPE_INNER.equals(ci.getPortType())){
				
				if(ci.STATE_UNINSTALL.equals(ci.getState()))continue;
				if(ci.STATE_ERROR.equals(ci.getState()))continue;
//				if(ci.STATE_ERROR.equals(ci.getState()))continue;
				innerList.add(ci);
			}
			
		}
		if(CardState.getPortMap().size()!=0)return;
//		设置
		CardState.setPortMap(tmpMap);
		//
		log.debug("set ok!");
		/*
		 * add for test
		 */
		CardInfo.setInnerList();
//		OperatorState.setOperatorStateLogon("001", "192.168.1.3");
	}
	public static CardInfo getCardInfo(String physicsPort){
		return (CardInfo)cardMap.get(physicsPort);
	}
	private static void setInnerList(){
		innerIpList=new ArrayList();
		innerPortList = new ArrayList();
		for(int i=0;i<innerList.size();i++){
			CardInfo ci = (CardInfo)innerList.get(i);
			innerIpList.add(ci.getIp());
			innerPortList.add(ci.getPhysicsPort());
		}
	}
	public static List getInnerIpList(){
		if(innerIpList.size()==0) CardInfo.setInnerList();
		return innerIpList;
	}
	public static List getInnerPortList(){
		if(innerPortList.size()==0) CardInfo.setInnerList();
		return innerPortList;
	}
	//-------------------------------------------------
	public String getLogicPort() {
		return logicPort;
	}
	public void setLogicPort(String logicPort) {
		this.logicPort = logicPort;
	}
	public String getPhysicsPort() {
		return physicsPort;
	}
	public void setPhysicsPort(String physicsPort) {
		this.physicsPort = physicsPort;
	}
	public String getPortType() {
		return portType;
	}
	public void setPortType(String portType) {
		this.portType = portType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setBo(BusinessObject bo) {
		this.bo = bo;
	}
	public static Map getCardMap() {
		return cardMap;
	}
	public static List getInnerList() {
		return innerList;
	}

}
