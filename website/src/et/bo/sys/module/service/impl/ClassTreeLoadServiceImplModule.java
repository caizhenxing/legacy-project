package et.bo.sys.module.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.common.SysStaticParameter;
import et.po.SysModule;
import et.po.SysTree;
import excellence.common.classtree.ClassTreeLoadService;
import excellence.common.tree.TreeNode;
import excellence.framework.base.dao.BaseDAO;



public class ClassTreeLoadServiceImplModule implements ClassTreeLoadService {

	private BaseDAO dao=null;
	ClassTreeHelp cth=new ClassTreeHelp();
	public List<TreeNode> load() {
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		String target=SysStaticParameter.MODULE_TARGET;
		//String gicon=SysStaticParameter.TREE_ICON_GROUP;
		//String uicon=SysStaticParameter.TREE_ICON_USER;
		String type="module";
		//String action="/ESell/system/type.do";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysModule sm=(SysModule)commonClass[i];
			if(sm.getTagShow().equals("1"))
			tn.setTagShow(true);
			else
				tn.setTagShow(false);
			tn.setIcon(sm.getIcon());
			tn.setExpanded(exp);
			tn.setLabel(sm.getName());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			tn.setTarget(target);
			tn.setDomain(type);
			tn.setOrder(sm.getLayerOrder());
			if(sm.getAction()!=null&&!sm.getAction().trim().equals(""))
			tn.setAction(sm.getAction());
			//tn.setAction(action+"?method=loadTreeNode&id="+sm.getId());
			treeList.add(tn);
		}
		return treeList;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	

}
