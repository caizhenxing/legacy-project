package et.bo.police.callcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardInfo {
	/*
	 * ����˿�:����ϵͳ������˿ںš�
	 */
	private String physicsPort;
	/*
	 * �߼��˿�:����ϵͳ���߼��˿ڡ�
	 */
	private String logicPort;
	/*
	 * �˿�����:�˿ڵ����͡�0-���߶˿ڣ�1-���߶˿ڡ�
	 */
	private String portType;
	/*
	 * ״̬,�ö˿ڵĵ�ǰ״̬��0-δ��װ��1-�𻵣�2-�رգ�3-������
	 */	
	private String state;
	/*
	 * ipӳ��,�������߶˿ڣ�Ӧ��ӳ��ɹ��ػ����������߽ӿڣ�Ӧ��ӳ�����ϯԱ�˿ڡ�
	 */
	private String ip;
	//����Ϣ��ļ��ϡ�ͨ������˿�Ѹ�ٵ��ҵ���صĶ�����
	private static Map cardMap = new HashMap();// key is physicsPort,value is CardInfo

	private static List innerPortList = new ArrayList();//�����ϵ��������ߵ�����˿ڵ�˳�򼯺ϣ�element is CardInfo��physicsPort�������������˿ڡ�
	
	//---------------------------------------------
	public final String PORT_TYPE_INNER="0";//����
	public final String PORT_TYPE_OUTER="1";//����
	
	public final String STATE_UNINSTALL="0";//δ��װ
	public final String STATE_ERROR="1";//��
	public final String STATE_CLOSE="2";//�ر�
	public final String STATE_WELL="3";//����
	//	---------------------------------------------
	
	//	��ʼ��,�ӹ��ػ������ݿ⣨ip��ȡ����Ϣ
	private void init(){
		// to do sthing
		
		
	}
	
	public String getLogicPort() {
		return logicPort;
	}
	public void setLogicPort(String logicPort) {
		this.logicPort = logicPort;
	}
	public String getPhysicsPort() {
		return physicsPort;
	}
	public void setPhysicsPort(String physicsPort) {
		this.physicsPort = physicsPort;
	}
	public String getPortType() {
		return portType;
	}
	public void setPortType(String portType) {
		this.portType = portType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public static Map getCardMap() {
		return cardMap;
	}
	public static CardInfo getCardInfo(String physicsPort){
		return (CardInfo)cardMap.get(physicsPort);
	}

	public static List getInnerPortList() {
		return innerPortList;
	}

}
