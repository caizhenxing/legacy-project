/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 30, 200911:26:48 AM
 * ������base.zyf.hibernate.usertype
 * �ļ�����BaseUserService.java
 * �����ߣ�zhaoyifei
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
