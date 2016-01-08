package et.bo.callcenter.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConnectInfo {
	private static Map curConn = new HashMap();//当前待处理的ConnectInfo，key is inPort。
	/*
	 * 流水号
	 */
	private String id;
	/*
	 * 通讯机ip
	 */
	private String clientIp;
	/*
	 * 呼入端口
	 */
	private String inPort;
	/*
	 * 座席端口
	 */
	private String operatorPort;
	/*
	 * 主叫号码
	 */
	private String phoneNum;
	/*
	 * 呼入时间
	 */
	private Date beginTime;
	/*
	 * 接通时间
	 */
	private Date operateTime;
	/*
	 * 结束时间
	 */
	private Date endTime;
	/*
	 * 录音文件路径,相对路径，
	 */
	private String recFile;
	/*
	 * 命令集合,一条一条命令的连接
	 */
	private StringBuffer cmd;
	/*
	 * 备注
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
