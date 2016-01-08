package et.bo.sys.common.tree.parameter.build;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import et.bo.sys.common.SysStaticParameter;
import et.config.hibernate.HibernateSessionFactory;
import excellence.common.tree.base.build.impl.BuildTreeServiceImpl;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.impl.TreeInfoServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTreeNode;

public class ViewTestTree {
	public static void main(String[] args)
	{
		ViewTestTree vtt = new ViewTestTree();
		vtt.getTree();
	}
	public TreeService getTree()
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark = 0";
		
		List l = session.createQuery(hql).list();
		Iterator it = l.iterator();
		TreeInfoServiceImpl ti = new TreeInfoServiceImpl();
		List<ViewTreeNode> vtnList = new ArrayList<ViewTreeNode>();
		while (it.hasNext()) {
			Object[] o = (Object[]) it.next();
			BaseTreeNodeService btn = new BaseTreeNodeServiceImpl();
			btn.setId(o[0].toString());
			btn.setLabel((o[1]==null?"":o[1].toString()));

			btn.setParentId((o[2]==null?"":o[2].toString()));
			ViewTreeNode vtn = new ViewTreeNode();
			vtn.setAction(((o[3]==null?"":(String)o[3])));
			vtn.setIcon((o[4]==null?"":(String)o[4]));
			//vtn.setBaseTreeNodeService(btn);
			btn.setIsRoot((o[7]==null?"0":(String)o[7]));
			btn.setTreeNodeExtendedService(vtn);
			//System.out.println(btn.getIsRoot()+"*"+o[7]);
			vtnList.add(vtn);
			//if("SYS_TREE_0000000625".equals(btn.getParentId()))

			if("treeRoot".equals(btn.getParentId()))
			{
				//System.out.println(btn.getIsRoot()+"ttttttttttttt"+o[7]);
				ViewTreeControlNode vtcn = new ViewTreeControlNode();
				//vtcn.setTreeNodeService(vtn);
				vtcn.setBaseTreeNodeService(btn);
				ti.setRoot(vtcn);
			}
			
		}
		ti.setTreeNodeList(vtnList);
		try
		{
			//随变找两个节点测试
			//TestTree tt = new TestTree();
			BuildTreeService bts = new BuildTreeServiceImpl();
			bts.setTreeService(new ViewTree());
			//System.out.println(tos.getRoot()+"-----------------");
			TreeService tree = bts.creator(ti);
			//ViewTreeControlNode v = (ViewTreeControlNode)tree.findNode("17");
			//System.out.println(v.getWidth(v));
				
			
			return tree;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
