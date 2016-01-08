/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * チェック用ユーティリティクラス。
 * ID RCSfile="$RCSfile: StringUtil.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/11 09:36:24 $"
 */
public class StringUtil {
	
	private static final char[] HANKAKU_SUJI = {'0','1','2','3','4','5','6','7','8','9'};
	private static final char[] ZENKAKU_SUJI = {'０','１','２','３','４','５','６','７','８','９'};
	private static final String ZENKAKU_KIGO = "！”＃＄％＆’（）＊＋，－．／、。・";
	static final char ZEN_SPACE = '　';		//全角スペース
	
//ADD　START 2007/07/11 BIS 金京浩 小書き文字を通常のカタカナ(ァィゥェォャュョッヵヶヮ)  大文字 (アイウエオヤユヨツカケワ)
	private static final char[] KOGAKI_KATAKANA = {'ァ','ィ','ゥ','ェ','ォ','ャ','ュ','ョ','ッ','ヵ','ヶ','ヮ'};
	private static final char[] OOMOJI_KATAKANA = {'ア','イ','ウ','エ','オ','ヤ','ユ','ヨ','ツ','カ','ケ','ワ'};
	
	/**
	 * 指定の文字列の中から、小書き文字を大文字に変換する。
	 * それ以外はそのまま。
	 * @param s
	 * @return
	 */
	public static final String toOomojiDigit(String s){
		if(s == null){
			return null;
		}
		for(int i=0; i<KOGAKI_KATAKANA.length; i++){
			s = s.replace(KOGAKI_KATAKANA[i], OOMOJI_KATAKANA[i]);
		}
		return s;
	}
		
//ADD　START 2007/07/11 BIS 金京浩
	
	/**
	 * パラメータが半角文字かどうかを判定する。
	 * パラメータが半角英数、半角記号、半角カナのいずれかの場合は
	 * trueを返す。全角文字が含まれている場合はfalseを返す。
	 * パラメータがnullの場合はfalseを返す。
	 * @param text 判定する文字列
	 * @return 半角文字だけで構成されているとき true
	 */
	public static final boolean isHankaku(final String text) {
		if (text == null) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!isHankaku(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * パラメータが全角文字かどうかを判定する。
	 * パラメータが全角文字の場合はtrueを返す。
	 * 半角英数、半角記号、半角カナのいずれかが含まれている場合はfalseを返す。
	 * パラメータがnullの場合はfalseを返す。
	 * @param text 判定する文字列
	 * @return 全角文字だけで構成されているとき true
	 */
	public static final boolean isZenkaku(final String text) {
		if (text == null) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (isHankaku(c)) {
				return false;	//1文字でも半角が含まれていた場合false
			}
		}
		return true;
	}
	
	/**
	 * パラメータが半角文字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 半角文字のとき true
	 */
	public static final boolean isHankaku(final char c) {
		return isAscii(c) || isKana(c);
	}

	/**
	 * パラメータが半角英数字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 英数字のとき true
	 */
	public static final boolean isLetterOrDigit(final char c) {
		return isLetter(c) || isDigit(c);
	}
	
	//2005/04/28 追加 ここから--------------------------------------------
	//理由 半角英数字の文字列チェックの追加
	/**
	 * パラメータが半角英数字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 英数字のとき true
	 */
	public static final boolean isLetterOrDigit(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(!StringUtil.isLetterOrDigit(c[i])){
				return false;
			}			
		}
		return true;
	}
	//追加 ここまで-------------------------------------------------------

	/**
	 * パラメータが半角英字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 英字のとき true
	 */
	public static final boolean isLetter(final char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	/**
	 * パラメータが(全角または半角)カナかどうかを判定する。
	 * @param s 判定する文字
	 * @return カナのとき true
	 */
	public static final boolean isKana(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(isZenkakuKana(c[i]) || isKigo(c[i])){
				continue;
			}			
			return false;
		}
		return true;
	}
	
	/**
	 * パラメータが半角カナかどうかを判定する。
	 * @param c 判定する文字
	 * @return 半角カナのとき true
	 */
	private static final boolean isKana(final char c) {
		return c >= '\uff61' && c <= '\uff9f'; // ｡ ～ ﾟ
	}

	/**
	 * パラメータが全角カナかどうかを判定する。
	 * @param c 判定する文字
	 * @return 全角カナのとき true
	 */
	public static final boolean isZenkakuKana(final char c) {
        return c == 'ー' || (c >= '\u30a1' && c <= '\u30f6') || c == ZEN_SPACE;// ｡ ～ ﾟ
	}

	/**
	 * パラメータが全角記号かどうかを判定する。
	 * @param c 判定する文字
	 * @return　記号の時 true
	 */
	private static final boolean isKigo(final char c){
		int index = ZENKAKU_KIGO.indexOf(c);
		return index != -1;
	}
	
	/**
	 * パラメータが半角数字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 数字のとき true
	 */
	public static final boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * パラメータが半角数字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 数字のとき true
	 */
	public static final boolean isDigit(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(!StringUtil.isDigit(c[i])){
				return false;
			}			
		}
		return true;
	}
	
	/**
	 * パラメータが半角ASCII文字かどうかを判定する。
	 * @param c 判定する文字
	 * @return 半角文字のとき true
	 */
	public static final boolean isAscii(final char c) {
		return c < '\u0080';
	}

	/**
	 * パラメータが未入力かどうかを判定する。
	 * @param text 判定する文字列
	 * @return 未入力であるとき true
	 */
	public static final boolean isBlank(final String text) {
		return (text == null || text.length() == 0);
	}

	/**
	 * パラメータがyyyy/MM/ddかどうかを判定する。
 	 * @param text 判定する文字列
	 * @return 日付として正しい文字列で構成されているとき true
	 */
	public static final boolean isDate(final String text) {
		if (!new DateUtil().setCal(text, "/")) {
			return false;
		}
		return true;
	}
	
	/**
	 * delimiterで文字列を分割し、文字列配列を返す。
	 * @param aString			分割する文字列	
	 * @param delimiter			区切り文字
	 * @return					delimiterで文字列を分割た、配列
	 */
	public static String[] delimitedListToStringArray(String aString, String delimiter) {
		if (aString == null)
			return new String[0];
		if (delimiter == null)
			return new String[]{aString};

		List l = new LinkedList();
		int pos = 0;
		int delpos = 0;
		while ((delpos = aString.indexOf(delimiter, pos)) != -1) {
			l.add(aString.substring(pos, delpos));
			pos = delpos + delimiter.length();
		}
		if (pos <= aString.length()) {
			l.add(aString.substring(pos));
		}

		return (String[]) l.toArray(new String[l.size()]);
	}
	
	
	/******************************************************
	 * 文字列置換メソッド<!--.--><br>
	 * 文字列同士を再帰的に置換する。<br>
	 * @param  str   文字列
	 * @param  str1  置換対象文字列
	 * @param  str2  置換する文字列
	******************************************************/
	public static String substrReplace(String str, String str1, String str2) {
		
		int i = str.indexOf(str1);
		
		if (i==-1) {
			return str;
		}else {
			String retStr = new StringBuffer()
							.append(str.substring(0, i))
							.append(str2)
							.append(substrReplace(str.substring(i+str1.length()), str1, str2))
							.toString();
			return retStr;
		}
	}
	
	
	/**
	 * 文字列配列をCSV形式で返す。<br>
	 * 文字列配列が[null]の場合は、例外を発生させる。<br>
	 * 文字列配列要素数が[0]でSQLエスケープがfalseの場合は、空の文字列を返す。<br>
	 * 文字列配列要素数が[0]SQLエスケープがtrueの場合は、['']を返す。<br>
	 * @param array   CSVに変換する文字列要素
	 * @param escape  文字列に対してSQLエスケープを行う場合は[true]
	 * @throws IllegalArgumentException 文字列配列がnullの場合
	 * @return
	 */
	public static String changeArray2CSV(String[] array, boolean escape)
		throws IllegalArgumentException
	{
		if(array == null){
			throw new IllegalArgumentException("changeArray2CSV() Error occured. array="+array);
		}else if(array.length == 0){
			if(escape){
				return "''";
			}else{
				return "";
			}
		}
		
		String s = (escape ? EscapeUtil.toSqlString(array[0]) : array[0]);
		StringBuffer buf = new StringBuffer("'" + s + "'");
		for(int i=1; i<array.length; i++){
			String ss = (escape ? EscapeUtil.toSqlString(array[i]) : array[i]);
			buf.append(",'")
			   .append(ss)
			   .append("'");
		}
		return buf.toString();
		
	}
	
	
	
	/**
	 * 文字列反復子をCSV形式で返す。<br>
	 * 文字列配列要素数が[0]でSQLエスケープがfalseの場合は、空の文字列を返す。<br>
	 * 文字列配列要素数が[0]SQLエスケープがtrueの場合は、['']を返す。<br>
	 * @param iterator   CSVに変換する文字列反復子
	 * @param escape     文字列に対してSQLエスケープを行う場合は[true]
	 * @throws IllegalArgumentException 文字列反復子がnullの場合
	 * @return
	 */
	public static String changeIterator2CSV(Iterator iterator, boolean escape)
		throws IllegalArgumentException
	{
		if(iterator == null){
			throw new IllegalArgumentException("changeIterator2CSV() Error occured. iterator="+iterator);
		}else if(!iterator.hasNext()){
			if(escape){
				return "''";
			}else{
				return "";
			}
		}
		
		String s = (String)iterator.next();
		s = (escape ? EscapeUtil.toSqlString(s) : s);
		
		StringBuffer buf = new StringBuffer("'" + s + "'");
		while(iterator.hasNext()){
			String ss = (String)iterator.next();
			ss = (escape ? EscapeUtil.toSqlString(ss) : ss);
			buf.append(",'")
			   .append(ss)
			   .append("'");
		}
		return buf.toString();
		
	}	
	
	
	/**
	 * 文字列から数値へ変換する。
	 * 文字列がnull、または数値ではない文字列の場合は、数字の[0]を返す。
	 * @param s
	 * @return
	 */
	public static int parseInt(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return 0;
		}
	}
	
	
	
	/**
	 * 渡されたオブジェクトをチェックし、null だった場合には空の文字列を返します。
	 * nullではなかった場合、渡されたオブジェクトの文字列表現を返します。
	 * @param obj
	 * @return
	 */
	public static String defaultString(Object obj){
		return defaultString(obj, "");
	}
	
	
	
	/**
  	 * 渡されたオブジェクトをチェックし、null だった場合には
  	 * 第二引数のオブジェクトの文字列表現を返します。
  	 * 第二引数のオブジェクトも null だった場合は null を返します。
	 * @param obj
	 * @param defaultObject
	 * @return
	 */
	public static String defaultString(Object obj, Object defaultObject){
		if(obj == null){
			return (defaultObject == null) ? null : defaultObject.toString();
		}else{
			return obj.toString();
		}
	}	
	
	
	/**
	 * 指定の文字列の中から、全角数字を半角数字に変換する。
	 * それ以外はそのまま。
	 * @param s
	 * @return
	 */
	public static final String toHankakuDigit(String s){
		if(s == null){
			return null;
		}
		for(int i=0; i<ZENKAKU_SUJI.length; i++){
			s = s.replace(ZENKAKU_SUJI[i], HANKAKU_SUJI[i]);
		}
		return s;
	}
	
	
	
	/**
	 * 指定された個数の[?]マークをコンマで結びつけて返す。
	 * @param count
	 * @return
	 */
	public static String getQuestionMark(int count){
		return getQuestionMark(count, ",");
	}
	
	
	
	/**
	 * 指定された個数の[?]マークをdelimで結びつけて返す。
	 * @param count
	 * @param delim 区切り文字
	 * @return
	 */
	public static String getQuestionMark(int count, String delim){
		StringBuffer buf = new StringBuffer("?");
		for(int i=1; i<count; i++){
			buf.append(delim + "?");
		}
		return buf.toString();
	}	
	
	/**
	 * 空文字列であるかチェックする<br>
	 * 全角空白、半角空白も空文字とする
	 * @param  str   文字列
	 * @return true 文字列です
	*/
	public static boolean isSpaceString(String str) {

		if (str == null || str.length() == 0)
			return true;

		//空白を取り外す
		String retStr = str.replaceAll("　", "").trim();
		
		return retStr.length() == 0;
	}
	
	/**
	 * パラメータの左右空白を除去し未入力かどうかを判定する。
	 * @param text 判定する文字列
	 * @return 未入力かスペースのみであるとき true
	 */
	public static final boolean isEscapeBlank(final String text) {
		return (text == null || text.trim().length() == 0);
	}
	
	//2005.09.12 iso 組織表金額計算修正のため追加
	/**
	 * 文字列から数値(long型)へ変換する。
	 * 文字列がnull、または数値ではない文字列の場合は、数字の[0]を返す。
	 * @param s
	 * @return
	 */
	public static long parseLong(String s){
		try{
			return Long.parseLong(s);
		}catch(Exception e){
			return 0;
		}
	}
	
	//2005.11.02 iso 追加
	/**
	 * パラメータに未入力があるかどうかを判定する。
	 * @param texts 判定する文字配列
	 * @return 未入力であるとき true
	 */
	public static final boolean isBlank(final String[] texts) {
		if(texts == null) {
			return false;
		}
		for(int i = 0; i < texts.length; i++) {
			if(isBlank(texts[i])) {
				return true;
			}
		}
		return false;
	}

// 2006/02/10 Start	
	/**
	 * ゼロを加える（桁数で不足の場合は、先頭に"0"を加える）
	 *
	 * 引数がnullの場合はスペースを返却。
	 * @param str 文字列
	 * @param len 文字列桁数
	 * @return String　編集した文字列
	 */
	public static final String fillLZero(String str, int len){
		if (str == null) {
			str = "";
        } else {
			str = str.trim();
		}
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < len - str.length(); i++) {
        	buffer.append('0');
        }
        buffer.append(str);
        return buffer.toString();	
	}
// 苗 End
// 2006/06/29　苗　追加ここから
    /**
     * パラメータが半角大英文字かどうかを判定する。
     * @param c 判定する文字
     * @return 大文英字のとき true
     */
    public static final boolean isCapitalLetter(final char c) {
        return (c >= 'A' && c <= 'Z');
    }
    
    /**
     * パラメータがスペース文字かどうかを判定する。
     * @param c 判定する文字
     * @return スペース文字のとき true
     */
    public static final boolean isSpaceLetter(final char c) {
        return (c == ' ');
    }
//　2006/06/29　苗　追加ここまで

// 2006/07/21 dyh add start
    /**
     * nullから半角文字列（１Ｂｙｔｅ）へ変換する。
     * @param str
     * @param len
     * @return String
     */
    public static final String convertNullToHanSpace(String str){
        if(isBlank(str)){
            return " ";
        }
        return str;
    }
}