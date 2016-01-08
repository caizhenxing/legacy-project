package et.bo.callcenter;

public interface ConstOperI {
	/*
	 * 签入/出状态-签入
	 */
	String IO_IN="1";
	/*
	 * 签入/出状态-签出
	 */
	String IO_OUT="0" ;
	/*
	 * 工作状态[离席、入席、振铃、通话]-离席
	 */
	String WORK_LEAVE="0";
	/*
	 * 工作状态[离席、入席、振铃、通话]-入席
	 */
	String WORK_IN="1";
	/*
	 * 工作状态[离席、入席、振铃、通话]-振铃
	 */
	String WORK_RING="2";
	/*
	 * 工作状态[离席、入席、振铃、通话]-通话
	 */
	String WORK_TEL="3";
	//-----------------------------	
	/*
	 * 工作状态[离席、入席、振铃、通话]-等待接听
	 */
	String WORK_WAIT="4";	
//	-----------------------------
	/*
	 * 虚拟工号
	 */
	String OPERATOR_NUM="***";
	int OPERATOR_PANEL_NUM=8;
	String OPERATOR_NULL="***#101#               !";
	
}
