package et.bo.callcenter;

public interface ConstantsI {
	/**
	 * socket���neckName
	 * max_socket_thread
	 */
//	------------------------------TYPE VALUE-----------------------------
	/*
	 * ������socket�˿�neckName
	 */
	String SERVER_SOCKET_PORT = "server_socket_port";
	/*
	 * ������ip neckName
	 */
	String SERVER_IP          = "server_ip";
	/*
	 * ���������ͨѶ�߳�����
	 */
	String MAX_SOCKET_THREAD  = "max_socket_thread";
	/*
	 * �ͻ���socket�˿�neckName
	 */
	String CLIENT_SOCKET_PORT = "client_socket_port";
	/*
	 * �ͻ���ip�б�neckName //
	 */
	String CLIENT_IP_LIST     = "client_ip_list";
	/*
	 * ���ػ�ip 
	 */
	String CLIENT_IP     = "client_ip";
	/*
	 * �ͻ��˲���ģ������
	 */
	String CLIENT_TEST_NUM    = "client_test_num";
	/*
	 * ����ͳɹ�
	 */
	String CMD_SEND_OK ="cmd_send_ok";
	/*
	 * ����سɹ� 
	 */
	String CMD_RETURN_OK ="cmd_return_ok";
	/*
	 * �����ʧ�� 
	 */
	String CMD_RETURN_ERROR ="cmd_return_error";
	/*
	 * �����ʧ�� 
	 */
	String CMD_SEND_ERROR ="cmd_send_error";
	/*
	 * �¼������ɹ� 
	 */
	String EVT_OPERATE_OK ="evt_operate_ok";
	/*
	 * �¼�����ʧ�� 
	 */
	String EVT_OPERATE_ERROR ="evt_operate_error";
	/*
	 * �¼��ַ����Ƿ� 
	 */
	String EVT_STRING_INVALID ="evt_string_invalid";
//	--------------------------TABLE NAME---------------------------------
	/*
	 * ������
	 */
	
	String CC_CARD="CC_CARD";
	String CC_CLIENT="CC_CLIENT";
	String CC_EXTENSION="CC_EXTENSION";
	String CC_INFO="CC_INFO";
	String CC_RULE="CC_RULE";
	
	String CC_LOG ="CC_LOG";
	
	/*
	 * �ָ����
	 */
	String DELIM1="#";
	String DELIM2="!";
	String BLANK=" ";
	String SEMICOLON=";";
	int INT_SEMICOLON=59;
	int PHONE_NUM_FORMAT_LEN=15;
}
