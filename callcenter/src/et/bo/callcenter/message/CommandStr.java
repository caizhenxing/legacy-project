package et.bo.callcenter.message;

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
