package et.bo.callcenter.serversocket.checklogin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SigninState {	
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
	private static Map<String, SigninState> signinStateMap = new HashMap<String, SigninState>();

	public static String NO_EXIST = "NO_EXIST";

	public static String EXIST = "EXIST";
	
	public static Map getSigninStateMap() {
		return signinStateMap;
	}
	
	/**
	 * 座席号是否存在
	 * @param operatorNum 座席号
	 * @return
	 */
	private static String ifOperatorNumExist(String operatorNum) {
		Iterator it = signinStateMap.keySet().iterator();
		String return_str = NO_EXIST;
		while (it.hasNext()) {
			String ip = (String) it.next();
			SigninState ss = (SigninState) signinStateMap.get(ip);
			if (ss.getOperatorNum().equals(operatorNum)) {
				if(ss.getIp()==null){
					break;
				}
				return_str = ss.getIp();
				break;
			}
		}
		return return_str;
	}
	
	/*
	 * 设置座席员用户登录
	 */
	public static void setOperatorStateLogin(String operatorNum, String ip){
		if (ip == null)  return;
		if (operatorNum == null)return;
		String return_str = ifOperatorNumExist(operatorNum);
		if(!return_str.equals("NO_EXIST"))signinStateMap.remove(return_str);
		SigninState ss = new SigninState();
		ss.setOperatorNum(operatorNum);
		ss.setIp(ip);
		signinStateMap.put(ip, ss);
		//System.out.println("签入的座席员总计：  "+signinStateMap.size()+"人");
		Iterator it = signinStateMap.keySet().iterator();
		while (it.hasNext()) {
			String opip = (String) it.next();
			SigninState signs = (SigninState) signinStateMap.get(opip);
			//System.out.println("座席员  "+signs.getOperatorNum()+"   ip  "+signs.getIp());
			}
		
	}
	
	/*
	 * 设置座席员用户登录
	 */
	public static void removeOperator(String ip){
		if (ip == null)  return;
		if(signinStateMap.containsKey(ip)){
			signinStateMap.remove(ip);
		}
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
