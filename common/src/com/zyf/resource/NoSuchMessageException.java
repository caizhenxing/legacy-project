package com.zyf.resource;

/**
 * ��ʹ��ָ����<code>code</code>����������Ϣʱ, û���ҵ���Ӧ����Ϣ�׳�����쳣
 * 
 * @author scott
 * @since 2006-1-9
 * @version $Id: NoSuchMessageException.java,v 1.1 2007/11/05 03:16:08 yushn Exp $
 *
 */
public class NoSuchMessageException extends RuntimeException {
    private String code;
    
    public NoSuchMessageException(String code) {
        super("����[" + code + "]û�ж�����Ϣ");
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }
}
