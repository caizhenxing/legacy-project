/**
 * 
 * 项目名称：struts2
 * 制作时间：May 20, 20095:02:36 PM
 * 包名：com.cc.sys.tree.service.impl
 * 文件名：SysTreeServiceImpl.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package com.cc.sys.tree.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.springframework.orm.hibernate3.HibernateTemplate;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.TreeViewI;
import base.zyf.common.tree.classtree.ClassTreeLoadService;
import base.zyf.common.tree.classtree.ClassTreeService;

import com.cc.sys.db.SysTree;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class SysTreeServiceImpl implements ClassTreeLoadService{

	private HibernateTemplate hibernateTemplate;
	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public void loadTreeNodeService(ClassTreeService cts) {
		Map<String,TreeNodeI> treeId = new HashMap<String,TreeNodeI>();
		Map<String,TreeNodeI> treeNick = new HashMap<String,TreeNodeI>();
		TreeNodeI tree = null;
		Object o = hibernateTemplate.load(SysTree.class, TreeNodeI.TREE_ROOT);
		if(o != null)
		{
			tree = (TreeNodeI)o;
			
			Stack<TreeNodeI> s = new Stack<TreeNodeI>();
			s.addAll(tree.getChildren());
			while(!s.empty())
			{
				TreeNodeI t = s.pop();
				s.addAll(t.getChildren());
				treeId.put(t.getId(), t);
				treeNick.put(((TreeViewI)t).nickName(), t);
			}
		}
		 cts.setParamTree(tree);
		 cts.setTreeId(treeId);
		 cts.setTreeNick(treeNick);
	}

}
