/**
 * 
 * 制作时间：2007-8-15下午07:47:07
 * 工程名：comoon
 * 文件名：com.zyf.common.dict.serviceBasicCode.java
 * 制作者：zhaoyifei
 * 
 */
package com.zyf.common.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.common.dict.service.BasicCodeService;
import com.zyf.common.dict.service.CommonBasicCodeService;
import com.zyf.core.ContextInfo;
import com.zyf.struts.BaseFormPlus;
import com.zyf.tools.Tools;
import com.zyf.web.view.ComboSupportList;

/**
 * @author zhaoyifei
 *
 */
public class CommonBasicCodeImpl implements BasicCodeService ,CommonBasicCodeService{

	private HibernateTemplate ht;
	private CommonBasicCode root;
	
	private static Map codes;
	
	
	private void loadCodes()
	{
		codes=new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class);
		dc.add(Restrictions.isNotNull("code"));
		List l= this.ht.findByCriteria(dc);
		Map m=new HashMap();
		Iterator i=l.iterator();
		while(i.hasNext())
		{
			CommonBasicCode hbc=(CommonBasicCode)i.next();
			codes.put(hbc.getCode(),hbc.getName());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.zyf.common.dict.service.DataDictService#getBasicCodeList(java.lang.String, java.lang.String)
	 */
	public Map getBasicCodeList(String sid, String bid) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class);
		dc.add(Restrictions.eq("subid",sid));
		dc.add(Restrictions.eq("typeCode",bid));
		dc.addOrder(Order.asc("showSequence"));
		List l= this.ht.findByCriteria(dc);
		Map m=new HashMap();
		Iterator i=l.iterator();
		while(i.hasNext())
		{
			CommonBasicCode hbc=(CommonBasicCode)i.next();
			m.put(hbc.getCode(),hbc.getName());
		}
		return m;
	}
	public String getBasicCodeName(String sid, String bid, String id) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class);
		dc.add(Restrictions.eq("subid",sid));
		dc.add(Restrictions.eq("typeCode",bid));
		dc.add(Restrictions.eq("code",id));
		List l= this.ht.findByCriteria(dc);
		if(l.size()!=0)
		{
			CommonBasicCode hbc=(CommonBasicCode)l.get(0);
			return hbc.getName();
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.dict.service.DataDictService#getBasicCodeTree(java.lang.String)
	 */
	public String getBasicCodeTree(String sid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.dict.service.DataDictService#getDictListByRoot(java.lang.String)
	 */
	public Map getDictListByRoot(String root) {
		// TODO Auto-generated method stub
		CommonBasicCode parent = load(root);
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class);
		dc.add(Restrictions.eq("parent", parent));
		dc.addOrder(Order.asc("showSequence"));
		List l= this.ht.findByCriteria(dc);
		Map m=new HashMap();
		Iterator i=l.iterator();
		while(i.hasNext())
		{
			CommonBasicCode hbc=(CommonBasicCode)i.next();
			m.put(hbc.getCode(),hbc.getName());
		}
		return m;
	}

	public HibernateTemplate getHt() {
		return ht;
	}

	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}

	public void delete(CommonBasicCode config) {
		// TODO Auto-generated method stub
		BaseFormPlus.getCodes(config.getSubid()).put(config.getTypeCode(), null);
		this.ht.delete(config);
	}

	public List list(CommonBasicCode config) {
		// TODO Auto-generated method stub
		ContextInfo.recoverQuery();
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class)
		.add(Restrictions.eq("parent", config))
		.addOrder(Order.asc("showSequence"));
	return this.ht.findByCriteria(dc);
	}

	public CommonBasicCode load(String id) {
		// TODO Auto-generated method stub
		return (CommonBasicCode) this.ht.load(CommonBasicCode.class, id);
	}
	public CommonBasicCode get(String id) {
		// TODO Auto-generated method stub
		return (CommonBasicCode) this.ht.get(CommonBasicCode.class, id);
	}
	public CommonBasicCode newInstance(String parentId) {
		// TODO Auto-generated method stub
		if("root".equals(parentId))
			return null;
		CommonBasicCode parent = get(parentId);
		if(parent==null)
			return null;
		if(parent.getLayerNum().intValue()<2)
			return null;
		if("2".equals(parent.getDeleteState()))
			return null;
		if(new Integer(3).equals(parent.getLayerNum()))
			parent=parent.getParent();
		CommonBasicCode r = new CommonBasicCode();
		r.setId(Tools.getPKCode());
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class)
			.add(Restrictions.eq("parent", parent))
			.setProjection(Projections.max("showSequence"));
		List list = this.ht.findByCriteria(dc);
		int i=1;
		if (list.size() > 0 && list.get(0) != null) {
			i = ((Integer)list.get(0)).intValue();
			i++;
		}
		r.setParent(parent);
		r.setSubid(r.getParent().getSubid());
		r.setTypeCode(r.getParent().getTypeCode());
		r.setShowSequence(new Integer(i));
		return r;
	}

	public void saveOrUpdate(CommonBasicCode config) {
		// TODO Auto-generated method stub
		this.ht.saveOrUpdate(config);
		BaseFormPlus.getCodes(config.getSubid()).put(config.getTypeCode(), null);
		loadCodes();
	}

	public CommonBasicCode getRoot() {
		return root;
	}

	public void setRoot(CommonBasicCode root) {
		this.root = root;
	}
	public ComboSupportList getComboList(String sid, String bid) {
		// TODO Auto-generated method stub
		
		List unitList = new ArrayList();
		DetachedCriteria dc = DetachedCriteria.forClass(CommonBasicCode.class);
		dc.add(Restrictions.eq("subid",sid));
		dc.add(Restrictions.eq("typeCode",bid));
		dc.add(Restrictions.isNotNull("code"));
		dc.addOrder(Order.asc("showSequence"));
		List l= this.ht.findByCriteria(dc);
		
		ComboSupportList supportList = new ComboSupportList("code", "name");
		supportList.addAll(l);		
		return supportList;
	}
	public String getNameByCode(String code) {
		// TODO Auto-generated method stub
		if(codes==null)
			loadCodes();
		if(!codes.containsKey(code))
		return null;
		return (String)codes.get(code);
	}

	
}
