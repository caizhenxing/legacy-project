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
 * 全角カナを半角カナに変換した文字列を返すためのclassです。濁音、半濁音は2
 * 文字に分割されます。 
 * 
 * ID RCSfile="$RCSfile: ZenHanConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:43 $"
 */
public class ZenHanConverter
{
    // statics ---------------------------------------------------------------
    /** 全角カナのunicodeマップ上での最初の( 一番数値として小さい )文字です。*/
    public static final int ZENKANA_FIRST = (int) 'ァ';

    /** 全角カナのunicodeマップ上での最初の( 一番数値として大きい )文字です。*/
    public static final int ZENKANA_LAST = (int) 'ヶ';


    // constructors ----------------------------------------------------------
    /**
     * 特に何もしません。
     */
    public ZenHanConverter() {}


    // public methods --------------------------------------------------------
    /**
     * valの中の全角カナを半角カナに変換した文字列を返します。
     */
    public String convert( String val )
    {
	if ( val == null )
	    return null;

	return _convert( val, 0, val.length() );
    }


    // protected methods -----------------------------------------------------
    /**
     * 指定されたchrが全角カナであればtrueをかえします。ここをオーバーライド
     * して全角カナ以外の( 変換可能な )全角にもtrueを返すようにして、
     * ZenHanTable.ZENHAN_TABLEに対応表を付け加えれば、全ての全角文字を半角に
     * 変換できます。
     */
    protected boolean isZenkaku( char chr )
    {
	return (  ZENKANA_FIRST <= chr && chr <= ZENKANA_LAST );
    }


    // private methods -------------------------------------------------------
    /**
     *
     */
    private int _putChars( char[] main,
			   int    mainPos,
			   char[] parts )
    {
	int pos = mainPos;
	for ( int i = 0; i < parts.length; i++ ) {
	    if ( parts[ i ] != (char) 0 )
		 main[ pos++ ] = parts[ i ];
	}
	return pos - mainPos;
    }


    /**
     * valの中の文字中のpos以降の文字からlengthの範囲でisZenkakuがtrueを返す
     * 文字を半角文字に変換して新しい文字列を返します。
     */
    private String _convert( String val,
			     int    pos,
			     int    length )
    {
	char[][][] table = HanZenTable.ZENHAN_TABLE;
	Comparator comp  = HanZenTable.ZENHAN_COMPARATOR;

	int    retIdx = 0;
	int    maxPos = pos + length;
	char[] base   = new char[ 1 ];
	char[] res;

	char[] nChars = val.toCharArray();
	char[] nRets  = new char[ length * 2 ];

	while ( pos < maxPos ) {
	    char currChar = nChars[ pos++ ];
	    if ( !isZenkaku( currChar ) ) {
		nRets[ retIdx++ ] = currChar;
		continue;
	    }

	    base[ 0 ] = currChar;
	    res = _convertPart( base, table, comp );
	    retIdx += _putChars( nRets, retIdx, res );
	}
	return new String( nRets, 0, retIdx );
    }

    /**
     * charsで指定された文字配列をsourceTableの表を使用して半角文字へ変換しま
     * す。charsには変換したい1文字を指定します。もし、指定されたcharsが変換
     * tableで変換出来ないならばそのまま同じ配列を返します。
     * charsのlengthは 0 < length < 2です( 全角 - 半角変換 )。
     * sourceTableはHanZenTable.ZENHAN_TABLEを、comparatorは
     * HanZenTable.ZENHAN_COMPARATORを指定します。
     */
    private char[] _convertPart( char[]     chars,
				 char[][][] sourceTable,
				 Comparator comparator )
    {
	int index = Arrays
	    .binarySearch( (Object[]) sourceTable, chars, comparator );

	if ( index >= 0 )
	    // succeed convert.
	    return sourceTable[ index ][ HanZenTable.IDX_HANKAKU ];
	else
	    // can't convert more.
	    return chars;
    }
}
