/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 値オブジェクトであることを示すクラス。
 * 
 * ID RCSfile="$RCSfile: ValueObject.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class ValueObject implements Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -2592182194435673611L;

	/**  ログ  */
	protected static Log log = LogFactory.getLog(ValueObject.class);

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

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
}
