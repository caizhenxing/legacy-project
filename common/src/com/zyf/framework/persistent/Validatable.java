/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.persistent;

/**
 * 验证业务实体内部状态, 在从组装来自页面的属性和保存业务实体到数据库时架构调用这个方法, 通常
 * 是验证字符串长度不能超过<b>4000</b>, 某些属性长度不超过数据库定义的长度等等
 * @author zhangli
 * @version $Id: Validatable.java,v 1.1 2007/11/05 03:16:18 yushn Exp $
 * @since 2007-4-7
 */
public interface Validatable {
    void validate();
}
