package et.bo.sys.right.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import ocelot.common.tree.TreeControl;
import ocelot.common.tree.TreeControlNode;

import et.bo.sys.common.SysStaticParameter;


/*
 * Created on 2004-9-8
 *
 * 
 */

/**
 * @author	 		:	¹¼Ïþ·å
 * @project			:
 * @class describe	:
 * 
 */
public class GrantHelper
{
	private Vector vSql = new Vector();
	/**
	 * 
	 */
	private final String ICON_GROUP = "";
	private final String ICON_USER = "";
	private final String ICON_UNSELECTED ="";
	
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
		TreeControlNode treeControlNode,
		boolean selected)
	{
		treeControlNode.setIcon(selected ? SysStaticParameter.GICON: SysStaticParameter.NICON);
	}
	//SET USER ICON
	private void setNodeIcon_User(
		TreeControlNode treeControlNode,
		boolean selected)
	{
		if (selected)
		{
			if (SysStaticParameter.NICON.equalsIgnoreCase(treeControlNode.getIcon()))
			{
				treeControlNode.setIcon(SysStaticParameter.UICON);
			}
		}
		else
		{
			treeControlNode.setIcon(SysStaticParameter.NICON);
		}
	}
	//handle The Parent Icon µØ¹é
	private void setParentIcon_Group(TreeControlNode treeControlNode)
	{
		TreeControlNode parent = treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_Group(parent, true); //parent is all true
		setParentIcon_Group(parent);
	}
	//handle The child Icon µØ¹é
	private void setParentIcon_User(TreeControlNode treeControlNode)
	{
		TreeControlNode parent = treeControlNode.getParent();
		if (parent == null)
			return;
		setNodeIcon_User(parent, true);
		setParentIcon_User(parent);
	}
	//handle The Children  Icon all child is same
	private void setChildrenIcon_Group(
		TreeControlNode treeControlNode,
		boolean selected)
	{
		ArrayList children = treeControlNode.getChildren();
		if (children.size() == 0)
			return;
		for (int i = 0; i < children.size(); i++)
		{
			TreeControlNode child = (TreeControlNode) children.get(i);
			setNodeIcon_Group(child, selected);
			setChildrenIcon_Group(child, selected);
		}
	}
	private void setChildrenIcon_User(
		TreeControlNode treeControlNode,
		boolean selected)
	{
		ArrayList children = treeControlNode.getChildren();
		if (children.size() == 0)
			return;
		for (int i = 0; i < children.size(); i++)
		{
			TreeControlNode child = (TreeControlNode) children.get(i);
			setNodeIcon_User(child, selected);
			setChildrenIcon_User(child, selected);
		}
	}
	public void userClickIcon(TreeControlNode treeControlNode)
	{
		if (treeControlNode.getIcon().equalsIgnoreCase(SysStaticParameter.GICON))
			return;
		boolean unselected =
			treeControlNode.getIcon().equalsIgnoreCase(SysStaticParameter.NICON);
		setNodeIcon_User(treeControlNode, unselected);
		if (unselected)
		{
			setParentIcon_User(treeControlNode);
		}
		setChildrenIcon_User(treeControlNode, unselected);
	}
	public void groupClickIcon(TreeControlNode treeControlNode)
	{
		boolean unselected =
			treeControlNode.getIcon().equalsIgnoreCase(SysStaticParameter.NICON);
		boolean selected = !unselected;
		setNodeIcon_Group(treeControlNode, unselected);
		if (unselected)
		{
			setParentIcon_Group(treeControlNode);
		}
		setChildrenIcon_Group(treeControlNode, unselected);
	}
	//-------------------------------------------------------------------------------------
	private String makeSql(
		String user_group_id,
		String mod_id,
		String UserOrGroupRight)
	{
		String sql = "insert into sys_right values(";
		sql += "'" + user_group_id + "',";
		sql += "'" + mod_id + "',";
		sql += "'" + UserOrGroupRight + "'";
		sql += ")";
		return sql;
	}
	public Vector getUpdateSqlSet_Group(TreeControl tc, String group_id)
	{
		String tmpSql =
			" delete from sys_right where user_group_id = '" + group_id + "' ";
		tmpSql += "and is_user_right ='"
			+ IS_GROUP_RIGHT
			+ "'";
		vSql.add(tmpSql);
		String rootName = tc.getRoot().getName();
		HashMap registry = tc.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			//
			String key = (String) iterator.next();
			if (rootName.equals(key))
				continue;
			//
			TreeControlNode node = (TreeControlNode) registry.get(key);
			if (ICON_GROUP.equalsIgnoreCase(node.getIcon()))
			{
				vSql.add(
					makeSql(group_id, key, IS_GROUP_RIGHT));
			}
		}
		return vSql;
	}
	public Vector getUpdateSqlSet_User(TreeControl tc, String user_id)
	{
		String tmpSql =
			" delete from sys_right where user_group_id = '" + user_id + "' ";
		tmpSql += "and is_user_right ='"
			+ IS_USER_RIGHT
			+ "'";
		vSql.add(tmpSql);
		String rootName = tc.getRoot().getName();
		HashMap registry = tc.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			//
			String key = (String) iterator.next();
			if (rootName.equals(key))
				continue;
			TreeControlNode node = (TreeControlNode) registry.get(key);
			if (ICON_USER.equalsIgnoreCase(node.getIcon()))
			{
				vSql.add(
					makeSql(user_id, key, IS_USER_RIGHT));
			}
		}
		return vSql;
	}
}
