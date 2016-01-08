package et.bo.sys.right.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.right.service.RightService;
import et.po.BaseTree;
import et.po.SysGroup;
import et.po.SysRightGroup;
import et.po.SysRightUser;
import et.po.SysUser;
import et.po.ViewTreeDetail;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.base.build.impl.BuildTreeServiceImpl;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.impl.TreeInfoServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
import excellence.common.tree.ext.view.impower.ViewTreeControlImpowerNode;
import excellence.framework.base.dao.BaseDAO;



public class RightServiceImpl implements RightService {

	private BaseDAO dao=null;
	private KeyService ks=null;
	private ClassTreeService classTreeService = null;
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}

	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}

	public void impowerGroup(String group, TreeService rights) {
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
		String rootName = rights.getRoot().getId();
		Map registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(key);
			if (SysStaticParameter.GICON.equalsIgnoreCase(node.getTmpIcon()))
			{
				SysRightGroup sru=new SysRightGroup();
				sru.setId(ks.getNext("SysRightUser"));
				sru.setSysGroup(su);
				BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,node.getId());
				if(bt!=null)
				sru.setBaseTree(bt);
				dao.saveEntity(sru);
			}			
		}
	}
	/**
	 * 由用户id得到该用户所具有的模块权限
	 * @param userId
	 * @return
	 */
	public TreeService getModuleTreeImpowers(String userId)
	{
		List<String> rights = this.loadRight(userId);
		TreeService treeService = new ViewTree();
		BuildTreeService bts = new BuildTreeServiceImpl();
		List<BaseTreeNodeService> treeList = new ArrayList<BaseTreeNodeService>(); 

		ClassTreeHelp cth=new ClassTreeHelp();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		ViewTreeControlImpowerNode root= new ViewTreeControlImpowerNode();
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			BaseTree bt=(BaseTree)commonClass[i];
			//System.out.println(bt.getType()+":"+bt.getLabel()+":"+bt.getId());
			if(SysStaticParameter.MOD_NODE_TYPE.equals(bt.getType()))
			{
				//System.out.println(bt.getIsRoot()+"isroot"+bt.getLabel()+":"+bt.getId()+commonClass.length);
				//过滤用户权限
				if(SysStaticParameter.SYSTEM_ADMINISTRATOR_ID.equals(userId)==false)
				{
					if(rights.contains(bt.getId())||"1".equals(bt.getIsRoot()))
					{
						BaseTreeNodeService btn=new BaseTreeNodeServiceImpl();
						ViewTreeNode tens = new ViewTreeNode();
						Iterator it = bt.getViewTreeDetails().iterator();
						if(it.hasNext())
						{
							ViewTreeDetail vtd = (ViewTreeDetail)it.next();
							tens.setIcon(vtd.getIcon());
							tens.setAction(vtd.getAction());
							tens.setTarget("contents");
						}
						btn.setLabel(bt.getLabel());
						
						btn.setId(bt.getId());
						btn.setParentId(bt.getParentId());
						btn.setNickName(bt.getNickName());
						btn.setTreeNodeExtendedService(tens);
						treeList.add(btn);
						if("1".equals(bt.getIsRoot()))
						{
							root.setBaseTreeNodeService(btn);
							treeService.setRoot(root);
						}
						
					}
				}
				else
				{
					BaseTreeNodeService btn=new BaseTreeNodeServiceImpl();
					ViewTreeNode tens = new ViewTreeNode();
					Iterator it = bt.getViewTreeDetails().iterator();
					if(it.hasNext())
					{
						ViewTreeDetail vtd = (ViewTreeDetail)it.next();
						tens.setIcon(vtd.getIcon());
						tens.setAction(vtd.getAction());
						tens.setTarget("contents");
					}
					btn.setLabel(bt.getLabel());
					
					btn.setId(bt.getId());
					btn.setParentId(bt.getParentId());
					btn.setNickName(bt.getNickName());
					btn.setTreeNodeExtendedService(tens);
					treeList.add(btn);
					if("1".equals(bt.getIsRoot()))
					{
						root.setBaseTreeNodeService(btn);
						treeService.setRoot(root);
					}
				}
				//结束不过虑权限全加载
			}
		}
		TreeInfoService tis = new TreeInfoServiceImpl();
		tis.setRoot(root);
		tis.setTreeNodeList(treeList);
		bts.setTreeService(treeService);
		try
		{
			//System.out.println(tis.getRoot()+":"+tis.getTreeNodeList().size()+"::::::");
			treeService = bts.creator(tis);
		}
		catch(Exception e){e.printStackTrace();}
		return treeService;
	}
	/**
	 * 给用户授权
	 * @param userId
	 * @param TreeService rights
	 * @return
	 */
	public void impowerUser(String user, TreeService rights) {
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
		String rootName = rights.getRoot().getId();
		Map registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(key);
			if (SysStaticParameter.UICON.equalsIgnoreCase(node.getTmpIcon()))
			{
				SysRightUser sru=new SysRightUser();
				sru.setId(ks.getNext("SysRightUser"));
				sru.setSysUser(su);
				BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,node.getId());
				if(bt!=null)
				sru.setBaseTree(bt);
				dao.saveEntity(sru);
			}			
		}
	}

	public List<String> loadRight(String userId) {
		// TODO Auto-generated method stub
		List<String> temp=new ArrayList<String>();
		SysUser su=(SysUser)dao.loadEntity(SysUser.class,userId);
		//.println("su is "+ su +userId+">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		SysGroup sg=su.getSysGroup();
		if(sg!=null)
		{
			Set sgr=sg.getSysRightGroups();
			Iterator a=sgr.iterator();
			while(a.hasNext())
			{
				SysRightGroup srg=(SysRightGroup)a.next();
				temp.add(srg.getBaseTree().getId());
			}
		}
		Set sur=su.getSysRightUsers();
		
		
		Iterator ii=sur.iterator();
		while(ii.hasNext())
		{
			SysRightUser sru=(SysRightUser)ii.next();
			String id=sru.getBaseTree().getId();
			if(!temp.contains(id))
				temp.add(id);
		}
		
		return temp;
	}
	//根据组id得到当前组所拥有的权限
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
				temp.add(srg.getBaseTree().getId());
			}
		}
		return temp;
	}
	//根据id加载当前用户 有了当前用户 就有了组 用户权限
	private SysUser getSysUserById(String id)
	{
		SysUser su=(SysUser)dao.loadEntity(SysUser.class,id);
		return su;
	}
	//根据id得到当前用户的权限
	private List<String> loadRightUser(SysUser su)
	{
		List<String> temp=new ArrayList<String>();
		
		Set sur=su.getSysRightUsers();
		
		Iterator ii=sur.iterator();
		while(ii.hasNext())
		{
			SysRightUser sru=(SysRightUser)ii.next();
			temp.add(sru.getBaseTree().getId());
		}
		
		return temp;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	/**
	 * 根据组id得到树
	 * group 是组id
	 */
	public TreeService loadGroupRight(String group) {

		//已有的权限
		//TreeService treeService = classTreeService.getSubTreeByNickName(SysStaticParameter.MOD_ROOT_NICKNAME);
			
		TreeService treeService = new ViewTree();
		BuildTreeService bts = new BuildTreeServiceImpl();
		List<String> right=loadRightGroup(group);
		List<BaseTreeNodeService> treeList = new ArrayList<BaseTreeNodeService>(); 
		//String target="operationframe";
		ClassTreeHelp cth=new ClassTreeHelp();
		
		String action="moduleLinkTreeSearch.do?method=group";
		//得到所有的SysModule  DetachedCriteria dc=DetachedCriteria.forClass(SysModule.class);
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		ViewTreeControlNode root= new ViewTreeControlNode();
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			BaseTree bt=(BaseTree)commonClass[i];
			if(SysStaticParameter.MOD_NODE_TYPE.equals(bt.getType()))
			{
				BaseTreeNodeService btn=new BaseTreeNodeServiceImpl();
				ViewTreeNode tens = new ViewTreeNode();
				
				if(right.contains(bt.getId()))
				{
					//tn.setIcon(SysStaticParameter.GICON);
					tens.setTmpIcon(SysStaticParameter.GICON);
				}
				Iterator it = bt.getViewTreeDetails().iterator();
				if(it.hasNext())
				{
					tens.setIcon(((ViewTreeDetail)it.next()).getIcon());
				}
				//tens.setIcon(bt.getViewTreeDetails().iterator().)
				//tn.setExpanded(exp);
				btn.setLabel(bt.getLabel());
				//.println(bt.getId()+">>>>>>>>>>>>>>>>>."+sm.getIsRoot());
				btn.setId(bt.getId());
				btn.setParentId(bt.getParentId());
				btn.setNickName(bt.getNickName());
				tens.setAction(action);
				btn.setTreeNodeExtendedService(tens);
				treeList.add(btn);
				if("1".equals(bt.getIsRoot()))
				{
					root.setBaseTreeNodeService(btn);
					treeService.setRoot(root);
				}
			}
		}
		TreeInfoService tis = new TreeInfoServiceImpl();
		tis.setRoot(root);
		tis.setTreeNodeList(treeList);
		bts.setTreeService(treeService);
		treeService = bts.creator(tis);
		return treeService;

	}
	/**
	 * 得到用户所具有的组权限及用户个人所具有的权限
	 * @param user 用户id
	 * @return TreeService 
	 */
	public TreeService loadUserRight(String user) {
		// TODO Auto-generated method stub
		TreeService treeService = new ViewTree();
		BuildTreeService bts = new BuildTreeServiceImpl();
		SysUser su = this.getSysUserById(user);
		List<String> userRight=loadRightUser(su);
		List<String> groupRight = new ArrayList<String>();
		SysGroup sg = su.getSysGroup();
		
		if(sg!=null&&sg.getId()!=null)
		{
			groupRight = this.loadRightGroup(su.getSysGroup().getId());
		}
	
		List<BaseTreeNodeService> treeList = new ArrayList<BaseTreeNodeService>(); 
		//String target="operationframe";
		ClassTreeHelp cth=new ClassTreeHelp();
		
		String action="moduleLinkTreeSearch.do?method=user";
		//得到所有的SysModule  DetachedCriteria dc=DetachedCriteria.forClass(SysModule.class);
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		ViewTreeControlImpowerNode root= new ViewTreeControlImpowerNode();
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			BaseTree bt=(BaseTree)commonClass[i];
			if(SysStaticParameter.MOD_NODE_TYPE.equals(bt.getType()))
			{
				BaseTreeNodeService btn=new BaseTreeNodeServiceImpl();
				ViewTreeNode tens = new ViewTreeNode();
				
				if(userRight.contains(bt.getId()))
				{
					//tn.setIcon(SysStaticParameter.GICON);
					tens.setTmpIcon(SysStaticParameter.UICON);
				}
				if(groupRight.contains(bt.getId()))
				{
					tens.setTmpIcon(SysStaticParameter.GICON);
				}
				Iterator it = bt.getViewTreeDetails().iterator();
				if(it.hasNext())
				{
					tens.setIcon(((ViewTreeDetail)it.next()).getIcon());
				}
				btn.setLabel(bt.getLabel());
				
				btn.setId(bt.getId());
				btn.setParentId(bt.getParentId());
				btn.setNickName(bt.getNickName());
				tens.setAction(action);
				btn.setTreeNodeExtendedService(tens);
				treeList.add(btn);
				if("1".equals(bt.getIsRoot()))
				{
					root.setBaseTreeNodeService(btn);
					treeService.setRoot(root);
				}
			}
		}
		TreeInfoService tis = new TreeInfoServiceImpl();
		tis.setRoot(root);
		tis.setTreeNodeList(treeList);
		bts.setTreeService(treeService);
		treeService = bts.creator(tis);
		return treeService;
	}
}
