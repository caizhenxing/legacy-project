/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.build.service;

import java.util.List;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;

/**
* 完成树的组装
*
* @version 	jan 01 2008 
* @author 王文权
*/
public interface BuildTreeService {
	/**
	 * 注入树TreeService的实现类
	 * @param TreeService treeService(这是一棵待组装的树)
	 * @version 2008-1-24
	 * @return
	 */
	void setTreeService(TreeService treeService);
	/**
	 * 注入树TreeService的实现类
	 * @param TreeService treeService(这是一棵待组装的树)
	 * @version 2008-1-24
	 * @return
	 */
	TreeService creator(TreeInfoService tii);
	/**
	 * 增加节点 同过循环list 将treeControlNode的孩子节点加入treeControlNode中
	 * @param TreeControlNodeService 树节点 
	 * @param List list 里边是TreeNodeService元素存的是树的基本属性信息
	 * @version 2008-1-24
	 * @return
	 */
	void addTheNode(TreeControlNodeService treeControlNode,List list);
}
