/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class FaxBean {
	// ���������߶˿ں�
	private String pbxPort = "";

	// ռ�ö˿�
	private String usingPort = "";

	// ���ڷ��ͣ�ռ�ã��Ѿ��������
	private String faxState = "";

	// ����·��
	private String faxPath = "";

	public String getFaxState() {
		return faxState;
	}

	public void setFaxState(String faxState) {
		this.faxState = faxState;
	}

	public String getPbxPort() {
		return pbxPort;
	}

	public void setPbxPort(String pbxPort) {
		this.pbxPort = pbxPort;
	}

	public String getUsingPort() {
		return usingPort;
	}

	public void setUsingPort(String usingPort) {
		this.usingPort = usingPort;
	}

	public String getFaxPath() {
		return faxPath;
	}

	public void setFaxPath(String faxPath) {
		this.faxPath = faxPath;
	}
}
