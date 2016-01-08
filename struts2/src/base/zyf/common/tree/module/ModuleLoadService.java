/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 20, 200910:08:56 AM
 * ������base.zyf.common.tree.module
 * �ļ�����ModuleLoadService.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree.module;

import java.util.Map;

import base.zyf.common.tree.TreeNodeI;

/**
 * ��Ҫ�����Ǽ���ϵͳģ��
 * @author zhaoyifei
 * @version 1.0
 */
public interface ModuleLoadService {

	public static final String SERVICE_NAME = "sys.tree.ModuleLoadService";
	/**
	 * 
	 * �������� ����ģ�����������û���Ȩ�޽��м���
	 * @param userId �û�id
	 * @return
	 * May 20, 2009 10:12:19 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public Map<String, ModuleTreeRight> loadTreeByUser(String userId);
	
	/**
	 * 
	 * �������� ����������ģ����
	 * @return
	 * May 20, 2009 10:15:03 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public TreeNodeI loadTree();
}
