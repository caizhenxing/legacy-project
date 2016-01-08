/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：common
 * 制作时间：Dec 6, 200711:20:15 AM
 * 包名：com.zyf.autokey.service.impl
 * 文件名：AutoKeyServiceImpl.java
 * 制作者：zhaoyf
 * @version 1.0
 */
package com.zyf.autokey.service.impl;

import java.text.DecimalFormat;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.autokey.domain.CommonKey;
import com.zyf.autokey.service.AutoKeyService;

/**
 * 
 * @author zhaoyf
 * @version 1.0
 */
public class AutoKeyServiceImpl implements AutoKeyService {

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
	 * @see com.zyf.autokey.service.AutoKeyService#autoSn(java.lang.String)
	 */
	public String autoSn(String key) {
		// TODO Auto-generated method stub
		return fautoSn(key,"#");
	}

	/* (non-Javadoc)
	 * @see com.zyf.autokey.service.AutoKeyService#rollbackSn(java.lang.String)
	 */
	public String rollbackSn(String key) {
		// TODO Auto-generated method stub
		CommonKey ck=(CommonKey)hibernateTemplate.get(CommonKey.class,key);
		if(ck==null)
		{
			return null;
		}
		ck.decrease();
		return new DecimalFormat(ck.getCodeStr()).format(ck.getCodeNum());
	}

	/* (non-Javadoc)
	 * @see com.zyf.autokey.service.AutoKeyService#setSn(java.lang.String, int)
	 */
	public String setSn(String key, int sn) {
		// TODO Auto-generated method stub
		if(sn==0)
			sn=1;
		CommonKey ck=(CommonKey)hibernateTemplate.get(CommonKey.class,key);
		if(ck==null)
		{
			return null;
		}
		ck.setCodeNum(new Long(sn));
		return new DecimalFormat(ck.getCodeStr()).format(ck.getCodeNum());
	}

	public String fautoSn(String key, String style) {
		// TODO Auto-generated method stub
		CommonKey ck=(CommonKey)hibernateTemplate.get(CommonKey.class,key);
		if(ck==null)
		{
			ck=new CommonKey();
			ck.setId(key);
			ck.setCodeNum(new Long(1));
			ck.setCodeStr(style);
		}
		if(!"#".equals(style))
		ck.setCodeStr(style);
		String sn=new DecimalFormat(ck.getCodeStr()).format(ck.getCodeNum());
		ck.autoGain();
		
		return sn;
	}

}
