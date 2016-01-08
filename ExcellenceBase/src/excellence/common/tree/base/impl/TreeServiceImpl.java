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

import java.util.HashMap;
import java.util.Iterator;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;

/**
* 是一个树接口 包含了对树的属性及其操作 增加节点、删除节点、得到父节点等
*
* @version 	jan 24 2008 
* 
* @author 王文权
*/
public class TreeServiceImpl implements TreeService {
//	 ----------------------------------------------------------- Constructors
    /**
     * 构造函数
     */
    public TreeServiceImpl() {
        super();
        setRoot(null);
    }
   /**
    * 构造TreeServiceImpl
    * @param TreeControlNodeService root 树节点
    */
    public TreeServiceImpl(TreeControlNodeService root) {
        setRoot(root);
    }
    // ----------------------------------------------------- Instance Variables
    /**
     * 树节点的集合 key 是节点的id value是节点TreeControlNodeService及其子类
     */
    protected HashMap registry = new HashMap();
    /**
     * 树的根节点
     */
    protected TreeControlNodeService root = null;
    
    
    
    
    /**
     * 得到树的根节点
     * @param
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    public TreeControlNodeService getRoot() {
        return (this.root);
    }
    /**
     * 设置树的根节点
     * @param TreeControlNodeService root 树节点
     * @version 2008-1-25
     * @return 
     */
    public void setRoot(TreeControlNodeService root){
       
    	//看看根节点是否为空不空删除
    	if (this.root != null)
        {
            removeNode(this.root);
        }
//    	System.out.println(root+"...................");
        if (root != null)
            addNode(root);
        this.root = root;
    }
    // --------------------------------------------------------- Public Methods
    /**
     * 根据节点id找出节点
     * @param String id 树节点的唯一标识
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    public TreeControlNodeService findNode(String id) {
    	
    	//防止并发访问
        synchronized (registry) {
            return ((TreeControlNodeService) registry.get(id));
        }
    }
    
    /**
     * 根据nickName查找树的节点
     * @param String nickName 树节点呢称
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    public TreeControlNodeService findNodeByNickName(String nickName)
    {
    	Iterator i = registry.keySet().iterator();
    	TreeControlNodeService tcn = null;
    	while(i.hasNext())
    	{
    		tcn = (TreeControlNodeService)registry.get((String)i.next());
    		//System.out.println(nickName+"::"+tcn.getTreeNodeService().getBaseTreeNodeService().getNickName());
    		if(nickName.equals(tcn.getBaseTreeNodeService().getNickName()))
    		{
    			return tcn;
    		}
    	}
    	return null;
    }
    /**
     * 增加树节点
     * @param TreeControlNodeService node 树节点
     * @version 2008-1-25
     * @return
     * @throws IllegalArgumentException 节点id重复抛异常
     */
    public void addNode(TreeControlNodeService node) throws IllegalArgumentException {
        synchronized (registry) {
            String id = node.getId();
            
            //看看id是否唯一
            if (registry.containsKey(id))
                throw new IllegalArgumentException("node id '" + id +
                                                   "' is not unique");
            node.setTree(this);
            registry.put(id, node);
        }
    }

    /**
     * 根据传入节点递归删除传入节点及其子节点
     * @param TreeControlNodeService node 树节点
     * @version 2008-1-25
     * @return
     */
    public void removeNode(TreeControlNodeService node) {
    	
    	//防止并发访问
        synchronized (registry) {
            TreeControlNodeService children[] = node.findChildren();
            
            //循环递归删除孩子节点　先删除叶子节点在删除本节点
            for (int i = 0; i < children.length; i++)
                removeNode(children[i]);
            TreeControlNodeService parent = node.getParent();
            if (parent != null) {
                parent.removeChild(node); 
            }
            node.setParent(null);
            node.setTree(null);
            if (node == this.root) {
                this.root = null;
            }
            registry.remove(node.getId());
        }
    }
	/**
	 * 得到树节点集合以map返回
	 * @param
	 * @version 2008-1-25
	 * @return Map  key 是节点的id value是节点TreeControlNodeService或其子类
	 */
	public HashMap getRegistry() {
		return registry;
	}
	/**
	 * 将树节点集合设入map中 这个方法不常用主要是给上clone()用的
	 * @param HashMap map key 是节点的id value是节点TreeControlNodeService或其子类
	 * @version 2008-1-25
	 * @return 
	 */
	protected void setRegistry(HashMap map) {
		registry = map;
	}
	/**
	 * 树的深考贝实现
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
		
		// TODO Auto-generated method stub
		TreeServiceImpl tcl=new TreeServiceImpl((TreeControlNodeService) this.root.clone());
		tcl.setRegistry(new HashMap(this.registry));
		return tcl;
	}
}
