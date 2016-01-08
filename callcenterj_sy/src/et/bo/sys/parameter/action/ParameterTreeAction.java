/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.parameter.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.parameter.service.ParameterTreeService;
import excellence.common.classtree.ClassTreeService;
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
 * 参数action
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ParameterTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(ParameterTreeAction.class);
	/**
	 * 构建参数tree
	 */
	private ParameterTreeService pts = null;
	private ClassTreeService classTreeService = null;
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}

	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}

	/**
	 * 加载树将其保存在session里 这个是给视图显示用的
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
//		List l = classTreeService.getLabelVaList("departmentRoot");
//		for(int i=0; i<l.size(); i++)
//		{
//			LabelValueBean lv = (LabelValueBean)l.get(i);
//			//System.out.println(lv.getLabel()+lv.getValue());
//		}
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		if(tree == null)
		{
			//System.out.println("第一次时param tree is null 从加载");
			try
			{
				tree = (ViewTree)pts.buildTree();
				//这两个节点不应给用户显示
				tree.removeNode(tree.findNode("SYS_TREE_0000001001"));
				tree.removeNode(tree.findNode("SYS_TREE_0000000041"));
				request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
				//request.getSession().setAttribute("paramTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		
		//节点是否展开
		String id = request.getParameter("tree");
		if(id!=null)
		{
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
	 * 根据nickName加载子树将其保存在session里 这个是给视图显示用的
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadSubParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		String nickName = (String)request.getSession().getAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION);
		ViewTree tree = null;
		if(nickName!=null)
		{
			request.getSession().setAttribute("currentSubNickName", nickName);
			request.getSession().removeAttribute(SysStaticParameter.PARAMETER_SUBTREE_INSESSION);
			tree = (ViewTree)pts.buildTree(nickName,true);
			request.getSession().setAttribute(SysStaticParameter.PARAMETER_SUBTREE_INSESSION, tree);
			request.getSession().removeAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION);
		}
		tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.PARAMETER_SUBTREE_INSESSION);
		//节点是否展开
		String id = request.getParameter("tree");
		if(id!=null)
		{
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//节点是否被选中
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		
		return mapping.findForward("showSubParamTree");
		
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
		TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		if(tree == null)
		{
			try
			{
				pts.setRootId(SysStaticParameter.PARAMETER_TREE_ROOT_ID);
				tree = pts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
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
			request.setAttribute(name,pts.loadViewBeanById(id) );
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
	 * 到子树main页
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toSubParamMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String nickName = request.getParameter("nickName");
		if(nickName!= null)
		{
			request.getSession().removeAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION);
			request.getSession().setAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION, nickName);
		}
		return mapping.findForward("toSubParamMain");
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
		TreeService srctree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		if(srctree == null)
		{
			try
			{
				pts.setRootId(SysStaticParameter.PARAMETER_TREE_ROOT_ID);
				srctree = pts.buildTree();
				request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, srctree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
        IBaseDTO formdto = (DynaActionFormDTO) form;
        request.setAttribute("id", formdto.get("id"));
        String operType = request.getParameter("operType");
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
    				//System.out.println("增加孩子节点size is :"+node.getChildren().size()+":"+layerOrder);
    			}
    		}
    		formdto.set("layerOrder", formdto.get("id"));
    		//reloadTree(request);
        	pts.addParamTree(formdto);
        	
        }
        if (operType.equals("update")) {
        	pts.updateParamTree(formdto);
            //return map.findForward("toParamDtl");
        }
        if (operType.equals("delete")) {
        	String did = (String)formdto.get("id");
        	ViewTreeControlNode node = (ViewTreeControlNode)srctree.findNode(did);
        	
			if(node != null)
			{
				pts.deleteNode(node);
			}
            
            //return map.findForward("operSuggest");
        }
        TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        if(tree!=null)
        {
        	tree.removeNode(tree.getRoot()); 
        }
        this.reloadTree(request);
        tree = pts.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
        
        return map.findForward("operSuggest");
    }
	
	public ParameterTreeService getPts() {
		return pts;
	}

	public void setPts(ParameterTreeService pts) {
		this.pts = pts;
	}
	/**
	 * 从新加载树将其保存在session里
	 * @param HttpServletRequest request
	 * @return 
	 * @throws
	 */
	private void reloadTree(HttpServletRequest request)
	{
		classTreeService.reloadParamTree();
		TreeService tree = pts.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
        //request.getSession().setAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION,this.currentSubTreeNickName);
        String nickName = (String)request.getSession().getAttribute("currentSubNickName");
        request.getSession().setAttribute(SysStaticParameter.PARAMETER_SUBTREE_NICKNAME_INSESSION, nickName);
        request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
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
		sb.append(numStr);
		return sb.toString();
	}
	/**
	 * 根据节点id得到当前节点
	 * @param nodeId
	 * @return TreeControlNodeService node
	 */
	protected  TreeControlNodeService getNodeById(String nodeId)
	{
		return classTreeService.getNodeById(nodeId);
	}
	/**
	 * 根据节点像数据库中记录日志
	 * @param TreeControlNodeService node 记录的节点
	 * @param ActionForm form struts 里的form
	 */
	protected  void log2Db(TreeControlNodeService node, ActionForm form)
	{
		System.out.println("计入日志！！！！");
		System.out.println(node.getId()+node.getLabel());
	}
	
}
