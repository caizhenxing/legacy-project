package com.zyf.web.support;

import java.sql.Timestamp;
import java.util.Date;

/**
 * ʵ�ִ��ַ���ת����<code>java.sql.Timestamp</code>, ������ʧ��<code>nano-second</code>
 * @author scott
 * @since 2006-4-17
 * @version $Id: SqlTimestampConverter.java,v 1.1 2007/11/05 03:16:26 yushn Exp $
 *
 */
public class SqlTimestampConverter extends DateConverter {
    protected Date cast(Date d) {
        return new Timestamp(d.getTime());
    }
}
