/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ����action
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class DepartmentTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(DepartmentTreeAction.class);
	/**
	 * ��������tree
	 */
	private DepartmentTreeService dts = null;
	
	private UserService us = null;
	
	//private ClassTreeService classTreeService;
//	public ActionForward mod(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		return null;
//	}
	/**
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
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
			//System.out.println("��һ��ʱdepartment tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
			
				//tree = (ViewTree)dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//Ϊ�˲��Լӵ�
		//tree = (ViewTree)classTreeService.getSubTreeByNickName("departmentRoot");
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
			//System.out.println(id);
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//�ڵ��Ƿ�ѡ��
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		return mapping.findForward("showParamTree");
		
	}
	
	/**
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õļ�����ʱ���Ը�������action
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
			//System.out.println("��һ��ʱdepartment tree is null �Ӽ���");
			try
			{
				try{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
				}
				catch(Exception e){e.printStackTrace();}
				//��������action
				this.getClassTreeService().setNodeActionFromTree("./deptTree.do?method=toParamDtl", tree);
				//tree = (ViewTree)dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_OPER_INSESSION, tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//Ϊ�˲��Լӵ�
		//tree = (ViewTree)classTreeService.getSubTreeByNickName("departmentRoot");
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
			//System.out.println(id);
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//�ڵ��Ƿ�ѡ��
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		return mapping.findForward("showParamOperTree");
		
	}
	
	/**
	 * ��ʾ������ϸ��Ϣ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toParamDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//ȡ���ڵ�id
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
		//ȡ��
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
		//id��Ϊnull��Ϊ�޸Ļ�ɾ��
		if(id!=null)
		{
			request.setAttribute(name,dts.loadViewBeanById(id) );
		}
		else
		{
			//�������Ӳ��� 
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
	 * ��mainҳ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toParamMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("toParamMain");
	}
	
	/**
	 * ��������ɾ�Ĳ���
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
    public ActionForward operParamTree(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//ȡ��
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
	 * ���¼��������䱣����session��
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
	 * ���ݽڵ�id�õ���ǰ�ڵ�
	 * @param nodeId
	 * @return TreeControlNodeService node
	 */
	protected  TreeControlNodeService getNodeById(String nodeId)
	{
		return this.getClassTreeService().getNodeById(nodeId);
	}
	/**
	 * ���ݽڵ������ݿ��м�¼��־
	 * @param TreeControlNodeService node ��¼�Ľڵ�
	 * @param ActionForm form struts ���form
	 */
	/*
	protected  void log2Db(TreeControlNodeService node, IBaseDTO dto)
	{
		System.out.println("������־��������"+new java.util.Date());
		System.out.println(node.getId()+node.getLabel());
	}
	*/
	/**
	 * ��ʼ����־��������Ϣ
	 *
	 */
	protected void initLogInfoMap()
	{
		this.logInfoMap = new HashMap();
		
	}
	
	/**
	 * ���ݽڵ������ݿ��м�¼��־
	 * @param TreeControlNodeService node ��¼�Ľڵ�
	 * @param IBaseDTO dto ��־�������������
	 */
	/*
	protected void log2Db(TreeControlNodeService node, Map logInfoMap)
	{
		System.out.println("������־��"+node.getId()+node.getLabel());
	}
	*/
	/**
	 * ��ʼ����־��������Ϣ
	 *
	 */
	protected void initNickName()
	{
		this.nickName = SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW;
	}
	
	//**********************************************************
//	���ҵ���ϸ�ƻ��Ĳ��� 	userselectҪ�޸�
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
	 * ����Ա�����ı����Ƶ����ı�����,ֻ��һ��һ���ƶ�
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
	 * ����Ա�����ı����Ƶ����ı�����,���������ƶ�
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
	 * ����Ա�����ı����Ƶ����ı�����,���Գ��������ƶ�
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
	 * ����Ա�����ı����Ƶ����ı�����,ֻ���Ե����ƶ�
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
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTreeForPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
		//session ��û�дӼ������������session��
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
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
			//System.out.println(id);
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//�ڵ��Ƿ�ѡ��
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		
		return mapping.findForward("showParamTree");
		
	}
	/**
	 * �������ֻ��Ϊ �ĵ�����layerOrder�õ������ط��ò���
	 * ��num��Ϊ����Ϊ8���ַ������� ���㲹ǰ0
	 * @param String lastLayerOrder
	 * @param int addNum 1 -1
	 * @return string ����Ϊ8���ַ��� 
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
