/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-23
 */
package et.test.string;

import et.bo.callcenter.serversocket.iconst.ConstRuleI;

/**
 * @author zhang feng
 * 
 */
public class TestAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestAgent ta = new TestAgent();
		System.out.println(ta.getAgentState(ConstRuleI.FAIL, "8002"));
	}

	// �ϳ��ַ���CMD_SENDTOIVRAGESTATE:0,8001;����IVRִ�гɹ����
	private String getAgentState(String state, String portNum) {
		StringBuilder sb = new StringBuilder();
		sb.append(ConstRuleI.CMD_IVR_SEND_AGENTSTATE);
		sb.append(ConstRuleI.SPLIT_SIGN_COLON);
		sb.append(state);
		sb.append(ConstRuleI.SPLIT_SIGN_COMMA);
		sb.append(portNum);
		sb.append(ConstRuleI.SPLIT_SIGN_SEMICOLON);
		return sb.toString();
	}

}
