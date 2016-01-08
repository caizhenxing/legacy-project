package et.bo.sys.ivr.service;

import java.util.List;

public interface BusinessObjectService {
//	 --------常量 ---------
	String SUCCEED = "0";

	String FAIL_DEFAULT = "999";

	String VALID_OK = "0";

	String VALID_BLACKLIST = "1";

	String VALID_FAIL = "2";

	// --------常量 over---------
	// -------------validate------------------
	/**
	 * 验证此电话是否为黑名单用户
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @return 0 表示验证通过 1 表示验证未通过
	 */
	String blacklist(String phoneNum);



}
