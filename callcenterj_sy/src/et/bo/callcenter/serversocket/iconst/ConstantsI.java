package et.bo.callcenter.serversocket.iconst;

import java.util.HashMap;
import java.util.Map;

public interface ConstantsI {
	/*
	 * �ָ����
	 */
	String DELIM1 = "#";

	String DELIM2 = "!";

	String BLANK = " ";

	String SEMICOLON = ";";

	int INT_SEMICOLON = 59;

	int PHONE_NUM_FORMAT_LEN = 15;

	// ---------------------------������������Ϣ----------------------------
	String pbxIp = "192.168.1.12";// ������ip
	String ivrIp = "192.168.1.11";// ivr������ip
	String confenceIp = "192.168.1.15";// ���������ip
	String cstaIp = "192.168.1.16";// ���������ip

	String TRUNK_PORT = "TRUNK";// �м̿�

	String USER_PORT = "USER";// �û���

	// --------------------------Socket�˿ڼ�������Ϣ-----------------------
	int PORT = 12000;

	int INIT_SOCKET = 20;

	// -----------------------------����������������ĸ���״̬��Ϣ-------------
	String IncommingIVR = "InIVR";

	String AgentToIVR = "ToAgent";

	String outCommingIVR = "OutIVR";

	// -------------------------------------------------------------------

	// -----------------------------���뵽�����е�����״̬��Ϣ-------------
	String InToIVR = "InToIVR";// ����IVR

	String IVRToAgent = "IVRToAgent";// IVR����ϯ

	String OutToIVR = "OutToIVR";// ��IVR��ȥ

	String AGENT_TO_AGENT = "AGENTTOAGENT";// ����ϯ����ϯ(����ת��״̬)

	String AGENT_OUTING = "AGENTOUTING";// ��ϯ���

	String PROCESS_TYPE_OUTINGCALL = "OUTGOING";// ��������Ϊ���

	String THREE_CALLING_BEGIN = "THREEBEGINRING";// �������п�ʼ����

	String THREE_TALK_BEGIN = "THREEBEGINTALK";// �������п�ʼͨ��

	// -------------------------------------------------------------------

	// -----------------------------���뵽IVR���ĸ�������--------------------
	String IVRISUSE = "USE";// ʹ����

	String IVRISNOTUSE = "NOUSE";// δʹ��

	// -------------------------------------------------------------------

	// -----------------------------��ʾ��talk���еĸ���״̬--------------------
	String AGENT_STATE = "AGENT";// ��ϯ

	String ANOTHER_AGENT = "OTHERAGENT";// ת������һ��ϯ

	String EXPERT_STATE = "EXPERT";// ר��

	String OUTING_AGENT = "OUTINGAGENT";

	// -------------------------------------------------------------------

	// -----------------------------��talk����ת���������е���Ϣ--------------------
	String TO_ANOTHER_AGENT = "TOAGENT";// ת������һ��ϯ

	// -------------------------------------------------------------------
	
	

}
