
package et.bo.sys.common.tree.parameter.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.common.tree.parameter.service.ParamTreeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 
 * @author db2admin
 *
 */

public class ParameterTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(ParameterTreeAction.class);
	private ParamTreeService bpt = null;
	public ActionForward mod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		if(tree == null)
		{
			try
			{
			bpt.setRootId(SysStaticParameter.DEPARTMENT_TREE_ROOT_ID);
			tree = bpt.buildTree();
			request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>session tree "+SysStaticParameter.PARAMETER_TREE_INSESSION);
			}
			catch(Exception e){e.printStackTrace();}
		}
		String id = request.getParameter("tree");
		if(id!=null)
		{
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			vt.setExpanded(!vt.isExpanded());
		}
		
		//System.out.println(bpt.getLabelValueBeanListByNickName("workType").size());
		//System.out.println(bpt.getIdByNidkName("workType"));
		return mapping.findForward("showParamTree");
		
	}
	public ActionForward toParamDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if(id==null)
			id = (String)request.getAttribute("id");
		TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		if(tree == null)
		{
			try
			{
				//DEPARTMENT_TREE_ROOT_IDbpt.setRootId(SysStaticParameter.PARAMETER_TREE_ROOT_ID);
				bpt.setRootId(SysStaticParameter.DEPARTMENT_TREE_ROOT_ID);
				tree = bpt.buildTree();
				request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>session tree "+SysStaticParameter.PARAMETER_TREE_INSESSION);
			}
			catch(Exception e){e.printStackTrace();}
		}
		if(id!=null)
		{
			TreeControlNodeService tcn = tree.findNode(id);
			String name=mapping.getName();
			request.setAttribute(name, this.getDTOByTreeControlNode(tcn));
		}
		return mapping.findForward("toParamDtl");
	}
	public IBaseDTO getDTOByTreeControlNode(TreeControlNodeService tcn)
	{
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id",tcn.getId());
		dto.set("nickName", tcn.getBaseTreeNodeService().getNickName());
		dto.set("label", tcn.getLabel());
		//System.out.println(tcn.getLabel()+"++++++++++++++++++=====");
		dto.set("parentId", tcn.getParentId());
		dto.set("type", tcn.getBaseTreeNodeService().getType());
		dto.set("remark",tcn.getReamrk());
		//dto.set("isShow",);
		/**
		 * form-property name="id" type="java.lang.String" />
      	  <form-property name="nickName" type="java.lang.String"/>
      	  <form-property name="label" type="java.lang.String"/>
      	  <form-property name="parentId" type="java.lang.String"/>
      	  <form-property name="type" type="java.lang.String"/>
      	  <form-property name="isShow
		 */
		return dto;
	}
	public ActionForward toParamMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("toParamMain");
	}
	
	 /**
     * <p>
     * 类型树操作
     * </p>
     * 
     * @param info:添加新闻系统基本信息
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operParamTree(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	
        IBaseDTO formdto = (DynaActionFormDTO) form;
        request.setAttribute("id", formdto.get("id"));
        String operType = request.getParameter("operType");
        //System.out.println(new java.util.Date()+":"+operType);
        request.setAttribute("opertype", operType);
        if (operType.equals("insert")) {
        	//System.out.println("insert");
            bpt.addParamTree(formdto);
       
        }
        if (operType.equals("update")) {
            bpt.upateParamTree(formdto);
            reloadTree(request);
            return map.findForward("toParamDtl");
        }
        if (operType.equals("delete")) {
            bpt.removeParamTree((String)formdto.get("id"));
            reloadTree(request);
            
            return map.findForward("operSuggest");
        }
        TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        if(tree!=null)
        {
        	tree.removeNode(tree.getRoot()); 
        }
        tree = bpt.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
        
        return map.findForward("toParamMain");
    }
	
	public ParamTreeService getBpt() {
		return bpt;
	}

	public void setBpt(ParamTreeService bpt) {
		this.bpt = bpt;
	}
	
	private void reloadTree(HttpServletRequest request)
	{
		TreeService tree = bpt.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
	}
	

}
