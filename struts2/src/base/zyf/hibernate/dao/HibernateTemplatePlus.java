/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 5, 200910:10:09 AM
 * ������base.zyf.hibernate.dao
 * �ļ�����HibernateTemplatePlus.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import base.zyf.web.condition.Condition;
import base.zyf.web.condition.ConditionConstants;
import base.zyf.web.condition.ConditionInfo;
import base.zyf.web.condition.ContextInfo;
import base.zyf.web.page.PageInfo;

/**
 * ��ǿ�� HibernateTemplate, ���� {@link HibernateTemplate} ��һЩ������ʵ��һЩ��ǿ����,
 * Ŀǰ�Ѿ�ʵ�ֵĹ����� : 
 * <ul>
 * <li>���� findByCriteria ����ʵ���Զ���ҳ, �Զ���ѯ</li>
 * </ul>
 * Ŀǰ�� Hibernate 3.0.5, 3.1.2, 3.1.3 �в���ͨ��, ����֤�����汾����, ��Ҫʵ���Զ���ѯ��ҳ���ܵ�
 * ҵ�������ʹ�� {@link #findByCriteria(DetachedCriteria)} ���в�ѯ
 * @see Condition
 * @see Conditions
 * @author zhaoyifei
 * @version 1.0
 */
public class HibernateTemplatePlus extends HibernateTemplate implements ConditionConstants {
	private static final String CRITERIA_ASSERT_ERROR_MESSAGE = " 's type is not " + CriteriaImpl.class + ", please make sure you are using Hibernate3.0.5!!! ";
	/**
	 * 
	 * TODO
	 * @version 1.0
	 */
	public HibernateTemplatePlus() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param sessionFactory
	 * TODO
	 * @version 1.0
	 */
	public HibernateTemplatePlus(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param sessionFactory
	 * @param allowCreate
	 * TODO
	 * @version 1.0
	 */
	public HibernateTemplatePlus(SessionFactory sessionFactory,
			boolean allowCreate) {
		super(sessionFactory, allowCreate);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException {
		addAliasFromConditionInfo(criteria);
		EnhancedCriteriaCallback callback = new EnhancedCriteriaCallback(criteria);
		
		return super.executeFind(callback);
	}
	
	/**
	 * ���������ѯ������װ
	 * �˹���ֻӰ��a.b.fieldname(2��)�������ε����������ܴ����ϲ�ѯ
	 * ʹ��������
	 * <code>
	 * 		ContextInfo.recoverQuery();
	 *		DetachedCriteria dc=DetachedCriteria.forClass(TblHrEmpTraReg.class)
	 *	    .add(Restrictions.eq("delFlg", "0"));
	 *		AutoConditionsUtil.AddAliasFromConditionInfo(dc);
	 *		return this.hibernateTemplate.findByCriteria(dc);
	 * </code>
	 */
	private DetachedCriteria addAliasFromConditionInfo(DetachedCriteria dc)
	{
		if(ContextInfo.isConcealQuery())
			return null;
		if(null == dc) 
			return null;
		Condition[] cs = ContextInfo.getContextCondition().getAppendConditions();
		if(cs.length == 0) return dc;
		
		Set as = new HashSet(); 
		Set oas = new HashSet(); 
		List cslist = new ArrayList();
		for(int i=0;i<cs.length;i++)
		{
			Condition con = cs[i];
			if(null == con ) continue;
			
			String name = con.getName();
			if(StringUtils.isBlank(name))
				continue;
			Object value = con.getValue();
			if(value == null)
				continue;
			if (String.class.isInstance(value)) {
				if(StringUtils.isBlank((String)value))
					continue;
			}
			if (String[].class.isInstance(value)) {
				value = ((String[]) value)[0].trim();
				if(StringUtils.isBlank((String)value))
					continue;
			}
			String operator=con.getOperator();
			if (operator == null) {
				operator = EQUAL;
			}
			if (LIKE.equals(operator)) {
				value = "%" + value + "%";
			}
			operator = " " + operator + " ";
			
			String propertyName = name;
			/**
			 * �����������Ҫ����������A.B.code��A.B
			 * A.code��A�����������Ҫ��������
			 */
			if(name.indexOf(PROPERTY_SEPARATOR)!=-1 && (name.indexOf(PROPERTY_SEPARATOR) != name.indexOf(".code")))
			{
				
				int lastindex = name.lastIndexOf(PROPERTY_SEPARATOR);
				if(name.indexOf(".code") == name.length()-5)
					lastindex=name.substring(0,lastindex).lastIndexOf(PROPERTY_SEPARATOR);
				String aliasName = createAliase(dc,as,oas,name.substring(0,lastindex));
				propertyName = aliasName + name.substring(lastindex);
				
				
			}
			//�����ѯ����
			try {	
				Constructor constructor = SimpleExpression.class.getDeclaredConstructor(new Class[] {String.class, Object.class, String.class});
				constructor.setAccessible(true);
				SimpleExpression expression = 
					(SimpleExpression) constructor.newInstance(
							new Object[] {propertyName, value, operator}
							);					
				dc.add(expression);
				
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("��̬�����ѯ�������� : " + e);
				}
				throw new RuntimeException("��̬�����ѯ��������", e);
			}
		}
//		Condition[] cs2 = new Condition[cslist.size()];
//		for(int i=0;i<cs2.length;i++)
//		{
//			cs2[i] = (Condition)cslist.get(i);
//		}
//		ContextInfo.getContextCondition().setAppendConditions(cs2);
		return null;
	}
	/**
	 * �ݹ鷽ʽ�ֲ㴴��alias,�Ѵ������Ĳ��ٴ���
	 * @param dc
	 * @param as
	 * @param path
	 * @return alias����
	 */
	private static String createAliase(DetachedCriteria dc,Set as ,Set oas ,String path)
	{
		int lastpos = path.lastIndexOf(PROPERTY_SEPARATOR);
		String aliasName = null;
		if(lastpos > -1)
		{
			aliasName = path.substring(lastpos+1);
			if(as.contains(aliasName)||oas.contains(aliasName))
				return aliasName;
			createAliase(dc,as,oas,path.substring(0, lastpos));
			dc.createAlias(path, aliasName);
		}
		else
		{
			aliasName = path;
			if(as.contains(path))
				return path;
			dc.createAlias(path,aliasName);
		}
		as.add(aliasName);
		return aliasName;
	}
	
	private void rebuildCriterial(DetachedCriteria criteria) {
		ConditionInfo info = ContextInfo.getContextCondition();
		if (info == null) {
			return;
		}
		
		Condition[] globalConditions = ContextInfo.getContextCondition().getOriginalConditions();
		Condition[] appendConditions = ContextInfo.getContextCondition().getAppendConditions();
		
		// ���� order ����
		Arrays.sort(appendConditions, new Comparator() {
			public int compare(Object o1, Object o2) {
				Condition condition1 = (Condition) o1;
				Condition condition2 = (Condition) o2;				
				return condition1.getOrder() - condition2.getOrder();
			}
		});
		
		// ��¼�����ѯ�����е����� alias
		List aliasList = new ArrayList();
		
		appendConditions(criteria, globalConditions, aliasList);
		appendConditions(criteria, appendConditions, aliasList);	
	}

	private void appendConditions(DetachedCriteria criteria, Condition[] conditions, List aliasList) {
		List criterions = new ArrayList();
		List prepends = new ArrayList();
		for (int i = 0; i < conditions.length; i++) {
			Condition condition = conditions[i];
			if (!condition.isPlace()) {
				continue;
			}
			
			if (StringUtils.isNotBlank(condition.getName())) {
				Criterion criterion = getSingleCondition(criteria, condition, aliasList);
				if(criterion != null)
				{
				criterions.add(criterion);
				prepends.add(condition.getPrepend());
				}
			} else {
				// �����ϲ�ѯ
				if (condition.getCompositeConditions().length > 0) {				
					appendConditions(criteria, condition.getCompositeConditions(), aliasList);
				} else {
					throw new RuntimeException("��ѯ�������ô���, �������Ժ͸��������������һ��");
				}
			} 
		}
		
		if (!CollectionUtils.isEmpty(criterions)) {
			Criterion criterion = new MultiCriterionsExpression((Criterion[]) criterions.toArray(new Criterion[0]), (String[]) prepends.toArray(new String[0]));
			criteria.add(criterion);
		}
	}
	
	private Criterion getSingleCondition(DetachedCriteria criteria, Condition condition, List aliasList) {
		String name = condition.getName();
		String operator = condition.getOperator();
		if (operator == null) {
			operator = EQUAL;
		}
		Object value = condition.getValue();
		// ��ȥ���˵Ŀո�
		if (String.class.isInstance(value)) {
			value = ((String) value).trim();
			if(StringUtils.isBlank((String)value))
				return null;
		}
		if (String[].class.isInstance(value)) {
			value = ((String[]) value)[0].trim();
			if(StringUtils.isBlank((String)value))
				return null;
		}
		if (LIKE.equals(operator)) {
			value = "%" + value + "%";
		}
		operator = " " + operator + " ";
		
		// ���������ѯ
		String lastAlias = null;		
		if (name.indexOf(PROPERTY_SEPARATOR) > -1 && condition.isCreateAlias()) {
			StringTokenizer tokenizer = new StringTokenizer(name, PROPERTY_SEPARATOR, false);
			StringBuffer createdAlias = new StringBuffer();
			while (tokenizer.hasMoreTokens()) {
				String alias = tokenizer.nextToken();
				if (tokenizer.hasMoreTokens()) {
					lastAlias = alias;
					createdAlias.append(alias);
					String associationPath = createdAlias.toString();
					createdAlias.append(PROPERTY_SEPARATOR);
					
					if (!aliasList.contains(associationPath)) {
						criteria.createAlias(associationPath, alias);
						aliasList.add(associationPath);
					}
				}
			}			
		}
		
		String propertyName = name;
		if (lastAlias != null) {
			propertyName = lastAlias + name.substring(name.lastIndexOf(PROPERTY_SEPARATOR));
		}
				
		try {	
			Constructor constructor = SimpleExpression.class.getDeclaredConstructor(new Class[] {String.class, Object.class, String.class});
			constructor.setAccessible(true);
			SimpleExpression expression = (SimpleExpression) constructor.newInstance(new Object[] {propertyName, value, operator});					
			return expression;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("��̬�����ѯ�������� : " + e);
			}
			throw new RuntimeException("��̬�����ѯ��������", e);
		} 	
	} 
	
	public static void main(String[] args) {
		String string = "a.b.c.d";
		StringTokenizer tokenizer = new StringTokenizer(string, ".", false);
		while (tokenizer.hasMoreTokens()) {
			System.out.println("Next Token -> " + tokenizer.nextToken());
		}
	}
	
	
	/**
     * �� criteria ��ȡ�� {@link Projection}, �ӿ���û�й����˷���, ��˴� {@link CriteriaImpl} ��ȡ��
     * @see CriteriaImpl#getProjection()
     * @param criteria the criteria
     * @return the Projection
     */
	private Projection getProjection(Criteria criteria) {
    	assertType(criteria);
    	CriteriaImpl impl = (CriteriaImpl) criteria;
    	return impl.getProjection();
    }
   
	private void assertType(Criteria criteria) {
		Assert.notNull(criteria, " criteria is required. ");
		String message = criteria + CRITERIA_ASSERT_ERROR_MESSAGE;
		if (!CriteriaImpl.class.isInstance(criteria)) {
    		if (logger.isDebugEnabled()) {
    			logger.debug(message);
    		}
    		throw new RuntimeException(message);
    	}
	}
    
	/**
	 * �õ� criteria �е� OrderEntry[]
	 * @param criteria the criteria
	 * @return the OrderEntry[]
	 */
	private OrderEntry[] getOrders(Criteria criteria) {
    	assertType(criteria);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Field field = getOrderEntriesField(criteria);
		try {
			return (OrderEntry[]) ((List) field.get(impl)).toArray(new OrderEntry[0]);
		} catch (Exception e) {
    		logAndThrowException(criteria, e);
    		throw new InternalError(" Runtime Exception impossibility can't throw ");
		} 
    }
    
	/**
	 * �Ƴ� criteria �е� OrderEntry[]
	 * @param criteria the criteria
	 * @return the criteria after removed OrderEntry[]
	 */
	private Criteria removeOrders(Criteria criteria) {
    	assertType(criteria);
    	CriteriaImpl impl = (CriteriaImpl) criteria;
    	
    	try {
        	Field field = getOrderEntriesField(criteria);
        	field.set(impl, new ArrayList());
        	return impl;
    	} catch (Exception e) {
    		logAndThrowException(criteria, e);
    		throw new InternalError(" Runtime Exception impossibility can't throw ");
    	}	
    }

    /**
     * Ϊ criteria ���� OrderEntry[]
     * @param criteria the criteria
     * @param orderEntries the OrderEntry[]
     * @return the criteria after add OrderEntry[]
     */
	private Criteria addOrders(Criteria criteria, OrderEntry[] orderEntries) {
    	assertType(criteria);
    	CriteriaImpl impl = (CriteriaImpl) criteria;
    	try {
        	Field field = getOrderEntriesField(criteria);
        	for (int i = 0; i < orderEntries.length; i++) {
        		List innerOrderEntries = (List) field.get(criteria);
        		innerOrderEntries.add(orderEntries[i]);
        	}
        	return impl;
    	} catch (Exception e) {
    		logAndThrowException(criteria, e);
    		throw new InternalError(" Runtime Exception impossibility can't throw ");
    	}
    }

	private void logAndThrowException(Criteria criteria, Exception e) {
		String message = criteria + CRITERIA_ASSERT_ERROR_MESSAGE;
		if (logger.isDebugEnabled()) {
			logger.debug(message, e);
		}
		throw new RuntimeException(message, e);
	}
    
	private Field getOrderEntriesField(Criteria criteria) {
		Assert.notNull(criteria, " criteria is requried. " );
		try {
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			logAndThrowException(criteria, e);
    		throw new InternalError();
		}
	}
	
	/**
	 * inner class , core implments of enchanced criteria
	 *
	 */
	private class EnhancedCriteriaCallback implements HibernateCallback {
		
		private DetachedCriteria criteria;

		public EnhancedCriteriaCallback(DetachedCriteria criteria) {
			this.criteria = criteria;
		}

		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			PageInfo pageInfo = null;
			int firstResult = 0;
			int maxResults = 0;
			
			// ��� ContextInfo �еĲ�ѯ����
			if (!ContextInfo.isConcealQuery()) {
				//rebuildCriterial(criteria);			
			
				if (ContextInfo.getContextCondition() != null) {
					pageInfo = ContextInfo.getContextCondition().getPageInfo();
				}
				
				
				// ��ѯ���������ε�ǰ����
		        ContextInfo.concealQuery();
			}
			//����һ����ִ�е�Criteria
			Criteria executableCriteria = criteria.getExecutableCriteria(session);				
			prepareCriteria(executableCriteria);
			
			// �����ҳ��ѯ
			if (pageInfo != null) {
				// Get the orginal orderEntries
				OrderEntry[] orderEntries = getOrders(executableCriteria);
				// Remove the orders
				executableCriteria = removeOrders(executableCriteria);				
				// get the original projection
				Projection projection = getProjection(executableCriteria);
				
				Integer iCount = (Integer) executableCriteria.setProjection(Projections.rowCount()).uniqueResult();
                if (iCount == null) {
                	throw new RuntimeException("�޷�ִ�� count ͳ��, �ܿ����� [ " + criteria.getClass() + " ] û����Ӧ�� hbm �����ļ�");
                }				
				int totalCount = iCount == null ? 0 : iCount.intValue();
				pageInfo.setRowCount(totalCount);
                
                executableCriteria.setProjection(projection);
                if (projection == null) {
                	// Set the ResultTransformer to get the same object structure with hql
                	executableCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
                }              
                // Add the orginal orderEntries
                executableCriteria = addOrders(executableCriteria, orderEntries);
                // ׷��������Ŀid(�������������,��ҳ�����޷�������ʾ)
                if(orderEntries.length == 0)
                {
                Order order = Order.asc("id");//new Order("id", true);
                executableCriteria.addOrder(order);
                }
                firstResult = pageInfo.getBegin();
				maxResults = pageInfo.getPageSize(); 
				if (firstResult >= 0) {
                	executableCriteria.setFirstResult(firstResult);
                }
                if (maxResults > 0) {
                	executableCriteria.setMaxResults(maxResults);
                }
			}
			
            return executableCriteria.list();
		}
	}
}
