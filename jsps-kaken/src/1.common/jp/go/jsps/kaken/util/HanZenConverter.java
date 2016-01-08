/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 半角カナを全角カナに変換した文字列を返すためのclassです。濁音、半濁音は1 文字にまとめられます。
 * 
 * ID RCSfile="$RCSfile: HanZenConverter.java,v $" Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class HanZenConverter {
	// statics ---------------------------------------------------------------
	/** 半角カナのunicodeマップ上での最初の( 一番数値として小さい )文字です。 */
	public static final int HANKANA_FIRST = 0xff61;

	/** 半角カナのunicodeマップ上での最後の( 一番数値として大きい )文字です。 */
	public static final int HANKANA_LAST = 0xff9f;

	//半角ASCII文字に対応する全角文字
	static final String ZENKAKU = "　！”＃＄％＆’（）＊＋，－．／０１２３４５６７８９：；＜＝＞？＠"
							+ "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ〔￥〕＾＿｀"
							+ "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ｛｜｝～・";
	//半角ASCII文字の最初の文字
	static final int ASCII_CODE_START = 32;
	//半角ASCII文字の最後の文字
	static final int ASCII_CODE_END = 127;
	
	// constructors ----------------------------------------------------------
	/**
	 * 特に何もしません。
	 */
	public HanZenConverter() {
	}

	// public methods --------------------------------------------------------
	/**
	 * valの中の半角カナを全角カナに変換した文字列を返します。
	 */
	public String convert(String val) {
		if (val == null)
			return null;

		return _convert(val, 0, val.length());
	}

	/**
	 * valの中の半角文字を全角に変換した文字列を返します。
	 */
	public String convertAll(String val) {
		if (val == null)
			return null;

		char[][][] table = HanZenTable.HANZEN_TABLE;
		Comparator comp = HanZenTable.HANZEN_COMPARATOR;
		int pos = 0;
		int length = val.length();
		
		int retIdx = 0;
		int maxPos = pos + length;
		char[] base = new char[1];
		char[] daku = new char[2];
		char[] nChars = val.toCharArray();
		char[] nRets = new char[length];

		while (pos < maxPos) {
			char currChar = nChars[pos++];
			
			//半角ASCII可視文字であるかチェック
			if (ASCII_CODE_START <= currChar && currChar <= ASCII_CODE_END){
				nRets[retIdx++] = ZENKAKU.charAt( currChar - ASCII_CODE_START );
				continue;
			}
			else if (!isHankaku(currChar)) {
				nRets[retIdx++] = currChar;
				continue;
			}

			char[] res;
			if (pos < maxPos) {
				char nextChar = nChars[pos];
				if (nextChar == 'ﾞ' || nextChar == 'ﾟ') {
					pos++;
					daku[HanZenTable.IDX_HAN_BASE] = currChar;
					daku[HanZenTable.IDX_HAN_DAKUON] = nextChar;
					res = _convertPart(daku, table, comp);
					retIdx += _putChars(nRets, retIdx, res);
					continue;
				}
			}

			base[0] = currChar;
			res = _convertPart(base, table, comp);
			retIdx += _putChars(nRets, retIdx, res);
		}
		return new String(nRets, 0, retIdx);
	}
	
	// protected methods -----------------------------------------------------
	/**
	 * 指定されたchrが半角カナであればtrueをかえします。ここをオーバーライド して半角カナ以外の半角にもtrueを返すようにして、
	 * HanZenTable.HANZEN_TABLEに対応表を付け加えれば、全ての半角文字を全角に 変換できます。
	 */
	protected boolean isHankaku(char chr) {
		return (HANKANA_FIRST <= chr && chr <= HANKANA_LAST);
	}

	// private methods -------------------------------------------------------
	/**
	 * mainの配列のmainPosの位置からpartsの値を上書きします。
	 */
	private int _putChars(char[] main, int mainPos, char[] parts) {
		int pos = mainPos;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i] != (char) 0)
				main[pos++] = parts[i];
		}
		return pos - mainPos;
	}

	/**
	 * valの中の文字中のpos以降の文字からlengthの範囲でisHankakuがtrueを返す 文字を全角文字に変換して新しい文字列を返します。
	 */
	private String _convert(String val, int pos, int length) {
		char[][][] table = HanZenTable.HANZEN_TABLE;
		Comparator comp = HanZenTable.HANZEN_COMPARATOR;

		int retIdx = 0;
		int maxPos = pos + length;
		char[] base = new char[1];
		char[] daku = new char[2];
		char[] nChars = val.toCharArray();
		char[] nRets = new char[length];

		while (pos < maxPos) {
			char currChar = nChars[pos++];
			if (!isHankaku(currChar)) {
				nRets[retIdx++] = currChar;
				continue;
			}

			char[] res;
			if (pos < maxPos) {
				char nextChar = nChars[pos];
				if (nextChar == 'ﾞ' || nextChar == 'ﾟ') {
					pos++;
					daku[HanZenTable.IDX_HAN_BASE] = currChar;
					daku[HanZenTable.IDX_HAN_DAKUON] = nextChar;
					res = _convertPart(daku, table, comp);
					retIdx += _putChars(nRets, retIdx, res);
					continue;
				}
			}

			base[0] = currChar;
			res = _convertPart(base, table, comp);
			retIdx += _putChars(nRets, retIdx, res);
		}
		return new String(nRets, 0, retIdx);
	}

	/**
	 * charsで指定された文字配列をsourceTableの表を使用して全角文字へ変換しま す。charsには1 partの文字分(
	 * 通常はlength == 1, 濁音、半濁音ならば length == 2になります。 )を指定します。もし、指定されたcharsが変換table
	 * で変換出来ないならばそのまま同じ配列を返します。
	 * <p> ( ex ) ｱﾞは変換出来ないので各文字に分けて変換します。
	 * </p>
	 * charsのlengthは 0 < length < 3です。
	 * sourceTableはHanZenTable.HANZEN_TABLEを、comparatorは
	 * HanZenTable.HANZEN_COMPARATORを指定します。
	 */
	private char[] _convertPart(char[] chars, char[][][] sourceTable,
			Comparator comparator) {
		int index = Arrays.binarySearch((Object[]) sourceTable, chars,
				comparator);

		if (index >= 0)
			// succeed convert.
			return sourceTable[index][HanZenTable.IDX_ZENKAKU];
		else if (chars.length == 1)
			// can't convert more.
			return chars;

		int len = chars.length;
		for (int i = 0; i < len; i++) {
			if (chars[i] == (char) 0)
				continue;

			char[] atom = new char[] { chars[i] };
			chars[i] = _convertPart(atom, sourceTable, comparator)[0];
		}
		return chars;
	}
}
