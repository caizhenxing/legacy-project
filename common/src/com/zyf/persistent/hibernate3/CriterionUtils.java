/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project framework
 */
package com.zyf.persistent.hibernate3;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.zyf.persistent.filter.ConditionConstants;
import com.zyf.utils.MiscUtils;

/**
 * @since 2006-10-4
 * @author java2enterprise
 * @version $Id: CriterionUtils.java,v 1.1 2007/11/05 03:16:17 yushn Exp $
 * @deprecated With HibernateUtils instead of this 
 */
public abstract class CriterionUtils {
	
	/**
	 * ���� id ���ϵõ� in ��ѯ����, ��ѯʱ��� id ���ϳ��� 1000 ��, ���Զ����з���
	 * @param ids list fill with id {@link String}
	 * @return ��Ϻ�� in ����
	 */
	public static Criterion splitIdsConditionIfNecessary(List ids) {
		return splitParamsConditionIfNecessary(ids, "id");
	}
	
	/**
	 * ���ݲ�������, �������� �õ� in ��ѯ����, ��ѯʱ��� �������ϳ��� 1000 ��, ���Զ����з���
	 * @param params list fill with param
	 * @param paramName ������������
	 * @return ��Ϻ�� in ����
	 */
	public static Criterion splitParamsConditionIfNecessary(List params, String paramName) {
		// Oracle ���ܵ�һ�����ʽ�е�����������
		int maxNumberOfExpression = 1000;
		List splitedList = MiscUtils.splitListBySize(params, maxNumberOfExpression);				
		int size = splitedList.size();
		Criterion[] criterions = new Criterion[size];
		String[] ops = new String[size];
		
		for (int i = 0; i < size; i++) {
			List splitedParams = (List) splitedList.get(i);
			criterions[i] = Restrictions.in(paramName, splitedParams);
			ops[i] = ConditionConstants.OR;
		}
		
		Criterion criterion = new MultiCriterionsExpression(criterions, ops);
		return criterion;
	}
	
}
