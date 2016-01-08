package et.bo.callcenter.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import et.bo.callcenter.ConstOperI;

public class CardState {
	//状态是由业务定义的，所以与业务有关。
	/*
	 * 物理端口:语音系统的物理端口号。key
	 */
	private String physicsPort;
	/*
	 * 电话
	 */
	private String phoneNum;
	/*
	 * 当前状态开始时间
	 */
	private Date curTime;
	/*
	 * 接通端口
	 */
	private String linkPort;
	/*
	 * 端口状态
	 */
	private String state;
	/*
	 * 座席工号
	 */
	private String operatorNum = ConstOperI.OPERATOR_NUM;
	/*
	 * 内外线端口状态，实际上与业务有点关系了。
	 */
	
	public final String INNER_SIGNIN="0";
	public final String INNER_SIGNOUT="1";
	public final String INNER_IN="2";
	public final String INNER_OUT="3";
	public final String INNER_RING="4";
	public final String INNER_CALLING="5";
	public final String INNER_NOBODY="6";
	
	public final String OUTER_BUSY="100";
	public final String OUTER_IDLE="101";

////	----------------------------------ooo----------------------------------------------------
//	/*
//	 * 此map在初始化的时候是在签入的时候。如果是三个座席，最多就是3个对象。，是active的。
//	 */
//	private static Map operatorIpMap=new HashMap();	//key is ip,value is OperatorState.
//	private static Map operatorInPortMap=new HashMap();//key is inPort,value is OperatorState.
//	//为了保证顺序。
//	private static List operatorList= new LinkedList();//entry is OperatorState.
//	//	----------------------------------ooo----------------------------------------------------

	private static CardStateHelper csh=new CardStateHelper();
	
	//all port,CardInfo init的时候，此类也init。
	private static Map portMap		= new HashMap();//key is physicsPort,value is CardState
	//座席登陆时候是知道ip的，根据ip的参数产生两个Map,即是
	private static Map innerActiveIpMap		= new HashMap();//key is ip,value is CardState
	private static Map innerActivePortMap	= new HashMap();//key is physicsPort,value is CardState
	private static List innerActiveList		= new LinkedList();//element is cardState
	private static Map<String,CardState> outerWaitingMap      = new HashMap<String,CardState>();//key is physicsPort ,value is CardState
	/*
	 * 构造方法1
	 */
	public CardState() {
    }
	/*
	 * 构造方法2
	 */
	public CardState(String port) {
		this.physicsPort=port;
    }
	//刷新
	public static void setInnerActiveMap(CardState cs){
		CardState.innerActivePortMap.put(cs.physicsPort, cs);
		CardInfo ci = CardInfo.getCardInfo(cs.physicsPort);
		String ip = ci.getIp();
		CardState.innerActiveIpMap.put(ip, cs);
		innerActiveList.add(cs);
	}
	
	//刷新
	public static void removeInnerActive(CardState cs){
		if(cs==null)return;
		CardInfo ci = CardInfo.getCardInfo(cs.physicsPort);
		String ip = ci.getIp();
		String port =ci.getPhysicsPort();
		innerActiveIpMap.remove(ip);
		innerActivePortMap.remove(port);
		System.err.println("^&^&*");
		System.err.println("system remove port:"+port);
		for (int i=0;i<innerActiveList.size();i++){
			CardState tmp=(CardState)innerActiveList.get(i);
			if(port.equals(tmp.getPhysicsPort()))innerActiveList.remove(tmp);
		}
	}
	
	//
	public static boolean isActiveInner(String port){
		return innerActivePortMap.containsKey(port);
	}
	/*
	 * 传送到座席的字符串
	 */
	public static List transToOperator(){
		return csh.transToOperator();
		
	}
	/*
	 * 传送到工控机的参数
	 */
	public static List transToCard(){
		return csh.transToCard();
	}
	/*
	 * 格式化参数到初始化
	 * physicsPort还在，
	 * state不能设置为空
	 */
	public void setNull(){
		this.curTime=null;
		this.linkPort=null;
		this.operatorNum=ConstOperI.OPERATOR_NUM;
		this.phoneNum=null;
		this.state=null;
//		this.physicsPort
//		this.state=null;
	}
	public static CardState getCardState(String port){
		return (CardState)portMap.get(port);
	}
	
	public String getLinkPort() {
		return linkPort;
	}
	public void setLinkPort(String linkPort) {
		this.linkPort = linkPort;
	}
	public String getOperatorNum() {
		return operatorNum;
	}
	public void setOperatorNum(String operatorNum) {
		this.operatorNum = operatorNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPhysicsPort() {
		return physicsPort;
	}
	public void setPhysicsPort(String physicsPort) {
		this.physicsPort = physicsPort;
	}
	public Date getCurTime() {
		return curTime;
	}
	public void setCurTime(Date curTime) {
		this.curTime = curTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public static Map getPortMap() {
		return portMap;
	}	
	public static Map getInnerActiveIpMap() {
		return innerActiveIpMap;
	}
	public static void setPortMap(Map portMap) {
		CardState.portMap = portMap;
	}
	public static List getInnerActiveList() {
		return innerActiveList;
	}
	public static Map getInnerActivePortMap() {
		return innerActivePortMap;
	}
	public static Map getOuterWaitingMap() {
		return outerWaitingMap;
	}
}
