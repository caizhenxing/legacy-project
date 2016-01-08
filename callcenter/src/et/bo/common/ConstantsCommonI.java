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
	 * 测试文件的文件名(mylog1)
	 */
	
	String TEST_FILE1		="C:/mylog_supervise.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */
	
	String TEST_FILE2		="C:/mylog_supervise2.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */
	
	String TEST_FILE3		="C:/mylog_supervise3.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */
	
	String TEST_FILE4		="C:/mylog_supervise4.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */
	
	String TEST_FILE5		="C:/mylog_supervise5.txt";
	/*
	 * 测试状态
	 */
	boolean TEST_STATE		=true;
	
	/**
	 * mylog1
	 */
	boolean TEST_STATE1		=true;
	/**
	 * mylog2
	 */
	boolean TEST_STATE2		=true;
	/**
	 * mylog2
	 */
	boolean TEST_STATE3		=true;
	/**
	 * mylog2
	 */
	boolean TEST_STATE4		=true;
	/**
	 * mylog2
	 */
	boolean TEST_STATE5		=true;
}
