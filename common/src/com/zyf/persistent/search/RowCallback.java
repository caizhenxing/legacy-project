package com.zyf.persistent.search;

import java.sql.ResultSet;

/**
 * ���ڰ���������ִ�в�ѯ���лص��ӿ�
 * @author scott
 * @since 2006-4-19
 * @version $Id: RowCallback.java,v 1.1 2007/11/05 03:16:16 yushn Exp $
 *
 */
public interface RowCallback {
    Object process(ResultSet resultSet, int rowNum);
}
