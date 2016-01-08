package et.bo.callcenter.serversocket.checklogin;

import java.util.Map;

import et.bo.callcenter.serversocket.checklogin.impl.LoginUser;
import et.po.SysUser;

public interface LoginMapService {
	/**
	 * 设置座席员LoginMap
	 */
	public String setLoginUer(String user, String ip, String skill);

	/**
	 * 检查是否为合法Ip
	 * 
	 * @param ip
	 * @return
	 */
	public String checkIp(String ip);

	/**
	 * 返回LoginMap
	 */
	public Map getLoginMap();

	/**
	 * 移除登录用户
	 * 
	 * @param ip
	 * @return
	 */
	public String removeUser(String ip);

	/**
	 * 放入用户列表
	 * 
	 * @param ip
	 * @param lu
	 */
	void putInList(String ip, LoginUser lu);

	/**
	 * 从用户列表中取出值的信息
	 * 
	 * @param ip
	 */
	void removeInList(String ip);

	/**
	 * 得到签入的内线用户列表
	 * 
	 * @return 内线用户的列表的信息
	 */
	String getInListValue();

	/**
	 * 根据座席号得到交换机的端口
	 * 
	 * @param agentNum
	 * @return 交换机的端口号
	 */
	String getPbxportByAgentnum(String agentNum);

	/**
	 * 通过交换机的端口号得到座席的ip号
	 * 
	 * @param pbxPort 交换机端口号
	 * @return 座席ip
	 */
	String getAgentIpByPort(String pbxPort);
	
	/**
	 * 
	 * @param pbxPort
	 * @return
	 */
	LoginUser getAgentByPort(String pbxPort);

}
