/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.fileupload;

import java.text.SimpleDateFormat;

import org.springframework.util.Assert;

import com.zyf.framework.persistent.Entity;
import com.zyf.framework.utils.AssertUtils;

/**
 * ҵ��ʵ��ȱʡ���ϴ��ļ�·��������, ����ҵ��ʵ��İ�������Ŀ¼, ͬʱ�����Ŀ¼����������
 * {@link Entity#getCreatedTime()}�������պ�<code>id</code>�ֱ���Ϊ·��,<b>ע��:��
 * ֧���ڲ���ʹ�����(<code>jdk/cglib</code>)</b>
 * @author zhangli
 * @version $Id: DefaultUploadFilePathGenerator.java,v 1.1 2007/07/27 03:13:55 zhuxb Exp $
 * @since 2007-4-25
 */
public class DefaultUploadFilePathGenerator implements UploadFilePathGenerator {
    private static SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    public String path(Entity entity) {
        AssertUtils.notNull(entity, "UploadFilePathGenerator's path����" + AssertUtils.NULL_VALUE_MESSAGE);
        StringBuffer buf = new StringBuffer(200).append("�����ϴ��ļ�·��ʱ").append(entity.getClass().getName());
        /* spring's assert throws IllegalArgumentException */
        Assert.notNull(entity.getId(), buf.append("����id�ǿ�ֵ").toString());
        Assert.notNull(entity.getCreatedTime(), buf.append("����createdTime�ǿ�ֵ").toString());
        
        String className = entity.getClass().getName();
        if (className.indexOf('$') > -1) {
            throw new IllegalArgumentException("��֧���ڲ����Լ������������ϴ��ļ�·��" + entity.getClass().getName());
        }
        
        return new StringBuffer(className.replace('.', '/'))
            .append(sdf.format(entity.getCreatedTime()))
            .append(entity.getId())
            .toString();
    }
}
