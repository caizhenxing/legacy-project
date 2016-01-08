/**
 * 
 * 项目名称：struts2
 * 制作时间：May 31, 200911:18:05 AM
 * 包名：base.zyf.common.code
 * 文件名：SysCodeService.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.code;

import base.zyf.web.view.ComboSupportList;

/**
 * 如果在systree表中没有对应的code列表，可以调用本接口查找在其他表中的code
 * @author zhaoyifei
 * @version 1.0
 */
public interface SysCodeService {

	public static final String SERVICE_NAME = "sys.code.SysCodeService";
	/**
	 * 
	 * 功能描述 根据code返回对应的list
	 * @param code 形如com.cc.sys.db.SysGroup-id-name; 
	 * 			com.cc.sys.db.SysGroup是类名，对应表名
	 * 			id name 对应code label.
	 * @return ComboSupportList
	 * May 31, 2009 11:20:31 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public ComboSupportList getlist(String code);
}
