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
	//�����ڵ����۵��˵���ʽ��ӡ���� ���ӽڵ�ĸ��ڵ� ѭ������ ����Ҷ�ӽڵ��ӡ����
	public String render(StringBuffer sb) 
	{
		if(tree != null)
		{
			//�Ӳ˵����㲹�յ�
			node = (ViewTreeControlNode)tree.getRoot();
			TreeControlNodeService[] nodes = node.findChildren();
			List<String> countList = new ArrayList<String>();
			for(int i=0; i<nodes.length; i++)
			{
				//<H1 class="title"><A href="javascript:void(0)">Ajax ��������</a></H1>
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
	//�������ڵ�ĺ��ӽڵ���action��Ҷ�ӽڵ��ӡ����
	public String renderRootSubLeaf(StringBuffer sb) 
	{
		if(tree != null)
		{
			//�Ӳ˵����㲹�յ�
			node = (ViewTreeControlNode)tree.getRoot();
			TreeControlNodeService[] nodes = node.findChildren();
			List<String> countList = new ArrayList<String>();
			for(int i=0; i<nodes.length; i++)
			{
				//<H1 class="title"><A href="javascript:void(0)">Ajax ��������</a></H1>
				//<div class="content">
				if(i==0)
				{
					sb.append("<H1 class=\"title\"><A href=\"javascript:void(0)\">�������ܽڵ�</a></H1>");
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
	 * ��һ���ڵ� �ݹ����������Ҷ�ӽڵ�
	 * @param ViewTreeControlNode node ��������ڵ���ӽڵ�
	 * @param StringBuffer sb ����׷���ַ���
	 * @param List countList һ��������List ��¼����ڵ�����Ч��Ҷ�ӽڵ�����
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
	 * ��һ���ڵ� �ݹ����������Ҷ�ӽڵ�
	 * @param ViewTreeControlNode node ��������ڵ���ӽڵ�
	 * @param StringBuffer sb ����׷���ַ���
	 * @param List countList һ��������List ��¼����ڵ�����Ч��Ҷ�ӽڵ�����
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
	 * ��һ���ڵ� �ݹ����������Ҷ�ӽڵ�
	 * @param int number ��Ҫ���ֶ��ٸ����ýڵ�
	 */
	public void renderNumberNullNode(StringBuffer sb,int number)
	{
		for(int i=0; i<number; i++)
		{
			sb.append("<p style=\"margin-left:-500px;\"><a href=\"javascript:void(0)\" >.</a></p>");
		}
	}
}
