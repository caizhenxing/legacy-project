/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.util;

import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.util.StringUtil;

/**
 * 文字列に含まれるHTML特殊文字をエスケープ変換するクラス。
 * ID RCSfile="$RCSfile: Escape.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/06/29 09:40:13 $"
 */
public class Escape {

	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * @param aObject
	 * @return	変換後オブジェクト
	 */
	public static Object getText(Object aObject) {
		if (aObject == null)
			return "";
		if (aObject instanceof String) {
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}

	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * @param aObject
	 * @return	変換後オブジェクト
	 */
	public static Object getNullToSpace(Object aObject) {
		if (aObject == null)
			return " ";
		if (aObject instanceof String) {
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}
	
    /**
     * URI文字列の指定文字を変換するメソッド
     * @param aUrl
     * @param fromString
     * @param toString
     * @return
     */
    public static String replace(String aUrl,String fromString,String toString) {
        return aUrl.replaceFirst(fromString,toString);  
    }

	
	/**
	 * 文字列を数値形式に変換するメソッド
	 * 数値に変換できない場合は0を戻す。
	 * @param s
	 * @return
	 */
	public static int getIntValue(String s){
		int retValue = 0;
		try{
			if(s != null){
				retValue = Integer.parseInt(s);
			}
		}catch(NumberFormatException e){
			//何もしない
		}
		return retValue;
	}
	

	//2005.11.01 iso 追加
	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * 空の場合、警告メッセージに変換。
	 * @param aObject
	 * @return	変換後オブジェクト
	 */
	public static Object getLabel(Object aObject) {
		return getLabel(aObject, "<font color=\"#ff0000\"><b>未入力です</b></font>");
	}

	//2005.11.01 iso 追加
	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * 空の場合、警告メッセージに変換。
	 * @param aObject
	 * @param message
	 * @return	変換後オブジェクト
	 */
	public static Object getLabel(Object aObject, String message) {
		if (aObject == null) {
			return "";
		}
		if (aObject instanceof String) {
			if(aObject.toString().equals("")) {
				return message;
			}
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}

	//2005.11.11 iso 追加
	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * Listに第3引数の値が含まれていてて(その他の時)で空の場合、警告メッセージに変換。
	 * @param リスト
	 * @param aObject
	 * @param その他の値
	 * @return	変換後オブジェクト
	 */
	public static Object getText(List labelList, Object aObject, String other) {
		return getText(labelList, aObject, other, "<font color=\"#ff0000\"><b>未入力です</b></font>");
	}

	//2005.11.11 iso 追加
	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * Listに第3引数の値が含まれていてて(その他の時)で空の場合、警告メッセージに変換。
	 * @param リスト
	 * @param aObject
	 * @param message
	 * @param その他の値
	 * @return	変換後オブジェクト
	 */
	public static Object getText(List labelList, Object aObject, String other, String message) {
		if(labelList.isEmpty() || StringUtil.isBlank(other)) {
			return "";
		}
		for(Iterator ite = labelList.iterator(); ite.hasNext();) {
			String labelValue = ite.next().toString();
			if(labelValue.startsWith(other)) {
				return getLabel(aObject, message);
			}
		}
		return getText(aObject);
	}
	
	/**
	 * 文字列に含まれるHTML特殊文字をエスケープ変換するメソッド
	 * 審査意見表示専用、最大150文字まで
	 * @param aObject
	 * @return	変換後オブジェクト
	 */
	public static Object getText150(Object aObject) {
		if (aObject == null)
			return "";
		if (aObject instanceof String) {
			String str = org.apache.velocity.anakia.Escape.getText((String) aObject);
			if (str.length() > 150){
				str = str.substring(0,150) + "～･･･";
			}
			return str;
		}
		return aObject;
	}
	
	/**
	 * パラメータの左右空白(全角と半角両方)を除去し未入力かどうかを判定する。
	 * @param text 判定する文字列
	 * @return 未入力かスペースのみであるとき true
	 */
	public static final boolean isBlank(final String text) {
		return (text == null || text.length() == 0);
	}

}
