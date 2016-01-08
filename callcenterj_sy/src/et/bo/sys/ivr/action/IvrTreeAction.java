package et.bo.sys.ivr.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.portCompare.service.PortCompareService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.ivr.service.IvrTreeService;
import et.bo.sys.parameter.action.ParameterTreeAction;
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

public class IvrTreeAction extends BaseAction{
	private static Log log = LogFactory.getLog(ParameterTreeAction.class);
	/**
	 * ��������tree
	 */
	private IvrTreeService ivrTreeService = null;
	
	private ClassTreeService classTreeService = null;
	
	private PortCompareService pcs = null;

	public PortCompareService getPcs() {
		return pcs;
	}

	public void setPcs(PortCompareService pcs) {
		this.pcs = pcs;
	}

	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}
	
	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
	//���ԵĿ�classBaseTreeService��ʹ����ʹ
	/*
	private ClassBaseTreeService classBaseTreeService = null;
	public ClassBaseTreeService getClassBaseTreeService() {
		return classBaseTreeService;
	}

	public void setClassBaseTreeService(ClassBaseTreeService classBaseTreeService) {
		this.classBaseTreeService = classBaseTreeService;
	}
	*/
	/**
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.IVR_TREE_INSESSION);
		
		if(tree == null)
		{
			//System.out.println("��һ��ʱparam tree is null �Ӽ���");
			try
			{
				tree = (ViewTree)ivrTreeService.buildTree();
				
				classTreeService.setNodeActionFromTree("./paramTree.do?method=toParamDtl", tree);
				request.getSession().setAttribute(SysStaticParameter.IVR_TREE_INSESSION, tree);
				//request.getSession().setAttribute("paramTree", tree);
			}
			catch(Exception e)
			{
				//System.out.println("����ivr��ʧ����!!");
				e.printStackTrace();
			}
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
	 * ���������䱣����session�� ����Ǹ���ͼ��ʾ�õ�
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadYuyinParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
		ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.IVR_YUYIN_TREE_INSESSION);
		
		if(tree == null)
		{
			//System.out.println("��һ��ʱparam tree is null �Ӽ���");
			try
			{
				
				tree = (ViewTree)classTreeService.getParamTree();
				if(tree==null)
				{
					classTreeService.loadParamTree();
					tree = (ViewTree)classTreeService.getParamTree();
				}
				
				classTreeService.setNodeActionFromTree("www.google.com", tree);
				request.getSession().setAttribute(SysStaticParameter.IVR_YUYIN_TREE_INSESSION, tree);
				//request.getSession().setAttribute("paramTree", tree);
			}
			catch(Exception e)
			{
				//System.out.println("����ivr��ʧ����!!");
				e.printStackTrace();
			}
		}
		
		//�ڵ��Ƿ�չ��
		String id = request.getParameter("tree");
		if(id!=null)
		{
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			if(vt != null)
			{
				vt.setExpanded(!vt.isExpanded());
			}
		}
		//�ڵ��Ƿ�ѡ��
		String selectId = request.getParameter("select");
		if(selectId!=null)
		{
			tree.selectNode(selectId);
		}
		
		return mapping.findForward("showParamTreeYuyin");
		
	}
	/**
	 * ��ʾ������ϸ��Ϣ
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward toParamDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IBaseDTO dto = null;
		//ȡ���ڵ�id
		//����ר���б�
		List<LabelValueBean> experterList = ivrTreeService.getExperterList();
		request.setAttribute("experterList", experterList);
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
		TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.IVR_TREE_INSESSION);
		if(tree == null)
		{
			try
			{
				ivrTreeService.setRootId(SysStaticParameter.IVR_TREE_ROOT_ID);
				tree = ivrTreeService.buildTree();
				request.getSession().setAttribute(SysStaticParameter.IVR_TREE_INSESSION, tree);
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
		//����ĸ��ڵ����⴦��
		String pFuncType = request.getParameter("pFuncType");
		if("input".equals(pFuncType)||"query".equals(pFuncType))
		{
			request.setAttribute("pFuncType", "onlyCase");
		}
		else if("conference".equals(pFuncType))
		{
			request.setAttribute("pFuncType", "conference");
		}
		if(id!=null)
		{
			dto = ivrTreeService.loadViewBeanById(id);
			if("case".equals((String)dto.get("Cfunctype")))
			{
				request.setAttribute("pFuncType", "onlyCase");
				request.setAttribute("isCase", "case");
			}
			request.setAttribute(name,dto );
			
		}
		else
		{
			//�������Ӳ��� 
			dto = new DynaBeanDTO();
			String parentId = request.getParameter("parentId");
			dto.set("parentId", request.getParameter("parentId"));
			
			ViewTreeControlNode pNode = (ViewTreeControlNode)classTreeService.getNodeById(parentId);
			if(pNode!=null)
			{
				dto.set("target", pNode.getTarget());
			}
			String pName = request.getParameter("parentName");
		
			
			try {
				dto.set("parentName", new String(request.getParameter("parentName").getBytes("iso8859-1")));
				//dto.set("CPfunctype", pFuncType);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.set("type", request.getParameter("type"));
			dto.set("createTime", TimeUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
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
	/*
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
	*/
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
		TreeService srctree = (TreeService)request.getSession().getAttribute(SysStaticParameter.IVR_TREE_INSESSION);
		if(srctree == null)
		{
			try {
				ivrTreeService.setRootId(SysStaticParameter.IVR_TREE_ROOT_ID);
				srctree = ivrTreeService.buildTree();
				request.getSession().setAttribute(SysStaticParameter.IVR_TREE_INSESSION, srctree);
			} catch(Exception e) {
				e.printStackTrace();
			}
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
    		formdto.set("layerOrder", layerOrder);
    		//reloadTree(request);
    		ivrTreeService.addParamTree(formdto);
        	
        }
        if (operType.equals("update")) {
        	ivrTreeService.updateParamTree(formdto);
            //return map.findForward("toParamDtl");
        }
        if (operType.equals("delete")) {
        	String did = (String)formdto.get("id");
        	ViewTreeControlNode node = (ViewTreeControlNode)srctree.findNode(did);
        	
			if(node != null)
			{
				ivrTreeService.deleteNode(node);
			}
            
            //return map.findForward("operSuggest");
        }
        TreeService tree = (TreeService)request.getSession().getAttribute(SysStaticParameter.IVR_TREE_INSESSION);

        this.reloadTree(request);
        tree = ivrTreeService.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
        request.getSession().setAttribute(SysStaticParameter.IVR_TREE_INSESSION, tree);
        
        return map.findForward("operSuggest");
    }
	
    /**
     * ���¼����ť��������ĳ���ڵ��¼��ҳ��
     * @param map
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward ivrRecord(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	return map.findForward("ivrRecord");
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
		TreeService tree = ivrTreeService.buildTree();
        request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
        
        request.getSession().setAttribute(SysStaticParameter.IVR_TREE_INSESSION, tree);
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
		//System.out.println("������־��������");
		//System.out.println(node.getId()+node.getLabel());
	}
	
	

	public IvrTreeService getIvrTreeService() {
		return ivrTreeService;
	}

	public void setIvrTreeService(IvrTreeService ivrTreeService) {
		this.ivrTreeService = ivrTreeService;
	}
}
