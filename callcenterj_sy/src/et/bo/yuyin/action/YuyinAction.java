package et.bo.yuyin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.ivr.service.IvrClassTreeService;
import et.bo.sys.ivr.service.IvrTreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.action.BaseAction;

public class YuyinAction extends BaseAction {

	private IvrClassTreeService its=null;
	private IvrTreeService ivrTreeService = null;
	
		/**
		 * @describe 进入市场供求主页面
		 */
		public ActionForward toyuyinTreeLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			//ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.IVR_YUYIN_TREE_INSESSION);
			//request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
			ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.IVR_YUYIN_TREE_INSESSION);
			
			if(tree == null)
			{
				//System.out.println("########################################0");
				//System.out.println("########################################1");
				//System.out.println("第一次时param tree is null 从加载");
				try
				{
					tree = (ViewTree)ivrTreeService.buildTree();
					
					its.setNodeActionFromTree("/callcenterj_sy/yuyin.do?method=toyuyinTreeSelectV", tree);
					request.getSession().setAttribute(SysStaticParameter.IVR_YUYIN_TREE_INSESSION, tree);
					//request.getSession().setAttribute("paramTree", tree);
				}
				catch(Exception e)
				{
					//System.out.println("加载ivr语音树失败了!!");
					e.printStackTrace();
				}
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
				request.setAttribute("selectTreeId", selectId);
			}
			
			return map.findForward("toyuyinTreeLoad");
	    }
		/*
		 * 保存当前节点値
		 */
		/**
		 * @describe 临时页面记录选择的树id
		 */
		public ActionForward toyuyinTreeSelectV(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setAttribute("treeId", request.getParameter("tree"));
			//System.out.println(request.getParameter("tree")+"#####");
			return map.findForward("toyuyinTreeSelected");
		}
		/**
		 * @describe 市场供求查询页
		 */
		public ActionForward toSadQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			
			return map.findForward("toSadQuery");
	    }
		public IvrClassTreeService getIts() {
			return its;
		}
		public void setIts(IvrClassTreeService its) {
			this.its = its;
		}
		public IvrTreeService getIvrTreeService() {
			return ivrTreeService;
		}
		public void setIvrTreeService(IvrTreeService ivrTreeService) {
			this.ivrTreeService = ivrTreeService;
		}
		
		
		
	

	    
}
