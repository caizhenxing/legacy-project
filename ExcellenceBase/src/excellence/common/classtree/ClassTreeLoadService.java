/**
 * className ClassTreeLoadService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.classtree;

import java.util.List;

import excellence.common.tree.base.service.BaseTreeNodeService;

/**
 * �������ݿ�ΪClassTreeService�ṩ����
 * ���ݿ�����db xml��
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface ClassTreeLoadService {
	/**
	 * ��db xml�ȵ���������TreeNodeService��
	 * @return List<BaseTreeNodeService>
	 */
	List<BaseTreeNodeService> loadTreeNodeService();
	/**
	 * ��db xml�ȵ���������TreeNodeService��
	 * @param String action �ڵ���Ϊ
	 * @param String target �ڵ�Ŀ��
	 * @return List<BaseTreeNodeService>
	 */
	List<BaseTreeNodeService> loadLeafRights(String action, String target);
	
	/**
	 * ���û���Ϣ��װ��BaseTreeNodeService�������ɲ�����ʱ����ΪҶ�ӽڵ㸽�ӵ���������
	 * @param String action �ڵ���Ϊ
	 * @param String target �ڵ�Ŀ��
	 * @return List<BaseTreeNodeService> 
	 */
	List<BaseTreeNodeService> getAllUsersForDeptTree(String action, String target);
}
