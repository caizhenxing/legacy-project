package et.bo.callcenter.business;

import java.util.Map;

/**
 * 
 * @author guxiaofeng
 *业务门面
 */
public interface BusinessObject{
//	--------常量 ---------
	String SUCCEED="0";
	String FAIL_DEFAULT="999";
	
	String VALID_OK="0";
	String VALID_BLACKLIST="1";
	String VALID_FAIL="2";
	//	--------常量 over---------
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
	 * 座席员验证
	 * 参数：用户名工号，和密码
	 * 返回：0成功，其它不成功。
	 */
	String isOperator(String u,String p);

	/*
	 * 得到工控机的ip
	 */
	String getCardPcIp();
	/*
	 * 得到卡端口对应的ip
	 */
	String getPortLinkedIp(String port);
	//-------------bo的PoliceCallin------------------------
	/*
	 * id is 业务的标识
	 */
	void setPcInstance(String id);
	/*
	 * 设置标识
	 */
	void setPcFuzzNum(String id,String fuzzNum);
	/*
	 * 取出验证次数
	 */
	int getPcNum(String id);
	/*
	 * 设置验证次数
	 * i is 验证次数。
	 */
	void setPcNum(String id,int i);
	/*
	 * 设置业务结果
	 */
	void setPcValidInfo(String id,String validInfo);
	/*
	 * 设置座席员
	 */
	void setPcOperatorNum(String id,String operatorNum);
	/*
	 * 存储信息到数据库,并将此id移除
	 */
	String savePc(String id);
//	-------------bo的PoliceCallin over------------------------
	
	String saveCclog(String id);
	String saveAll(String id);
	/*
	 * 验证座席等用户登录
	 */
	String validUser(String user,String password);
	
	public boolean insertValue(String pocid);
}
