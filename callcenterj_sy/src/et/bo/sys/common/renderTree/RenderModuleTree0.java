package et.bo.sys.common.renderTree;

import java.util.ArrayList;
import java.util.List;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;

public class RenderModuleTree0 {
	private ViewTree tree;
	private ViewTreeControlNode node;
	private final int SHOW_SUM_ROW = 12;
	public RenderModuleTree0(ViewTree tree)
	{
		this.tree = tree;
	}
	//将树节点以折叠菜单形式打印出来 有子节点的父节点 循环遍历 将其叶子节点打印出来
	public String render(StringBuffer sb) 
	{
		if(tree != null)
		{
			//子菜单不足补空的
			node = (ViewTreeControlNode)tree.getRoot();
			TreeControlNodeService[] nodes = node.findChildren();
			List<String> countList = new ArrayList<String>();
			for(int i=0; i<nodes.length; i++)
			{
				//<H1 class="title"><A href="javascript:void(0)">Ajax 入门两则</a></H1>
				//<div class="content">
				ViewTreeControlNode tNode = (ViewTreeControlNode)nodes[i];
				if(!tNode.isLeaf())
				{
					sb.append("<H1 class=\"title\"><A href=\"javascript:void(0)\">"+tNode.getLabel()+"</a></H1>");
					sb.append("<div class=\"content\">");
					renderAllLeafNodeOfNodes(tNode,sb,countList);
					if((SHOW_SUM_ROW-countList.size())>0)
					{
						renderNumberNullNode(sb,SHOW_SUM_ROW-countList.size());
					}
					countList.clear();
					sb.append("</div>");
				}
			}
		}
		return sb.toString();
	}
	//将树根节点的孩子节点有action的叶子节点打印出来
	public String renderRootSubLeaf(StringBuffer sb) 
	{
		if(tree != null)
		{
			//子菜单不足补空的
			node = (ViewTreeControlNode)tree.getRoot();
			TreeControlNodeService[] nodes = node.findChildren();
			List<String> countList = new ArrayList<String>();
			for(int i=0; i<nodes.length; i++)
			{
				//<H1 class="title"><A href="javascript:void(0)">Ajax 入门两则</a></H1>
				//<div class="content">
				if(i==0)
				{
					sb.append("<H1 class=\"title\"><A href=\"javascript:void(0)\">其他功能节点</a></H1>");
					sb.append("<div class=\"content\">");
				}
				ViewTreeControlNode tNode = (ViewTreeControlNode)nodes[i];
				if(tNode.isLeaf()&&tNode.getAction()!=null&&!"".equals(tNode.getAction().trim()))
				{
					sb.append("<p><a href=\""+tNode.getAction()+"\" target=\"contents\">"+tNode.getLabel()+"</a></p>");
				
					if(i==(nodes.length-1))
					{
						if((SHOW_SUM_ROW-i)>0)
						{
							renderNumberNullNode(sb,SHOW_SUM_ROW-i);
						}
						sb.append("</div>");
					}
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 传一个节点 递归呈现其下属叶子节点
	 * @param ViewTreeControlNode node 遍历这个节点的子节点
	 * @param StringBuffer sb 往里追加字符串
	 * @param List countList 一个记数器List 记录这个节点下有效的叶子节点数量
	 */
	public void renderAllLeafNodeOfNodes(ViewTreeControlNode node, StringBuffer sb)
	{
		TreeControlNodeService[] nodes = node.findChildren();
		for(int i=0; i<nodes.length; i++)
		{
			ViewTreeControlNode cNode = (ViewTreeControlNode)nodes[i];
			if(cNode.isLeaf())
			{
				if(cNode.getAction()!=null&&!"".equals(cNode.getAction()))
				{
					sb.append("<p><a href=\""+cNode.getAction()+"\" target=\"contents\">"+cNode.getLabel()+"</a></p>");
				}
			}
			else
			{
				renderAllLeafNodeOfNodes(cNode, sb);
			}
		}
	}
	/**
	 * 传一个节点 递归呈现其下属叶子节点
	 * @param ViewTreeControlNode node 遍历这个节点的子节点
	 * @param StringBuffer sb 往里追加字符串
	 * @param List countList 一个记数器List 记录这个节点下有效的叶子节点数量
	 */
	public void renderAllLeafNodeOfNodes(ViewTreeControlNode node, StringBuffer sb, List<String> countList)
	{
		
		TreeControlNodeService[] nodes = node.findChildren();
		for(int i=0; i<nodes.length; i++)
		{
			ViewTreeControlNode cNode = (ViewTreeControlNode)nodes[i];
			if(cNode.isLeaf())
			{
				if(cNode.getAction()!=null&&!"".equals(cNode.getAction()))
				{
					countList.add(cNode.getId());
					sb.append("<p><a href=\""+cNode.getAction()+"\" target=\"contents\">"+cNode.getLabel()+"</a></p>");
				}
			}
			else
			{
				renderAllLeafNodeOfNodes(cNode, sb, countList);
			}
		}
	}
	/**
	 * 传一个节点 递归呈现其下属叶子节点
	 * @param int number 需要呈现多少个无用节点
	 */
	public void renderNumberNullNode(StringBuffer sb,int number)
	{
		for(int i=0; i<number; i++)
		{
			sb.append("<p style=\"margin-left:-500px;\"><a href=\"javascript:void(0)\" >.</a></p>");
		}
	}
}
