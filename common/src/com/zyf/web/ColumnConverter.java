package com.zyf.web;

import org.apache.commons.beanutils.Converter;

import com.zyf.persistent.filter.DtoMetadata;
import com.zyf.persistent.filter.impl.DefaultDtoMetadata;

/**
 * 
 * @author Scott Captain
 * @since 2006-7-19
 * @version $Id: ColumnConverter.java,v 1.1 2007/11/05 03:16:04 yushn Exp $
 *
 */
public class ColumnConverter implements Converter {
    private DtoMetadata dtoMetadata = new DefaultDtoMetadata();

    public Object convert(Class clazz, Object value) {
        if (value != null) {
            String columnName = (String) value;
            return dtoMetadata.getColumnName(columnName);
        }

        return null;
    }

}
