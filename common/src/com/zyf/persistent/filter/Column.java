package com.zyf.persistent.filter;

/**
 * ���ڲ�ѯ�����б�ʾ������֮���ϵ��ֵ����, �������ݿ���������, <code>finishedTime</code>, 
 * <code>deadline</code>, �������͵���������������<code>finishedTime < deadline</code>
 * ��ʾֵ��<code>dto</code>����������
 * @author Scott Captain
 * @since 2006-7-19
 * @version $Id: Column.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 *
 */
public class Column {
    private String columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
