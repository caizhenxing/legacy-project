/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class IvrBean {
	// 交换机端口号
	private String pbxPort = "";

	// 自动语音状态，开始，正在进行，结束
	private String ivrState = "";

	// 占用的板卡的端口号
	private String ivrPort = "";

	// 来电号码
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
