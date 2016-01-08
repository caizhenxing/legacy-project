/**
 * className BaseTreeService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.impl;

import java.util.List;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;

/**
* �������ݿ⣬�ô˼���ֱ��������
*
* @version 	jan 24 2008 
* @author ����Ȩ
*/
public class TreeInfoServiceImpl implements TreeInfoService{
	/**
	 * �������ڵ��б� �����TreeNodeServiceԪ��
	 */
	private List treeNodeList;
	/**
	 * �������ĸ�Ԫ��
	 */
	private TreeControlNodeService root;
	/**
	 * 
	 * �õ��������ڵ��б� �����TreeNodeServiceԪ��
	 * @param
	 * @version 2008-1-4
	 * @return List �����TreeNodeServiceԪ�� ���������Ļ�����Ϣ����չ��Ϣ
	 * @throws
	 */
	public List getTreeNodeList()
	{
		return this.treeNodeList;
	}
	/**
	 * 
	 * �õ��������ڵ��б� �����TreeNodeServiceԪ��
	 * @param List nodeList �����TreeNodeServiceԪ�� ���������Ļ�����Ϣ����չ��Ϣ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setTreeNodeList(List treeNodeList)
	{
		this.treeNodeList = treeNodeList;
	}
	/**
	 * 
	 * �õ����ĸ�Ԫ��
	 * @param
	 * @version 2008-1-4
	 * @return TreeControlNodeService root ���ڵ� �Ǹ�Ԫ��
	 * @throws
	 */
	public TreeControlNodeService getRoot()
	{
		return this.root;
	}
	/**
	 * 
	 * �������ĸ�Ԫ��
	 * @param TreeControlNodeService tcns ���ڵ� ����Ϊ��Ԫ��
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setRoot(TreeControlNodeService root)
	{
		this.root = root;
	}
}
