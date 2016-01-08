package et.bo.sys.department.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.sys.department.service.DepartmentService;
import et.po.SysDepartment;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;




public class DepartmentServiceImpl implements DepartmentService {

	private ClassTreeService cts=null;
	private BaseDAO dao=null;
	
	private KeyService ks = null;
	public TreeControlI loadDepartments() {
		// TODO Auto-generated method stub
		return cts.getTree(cts.ROOT,true);
	}

	public void addDepartment(IBaseDTO dto) {
		// TODO Auto-generated method stub
		SysDepartment sd=new SysDepartment();
		
		sd.setName((String)dto.get("name"));
		sd.setParentId((String)dto.get("parentId"));
		sd.setRemarks((String)dto.get("remark"));
		sd.setTagShow((String)dto.get("tagShow"));
		sd.setId(ks.getNext("sys_department"));
		
		dao.saveEntity(sd);
		cts.reload();
	}

	public void removeDepartment(String id) {
		// TODO Auto-generated method stub
		SysDepartment sd=(SysDepartment)dao.loadEntity(SysDepartment.class,id);
		if(sd!=null)
		{
			if(null!=sd.getIsSys()&&"1".equals(sd.getIsSys()))
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

	public void updateDepartment(IBaseDTO dto) {
		// TODO Auto-generated method stub
		String id=(String)dto.get("id");
		SysDepartment sd=(SysDepartment)dao.loadEntity(SysDepartment.class,id);
		if(sd!=null)
		{
			
			sd.setName((String)dto.get("name"));
			sd.setParentId((String)dto.get("parentId"));
			sd.setRemarks((String)dto.get("remark"));
			sd.setTagShow((String)dto.get("tagShow"));
			sd.setAdmin((String)dto.get("admin"));
		}
		cts.reload();
		dao.saveEntity(sd);
	}

	public IBaseDTO getDepartment(String id) {
		// TODO Auto-generated method stub
		IBaseDTO  dto=new DynaBeanDTO();
		SysDepartment sd=(SysDepartment)dao.loadEntity(SysDepartment.class,id);
		
		dto.set("id",sd.getId());
		dto.set("name",sd.getName());
		dto.set("parentId",sd.getParentId());
		dto.set("remark",sd.getRemarks());
		dto.set("tagShow",sd.getTagShow());
		dto.set("admin",sd.getAdmin());
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

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	private List recursionFind(Object id)
	{
		List list=new ArrayList();
		Queue queue=new LinkedList();
		queue.add(id);
		while(!queue.isEmpty())
		{
			Object a=dao.loadEntity(SysDepartment.class,(Serializable)queue.peek());
			list.add(a);
			DetachedCriteria criteria=DetachedCriteria.forClass(SysDepartment.class);
			criteria.add(Restrictions.eq("parentId",queue.peek()));
			MyQuery mq=new MyQueryImpl();
			mq.setDetachedCriteria(criteria);
			Object[] re=dao.findEntity(mq);
			
			for(int i=0,size=re.length;i<size;i++)
			{
				SysDepartment sd=(SysDepartment)re[i];
				queue.add(sd.getId());
			}
			queue.poll();
		}
		return list;
	}

	public List<LabelValueBean> getUserListByDep(String id) {
		// TODO Auto-generated method stub
		SysDepartment sd=(SysDepartment)dao.loadEntity(SysDepartment.class,id);
		if(sd==null)
			return null;
		List<LabelValueBean> userList=new ArrayList<LabelValueBean>();
		Iterator<SysUser> i=sd.getSysUsers().iterator();
		while(i.hasNext())
		{
			LabelValueBean lvb=new LabelValueBean();
			SysUser su=i.next();
			lvb.setLabel(su.getUserName());
			lvb.setValue(su.getUserId());
			userList.add(lvb);
		}
		return userList;
	}
	
	
	public MyQuery adminDep(String user)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(SysDepartment.class);
		dc.add(Restrictions.eq("admin",user));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	
	public MyQuery subDep(String dep)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(SysDepartment.class);
		dc.add(Restrictions.eq("parentId",dep));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public List<String> getDepartments(String user) {
		// TODO Auto-generated method stub
		Object[] re=dao.findEntity(adminDep(user));
		if(re.length==0)
			return null;
		List<String> l=new ArrayList<String>(re.length);
		for(int i=0,size=re.length;i<size;i++)
		{
			String id=((SysDepartment)re[i]).getId();
			l.add(id);
		}
		return l;
	}

	public List<LabelValueBean> getDepListByDep(String id) {
		// TODO Auto-generated method stub
		SysDepartment sd=(SysDepartment)dao.loadEntity(SysDepartment.class,id);
		if(sd==null)
			return null;
		List<LabelValueBean> depList=new ArrayList<LabelValueBean>();
		LabelValueBean lvb1=new LabelValueBean();
		lvb1.setLabel("--"+sd.getName());
		lvb1.setValue(sd.getId());
		depList.add(lvb1);
		Object[] re=dao.findEntity(subDep(id));
		for(int i=0,size=re.length;i<size;i++)
		{
			SysDepartment s=(SysDepartment)re[i];
			LabelValueBean lvb=new LabelValueBean();
			lvb.setLabel("--"+s.getName());
			lvb.setValue(s.getId());
			depList.add(lvb);
		}
		return depList;
		
	}

	public TreeControlI getDepartments() {
		// TODO Auto-generated method stub
		return cts.getTree(cts.ROOT,true,"dep.do?method=select&id=","select");
	}

	
}
