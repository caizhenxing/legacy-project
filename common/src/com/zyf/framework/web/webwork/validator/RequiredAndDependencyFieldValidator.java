package com.zyf.framework.web.webwork.validator;

import com.opensymphony.xwork.validator.ValidationException;
import com.opensymphony.xwork.validator.validators.FieldValidatorSupport;

/**
 * �ֶβ���Ϊnull��""
 * @since 2005-9-21
 * @author Ǯ����
 * @version $Id: RequiredAndDependencyFieldValidator.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class RequiredAndDependencyFieldValidator extends FieldValidatorSupport{
	private String dependencyField = null;
	private String dependencyFieldEqualValue = null;

	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);
        
        Object dependencyValue = this.getFieldValue(dependencyField,object);
        Object equalValue = this.getFieldValue(dependencyFieldEqualValue,object);
        
        if((dependencyValue!=null && dependencyValue.equals(equalValue)) && (value == null || value.equals(""))){
        	addFieldError(fieldName, object);
        }
	}

	public String getDependencyField() {
		return dependencyField;
	}

	public void setDependencyField(String dependencyField) {
		this.dependencyField = dependencyField;
	}

	public String getDependencyFieldEqualValue() {
		return dependencyFieldEqualValue;
	}

	public void setDependencyFieldEqualValue(String dependencyFieldValue) {
		this.dependencyFieldEqualValue = dependencyFieldValue;
	}
}
