/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-9-6
 * 文件名：CommonBasicCodeInitImpl.java
 * 制作者：李鹤
 * 
 */
package com.zyf.common.basiccode.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.common.basiccode.service.CommonBasicCodeInitService;
import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.core.ContextInfo;

public class CommonBasicCodeInitImpl implements CommonBasicCodeInitService {

	private HibernateTemplate ht;

	public HibernateTemplate getHt() {
		return ht;
	}

	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}

	public List load(String id) {
		ContextInfo.recoverQuery();
		DetachedCriteria d = DetachedCriteria.forClass(CommonBasicCode.class);
		d.add(Restrictions.eq("subid", id));
		List l = ht.findByCriteria(d);
		return l;
	}

	public String reset(String subID) {
		String rsInfo = "";
		CommonBasicCode bc = null;
		ContextInfo.recoverQuery();
		DetachedCriteria d = DetachedCriteria.forClass(CommonBasicCode.class);
		d.add(Restrictions.eq("subid", subID));
		List l = ht.findByCriteria(d);
		if (l.size() == 0) {
			rsInfo = "记录不存在，设置完毕";
		} else {
			for (int i = 0; i < l.size(); i++) {
				bc = new CommonBasicCode();
				bc = (CommonBasicCode) l.get(i);
				ht.delete(bc);
			}
			rsInfo = "设置成功";
		}
		return rsInfo;
	}

	public List find(String id) {
		ContextInfo.recoverQuery();
		DetachedCriteria d = DetachedCriteria.forClass(CommonBasicCode.class);
		d.add(Restrictions.eq("id", id));
		List l = ht.findByCriteria(d);
		return l;
	}

}
