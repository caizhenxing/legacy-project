/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 5, 20075:13:52 PM
 * 文件名：EnhancehibernateTemplatePlus.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.hibernate3;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import com.zyf.persistent.hibernate3.EnhancedHibernateTemplate;

/**
 * @author zhaoyf
 *
 */
public class EnhancehibernateTemplatePlus extends EnhancedHibernateTemplate {

	public List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException {
		// TODO Auto-generated method stub
		AutoConditionsUtil.AddAliasFromConditionInfo(criteria);
		return super.findByCriteria(criteria);
	}
//	private void create(Object entity)
//	{
//		if(entity instanceof EntityPlus)
//		{
//		EntityPlus ep=(EntityPlus)entity;
//		if(ep.getId()!=null)
//		{
//			this.modify(entity);
//			return;
//		}
//		ep.setCreatedTime(Tools.getNowTime());
//		ep.setCreator(ContextInfo.getContextUser());
//		ep.setCreatorDept(new DepartmentCodeName().copyFrom(ContextInfo.getContextUser().getDepartment()));
//		}
//	}
//	private void modify(Object entity)
//	{
//		if(entity instanceof EntityPlus)
//		{
//		EntityPlus ep=(EntityPlus)entity;
//		ep.setModifiedTime(Tools.getNowTime());
//		ep.setModifier(ContextInfo.getContextUser());
//		ep.setModifierDept(new DepartmentCodeName().copyFrom(ContextInfo.getContextUser().getDepartment()));
//		}
//	}
//	private void createOrModify(Object entity)
//	{
//		if(this.contains(entity))
//		{
//			this.modify(entity);
//			return;
//		}
//		this.create(entity);
//	}
//	public void save(Object entity, Serializable id) throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.create(entity);
//		super.save(entity, id);
//	}
//
//	public Serializable save(Object entity) throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.create(entity);
//		return super.save(entity);
//	}
//
//	public void save(String entityName, Object entity, Serializable id)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.create(entity);
//		super.save(entityName, entity, id);
//	}
//
//	public Serializable save(String entityName, Object entity)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.create(entity);
//		return super.save(entityName, entity);
//	}
//
//	public void saveOrUpdate(Object entity) throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.createOrModify(entity);
//		super.saveOrUpdate(entity);
//	}
//
//	public void saveOrUpdate(String entityName, Object entity)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.createOrModify(entity);
//		super.saveOrUpdate(entityName, entity);
//	}
//
//
//	public void update(Object entity, LockMode lockMode)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.modify(entity);
//		super.update(entity, lockMode);
//	}
//
//	public void update(Object entity) throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.modify(entity);
//		super.update(entity);
//	}
//
//	public void update(String entityName, Object entity, LockMode lockMode)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.modify(entity);
//		super.update(entityName, entity, lockMode);
//	}
//
//	public void update(String entityName, Object entity)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		this.modify(entity);
//		super.update(entityName, entity);
//	}

}
