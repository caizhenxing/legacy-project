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
 * @since 2005-8-29
 * @author ÍõÕþ
 * @version $Id: IntegerConverter.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public class IntegerConverter extends WebWorkTypeConverter {

	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (toClass != Integer.class) {
			throw new TypeConversionException(new UnsupportedOperationException(getClass() + " only support " + Integer.class + " Type!"));
		}

		if (values == null || values.length == 0 || "".equals(values[0])) {
			return null;
		}
		
		try {
			return new Integer(values[0]);
		} catch (NumberFormatException e) {
			throw new TypeConversionException("Can't convert " + values[0] + " to " + Integer.class );
		}	
	}

	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		
		if (! Integer.class.isInstance(o)) {
			throw new TypeConversionException(new UnsupportedOperationException(getClass() + "  only support " + Integer.class + " Type!"));
		}
		return ((Integer)o).toString();
	}

}
