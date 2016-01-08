package et.bo.common;

public interface ConstantsCommonI {
	String RUNING_STATE_APPLICATION="0";
	String RUNING_STATE_WEB="1";
	
	/*
	 * 测试标志
	 */
	String TEST_LINE 		="----------------------------------------------------";
	String TEST_AT   		="@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
	String TEST_DELIM1		="[";
	String TEST_DELIM2		="]";
	String TEST_DELIM3		=":";
	String TEST_LOG_PATH 	="./src/et/config/log4j/log4j.xml";//当前路径是项目的根路径。
	String TEST_RETURN		="\r\n";
	/*
	 * 测试文件的文件名
	 */
	
	String TEST_FILE		="C:/temp/log/myLog.txt";
	/*
	 * 测试状态
	 */
	boolean TEST_STATE		=true;
}
