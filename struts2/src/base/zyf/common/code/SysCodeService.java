/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 31, 200911:18:05 AM
 * ������base.zyf.common.code
 * �ļ�����SysCodeService.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.code;

import base.zyf.web.view.ComboSupportList;

/**
 * �����systree����û�ж�Ӧ��code�б����Ե��ñ��ӿڲ������������е�code
 * @author zhaoyifei
 * @version 1.0
 */
public interface SysCodeService {

	public static final String SERVICE_NAME = "sys.code.SysCodeService";
	/**
	 * 
	 * �������� ����code���ض�Ӧ��list
	 * @param code ����com.cc.sys.db.SysGroup-id-name; 
	 * 			com.cc.sys.db.SysGroup����������Ӧ����
	 * 			id name ��Ӧcode label.
	 * @return ComboSupportList
	 * May 31, 2009 11:20:31 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public ComboSupportList getlist(String code);
}
