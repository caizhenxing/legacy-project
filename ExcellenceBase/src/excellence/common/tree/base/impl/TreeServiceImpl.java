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

import java.util.HashMap;
import java.util.Iterator;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;

/**
* ��һ�����ӿ� �����˶��������Լ������ ���ӽڵ㡢ɾ���ڵ㡢�õ����ڵ��
*
* @version 	jan 24 2008 
* 
* @author ����Ȩ
*/
public class TreeServiceImpl implements TreeService {
//	 ----------------------------------------------------------- Constructors
    /**
     * ���캯��
     */
    public TreeServiceImpl() {
        super();
        setRoot(null);
    }
   /**
    * ����TreeServiceImpl
    * @param TreeControlNodeService root ���ڵ�
    */
    public TreeServiceImpl(TreeControlNodeService root) {
        setRoot(root);
    }
    // ----------------------------------------------------- Instance Variables
    /**
     * ���ڵ�ļ��� key �ǽڵ��id value�ǽڵ�TreeControlNodeService��������
     */
    protected HashMap registry = new HashMap();
    /**
     * ���ĸ��ڵ�
     */
    protected TreeControlNodeService root = null;
    
    
    
    
    /**
     * �õ����ĸ��ڵ�
     * @param
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
     */
    public TreeControlNodeService getRoot() {
        return (this.root);
    }
    /**
     * �������ĸ��ڵ�
     * @param TreeControlNodeService root ���ڵ�
     * @version 2008-1-25
     * @return 
     */
    public void setRoot(TreeControlNodeService root){
       
    	//�������ڵ��Ƿ�Ϊ�ղ���ɾ��
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
     * ���ݽڵ�id�ҳ��ڵ�
     * @param String id ���ڵ��Ψһ��ʶ
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
     */
    public TreeControlNodeService findNode(String id) {
    	
    	//��ֹ��������
        synchronized (registry) {
            return ((TreeControlNodeService) registry.get(id));
        }
    }
    
    /**
     * ����nickName�������Ľڵ�
     * @param String nickName ���ڵ��س�
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
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
     * �������ڵ�
     * @param TreeControlNodeService node ���ڵ�
     * @version 2008-1-25
     * @return
     * @throws IllegalArgumentException �ڵ�id�ظ����쳣
     */
    public void addNode(TreeControlNodeService node) throws IllegalArgumentException {
        synchronized (registry) {
            String id = node.getId();
            
            //����id�Ƿ�Ψһ
            if (registry.containsKey(id))
                throw new IllegalArgumentException("node id '" + id +
                                                   "' is not unique");
            node.setTree(this);
            registry.put(id, node);
        }
    }

    /**
     * ���ݴ���ڵ�ݹ�ɾ������ڵ㼰���ӽڵ�
     * @param TreeControlNodeService node ���ڵ�
     * @version 2008-1-25
     * @return
     */
    public void removeNode(TreeControlNodeService node) {
    	
    	//��ֹ��������
        synchronized (registry) {
            TreeControlNodeService children[] = node.findChildren();
            
            //ѭ���ݹ�ɾ�����ӽڵ㡡��ɾ��Ҷ�ӽڵ���ɾ�����ڵ�
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
	 * �õ����ڵ㼯����map����
	 * @param
	 * @version 2008-1-25
	 * @return Map  key �ǽڵ��id value�ǽڵ�TreeControlNodeService��������
	 */
	public HashMap getRegistry() {
		return registry;
	}
	/**
	 * �����ڵ㼯������map�� ���������������Ҫ�Ǹ���clone()�õ�
	 * @param HashMap map key �ǽڵ��id value�ǽڵ�TreeControlNodeService��������
	 * @version 2008-1-25
	 * @return 
	 */
	protected void setRegistry(HashMap map) {
		registry = map;
	}
	/**
	 * �������ʵ��
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
		
		// TODO Auto-generated method stub
		TreeServiceImpl tcl=new TreeServiceImpl((TreeControlNodeService) this.root.clone());
		tcl.setRegistry(new HashMap(this.registry));
		return tcl;
	}
}
