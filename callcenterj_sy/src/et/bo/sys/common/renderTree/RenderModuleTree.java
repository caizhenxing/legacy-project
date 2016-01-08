package et.bo.sys.common.renderTree;

import java.util.ArrayList;
import java.util.List;

import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTreeNode;

public class RenderModuleTree {
	private ViewTree tree;
	private ViewTreeControlNode node;
	private final int SHOW_SUM_ROW = 8;
	private String basePath;
	String cssImg = "";
	public RenderModuleTree(ViewTree tree)
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

				ViewTreeControlNode tNode = (ViewTreeControlNode)nodes[i];
				ViewTreeNode v = (ViewTreeNode)tNode.getBaseTreeNodeService().getTreeNodeExtendedService();

				if(!tNode.isLeaf()&&!"0".equals(v.getTagShow()))
				{
					String imgPath = tNode.getIconByKey("collapse_r");
					if(imgPath!=null&&"".equals(imgPath)==false&&basePath!=null)
					{
						imgPath = basePath+"/"+imgPath;
						cssImg = "background-image: url("+imgPath+");";
					}
					renderAllLeafNodeAndTitleOfNodes(tNode,sb,countList,tNode.getLabel());
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
			int count = 0;
			node = (ViewTreeControlNode)tree.getRoot();
			TreeControlNodeService[] nodes = node.findChildren();
			List<String> countList = new ArrayList<String>();
			for(int i=0; i<nodes.length; i++)
			{
				count = i;
				String imgPath = "dh26.jpg";
				
				if(imgPath!=null&&"".equals(imgPath)==false&&basePath!=null)
				{
					imgPath = basePath+"/"+imgPath;
					cssImg = "background-image: url("+imgPath+");";
				}
				ViewTreeControlNode tNode = (ViewTreeControlNode)nodes[i];
				if(tNode.isLeaf()&&tNode.getAction()!=null&&!"".equals(tNode.getAction().trim()))
				{
					ViewTreeNode v = (ViewTreeNode)tNode.getBaseTreeNodeService().getTreeNodeExtendedService();
					if(!"0".equals(v.getTagShow()))
					{
						sb.append("<p><a href=\""+tNode.getAction()+"\" target=\"contents\">"+tNode.getLabel()+"</a></p>");
						count--;
					}
					if(i==(nodes.length-1))
					{
						if((SHOW_SUM_ROW-i)>0)
						{
							renderNumberNullNode(sb,SHOW_SUM_ROW-count);
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
					ViewTreeNode vNode = (ViewTreeNode)cNode.getBaseTreeNodeService().getTreeNodeExtendedService();
//					//System.out.println(vNode.getTagShow()+"&&&&&&&&&&&&&&&&&&&&&&&&");
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
					ViewTreeNode vNode = (ViewTreeNode)cNode.getBaseTreeNodeService().getTreeNodeExtendedService();
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
	 * @param ViewTreeControlNode node 遍历这个节点的子节点
	 * @param StringBuffer sb 往里追加字符串
	 * @param List countList 一个记数器List 记录这个节点下有效的叶子节点数量
	 */
	public void renderAllLeafNodeAndTitleOfNodes(ViewTreeControlNode node, StringBuffer sb, List<String> countList,String label)
	{
		
		TreeControlNodeService[] nodes = node.findChildren();
		int count = 0;
		for(int i=0; i<nodes.length; i++)
		{
			ViewTreeControlNode cNode = (ViewTreeControlNode)nodes[i];
			if(cNode.isLeaf())
			{
				if(cNode.getAction()!=null&&!"".equals(cNode.getAction()))
				{
					if(count==0)
					{
						String title = "<H1 class=\"title\"><A href=\""+cNode.getAction()+"\" target=\"contents\"><div style=\"display:inline;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><span>"+label+"</span></A></H1>";
						
						sb.append(title);
						sb.append("<div class=\"content\">");
						count++;
					}
					countList.add(cNode.getId());
					ViewTreeNode vNode = (ViewTreeNode)cNode.getBaseTreeNodeService().getTreeNodeExtendedService();
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
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	
}
