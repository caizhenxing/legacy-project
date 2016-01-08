/**
 * className BaseTreeService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */


package excellence.common.tree.base.service;

import java.util.Map;

/**
 * 
* 是一个树接口 包含了对树的属性及其操作 增加节点、删除节点、得到父节点等
*
* @version 	jan 24 2008 
* 
* @author 王文权
*/
public interface TreeService {

    /**
     * 得到树的根节点
     * @param
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    TreeControlNodeService getRoot();
    /**
     * 设置树的根节点
     * @param TreeControlNodeService root 树节点
     * @version 2008-1-25
     * @return 
     */
    void setRoot(TreeControlNodeService root);
    /**
     * 根据id查找树的节点
     * @param String id 树节点的唯一标识
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    public TreeControlNodeService findNode(String id);
    /**
     * 根据nickName查找树的节点
     * @param String nickName 树节点呢称
     * @version 2008-1-25
     * @return TreeControlNodeService 树节点
     */
    public TreeControlNodeService findNodeByNickName(String nickName);
    /**
     * 增加树的节点
     * @param TreeControlNodeService node 树节点
     * @version 2008-1-25
     * @return
     */
    public void addNode(TreeControlNodeService node) throws IllegalArgumentException;
    /**
     * 删除树的节点
     * @param TreeControlNodeService node 树节点
     * @version 2008-1-25
     * @return
     */
    public void removeNode(TreeControlNodeService node);
    public Map getRegistry();
    /**
     * 树的深拷贝
     * @param
     * @version 2008-1-26
     * @return Object 树的实例
     */
	public Object clone() throws CloneNotSupportedException;
}
