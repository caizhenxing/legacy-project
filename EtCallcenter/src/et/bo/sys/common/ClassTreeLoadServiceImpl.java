package et.bo.sys.common;

import java.util.ArrayList;
import java.util.List;

import et.po.SysTree;
import excellence.common.classtree.ClassTreeLoadService;
import excellence.common.tree.TreeNode;
import excellence.framework.base.dao.BaseDAO;



public class ClassTreeLoadServiceImpl implements ClassTreeLoadService {

	private BaseDAO baseDao=null;
	ClassTreeHelp cth=new ClassTreeHelp();
	public List<TreeNode> load() {
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		String target="operationframe";
		//String gicon=SysStaticParameter.TREE_ICON_GROUP;
		//String uicon=SysStaticParameter.TREE_ICON_USER;
		String domain="";
		String icon=SysStaticParameter.NICON;
		//String action="/ESell/system/type.do";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=baseDao.findEntity(cth.createClassTreeQuery());
		
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysTree sm=(SysTree)commonClass[i];
			if(sm.getTagShow().equals("1"))
				tn.setTagShow(true);
				else
					tn.setTagShow(false);
			tn.setIcon(icon);
			tn.setExpanded(exp);
			tn.setLabel(sm.getLabel());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			tn.setTarget(target);
			tn.setDomain(sm.getType());
			tn.setNickname(sm.getProcAlias());
			tn.setAction("tree.do?method=load&id="+tn.getName());
			treeList.add(tn);
		}
		return treeList;
	}
	public BaseDAO getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

}
