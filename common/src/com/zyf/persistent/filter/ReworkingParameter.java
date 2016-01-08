package com.zyf.persistent.filter;

/**
 * 基于架构安全,通用查询目的修改一个<code>sql</code>参数的回调接口, 实现作为<code>SqlMapProxy</code>
 * 的属性配置
 * 
 * @author scott
 * @since 2006-5-6
 * @version $Id: ReworkingParameter.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 *
 */
public interface ReworkingParameter {
    /**
     * 提供了可修改的参数, 具体的内容取决于这个实例作用的方法 
     * @param params
     */
    void rework(Object[] params);
    
    /**
     * 恢复修改了的参数
     */
    void restore();
}
