package et.bo.police.callcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OperatorState {
	//----------------------------------ooo----------------------------------------------------
	/*
	 * ��map�ڳ�ʼ����ʱ������ǩ���ʱ�������������ϯ��������3�����󡣣���active�ġ�
	 */
	private static Map operatorIpMap=new HashMap();	//key is ip,value is OperatorState.
	private static Map operatorInPortMap=new HashMap();//key is inPort,value is OperatorState.
	//Ϊ�˱�֤˳��
	private static List operatorList= new LinkedList();//entry is OperatorState.
	//	----------------------------------ooo----------------------------------------------------
	/*
	 * ����3�����ϵ����ݶ���һ���ġ�ֻ������˳��ͬ��
	 */
	//������״̬,��ʱ��û��ȷ���Ǹ���ϯҪ���롣����û����ϯ���롣
	private static Map waitingMap=new HashMap();//key is inport ,value is OperatorState
	/*
	 * for ��ʽ���绰�������õĿո�
	 */
	private static String[] sBlank;
	/*
	 * ����˿�,�п���û����null��
	 */
	private String inPort;
	/*
	 * ��ϯ�˿�
	 */
	private String operatorPort;
	/*
	 * ��ϯ����
	 */
	private String operatorNum;
	/*
	 * ��ϯip
	 */
	private String ip;
	/*
	 * ��ǰʱ��
	 */
	private Date curTime;
	/*
	 * ǩ��ǩ��״̬
	 */
	private String ioState;
	/*
	 * ����״̬
	 */
	private String workState;
	/*
	 * ���к���
	 */
	private String phoneNum;
	/*
	 * �ֻ�����
	 */
	private String ext;
	public Date getCurTime() {
		return curTime;
	}

	public void setCurTime(Date curTime) {
		this.curTime = curTime;
	}

	public String getIoState() {
		return ioState;
	}

	public void setIoState(String ioState) {
		this.ioState = ioState;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperatorNum() {
		return operatorNum;
	}

	public void setOperatorNum(String operatorNum) {
		this.operatorNum = operatorNum;
	}

	public String getOperatorPort() {
		return operatorPort;
	}

	public void setOperatorPort(String operatorPort) {
		this.operatorPort = operatorPort;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public OperatorState() {
		// TODO Auto-generated constructor stub
		
	}
	/**
	 * ���͵���ϯ�����
	 * @param args
	 */

	public static String transToOperator(){
		StringBuffer sb=new StringBuffer();
		//first �Ǽ�����ϯ��״̬
		for(int i=0;i<operatorList.size();i++){
			OperatorState os=(OperatorState)operatorList.get(i);
			sb.append(toOperatorStr(os));
		}
		//second �Ǽ���������״̬,��ʱ��û��ȷ���Ǹ���ϯҪ���롣����û����ϯ�˿ڡ�
		Iterator i =waitingMap.entrySet().iterator();
		while(i.hasNext()){
			OperatorState os=(OperatorState)i.next();
			sb.append(toOperatorStr(os));
		}
		return sb.toString();
	}
	/*
	 * ��ʽ���ɴ���һ����ϯ���ַ���
	 */
	private static String toOperatorStr(OperatorState os){
		StringBuffer sb=new StringBuffer();
		sb.append(os.getOperatorNum());
			sb.append(OperatorI.DELIM1);
		sb.append(os.getWorkState());
			sb.append(OperatorI.DELIM1);
		sb.append(formatedPhoneNum(os.getPhoneNum()));
			sb.append(OperatorI.DELIM2);
		return sb.toString();
	}
	//���뵽���ػ�������Ϣ��list��Ԫ��Ϊobject[],׼����ʽ���Ĳ�����
	public static List transToCard(){
		List list = new ArrayList();
		for(int i=0;i<operatorList.size();i++){
			OperatorState os=(OperatorState)operatorList.get(i);
			String isHere =
				OperatorI.WORK_LEAVE.equals(os.getWorkState())?
						OperatorI.WORK_LEAVE:OperatorI.WORK_IN;
			Object[] o={os.getOperatorPort(),isHere};
			list.add(o);
		}
		return list;
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<3;i++){
			OperatorState os = new OperatorState();
			os.setOperatorNum(String.valueOf(i));
			os.setWorkState(String.valueOf(i));
			System.out.println(os.getWorkState());
//			OperatorState.getOperatorStateMap().put(i, os);
//			OperatorState.getOperatorStateList().add(os);
//			
//			OperatorState os1=(OperatorState)OperatorState.getOperatorStateMap().get(i);
//			os1.setWorkState("hh"+String.valueOf(i));
//			System.out.println(os1.getWorkState());
//			OperatorState os2=(OperatorState)OperatorState.getOperatorStateList().get(i);
//			System.out.println(os2.getWorkState());
//			System.out.println("--------");
		}		
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
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
		if (null!=sBlank) OperatorState.initBlank();
		if(null==phoneNum) return sBlank[OperatorI.PHONE_NUM_FORMAT_LEN];
		int len=phoneNum.length();
		StringBuffer sb = new StringBuffer(phoneNum);
		sb.append(sBlank[OperatorI.PHONE_NUM_FORMAT_LEN-len]);
		return sb.toString();
	}

}
