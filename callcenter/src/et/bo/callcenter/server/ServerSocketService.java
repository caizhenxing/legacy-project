package et.bo.callcenter.server;

import java.util.Map;

public interface ServerSocketService {
	/*
	 * �������˷��͸��ͻ��˵���Ϣ��
	 * ������ip-�ͻ��˵�ip.
	 * 		msg ��Ϣ����.
	 */
	public String sendContent(String ip,String msg);
	/*
	 * �õ���ǰ�ĳ����ӵļ���
	 */
//	public Map getCurLink();
	/*
	 * ����ServerSocket
	 */
	public void runServerSocket();
}
