package et.bo.sys.module.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.module.service.ModuleService;
import et.bo.sys.right.service.RightService;

import et.po.SysModule;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.TreeControlFactory;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.TreeControlNode;
import excellence.common.tree.TreeInLove;
import excellence.common.tree.TreeNode;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ModuleServiceImpl implements ModuleService {

	private ClassTreeService cts=null;
	private BaseDAO dao=null;
	private RightService rs=null;
	private KeyService ks = null;
	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public TreeControlI loadModules(String userId) {
		// TODO Auto-generated method stub
		
		List<String> rights=rs.loadRight(userId);
		return cts.getTree(rights);
		
	}

	public void addModule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		SysModule sm=new SysModule();
		sm.setAction((String)dto.get("action"));
		sm.setName((String)dto.get("name"));
		sm.setParentId((String)dto.get("parentId"));
		sm.setRemarks((String)dto.get("remark"));
		sm.setTagShow((String)dto.get("tagShow"));
		sm.setId(ks.getNext("sys_module"));
		sm.setIcon(SysStaticParameter.NICON);
		sm.setLayerOrder(sm.getId());
		dao.saveEntity(sm);
		cts.reload();
	}

	public void removeModule(String id) {
		// TODO Auto-generated method stub
		SysModule sm=(SysModule)dao.loadEntity(SysModule.class,id);
		if(sm!=null)
		{
			if(null!=sm.getIsSys()&&"1".equals(sm.getIsSys()))
				return ;
		}
		else
			return ;
		List list=recursionFind(id);
		Iterator i=list.iterator();
		while(i.hasNext())
		{
			dao.removeEntity(i.next());
		}
		cts.reload();
	}

	public void updateModule(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String id=(String)dto.get("id");
		SysModule sm=(SysModule)dao.loadEntity(SysModule.class,id);
		if(sm!=null)
		{
			sm.setAction((String)dto.get("action"));
			sm.setName((String)dto.get("name"));
			sm.setParentId((String)dto.get("parentId"));
			sm.setRemarks((String)dto.get("remark"));
			sm.setTagShow((String)dto.get("tagShow"));
		}
		cts.reload();
		dao.saveEntity(sm);
	}

	public IBaseDTO getModule(String id) {
		// TODO Auto-generated method stub
		IBaseDTO  dto=new DynaBeanDTO();
		SysModule sm=(SysModule)dao.loadEntity(SysModule.class,id);
		dto.set("action",sm.getAction());
		dto.set("id",sm.getId());
		dto.set("name",sm.getName());
		dto.set("parentId",sm.getParentId());
		dto.set("remark",sm.getRemarks());
		dto.set("tagShow",sm.getTagShow());
		
		return dto;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public RightService getRs() {
		return rs;
	}

	public void setRs(RightService rs) {
		this.rs = rs;
	}

	public TreeControlI loadModules() {
		// TODO Auto-generated method stub
		List<TreeNode> treeList = new ArrayList<TreeNode>(); 
		String target="operationframe";
		ClassTreeHelp cth=new ClassTreeHelp();
		
		String type="module";
		//String action="/ESell/system/type.do";
		boolean exp=SysStaticParameter.TREE_EXPANDED;
		treeList=new ArrayList();
		Object[] commonClass=dao.findEntity(cth.createClassTreeQuery());
		
		TreeNode root=null;
		for(int i=0,j=commonClass.length;i<j;i++)
		{
			TreeNode tn=new TreeNode();
			SysModule sm=(SysModule)commonClass[i];
			tn.setIcon(sm.getIcon());
			tn.setExpanded(exp);
			tn.setLabel(sm.getName());
			tn.setName(sm.getId().toString());
			tn.setParentName(sm.getParentId().toString());
			tn.setTarget(target);
			tn.setDomain(type);
			tn.setAction("module.do?method=load&id="+sm.getId());
			//tn.setAction(action+"?method=loadTreeNode&id="+sm.getId());
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
	private List recursionFind(Object id)
	{
		List list=new ArrayList();
		Queue queue=new LinkedList();
		queue.add(id);
		while(!queue.isEmpty())
		{
			Object a=dao.loadEntity(SysModule.class,(Serializable)queue.peek());
			list.add(a);
			DetachedCriteria criteria=DetachedCriteria.forClass(SysModule.class);
			criteria.add(Restrictions.eq("parentId",queue.peek()));
			MyQuery mq=new MyQueryImpl();
			mq.setDetachedCriteria(criteria);
			Object[] re=dao.findEntity(mq);
			
			for(int i=0,size=re.length;i<size;i++)
			{
				SysModule sd=(SysModule)re[i];
				queue.add(sd.getId());
			}
			queue.poll();
		}
		return list;
	}
}
