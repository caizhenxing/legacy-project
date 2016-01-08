package et.bo.callcenter.serversocket.iconst;

import java.util.HashMap;
import java.util.Map;

public interface ConstantsI {
	/*
	 * 分割符号
	 */
	String DELIM1 = "#";

	String DELIM2 = "!";

	String BLANK = " ";

	String SEMICOLON = ";";

	int INT_SEMICOLON = 59;

	int PHONE_NUM_FORMAT_LEN = 15;

	// ---------------------------交换机配置信息----------------------------
	String pbxIp = "192.168.1.12";// 交换机ip
	String ivrIp = "192.168.1.11";// ivr服务器ip
	String confenceIp = "192.168.1.15";// 会议服务器ip
	String cstaIp = "192.168.1.16";// 会议服务器ip

	String TRUNK_PORT = "TRUNK";// 中继口

	String USER_PORT = "USER";// 用户口

	// --------------------------Socket端口及配置信息-----------------------
	int PORT = 12000;

	int INIT_SOCKET = 20;

	// -----------------------------交换机及其它组件的各种状态信息-------------
	String IncommingIVR = "InIVR";

	String AgentToIVR = "ToAgent";

	String outCommingIVR = "OutIVR";

	// -------------------------------------------------------------------

	// -----------------------------进入到流程中的那种状态信息-------------
	String InToIVR = "InToIVR";// 进入IVR

	String IVRToAgent = "IVRToAgent";// IVR到座席

	String OutToIVR = "OutToIVR";// 从IVR出去

	String AGENT_TO_AGENT = "AGENTTOAGENT";// 从座席到座席(内线转接状态)

	String AGENT_OUTING = "AGENTOUTING";// 座席外呼

	String PROCESS_TYPE_OUTINGCALL = "OUTGOING";// 接续类型为外呼

	String THREE_CALLING_BEGIN = "THREEBEGINRING";// 三方呼叫开始震铃

	String THREE_TALK_BEGIN = "THREEBEGINTALK";// 三方呼叫开始通话

	// -------------------------------------------------------------------

	// -----------------------------进入到IVR的哪个流程中--------------------
	String IVRISUSE = "USE";// 使用中

	String IVRISNOTUSE = "NOUSE";// 未使用

	// -------------------------------------------------------------------

	// -----------------------------表示在talk表中的各种状态--------------------
	String AGENT_STATE = "AGENT";// 座席

	String ANOTHER_AGENT = "OTHERAGENT";// 转换到另一座席

	String EXPERT_STATE = "EXPERT";// 专家

	String OUTING_AGENT = "OUTINGAGENT";

	// -------------------------------------------------------------------

	// -----------------------------从talk表中转到其它表中的信息--------------------
	String TO_ANOTHER_AGENT = "TOAGENT";// 转换到另一座席

	// -------------------------------------------------------------------
	
	

}
