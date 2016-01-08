/**
 * 沈阳卓越科技有限公司
 * 2008-4-9
 */
package et.bo.callcenter.serversocket.panel;

import java.util.HashMap;
import java.util.Map;

import et.bo.callcenter.serversocket.panel.bean.AgentStateBean;
import et.bo.callcenter.serversocket.panel.impl.AgentBean;
import et.bo.callcenter.serversocket.panel.impl.AgentInfoBean;
import et.bo.callcenter.serversocket.panel.impl.AgentPanel;
import et.bo.callcenter.serversocket.panel.impl.CtiBean;
import et.bo.callcenter.serversocket.panel.impl.IvrBean;

/**
 * @author zhang feng
 * 
 */
public interface RefreshPanelService {

	// 时时监控Cti的状态(key值是电话号，value是CTIBean)
	public static Map<String, CtiBean> ctiBeanMap = new HashMap<String, CtiBean>();

	// 时时更新座席电话状态
	public static Map<String, AgentStateBean> agentStateMap = new HashMap<String, AgentStateBean>();

	// ivrstateMap的状态，初始化所有的IVR的状态，并且时时监控IVR的端口看看是否已经被占用，如果被占用，
	// key值为IVR与交换机连接的端口号，value为交换机连接的IVR线的状态
	// use 表示使用中
	// nouse 表示不在使用中
	public static Map<String, String> ivrStateMap = new HashMap<String, String>();

	public static Map<String, AgentInfoBean> agentInfoMap = new HashMap<String, AgentInfoBean>();

	/**
	 * 刷新用户座席面板
	 * 
	 * @param ap
	 * @return 向座席发出的格式化后的字符串
	 */
	String refreshPanel(AgentPanel ap);

	/**
	 * 刷新用户座席面板中三方通话的专家列表
	 * 
	 * @param ap
	 * @return 向座席发出的格式化后的字符串
	 */
	String refreshPanelTExperterList(AgentPanel ap);

	/**
	 * 从CtiBeanMap里根据ctiBean的mainKey找到ctiBeanMap的key
	 * 
	 * @param mainKey
	 * @return
	 */
	String getKeyByMainKey(String mainKey);

	/**
	 * 从ivrStateMap里找出没有使用的端口号
	 * 
	 * @return
	 */
	String getNoUseIvrPort();

	/**
	 * 从正在使用的端口中找到key値
	 */
	String getKeyFromCtiUsingPort(String pbxPort);

	/**
	 * 从正在使用的端口中找到mainKey値
	 */
	String getMainKeyFromCtiUsingPort(String pbxPort);

	/**
	 * 根据交换机端口号得到对应的AgentBean
	 * 
	 * @param mainkey
	 *            CtiBeanMap的key值
	 * @param pbxPort
	 *            交换机端口号
	 * @return
	 */
	AgentBean getAgentBeanBypbxport(String mainKey, String pbxPort);

}
