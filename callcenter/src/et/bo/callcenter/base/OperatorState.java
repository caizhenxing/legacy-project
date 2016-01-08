package et.bo.callcenter.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import et.bo.common.testing.MyLog;
/*
 * 座席员状态
 */
public class OperatorState {
	/*
	 * 座席员登陆ip，需要确定座席员。
	 */
	private String ip;
	/*
	 * 工号
	 */
	private String operatorNum;
	/*
	 * 座席员状态
	 */
	private String state;
	/*
	 * 状态时间
	 */
	private Date curTime;
	
	/*
	 * 座席员状态集合,主键是operatorNum,value is OperatorState.
	 */
	private static Map<String,OperatorState> operatorStateMap = new HashMap<String,OperatorState>();
	
	public final String SIGNIN="0";
	public final String SIGNOUT="1";
	public final String IN="2";
	public final String OUT="3";
	public final String LOG_ON="4";
	public final String LOG_OUT="5";	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public static Map getOperatorStateMap() {
		return operatorStateMap;
	}
	/*
	 * 通过ip得到相关的OperatorState。
	 */
	public static OperatorState getOperatorState(String ip){
		if(ip==null) return null;
		
		Iterator i =OperatorState.getOperatorStateMap().keySet().iterator();
		OperatorState os=null;
		while(i.hasNext()){
			String on=(String)i.next();
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("ip is:"+ip);
			System.out.println("operator num is:"+on);
			OperatorState os1=operatorStateMap.get(on);
			if(!ip.equals(os1.getIp()))continue;
			os=os1;
			break;
		}
		return os;
	}
	/*
	 * 设置座席员用户登录
	 */
	public static void setOperatorStateLogon(String user,String ip){
		if(ip==null)return;
		if(user==null)return;
		System.out.println("^^^^^^^^ip is"+ip);
		/*
		 * 是否是内线ip
		 */
		boolean bNotIn=true;
		
		for(int i=0;i<CardInfo.getInnerIpList().size();i++){
			String tmpIp=(String)CardInfo.getInnerIpList().get(i);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println(tmpIp);
			if(ip.equals(tmpIp)){
				bNotIn=false;
				break;
			}
		}
		
		if(bNotIn) return;
		/*
		 * set info;
		 */
		String port=null;
		for(int i=0;i<CardInfo.getInnerList().size();i++){
			CardInfo ci=(CardInfo)CardInfo.getInnerList().get(i);
			if(ip.equals(ci.getIp())){
				port=ci.getPhysicsPort();
				break;
			}
		}
		removeIp(ip);
		if(port==null)return;
		CardState cs =new CardState();
		cs.setCurTime(new Date());
		cs.setOperatorNum(user);
		cs.setPhysicsPort(port);
		cs.setState(cs.INNER_SIGNOUT);
		CardState.setInnerActiveMap(cs);
		OperatorState os=new OperatorState();
 		os.setOperatorNum(user);
 		os.setCurTime(new Date());
 		os.setIp(ip);
 		os.setState(os.LOG_ON);
 		OperatorState.getOperatorStateMap().put(user, os);
 		Iterator itTest=OperatorState.getOperatorStateMap().keySet().iterator();
		while(itTest.hasNext()){
			String sT=(String)itTest.next();
			System.out.println("***********xunhuan************");
			System.out.println(sT);
			OperatorState os1T=(OperatorState)OperatorState.getOperatorStateMap().get(sT);
			System.out.println(os1T.getIp());
		}
	}
	private static void removeIp(String ip){
		Iterator itTest=OperatorState.getOperatorStateMap().keySet().iterator();
		while(itTest.hasNext()){
			String sT=(String)itTest.next();
			System.out.println(sT);
			OperatorState os1T=(OperatorState)OperatorState.getOperatorStateMap().get(sT);
			String ipTmp=os1T.getIp();
			System.out.println("|||||||||||||||");
			System.out.println(ipTmp);
			if(ip.equals(ipTmp)){
				System.out.println(ipTmp+"  remove it");
//				OperatorState.getOperatorStateMap().remove(sT);
				itTest.remove();
			}
		}
	}
	public static List getOperatorStateIpList(){
		List<String> list=new ArrayList<String>();
		Iterator it=operatorStateMap.keySet().iterator();
		while(it.hasNext()){
			String on=(String)it.next();
			OperatorState os = operatorStateMap.get(on);
			System.out.println(")))))))))))))))))))))))))))))");
			os.getIp();
			System.out.println(")))))))))))))))))))))))))))))");
			list.add(os.getIp());
		}
		return list;
	}
	//------------------------------------------------------
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}	

	public Date getCurTime() {
		return curTime;
	}

	public void setCurTime(Date curTime) {
		this.curTime = curTime;
	}

}
