/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class IvrBean {
	// �������˿ں�
	private String pbxPort = "";

	// �Զ�����״̬����ʼ�����ڽ��У�����
	private String ivrState = "";

	// ռ�õİ忨�Ķ˿ں�
	private String ivrPort = "";

	// �������
	private String IncommingNum = "";

	public String getIncommingNum() {
		return IncommingNum;
	}

	public void setIncommingNum(String incommingNum) {
		IncommingNum = incommingNum;
	}

	public String getIvrPort() {
		return ivrPort;
	}

	public void setIvrPort(String ivrPort) {
		this.ivrPort = ivrPort;
	}

	public String getIvrState() {
		return ivrState;
	}

	public void setIvrState(String ivrState) {
		this.ivrState = ivrState;
	}

	public String getPbxPort() {
		return pbxPort;
	}

	public void setPbxPort(String pbxPort) {
		this.pbxPort = pbxPort;
	}
}
