/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service.excelimpl;

import java.util.List;

/**
 * �б��ʽ��<code>excel</code>���������
 * @author zhangli
 * @version $Id: ReportWithGridFormatOutputter.java,v 1.2 2007/12/17 11:02:38 lanxg Exp $
 * @since 2007-5-8
 */
public interface ReportWithGridFormatOutputter {
    
    void execute(int start, int end, List cells, RowCallback callback);
}
