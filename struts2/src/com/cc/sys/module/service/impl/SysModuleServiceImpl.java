/**
 * 
 * 项目名称：struts2
 * 制作时间：May 20, 200910:35:24 AM
 * 包名：com.cc.sys.module.service.impl
 * 文件名：SysModuleServiceImpl.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package com.cc.sys.module.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.module.ModuleLoadService;
import base.zyf.common.tree.module.ModuleTreeRight;

import com.cc.sys.db.SysGroup;
import com.cc.sys.db.SysModule;
import com.cc.sys.db.SysRightGroup;
import com.cc.sys.module.service.SysModuleService;

/**
 * 
 * @author 赵一非
 * @version 1.0
 */
public class SysModuleServiceImpl implements SysModuleService,
		ModuleLoadService {
	private HibernateTemplate hibernateTemplate;
	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/* (non-Javadoc)
	 * @see com.cc.sys.module.service.impl.SysModuleService#loadAll()
	 */
	public TreeNodeI loadAll() {
		TreeNodeI tni = null;
		Object o = hibernateTemplate.load(SysModule.class, TreeNodeI.TREE_ROOT);
		if(o != null)
		tni = (TreeNodeI)o;
		return tni;
	}

	/* (non-Javadoc)
	 * @see com.cc.sys.module.service.impl.SysModuleService#loadByUser(java.lang.String)
	 */
	public TreeNodeI loadByUser(String id) {
		// TODO Auto-generated method stub
		TreeNodeI tni = null;
		Object o = hibernateTemplate.load(SysModule.class, id);
		if(o != null)
		tni = (TreeNodeI)o;
		return tni;
	}

	/* (non-Javadoc)
	 * @see base.zyf.common.tree.module.ModuleLoadService#loadTree()
	 */
	public TreeNodeI loadTree() {
		// TODO Auto-generated method stub
		return this.loadAll();
	}

	public Map<String, ModuleTreeRight> loadTreeByUser(String userId) {
		// TODO Auto-generated method stub
		Map<String, ModuleTreeRight> m = new HashMap<String, ModuleTreeRight>();
		DetachedCriteria criteria=DetachedCriteria.forClass(SysModule.class);
		criteria.createAlias("sysRightGroups", "srg");
		criteria.createAlias("srg.sysGroup", "group");
		criteria.createAlias("group.sysUsers", "user");
		criteria.add(Restrictions.eq("user.userId", userId));
		List<SysModule> l = this.hibernateTemplate.findByCriteria(criteria);
		for(SysModule sm:l)
		{
			m.put(sm.getId(), new ModuleTreeRight(sm));
		}
		return m;
	}

	public void grantGroup(String id, String grantS) {
		SysGroup sg = (SysGroup)hibernateTemplate.load(SysGroup.class, id);
		hibernateTemplate.deleteAll(sg.getSysRightGroups());
		sg.getSysRightGroups().clear();
		String[] gs = grantS.split(";");
		List<SysRightGroup> l = new ArrayList<SysRightGroup>();
		for(int i = 0; i<gs.length; i++)
		{
			SysRightGroup srg =new SysRightGroup();
			srg.setGroupId(sg.getId());
			srg.setModId(gs[i]);
			l.add(srg);
			sg.getSysRightGroups().add(srg);
		}
		hibernateTemplate.saveOrUpdateAll(l);
		hibernateTemplate.flush();
	}


}
