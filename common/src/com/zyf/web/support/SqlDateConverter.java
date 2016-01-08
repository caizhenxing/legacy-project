package com.zyf.web.support;

import java.util.Date;

/**
 * ʵ�ִ��ַ���ת����<code>java.sql.Date</code>
 * @author scott
 * @since 2006-4-17
 * @version $Id: SqlDateConverter.java,v 1.1 2007/11/05 03:16:26 yushn Exp $
 *
 */
public class SqlDateConverter extends DateConverter {
    protected Date cast(Date d) {
        return new java.sql.Date(d.getTime());
    }
}
