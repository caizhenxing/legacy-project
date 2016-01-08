package et.bo.callcenter.message;

public interface CommandStr {
	/*
	 * 发送之前命令串整合结果。
	 * 
	 */
	String getCmdResultBeforeSend();
	/*
	 * 得到命令串。
	 */
	String getSendMsg(String cmd,String[] args);
}
