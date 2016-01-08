/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.classtree;

import java.util.List;
import java.util.NoSuchElementException;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
/**
 * 加载树取子树等操作
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface ClassTreeService {
	/**
	 * 根据id得到树节点nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException 节点没找到
	 */
	String getIdByNickname(String nickName);
	/**
	 * 返回树中所有节点
	 * @return
	 */
	TreeService getParamTree();
	/**
	 * 根据id得到树节点nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException 节点没找到
	 */
	String getNickNameById(String id);
	/**
	 * 根据id得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	TreeControlNodeService getNodeById(String id);
	/**
	 * 根据id得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	String getLabelById(String id);

	/**
	 * 根据nickName得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	TreeControlNodeService getNodeByNickName(String nickName);
	/**
	 * 根据nickName得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	String getLabelByNickName(String nickName);
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName);
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param boolean needRoot 是否需要根节点
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName,boolean needRoot);
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param String changeRootId 修改根节点Id
	 * @param String changeRootLabel 修改根节点Label
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName,String changeRootId,String changeRootLabel);
	/**
	 * 将树载入内存中
	 */
	void loadParamTree();
	/**
	 * 重新载入树
	 */
	void reloadParamTree();
	/**
	 * 提取子树
	 * @param nickName
	 * @return
	 */
	TreeService getSubTreeByNickName(String nickName);
	
    /**
     * 取子树
     * @param nickName 根节点
     * @param action 新动作
     * @param target 新目标
     * @return
     */
	TreeService getSubTreeByNickName(String nickName,String action, String target);
	
	/**
	 * 设置树节点的action行为
	 * @param action 行为
	 * @param TreeService tree
	 */
	void setNodeActionFromTree(String action,TreeService tree);
	
	/**
	 * 
	 * 将叶子节点加到模块树里供叶子节点授权使用
	 * @param TreeService tree
	 */
	TreeService addLeafs2Tree(TreeService tree,String action,String target);
	/**
	 * 
	 * 将部门人员节点加到模块树里供部门批量授权使用
	 * @param TreeService tree
	 */
	TreeService addPersons2Tree(TreeService tree,String action,String target);
	/**
	 * 像数据库里记录日志信息
	 * @param TreeContorlNodeService node
	 */
	//void log2db(TreeControlNodeService node,Map otherInfoMap);
}
