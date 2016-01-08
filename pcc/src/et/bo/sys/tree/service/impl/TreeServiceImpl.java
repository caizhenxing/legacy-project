package et.bo.sys.tree.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ocelot.common.classtree.ClassTreeService;
import ocelot.common.key.KeyService;
import ocelot.common.tree.TreeControlI;
import ocelot.framework.base.dao.BaseDAO;
import ocelot.framework.base.dto.IBaseDTO;
import ocelot.framework.base.dto.impl.DynaBeanDTO;
import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.sys.tree.service.TreeService;
import et.po.SysTree;



public class TreeServiceImpl implements TreeService {

	private ClassTreeService cts=null;
	private BaseDAO dao=null;
	
	private KeyService ks = null;
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

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public TreeControlI loadTrees() {
		// TODO Auto-generated method stub
		return cts.getTree(cts.ROOT,true);
	}

	public void addTree(IBaseDTO dto) {
		// TODO Auto-generated method stub
		SysTree st=new SysTree();
		st.setHandleNode("idu");
		st.setProcAlias((String)dto.get("procAlias"));
		st.setTagDel("0");
		st.setTagSys((String)dto.get("tagSys"));
		st.setType((String)dto.get("domian"));
		st.setLabel((String)dto.get("name"));
		st.setParentId((String)dto.get("parentId"));
		st.setRemark((String)dto.get("remark"));
		st.setTagShow((String)dto.get("tagShow"));
		st.setId(ks.getNext("sys_tree"));
		dao.saveEntity(st);
		cts.reload();
	}

	public void removeTree(String id) {
		// TODO Auto-generated method stub
		List list=recursionFind(id);
		Iterator i=list.iterator();
		while(i.hasNext())
		{
			SysTree st=(SysTree)i.next();
			st.setTagDel("1");
			dao.removeEntity(st);
		}
		cts.reload();
	}

	public void updateTree(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String id=(String)dto.get("id");
		SysTree st=(SysTree)dao.loadEntity(SysTree.class,id);
		if(st!=null)
		{
			
			//st.setHandleNode((String)dto.get("handleNode"));
			st.setProcAlias((String)dto.get("procAlias"));
			
			st.setTagSys((String)dto.get("tagSys"));
			st.setType((String)dto.get("domian"));
			st.setLabel((String)dto.get("name"));
			st.setParentId((String)dto.get("parentId"));
			st.setRemark((String)dto.get("remark"));
			st.setTagShow((String)dto.get("tagShow"));
			
		}
		cts.reload();
		dao.saveEntity(st);
	}

	public IBaseDTO getTree(String id) {
		// TODO Auto-generated method stub
		IBaseDTO  dto=new DynaBeanDTO();
		SysTree st=(SysTree)dao.loadEntity(SysTree.class,id);
		
		dto.set("id",st.getId());
		dto.set("name",st.getLabel());
		dto.set("parentId",st.getParentId());
		dto.set("remark",st.getRemark());
		dto.set("tagShow",st.getTagShow());
		//dto.set("handleNode",st.getHandleNode());
		dto.set("procAlias",st.getProcAlias());
		dto.set("tagSys",st.getTagSys());
		dto.set("domain",st.getType());
		
		return dto;
	}
	private List recursionFind(Object id)
	{
		List list=new ArrayList();
		Queue queue=new LinkedList();
		queue.add(id);
		while(!queue.isEmpty())
		{
			Object a=dao.loadEntity(SysTree.class,(Serializable)queue.peek());
			list.add(a);
			DetachedCriteria criteria=DetachedCriteria.forClass(SysTree.class);
			criteria.add(Restrictions.eq("parentId",queue.peek()));
			MyQuery mq=new MyQueryImpl();
			mq.setDetachedCriteria(criteria);
			Object[] re=dao.findEntity(mq);
			
			for(int i=0,size=re.length;i<size;i++)
			{
				SysTree st=(SysTree)re[i];
				queue.add(st.getId());
			}
			queue.poll();
		}
		return list;
	}
}
