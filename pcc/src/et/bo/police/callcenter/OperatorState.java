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
	 * 此map在初始化的时候是在签入的时候。如果是三个座席，最多就是3个对象。，是active的。
	 */
	private static Map operatorIpMap=new HashMap();	//key is ip,value is OperatorState.
	private static Map operatorInPortMap=new HashMap();//key is inPort,value is OperatorState.
	//为了保证顺序。
	private static List operatorList= new LinkedList();//entry is OperatorState.
	//	----------------------------------ooo----------------------------------------------------
	/*
	 * 以上3个集合的内容都是一样的。只是搜索顺序不同。
	 */
	//待机的状态,此时还没有确定那个座席要接入。所以没有座席号码。
	private static Map waitingMap=new HashMap();//key is inport ,value is OperatorState
	/*
	 * for 格式化电话号码所用的空格
	 */
	private static String[] sBlank;
	/*
	 * 呼入端口,有可能没有是null。
	 */
	private String inPort;
	/*
	 * 座席端口
	 */
	private String operatorPort;
	/*
	 * 座席工号
	 */
	private String operatorNum;
	/*
	 * 座席ip
	 */
	private String ip;
	/*
	 * 当前时间
	 */
	private Date curTime;
	/*
	 * 签入签出状态
	 */
	private String ioState;
	/*
	 * 工作状态
	 */
	private String workState;
	/*
	 * 主叫号码
	 */
	private String phoneNum;
	/*
	 * 分机号码
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
	 * 传送到座席的命令串
	 * @param args
	 */

	public static String transToOperator(){
		StringBuffer sb=new StringBuffer();
		//first 是几个座席的状态
		for(int i=0;i<operatorList.size();i++){
			OperatorState os=(OperatorState)operatorList.get(i);
			sb.append(toOperatorStr(os));
		}
		//second 是几个待机的状态,此时还没有确定那个座席要接入。所以没有座席端口。
		Iterator i =waitingMap.entrySet().iterator();
		while(i.hasNext()){
			OperatorState os=(OperatorState)i.next();
			sb.append(toOperatorStr(os));
		}
		return sb.toString();
	}
	/*
	 * 格式化成传回一个座席的字符串
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
	//传入到工控机器的信息，list。元素为object[],准备格式化的参数。
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
		if (null!=sBlank) OperatorState.initBlank();
		if(null==phoneNum) return sBlank[OperatorI.PHONE_NUM_FORMAT_LEN];
		int len=phoneNum.length();
		StringBuffer sb = new StringBuffer(phoneNum);
		sb.append(sBlank[OperatorI.PHONE_NUM_FORMAT_LEN-len]);
		return sb.toString();
	}

}
