/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service.excelimpl;

import java.util.List;

/**
 * 列表格式的<code>excel</code>报表输出器
 * @author zhangli
 * @version $Id: ReportWithGridFormatOutputter.java,v 1.2 2007/12/17 11:02:38 lanxg Exp $
 * @since 2007-5-8
 */
public interface ReportWithGridFormatOutputter {
    
    void execute(int start, int end, List cells, RowCallback callback);
}
