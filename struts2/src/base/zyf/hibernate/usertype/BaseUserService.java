/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 30, 200911:26:48 AM
 * 包名：base.zyf.hibernate.usertype
 * 文件名：BaseUserService.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public interface BaseUserService {

	public static final String SERVICE_NAME = "hibernate.BaseUserService";
	public String getNameById(String id);
}
