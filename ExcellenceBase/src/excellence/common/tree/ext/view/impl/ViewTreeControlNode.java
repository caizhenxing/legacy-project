/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.ext.view.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
import excellence.common.tree.base.impl.TreeControlNodeServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.util.ViewTreeUtil;
/**
 * 视图树节点
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ViewTreeControlNode extends TreeControlNodeServiceImpl{
	/**
	 * 得到树节点宽度
	 */
	private int width = 0;//TreeControlNodeServiceImpl
	/**
	 * 得到节点相对于某一节点的宽度
	 */
	private int relativeWidth = 0;
	/**
	 * 是否是最后一个节点
	 */
	private boolean last;
	/**
	 * 显示图表
	 */
	private String icon;
	/**
	 * tmpIcon 和 icon为排他关系 有tmpImg就没有icon
	 * 注意当不用tmpIcon时要将其设为null否则不显示icon
	 */
	private String tmpIcon;
	/**
	 * 构造函数
	 *
	 */
	public ViewTreeControlNode()
	{

	}
	/**
	 * 节点是否展开
	 */
	protected boolean expanded = false;
	/**
	 * 节点是否展开
	 * @param
	 * @version 2008-1-24
	 * @return expanded
	 */
	public boolean isExpanded() {
		return (this.expanded);
	}
	/**
	 * 节点是否展开
	 * @param boolean expanded
	 * @version 2008-1-24
	 * @return 
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * 节点是最后一个节点
	 * @param
	 * @version 2008-1-24
	 * @return boolean last
	 */
	public boolean isLast() {
		return (this.last);
	}
	/**
	 * 节点是最后一个节点
	 * @param boolean last
	 * @version 2008-1-24
	 * @return boolean last
	 */
	void setLast(boolean last) {
		this.last = last;
	}
	
	
	/**
	 * 
	 * 增加子节点
	 * @param TreeControlNodeService child 要增加的树节点
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id不唯一抛出
	 */
	public void addChild(TreeControlNodeService child) throws IllegalArgumentException {
		this.getTree().addNode(child);
		child.setParent(this);
		synchronized (this.getChildren()) {
			int n = this.getChildren().size();
			if (n > 0) {
				ViewTreeControlNode node = (ViewTreeControlNode) this.getChildren().get(n - 1);
				node.setLast(false);
			}
			((ViewTreeControlNode)child).setLast(true);
			this.getChildren().add(child);
		}
	}
	/**
	 * 
	 * 是否是叶子节点
	 * @param
	 * @version 2008-1-4
	 * @return boolean  
	 * @throws 
	 */
	public boolean isLeaf() {
			return (this.getChildren().size() < 1);
	}
	
	public String getTarget()
	{
		ViewTreeNode v = (ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService();
		return v.getTarget();
	}
	public String getAction()
	{
		ViewTreeNode v = (ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService();
		return v.getAction();
	}
	public void setAction(String action)
	{
		((ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService()).setAction(action);
	}
	/**
	 * 当前节点是否被选种
	 */
	protected boolean selected = false;
	/**
	 * 
	 * 节点选种状态
	 * @param
	 * @version 2008-1-4
	 * @return boolean  
	 * @throws 
	 */
	public boolean isSelected() {
		return (this.selected);
	}
	/**
	 * 
	 * 节点选种状态
	 * @param boolean selected
	 * @version 2008-1-4
	 * @throws 
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * 
	 * 节点宽度
	 * @param ViewTreeControlNodeDict tcns 穿入节点得到其宽度
	 * @version 2008-1-4
	 * @throws 
	 */
	int getWidth(ViewTreeControlNode tcns)
	{
		//循环调用
		width++;
		ViewTreeControlNode tcn = (ViewTreeControlNode)tcns.getParent();
		if(tcn!=null)
		{
			if("treeRoot".equals(tcn.getParentId())==false)
			{
				getWidth(tcn);
			}
		}
		return width;
	}
	/**
	 * 
	 * 节点宽度
	 * @param ViewTreeControlNodeDict tcns 穿入节点得到其宽度
	 * @version 2008-1-4
	 * @throws 
	 */
	int getWidth(ViewTreeControlNode tcns,String rootId)
	{
		//循环调用
		ViewTreeControlNode tcn = (ViewTreeControlNode)tcns.getParent();
		relativeWidth++;
		if(tcn!=null)
		{
			if(rootId.equals(tcn.getId())==false)
			{
				getWidth(tcn,rootId);
			}
		}
		return relativeWidth;
	}
	/**
	 * 
	 * 节点宽度 外部用
	 * @param ViewTreeControlNodeDict tcns 穿入节点得到其宽度
	 * @version 2008-1-4
	 * @throws 
	 */
	public int getWidth()
	{
		if(width!=0)
			width = 0;
		return getWidth(this);
	}
	/**
	 * 
	 * 节点宽度 外部用
	 * @param String rootId 穿入节点得到其宽度
	 * @version 2008-1-4
	 * @throws 
	 */
	public int getWidth(String rootId)
	{
		if(relativeWidth!=0)
			relativeWidth = 0;
		if(rootId.equals(this.getId()))
			return relativeWidth;
		return getWidth(this,rootId);
	}
	/**
	 * 
	 * 节点显示图标 内部用
	 * @param 
	 * @version 2008-1-4
	 * @throws 
	 */
	private String getIcon() {
		ViewTreeNode v = (ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService();
		return v.getIcon();
	}
	/**
	 * 
	 * 节点显示图标 外部调用 
	 * @param String key like expanded closed 
	 * @version 2008-1-4
	 * @throws 
	 */
	public String getIconByKey(String key)
	{
		//System.out.println(this.getIcon()+":"+this.getId());
		Map kv = ViewTreeUtil.parseKeyValue(this.getIcon());
		String icon = (String)kv.get(key);
		//System.out.println(this.getIcon()+":"+kv.get("leaf"));
		if(icon==null)
			return (String)kv.get("default");
		else
			return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setTmpIcon(String tmpIcon)
	{
		((ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService()).setTmpIcon(tmpIcon);
	}
	public String getTmpIcon()
	{
			return ((ViewTreeNode)this.getBaseTreeNodeService().getTreeNodeExtendedService()).getTmpIcon();
	}
	/**
	 * 
	 * 节点行为 外部调用 
	 * @param String key like href onclick 
	 * @version 2008-1-4
	 * @throws 
	 */
	public String getActionByKey(String key)
	{
		Map kv = ViewTreeUtil.parseKeyValue(this.getAction());
		String action = (String)kv.get(key);
		if(action==null)
			return null;
		else
			return action;
	}
	
	  /**
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
     * @throws CloneNotSupportedException;
     */
	public Object clone() throws CloneNotSupportedException {
		ViewTreeControlNode node = new ViewTreeControlNode();
		node.setBaseTreeNodeService((BaseTreeNodeService)this.getBaseTreeNodeService().clone());
		node.width = this.getWidth();
		node.last = this.isLast();
		node.icon = this.getIcon();
		node.tmpIcon = this.getTmpIcon();
		node.children = new ArrayList();
		Iterator i = this.children.iterator();
		while (i.hasNext()) {
			node.children.add(((ViewTreeControlNode) i.next()).clone());
		}
		return node;
	}
}
