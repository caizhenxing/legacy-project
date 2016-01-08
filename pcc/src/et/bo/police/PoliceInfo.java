package et.bo.police;

public interface PoliceInfo{
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	//-------------validate------------------
	/*
	 * 警务信息黑名单，
	 * 参数：电话号码
	 * 返回：0成功，其它不成功。
	 */
	String blacklist(String phoneNum);
	/*
	 * 警员验证
	 * 参数：警号，和密码
	 * 返回：0成功，其它不成功。
	 */
	String isPolice(String fuzzNum,String pass);
	/*
	 * 警务呼叫中心数据库是否通
	 * 返回：0成功，其它不成功。
	 */
//	-------------connect------------------
	String isDbConnect();
	/*
	 * 与座席的连接socket是否连通
	 * 返回：0成功，其它不成功。
	 */
	String isOperatorConnect(String ip);
	/*
	 * 得到工控机的ip
	 */
	String getCardPcIp();
	
}
