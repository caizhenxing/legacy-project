/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.fileupload;

import java.text.SimpleDateFormat;

import org.springframework.util.Assert;

import com.zyf.framework.persistent.Entity;
import com.zyf.framework.utils.AssertUtils;

/**
 * 业务实体缺省的上传文件路径生成器, 根据业务实体的包名构建目录, 同时在这个目录下再以属性
 * {@link Entity#getCreatedTime()}的年月日和<code>id</code>分别作为路径,<b>注意:不
 * 支持内部类和代理类(<code>jdk/cglib</code>)</b>
 * @author zhangli
 * @version $Id: DefaultUploadFilePathGenerator.java,v 1.1 2007/07/27 03:13:55 zhuxb Exp $
 * @since 2007-4-25
 */
public class DefaultUploadFilePathGenerator implements UploadFilePathGenerator {
    private static SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    public String path(Entity entity) {
        AssertUtils.notNull(entity, "UploadFilePathGenerator's path方法" + AssertUtils.NULL_VALUE_MESSAGE);
        StringBuffer buf = new StringBuffer(200).append("生成上传文件路径时").append(entity.getClass().getName());
        /* spring's assert throws IllegalArgumentException */
        Assert.notNull(entity.getId(), buf.append("属性id是空值").toString());
        Assert.notNull(entity.getCreatedTime(), buf.append("属性createdTime是空值").toString());
        
        String className = entity.getClass().getName();
        if (className.indexOf('$') > -1) {
            throw new IllegalArgumentException("不支持内部类以及代理类生成上传文件路径" + entity.getClass().getName());
        }
        
        return new StringBuffer(className.replace('.', '/'))
            .append(sdf.format(entity.getCreatedTime()))
            .append(entity.getId())
            .toString();
    }
}
