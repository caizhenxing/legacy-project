package et.bo.callcenter;

public interface ConstantsI {
	/**
	 * socket相关neckName
	 * max_socket_thread
	 */
//	------------------------------TYPE VALUE-----------------------------
	/*
	 * 服务器socket端口neckName
	 */
	String SERVER_SOCKET_PORT = "server_socket_port";
	/*
	 * 服务器ip neckName
	 */
	String SERVER_IP          = "server_ip";
	/*
	 * 服务器最大通讯线程数量
	 */
	String MAX_SOCKET_THREAD  = "max_socket_thread";
	/*
	 * 客户端socket端口neckName
	 */
	String CLIENT_SOCKET_PORT = "client_socket_port";
	/*
	 * 客户端ip列表neckName //
	 */
	String CLIENT_IP_LIST     = "client_ip_list";
	/*
	 * 工控机ip 
	 */
	String CLIENT_IP     = "client_ip";
	/*
	 * 客户端测试模拟数量
	 */
	String CLIENT_TEST_NUM    = "client_test_num";
	/*
	 * 命令传送成功
	 */
	String CMD_SEND_OK ="cmd_send_ok";
	/*
	 * 命令返回成功 
	 */
	String CMD_RETURN_OK ="cmd_return_ok";
	/*
	 * 命令返回失败 
	 */
	String CMD_RETURN_ERROR ="cmd_return_error";
	/*
	 * 命令传送失败 
	 */
	String CMD_SEND_ERROR ="cmd_send_error";
	/*
	 * 事件操作成功 
	 */
	String EVT_OPERATE_OK ="evt_operate_ok";
	/*
	 * 事件操作失败 
	 */
	String EVT_OPERATE_ERROR ="evt_operate_error";
	/*
	 * 事件字符串非法 
	 */
	String EVT_STRING_INVALID ="evt_string_invalid";
//	--------------------------TABLE NAME---------------------------------
	/*
	 * 表名称
	 */
	
	String CC_CARD="CC_CARD";
	String CC_CLIENT="CC_CLIENT";
	String CC_EXTENSION="CC_EXTENSION";
	String CC_INFO="CC_INFO";
	String CC_RULE="CC_RULE";
	
	String CC_LOG ="CC_LOG";
	
	/*
	 * 分割符号
	 */
	String DELIM1="#";
	String DELIM2="!";
	String BLANK=" ";
	String SEMICOLON=";";
	int INT_SEMICOLON=59;
	int PHONE_NUM_FORMAT_LEN=15;
}
