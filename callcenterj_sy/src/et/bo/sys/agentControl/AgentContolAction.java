/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.sys.agentControl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import excellence.common.classtree.ClassTreeService;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.action.BaseAction;

/**
 * <p>��ϯȨ�޵�������action</p>
 * 
 * @version 2008-03-28
 * @author ����Ȩ
 */

public class AgentContolAction extends BaseAction {
	
	static Logger log = Logger.getLogger(AgentContolAction.class.getName());
	private ClassTreeService cts;
	/**
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toMainControl(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		String treeId = request.getParameter("tree");
		ViewTreeControlNode node = (ViewTreeControlNode)cts.getNodeById(treeId);
		if(node!=null)
		{
			request.getSession().setAttribute("treeNode", node);
		}
		return map.findForward("toAgentNavMain");
		
	}
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	
}
