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
package jp.go.jsps.kaken.model.common;

import java.io.*;
import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.collections.*;

/**
 * 設定ファイルクラス。リソースバンドルより設定情報を取得する。
 * 
 * ID RCSfile="$RCSfile: ApplicationSettings.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class ApplicationSettings {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** リソースバンドル */
	private static final ResourceBundle RESOURCE_BUNDLE =
		ResourceBundle.getBundle(ISettingKeys.BUNDLE_NAME);
	
	/** 動的項目パラメータ名のList */
	private static FastArrayList paramList    = null;
	
	/** 動的項目の値のList */
	private static FastArrayList variableList = null;
	
	
	//---------------------------------------------------------------------
	// Static initialize
	//---------------------------------------------------------------------
	static{
		paramList    = new FastArrayList();
		variableList = new FastArrayList();
		for(int i=0;;i++){
			try{
				//動的項目パラメータ名
				String keyParam   = ISettingKeys.VARIABLE_PARAM + "[" + i + "]";
				String valueParam = RESOURCE_BUNDLE.getString(keyParam); 
				//動的項目の値を取得
				String keyValue   = ISettingKeys.VARIABLE_VALUE + "[" + i + "]";
				String valueValue = RESOURCE_BUNDLE.getString(keyValue);
				//動的項目のパラメータ名と値をセット（両方セットされていれば）
				paramList.add(valueParam);
				variableList.add(StringUtil.defaultString(valueValue));
			}catch(MissingResourceException e){
				break;
			}
		}
		paramList.setFast(true);
		variableList.setFast(true);	
	}	
	
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * デフォルトコンストラクタ。
	 */
	private ApplicationSettings() {
	}
	
	
	/**
	 * リソースバンドルから指定のキーの値を取得する。
	 * 動的項目「VARIABLE_PARAM」「VARIABLE_VALUE」が存在した場合は置換する。
	 * @param key
	 * @return
	 */
	private static String getBundleString(String key){
		String value = RESOURCE_BUNDLE.getString(key);
		for(int i=0; i<paramList.size(); i++){
			String param    = (String)paramList.get(i);					//変換対象文字列
			String variable = (String)variableList.get(i);				//変換後文字列
			value = StringUtil.substrReplace(value, param, variable);	//変換処理
		}
		return value;
	}
	
	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * キーに該当するメッセージを取得する。
	 * @param key	キー
	 * @return	メッセージ
	 */
	public static String getString(String key) throws SystemException {
		return getString(key, true);
	}
	
	/**
	 * キーに該当するメッセージを取得する。
	 * checkが[true] の場合、キーに該当する値が存在しなかったら例外を返す。
	 * checkが[false]の場合、キーに該当する値が存在しなかったらnullを返す。
	 * @param key		キー
	 * @param check	キーに該当する値が存在するかのチェックフラグ
	 * @return			メッセージ
	 */
	public static String getString(String key, boolean check) throws SystemException {
		try {
			return getBundleString(key);
		} catch (MissingResourceException e) {
			if(check){
				throw new SystemException(
					"「ApplicationSettings.properties」に変数「" + key + "」が存在しません。",
					e);
			}else{
				return null;
			}
		}
	}

	/**
	 * メッセージキーに該当する値を数値で取得する。
	 * @param key	キー		
	 * @return		値
	 */
	public static Integer getInteger(String key) {
		try {
			return new Integer(getBundleString(key));
		} catch (NumberFormatException e) {
			throw new SystemException(
				"「ApplicationSettings.properties」の変数「" + key + "」が不正です。",
				e);
		}
	}
	
	/**
	 * メッセージキーに該当する値を数値で取得する。
	 * @param key	キー		
	 * @return		値
	 */
	public static int getInt(String key){
		return getInteger(key).intValue();
	}
	
	/**
	 * メッセージキーに該当する値をbooleanで取得する。
	 * @param key	キー		
	 * @return		値
	 */
	public static boolean getBoolean(String key) {
		return new Boolean(getBundleString(key)).booleanValue();
	}
	
	/**
	 * メッセージキーに該当するファイルオブジェクトを取得する。
	 * @param key	キー
	 * @return	ファイルオブジェクト
	 */
	public static File getFile(String key) throws SystemException {
		try {
			String value = getBundleString(key);
			return new File(value);
		} catch (MissingResourceException e) {
			throw new SystemException(
				"「ApplicationSettings.properties」に変数「" + key + "」が存在しません。",
				e);
		}
	}
	
	/**
	 * メッセージキーに該当する値を取得する。
	 * @param key	カンマ文字列で指定されたキー
	 * @return		文字列配列
	 */
	public static String[] getStrings(String key) {
		List list = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(getString(key), ",");
		while (tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	
	
	
}
