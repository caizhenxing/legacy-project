package et.bo.sys.department.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.department.service.DepartmentService;
import et.bo.sys.user.service.UserService;
import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.TreeControlNode;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;


public class DepartmentAction extends BaseAction {

	private DepartmentService ds=null;
	private UserService us=null;
	public UserService getUs() {
		return us;
	}
	public void setUs(UserService us) {
		this.us = us;
	}
	public ActionForward load(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String type=request.getParameter("type");
		
		if(type!=null&&type.equals("insert"))
		{
			IBaseDTO dto=(IBaseDTO)form;
			String id=request.getParameter("parentId");
			request.setAttribute("parentId",id);
			return mapping.findForward("insert");
		}
		String id=request.getParameter("id");
		List<LabelValueBean> userList=ds.getUserListByDep(id);
		request.setAttribute("userList",userList);
		String name=mapping.getName();
		IBaseDTO dto=ds.getDepartment(id);
		request.setAttribute(name,dto);
		return mapping.findForward("info");
    }
	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String operflag=(String)request.getAttribute("operflag");
		if(operflag!=null)
		{
			request.setAttribute("operflag",operflag);
		}
		try
		{
		TreeControlI tci=ds.loadDepartments();
		request.getSession().setAttribute("depSession",tci.getTreeControl());
		return mapping.findForward("main");
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
    }
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		String id=(String) aform.get("id");
		try
		{
		ds.updateDepartment(aform);
		request.setAttribute(mapping.getName(),aform);
		request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		
		return mapping.findForward("info");
    }
	public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO aform=(IBaseDTO) form;
		String id=request.getParameter("id");
		try{
		ds.removeDepartment(id);
		request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		return mapping.findForward("delete");
    }
	public ActionForward todelete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO aform=(IBaseDTO) form;
		String id=request.getParameter("id");
		request.setAttribute("id",id);
		
		
		return mapping.findForward("todelete");
    }
	public ActionForward insert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		
		try
		{
			ds.addDepartment(aform);
			request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		
		return mapping.findForward("insertsuccess");
    }
	
	public DepartmentService getDs() {
		return ds;
	}
	public void setDs(DepartmentService ds) {
		this.ds = ds;
	}
	public ActionForward depTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sSession = request.getParameter("name");
		

		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}

		// String sSession=request.getParameter("name");

		String name = request.getParameter("tree");

		TreeControl control = (TreeControl) session.getAttribute(sSession);

		// Handle a tree expand/contract event
		name = request.getParameter("tree");

		if (name != null) {

			TreeControlNode node = control.findNode(name);

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

		return mapping.findForward("selecttree");

	}
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String receivers = request.getParameter("value");
		String id=request.getParameter("id");
		if(id!=null)
		{
		List ul = ds.getUserListByDep(id);
		request.getSession().setAttribute("userList", ul);
		List l=(List)request.getSession().getAttribute("userList2");
		if(l==null)
		request.getSession().setAttribute("userList2", new ArrayList());
		else
			request.getSession().setAttribute("userList2", l);
		return mapping.findForward("selectDep");
		}
		else
		{
			List l=us.getUsers(receivers);
			TreeControlI tci=ds.getDepartments();
			request.getSession().setAttribute("depSelectSession",tci.getTreeControl());
			request.getSession().setAttribute("userList", new ArrayList());
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}
		
	}
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//String receivers = request.getParameter("value");
		String select=request.getParameter("select");
		
		List l=(List)request.getSession().getAttribute("userList2");
		List<LabelValueBean> ul = (List)request.getSession().getAttribute("userList");
		for(int i=0,size=ul.size();i<size;i++)
		{
			LabelValueBean lvb=ul.get(i);
			if(lvb.getValue().equals(select))
			{
				l.add(lvb);
				ul.remove(lvb);
				break;
			}
		}
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("selectDep");
		
		
	}
	public ActionForward addall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//String receivers = request.getParameter("value");
		List l=(List)request.getSession().getAttribute("userList2");
		List ul = (List)request.getSession().getAttribute("userList");
		l.addAll(ul);
		ul.clear();
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("selectDep");
	}
	public ActionForward suball(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//String receivers = request.getParameter("value");
		List l=(List)request.getSession().getAttribute("userList2");
		List ul = (List)request.getSession().getAttribute("userList");
		ul.addAll(l);
		l.clear();
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("selectDep");
	}
	public ActionForward sub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String select=request.getParameter("select");
		
		List<LabelValueBean> l=(List)request.getSession().getAttribute("userList2");
		List ul = (List)request.getSession().getAttribute("userList");
		for(int i=0,size=l.size();i<size;i++)
		{
			LabelValueBean lvb=l.get(i);
			if(lvb.getValue().equals(select))
			{
				ul.add(lvb);
				l.remove(lvb);
				break;
			}
		}
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("selectDep");
		
	}
}
