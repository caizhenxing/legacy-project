/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.build.service;

import java.util.List;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;

/**
* ���������װ
*
* @version 	jan 01 2008 
* @author ����Ȩ
*/
public interface BuildTreeService {
	/**
	 * ע����TreeService��ʵ����
	 * @param TreeService treeService(����һ�ô���װ����)
	 * @version 2008-1-24
	 * @return
	 */
	void setTreeService(TreeService treeService);
	/**
	 * ע����TreeService��ʵ����
	 * @param TreeService treeService(����һ�ô���װ����)
	 * @version 2008-1-24
	 * @return
	 */
	TreeService creator(TreeInfoService tii);
	/**
	 * ���ӽڵ� ͬ��ѭ��list ��treeControlNode�ĺ��ӽڵ����treeControlNode��
	 * @param TreeControlNodeService ���ڵ� 
	 * @param List list �����TreeNodeServiceԪ�ش�������Ļ���������Ϣ
	 * @version 2008-1-24
	 * @return
	 */
	void addTheNode(TreeControlNodeService treeControlNode,List list);
}
