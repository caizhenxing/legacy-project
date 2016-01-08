/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ��ͼ���ڵ�
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ViewTreeControlNode extends TreeControlNodeServiceImpl{
	/**
	 * �õ����ڵ���
	 */
	private int width = 0;//TreeControlNodeServiceImpl
	/**
	 * �õ��ڵ������ĳһ�ڵ�Ŀ��
	 */
	private int relativeWidth = 0;
	/**
	 * �Ƿ������һ���ڵ�
	 */
	private boolean last;
	/**
	 * ��ʾͼ��
	 */
	private String icon;
	/**
	 * tmpIcon �� iconΪ������ϵ ��tmpImg��û��icon
	 * ע�⵱����tmpIconʱҪ������Ϊnull������ʾicon
	 */
	private String tmpIcon;
	/**
	 * ���캯��
	 *
	 */
	public ViewTreeControlNode()
	{

	}
	/**
	 * �ڵ��Ƿ�չ��
	 */
	protected boolean expanded = false;
	/**
	 * �ڵ��Ƿ�չ��
	 * @param
	 * @version 2008-1-24
	 * @return expanded
	 */
	public boolean isExpanded() {
		return (this.expanded);
	}
	/**
	 * �ڵ��Ƿ�չ��
	 * @param boolean expanded
	 * @version 2008-1-24
	 * @return 
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * �ڵ������һ���ڵ�
	 * @param
	 * @version 2008-1-24
	 * @return boolean last
	 */
	public boolean isLast() {
		return (this.last);
	}
	/**
	 * �ڵ������һ���ڵ�
	 * @param boolean last
	 * @version 2008-1-24
	 * @return boolean last
	 */
	void setLast(boolean last) {
		this.last = last;
	}
	
	
	/**
	 * 
	 * �����ӽڵ�
	 * @param TreeControlNodeService child Ҫ���ӵ����ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id��Ψһ�׳�
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
	 * �Ƿ���Ҷ�ӽڵ�
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
	 * ��ǰ�ڵ��Ƿ�ѡ��
	 */
	protected boolean selected = false;
	/**
	 * 
	 * �ڵ�ѡ��״̬
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
	 * �ڵ�ѡ��״̬
	 * @param boolean selected
	 * @version 2008-1-4
	 * @throws 
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * 
	 * �ڵ���
	 * @param ViewTreeControlNodeDict tcns ����ڵ�õ�����
	 * @version 2008-1-4
	 * @throws 
	 */
	int getWidth(ViewTreeControlNode tcns)
	{
		//ѭ������
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
	 * �ڵ���
	 * @param ViewTreeControlNodeDict tcns ����ڵ�õ�����
	 * @version 2008-1-4
	 * @throws 
	 */
	int getWidth(ViewTreeControlNode tcns,String rootId)
	{
		//ѭ������
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
	 * �ڵ��� �ⲿ��
	 * @param ViewTreeControlNodeDict tcns ����ڵ�õ�����
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
	 * �ڵ��� �ⲿ��
	 * @param String rootId ����ڵ�õ�����
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
	 * �ڵ���ʾͼ�� �ڲ���
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
	 * �ڵ���ʾͼ�� �ⲿ���� 
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
	 * �ڵ���Ϊ �ⲿ���� 
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
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
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
