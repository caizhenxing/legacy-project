/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * 検証フォームの基本となるクラス。
 * 
 * ID RCSfile="$RCSfile: BaseValidatorForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class BaseValidatorForm extends ValidatorForm {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	protected static Log log = LogFactory.getLog(BaseValidatorForm.class);

	/**
	 * 新規登録アクション。 
	 */
	public static final String ADD_ACTION = "add";

	/**
	 * 修正アクション。
	 */
	public static final String EDIT_ACTION = "edit";

	/**
	 * 削除アクション。
	 */
	public static final String DELETE_ACTION = "delete";

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 属性情報 */
	private Map dto = new HashMap();
	
	/** アクション。 */
	private String action;

	/** 遷移元ページ*/
	private String source;
	
	/** 遷移先ページ*/
	private String destination;

	/** 組み合わせチェックで使用するダミーフィールド*/
	private String combination;
			
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public BaseValidatorForm() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		//super.reset(mapping, request);
//		action=ADD_ACTION;
		dto.clear();
	}

	/* 
	 * 入力チェック。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//スーパークラスの処理を呼び出す。 
		ActionErrors errors = super.validate(mapping, request);
		return errors;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer results = new StringBuffer();
		results.append(getClassAsString(getClass().getSuperclass()));
		results.append(getClassAsString(getClass()));
		return results.toString();
	}

	/**
	 * 定義された情報を文字列として表示する。
	 * @param clazz	　デバック用文字列を表示するための文字列	
	 * @return	デバッグ用メッセージ
	 */
	public StringBuffer getClassAsString(Class clazz) {
		StringBuffer results = new StringBuffer();
		results.append(getClass().getName() + "\n");

		Field[] fields = clazz.getDeclaredFields();
		try {
			AccessibleObject.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				results.append(
					"\t"
						+ fields[i].getName()
						+ "="
						+ fields[i].get(this)
						+ "\n");
			}
		} catch (Exception e) {
			log.error(e);
		}

		return results;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * DTOマップを取得する。
	 * @return	Dtoマップ	
	 */
	public Map getDto() {
		return dto;
	}

	/**
	 * DTOマップのキーに該当する値を取得する。
	 * @param key	キー
	 * @return		値
	 */
	public Object getDto(String key) {
		return dto.get(key);
	}

	/**
	 * DTOマップをセットする。
	 * @param map	DTOマップ
	 */
	public void setDto(Map map) {
		dto = map;
	}

	/**
	 * DTOマップに値をセットする
	 * @param key		キー値
	 * @param value		値
	 */
	public void setDto(String key, Object value) {
		dto.put(key,value);
	}

	/**
	 * アクション属性を取得する。
	 * @return	アクション名
	 */
	public String getAction() {
		return action;
	}

	/**
	 * アクション属性をセットする。
	 * @param string	アクション名
	 */
	public void setAction(String string) {
		action = string;
	}
	/**
	 * @return
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param string
	 */
	public void setDestination(String string) {
		destination = string;
	}

	/**
	 * @param string
	 */
	public void setSource(String string) {
		source = string;
	}

	/**
	 * @return
	 */
	public String getCombination() {
		return combination;
	}

	/**
	 * @param string
	 */
	public void setCombination(String string) {
		combination = string;
	}

}
