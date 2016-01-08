/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.layerOrder.action;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.layerOrder.service.LayerOrderTreeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.action.BaseAction;

/**
 * 与树排序相关的类
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class LayerOrderTreeAction extends BaseAction {

	private static Log log = LogFactory.getLog(LayerOrderTreeAction.class);
	/**
	 * 构建部门tree
	 */
	private LayerOrderTreeService layerOrderTreeService = null;
	
	/**
	 * 加载树将其保存在session里 这个是给视图显示用的
	 * @param ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward loadParamTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//request.getSession().removeAttribute(SysStaticParameter.PARAMETER_TREE_INSESSION);
		
		String nickName = request.getParameter("nickName");
		ViewTree tree = null;
		if(nickName != null)
		{
			request.getSession().removeAttribute(SysStaticParameter.LAYERORDERTREE_INSESSION);
			tree = (ViewTree)layerOrderTreeService.buildTree(nickName, "./../layerOrder/layerOrder.do?method=loadParamTree", "");
			request.getSession().setAttribute(SysStaticParameter.LAYERORDERTREE_INSESSION, tree);
		}
		tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.LAYERORDERTREE_INSESSION);
		if(tree == null)
		{
			throw new NullPointerException("LayerOrderTreeAction 的 loadParamTree 的 tree为null错误!");
		}
		//为了测试加的
		//tree = (ViewTree)classTreeService.getSubTreeByNickName("departmentRoot");
	
		String oper = request.getParameter("oper");
		//节点是否被选中
		String selectId = request.getParameter("select");
		//节点是否展开
		String id = request.getParameter("tree");
		//System.out.println(id+tree.getRoot().getLabel()+tree.getRoot().getId());
		if(id!=null&&selectId==null)
		{
			ViewTreeControlNode vt = (ViewTreeControlNode)tree.findNode(id);
			if(vt!=null)
			vt.setExpanded(!vt.isExpanded());
		}
		if(selectId!=null)
		{
			tree.selectNode(selectId);
			TreeControlNodeService selectNode = tree.findNode(selectId);
			TreeControlNodeService[] childs = selectNode.getParent().findChildren();
			ArrayList<TreeControlNodeService> cList = new ArrayList<TreeControlNodeService>();
			TreeControlNodeService upNode = null;
			TreeControlNodeService downNode = null;
			for(int i=0; i<childs.length; i++)
			{
				if(selectNode.getId().equals(childs[i].getId()))
				{
					if("up".equals(oper))
					{
						if(i>0)
						{
							upNode = childs[i-1];
						}
						else if(i==0)
						{
							if(childs.length>1)
							{
								upNode = childs[childs.length-1];
							}
							else
							{
								upNode = childs[0];
							}
						}
						String selectLayerOrder = selectNode.getBaseTreeNodeService().getLayerOrder();
						selectNode.getBaseTreeNodeService().setLayerOrder(upNode.getBaseTreeNodeService().getLayerOrder());
						upNode.getBaseTreeNodeService().setLayerOrder(selectLayerOrder);
					}
					else if("down".equals(oper))// down
					{
						if(i==(childs.length-1))
						{
							downNode = childs[0];
						}
						else if(i<(childs.length-1))
						{
							downNode = childs[i+1];
						}
						String selectLayerOrder = selectNode.getBaseTreeNodeService().getLayerOrder();
						selectNode.getBaseTreeNodeService().setLayerOrder(downNode.getBaseTreeNodeService().getLayerOrder());
						downNode.getBaseTreeNodeService().setLayerOrder(selectLayerOrder);
					}
				}

				cList.add(childs[i]);
			}
			Collections.sort(cList);
			selectNode.getParent().setChildren(cList);
		}
		return mapping.findForward("toLayerOrderTree");
		
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

    	ViewTree tree = (ViewTree)request.getSession().getAttribute(SysStaticParameter.LAYERORDERTREE_INSESSION);
        layerOrderTreeService.updateParamTreeLayerOrder(tree);
        request.setAttribute("operSuccess", "true");
    	return map.findForward("toOrderTreeSuccess");
    }
	public LayerOrderTreeService getLayerOrderTreeService() {
		return layerOrderTreeService;
	}
	public void setLayerOrderTreeService(LayerOrderTreeService layerOrderTreeService) {
		this.layerOrderTreeService = layerOrderTreeService;
	}
	
	/**
	 * 这个方法只是为 的到树的layerOrder用的其他地方用不上
	 * LayerOrder 格式00000001 00000010 长度是8 将lastLayerOrder末位加一返回长度唯一的字符串
	 * @param String lastLayerOrder
	 * @param int addNum 1 -1
	 * @return string 长度为8的字符串 
	 */
	private String getLayerOrderStr(String lastLayerOrder,int addNum)
	{
		String numStr = (Integer.parseInt(lastLayerOrder)+addNum)+"";
		int numL = numStr.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<(8-numL); i++)
		{
			sb.append("0");
		}
		sb.append(numL);
		return sb.toString();
	}
	
}
