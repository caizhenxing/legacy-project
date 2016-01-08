/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ����action
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ParameterTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(ParameterTreeAction.class);
	/**
	 * ��������tree
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
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
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
			//System.out.println("��һ��ʱparam tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)pts.buildTree();
				//�������ڵ㲻Ӧ���û���ʾ
				tree.removeNode(tree.findNode("SYS_TREE_0000001001"));
				tree.removeNode(tree.findNode("SYS_TREE_0000000041"));
				request.getSession().setAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION, tree);
				//request.getSession().setAttribute("paramTree", tree);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
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
	 * ����nickName�����������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
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
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			
			vt.setExpanded(!vt.isExpanded());
		}
		//�ڵ��Ƿ�ѡ��
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		
		return mapping.findForward("showSubParamTree");
		
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
		//id��Ϊnull��Ϊ�޸Ļ�ɾ��
		if(id!=null)
		{
			request.setAttribute(name,pts.loadViewBeanById(id) );
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
	 * ������mainҳ
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
	 * ��������ɾ�Ĳ���
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
    public ActionForward operParamTree(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//ȡ��
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
    				//System.out.println("���Ӻ��ӽڵ�size is :"+node.getChildren().size()+":"+layerOrder);
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
	 * ���¼��������䱣����session��
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
		sb.append(numStr);
		return sb.toString();
	}
	/**
	 * ���ݽڵ�id�õ���ǰ�ڵ�
	 * @param nodeId
	 * @return TreeControlNodeService node
	 */
	protected  TreeControlNodeService getNodeById(String nodeId)
	{
		return classTreeService.getNodeById(nodeId);
	}
	/**
	 * ���ݽڵ������ݿ��м�¼��־
	 * @param TreeControlNodeService node ��¼�Ľڵ�
	 * @param ActionForm form struts ���form
	 */
	protected  void log2Db(TreeControlNodeService node, ActionForm form)
	{
		System.out.println("������־��������");
		System.out.println(node.getId()+node.getLabel());
	}
	
}
