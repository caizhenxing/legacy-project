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

import ocelot.common.tree.TreeControl;
import ocelot.common.tree.TreeControlI;
import ocelot.common.tree.TreeControlNode;
import ocelot.framework.base.action.BaseAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.CommonInfoManager;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import et.bo.sys.right.service.impl.GrantHelper;


/**<code>ModuleLinkTreeSearch</code> inherit <code>BaseAction</code> 
 * Tree in session expanded or unexpanded 
 * 
 * @author  yifei zhao
 * @author 	xiaofeng gu
 * @version 06/04/18
 * @since   2.0
 * @see BaseAction
 * @see TreeControlI
 * @see TreeControlNode
 * @see TreeControl
 */

public class ModuleLinkTreeSearch extends BaseAction {

	private static Log log = LogFactory.getLog(ModuleLinkTreeSearch.class);
	public ActionForward mod(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		UserInfo ub=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		 String sSession=SysStaticParameter.MOD_TREE_IN_SESSION;
	       
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
					CommonInfoManager.getInstance().addLog(ub.getUserName(), "comein", node.getLabel(), request.getRemoteAddr(),"");
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			return (mapping.findForward("ok"));
	    }
	public ActionForward module(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		
		 String sSession=request.getParameter("name");
	       
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
					
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			return (mapping.findForward("module"));
	    }
	public ActionForward group(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		
		TreeControl control =
			(TreeControl) session.getAttribute(
					SysStaticParameter.GRANT_TREE);
		GrantHelper gh = new GrantHelper();
//		StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
//		mod_id = sm.switchChinese(mod_id);
		if (mod_id != null)
		{
			TreeControlNode node = control.findNode(mod_id);
			control.selectNode(mod_id);
			gh.groupClickIcon(node);
		}
		session.setAttribute(
				SysStaticParameter.GRANT_TREE,
			control);
		return mapping.findForward("group");
	    }
	public ActionForward user(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		TreeControl control =
			(TreeControl) session.getAttribute(
					SysStaticParameter.GRANT_TREE);
		GrantHelper gh = new GrantHelper();
//		StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
		//System.out.println("tree name"+mod_id);
//		mod_id = sm.switchChinese(mod_id);
		if (mod_id != null)
		{
			TreeControlNode node = control.findNode(mod_id);
			control.selectNode(mod_id);
			gh.userClickIcon(node);
		}
		session.setAttribute(
				SysStaticParameter.GRANT_TREE,
			control);
		return mapping.findForward("user");
	    }
	public ActionForward right(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		 String sSession=SysStaticParameter.GRANT_TREE;
	     String type=request.getParameter("type");
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			if(type.equals("u"))
			return (mapping.findForward("user"));
			else
				return (mapping.findForward("group"));
	}
	public ActionForward dep(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		 String sSession=request.getParameter("name");
	       
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			return (mapping.findForward("dep"));
	    }
	public ActionForward tree(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		 String sSession=request.getParameter("name");
	       
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			return (mapping.findForward("tree"));
	    }
	public ActionForward station(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		if(session==null)
		{
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		 String sSession=request.getParameter("name");
	       
	        String name = null;
			
			TreeControl control =
				(TreeControl) session.getAttribute(sSession);

			// Handle a tree expand/contract event
			name = request.getParameter("tree");

			if (name != null) {

				TreeControlNode node = control.findNode(name);

				if (node != null){
					node.setExpanded(!node.isExpanded());
				}
			}else{
			}

			// Handle a select item event
			name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);
			}
			return (mapping.findForward("station"));
	    }
}
