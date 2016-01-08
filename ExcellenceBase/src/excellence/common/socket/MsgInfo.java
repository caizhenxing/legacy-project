package excellence.common.socket;

public class MsgInfo {

	String ip;
	String msg;
	public MsgInfo()
	{
		super();
	}
	public MsgInfo(String ip,String msg)
	{
		this.ip=ip;
		this.msg=msg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
