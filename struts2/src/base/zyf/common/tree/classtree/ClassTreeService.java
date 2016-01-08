/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 *  。
 */
package base.zyf.common.tree.classtree;

import java.util.Map;
import java.util.NoSuchElementException;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.classtree.impl.ClassTreeServiceImpl;
import base.zyf.web.view.ComboSupportList;

/**
 * 加载树取子树等操作
 *
 * @version 2
 * @author zhaoyifei
 */
public interface ClassTreeService {
	
	
	/**
	 * 类型树根节点名字 定死了
	 */
	public static final String paramTreeRootNickName = "paramTreeRoot";
	
	public static final String SERVICE_NAME = "sys.tree.ClassTreeService";
	/**
	 * 根据id得到树节点nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getIdByNickname(String nickName);
	/**
	 * 返回树中所有节点
	 * @return
	 */
	public TreeNodeI getParamTree();
	
	/**
	 * 根据id得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeNodeI getNodeById(String id);
	/**
	 * 根据id得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelById(String id);

	/**
	 * 根据nickName得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeNodeI getNodeByNickName(String nickName);
	/**
	 * 根据nickName得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelByNickName(String nickName);
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @return ComboSupportList
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName);
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param boolean needRoot 是否需要根节点
	 * @return ComboSupportList
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName,boolean needRoot);
	/**
	 * 将树载入内存中
	 */
	void loadParamTree();
	/**
	 * 重新载入树
	 */
	public void reloadParamTree();
	
	public void setTreeId(Map<String, TreeNodeI> treeId);
	
	public void setTreeNick(Map<String, TreeNodeI> treeNick);
 
	public void setParamTree(TreeNodeI paramTree);
}
