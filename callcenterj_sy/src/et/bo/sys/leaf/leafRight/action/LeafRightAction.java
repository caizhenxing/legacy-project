/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.leaf.leafRight.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.leaf.leafRight.service.LeafRightService;
import et.bo.sys.right.service.impl.GrantHelper;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impower.ViewTreeControlImpowerNode;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * Ҷ�ӽڵ��ֵ����Ȩaction
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class LeafRightAction extends BaseAction {

	private static Log log = LogFactory.getLog(LeafRightAction.class);
	/**
	 * ��������tree
	 */
	private LeafRightService leafRightService = null;

	
	
	//private ClassTreeService classTreeService;
//	public ActionForward mod(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		return null;
//	}
	/**
	 * ֻ���ǵ����û��Ľ�ɫȨ�޺��û����ɫȨ�޻�û����������Ӧ�ø�һ��
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ� Ҷ��Ȩ���õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION );

		String roleId = request.getParameter("roleId");
		if(roleId!=null)
		{
			request.getSession().setAttribute("leafRightRoleId", roleId);
		}
		if(tree == null)
		{
			//System.out.println("��һ��ʱmodule tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.MOD_ROOT_NICKNAME);

//				Ϊ�˲��Լӵ�
//				if(tree!=null)
//				{
//
//					String icon = "expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;";
//					String type = "leaf_right_btn";
//					String nickName;
//					
//					Map<String,ViewTreeControlNode> nodes = tree.getRegistry();
//					Iterator<ViewTreeControlNode> it = nodes.values().iterator();
//					while(it.hasNext())
//					{
//						ViewTreeControlNode curNode = it.next();
//						if(curNode.isLeaf())
//						{
//							String insertSql = "insert into sys_leaf_right(id,tree_id,type, icon,label,nickName,remark)";
//							String action =  curNode.getAction()==null?"":curNode.getAction();
//							int lastSolidus = action.lastIndexOf("/"); //last / λ��
//							if(lastSolidus!=-1)
//							{
//								action = action.substring(lastSolidus+1);
//								int indexDot = action.indexOf(".");
//								if(indexDot!=-1)
//								{
//									action = action.substring(0, indexDot);
//								}
//							}
//
//							insertSql+="values('"+leafRightService.getKey()+"','"+curNode.getId()+"','"+type+"', '"+icon+"','"+curNode.getLabel()+"','"+action+"_export"+"','�ڵ�����"+curNode.getLabel()+"@�ڵ�action"+curNode.getAction()+"');";
//							System.out.println(insertSql);
//							//System.out.println(curNode.getLabel()+"@"+action+"@"+curNode.getAction());
//						
//						}
//						
//					}
//				}
//				else
//				{
//					System.out.println("treeSrc is NULL################");
//				}
//				end Ϊ�˲��Լӵ�		
				tree = (ViewTree)this.getClassTreeService().addLeafs2Tree(tree, "./../../sys/leafRight/leafRight.do?method=roleGrantClick", null);
				request.getSession().setAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION , tree);
				
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		//System.out.println(tree.getRoot().getLabel());
		
		if(roleId!=null)
		{

			leafRightService.impowerRoleIcon(roleId, tree, SysStaticParameter.RICON);
		}
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
		return mapping.findForward("showLeafRightTree");
		
	}
	/**
	 * ֻ���ǵ����û��Ľ�ɫȨ�޺��û����ɫȨ�޻�û����������Ӧ�ø�һ��
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ� ����������Ȩ���õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadDeptRoleTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION );
		String roleId = request.getParameter("roleId");
		if(roleId!=null)
		{
			//�������õ� ����������Ȩ��Ҫ��ɫid
			request.getSession().setAttribute("leafRightDeptRoleId", roleId);
		}
		if(tree == null)
		{
			//System.out.println("��һ��ʱ����������Ȩ tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.DEPARTMENT_TREEROOT_NICKNAMW);
				tree = (ViewTree)this.getClassTreeService().addPersons2Tree(tree, "./../../sys/leafRight/leafRight.do?method=roleRersonBatchGrantClick", null);
				request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION , tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		if(roleId!=null)
			leafRightService.impowerRoleIcon(roleId, tree, SysStaticParameter.RICON);
		
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
		return mapping.findForward("showPersonBatchRightTree");
		
	}

	/**
	 * �ڵ㱻���ʱ����Ϊ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleGrantClick(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		ViewTree tree = (ViewTree) session
		      .getAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION);
		GrantHelper gh = new GrantHelper();
		//List<String> tmpGroups = gh.getImpowerGroupFromTree(tree); 
		gh.setImpowerGroup2TreeNode(tree);
		// StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
	    if (mod_id != null) {
			ViewTreeControlNode node = (ViewTreeControlNode)tree.findNode(mod_id);
			tree.selectNode(mod_id);
			gh.roleClickIcon(node);
	    }
	    
		session.setAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION, tree);
		return mapping.findForward("showLeafRightTree");
		
	}
	
	/**
	 * �ڵ㱻���ʱ����Ϊ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleRersonBatchGrantClick(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		ViewTree tree = (ViewTree) session
		      .getAttribute(SysStaticParameter.DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION);
		GrantHelper gh = new GrantHelper();
		//List<String> tmpGroups = gh.getImpowerGroupFromTree(tree); 
		gh.setImpowerGroup2TreeNode(tree);
		// StringManager sm = new StringManager();
		String mod_id = request.getParameter("tree");
	    if (mod_id != null) {
			ViewTreeControlNode node = (ViewTreeControlNode)tree.findNode(mod_id);
			tree.selectNode(mod_id);
			gh.roleClickIcon(node);
	    }
	    
		session.setAttribute(SysStaticParameter.DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION, tree);
		return mapping.findForward("showPersonBatchRightTree");
		
	}
	/**
	 * ��������ɫ��Ȩ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleGrant(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		ViewTree tree = (ViewTree) session
		      .getAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION);
		String roleId = (String)request.getSession().getAttribute("leafRightRoleId");
	    if(roleId!=null&&tree!=null)
	    	leafRightService.impowerRole(roleId, tree);
		//session.setAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION, tree);
		return mapping.findForward("grantLeafSuccess");
		
	}
	/**
	 * �������µ��û���������Ȩ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleBatchPersonGrant(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session == null) {
			return mapping.findForward(SysStaticParameter.SESSION_TIME_OUT);
		}
		ViewTree tree = (ViewTree) session
		      .getAttribute(SysStaticParameter.DEPARTMENT_PERSON_TREE_BATCH_LEAFRIGHT_INSESSION);
		String roleId = (String)request.getSession().getAttribute("leafRightDeptRoleId");
	   // System.out.println("������Ȩ"+roleId+""+tree);
		if(roleId!=null&&tree!=null)
	    	leafRightService.impowerBatchPerson2Role(roleId, tree);
		//session.setAttribute(SysStaticParameter.MODULE_TREE_LEAF_RIGHT_INSESSION, tree);
		return mapping.findForward("grantLeafSuccess");
		
	}
	/**
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ� Ҷ�ӽڵ���ɾ���õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamOperTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);

		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.MODULE_TREE_LEAF_INSESSION);
	
		if(tree == null)
		{
			//System.out.println("��һ��ʱmodule tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)this.getClassTreeService().getSubTreeByNickName(SysStaticParameter.MOD_ROOT_NICKNAME,"./leafRight.do?method=treeList","operationframeTree");
				//this.getClassTreeService().setNodeActionFromTree("./leafRight.do?method=toLeafRightDtl", tree);
				this.getClassTreeService().setNodeActionFromTree("./leafRight.do?method=treeList", tree);
				//tree = (ViewTree)dts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.MODULE_TREE_LEAF_INSESSION, tree);
				//request.getSession().setAttribute("deptTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		//System.out.println(tree.getRoot().getLabel());
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
		return mapping.findForward("showLeafOperTree");
		
	}
	/**
	 * ��ʾ��ǰ�ڵ������е�Ȩ��
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toNodeLeafRight(ActionMapping mapping, ActionForm form,
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
		List<DynaBeanDTO> list = leafRightService.getLeafRightByNodeId(id);
		request.setAttribute("list", list);
		return mapping.findForward("toNodeLeafRight");
	}
	
	/**
	 * ��ʾ������ϸ��Ϣ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toLeafRightDtl(ActionMapping mapping, ActionForm form,
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
				//dts.setRootId(SysStaticParameter.DEPARTMENT_TREE_ROOT_ID);
				//tree = dts.buildTree();
				//request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
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
			//request.setAttribute(name,dts.loadViewBeanById(id) );
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
		
		return mapping.findForward("toLeafRightDtl");
	}
	/**
	 * �ӽڵ���Ȩҳmain
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toLeafRightMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		if(roleId!=null)
		{
			request.getSession().setAttribute("leafRightRoleId", roleId);
			return mapping.findForward("toLeafRightMain");
		}
		return mapping.findForward("error");
	}
	
	/**
	 * �ӽڵ�Ȩ�޵���ɾ��mainҳ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toLeafOperMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			return mapping.findForward("toLeafOperMain");
	}
	/**
	 * ��ɫ��Ȩ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward grantLeafRights(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String roleId = (String)request.getSession().getAttribute("leafRightRoleId");
		String treeId = request.getParameter("treeId");
		String leafRightIdsStr = request.getParameter("leafRightIds");
		List<String> leafRightIds = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(leafRightIdsStr,"|");
		while(st.hasMoreTokens())
		{
			leafRightIds.add(st.nextToken());
		}
		if(roleId != null&&treeId != null)
		{
			//ɾ����ǰ�ڵ�Ȩ��
			leafRightService.deleteRoleRights(roleId, treeId);
			//System.out.println("ɾ����Ȩ����"+leafRightIds.size());
			if(leafRightIds.size()>0)
			{
				//����Ȩ��
				leafRightService.grantRoleRights(roleId, leafRightIds);
			}
		}
		return mapping.findForward("error");
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
		if(srctree == null)
		{
			try
			{
				//dts.setRootId(SysStaticParameter.DEPARTMENT_TREE_ROOT_ID);
				//srctree = dts.buildTree();
				//request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, srctree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
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
    				layerOrder = new String(b)+"_"+(node.findChildren().length+1);
    			}
    		}
    		formdto.set("layerOrder", layerOrder);
        	//dts.addParamTree(formdto);
        }
        if (operType.equals("update")) {
        	//dts.updateParamTree(formdto);
            reloadTree(request);
            return map.findForward("toParamDtl");
        }
        if (operType.equals("delete")) {
         	String did = (String)formdto.get("id");
        	ViewTreeControlNode node = (ViewTreeControlNode)srctree.findNode(did);
        	
			if(node != null)
			{
				//dts.deleteNode(node);
			}
            reloadTree(request);
            
            return map.findForward("operSuggest");
        }
        TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
        if(tree!=null)
        {
        	tree.removeNode(tree.getRoot()); 
        }
        //tree = dts.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.DEPARTMENT_TREE_INSESSION, tree);
        
        return map.findForward("toParamMain");
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
	
	protected void initNickName()
	{
		
	}

	public LeafRightService getLeafRightService() {
		return leafRightService;
	}

	public void setLeafRightService(LeafRightService leafRightService) {
		this.leafRightService = leafRightService;
	}
	
	//**********************************************************
	/**
	 * Ҷ��ҳ�ļ���
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward treeLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request , HttpServletResponse response)
	{
		
		DynaActionFormDTO dbd = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		
        request.setAttribute("type",type);
        
       
   
        if(type.equals("insert")){
        	
        	return map.findForward("treeLoad");
        }
        if(type.equals("update")){
        	String id = request.getParameter("id");

        	IBaseDTO dto = leafRightService.treeInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	request.setAttribute("id", id);
        	return map.findForward("treeLoad");
        }
        if(type.equals("detail")){
        	String id = request.getParameter("id");

        	IBaseDTO dto = leafRightService.treeInfo(id);
        	request.setAttribute(map.getName(), dto);
        	
        	request.setAttribute("id", id);
        	
        	return map.findForward("treeLoad");
        }
        if(type.equals("delete")){
        	String id = request.getParameter("id");

        	IBaseDTO dto = leafRightService.treeInfo(id);
        	request.setAttribute(map.getName(), dto);
        	request.setAttribute("id", id);
        	return map.findForward("treeLoad");
        }
		return map.findForward("treeLoad");
		
	}
	
	
	/**
	 * ��Ҷ�ӵ���ɾ�Ĳ���
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward opertree(ActionMapping map, ActionForm form,
			HttpServletRequest request , HttpServletResponse response){
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		
        request.setAttribute("type",type);
        
        String treeId = request.getSession().getAttribute("treeId").toString();
        
        
		if (type.equals("insert")) {
			try {
				leafRightService.addDict(dto,treeId);
				request.setAttribute("operSign", "sys.common.operSuccess");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				return map.findForward("error");
			}
		}        
		if (type.equals("update")){
			try { 
				boolean b=leafRightService.updateDict(dto);
				if(b==true){
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("treeLoad");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
		
				return map.findForward("treeLoad");
			} catch (RuntimeException e) {
//				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		if (type.equals("delete")){
			try {
				leafRightService.deleteDict((String)dto.get("id"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				
				return map.findForward("treeLoad");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("treeLoad");
	}
	
	/**
	 * Ҷ�Ӳ�ѯ
	 * 
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward treeList(ActionMapping map, ActionForm form,
			HttpServletRequest request , HttpServletResponse response)
	{
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String treeId = null;
		
		if(request.getParameter("tree")!=null)
		{
			treeId=request.getParameter("tree").toString();
			request.getSession().setAttribute("treeId", treeId);
		}
		else
		{
			treeId = request.getSession().getAttribute("treeId").toString();
			request.getSession().setAttribute("treeId", treeId);
		}

		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("treeListpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(2);
        List list = leafRightService.treeList(dto,pageInfo,treeId);
        int size = leafRightService.getTreeSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list", list);
        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
        request.getSession().setAttribute("treeListpageTurning",pt);       
		return map.findForward("treeList");

	}
}
