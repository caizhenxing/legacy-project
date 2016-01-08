package com.zyf.common.transfer.importexcel;


/**
 * 公共 导入配置项, 一个这样的配置项表现一个<code>dbf</code>或<code>excel</code>域与
 * 数据库表之间的对应关系
 * @author Scott Captain
 * @since 2006-8-16
 * @version $Id: CommonTransferConfigItem.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 *
 */
public class CommonTransferConfigItem {
    /** 数据库表的列名称 */
    private String columnName;
    
    /** <code>dbf</code>或<code>excel</code>中列名称 */
    private String fieldName;
    
    /** 一个列的中文名称 */
    private String label;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("column name=[").append(columnName).append("],");
        sb.append("field name=[").append(fieldName).append("],");
        sb.append("label=[").append(label).append("]");
        return sb.toString();
    }
}
