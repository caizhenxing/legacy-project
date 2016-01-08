/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.parameter.service;
import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
/**
 * 构建部门tree
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface ParameterTreeService {
	/**
	 * 构造类型树返回
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree();
	/**
	 * 根据nickName构造一刻树返回
	 * @param String nickName 根节点唯一名
	 * @param boolean rootNeedAction 根节点是否也有行为
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree(String nickName,boolean rootNeedAction);
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
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
	void deleteNode(TreeControlNodeService node);
	//void reloadParamTree();
	/**
	 * 设置根节点id
	 * @param String rootId
	 * @return
	 * @throws
	 */
	void setRootId(String rootId);
	
}
