/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-26
 */
package et.bo.callcenter.serversocket.panel.impl;

/**
 * @author zhang feng
 * 
 */
public class RecordBean {

	// ��ϯ�������ֵ
	private String talkKey = "";

	// ¼���˿�
	private String recordPort = "";

	// ¼���ļ�·��
	private String recordPath = "";

	// ����¼���ļ���
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
