/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.department.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.department.service.DepartmentTreeService;
import et.bo.sys.user.service.UserService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 部门action
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class DepartmentTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(DepartmentTreeAction.class);
	/**
	 * 构建部门tree
	 */
	private DepartmentTreeService dts = null;
	
	private UserService us = null;
	
	//private ClassTreeService classTreeService;
//	public ActionForward mod(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		return null;
//	}
	/**
	 * 加载树将其保存在session里 这个是给视图显示用的
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);

		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
	
		if(tree == null)
		{
			//System.out.println("第一次时department tree is null 从加载");
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
			
				//tree = (ViewTree)dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//为了测试加的
		//tree = (ViewTree)classTreeService.getSubTreeByNickName("departmentRoot");
		//节点是否展开
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
		}
		return mapping.findForward("showParamTree");
		
	}
	
	/**
	 * 加载树将其保存在session里 这个是给视图显示用的加载树时可以给树设置action
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadDeptTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);

		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_OPER_INSESSION);
	
		if(tree == null)
		{
			//System.out.println("第一次时department tree is null 从加载");
			try
			{
				try{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
				}
				catch(Exception e){e.printStackTrace();}
				//从设树的action
				this.getClassTreeService().setNodeActionFromTree("./deptTree.do?method=toParamDtl", tree);
				//tree = (ViewTree)dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_OPER_INSESSION, tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//为了测试加的
		//tree = (ViewTree)classTreeService.getSubTreeByNickName("departmentRoot");
		//节点是否展开
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
		}
		return mapping.findForward("showParamOperTree");
		
	}
	
	/**
	 * 显示树的详细信息
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toParamDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//取树节点id
		String id = request.getParameter("id");
		if(id==null)
			id = (String)request.getAttribute("id");
		if(id==null)
		{
			id = request.getParameter("tree");
			if(id==null)
				id = (String)request.getAttribute("tree");
		}
		if(id==null)
		{
			id = request.getParameter("select");
			if(id==null)
				id = (String)request.getAttribute("select");
		}
		//取树
		TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
		if(tree == null)
		{
			try
			{
				dts.setRootId(SysStaticParameter.DEPARTMENT_TREE_ROOT_ID);
				tree = dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		String opertype = request.getParameter("opertype");
		if(opertype!=null)
		{
			request.setAttribute("opertype", opertype);
		}
		String name=mapping.getName();
		//id不为null则为修改或删除
		if(id!=null)
		{
			request.setAttribute(name,dts.loadViewBeanById(id) );
		}
		else
		{
			//处理增加操作 
			IBaseDTO dto = new DynaBeanDTO();
			dto.set("parentId", request.getParameter("parentId"));
			String pName = request.getParameter("parentName");

			try {
				dto.set("parentName", new String(request.getParameter("parentName").getBytes("iso8859-1")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dto.set("type", request.getParameter("type"));
			dto.set("createTime", TimeUtil.getNowTime("yyyy-mm-dd HH:mm:ss"));
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			request.setAttribute(name,dto );
		}
		
		return mapping.findForward("toParamDtl");
	}
	/**
	 * 到main页
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toParamMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("toParamMain");
	}
	
	/**
	 * 对树的增删改操做
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
    public ActionForward operParamTree(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//取树
		TreeService srctree = (TreeService)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);

		
        IBaseDTO formdto = (DynaActionFormDTO) form;
        request.setAttribute("id", formdto.get("id"));
        String operType = request.getParameter("operType");
        //System.out.println(new java.util.Date()+":"+operType);
        request.setAttribute("opertype", operType);
        if (operType.equals("add")) {
        	String parentId = (String)formdto.get("parentId");
    		String layerOrder = "";

    		if(parentId!=null)
    		{
    			ViewTreeControlNode node = (ViewTreeControlNode)srctree.findNode(parentId);
    			if(node != null)
    			{
    				byte[] b = {(byte)(65+node.getWidth())};
    				layerOrder = new String(b)+"_"+getLastLayerOrderStr(node.findChildren().length+1);
    			}
    		}
    		formdto.set("layerOrder", layerOrder);
        	dts.addParamTree(formdto);
        	reloadTree(request);
        }
        if (operType.equals("update")) {
        	dts.updateParamTree(formdto);
            reloadTree(request);
            
            return map.findForward("toParamDtl");
        }
        if (operType.equals("delete")) {
         	String did = (String)formdto.get("id");
        	ViewTreeControlNode node = (ViewTreeControlNode)srctree.findNode(did);
        	
			if(node != null)
			{
				dts.deleteNode(node);
			}
            reloadTree(request);
            
            return map.findForward("operSuggest");
        }
        TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
        if(tree!=null)
        {
        	tree.removeNode(tree.getRoot()); 
        }
        tree = dts.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
        
        return map.findForward("toParamMain");
    }
	
	public DepartmentTreeService getDts() {
		return dts;
	}

	public void setDts(DepartmentTreeService dts) {
		this.dts = dts;
	}
	
	/**
	 * 从新加载树将其保存在session里
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
	private void reloadTree(HttpServletRequest request)
	{
		this.getClassTreeService().reloadParamTree();
		TreeService tree = this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
        request.getSession().removeAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
	}
	
	/**
	 * 根据节点id得到当前节点
	 * @param nodeId
	 * @return TreeControlNodeService node
	 */
	protected  TreeControlNodeService getNodeById(String nodeId)
	{
		return this.getClassTreeService().getNodeById(nodeId);
	}
	/**
	 * 根据节点像数据库中记录日志
	 * @param TreeControlNodeService node 记录的节点
	 * @param ActionForm form struts 里的form
	 */
	/*
	protected  void log2Db(TreeControlNodeService node, IBaseDTO dto)
	{
		System.out.println("计入日志！！！！"+new java.util.Date());
		System.out.println(node.getId()+node.getLabel());
	}
	*/
	/**
	 * 初始化日志的其他信息
	 *
	 */
	protected void initLogInfoMap()
	{
		this.logInfoMap = new HashMap();
		
	}
	
	/**
	 * 根据节点像数据库中记录日志
	 * @param TreeControlNodeService node 记录的节点
	 * @param IBaseDTO dto 日志所需的其他属性
	 */
	/*
	protected void log2Db(TreeControlNodeService node, Map logInfoMap)
	{
		System.out.println("计入日志："+node.getId()+node.getLabel());
	}
	*/
	/**
	 * 初始化日志的其他信息
	 *
	 */
	protected void initNickName()
	{
		this.nickName = SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW;
	}
	
	//**********************************************************
//	对我的详细计划的操作 	userselect要修改
	public ActionForward userselect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		IBaseDTO aform= new DynaBeanDTO();
		aform.set("selectName", request.getParameter("selectSelect"));
    	List al = new ArrayList();
		al = dts.getUserListselect(aform);
		
		
		request.getSession().setAttribute("userList", al);
		return mapping.findForward("selectDep");
	}
	
	
	
	public ActionForward userselect1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		return mapping.findForward("selectDep");
	}
	
	/**
	 * 把人员从左文本框移到右文本框中,只能一个一个移动
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
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
	
	/**
	 * 把人员从左文本框移到右文本框中,可以批量移动
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
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
	
	
	/**
	 * 把人员从右文本框移到左文本框中,可以成批量的移动
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
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
	
	/**
	 * 把人员从右文本框移到左文本框中,只可以单个移动
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
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
	
	public ActionForward toDeptPersonMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("toDeptPersonMain");
	}
	
	
	
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String receivers = request.getParameter("value");
		String id=request.getParameter("tree");
		if(id!=null)
		{
		List ul = dts.getUserListByDep(id);
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
			//TreeControlI tci=bpt.getDepartments();
			ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
			request.getSession().setAttribute("depSelectSession",tree);
			request.getSession().setAttribute("userList", new ArrayList());
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}
		
	}

	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}
	
	/**
	 * 加载树将其保存在session里 这个是给视图显示用的
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTreeForPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
		//session 里没有从加载树将其存入session里
		if(tree == null)
		{
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW, "./deptTree.do?method=select", "operationframe");
				//tree = (ViewTree)dts.buildTree("./deptTree.do?method=select","operationframe");
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//节点是否展开
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
		}
		
		return mapping.findForward("showParamTree");
		
	}
	/**
	 * 这个方法只是为 的到树的layerOrder用的其他地方用不上
	 * 将num变为长度为8的字符串返回 不足补前0
	 * @param String lastLayerOrder
	 * @param int addNum 1 -1
	 * @return string 长度为8的字符串 
	 */
	private String getLastLayerOrderStr(int num)
	{
		String numStr = num + "";
		int numL = numStr.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<(8-numL); i++)
		{
			sb.append("0");
		}
		sb.append(numL);
		return sb.toString();
	}
	protected void log2Db(TreeControlNodeService node, Map logInfoMap)
	{
		
	}
}
