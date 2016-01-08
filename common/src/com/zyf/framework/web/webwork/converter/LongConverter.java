/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.web.webwork.converter;

import java.util.Map;

import com.opensymphony.webwork.util.WebWorkTypeConverter;
import com.opensymphony.xwork.util.TypeConversionException;

/**
 * @since 2006-1-25
 * @author ÍõÕþ
 * @version $Id: LongConverter.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public class LongConverter extends WebWorkTypeConverter {

	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (toClass != Long.class) {
			throw new TypeConversionException(new UnsupportedOperationException(getClass() + " only support " + Long.class + " Type!"));
		}

		if (values == null || values.length == 0 || "".equals(values[0])) {
			return null;
		}
		
		try {
			return new Long(values[0]);
		} catch (NumberFormatException e) {
			throw new TypeConversionException("Can't convert " + values[0] + " to " + Integer.class );
		}	
	}

	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		
		if (! Long.class.isInstance(o)) {
			throw new TypeConversionException(new UnsupportedOperationException(getClass() + "  only support " + Long.class + " Type!"));
		}
		return ((Long)o).toString();
	}

}
