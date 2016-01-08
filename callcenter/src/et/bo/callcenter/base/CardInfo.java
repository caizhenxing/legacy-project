package et.bo.callcenter.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.business.BusinessObject;

public class CardInfo {
	private static Log log = LogFactory.getLog(CardInfo.class);
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
	//-------------ע��
	private static BusinessObject bo=null;
	
	//����Ϣ��ļ��ϡ�ͨ������˿�Ѹ�ٵ��ҵ���صĶ������ڣ����ɣ����ʼ����ʱ������ˡ�
	private static Map cardMap = new HashMap();// key is physicsPort,value is CardInfo
	
	private static List innerList = new ArrayList();//�����ϵ��������ߵ�CardInfo�ļ��ϡ�
	private static List innerIpList = new ArrayList();//�����ϵ��������ߵ�ip�ļ��ϡ���Ϊ����������Ϣ����ϯ�á�
	private static List innerPortList = new ArrayList();//�����ϵ��������ߵ�physicsPort�ļ��ϡ���Ϊ�����ػ���������Ϣ�á�
	//---------------------------------------------
	public final String PORT_TYPE_INNER="0";//����
	public final String PORT_TYPE_OUTER="1";//����
	
	public final String STATE_UNINSTALL="0";//δ��װ
	public final String STATE_ERROR="1";//��
	public final String STATE_CLOSE="2";//�ر�
	public final String STATE_WELL="3";//����
	//	---------------------------------------------	
	/*
	 * list֮�е�ÿһ��Ԫ���ǣ�CardInfo������������������еĿ�
	 * ��ʱû�У��
	 * ��ʼ��,�ӹ��ػ������ݿ⣨ip��ȡ����Ϣ
	 */
	
	public static void initMap(List list){
		
		if(list==null)return;
		cardMap =new HashMap();
		innerList= new ArrayList();
		Map tmpMap = new HashMap();
		log.debug("list.size() is:"+list.size());
		for(int i=0;i<list.size();i++){
			
			CardInfo ci = (CardInfo)list.get(i);
			String port=ci.getPhysicsPort();
			
			ci.setIp(bo.getPortLinkedIp(port));
			
			cardMap.put(ci.getPhysicsPort(), ci);
			//
			tmpMap.put(port, new CardState(port));
			
			//
			if(ci.PORT_TYPE_INNER.equals(ci.getPortType())){
				
				if(ci.STATE_UNINSTALL.equals(ci.getState()))continue;
				if(ci.STATE_ERROR.equals(ci.getState()))continue;
//				if(ci.STATE_ERROR.equals(ci.getState()))continue;
				innerList.add(ci);
			}
			
		}
		if(CardState.getPortMap().size()!=0)return;
//		����
		CardState.setPortMap(tmpMap);
		//
		log.debug("set ok!");
		/*
		 * add for test
		 */
		CardInfo.setInnerList();
//		OperatorState.setOperatorStateLogon("001", "192.168.1.3");
	}
	public static CardInfo getCardInfo(String physicsPort){
		return (CardInfo)cardMap.get(physicsPort);
	}
	private static void setInnerList(){
		innerIpList=new ArrayList();
		innerPortList = new ArrayList();
		for(int i=0;i<innerList.size();i++){
			CardInfo ci = (CardInfo)innerList.get(i);
			innerIpList.add(ci.getIp());
			innerPortList.add(ci.getPhysicsPort());
		}
	}
	public static List getInnerIpList(){
		if(innerIpList.size()==0) CardInfo.setInnerList();
		return innerIpList;
	}
	public static List getInnerPortList(){
		if(innerPortList.size()==0) CardInfo.setInnerList();
		return innerPortList;
	}
	//-------------------------------------------------
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
	
	public void setBo(BusinessObject bo) {
		this.bo = bo;
	}
	public static Map getCardMap() {
		return cardMap;
	}
	public static List getInnerList() {
		return innerList;
	}

}
