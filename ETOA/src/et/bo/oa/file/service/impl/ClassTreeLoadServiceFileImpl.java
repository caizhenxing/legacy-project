package et.bo.oa.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.common.SysStaticParameter;
import et.po.FileManager;
import excellence.common.classtree.ClassTreeLoadService;
import excellence.common.tree.TreeNode;
import excellence.framework.base.dao.BaseDAO;



public class ClassTreeLoadServiceFileImpl implements ClassTreeLoadService {

	private BaseDAO dao=null;
	ClassTreeHelp cth=new ClassTreeHelp();
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public List<TreeNode> load() {
		// TODO Auto-generated method stub
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		String target="operationframe";
		//String gicon=SysStaticParameter.TREE_ICON_GROUP;
		String icon=SysStaticParameter.NICON;
		//String type="module";
		//String action="/ESell/system/type.do";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			FileManager fm=(FileManager)commonClass[i];
			
				tn.setTagShow(true);
			
			tn.setIcon(icon);
			tn.setExpanded(exp);
			tn.setLabel(fm.getName());
			tn.setName(fm.getId());
			tn.setParentName(fm.getParentId().toString());
			tn.setTarget(target);
			tn.setAction("dep.do?method=load&id="+fm.getId());
			treeList.add(tn);
		}
		if(treeList.size()==0)
			return null;
		return treeList;
	}

}
