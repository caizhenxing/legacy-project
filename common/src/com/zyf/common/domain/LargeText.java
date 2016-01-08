/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.zyf.exception.UnexpectedException;
import com.zyf.framework.persistent.Entity;

/**
 * <p>����ҵ��ʵ���б�����ܺܳ����ַ���, �����ַ������ȱ��������ݿ��е�<code>VARCHAR</code>
 * ���п��ܳ�������, ����������ҳ���о�����Ϊ���ı�������<code>html</code>���߱༭��������,
 * ���ڷ���������Գ������ַ�ʽ������ҵ��ʵ����<pre>
 *     ......
 *     private String name;
 *     private LargeText note = new LargeText(this);
 *     ......
 * </pre>,�����ڱ��浽���ݿ�֮ǰ��������{@link #owner}����</p>
 * <p>�������ֵ��ַ���ʵ��д��<code>vfs</code>��, һ�Ǳ��������ݳ������ƵĿ�����, ����
 * ������<code>CLOB</code>���͵ĳ�ʼ�����ݵĲ���, ���ǲ�ͬ�����ݿ��Ʒ֮���һ����</p>
 * <p>������Ƕ�<code>String</code>���͵ķ�װ, ���е�{@link #owner}������Ϊ�˷���־û�</p>
 * @author zhangli
 * @version $Id: LargeText.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @since 2007-4-26
 */
public class LargeText implements Serializable, Cloneable {
    
    private static final String TAG_PATTERN = "(<[^>]+>)|([\\n\\r]+)|(\\&#?[^\\&#;]+;)|(#[0-9a-zA-Z]+;)";
    
    private static final String NEW_LINE_PATTERN = "[\\r\\n\\\\]+";
    
    public LargeText() {
        super();
    }
    
    public LargeText(Entity owner) {
        this.owner = owner;
    }
    
    /** �ַ��������� */
    private String content;
    
    /** ӵ��������Ե�ҵ��ʵ�� */
    private Entity owner;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }
    
    /**
     * ȥ�������е�<code>html</code>��Ǻͻ��лس������ַ�, ���ش��ı���ʽ������
     * @return ���ı���ʽ������
     */
    public String getPlainContent() {
        if (StringUtils.isNotBlank(getContent())) {
            return getContent().replaceAll(TAG_PATTERN, new String());
        }
        return getContent();
    }
    
    /**
     * ���ڵ�ҳ����ʾ��ʽ����<code>hidden field</code>��������, �������а��������ַ�ʱ
     * ���ҳ�����, �ṩ����������������ֿ���
     * @return �ų����س�,����,<b>\</b>������
     */
    public String getWithoutNewLineContent() {
        if (StringUtils.isNotBlank(getContent())) {
            return getContent().replaceAll(NEW_LINE_PATTERN, new String());
        }
        return getContent();
    }

    public Object clone() {
        LargeText lt = null;
        try {
            lt = (LargeText) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnexpectedException(getClass().getName() + " throw it?!", e);
        }
        return lt;
    }
}
