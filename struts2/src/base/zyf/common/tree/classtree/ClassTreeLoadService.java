/**
 * className ClassTreeLoadService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 *  ��
 */
package base.zyf.common.tree.classtree;

import java.util.Map;

import base.zyf.common.tree.TreeNodeI;


/**
 * �������ݿ�ΪClassTreeService�ṩ����
 * ���ݿ�����db xml��
 *
 * @version 2
 * @author zhaoyifei
 */
public interface ClassTreeLoadService {
	public static final String SERVICE_NAME = "sys.tree.ClassTreeLoadService";
	/**
	 * ��db xml�ȵ���������TreeNodeI��
	 * @return TreeNodeI
	 */
	public void loadTreeNodeService(ClassTreeService cts);
	
}
