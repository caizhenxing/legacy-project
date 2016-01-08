/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.codename;

/**
 * ϵͳ��<code>enumeration</code>�Ļ���, �ܹ��ṩ�����ݿ�, ҳ������ת��, Ҫ���������ʵ��
 * ��̬�ķ���, ����:
 * <pre>
 *      public static InstrumentState getByCode(Striug code) {
 *          return (InstrumentState) INSTANCES.get(code);
 *      }
 * </pre>
 * @author zhangli
 * @version $Id: EnumerationCodeName.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 * @since 2007-4-27
 */
public class EnumerationCodeName implements CodeName {
    
    /** ���ݴ�����ö��ʵ���ķ����� */
    public static final String METHOD_NAME = "getByCode";
    
    /** ö��ʵ���ķ����Ĳ��� */
    public static final Class[] METHOD_PARAMETERS = new Class[] {String.class};

    protected String code;
    
    protected String name;
    
    protected EnumerationCodeName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    
    /**
     * ������������Ϊ�˴�ҳ����װ����ʱʹ��, ���û�����<code>write</code>����, ����
     * <code>java bean</code>�Ĺ淶�޷���װ�������
     */
    public void setCode(String code) {}
    public void setName(String name) {}

    public String getName() {
        return name;
    }
    
    public String toString() {
        return getName();
    }
}
