/**
 * @(#)ModuleLinkTreeSearch.java	 06/04/18
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.right.service.impl.GrantHelper;
import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.TreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.action.BaseAction;

/**
 * <code>ModuleLinkTreeSearch</code> inherit <code>BaseAction</code> Tree in
 * session expanded or unexpanded
 * 
 * @author yifei zhao
 * @author xiaofeng gu
 * @author wangwenquan
 * @version 08/04/18
 * @since 2.0
 * @see BaseAction
 * @see TreeControlI
 * @see TreeControlNode
 * @see TreeControl
 */

public class ModuleLinkTreeSearch extends BaseAction {

	private static Log log = LogFactory.getLog(ModuleLinkTreeSearch.class);

	/**
	 * 加载模块树
	 */
	public ActionForward mod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//UserInfo ub = (UserInfo) request.getSession().getAttribute(
				//SysStaticParameter.USER_IN_SESSION);
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		String sSession = SysStaticParameter.MOD_TREE_IN_SESSION;
		String reloadTree = request.getParameter("reloadTree");
		if(reloadTree!=null&&!"".equals(reloadTree.trim()))
		{
			
		}
		ViewTree tree = (ViewTree) session.getAttribute(sSession);
		
		// Handle a tree expand/contract event
//		节点是否展开
		String id = request.getParameter("tree");
		if(id!=null)
		{
			//System.out.println(id);
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//节点是否被选中
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
			ViewTreeControlNode node = (ViewTreeControlNode)tree.findNode(selectId);
			if(node.getBaseTreeNodeService().getNickName()!=null)
			{
				request.setAttribute("logNickName", node.getBaseTreeNodeService().getNickName());
				//this.initNickName(node.getBaseTreeNodeService().getNickName());
			}
		}
		return (mapping.findForward("ok"));
	}

	/**
	 * 给组授权时单击换图片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}

		ViewTree tree = (ViewTree) session
				.getAttribute(SysStaticParameter.GRANT_TREE);
		GrantHelper gh = new GrantHelper();
		// StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
		// mod_id = sm.switchChinese(mod_id);
		if (mod_id != null) {
			ViewTreeControlNode node = (ViewTreeControlNode)tree.findNode(mod_id);
			tree.selectNode(mod_id);
			gh.groupClickIcon(node);
		}
		session.setAttribute(SysStaticParameter.GRANT_TREE, tree);
		return mapping.findForward("group");
	}
	
	/**
	 * 给用户授权时单击换图片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		ViewTree tree = (ViewTree) session
		      .getAttribute(SysStaticParameter.GRANT_TREE);
		GrantHelper gh = new GrantHelper();
		//List<String> tmpGroups = gh.getImpowerGroupFromTree(tree); 
		gh.setImpowerGroup2TreeNode(tree);
		// StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
	    if (mod_id != null) {
			ViewTreeControlNode node = (ViewTreeControlNode)tree.findNode(mod_id);
			tree.selectNode(mod_id);
			gh.userClickIcon(node);
	    }
	    
		session.setAttribute(SysStaticParameter.GRANT_TREE, tree);
		return mapping.findForward("user");
	}
	/**
	 * 组和用户授权用的
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward right(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		String sSession = SysStaticParameter.GRANT_TREE;
		String type = request.getParameter("type");
		String name = null;

		ViewTree control = (ViewTree) session.getAttribute(sSession);

		// Handle a tree expand/contract event
		name = request.getParameter("tree");

		if (name != null) {

			ViewTreeControlNode node = (ViewTreeControlNode)control.findNode(name);

			if (node != null) {
				node.setExpanded(!node.isExpanded());
			}
		} else {
		}

		// Handle a select item event
		name = request.getParameter("select");
		if (name != null) {
			control.selectNode(name);
		}
		if (type.equals("u"))
			return (mapping.findForward("user"));
		else
			return (mapping.findForward("group"));
	}
	

	
	protected void initNickName()
	{
		this.nickName=SysStaticParameter.MOD_ROOT_NICKNAME;
	}
}
