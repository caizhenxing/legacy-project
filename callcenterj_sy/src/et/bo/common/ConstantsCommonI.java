package et.bo.common;

public interface ConstantsCommonI {
	String RUNING_STATE_APPLICATION = "0";
	String RUNING_STATE_WEB = "1";

	/*
	 * 测试标志
	 */
	String TEST_LINE = "----------------------------------------------------";
	String TEST_AT = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
	String TEST_DELIM1 = "[";
	String TEST_DELIM2 = "]";
	String TEST_DELIM3 = ":";
	String TEST_LOG_PATH = "./src/et/config/log4j/log4j.xml";// 当前路径是项目的根路径。
	String TEST_RETURN = "\r\n";
	/*
	 * 测试文件的文件名
	 */

	String TEST_FILE = "C:/log/myLogcclogivr.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */

	String TEST_FILECLIENT = "C:/log/myLogClient.txt";
	/*
	 * 测试pbx相关文件
	 */
	String TEST_FILEPBX = "C:/log/myLogPbx.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */

	String TEST_FILE2 = "C:/log/myLog2.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */

	String TEST_FILE3 = "C:/log/myLog3.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */

	String TEST_FILE4 = "C:/log/myLog4.txt";
	/*
	 * 测试文件的文件名(mylog1)
	 */

	String TEST_FILE5 = "C:/log/myLog5.txt";
	/*
	 * mylog for ivr invoke
	 */
	boolean TEST_STATE = true;

	/**
	 * mylog for client
	 */
	boolean TEST_STATE1 = true;
	/**
	 * mylog2
	 */
	boolean TEST_STATE2 = false;
	/**
	 * mylog2
	 */
	boolean TEST_STATE3 = false;
	/**
	 * mylog2
	 */
	boolean TEST_STATE4 = false;
	/**
	 * mylog2
	 */
	boolean TEST_STATE5 = false;
	/**
	 * mylog2
	 */
	boolean TEST_STATE_PBX = true;
	/**
	 * add by liang
	 */
	public static final String INSERT_OPER = "insert";
	public static final String UPDATE_OPER = "update";
	public static final String DELETE_OPER = "delete";
	public static final String DETAIL_OPER = "detail";
}
