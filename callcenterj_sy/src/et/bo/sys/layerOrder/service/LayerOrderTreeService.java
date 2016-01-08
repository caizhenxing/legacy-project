/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.layerOrder.service;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.framework.base.dto.IBaseDTO;
/**
 * 树排序相关类
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface LayerOrderTreeService {
	/**
	 * 根据nickName调用TreeService得到相应子树
	 * @param nickName 根节点nickName
	 * @param action 节点action
	 * @param target 根节点target
	 * @return TreeService 类型树
	 * @throws
	 */
	TreeService buildTree(String nickName,String action, String target);
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
	 * 把树的layerOrder属性改了以完成排序功能
	 * @param ViewTree tree
	 * @return
	 * @throws
	 */
	void updateParamTreeLayerOrder(ViewTree tree);
	/**
	 * 树节点的删除
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);
}
