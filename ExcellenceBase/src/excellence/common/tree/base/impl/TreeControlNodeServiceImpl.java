/**
 * className BaseTreeService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
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
* 树节点的实现封装了对树的操作 增加节点、删除节点、得到夫节点等
*
* @version 	jan 24 2008 
* @author 王文权
*/
public class TreeControlNodeServiceImpl implements TreeControlNodeService {
	
	/**
	 * 构造函数
	 */
	public TreeControlNodeServiceImpl()
	{
		
	}
	// ----------------------------------------------------- Instance Variables
	
	/**
	 * 树孩子节点List 里边是树节点集合
	 */
	protected List children = new ArrayList();
	/**
	 * 本节点所属的树
	 */
	private TreeService tree;
	/**
	 * 树父节点
	 */
	private TreeControlNodeService parent;
	/**
	 * 树属性定义注入里边
	 */
	private BaseTreeNodeService treeNodeService;
	
	/**
	 * 
	 * 增加子节点
	 * @param TreeControlNodeService child 新的树节点
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id不唯一抛出
	 */
	public void addChild(TreeControlNodeService child) throws IllegalArgumentException {
		
		// TODO Auto-generated method stub
		tree.addNode(child);
		child.setParent(this);
		
		//防止并发访问
		synchronized (children) {
			children.add(child);
		}
	}
	/**
	 * 
	 * 在指定位置增加子节点
	 * @param int offset 在哪个位置增加节点
	 * @param TreeControlNodeService child 要增加的树节点
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id不唯一抛出
	 */
	public void addChild(int offset, TreeControlNodeService child)throws IllegalArgumentException {
		
		// TODO Auto-generated method stub
		tree.addNode(child);
		child.setParent(this);
		
		//防止并发访问
		synchronized (children) {
			children.add(offset, child);
		}
	}

	
	/**
	 * 
	 * 得到孩子节点集合
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService[] 树节点数组
	 * @throws
	 */
	public TreeControlNodeService[] findChildren() {
		
		// TODO Auto-generated method stub
		
		//防止并发访问
		synchronized (children) {
			TreeControlNodeService results[] = new TreeControlNodeService[children.size()];

			return ((TreeControlNodeService[]) children.toArray(results));
		}
	}
	/**
	 * 
	 * 得到树孩子节点列表
	 * @version 2008-1-4
	 * @return List 树节点树孩子节点集合
	 */
	public List getChildren() {
		
		// TODO Auto-generated method stub
		return this.children;
	}

	/**
	 * 
	 * 得到树的唯一标识
	 * @param 
	 * @version 2008-1-4
	 * @return id 树的唯一标识
	 * @throws
	 */
	public String getId() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getId();
	}
	/**
	 * 
	 * 得到树的父节点
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService 树父节点
	 * @throws
	 */
	public TreeControlNodeService getParent() {
		
		// TODO Auto-generated method stub
		return this.parent;
	}
	/**
	 * 
	 * 得到树节点的父节点Id
	 * @param
	 * @version 2008-1-4
	 * @return String parentId 树父节点Id
	 * @throws
	 */
	public String getParentId() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getParentId();
	}
	
	/**
	 * 
	 * 得到树节点类型
	 * @param 
	 * @version 2008-1-4
	 * @return String type 树类型
	 * @throws
	 */
	public String getType()
	{
		return this.getBaseTreeNodeService().getType();
	}
	/**
	 * 
	 * 得到树节点备注信息
	 * @param 
	 * @version 2008-1-4
	 * @return String remark 得到树节点备注信息
	 * @throws
	 */
	public String getReamrk()
	{
		return this.getBaseTreeNodeService().getRemark();
	}
	/**
	 * 
	 * 得到树TreeService 是一棵树
	 * @param 
	 * @version 2008-1-4
	 * @return TreeService 是一棵树
	 * @throws
	 */
	public TreeService getTree() {
		
		// TODO Auto-generated method stub
		return this.tree;
	}
	/**
	 * 
	 * 得到BaseTreeNodeService 是里边有树基本属性及扩展属性的定义注入它树节点就有了id,parntId等树的基本信息同时也有了扩展信息
	 * @param 
	 * @version 2008-1-4
	 * @return TreeNodeService treeNodeService 是树基本属性的和扩展属性的定义包含id,parentId,label等
	 * @throws
	 */
	public BaseTreeNodeService getBaseTreeNodeService() {
		
		// TODO Auto-generated method stub
		return this.treeNodeService;
	}

	/**
	 * 
	 * 删除节点
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
	 * 删除节点
	 * @param int offset
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(int offset) {
		
		// TODO Auto-generated method stub
		synchronized (children) {
			TreeControlNodeService child = (TreeControlNodeService) children.get(offset);
			
			//树递归删除child
			tree.removeNode(child);
			child.setParent(null);
			children.remove(offset);
		}
	}
	/**
	 * 
	 * 根据所传的孩子节点将其从本节点的孩子节点中删除 前提待删除节点child的孩子节点都删除了　使用时要注意
	 * @param TreeControlNodeService child 要删除的孩子节点
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(TreeControlNodeService child) {
		
		// TODO Auto-generated method stub
		if (child == null) {
			return;
		}
		//防止并发访问
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
	 * 设置孩子节点集合
	 * @param ArrayList list 元素是孩子节点
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
	 * 设置树节点的名称
	 * @param String label 树节点的显示名称
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
	 * 得到树节点的名称
	 * @version 2008-1-4
	 * @return String label 树节点的显示名称
	 * @throws
	 */
	public String getLabel() {
		
		// TODO Auto-generated method stub
		return this.getBaseTreeNodeService().getLabel();
	}
	/**
	 * 
	 * 设置树节点的Id
	 * @param String id 树节点的唯一标识
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
	 * 设置树的父节点
	 * @param　TreeControlNodeService parent 待设置树的父节点
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
	 * 设置树的父节点Id
	 * @param　String parentId 设置树的父节点Id
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
	 * 设置树 TreeService 是一棵树
	 * @param TreeService tree 是一棵树
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
	 * 设置BaseTreeNodeService 是里边有树基本属性及扩展属性的定义注入它树节点就有了id,parntId等树的基本信息同时也有了扩展信息
	 * @param BaseTreeNodeService treeNodeService 是树基本属性的和扩展属性的定义包含id,parentId,label等
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
	 * 比较两个树节点的大小
	 * @param Object o 是树节点
	 * @version 2008-1-4
	 * @return int -1,0,1 分别对应 小于,等于,大于
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
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
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
