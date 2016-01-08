package et.bo.callcenter.serversocket.checklogin;

import java.util.Map;

import et.bo.callcenter.serversocket.checklogin.impl.LoginUser;
import et.po.SysUser;

public interface LoginMapService {
	/**
	 * ������ϯԱLoginMap
	 */
	public String setLoginUer(String user, String ip, String skill);

	/**
	 * ����Ƿ�Ϊ�Ϸ�Ip
	 * 
	 * @param ip
	 * @return
	 */
	public String checkIp(String ip);

	/**
	 * ����LoginMap
	 */
	public Map getLoginMap();

	/**
	 * �Ƴ���¼�û�
	 * 
	 * @param ip
	 * @return
	 */
	public String removeUser(String ip);

	/**
	 * �����û��б�
	 * 
	 * @param ip
	 * @param lu
	 */
	void putInList(String ip, LoginUser lu);

	/**
	 * ���û��б���ȡ��ֵ����Ϣ
	 * 
	 * @param ip
	 */
	void removeInList(String ip);

	/**
	 * �õ�ǩ��������û��б�
	 * 
	 * @return �����û����б����Ϣ
	 */
	String getInListValue();

	/**
	 * ������ϯ�ŵõ��������Ķ˿�
	 * 
	 * @param agentNum
	 * @return �������Ķ˿ں�
	 */
	String getPbxportByAgentnum(String agentNum);

	/**
	 * ͨ���������Ķ˿ںŵõ���ϯ��ip��
	 * 
	 * @param pbxPort �������˿ں�
	 * @return ��ϯip
	 */
	String getAgentIpByPort(String pbxPort);
	
	/**
	 * 
	 * @param pbxPort
	 * @return
	 */
	LoginUser getAgentByPort(String pbxPort);

}
