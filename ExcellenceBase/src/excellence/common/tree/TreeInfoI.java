/*
 * Created on 2005-8-20
 * To change the template for this generated file go to
 */
package excellence.common.tree;

import java.util.*;

/**
 * @author	 		:	辜晓峰
 * @project			:	隔离数据库，用此集合直接生成树。
 * @class describe	:
 * 
 */
public interface TreeInfoI{
	/*
	 * 元素为TreeNode，一般直接用TreeControlNode
	 */
	public List getTreeNodeList();
	/*
	 * 根元素
	 */
	public TreeControlNode getRoot();
}