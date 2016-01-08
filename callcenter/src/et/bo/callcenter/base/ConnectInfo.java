package et.bo.callcenter.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConnectInfo {
	private static Map curConn = new HashMap();//��ǰ�������ConnectInfo��key is inPort��
	/*
	 * ��ˮ��
	 */
	private String id;
	/*
	 * ͨѶ��ip
	 */
	private String clientIp;
	/*
	 * ����˿�
	 */
	private String inPort;
	/*
	 * ��ϯ�˿�
	 */
	private String operatorPort;
	/*
	 * ���к���
	 */
	private String phoneNum;
	/*
	 * ����ʱ��
	 */
	private Date beginTime;
	/*
	 * ��ͨʱ��
	 */
	private Date operateTime;
	/*
	 * ����ʱ��
	 */
	private Date endTime;
	/*
	 * ¼���ļ�·��,���·����
	 */
	private String recFile;
	/*
	 * �����,һ��һ�����������
	 */
	private StringBuffer cmd;
	/*
	 * ��ע
	 */
	private String remark;
	
	public ConnectInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public StringBuffer getCmd() {
		return cmd;
	}

	public void setCmd(StringBuffer cmd) {
		this.cmd = cmd;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getRecFile() {
		return recFile;
	}

	public void setRecFile(String recFile) {
		this.recFile = recFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInPort() {
		return inPort;
	}

	public void setInPort(String inPort) {
		this.inPort = inPort;
	}

	public String getOperatorPort() {
		return operatorPort;
	}

	public void setOperatorPort(String operatorPort) {
		this.operatorPort = operatorPort;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static Map getCurConn() {
		return curConn;
	}
}
