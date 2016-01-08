package et.bo.callcenter.serversocket.checklogin.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.portCompare.service.PortCompareService;
import et.bo.callcenter.serversocket.checklogin.LoginMapService;
import et.bo.callcenter.serversocket.iconst.ConstRuleI;
import et.bo.common.mylog.MyLogClient;

public class LoginMapServiceImpl implements LoginMapService {

	String SUCCESS = "0";

	String ERROR = "1";

	private static Log log = LogFactory.getLog(LoginMapServiceImpl.class);

	private PortCompareService portCompareService = null;

	// key is ip value is operator,key的值是座席员的ip号,在用户登陆的时候用
	private static Map<String, LoginUser> LoginMap = new HashMap<String, LoginUser>();

	// key is ip value is operator,key的值是座席员号,在用户签入的时候用
	public static Map<String, LoginUser> InUserMap = new HashMap<String, LoginUser>();

	public Map getLoginMap() {
		return LoginMap;
	}

	public String setLoginUer(String ip, String user, String skill) {
		if (ip == null)
			return ERROR;
		if (user == null)
			return ERROR;
		// checkIp(ip);
		// LoginMap为空时
		// LoginMap.clear();
		if (LoginMap.isEmpty() || LoginMap == null) {
		} else {
			// ip存在时
			if (LoginMap.containsKey(ip)) {
				LoginMap.remove(ip);
				// server.command(ip, "OUTTIM:1;");
				// String return_key =
				// StaticMapHelp.loginMapHaveUser(LoginMap,user);
				// if(!(return_key==null||return_key.equals("noExist"))){
				// ecs.setPortFlag(return_key, false);
				// server.command(return_key, "OUTTIM:0;");
				// LoginMap.remove(return_key);
				// }

			} else {
				// String return_key =
				// StaticMapHelp.loginMapHaveUser(LoginMap,user);
				// System.out.println("return_key is " + return_key);
				// if(!(return_key==null||return_key.equals("noExist"))){
				// ecs.setPortFlag(return_key, false);
				// server.command(return_key, "OUTTIM:0;");
				// LoginMap.remove(return_key);
				// }
			}
		}
		// 把用户名密码加入到LoginMap中
		LoginUser lu = new LoginUser();
		lu.setIp(ip);
		MyLogClient.info("看看loginMap里面加上値没有......." + getPortByIp(ip));
		lu.setPort(getPortByIp(ip));
		lu.setUser(user);
		lu.setSkill(skill);
		LoginMap.put(ip, lu);

		Iterator it = LoginMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			LoginUser luu = (LoginUser) LoginMap.get(key);
			log.info(" 座席 " + luu.getUser() + " 端口 " + luu.getPort() + " ip  "
					+ luu.getIp());
		}
		return SUCCESS;
	}

	public String checkIp(String ip) {
		Map map = portCompareService.getInnerPortMap();
		Iterator it = map.keySet().iterator();
		boolean flag = false;
		while (it.hasNext()) {
			String key = (String) it.next();

			if (map.get(key).toString().equals(ip)) {
				log.info("这个ip的座席可以登录这个系统！");
				flag = true;
				break;
			}
		}
		if (flag == true) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private String getPortByIp(String ip) {
		Map map = portCompareService.getInnerPortMap();
		Iterator it = map.keySet().iterator();
		String port = "";
		while (it.hasNext()) {
			String key = (String) it.next();
			if (map.get(key).toString().equals(ip)) {
				port = key;
				break;
			}
		}
		return port;
	}

	public String removeUser(String ip) {
		log.info("移除ip--->" + ip);
		if (LoginMap.containsKey(ip)) {
			LoginMap.remove(ip);
		}
		return "";
	}

	public PortCompareService getPortCompareService() {
		return portCompareService;
	}

	public void setPortCompareService(PortCompareService portCompareService) {
		this.portCompareService = portCompareService;
	}

	public String getInListValue() {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		Iterator it = InUserMap.values().iterator();
		result.append(ConstRuleI.CMD_TO_AGENT_USERLIST);
		result.append(ConstRuleI.SPLIT_SIGN_COLON);
		while (it.hasNext()) {
			LoginUser lu = (LoginUser) it.next();
			String user = lu.getUser();
			result.append(user);
			result.append(ConstRuleI.SPLIT_SIGN_jing);
		}
		result.append(ConstRuleI.SPLIT_SIGN_SEMICOLON);
		return result.toString();
	}

	public void putInList(String ip, LoginUser lu) {
		// TODO Auto-generated method stub
		InUserMap.put(ip, lu);
	}

	public void removeInList(String ip) {
		// TODO Auto-generated method stub
		InUserMap.remove(ip);
	}

	public String getPbxportByAgentnum(String agentNum) {
		// TODO Auto-generated method stub
		String pbxPort = "";
		Iterator it = LoginMap.values().iterator();
		while (it.hasNext()) {
			LoginUser lu = (LoginUser) it.next();
			if (lu.getUser().equals(agentNum)) {
				pbxPort = lu.getPort();
			}
		}
		return pbxPort;
	}

	// 根据内线端口号得到内线的签入的ip
	public String getAgentIpByPort(String pbxPort) {
		String agentIp = "";
		Iterator it = LoginMap.values().iterator();
		while (it.hasNext()) {
			LoginUser lu = (LoginUser) it.next();
			if (pbxPort.equals(lu.getPort())) {
				agentIp = lu.getIp();
			}
		}
		return agentIp;
	}

	public LoginUser getAgentByPort(String pbxPort) {
		// TODO Auto-generated method stub
		LoginUser resultLu = null;
		Iterator it = LoginMap.values().iterator();
		while (it.hasNext()) {
			LoginUser lu = (LoginUser) it.next();
			if (lu.getPort().equals(pbxPort)) {
				resultLu = lu;
			}
		}
		return resultLu;
	}

}
