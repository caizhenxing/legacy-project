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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeNodeExtendedService;
import excellence.common.tree.base.service.TreeService;


/**
* ���ڵ��ʵ�ַ�װ�˶����Ĳ��� ���ӽڵ㡢ɾ���ڵ㡢�õ���ڵ��
*
* @version 	jan 24 2008 
* @author ����Ȩ
*/
public class TreeControlNodeServiceImpl implements TreeControlNodeService {
	
	/**
	 * ���캯��
	 */
	public TreeControlNodeServiceImpl()
	{
		
	}
	// ----------------------------------------------------- Instance Variables
	
	/**
	 * �����ӽڵ�List ��������ڵ㼯��
	 */
	protected List children = new ArrayList();
	/**
	 * ���ڵ���������
	 */
	private TreeService tree;
	/**
	 * �����ڵ�
	 */
	private TreeControlNodeService parent;
	/**
	 * �����Զ���ע�����
	 */
	private BaseTreeNodeService treeNodeService;
	
	/**
	 * 
	 * �����ӽڵ�
	 * @param TreeControlNodeService child �µ����ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id��Ψһ�׳�
	 */
	public void addChild(TreeControlNodeService child) throws IllegalArgumentException {
		
		// TODO Auto-generated method stub
		tree.addNode(child);
		child.setParent(this);
		
		//��ֹ��������
		synchronized (children) {
			children.add(child);
		}
	}
	/**
	 * 
	 * ��ָ��λ�������ӽڵ�
	 * @param int offset ���ĸ�λ�����ӽڵ�
	 * @param TreeControlNodeService child Ҫ���ӵ����ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id��Ψһ�׳�
	 */
	public void addChild(int offset, TreeControlNodeService child)throws IllegalArgumentException {
		
		// TODO Auto-generated method stub
		tree.addNode(child);
		child.setParent(this);
		
		//��ֹ��������
		synchronized (children) {
			children.add(offset, child);
		}
	}

	
	/**
	 * 
	 * �õ����ӽڵ㼯��
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService[] ���ڵ�����
	 * @throws
	 */
	public TreeControlNodeService[] findChildren() {
		
		// TODO Auto-generated method stub
		
		//��ֹ��������
		synchronized (children) {
			TreeControlNodeService results[] = new TreeControlNodeService[children.size()];

			return ((TreeControlNodeService[]) children.toArray(results));
		}
	}
	/**
	 * 
	 * �õ������ӽڵ��б�
	 * @version 2008-1-4
	 * @return List ���ڵ������ӽڵ㼯��
	 */
	public List getChildren() {
		
		// TODO Auto-generated method stub
		return this.children;
	}

	/**
	 * 
	 * �õ�����Ψһ��ʶ
	 * @param 
	 * @version 2008-1-4
	 * @return id ����Ψһ��ʶ
	 * @throws
	 */
	public String getId() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getId();
	}
	/**
	 * 
	 * �õ����ĸ��ڵ�
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService �����ڵ�
	 * @throws
	 */
	public TreeControlNodeService getParent() {
		
		// TODO Auto-generated method stub
		return this.parent;
	}
	/**
	 * 
	 * �õ����ڵ�ĸ��ڵ�Id
	 * @param
	 * @version 2008-1-4
	 * @return String parentId �����ڵ�Id
	 * @throws
	 */
	public String getParentId() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getParentId();
	}
	
	/**
	 * 
	 * �õ����ڵ�����
	 * @param 
	 * @version 2008-1-4
	 * @return String type ������
	 * @throws
	 */
	public String getType()
	{
		return this.getBaseTreeNodeService().getType();
	}
	/**
	 * 
	 * �õ����ڵ㱸ע��Ϣ
	 * @param 
	 * @version 2008-1-4
	 * @return String remark �õ����ڵ㱸ע��Ϣ
	 * @throws
	 */
	public String getReamrk()
	{
		return this.getBaseTreeNodeService().getRemark();
	}
	/**
	 * 
	 * �õ���TreeService ��һ����
	 * @param 
	 * @version 2008-1-4
	 * @return TreeService ��һ����
	 * @throws
	 */
	public TreeService getTree() {
		
		// TODO Auto-generated method stub
		return this.tree;
	}
	/**
	 * 
	 * �õ�BaseTreeNodeService ����������������Լ���չ���ԵĶ���ע�������ڵ������id,parntId�����Ļ�����ϢͬʱҲ������չ��Ϣ
	 * @param 
	 * @version 2008-1-4
	 * @return TreeNodeService treeNodeService �����������Եĺ���չ���ԵĶ������id,parentId,label��
	 * @throws
	 */
	public BaseTreeNodeService getBaseTreeNodeService() {
		
		// TODO Auto-generated method stub
		return this.treeNodeService;
	}

	/**
	 * 
	 * ɾ���ڵ�
	 * @param 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void remove() {
		
		// TODO Auto-generated method stub
		if (tree != null) {
			tree.removeNode(this);
		}
	}
	/**
	 * 
	 * ɾ���ڵ�
	 * @param int offset
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(int offset) {
		
		// TODO Auto-generated method stub
		synchronized (children) {
			TreeControlNodeService child = (TreeControlNodeService) children.get(offset);
			
			//���ݹ�ɾ��child
			tree.removeNode(child);
			child.setParent(null);
			children.remove(offset);
		}
	}
	/**
	 * 
	 * ���������ĺ��ӽڵ㽫��ӱ��ڵ�ĺ��ӽڵ���ɾ�� ǰ���ɾ���ڵ�child�ĺ��ӽڵ㶼ɾ���ˡ�ʹ��ʱҪע��
	 * @param TreeControlNodeService child Ҫɾ���ĺ��ӽڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(TreeControlNodeService child) {
		
		// TODO Auto-generated method stub
		if (child == null) {
			return;
		}
		//��ֹ��������
		synchronized (children) {
			int n = children.size();
			for (int i = 0; i < n; i++) {
				if (child == (TreeControlNodeService) children.get(i)) {
					children.remove(i);
					return;
				}
			}
		}
	}
	/**
	 * 
	 * ���ú��ӽڵ㼯��
	 * @param ArrayList list Ԫ���Ǻ��ӽڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setChildren(ArrayList list) {
		
		// TODO Auto-generated method stub
		this.children = list;
	}
	/**
	 * 
	 * �������ڵ������
	 * @param String label ���ڵ����ʾ����
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setLabel(String label) {
		
		// TODO Auto-generated method stub
		this.getBaseTreeNodeService().setLabel(label);
	}
	/**
	 * 
	 * �õ����ڵ������
	 * @version 2008-1-4
	 * @return String label ���ڵ����ʾ����
	 * @throws
	 */
	public String getLabel() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getLabel();
	}
	/**
	 * 
	 * �������ڵ��Id
	 * @param String id ���ڵ��Ψһ��ʶ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setId(String id) {
		
		// TODO Auto-generated method stub
		this.getBaseTreeNodeService().setId(id);
	}
	/**
	 * 
	 * �������ĸ��ڵ�
	 * @param��TreeControlNodeService parent ���������ĸ��ڵ�
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setParent(TreeControlNodeService parent) {
		
		// TODO Auto-generated method stub
		this.parent = parent;
	}
	/**
	 * 
	 * �������ĸ��ڵ�Id
	 * @param��String parentId �������ĸ��ڵ�Id
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setParentId(String parentId) {
		
		// TODO Auto-generated method stub
		this.getBaseTreeNodeService().setParentId(parentId);
	}

	/**
	 * 
	 * ������ TreeService ��һ����
	 * @param TreeService tree ��һ����
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setTree(TreeService tree) {
		
		// TODO Auto-generated method stub
		this.tree = tree;
	}
	/**
	 * 
	 * ����BaseTreeNodeService ����������������Լ���չ���ԵĶ���ע�������ڵ������id,parntId�����Ļ�����ϢͬʱҲ������չ��Ϣ
	 * @param BaseTreeNodeService treeNodeService �����������Եĺ���չ���ԵĶ������id,parentId,label��
	 * @version 2008-1-4
	 * @return void
	 * @throws
	 */
	public void setBaseTreeNodeService(BaseTreeNodeService treeNodeService) {
		
		// TODO Auto-generated method stub
		this.treeNodeService = treeNodeService;
	}
	/**
	 * 
	 * �Ƚ��������ڵ�Ĵ�С
	 * @param Object o �����ڵ�
	 * @version 2008-1-4
	 * @return int -1,0,1 �ֱ��Ӧ С��,����,����
	 * @throws
	 */
	public int compareTo(Object o) {
		
		// TODO Auto-generated method stub
		TreeControlNodeServiceImpl tcn = (TreeControlNodeServiceImpl) o;
		String layerOrder = this.getBaseTreeNodeService().getLayerOrder();
		if(layerOrder == null)
		{
			layerOrder = "";
		}
		return layerOrder.compareTo(tcn.getBaseTreeNodeService().getLayerOrder());

	}
    /**
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
     * @throws CloneNotSupportedException;
     */
	public Object clone() throws CloneNotSupportedException {
		TreeControlNodeServiceImpl node = new TreeControlNodeServiceImpl();
		node.setBaseTreeNodeService((BaseTreeNodeService)this.getBaseTreeNodeService().clone());
		node.children = new ArrayList();
		Iterator i = this.children.iterator();
		while (i.hasNext()) {
			node.children.add(((TreeControlNodeServiceImpl) i.next()).clone());
		}
		return node;
	}
}
