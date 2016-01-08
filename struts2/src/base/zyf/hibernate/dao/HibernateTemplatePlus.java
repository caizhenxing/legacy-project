/**
 * 
 * 项目名称：struts2
 * 制作时间：May 5, 200910:10:09 AM
 * 包名：base.zyf.hibernate.dao
 * 文件名：HibernateTemplatePlus.java
 * 制作者：zhaoyifei
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
 * 增强的 HibernateTemplate, 重载 {@link HibernateTemplate} 的一些方法以实现一些增强功能,
 * 目前已经实现的功能有 : 
 * <ul>
 * <li>重载 findByCriteria 方法实现自动翻页, 自动查询</li>
 * </ul>
 * 目前在 Hibernate 3.0.5, 3.1.2, 3.1.3 中测试通过, 不保证其他版本可用, 需要实现自动查询分页功能的
 * 业务类必须使用 {@link #findByCriteria(DetachedCriteria)} 进行查询
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
	 * 处理关联查询条件组装
	 * 此过程只影响a.b.fieldname(2级)及更多层次的条件，不能处理复合查询
	 * 使用样例：
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
			 * 这两种情况需要建立别名：A.B.code，A.B
			 * A.code，A这种情况不需要建立别名
			 */
			if(name.indexOf(PROPERTY_SEPARATOR)!=-1 && (name.indexOf(PROPERTY_SEPARATOR) != name.indexOf(".code")))
			{
				
				int lastindex = name.lastIndexOf(PROPERTY_SEPARATOR);
				if(name.indexOf(".code") == name.length()-5)
					lastindex=name.substring(0,lastindex).lastIndexOf(PROPERTY_SEPARATOR);
				String aliasName = createAliase(dc,as,oas,name.substring(0,lastindex));
				propertyName = aliasName + name.substring(lastindex);
				
				
			}
			//构造查询条件
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
					logger.error("动态构造查询条件出错 : " + e);
				}
				throw new RuntimeException("动态构造查询条件出错", e);
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
	 * 递归方式分层创建alias,已创建过的不再创建
	 * @param dc
	 * @param as
	 * @param path
	 * @return alias名称
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
		
		// 根据 order 排序
		Arrays.sort(appendConditions, new Comparator() {
			public int compare(Object o1, Object o2) {
				Condition condition1 = (Condition) o1;
				Condition condition2 = (Condition) o2;				
				return condition1.getOrder() - condition2.getOrder();
			}
		});
		
		// 记录构造查询条件中的所有 alias
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
				// 处理复合查询
				if (condition.getCompositeConditions().length > 0) {				
					appendConditions(criteria, condition.getCompositeConditions(), aliasList);
				} else {
					throw new RuntimeException("查询条件配置错误, 基本属性和复合条件必须包含一项");
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
		// 截去两端的空格
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
		
		// 处理关联查询
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
				logger.error("动态构造查询条件出错 : " + e);
			}
			throw new RuntimeException("动态构造查询条件出错", e);
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
     * 从 criteria 中取得 {@link Projection}, 接口中没有公开此方法, 因此从 {@link CriteriaImpl} 中取得
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
	 * 得到 criteria 中的 OrderEntry[]
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
	 * 移除 criteria 中的 OrderEntry[]
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
     * 为 criteria 增加 OrderEntry[]
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
			
			// 添加 ContextInfo 中的查询条件
			if (!ContextInfo.isConcealQuery()) {
				//rebuildCriterial(criteria);			
			
				if (ContextInfo.getContextCondition() != null) {
					pageInfo = ContextInfo.getContextCondition().getPageInfo();
				}
				
				
				// 查询结束后屏蔽当前条件
		        ContextInfo.concealQuery();
			}
			//构造一个可执行的Criteria
			Criteria executableCriteria = criteria.getExecutableCriteria(session);				
			prepareCriteria(executableCriteria);
			
			// 构造分页查询
			if (pageInfo != null) {
				// Get the orginal orderEntries
				OrderEntry[] orderEntries = getOrders(executableCriteria);
				// Remove the orders
				executableCriteria = removeOrders(executableCriteria);				
				// get the original projection
				Projection projection = getProjection(executableCriteria);
				
				Integer iCount = (Integer) executableCriteria.setProjection(Projections.rowCount()).uniqueResult();
                if (iCount == null) {
                	throw new RuntimeException("无法执行 count 统计, 很可能是 [ " + criteria.getClass() + " ] 没有相应的 hbm 配置文件");
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
                // 追加排序项目id(如果不进行排序,翻页处理无法正常显示)
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
