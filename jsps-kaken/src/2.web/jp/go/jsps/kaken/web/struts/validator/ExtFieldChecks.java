/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts.validator;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;

/**
 * Validator�`�F�b�N���g������N���X�B
 * 
 * ID RCSfile="$RCSfile: ExtFieldChecks.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:03 $"
 */
public class ExtFieldChecks extends FieldChecks {

	/**
	 *  Commons Logging instance.
	 */
	private static final Log log = LogFactory.getLog(ExtFieldChecks.class);

	private static final String FIELD_TEST_NOTEQUAL = "NOTEQUAL";

	/**
	 * �t�B�[���h�̍ő啶����o�C�g���̃`�F�b�N���s���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return True if stated conditions met.
	 */
	public static boolean validateMaxLengthB(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			try {
				int maxlengthb =
					Integer.parseInt(field.getVarValue("maxlengthb"));
				if (value.getBytes("WINDOWS-31J").length > maxlengthb) {
					errors.add(
						field.getKey(),
						Resources.getActionError(request, va, field));
					return false;
				}
			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�B�[���h�̎w�蕶����o�C�g���̃`�F�b�N���s���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return True if stated conditions met.
	 */
	public static boolean validateLengthB(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			try {
				int lengthb = Integer.parseInt(field.getVarValue("lengthb"));
				if (value.getBytes("WINDOWS-31J").length != lengthb) {
					errors.add(
						field.getKey(),
						Resources.getActionError(request, va, field));
					return false;
				}

			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�B�[���h�̎w�蕶����o�C�g���̃`�F�b�N���s���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return True if stated conditions met.
	 */
	public static boolean validateLength(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null && !value.equals("")) {
			try {
				int length = Integer.parseInt(field.getVarValue("length"));
				if (value.length() != length) {
					errors.add(
						field.getKey(),
						Resources.getActionError(request, va, field));
					return false;
				}

			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�B�[���h�������݂̂ł��邩���؂���B
	 * �S�p���p��킸���l�^�ł����true��Ԃ��B
	 * �܂��A�S�p�����̏ꍇ�͔��p�����ɕϊ������l��bean�ɃZ�b�g����B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateNumeric(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//�v���p�e�B�ăZ�b�g�i�S�p�����𔼊p�����ɕϊ������l�j
		try {
			value = StringUtil.toHankakuDigit(value);
			PropertyUtils.setProperty(bean, field.getProperty(), value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//������\�������؂���
		if (value != null) {
			try {
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (!StringUtil.isDigit(c)) {
						errors.add(
							field.getKey(),
							Resources.getActionError(request, va, field));
						return false;
					}
				}
			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�B�[���h�����pASCII�����݂̂ł��邩���؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateAscii(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			try {
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (!StringUtil.isAscii(c)) {
						errors.add(
							field.getKey(),
							Resources.getActionError(request, va, field));
						return false;
					}
				}
			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}


	/**
	 * �t�B�[���h���S�p�����݂̂ł��邩���؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateZenkaku(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			if(!StringUtil.isZenkaku(value)){
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�B�[���h���t���K�i�݂̂ł��邩���؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateFurigana(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			if(!StringUtil.isKana(value)){
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	
	/**
	 * �t�B�[���h�����������t�ł��邩���؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateDateFields(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {
		
		//�ݒ���̎擾�ƃ`�F�b�N		
		String dateProperties = field.getVarValue("dateProperties");
		if(dateProperties == null){
			throw new IllegalArgumentException("dateFields�`�F�b�N�ł́A���t�t�B�[���h'dateProperties'���w�肵�Ă��������B");
		}
		String actionFlg = field.getVarValue("action");
		if(actionFlg == null){
			actionFlg = "ADD";
		}
				
		//�`�F�b�N���鍀�ڂ��擾����B
		String[] datePropery = StringUtil.delimitedListToStringArray(dateProperties,",");

		//���Ɉˑ����ڂ��A�G���[�ł���ꍇ�͏������Ȃ��B
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�`�F�b�N���鍀��
			if(checkExists(datePropery, element)) return true;
		}

		//�t�H�[�����Y���f�[�^�̎擾
		String year = getString(bean, datePropery[0]);
		String month = getString(bean, datePropery[1]);
		String day = getString(bean, datePropery[2]);

		if(actionFlg.equals("ADD")){
			//�o�^�������̏ꍇ�́A�ˑ����ڂ��A�����͂ł���ꍇ�͏������Ȃ��B
			if (GenericValidator.isBlankOrNull(year) || GenericValidator.isBlankOrNull(month) || GenericValidator.isBlankOrNull(day)) {
				return true;
			}
		}else if(actionFlg.equals("SEARCH")){
			//�����������̏ꍇ�́A�ˑ����ڂ����ׂĖ����͂ł���ꍇ�͏������Ȃ��B
			if (GenericValidator.isBlankOrNull(year) && GenericValidator.isBlankOrNull(month) && GenericValidator.isBlankOrNull(day)) {
				return true;
			}			
		}
		
		//���t�Ó����`�F�b�N
		if (!StringUtil.isDate(year + "/" + month + "/" + day)) {
				errors.add(
					datePropery[0],
					Resources.getActionError(request, va, field));
			return false;
		}
		
		return true;
	}

	/**
	 * ���t�̊��ԁi���N���������`���N���������j�����������ǂ��������؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateDateHani(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		//�ݒ���̎擾�ƃ`�F�b�N		
		String startDate = field.getVarValue("startDate");
		if(startDate == null){
			throw new IllegalArgumentException("startDate�`�F�b�N�ł́A���t�͈̓t�B�[���h'startDate'���w�肵�Ă��������B");
		}
		String endDate = field.getVarValue("endDate");
		if(endDate == null){
			throw new IllegalArgumentException("endDate�`�F�b�N�ł́A���t�͈̓t�B�[���h'endDate'���w�肵�Ă��������B");
		}
		//�`�F�b�N���鍀�ڂ��擾����B
		String[] startDateArgs = StringUtil.delimitedListToStringArray(startDate,",");
		String[] endDateArgs = StringUtil.delimitedListToStringArray(endDate,",");		

		//���Ɉˑ����ڂ��A�G���[�ł���ꍇ�͏������Ȃ��B
		//�J�n��
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�`�F�b�N���鍀��
			if(checkExists(startDateArgs, element)) return true;
		}
		//�I����
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�`�F�b�N���鍀��
			if(checkExists(endDateArgs, element)) return true;
		}
		//�t�H�[�����Y���f�[�^�̎擾
		//�J�n��
		String startYear = getString(bean, startDateArgs[0]);
		String startMonth = getString(bean, startDateArgs[1]);
		String startDay = getString(bean, startDateArgs[2]);
		//�I����
		String endYear = getString(bean, endDateArgs[0]);
		String endMonth = getString(bean, endDateArgs[1]);
		String endDay = getString(bean, endDateArgs[2]);

		//�ˑ����ڂ��A�����͂ł���ꍇ�͏������Ȃ��B
		if (GenericValidator.isBlankOrNull(startYear) || GenericValidator.isBlankOrNull(startMonth) || GenericValidator.isBlankOrNull(startDay)
			|| GenericValidator.isBlankOrNull(endYear) || GenericValidator.isBlankOrNull(endMonth) || GenericValidator.isBlankOrNull(endDay)
			) {
			return true;		
		}
				
		DateUtil stateDateUtil = new DateUtil();
		stateDateUtil.setCal(startYear,startMonth,startDay);
		DateUtil endDateUtil = new DateUtil();
		endDateUtil.setCal(endYear,endMonth,endDay);
		int hi = stateDateUtil.getElapse(endDateUtil);
		if(hi < 0){
			//�J�n�����I�����̏ꍇ
			errors.add(
			startDateArgs[0],
				Resources.getActionError(request, va, field));
			return false;
		}	
		
		return true;
	}

	/**
	 * ���t�̊��ԁi���N�����`���N�����j�����������ǂ��������؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateDateHaniNengetu(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		//�ݒ���̎擾�ƃ`�F�b�N		
		String startDate = field.getVarValue("startDate");
		if(startDate == null){
			throw new IllegalArgumentException("startDate�`�F�b�N�ł́A���t�͈̓t�B�[���h'startDate'���w�肵�Ă��������B");
		}
		String endDate = field.getVarValue("endDate");
		if(endDate == null){
			throw new IllegalArgumentException("endDate�`�F�b�N�ł́A���t�͈̓t�B�[���h'endDate'���w�肵�Ă��������B");
		}
		//�`�F�b�N���鍀�ڂ��擾����B
		String[] startDateArgs = StringUtil.delimitedListToStringArray(startDate,",");
		String[] endDateArgs = StringUtil.delimitedListToStringArray(endDate,",");		

		//���Ɉˑ����ڂ��A�G���[�ł���ꍇ�͏������Ȃ��B
		//�J�n��
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�`�F�b�N���鍀��
			if(checkExists(startDateArgs, element)) return true;
		}
		//�I����
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�`�F�b�N���鍀��
			if(checkExists(endDateArgs, element)) return true;
		}
		//�t�H�[�����Y���f�[�^�̎擾
		//�J�n��
		String startYear = getString(bean, startDateArgs[0]);
		String startMonth = getString(bean, startDateArgs[1]);
		//String startDay = getString(bean, startDateArgs[2]);
		String startDay = "01";		//����01��ݒ肷��

		//�I����
		String endYear = getString(bean, endDateArgs[0]);
		String endMonth = getString(bean, endDateArgs[1]);
		//String endDay = getString(bean, endDateArgs[2]);
		String endDay = "01";		//����01��ݒ肷��

		//�ˑ����ڂ��A�����͂ł���ꍇ�͏������Ȃ��B
		if (GenericValidator.isBlankOrNull(startYear) || GenericValidator.isBlankOrNull(startMonth) || GenericValidator.isBlankOrNull(startDay)
			|| GenericValidator.isBlankOrNull(endYear) || GenericValidator.isBlankOrNull(endMonth) || GenericValidator.isBlankOrNull(endDay)
			) {
			return true;		
		}
				
		DateUtil stateDateUtil = new DateUtil();
		stateDateUtil.setCal(startYear,startMonth,startDay);
		DateUtil endDateUtil = new DateUtil();
		endDateUtil.setCal(endYear,endMonth,endDay);
		int hi = stateDateUtil.getElapse(endDateUtil);
		if(hi < 0){
			//�J�n�����I�����̏ꍇ
			errors.add(
			startDateArgs[0],
				Resources.getActionError(request, va, field));
			return false;
		}	
		
		return true;
	}

	
	/**
	 * �ˑ����ڂ̃G���[���`�F�b�N����B
	 * �ˑ����ڂɃG���[���������ꍇ�͎��̃`�F�b�N�̏��������Ȃ��B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateDependFields(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {
		
		//�ˑ�����v���p�e�B
		String[] dependPropery = StringUtil.delimitedListToStringArray(field.getVarValue("dependProperties"),",");

		//���Ɉˑ����ڂ��A�G���[�ł���ꍇ�͏������Ȃ��B
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//�ˑ����鍀�ڂ��G���[�̏ꍇ�͎��̃`�F�b�N���Ȃ��B
			if(checkExists(dependPropery, element)) return false;
		}
		
		//���̃`�F�b�N���s���B
		return true;
	}


	/**
	 * �t�B�[���h�̊g���q���w�肵���g���q�ł��邩���؂���B
	 * �I���ł���g���q�𕡐��w�肷�邱�Ƃ��\�B
	 * �����w�肷��ꍇ�́Aextension�^�O�ŃJ���}�u,�v�ŋ�؂��Ďw�肷��B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateFileExtension(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {
		
		//**** 2004.9.27�@�g���q�̕����w�肪�ł���悤�ɏC�� ****
		//�ݒ���̎擾�ƃ`�F�b�N		
		String extStr = field.getVarValue("extension");
		//�������z��ɕ�������
		String[] extensions = StringUtil.delimitedListToStringArray(extStr, ",");
		if(extensions == null || extensions.length == 0){
			throw new IllegalArgumentException("extension�`�F�b�N�ł́A�g���q�t�B�[���h'extension'���w�肵�Ă��������B");
		}
//		String extension = field.getVarValue("extension");
//		if(extension == null){
//			throw new IllegalArgumentException("extension�`�F�b�N�ł́A�g���q�t�B�[���h'extension'���w�肵�Ă��������B");
//		}

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = (String) ValidatorUtil.getValueAsString(bean, field.getProperty());
		}
		
		//�t�@�C�����I������Ă���ꍇ�̂݃`�F�b�N
		boolean flg = false;
		if(value != null && value.length() != 0){
			int index = value.lastIndexOf(".");
			
			if(index < 0){
				//�g���q���Ȃ�������G���[
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return flg;				
			}
			
			//�g���q���J��Ԃ�
			for(int i = 0; i<extensions.length; i++){
				String ext = value.substring(index+1).toLowerCase();
				//�g���q�`�F�b�N
				if(extensions[i].equals("*")){	
					flg = true;	//�g���q�͎w��Ȃ�
				}else if((extensions[i].toLowerCase()).equals(ext)){	
					flg = true;	//�g���q���������ꍇ
				}
			}
			if(flg == false){
				//�g���q�G���[����������
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
					return flg;						
			}else{
				//�g���q�G���[���Ȃ�������
				return flg;						
			}
		}		
//		if(value != null && value.length() != 0){
//			int index = value.lastIndexOf(".");
//			if(index > -1){
//				String ext = value.substring(index+1).toLowerCase();
//				//�g���q�`�F�b�N
//				if(extension.equals("*")){
//					return true;
//				}else if(!(extension.toLowerCase()).equals(ext)){
//					errors.add(
//						field.getKey(),
//						Resources.getActionError(request, va, field));
//					return false;		
//				}
//			}else{
//				//�g���q���Ȃ�������G���[
//				errors.add(
//					field.getKey(),
//					Resources.getActionError(request, va, field));
//				return false;
//			}
//		}		
		return true;
	}

	/**
	 * �t�B�[���h�̃t�@�C���T�C�Y�����؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateMaxFileSize(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {
		
        //
        if(ValidatorUtil.getValueAsString(bean, field.getProperty()) == null){
            return true;
        }
        
		//�ݒ���̎擾�ƃ`�F�b�N		
		String maxFileSize = field.getVarValue("maxfilesize");
		if(maxFileSize == null){
			throw new IllegalArgumentException("maxfilesize�`�F�b�N�ł́A�g���q�t�B�[���h'maxfilesize'���w�肵�Ă��������B");
		}
		
		int maxsize = 0;		
		//M�P�ʂ��o�C�g�P�ʂɕϊ�
		int index = maxFileSize.lastIndexOf("M");
		if(index > -1){
			maxsize = Integer.parseInt(maxFileSize.substring(0, index));
			maxsize = maxsize*1048576;			
		}else{
			//����ȊO�͕ϊ����Ȃ�
		}

		if(Integer.parseInt(ValidatorUtil.getValueAsString(bean, field.getProperty() + ".fileSize")) > maxsize){
			errors.add(
				field.getKey(),
				Resources.getActionError(request, va, field));
			return false;
		}
		return true;
	}


	/**
	 * �t�@�C���̕K�{�����؂���B�i0�o�C�g�̓G���[�Ƃ���j
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateRequiredFile(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {
		
		//
		if(ValidatorUtil.getValueAsString(bean, field.getProperty()) == null){
			return true;
		}
        
		if(Integer.parseInt(ValidatorUtil.getValueAsString(bean, field.getProperty() + ".fileSize")) == 0){
			errors.add(
				field.getKey(),
				Resources.getActionError(request, va, field));
			return false;
		}
		return true;
	}



	/**
	 * �v���p�e�B���̒l��������������t�@�C���K�{�`�F�b�N������B
	 *
	 * @param bean The bean validation is being performed on.
	 * @param va The <code>ValidatorAction</code> that is currently being 
	 * performed.
	 * @param field The <code>Field</code> object associated with the current 
	 * field being validated.
	 * @param errors The <code>ActionErrors</code> object to add errors to if 
	 * any validation errors occur.
	 * @param validator The <code>Validator</code> instance, used to access 
	 * other field values.
	 * @param request Current request object.
	 * @return true if meets stated requirements, false otherwise.
	 */
	public static boolean validateRequiredFileIf(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		org.apache.commons.validator.Validator validator,
		HttpServletRequest request) {

		Object form =
			validator.getResource(
				org.apache.commons.validator.Validator.BEAN_KEY);
		String value = null;
		boolean required = false;

		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		int i = 0;
		String fieldJoin = "AND";
		if (!GenericValidator.isBlankOrNull(field.getVarValue("fieldJoin"))) {
			fieldJoin = field.getVarValue("fieldJoin");
		}

		if (fieldJoin.equalsIgnoreCase("AND")) {
			required = true;
		}

		while (!GenericValidator.isBlankOrNull(field.getVarValue("field[" + i + "]"))) {
			String dependProp = field.getVarValue("field[" + i + "]");
			String dependTest = field.getVarValue("fieldTest[" + i + "]");
			String dependTestValue = field.getVarValue("fieldValue[" + i + "]");
			String dependIndexed = field.getVarValue("fieldIndexed[" + i + "]");

			if (dependIndexed == null) {
				dependIndexed = "false";
			}

			String dependVal = null;
			boolean thisRequired = false;
			if (field.isIndexed() && dependIndexed.equalsIgnoreCase("true")) {
				String key = field.getKey();
				if ((key.indexOf("[") > -1) && (key.indexOf("]") > -1)) {
					String ind = key.substring(0, key.indexOf(".") + 1);
					dependProp = ind + dependProp;
				}
			}

			dependVal = ValidatorUtil.getValueAsString(form, dependProp);
			if (dependTest.equals(FIELD_TEST_NULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					thisRequired = false;
				} else {
					thisRequired = true;
				}
			}

			if (dependTest.equals(FIELD_TEST_NOTNULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					thisRequired = true;
				} else {
					thisRequired = false;
				}
			}

			if (dependTest.equals(FIELD_TEST_EQUAL)) {
				thisRequired = dependTestValue.equalsIgnoreCase(dependVal);
			}

			if (fieldJoin.equalsIgnoreCase("AND")) {
				required = required && thisRequired;
			} else {
				required = required || thisRequired;
			}

			i++;
		}

		//�`�F�b�N����B
		if (required) {
			return validateRequiredFile(bean, va, field, errors, request);
		}

		//�`�F�b�N���Ȃ��B
		return false;
	}			 


	/**
	 * �z��̒��ɂɗv�f���܂܂�Ă��邩�`�F�b�N����B
	 * @param datePropery
	 * @param element
	 * @return ���݂���Ƃ��@true ���Ȃ��Ƃ��@false
	 */
	private static boolean checkExists(String[] property, String element) {
		for (int i = 0; i < property.length; i++) {
			if(element.equals(property[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �I�u�W�F�N�g���A�t�B�[���h�̒l�𕶎���Ŏ擾����B
	 * @param bean			�`�F�b�N�I�u�W�F�N�g
	 * @param field			�擾����t�B�[���h��
	 * @return
	 */
	private static String getString(Object bean, String field) {
		String value = null;
		
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field);
		}
		return value;
	}
	
	
	/**
	 * �v���p�e�B���̒l��������������`�F�b�N������B
	 *
	 * @param bean The bean validation is being performed on.
	 * @param va The <code>ValidatorAction</code> that is currently being 
	 * performed.
	 * @param field The <code>Field</code> object associated with the current 
	 * field being validated.
	 * @param errors The <code>ActionErrors</code> object to add errors to if 
	 * any validation errors occur.
	 * @param validator The <code>Validator</code> instance, used to access 
	 * other field values.
	 * @param request Current request object.
	 * @return true if meets stated requirements, false otherwise.
	 */
	public static boolean validateIf(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		org.apache.commons.validator.Validator validator,
		HttpServletRequest request) {

		Object form =
			validator.getResource(
				org.apache.commons.validator.Validator.BEAN_KEY);
		String value = null;
		boolean required = false;

		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		int i = 0;
		String fieldJoin = "AND";
		if (!GenericValidator.isBlankOrNull(field.getVarValue("fieldJoin"))) {
			fieldJoin = field.getVarValue("fieldJoin");
		}

		if (fieldJoin.equalsIgnoreCase("AND")) {
			required = true;
		}

		while (!GenericValidator
			.isBlankOrNull(field.getVarValue("field[" + i + "]"))) {
			String dependProp = field.getVarValue("field[" + i + "]");
			String dependTest = field.getVarValue("fieldTest[" + i + "]");
			String dependTestValue = field.getVarValue("fieldValue[" + i + "]");
			String dependIndexed = field.getVarValue("fieldIndexed[" + i + "]");

			if (dependIndexed == null) {
				dependIndexed = "false";
			}

			String dependVal = null;
			boolean thisRequired = false;
			if (field.isIndexed() && dependIndexed.equalsIgnoreCase("true")) {
				String key = field.getKey();
				if ((key.indexOf("[") > -1) && (key.indexOf("]") > -1)) {
					String ind = key.substring(0, key.indexOf(".") + 1);
					dependProp = ind + dependProp;
				}
			}

			dependVal = ValidatorUtil.getValueAsString(form, dependProp);
			if (dependTest.equals(FIELD_TEST_NULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					thisRequired = false;
				} else {
					thisRequired = true;
				}
			}

			if (dependTest.equals(FIELD_TEST_NOTNULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					thisRequired = true;
				} else {
					thisRequired = false;
				}
			}

			//NOT EQUAL�`�F�b�N�@2005/07/07�ǉ�
			if (dependTest.equals(FIELD_TEST_NOTEQUAL)) {
				thisRequired = !dependTestValue.equalsIgnoreCase(dependVal);
			}

			if (dependTest.equals(FIELD_TEST_EQUAL)) {
				thisRequired = dependTestValue.equalsIgnoreCase(dependVal);
			}

			if (fieldJoin.equalsIgnoreCase("AND")) {
				required = required && thisRequired;
			} else {
				required = required || thisRequired;
			}

			i++;
		}

		//�`�F�b�N����B
		if (required) {
			return true;
		}

		//�`�F�b�N���Ȃ��B
		return false;
	}			 
		
	
	
	/**
	 * �w�肳�ꂽ���X�g�v���p�e�B���󂩂ǂ������`�F�b�N����B
	 *
	 * @param bean The bean validation is being performed on.
	 * @param va The <code>ValidatorAction</code> that is currently being performed.
	 * @param field The <code>Field</code> object associated with the current 
	 * field being validated.
	 * @param errors The <code>ActionErrors</code> object to add errors to if 
	 * any validation errors occur.
	 * @param request Current request object.
	 * @return true if meets stated requirements, false otherwise.
	 */
	public static boolean validateRequiredList(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		//�v���p�e�B�̎擾
		Object value = null;
		try {
			value = PropertyUtils.getProperty(bean, field.getProperty());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		//�擾�����v���p�e�B�����X�g�̂Ƃ��B
		if (value != null && List.class.isInstance(value)) {
			if (((List) value).isEmpty()) {
				errors.add(field.getKey(),Resources.getActionError(request, va, field));
				return false;
			} else {
				return true;
			}
		}
		return true;
	}


	/**
	 * �t�B�[���h�̍ŏ�������o�C�g���̃`�F�b�N���s���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return True if stated conditions met.
	 */
	public static boolean validateMinLengthB(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//������\�������؂���
		if (value != null) {
			try {
				int minlengthb =
					Integer.parseInt(field.getVarValue("minlengthb"));
				if (value.getBytes("WINDOWS-31J").length < minlengthb) {
					errors.add(
						field.getKey(),
						Resources.getActionError(request, va, field));
					return false;
				}
			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * �t�B�[���h���ő�l�i�����j�ȓ��ł��邩���؂���B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateMaxValue(
		Object bean,
		ValidatorAction va,
		Field field,
		ActionErrors errors,
		HttpServletRequest request) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		//�ő�l�`�F�b�N
		if (value != null) {
			try {
				for (int i = 0; i < value.length(); i++) {
					int maxValue = Integer.parseInt(field.getVarValue("maxValue"));
					int intValue = Integer.parseInt(value);					
					if(intValue > maxValue){
						errors.add(
							field.getKey(),
							Resources.getActionError(request, va, field));
						return false;
					}
				}
			} catch (Exception e) {
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}	
	
	

	/**
	 * �t�B�[���h�̒l�ƈ�v���Ȃ��������؂���B
	 * �����w�肷��ꍇ�́A�J���}�u,�v�ŋ�؂��Ďw�肷��B
	 *
	 * @param  bean     The bean validation is being performed on.
	 * @param  va       The <code>ValidatorAction</code> that is currently being performed.
	 * @param  field    The <code>Field</code> object associated with the current
	 *      field being validated.
	 * @param  errors   The <code>ActionErrors</code> object to add errors to if any
	 *      validation errors occur.
	 * @param  request  Current request object.
	 * @return          An Integer if valid, a null otherwise.
	 */
	public static boolean validateNotContain(
			Object bean,
			ValidatorAction va,
			Field field,
			ActionErrors errors,
			HttpServletRequest request) {
		
		//�ݒ���̎擾�ƃ`�F�b�N		
		String extStr = field.getVarValue("notcontain");
		//�������z��ɕ�������
		String[] values = StringUtil.delimitedListToStringArray(extStr, ",");
		if(values == null || values.length == 0){
			throw new IllegalArgumentException("notcontain�`�F�b�N�ł́A�g���q�t�B�[���h'notcontain'���w�肵�Ă��������B");
		}
		
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = (String) ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		for(int i = 0; i < values.length; i++) {
			//��v������G���[
			if(values[i].equals(value)) {
				errors.add(field.getKey(),Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	
	
}
