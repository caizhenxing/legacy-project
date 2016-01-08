/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service.excelimpl;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import bsh.EvalError;


/**
 * @author zhangli
 * @version $Id: RowCallback.java,v 1.2 2007/12/17 11:02:38 lanxg Exp $
 * @since 2007-5-8
 */
public interface RowCallback {
    
    void row(int index, Object obj) throws EvalError, RowsExceededException, WriteException;
}
