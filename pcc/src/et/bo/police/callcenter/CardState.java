package et.bo.police.callcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CardState {
	//״̬����ҵ����ģ�������ҵ���йء�
	/*
	 * ����˿�:����ϵͳ������˿ںš�key
	 */
	private String physicsPort;
	/*
	 * �绰
	 */
	private String phoneNum;
	/*
	 * ��ǰ״̬��ʼʱ��
	 */
	private Date curTime;
	/*
	 * ��ͨ�˿�
	 */
	private String linkPort;
	/*
	 * �˿�״̬
	 */
	private String state;
	/*
	 * ��ϯ����
	 */
	private String operatorNum;
	/*
	 * �����߶˿�״̬��ʵ������ҵ���е��ϵ�ˡ�
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
//	 * ��map�ڳ�ʼ����ʱ������ǩ���ʱ�������������ϯ��������3�����󡣣���active�ġ�
//	 */
//	private static Map operatorIpMap=new HashMap();	//key is ip,value is OperatorState.
//	private static Map operatorInPortMap=new HashMap();//key is inPort,value is OperatorState.
//	//Ϊ�˱�֤˳��
//	private static List operatorList= new LinkedList();//entry is OperatorState.
//	//	----------------------------------ooo----------------------------------------------------
//	
	
	//��ϯ��½ʱ����֪��ip�ģ�����ip�Ĳ�����������Map,����
	private static Map innerActiveIpMap		= new HashMap();
	private static Map innerActivePortMap	= new HashMap();
	private static List innerActiveList		= new LinkedList();
	private static Map outerWaitingMap=new HashMap();//key is physicsPort ,value is CardState
	/*
	 * for ��ʽ���绰�������õĿո�
	 */
	private static String[] sBlank;
	
	//ˢ��
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
	 * ��ʼ���ո�
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
	 * ��ʽ����15λ�ĵ绰����
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
	 * ��ʽ���ɴ���һ����ϯ���ַ���
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
	 * ���͵���ϯ���ַ����������
	 */
	public static String transToOperator(){
		StringBuffer sb=new StringBuffer();
//		first �Ǽ�����ϯ��״̬
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
//		second �Ǽ���������״̬,��ʱ��û��ȷ���Ǹ���ϯҪ���롣����û����ϯ�˿ڡ�
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
