package et.bo.police.callcenter.server;

public interface CommandStr {
	/*
	 * ����֮ǰ������Ͻ����
	 * 
	 */
	String getCmdResultBeforeSend();
	/*
	 * �õ������
	 */
	String getSendMsg(String cmd,String[] args);
}
