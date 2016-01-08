/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.layerOrder.service.impl;

import java.util.Iterator;
import java.util.Map;

import et.bo.sys.layerOrder.service.LayerOrderTreeService;
import et.po.BaseTree;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 树排序相关类
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class LayerOrderTreeServiceImpl implements LayerOrderTreeService{
	private ClassTreeService classTreeService = null;
	private BaseDAO dao = null;
	private KeyService ks = null;
	/**
	 * 根据nickName调用TreeService得到相应子树
	 * @param nickName 根节点nickName
	 * @param action 节点action
	 * @param target 根节点target
	 * @return TreeService 类型树
	 * @throws
	 */
	public TreeService buildTree(String nickName,String action, String target)
	{
		return classTreeService.getSubTreeByNickName(nickName, action, target);
	}
	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	public void addParamTree(IBaseDTO dto)
	{
		this.reloadParamTree();
	}
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	public void updateParamTree(IBaseDTO dto)
	{
		this.reloadParamTree();
	}
	/**
	 * 树节点的删除
	 * @param id BaseTree的ID
	 * @return
	 * @throws
	 */
	public void removeParamTree(String id)
	{
		this.reloadParamTree();
	}
	
	/**
	 * 把树的layerOrder属性改了以完成排序功能
	 * @param ViewTree tree
	 * @return
	 * @throws
	 */
	public void updateParamTreeLayerOrder(ViewTree tree)
	{
		Map registry = tree.getRegistry();
		Iterator keys = registry.keySet().iterator();
		while(keys.hasNext())
		{
			String key = (String)keys.next();
			BaseTreeNodeService btn = ((TreeControlNodeService)registry.get(key)).getBaseTreeNodeService();
			String id = btn.getId();
			String layerOrder = btn.getLayerOrder();
			BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
			bt.setLayerOrder(layerOrder);
			dao.saveEntity(bt);
		}
		reloadParamTree();
	}
	
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}
	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
	
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	/**
	 * 对数据库进行破坏性操作需要从加载树
	 *
	 */
	private void reloadParamTree()
	{
		classTreeService.reloadParamTree();
	}
	
}
