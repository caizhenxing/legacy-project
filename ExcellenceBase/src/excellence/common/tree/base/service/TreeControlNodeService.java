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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* ��һ�����ڵ� �����˶����ڵ�����Լ������ ���ӽڵ㡢ɾ���ڵ㡢�õ����ڵ��
*
* @version 	jan 24 2008 
* @author ����Ȩ
*/
public interface TreeControlNodeService  extends Comparable , Serializable, Cloneable{

	/**
	 * 
	 * ����BaseTreeNodeService ����������������Լ���չ���ԵĶ���ע�������ڵ������id,parntId�����Ļ�����ϢͬʱҲ������չ��Ϣ
	 * @param BaseTreeNodeService treeNodeService �����������Եĺ���չ���ԵĶ������id,parentId,label��
	 * @version 2008-1-4
	 * @return void
	 * @throws
	 */
	void setBaseTreeNodeService(BaseTreeNodeService treeNodeService);
	/**
	 * 
	 * �õ�BaseTreeNodeService ����������������Լ���չ���ԵĶ���ע�������ڵ������id,parntId�����Ļ�����ϢͬʱҲ������չ��Ϣ
	 * @param 
	 * @version 2008-1-4
	 * @return BaseTreeNodeService treeNodeService �����������Եĺ���չ���ԵĶ������id,parentId,label��
	 * @throws
	 */
	BaseTreeNodeService getBaseTreeNodeService();

	// ------------------------------------------------------------- Properties
	/**
	 * 
	 * �õ����ڵ�����
	 * @param 
	 * @version 2008-1-4
	 * @return String type ������
	 * @throws
	 */
	String getType();
	/**
	 * 
	 * �õ����ڵ㱸ע��Ϣ
	 * @param 
	 * @version 2008-1-4
	 * @return String remark �õ����ڵ㱸ע��Ϣ
	 * @throws
	 */
	String getReamrk();
	/**
	 * 
	 * �õ����ĸ��ڵ�
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService ���ڵ�
	 * @throws
	 */
	TreeControlNodeService getParent();
	
	/**
	 * 
	 * �������ĸ��ڵ�
	 * @param  TreeControlNodeService parent ���ڵ�
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setParent(TreeControlNodeService parent);

	/**
	 * 
	 * �õ���TreeService ��һ����
	 * @param 
	 * @version 2008-1-4
	 * @return TreeService ��һ����
	 * @throws
	 */
	TreeService getTree();
	/**
	 * 
	 * ������ TreeService ��һ����
	 * @param TreeService tree ��һ����
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTree(TreeService tree);
	// --------------------------------------------------------- Public Methods

	/**
	 * 
	 * �����ӽڵ�
	 * @param TreeControlNodeService child �µ����ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id��Ψһ�׳�
	 */
	public void addChild(TreeControlNodeService child) throws IllegalArgumentException;

	/**
	 * 
	 * ��ָ��λ�������ӽڵ�
	 * @param int offset ���ĸ�λ�����ӽڵ�
	 * @param TreeControlNodeService child Ҫ���ӵ����ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id��Ψһ�׳�
	 */
	public void addChild(int offset, TreeControlNodeService child)throws IllegalArgumentException;

	/**
	 * 
	 * �õ����ӽڵ㼯��
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService[] ���ڵ�����
	 * @throws
	 */
	public TreeControlNodeService[] findChildren();

	/**
	 * 
	 * ɾ�����ڵ�
	 * @param 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void remove();

	/**
	 * 
	 * ����λ��ɾ���ڵ�
	 * @param int offset ɾ���ڼ������ӽڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(int offset);

	// -------------------------------------------------------- Package Methods

	/**
	 * 
	 * ���ݴ��뺢�ӽڵ�ɾ�� ǰ���ɾ���ڵ�child�ĺ��ӽڵ㶼ɾ���� ʹ��ʱҪע��
	 * @param TreeControlNodeService child ��ɾ���������ӽڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void removeChild(TreeControlNodeService child);

	/**
	 * 
	 * �õ����ӽڵ�
	 * @param 
	 * @version 2008-1-4
	 * @return List ���ӽڵ㼯��
	 * @throws
	 */
	List getChildren();


	/**
	 * 
	 * ���ú��ӽڵ�
	 * @param ArrayList list ���ӽڵ㼯��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setChildren(ArrayList list);

	/**
	 * 
	 * �������ڵ������
	 * @param label ���ڵ������
	 * @version 2008-1-4
	 * @throws
	 */
	void setLabel(String string);
	/**
	 * 
	 * �õ����ڵ������
	 * @param 
	 * @version 2008-1-4
	 * @return String label ���ڵ������
	 * @throws
	 */
	String getLabel();
	/**
	 * 
	 * �õ�����Ψһ��ʶ
	 * @param 
	 * @version 2008-1-4
	 * @return id ����Ψһ��ʶ
	 * @throws
	 */
	public String getId();
	/**
	 * 
	 * ��������Ψһ��ʶ
	 * @param String id ����Ψһ��ʶ
	 * @version 2008-1-4
	 * @throws
	 */
	void setId(String id);

	/**
	 * 
	 * �õ����ĸ��ڵ�Id
	 * @param 
	 * @version 2008-1-4
	 * @return String parentId ���Ľڵ�ĸ��ڵ�Id
	 * @throws
	 */
	String getParentId();
	/**
	 * 
	 * �������ĸ��ڵ�Id
	 * @param��String parentId �������Ľڵ�ĸ��ڵ�Id
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setParentId(String parentId);
	/**
	 * 
	 * �Ƚ��������ڵ�Ĵ�С
	 * @param Object o �����ڵ�
	 * @version 2008-1-4
	 * @return int -1,0,1 �ֱ��Ӧ С��,����,����
	 * @throws
	 */
	int compareTo(Object o);
    /**
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
     * @throws CloneNotSupportedException;
     */
	Object clone() throws CloneNotSupportedException;
}
