package et.bo.sys.department.service.impl;

import java.util.ArrayList;
import java.util.List;

import ocelot.common.classtree.ClassTreeLoadService;
import ocelot.common.tree.TreeNode;
import ocelot.framework.base.dao.BaseDAO;

import et.bo.sys.common.SysStaticParameter;
import et.po.SysDepartment;
import et.po.SysModule;



public class ClassTreeLoadServiceDepImpl implements ClassTreeLoadService {

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
		System.out.println(commonClass.length);
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysDepartment sd=(SysDepartment)commonClass[i];
			if(sd.getTagShow().equals("1"))
				tn.setTagShow(true);
				else
					tn.setTagShow(false);
			tn.setIcon(icon);
			tn.setExpanded(exp);
			tn.setLabel(sd.getName());
			tn.setName(sd.getId().toString());
			tn.setParentName(sd.getParentId().toString());
			tn.setTarget(target);
			tn.setAction("dep.do?method=load&id="+sd.getId());
			treeList.add(tn);
		}
		return treeList;
	}

}