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
 * Validatorチェックを拡張するクラス。
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
	 * フィールドの最大文字列バイト長のチェックを行う。
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

		//文字列表現を検証する
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
	 * フィールドの指定文字列バイト長のチェックを行う。
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

		//文字列表現を検証する
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
	 * フィールドの指定文字列バイト長のチェックを行う。
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

		//文字列表現を検証する
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
	 * フィールドが数字のみであるか検証する。
	 * 全角半角問わず数値型であればtrueを返す。
	 * また、全角数字の場合は半角数字に変換した値をbeanにセットする。
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

		//プロパティ再セット（全角数字を半角数字に変換した値）
		try {
			value = StringUtil.toHankakuDigit(value);
			PropertyUtils.setProperty(bean, field.getProperty(), value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//文字列表現を検証する
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
	 * フィールドが半角ASCII文字のみであるか検証する。
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

		//文字列表現を検証する
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
	 * フィールドが全角文字のみであるか検証する。
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

		//文字列表現を検証する
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
	 * フィールドがフリガナのみであるか検証する。
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

		//文字列表現を検証する
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
	 * フィールドが正しい日付であるか検証する。
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
		
		//設定情報の取得とチェック		
		String dateProperties = field.getVarValue("dateProperties");
		if(dateProperties == null){
			throw new IllegalArgumentException("dateFieldsチェックでは、日付フィールド'dateProperties'を指定してください。");
		}
		String actionFlg = field.getVarValue("action");
		if(actionFlg == null){
			actionFlg = "ADD";
		}
				
		//チェックする項目を取得する。
		String[] datePropery = StringUtil.delimitedListToStringArray(dateProperties,",");

		//既に依存項目が、エラーである場合は処理しない。
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//チェックする項目
			if(checkExists(datePropery, element)) return true;
		}

		//フォームより該当データの取得
		String year = getString(bean, datePropery[0]);
		String month = getString(bean, datePropery[1]);
		String day = getString(bean, datePropery[2]);

		if(actionFlg.equals("ADD")){
			//登録処理時の場合は、依存項目が、未入力である場合は処理しない。
			if (GenericValidator.isBlankOrNull(year) || GenericValidator.isBlankOrNull(month) || GenericValidator.isBlankOrNull(day)) {
				return true;
			}
		}else if(actionFlg.equals("SEARCH")){
			//検索処理時の場合は、依存項目がすべて未入力である場合は処理しない。
			if (GenericValidator.isBlankOrNull(year) && GenericValidator.isBlankOrNull(month) && GenericValidator.isBlankOrNull(day)) {
				return true;
			}			
		}
		
		//日付妥当性チェック
		if (!StringUtil.isDate(year + "/" + month + "/" + day)) {
				errors.add(
					datePropery[0],
					Resources.getActionError(request, va, field));
			return false;
		}
		
		return true;
	}

	/**
	 * 日付の期間（○年○月○日～○年○月○日）が正しいかどうかを検証する。
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

		//設定情報の取得とチェック		
		String startDate = field.getVarValue("startDate");
		if(startDate == null){
			throw new IllegalArgumentException("startDateチェックでは、日付範囲フィールド'startDate'を指定してください。");
		}
		String endDate = field.getVarValue("endDate");
		if(endDate == null){
			throw new IllegalArgumentException("endDateチェックでは、日付範囲フィールド'endDate'を指定してください。");
		}
		//チェックする項目を取得する。
		String[] startDateArgs = StringUtil.delimitedListToStringArray(startDate,",");
		String[] endDateArgs = StringUtil.delimitedListToStringArray(endDate,",");		

		//既に依存項目が、エラーである場合は処理しない。
		//開始日
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//チェックする項目
			if(checkExists(startDateArgs, element)) return true;
		}
		//終了日
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//チェックする項目
			if(checkExists(endDateArgs, element)) return true;
		}
		//フォームより該当データの取得
		//開始日
		String startYear = getString(bean, startDateArgs[0]);
		String startMonth = getString(bean, startDateArgs[1]);
		String startDay = getString(bean, startDateArgs[2]);
		//終了日
		String endYear = getString(bean, endDateArgs[0]);
		String endMonth = getString(bean, endDateArgs[1]);
		String endDay = getString(bean, endDateArgs[2]);

		//依存項目が、未入力である場合は処理しない。
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
			//開始日＞終了日の場合
			errors.add(
			startDateArgs[0],
				Resources.getActionError(request, va, field));
			return false;
		}	
		
		return true;
	}

	/**
	 * 日付の期間（○年○月～○年○月）が正しいかどうかを検証する。
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

		//設定情報の取得とチェック		
		String startDate = field.getVarValue("startDate");
		if(startDate == null){
			throw new IllegalArgumentException("startDateチェックでは、日付範囲フィールド'startDate'を指定してください。");
		}
		String endDate = field.getVarValue("endDate");
		if(endDate == null){
			throw new IllegalArgumentException("endDateチェックでは、日付範囲フィールド'endDate'を指定してください。");
		}
		//チェックする項目を取得する。
		String[] startDateArgs = StringUtil.delimitedListToStringArray(startDate,",");
		String[] endDateArgs = StringUtil.delimitedListToStringArray(endDate,",");		

		//既に依存項目が、エラーである場合は処理しない。
		//開始日
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//チェックする項目
			if(checkExists(startDateArgs, element)) return true;
		}
		//終了日
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//チェックする項目
			if(checkExists(endDateArgs, element)) return true;
		}
		//フォームより該当データの取得
		//開始日
		String startYear = getString(bean, startDateArgs[0]);
		String startMonth = getString(bean, startDateArgs[1]);
		//String startDay = getString(bean, startDateArgs[2]);
		String startDay = "01";		//日に01を設定する

		//終了日
		String endYear = getString(bean, endDateArgs[0]);
		String endMonth = getString(bean, endDateArgs[1]);
		//String endDay = getString(bean, endDateArgs[2]);
		String endDay = "01";		//日に01を設定する

		//依存項目が、未入力である場合は処理しない。
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
			//開始日＞終了日の場合
			errors.add(
			startDateArgs[0],
				Resources.getActionError(request, va, field));
			return false;
		}	
		
		return true;
	}

	
	/**
	 * 依存項目のエラーをチェックする。
	 * 依存項目にエラーが合った場合は次のチェックの処理をしない。
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
		
		//依存するプロパティ
		String[] dependPropery = StringUtil.delimitedListToStringArray(field.getVarValue("dependProperties"),",");

		//既に依存項目が、エラーである場合は処理しない。
		for (Iterator iter = errors.properties(); iter.hasNext();) {
			String element = (String) iter.next();
			//依存する項目がエラーの場合は次のチェックしない。
			if(checkExists(dependPropery, element)) return false;
		}
		
		//次のチェックを行う。
		return true;
	}


	/**
	 * フィールドの拡張子が指定した拡張子であるか検証する。
	 * 選択できる拡張子を複数指定することが可能。
	 * 複数指定する場合は、extensionタグでカンマ「,」で区切って指定する。
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
		
		//**** 2004.9.27　拡張子の複数指定ができるように修正 ****
		//設定情報の取得とチェック		
		String extStr = field.getVarValue("extension");
		//文字列を配列に分割する
		String[] extensions = StringUtil.delimitedListToStringArray(extStr, ",");
		if(extensions == null || extensions.length == 0){
			throw new IllegalArgumentException("extensionチェックでは、拡張子フィールド'extension'を指定してください。");
		}
//		String extension = field.getVarValue("extension");
//		if(extension == null){
//			throw new IllegalArgumentException("extensionチェックでは、拡張子フィールド'extension'を指定してください。");
//		}

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = (String) ValidatorUtil.getValueAsString(bean, field.getProperty());
		}
		
		//ファイルが選択されている場合のみチェック
		boolean flg = false;
		if(value != null && value.length() != 0){
			int index = value.lastIndexOf(".");
			
			if(index < 0){
				//拡張子がなかったらエラー
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
				return flg;				
			}
			
			//拡張子分繰り返し
			for(int i = 0; i<extensions.length; i++){
				String ext = value.substring(index+1).toLowerCase();
				//拡張子チェック
				if(extensions[i].equals("*")){	
					flg = true;	//拡張子は指定なし
				}else if((extensions[i].toLowerCase()).equals(ext)){	
					flg = true;	//拡張子が正しい場合
				}
			}
			if(flg == false){
				//拡張子エラーがあったら
				errors.add(
					field.getKey(),
					Resources.getActionError(request, va, field));
					return flg;						
			}else{
				//拡張子エラーがなかったら
				return flg;						
			}
		}		
//		if(value != null && value.length() != 0){
//			int index = value.lastIndexOf(".");
//			if(index > -1){
//				String ext = value.substring(index+1).toLowerCase();
//				//拡張子チェック
//				if(extension.equals("*")){
//					return true;
//				}else if(!(extension.toLowerCase()).equals(ext)){
//					errors.add(
//						field.getKey(),
//						Resources.getActionError(request, va, field));
//					return false;		
//				}
//			}else{
//				//拡張子がなかったらエラー
//				errors.add(
//					field.getKey(),
//					Resources.getActionError(request, va, field));
//				return false;
//			}
//		}		
		return true;
	}

	/**
	 * フィールドのファイルサイズを検証する。
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
        
		//設定情報の取得とチェック		
		String maxFileSize = field.getVarValue("maxfilesize");
		if(maxFileSize == null){
			throw new IllegalArgumentException("maxfilesizeチェックでは、拡張子フィールド'maxfilesize'を指定してください。");
		}
		
		int maxsize = 0;		
		//M単位をバイト単位に変換
		int index = maxFileSize.lastIndexOf("M");
		if(index > -1){
			maxsize = Integer.parseInt(maxFileSize.substring(0, index));
			maxsize = maxsize*1048576;			
		}else{
			//それ以外は変換しない
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
	 * ファイルの必須を検証する。（0バイトはエラーとする）
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
	 * プロパティ名の値が○○だったらファイル必須チェックをする。
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

		//チェックする。
		if (required) {
			return validateRequiredFile(bean, va, field, errors, request);
		}

		//チェックしない。
		return false;
	}			 


	/**
	 * 配列の中にに要素が含まれているかチェックする。
	 * @param datePropery
	 * @param element
	 * @return 存在するとき　true しないとき　false
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
	 * オブジェクトより、フィールドの値を文字列で取得する。
	 * @param bean			チェックオブジェクト
	 * @param field			取得するフィールド名
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
	 * プロパティ名の値が○○だったらチェックをする。
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

			//NOT EQUALチェック　2005/07/07追加
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

		//チェックする。
		if (required) {
			return true;
		}

		//チェックしない。
		return false;
	}			 
		
	
	
	/**
	 * 指定されたリストプロパティが空かどうかをチェックする。
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

		//プロパティの取得
		Object value = null;
		try {
			value = PropertyUtils.getProperty(bean, field.getProperty());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		//取得したプロパティがリストのとき。
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
	 * フィールドの最小文字列バイト長のチェックを行う。
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

		//文字列表現を検証する
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
	 * フィールドが最大値（整数）以内であるか検証する。
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

		//最大値チェック
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
	 * フィールドの値と一致しないかを検証する。
	 * 複数指定する場合は、カンマ「,」で区切って指定する。
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
		
		//設定情報の取得とチェック		
		String extStr = field.getVarValue("notcontain");
		//文字列を配列に分割する
		String[] values = StringUtil.delimitedListToStringArray(extStr, ",");
		if(values == null || values.length == 0){
			throw new IllegalArgumentException("notcontainチェックでは、拡張子フィールド'notcontain'を指定してください。");
		}
		
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = (String) ValidatorUtil.getValueAsString(bean, field.getProperty());
		}

		for(int i = 0; i < values.length; i++) {
			//一致したらエラー
			if(values[i].equals(value)) {
				errors.add(field.getKey(),Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}

	
	
}
