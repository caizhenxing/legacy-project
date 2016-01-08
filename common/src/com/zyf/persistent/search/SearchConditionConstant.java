package com.zyf.persistent.search;

/**
 * �����ѯ������ʹ�õ��ĳ���
 * @author scott
 * @since 2006-4-21
 * @version $Id: SearchConditionConstant.java,v 1.1 2007/11/05 03:16:16 yushn Exp $
 *
 */
public interface SearchConditionConstant {
    /** ��ʾ���Ե���ֵ */
    String EQUAL = "=";
    
    /** ��ʾ���Բ�����ֵ */
    String NOT_EQUAL = "!=";
    
    /** ��ʾ���Դ��ڻ����ֵ */
    String GREATER_EQUAL = ">=";
    
    /** ��ʾ���Դ���ֵ */
    String GREATER = ">";
    
    /** ��ʾ����С�ڻ����ֵ */
    String LESS_EQUAL = "<=";
    
    /** ��ʾ����С��ֵ */
    String LESS = "<";
    
    /** ��ʾ��������ƥ���ֵ */
    String LIKE = "like";
    
    /** ��ʾ����֮���"and"��ϵ */
    String AND = "and";
    
    /** ��ʾ����֮���"or"��ϵ */
    String OR = "or";
    
    /** ��ʾ����֮���"not"��ϵ */
    /*
    String NOT = "not";
    */

    /** ����ָ��������ֵ֮��Ĳ�����ʱʹ�õ�ȱʡ������ */
    String DEFAULT_OPERATOR = EQUAL;
    
    /** ����ָ������֮��Ĺ�ϵʱʹ�����ȱʡ�Ĺ�ϵ */
    String DEFAULT_RELATION = AND;
}
