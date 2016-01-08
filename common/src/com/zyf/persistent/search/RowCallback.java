package com.zyf.persistent.search;

import java.sql.ResultSet;

/**
 * 用于按检索条件执行查询的行回调接口
 * @author scott
 * @since 2006-4-19
 * @version $Id: RowCallback.java,v 1.1 2007/11/05 03:16:16 yushn Exp $
 *
 */
public interface RowCallback {
    Object process(ResultSet resultSet, int rowNum);
}
