/**
 * className IvrTreeService 
 * 
 * 创建日期 2008-4-1
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.service;
import java.util.List;

import et.bo.sys.basetree.service.impl.IVRTreeNode;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
/**
 * ivr树类
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface IvrTreeService {
	/**
	 * 从内存中得到树状列表的信息
	 * 
	 * @return list<TreeNode>
	 */
	public List<IVRTreeNode> getStaticTreeInfo();
	/**
	 * 得到专家列表
	 * @return List<LabelValueBean> id name
	 */
	public List<LabelValueBean> getExperterList();
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param rootId 根节点id
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree();
	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	void addParamTree(IBaseDTO dto);
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	void updateParamTree(IBaseDTO dto);
	/**
	 * 树节点的删除
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	//void removeParamTree(String id);
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
	void deleteNode(TreeControlNodeService node);
    /**
     * 根据id将BaseTree and ViewTreeDtail加载到IBaseDTO中返回给视图调用
     * @param id
     * @return
     */
   IBaseDTO loadViewBeanById(String id);
	/**
	 * 根据NickName以label Value的形式显示该树的子节点列表
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName,TreeService tree);
	/**
	 * 根据NickName以该节点的id
	 * @param String nickName,TreeService tree
	 * @return
	 * @throws
	 */
	String getIdByNidkName(String nickName,TreeService tree);
	//void reloadParamTree();
	/**
	 * 设置根节点id
	 * @param String rootId
	 * @return
	 * @throws
	 */
	void setRootId(String rootId);
	
	
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param 
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree(String nodeAction,String target);


	/**
	 * 树节点的删除
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);

	
	
	/**
	 * 得到ivr id label List给视图用 实际存的时候需要根据id找到nickName 
	 * @return List LabelValueBeanList (functype id value)
	 */
	//public List<LabelValueBean> getIvrFunctypeLVList();
	/**
	 * 得到用户列表
	 * @param
	 * @version 2008-02-15
	 * @return
	 */
	public List<LabelValueBean> getUserListselect(IBaseDTO dto);
}
