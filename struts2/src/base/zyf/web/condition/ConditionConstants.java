package base.zyf.web.condition;

/**
 * ����������ʹ�õ��ĳ���
 * @author scott
 * @since 2006-4-21
 * @version $Id: ConditionConstants.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 *
 */
public interface ConditionConstants {
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
    
    /** ��ʾ<code>sql</code>����е�<code>in</code>��ϵ������ */
    String IN = "in";
    
    /** ��ʾ����֮���"and"��ϵ */
    String AND = "and";
    
    /** ��ʾ����֮���"or"��ϵ */
    String OR = "or";
    
    /** Ƕ������֮��ķָ��� */
    String PROPERTY_SEPARATOR = ".";
    
    /** ��ʾ����֮���"not"��ϵ */
    /*
    String NOT = "not";
    */

    /** ����ָ��������ֵ֮��Ĳ�����ʱʹ�õ�ȱʡ������ */
    String DEFAULT_OPERATOR = EQUAL;
    
    /** ����ָ������֮��Ĺ�ϵʱʹ�����ȱʡ�Ĺ�ϵ */
    String DEFAULT_RELATION = AND;
    
    /** ����ָ������������ʱȱʡ������ֵ���� */
    Class DEFAULT_TYPE = String.class;
}
