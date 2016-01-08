package et.bo.police.callcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardInfo {
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
	//卡信息类的集合。通过物理端口迅速的找到相关的东东。
	private static Map cardMap = new HashMap();// key is physicsPort,value is CardInfo

	private static List innerPortList = new ArrayList();//即卡上的所有内线的物理端口的顺序集合，element is CardInfo的physicsPort，即本类的物理端口。
	
	//---------------------------------------------
	public final String PORT_TYPE_INNER="0";//内线
	public final String PORT_TYPE_OUTER="1";//外线
	
	public final String STATE_UNINSTALL="0";//未安装
	public final String STATE_ERROR="1";//损坏
	public final String STATE_CLOSE="2";//关闭
	public final String STATE_WELL="3";//正常
	//	---------------------------------------------
	
	//	初始化,从工控机和数据库（ip）取出信息
	private void init(){
		// to do sthing
		
		
	}
	
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
	
	public static Map getCardMap() {
		return cardMap;
	}
	public static CardInfo getCardInfo(String physicsPort){
		return (CardInfo)cardMap.get(physicsPort);
	}

	public static List getInnerPortList() {
		return innerPortList;
	}

}
