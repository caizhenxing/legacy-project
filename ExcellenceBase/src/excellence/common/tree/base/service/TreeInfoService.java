/**
 * className BaseTreeService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.service;

import java.util.List;

/**
* �������ݿ⣬�ô˼���ֱ��������
*
* @version 	jan 24 2008 
* @author ����Ȩ
*/
public interface TreeInfoService{
	/**
	 * 
	 * �õ��������ڵ��б� �����TreeNodeServiceԪ��
	 * @param
	 * @version 2008-1-4
	 * @return List �����TreeNodeServiceԪ�� ���������Ļ�����Ϣ����չ��Ϣ
	 * @throws
	 */
	List getTreeNodeList();
	/**
	 * 
	 * �õ��������ڵ��б� �����TreeNodeServiceԪ��
	 * @param List nodeList �����TreeNodeServiceԪ�� ���������Ļ�����Ϣ����չ��Ϣ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTreeNodeList(List nodeList);
	/**
	 * 
	 * �õ����ĸ�Ԫ��
	 * @param
	 * @version 2008-1-4
	 * @return TreeControlNodeService root ���ڵ� �Ǹ�Ԫ��
	 * @throws
	 */
	TreeControlNodeService getRoot();
	/**
	 * 
	 * �������ĸ�Ԫ��
	 * @param TreeControlNodeService tcns ���ڵ� ����Ϊ��Ԫ��
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setRoot(TreeControlNodeService tcns);
}