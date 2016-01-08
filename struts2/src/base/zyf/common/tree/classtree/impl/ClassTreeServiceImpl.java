/**
 *  
 * 创建日期 2009-5-8
 * 
 * @version 2
 * 这个版本是在struts2上运行的，dao和keyservice和LabelValueBean取消了.
 * 
 */
package base.zyf.common.tree.classtree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.classtree.ClassTreeLoadService;
import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.web.view.ComboSupportList;
import base.zyf.web.view.OptionBean;

/**
 * 加载树取子树等操作
 *
 * @version 2
 * @author zhaoyifei
 */
public class ClassTreeServiceImpl implements ClassTreeService{
	private static TreeNodeI paramTree = null;
	
	private ClassTreeLoadService classTreeLoadService;
	
	private Map<String,TreeNodeI> treeId;
	
	private Map<String,TreeNodeI> treeNick;
	
//	private static String space=" │ ";
//	private static String labelRoot="#┬";
//	private static String labelBegin="┬";
//	//String middleHead="│┌";//┏
//	private static String middleCenter="├";//┣
//	private static String middleTail="└";//┗
//	private static String middle="│";
	
	
	
	public ClassTreeLoadService getClassTreeLoadService() {
		return classTreeLoadService;
	}
	public void setClassTreeLoadService(ClassTreeLoadService classTreeLoadService) {
		this.classTreeLoadService = classTreeLoadService;
	}
	public String getIdByNickname(String nickName)
	{
		TreeNodeI tni = this.getNodeByNickName(nickName);
		if(tni != null)
		{
			return tni.getName();
		}else
		{
			return "";
		}
		
	}
	public ComboSupportList getLabelVaList(String nickName)
	{
		//注释掉的是带根节点的
		//不带根节点
		return this.getLabelVaList(nickName, false);
	}
	
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param boolean needRoot 是否需要根节点
	 * @return List<LabelValueBean>
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName,boolean needRoot)
	{
		TreeNodeI tcns = this.getNodeByNickName(nickName);
		if(tcns == null)
			return null;
		List<TreeNodeI> nodes = tcns.getChildren();
		ComboSupportList csl = new ComboSupportList("id","label");
		csl.addAll(nodes);
		return csl;
	}
	
	
	
	
	/**
	 * 加工List<LabelValueBean> list
	 * @param list 往里边写入lv
	 * @param node 节点
	 * @param needAll 是否要第归所有子节点
	 * @param type ivr department and others
	 */
	private void recursion (ComboSupportList list, TreeNodeI node, boolean needAll)
	{
		OptionBean ob = new OptionBean();

		ob.setName(node.getLabel());
		ob.setCode(node.getName());
		List<TreeNodeI> nodes = node.getChildren();
		list.add(ob);
		if(needAll)
		for(int i=0; i<nodes.size(); i++)
		{
			recursion (list,nodes.get(i));
		}
	}
	
	/**
	 * 加工List<LabelValueBean> list
	 * @param list 往里边写入lv
	 * @param node 节点
	 */
	private void recursion (ComboSupportList list,TreeNodeI node)
	{
		recursion(list,node,false);
	}
	
	/**
	 * 根据id得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeNodeI getNodeById(String id)
	{
		if(paramTree == null){
			this.loadParamTree();
		}
		if(this.treeId.containsKey(id))
		{
			return this.treeId.get(id);
		}
		return null;
		//throw new NoSuchElementException("在ClassTreeServiceImpl 里 调用getNodeById("+id+") 的到的树节点为空error");
	}
	/**
	 * 根据id得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelById(String id)
	{
		TreeNodeI node = this.getNodeById(id);
		if(node == null){
			return "";
		}
		return node.getLabel();
	}
	
	/**
	 * 根据nickName得到树节点
	 * @param String nickName
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeNodeI getNodeByNickName(String nickName)
	{
		if(paramTree == null)
		{
			this.loadParamTree();
		}
		if(this.treeNick.containsKey(nickName))
		{
			return this.treeNick.get(nickName);
		}
		return null;
		
	}
	/**
	 * 根据nickName得到树节点label
	 * @param id
	 * @return label
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelByNickName(String nickName)
	{
		TreeNodeI tni = this.getNodeByNickName(nickName);
		if(tni != null)
		{
			return tni.getLabel();
		}else
		{
			return "";
		}
	}
	@SuppressWarnings("unchecked")
	public void loadParamTree()
	{
		if (paramTree == null) {
			this.classTreeLoadService.loadTreeNodeService(this);
		}
	}
	/**
	 * 树从加载
	 */
	public void reloadParamTree()
	{
		this.paramTree = null;
		loadParamTree();
	}
	
	/**
	 * 返回树中所有节点
	 * @return
	 */
	public TreeNodeI getParamTree()
	{
		return this.paramTree;
	}
	/**
	 * @return the treeId
	 */
	public Map<String, TreeNodeI> getTreeId() {
		return treeId;
	}
	/**
	 * @param treeId the treeId to set
	 */
	public void setTreeId(Map<String, TreeNodeI> treeId) {
		this.treeId = treeId;
	}
	/**
	 * @return the treeNick
	 */
	public Map<String, TreeNodeI> getTreeNick() {
		return treeNick;
	}
	/**
	 * @param treeNick the treeNick to set
	 */
	public void setTreeNick(Map<String, TreeNodeI> treeNick) {
		this.treeNick = treeNick;
	}
	/**
	 * @param paramTree the paramTree to set
	 */
	public void setParamTree(TreeNodeI paramTree) {
		ClassTreeServiceImpl.paramTree = paramTree;
	}

	
}
