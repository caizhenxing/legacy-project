package et.bo.sys.right.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import et.bo.sys.common.SysStaticParameter;
import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impower.ViewTreeControlImpowerNode;


/*
 * Created on 2004-9-8
 *
 * 
 */

/**
 * @author	 		:	王文权
 * @project			:
 * @class describe	: 
 * 
 */
public class GrantHelper
{
	/**
	 * 
	 */
	public static final String IS_USER_RIGHT = "U";
	public static final String IS_GROUP_RIGHT = "G";
	public GrantHelper()
	{
		super();
	}
	public static void main(String[] args)
	{}

	//SET GROUP ICON
	private void setNodeIcon_Group(
			ViewTreeControlNode treeControlNode,
		boolean selected)
	{
		treeControlNode.setTmpIcon(selected ? SysStaticParameter.GICON: null);
	}
	//SET USER ICON
	private void setNodeIcon_User(
		ViewTreeControlNode treeControlNode,
		boolean selected)
	{
		((ViewTreeControlImpowerNode)treeControlNode).setTmpUserIcon(selected ? SysStaticParameter.UICON: null);
		if(((ViewTreeControlImpowerNode)treeControlNode).getTmpGroupIcon()==null)
			treeControlNode.setTmpIcon(selected ? SysStaticParameter.UICON: null);
	}
	//SET ROLE ICON
	private void setNodeIcon_Role(
		ViewTreeControlNode treeControlNode,
		boolean selected)
	{
		((ViewTreeControlImpowerNode)treeControlNode).setTmpUserIcon(selected ? SysStaticParameter.UICON: null);
		if(((ViewTreeControlImpowerNode)treeControlNode).getTmpGroupIcon()==null)
			treeControlNode.setTmpIcon(selected ? SysStaticParameter.RICON: null);
	}
	
	/**
	 * 给节点类型为type的元素赋图标rightIcon
	 * @param treeControlNode
	 * @param selected
	 * @param type
	 * @param rightIcon
	 */
	private void setNodeIcon_Role(
		ViewTreeControlNode treeControlNode,
		boolean selected,String type,String rightIcon)
	{
		((ViewTreeControlImpowerNode)treeControlNode).setTmpUserIcon(selected ? SysStaticParameter.UICON: null);
		if(((ViewTreeControlImpowerNode)treeControlNode).getTmpGroupIcon()==null)
			treeControlNode.setTmpIcon(selected ? SysStaticParameter.RICON: null);
	}
	//handle The Parent Icon 地归
	private void setParentIcon_Group(ViewTreeControlNode treeControlNode)
	{
		ViewTreeControlNode parent = (ViewTreeControlNode)treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_Group(parent, true); //parent is all true
		setParentIcon_Group(parent);
	}
	//handle The child Icon 地归
	private void setParentIcon_User(ViewTreeControlNode treeControlNode)
	{
		ViewTreeControlNode parent = (ViewTreeControlNode)treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_User(parent, true);
		setParentIcon_User(parent);
	}
	//handle The child Icon 地归
	private void setParentIcon_Role(ViewTreeControlNode treeControlNode)
	{
		ViewTreeControlNode parent = (ViewTreeControlNode)treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_Role(parent, true);
		setParentIcon_Role(parent);
	}
	
	/**
	 * 给节点类型为type的元素赋图标rightIcon
	 * @param treeControlNode
	 * @param type
	 * @param rightIcon
	 */
	private void setParentIcon_Role(ViewTreeControlNode treeControlNode,String type, String rightIcon)
	{
		ViewTreeControlNode parent = (ViewTreeControlNode)treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_Role(parent, true, type, rightIcon);
		setParentIcon_Role(parent, type, rightIcon);
	}
	//handle The Children  Icon all child is same
	private void setChildrenIcon_Group(
			ViewTreeControlNode treeControlNode,
		boolean selected)
	{
		List children = treeControlNode.getChildren();
		if (children.size() == 0)
			return;
		for (int i = 0; i < children.size(); i++)
		{
			ViewTreeControlNode child = (ViewTreeControlNode) children.get(i);
			setNodeIcon_Group(child, selected);
			setChildrenIcon_Group(child, selected);
		}
	}
	private void setChildrenIcon_User(
		ViewTreeControlNode treeControlNode,
		boolean selected)
	{
		List children = treeControlNode.getChildren();
		if (children.size() == 0)
			return;
		for (int i = 0; i < children.size(); i++)
		{
			ViewTreeControlNode child = (ViewTreeControlNode) children.get(i);
			setNodeIcon_User(child, selected);
			setChildrenIcon_User(child, selected);
		}
	}
	private void setChildrenIcon_Role(
			ViewTreeControlNode treeControlNode,
			boolean selected)
		{
			List children = treeControlNode.getChildren();
			if (children.size() == 0)
				return;
			for (int i = 0; i < children.size(); i++)
			{
				ViewTreeControlNode child = (ViewTreeControlNode) children.get(i);
				setNodeIcon_Role(child, selected);
				setChildrenIcon_Role(child, selected);
			}
		}
	/**
	 * 给节点类型为type的元素赋图标rightIcon
	 * @param treeControlNode
	 * @param selected
	 * @param type
	 * @param rightIcon
	 */
	private void setChildrenIcon_Role(
			ViewTreeControlNode treeControlNode,
			boolean selected,String type, String rightIcon)
		{
			List children = treeControlNode.getChildren();
			if (children.size() == 0)
				return;
			for (int i = 0; i < children.size(); i++)
			{
				ViewTreeControlNode child = (ViewTreeControlNode) children.get(i);
				setNodeIcon_Role(child, selected,type,rightIcon);
				setChildrenIcon_Role(child, selected,type,rightIcon);
			}
		}
	public void userClickIcon(ViewTreeControlNode treeControlNode)
	{
		boolean selected = SysStaticParameter.UICON.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpUserIcon());
		if(!selected)
		{
			selected = SysStaticParameter.UICON.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpIcon());
		}
		setNodeIcon_User(treeControlNode, !selected);
		if (!selected)
		{
			setParentIcon_User(treeControlNode);
		}
		setChildrenIcon_User(treeControlNode, !selected);
	}
	public void roleClickIcon(ViewTreeControlNode treeControlNode)
	{
		boolean selected = SysStaticParameter.RICON.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpUserIcon());
		if(!selected)
		{
			selected = SysStaticParameter.RICON.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpIcon());
		}
		setNodeIcon_Role(treeControlNode, !selected);
		if (!selected)
		{
			setParentIcon_Role(treeControlNode);
		}
		setChildrenIcon_Role(treeControlNode, !selected);
	}
    /**
     * 给节点类型为type的元素赋图标rightIcon
     * @param treeControlNode
     * @param type
     * @param rightIcon
     */
	public void roleClickIcon(ViewTreeControlNode treeControlNode, String type,String rightIcon)
	{
		boolean selected = rightIcon.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpUserIcon());
		if(!selected)
		{
			selected = rightIcon.equalsIgnoreCase(((ViewTreeControlImpowerNode)treeControlNode).getTmpIcon());
		}
		setNodeIcon_Role(treeControlNode, !selected,type,rightIcon);
		if (!selected)
		{
			setParentIcon_Role(treeControlNode,type,rightIcon);
		}
		setChildrenIcon_Role(treeControlNode, !selected,type,rightIcon);
	}
	/*
	 * getImpowerGroupFromTree 和 setImpowerGroupFromTree联合使用
	 * 
	 */
	private List<String> getImpowerGroupFromTree(ViewTree tree)
	{
		List<String> tmpGroup = new ArrayList<String>();
		Map registry = tree.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(key);
			if (SysStaticParameter.GICON.equalsIgnoreCase(node.getTmpIcon()))
			{
				tmpGroup.add(node.getId());
			}			
		}
		return tmpGroup;
	}
	/*
	 * 
	 */
	public void setImpowerGroupFromTree(ViewTree tree,List<String> groups)
	{
		Map registry = tree.getRegistry();
		for(int i=0; i<groups.size(); i++)
		{
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(groups.get(i));
			node.setTmpIcon(SysStaticParameter.GICON);
		}
	}
	/**
	 * 给有权限的节点设图标
	 * @param tree
	 */
	public void setImpowerGroup2TreeNode(ViewTree tree)
	{
		List<String> groups = this.getImpowerGroupFromTree(tree);
		Map registry = tree.getRegistry();
		for(int i=0; i<groups.size(); i++)
		{
			ViewTreeControlImpowerNode node = (ViewTreeControlImpowerNode)registry.get(groups.get(i));
			node.setTmpGroupIcon(SysStaticParameter.GICON);
		}
	}
	//111111111111111111111111111111
	public void groupClickIcon(ViewTreeControlNode treeControlNode)
	{
		boolean selected =
			SysStaticParameter.GICON.equalsIgnoreCase(treeControlNode.getTmpIcon());
		setNodeIcon_Group(treeControlNode, !selected);
		if (!selected)
		{
			setParentIcon_Group(treeControlNode);
		}
		setChildrenIcon_Group(treeControlNode, !selected);

	}
	/**
	 * 根据用户id得到当前用户所具有的权限列表
	 * @param String userId 根据得到user得到组权限用户权限
	 * @version 2008-1-24
	 * @return 树节点id
	 */

	//-------------------------------------------------------------------------------------
//	private String makeSql(
//		String user_group_id,
//		String mod_id,
//		String UserOrGroupRight)
//	{
//		String sql = "insert into sys_right values(";
//		sql += "'" + user_group_id + "',";
//		sql += "'" + mod_id + "',";
//		sql += "'" + UserOrGroupRight + "'";
//		sql += ")";
//		return sql;
//	}
//	public Vector getUpdateSqlSet_Group(TreeControl tc, String group_id)
//	{
//		String tmpSql =
//			" delete from sys_right where user_group_id = '" + group_id + "' ";
//		tmpSql += "and is_user_right ='"
//			+ IS_GROUP_RIGHT
//			+ "'";
//		vSql.add(tmpSql);
//		String rootName = tc.getRoot().getName();
//		HashMap registry = tc.getRegistry();
//		Iterator iterator = registry.keySet().iterator();
//		while (iterator.hasNext())
//		{
//			//
//			String key = (String) iterator.next();
//			if (rootName.equals(key))
//				continue;
//			//
//			TreeControlNode node = (TreeControlNode) registry.get(key);
//			if (ICON_GROUP.equalsIgnoreCase(node.getIcon()))
//			{
//				vSql.add(
//					makeSql(group_id, key, IS_GROUP_RIGHT));
//			}
//		}
//		return vSql;
//	}
//	public Vector getUpdateSqlSet_User(TreeControl tc, String user_id)
//	{
//		String tmpSql =
//			" delete from sys_right where user_group_id = '" + user_id + "' ";
//		tmpSql += "and is_user_right ='"
//			+ IS_USER_RIGHT
//			+ "'";
//		vSql.add(tmpSql);
//		String rootName = tc.getRoot().getName();
//		HashMap registry = tc.getRegistry();
//		Iterator iterator = registry.keySet().iterator();
//		while (iterator.hasNext())
//		{
//			//
//			String key = (String) iterator.next();
//			if (rootName.equals(key))
//				continue;
//			TreeControlNode node = (TreeControlNode) registry.get(key);
//			if (ICON_USER.equalsIgnoreCase(node.getIcon()))
//			{
//				vSql.add(
//					makeSql(user_id, key, IS_USER_RIGHT));
//			}
//		}
//		return vSql;
//	}
}
