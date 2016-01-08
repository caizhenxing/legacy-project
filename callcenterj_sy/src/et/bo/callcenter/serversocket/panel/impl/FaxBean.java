/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class FaxBean {
	// 交换机内线端口号
	private String pbxPort = "";

	// 占用端口
	private String usingPort = "";

	// 正在发送，占用，已经发送完成
	private String faxState = "";

	// 传真路径
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
