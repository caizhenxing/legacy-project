/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.config;

import com.zyf.framework.codename.CodeName;

/**
 * 报表配置
 * @author zhangli
 * @version $Id: ReportConfig.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @since 2007-5-6
 */
public interface ReportConfig extends CodeName {
    
    /** 保存报表模板的根目录, 这段路径对应用透明, 以便从全局管理不同应用的<code>vfs</code>路径 */
    public static final String TEMPLATE_ROOT_PATH = "report/template";
}
