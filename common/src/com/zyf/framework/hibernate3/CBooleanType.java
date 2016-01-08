package com.zyf.framework.hibernate3;

import org.hibernate.type.BooleanType;

/**
 * @since 2005-10-26
 * @author Ç®°²´¨
 * @version $Id: CBooleanType.java,v 1.1 2007/12/08 08:48:13 lanxg Exp $
 */
public class CBooleanType extends BooleanType{

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -7824642794364576044L;

	public String objectToSQLString(Object value) throws Exception {
		return ( ( (Boolean) value ).booleanValue() ) ? "'t'" : "'f'";
	}

}
