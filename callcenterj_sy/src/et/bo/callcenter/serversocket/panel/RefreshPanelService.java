/**
 * ����׿Խ�Ƽ����޹�˾
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

	// ʱʱ���Cti��״̬(keyֵ�ǵ绰�ţ�value��CTIBean)
	public static Map<String, CtiBean> ctiBeanMap = new HashMap<String, CtiBean>();

	// ʱʱ������ϯ�绰״̬
	public static Map<String, AgentStateBean> agentStateMap = new HashMap<String, AgentStateBean>();

	// ivrstateMap��״̬����ʼ�����е�IVR��״̬������ʱʱ���IVR�Ķ˿ڿ����Ƿ��Ѿ���ռ�ã������ռ�ã�
	// keyֵΪIVR�뽻�������ӵĶ˿ںţ�valueΪ���������ӵ�IVR�ߵ�״̬
	// use ��ʾʹ����
	// nouse ��ʾ����ʹ����
	public static Map<String, String> ivrStateMap = new HashMap<String, String>();

	public static Map<String, AgentInfoBean> agentInfoMap = new HashMap<String, AgentInfoBean>();

	/**
	 * ˢ���û���ϯ���
	 * 
	 * @param ap
	 * @return ����ϯ�����ĸ�ʽ������ַ���
	 */
	String refreshPanel(AgentPanel ap);

	/**
	 * ˢ���û���ϯ���������ͨ����ר���б�
	 * 
	 * @param ap
	 * @return ����ϯ�����ĸ�ʽ������ַ���
	 */
	String refreshPanelTExperterList(AgentPanel ap);

	/**
	 * ��CtiBeanMap�����ctiBean��mainKey�ҵ�ctiBeanMap��key
	 * 
	 * @param mainKey
	 * @return
	 */
	String getKeyByMainKey(String mainKey);

	/**
	 * ��ivrStateMap���ҳ�û��ʹ�õĶ˿ں�
	 * 
	 * @return
	 */
	String getNoUseIvrPort();

	/**
	 * ������ʹ�õĶ˿����ҵ�key��
	 */
	String getKeyFromCtiUsingPort(String pbxPort);

	/**
	 * ������ʹ�õĶ˿����ҵ�mainKey��
	 */
	String getMainKeyFromCtiUsingPort(String pbxPort);

	/**
	 * ���ݽ������˿ںŵõ���Ӧ��AgentBean
	 * 
	 * @param mainkey
	 *            CtiBeanMap��keyֵ
	 * @param pbxPort
	 *            �������˿ں�
	 * @return
	 */
	AgentBean getAgentBeanBypbxport(String mainKey, String pbxPort);

}
