package et.bo.sys.ivr.service;

import java.util.List;

public interface BusinessObjectService {
//	 --------���� ---------
	String SUCCEED = "0";

	String FAIL_DEFAULT = "999";

	String VALID_OK = "0";

	String VALID_BLACKLIST = "1";

	String VALID_FAIL = "2";

	// --------���� over---------
	// -------------validate------------------
	/**
	 * ��֤�˵绰�Ƿ�Ϊ�������û�
	 * 
	 * @param phoneNum
	 *            �绰����
	 * @return 0 ��ʾ��֤ͨ�� 1 ��ʾ��֤δͨ��
	 */
	String blacklist(String phoneNum);



}
