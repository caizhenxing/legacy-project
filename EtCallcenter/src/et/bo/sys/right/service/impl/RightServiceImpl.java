package et.bo.sys.right.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.module.service.impl.ClassTreeHelp;
import et.bo.sys.right.service.RightService;
import et.po.SysGroup;
import et.po.SysModule;
import et.po.SysRightGroup;
import et.po.SysRightUser;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlFactory;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.TreeControlNode;
import excellence.common.tree.TreeInLove;
import excellence.common.tree.TreeNode;
import excellence.framework.base.dao.BaseDAO;



public class RightServiceImpl implements RightService {

	private BaseDAO dao=null;
	private KeyService ks=null;
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public void impowerGroup(String group, TreeControl rights) {
		// TODO Auto-generated method stub
		SysGroup su=(SysGroup)dao.loadEntity(SysGroup.class,group);
		Set s=su.getSysRightGroups();
		Iterator i=s.iterator();
		while(i.hasNext())
		{
			SysRightGroup sru=(SysRightGroup)i.next();
			dao.removeEntity(sru);
		}
		dao.flush();
		String rootName = rights.getRoot().getName();
		HashMap registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			TreeControlNode node = (TreeControlNode) registry.get(key);
			if (SysStaticParameter.GICON.equalsIgnoreCase(node.getIcon()))
			{
				SysRightGroup sru=new SysRightGroup();
				sru.setId(ks.getNext("SysRightUser"));
				sru.setSysGroup(su);
				SysModule sm=(SysModule)dao.loadEntity(SysModule.class,node.getName());
				if(sm!=null)
				sru.setSysModule(sm);
				dao.saveEntity(sru);
			}			
		}
	}

	public void impowerUser(String user, TreeControl rights) {
		// TODO Auto-generated method stub
		SysUser su=(SysUser)dao.loadEntity(SysUser.class,user);
		Set s=su.getSysRightUsers();
		Iterator i=s.iterator();
		while(i.hasNext())
		{
			SysRightUser sru=(SysRightUser)i.next();
			dao.removeEntity(sru);
		}
		dao.flush();
		String rootName = rights.getRoot().getName();
		HashMap registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			TreeControlNode node = (TreeControlNode) registry.get(key);
			if (SysStaticParameter.UICON.equalsIgnoreCase(node.getIcon()))
			{
				SysRightUser sru=new SysRightUser();
				sru.setId(ks.getNext("SysRightUser"));
				sru.setSysUser(su);
				SysModule sm=(SysModule)dao.loadEntity(SysModule.class,node.getName());
				if(sm!=null)
				sru.setSysModule(sm);
				dao.saveEntity(sru);
			}			
		}
	}

	public List<String> loadRight(String userId) {
		// TODO Auto-generated method stub
		List<String> temp=new ArrayList<String>();
		SysUser su=(SysUser)dao.loadEntity(SysUser.class,userId);
		SysGroup sg=su.getSysGroup();
		if(sg!=null)
		{
			Set sgr=sg.getSysRightGroups();
			Iterator a=sgr.iterator();
			while(a.hasNext())
			{
				SysRightGroup srg=(SysRightGroup)a.next();
				temp.add(srg.getSysModule().getId());
			}
		}
		Set sur=su.getSysRightUsers();
		
		
		Iterator ii=sur.iterator();
		while(ii.hasNext())
		{
			SysRightUser sru=(SysRightUser)ii.next();
			String id=sru.getSysModule().getId();
			if(!temp.contains(id))
				temp.add(id);
		}
		
		return temp;
	}
	private List<String> loadRightGroup(String id)
	{
		List<String> temp=new ArrayList<String>();
		
		SysGroup sg=(SysGroup)dao.loadEntity(SysGroup.class,id);
		if(sg!=null)
		{
			Set sgr=sg.getSysRightGroups();
			Iterator a=sgr.iterator();
			while(a.hasNext())
			{
				SysRightGroup srg=(SysRightGroup)a.next();
				temp.add(srg.getSysModule().getId());
			}
		}
		return temp;
	}
	private List<String> loadRightUser(String id)
	{
		List<String> temp=new ArrayList<String>();
		
		SysUser su=(SysUser)dao.loadEntity(SysUser.class,id);
		Set sur=su.getSysRightUsers();
		
		
		Iterator ii=sur.iterator();
		while(ii.hasNext())
		{
			SysRightUser sru=(SysRightUser)ii.next();
			String mid=sru.getSysModule().getId();
			
				temp.add(mid);
		}
		
		return temp;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public TreeControlI loadGroupRight(String group) {
		// TODO Auto-generated method stub
		List<String> right=loadRightGroup(group);
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		//String target="operationframe";
		ClassTreeHelp cth=new ClassTreeHelp();
		
		String type="module";
		String action="moduleLinkTreeSearch.do?method=group&tree=";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		TreeNode root=null;
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysModule sm=(SysModule)commonClass[i];
			if(right.contains(sm.getId()))
			tn.setIcon(SysStaticParameter.GICON);
			else
				tn.setIcon(SysStaticParameter.NICON);
			tn.setExpanded(exp);
			tn.setLabel(sm.getName());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			//tn.setTarget(target);
			tn.setDomain(type);
			//tn.setAction("module.do?method=load&id="+sm.getId());
			tn.setAction(action+sm.getId());
			treeList.add(tn);
			if(tn.getParentName().equals(ClassTreeService.ROOT))
				root=tn;
		}
		TreeInLove til=new TreeInLove();
		til.setL(treeList);
		
		til.setTcn(new TreeControlNode(root));
		TreeControlI tci=TreeControlFactory.creator("moduleSession",til);
		return tci;
	}

	public TreeControlI loadUserRight(String user) {
		// TODO Auto-generated method stub
		List<String> right=loadRightUser(user);
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		String target="operationframe";
		ClassTreeHelp cth=new ClassTreeHelp();
		
		String type="module";
		String action="moduleLinkTreeSearch.do?method=user&tree=";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
	//	
		TreeNode root=null;
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysModule sm=(SysModule)commonClass[i];
			if(right.contains(sm.getId()))
			tn.setIcon(SysStaticParameter.UICON);
			else
				tn.setIcon(SysStaticParameter.NICON);
			tn.setExpanded(exp);
			tn.setLabel(sm.getName());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			//tn.setTarget(target);
			tn.setDomain(type);
			//tn.setAction("module.do?method=load&id="+sm.getId());
			tn.setAction(action+sm.getId());
			treeList.add(tn);
			if(tn.getParentName().equals(ClassTreeService.ROOT))
				root=tn;
		}
		TreeInLove til=new TreeInLove();
		til.setL(treeList);
		
		til.setTcn(new TreeControlNode(root));
		TreeControlI tci=TreeControlFactory.creator("moduleSession",til);
		return tci;
	}
}
