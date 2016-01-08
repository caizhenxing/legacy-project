/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.build.impl;

import java.util.List;

import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
/**
 * 完成树的组装
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class BuildTreeServiceImpl implements BuildTreeService {
	/**
	 * 注入树TreeService的实现类,可用spring注入实现
	 */
	private TreeService treeService;
	/**
	 * 注入树TreeService的实现类
	 * @param TreeService treeService(这是一棵待组装的树)
	 * @version 2008-1-24
	 * @return
	 */
	public void setTreeService(TreeService treeService)
	{
		this.treeService = treeService;
	}
	/**
	 * 得到树TreeService的实现类由BuildTreeService内部使用
	 * @version 2008-1-24
	 * @return TreeService treeService(这是一棵待组装的树)
	 */
	private TreeService getTreeService()
	{
		return this.treeService;
	}
	/**
	 * 组装树 根据TreeInfoService 调用addTheNode组装树返回
	 * @param TreeInfoService tii(存的是root treeList)
	 * @version 2008-1-24
	 * @return TreeService 返回的是树
	 */
	public TreeService creator(TreeInfoService tis) {
		
		// TODO Auto-generated method stub
		if(this.getTreeService()==null)
			throw new NullPointerException("TreeService为空 请调用setTreeService(TreeService treeService)注入树TreeService");
		TreeService treeService= this.getTreeService();
		treeService.setRoot(tis.getRoot());
		if(treeService.getRoot()==null)
			throw new NullPointerException("在BuildTreeService里调用creator(TreeInfoService tis)的tis里的*<树根节点是空>*错误");
		addTheNode(treeService.getRoot(),tis.getTreeNodeList());
    	return treeService;
	}
	/**
	 * 增加节点 同过循环list 将treeControlNode的孩子节点加入treeControlNode中
	 * @param TreeControlNodeService 树节点 
	 * @param List list 里边是BaseTreeNodeService元素存的是树的基本属性信息
	 * @version 2008-1-24
	 * @return
	 */
	public void addTheNode(TreeControlNodeService treeControlNode, List list) {
		
		// TODO Auto-generated method stub

		//先增加本节点在增加孩子节点
		
		String id=treeControlNode.getId();
		for(int i=0;i<list.size();i++){
			//System.out.println(list.get(i).getClass()+"<<<<<<<<<<<<<<<<<<<<<<<");
			BaseTreeNodeService btns=(BaseTreeNodeServiceImpl)list.get(i);
			
			//看看是不是当前节点的孩子节点
			if(id.equals(btns.getParentId())){//
				try
				{
					
					//System.out.println("id"+id+"<>parentId"+tns.getBaseTreeNodeService().getParentId()+"<>"+tns.getBaseTreeNodeService().getId());
					TreeControlNodeService tcn = treeControlNode.getClass().newInstance();
					//System.out.println(tcn.getClass());
					tcn.setBaseTreeNodeService(btns);
					treeControlNode.addChild(tcn);
					
					//递归增加孩子节点
					addTheNode(tcn,list);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
