package et.bo.callcenter.message;

public interface EventHelperService {
	/*
	 * 刷新座席面版的状态
	 * 
	 */
	public void refreshOperatorPanel();
	/*
	 * 刷新工控机的座席状态
	 * 工控机：icc
	 */
	public void refreshIccOperatorState();
	/*
	 * 全部刷新，以上2个命令
	 */
	public void refreshIccAndOperator();
	/*
	 * 存储cclog、policeCallin、Police_callin_info
	 */
	/*
	 * 刷新工控机的座席员 :physicsPort and OperatorNum
	 * 工控机：icc 
	 */
	public void refreshIccOperator(String physicsPort,String OperatorNum);
	public void saveInfo(String id);
	public void callRe(String inPort,String result);
	public void printArg(String s[]);
	
	public void oosign(String op,String s);
	public void ooseat(String op,String s);
	public void oocall(String op,String s);
}
