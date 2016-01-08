package et.bo.police.callcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private String operatorNum;
	/*
	 * 内外线端口状态，实际上与业务有点关系了。
	 */
	
	public final String INNER_SIGNIN="0";
	public final String INNER_SIGNOUT="1";
	public final String INNER_IN="2";
	public final String INNER_OUT="3";
	public final String INNER_RING="4";
	public final String INNER_CALLING="5";
	
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
//	
	
	//座席登陆时候是知道ip的，根据ip的参数产生两个Map,即是
	private static Map innerActiveIpMap		= new HashMap();
	private static Map innerActivePortMap	= new HashMap();
	private static List innerActiveList		= new LinkedList();
	private static Map outerWaitingMap=new HashMap();//key is physicsPort ,value is CardState
	/*
	 * for 格式化电话号码所用的空格
	 */
	private static String[] sBlank;
	
	//刷新
	public static void setInnerActiveMap(CardState cs){
		CardState.innerActiveIpMap.put(cs.physicsPort, cs);
		CardInfo ci = CardInfo.getCardInfo(cs.physicsPort);
		String ip = ci.getIp();
		CardState.innerActivePortMap.put(ip, cs);
	}
	//
	private static boolean isActiveInner(String port){
		return innerActivePortMap.containsKey(port);
	}
	//
//	private static CardState getCardState(String port){
//		return (CardState)CardState.innerActivePortMap.get(port);
//	}
	/*
	 * 初始化空格
	 */
	private static void initBlank(){		
		sBlank = new String[OperatorI.PHONE_NUM_FORMAT_LEN];
		sBlank[0] ="";
		for(int i=1;i<=OperatorI.PHONE_NUM_FORMAT_LEN;i++){
			StringBuffer sb=new StringBuffer("");
			for(int j=0;j<i;j++){
				sb.append(OperatorI.BLANK);
			}
			sBlank[i] = sb.toString();
		}
	}
	/*
	 * 格式化成15位的电话号码
	 */
	private static String formatedPhoneNum(String phoneNum){
		
		if (null!=sBlank) CardState.initBlank();
		if(null==phoneNum) return sBlank[OperatorI.PHONE_NUM_FORMAT_LEN];
		int len=phoneNum.length();
		StringBuffer sb = new StringBuffer(phoneNum);
		sb.append(sBlank[OperatorI.PHONE_NUM_FORMAT_LEN-len]);
		return sb.toString();		
	}
	/*
	 * 格式化成传回一个座席的字符串
	 * 
	 */
	private static String formatOperatorStr(CardState cs){
		StringBuffer sb=new StringBuffer();
		sb.append(cs.getOperatorNum());
			sb.append(OperatorI.DELIM1);
		sb.append(cs.getState());
			sb.append(OperatorI.DELIM1);
		sb.append(formatedPhoneNum(cs.getPhoneNum()));
			sb.append(OperatorI.DELIM2);
		return sb.toString();
	}
	/*
	 * 传送到座席的字符串，多个串
	 */
	public static String transToOperator(){
		StringBuffer sb=new StringBuffer();
//		first 是几个座席的状态
//		for(int i =0;i<innerActiveList.size();i++){
//			toOperatorStr((CardState)innerActiveList.get(i));
//		}
		for(int i =0;i<CardInfo.getInnerPortList().size();i++){
			String port = (String)CardInfo.getInnerPortList().get(i);
			if(!CardState.isActiveInner(port)){
				continue;
			}else{
				formatOperatorStr(
						(CardState)CardState.innerActivePortMap.get(port)
						);
			}			
		}
//		second 是几个待机的状态,此时还没有确定那个座席要接入。所以没有座席端口。
		Iterator i =outerWaitingMap.entrySet().iterator();
		while(i.hasNext()){
			CardState cs=(CardState)i.next();
			sb.append(formatOperatorStr(cs));
		}
		return sb.toString();
	}
	/*
	 * 
	 */
	public static List transToCard(){
		List list = new ArrayList();
		for(int i=0;i<CardInfo.getInnerPortList().size();i++){
			String port = (String)CardInfo.getInnerPortList().get(i);
			String isHere = CardState.isActiveInner(port)?
					OperatorI.WORK_IN:OperatorI.WORK_LEAVE;
			String[] o={port,isHere};
			list.add(o);			
		}
		return list;
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
}
