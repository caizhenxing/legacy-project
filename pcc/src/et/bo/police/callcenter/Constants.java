package et.bo.police.callcenter;

public interface Constants {
	/**
	 * socket���neckName
	 * max_socket_thread
	 */
	//----------------------------------TEST THING-------------------------
	/*
	 * ���Ա�־
	 */
	String TEST_LINE 		="----------------------------------------------------";
	String TEST_AT   		="@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
	String TEST_LOG_PATH 	="./src/et/config/log4j/log4j.xml";//��ǰ·������Ŀ�ĸ�·����
	String TEST_RETURN		="\r\n";
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
	 * �ͻ���ip�б�neckName
	 */
	String CLIENT_IP_LIST     = "client_ip_list";
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
}
