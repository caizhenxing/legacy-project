/**
 * 沈阳卓越科技有限公司
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class RecordBean {

	// 座席表的主键值
	private String talkKey = "";

	// 录音端口
	private String recordPort = "";

	// 录音文件路径
	private String recordPath = "";

	// 生成录音文件名
	private String recordName = "";

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public String getRecordPort() {
		return recordPort;
	}

	public void setRecordPort(String recordPort) {
		this.recordPort = recordPort;
	}

	public String getTalkKey() {
		return talkKey;
	}

	public void setTalkKey(String talkKey) {
		this.talkKey = talkKey;
	}
}
