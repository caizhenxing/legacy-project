package com.zyf.framework.web.webwork.validator;

import com.opensymphony.xwork.validator.ValidationException;
import com.opensymphony.xwork.validator.validators.FieldValidatorSupport;

/**
 * 字段不能为null或""
 * @since 2005-9-20
 * @author 钱安川
 * @version $Id: RequiredFieldValidator.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class RequiredFieldValidator extends FieldValidatorSupport{

	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);

        if (value == null || value.equals("")) {
            addFieldError(fieldName, object);
        }
	}

}
