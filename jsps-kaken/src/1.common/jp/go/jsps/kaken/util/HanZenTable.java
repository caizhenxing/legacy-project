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
 * 半角全角のカナ相互の比較テーブルをcharの配列( char[][][] )の形で提供します。
 * 1次元目は半角/全角の格納された行、2次元目は半角/全角の別、3次元目は半角文字
 * 2文字を扱う( 濁音、半濁音 )ために配列になっています。
 * 
 * ID RCSfile="$RCSfile: HanZenTable.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class HanZenTable
{
    // statics ---------------------------------------------------------------
    /** 比較テーブルの2次元目で使用する半角文字を表す定数です。
     * HANZEN_TABLE[ row number ][ IDX_HANKAKU ] のように使用します。*/
    public static final int IDX_HANKAKU = 0;

    /** 比較テーブルの2次元目で使用する全角文字を表す定数です。
     * HANZEN_TABLE[ row number ][ IDX_ZENKAKU ] のように使用します。*/
    public static final int IDX_ZENKAKU = 1;

    /** 半角文字特有の定数で、濁音以外の部分を表します( つまり一文字目 )。
	HANZEN_TABLE[ row number ][ HANKAKU ][ IDX_HAN_BASE ]のように使用しま
	す。*/
    public static final int IDX_HAN_BASE = 0;

    /** 半角文字特有の定数で、濁音部分を表します( つまり二文字目 )。
	HANZEN_TABLE[ row number ][ HANKAKU ][ IDX_HAN_DAKUON ]のように使用しま
	す。存在しない場合もあるので注意が必要です。*/
    public static final int IDX_HAN_DAKUON = 1;

    /** 半角 - 全角変換に使用する、並べ替え、search用の比較classです。*/
    public static final Comparator HANZEN_COMPARATOR
	= new HankakuComparator();

    /** 全角 - 半角変換に使用する、並べ替え、search用の比較classです。*/
    public static final Comparator ZENHAN_COMPARATOR
	= new ZenkakuComparator();


    /** 半角 - 全角変換用テーブルです。*/
    protected static final char[][][] HANZEN_TABLE;

    /** 全角 - 半角変換用テーブルです。*/
    protected static final char[][][] ZENHAN_TABLE;
    static {
	char[][][] table = new char[][][] 
	{
	    {{'｡'}, {'。'}},
	    {{'｢'}, {'「'}},
	    {{'｣'}, {'」'}},
	    {{'､'}, {'、'}},
	    {{'･'}, {'・'}},
	    {{'ｦ'}, {'ヲ'}},
	    {{'ｧ'}, {'ァ'}},
	    {{'ｨ'}, {'ィ'}},
	    {{'ｩ'}, {'ゥ'}},
	    {{'ｪ'}, {'ェ'}},
	    {{'ｫ'}, {'ォ'}},
	    {{'ｬ'}, {'ャ'}},
	    {{'ｭ'}, {'ュ'}},
	    {{'ｮ'}, {'ョ'}},
	    {{'ｯ'}, {'ッ'}},
	    {{'ｰ'}, {'ー'}},
	    {{'ｱ'}, {'ア'}},
	    {{'ｲ'}, {'イ'}},
	    {{'ｳ'}, {'ウ'}}, {{'ｳ', 'ﾞ'}, {'ヴ'}},
	    {{'ｴ'}, {'エ'}},
	    {{'ｵ'}, {'オ'}},
	    {{'ｶ'}, {'カ'}}, {{'ｶ', 'ﾞ'}, {'ガ'}},
	    {{'ｷ'}, {'キ'}}, {{'ｷ', 'ﾞ'}, {'ギ'}},
	    {{'ｸ'}, {'ク'}}, {{'ｸ', 'ﾞ'}, {'グ'}},
	    {{'ｹ'}, {'ケ'}}, {{'ｹ', 'ﾞ'}, {'ゲ'}},
	    {{'ｺ'}, {'コ'}}, {{'ｺ', 'ﾞ'}, {'ゴ'}},
	    {{'ｻ'}, {'サ'}}, {{'ｻ', 'ﾞ'}, {'ザ'}},
	    {{'ｼ'}, {'シ'}}, {{'ｼ', 'ﾞ'}, {'ジ'}},
	    {{'ｽ'}, {'ス'}}, {{'ｽ', 'ﾞ'}, {'ズ'}},
	    {{'ｾ'}, {'セ'}}, {{'ｾ', 'ﾞ'}, {'ゼ'}},
	    {{'ｿ'}, {'ソ'}}, {{'ｿ', 'ﾞ'}, {'ゾ'}},
	    {{'ﾀ'}, {'タ'}}, {{'ﾀ', 'ﾞ'}, {'ダ'}},
	    {{'ﾁ'}, {'チ'}}, {{'ﾁ', 'ﾞ'}, {'ヂ'}},
	    {{'ﾂ'}, {'ツ'}}, {{'ﾂ', 'ﾞ'}, {'ヅ'}},
	    {{'ﾃ'}, {'テ'}}, {{'ﾃ', 'ﾞ'}, {'デ'}},
	    {{'ﾄ'}, {'ト'}}, {{'ﾄ', 'ﾞ'}, {'ド'}},
	    {{'ﾅ'}, {'ナ'}},
	    {{'ﾆ'}, {'ニ'}},
	    {{'ﾇ'}, {'ヌ'}},
	    {{'ﾈ'}, {'ネ'}},
	    {{'ﾉ'}, {'ノ'}},
	    {{'ﾊ'}, {'ハ'}}, {{'ﾊ', 'ﾞ'}, {'バ'}}, {{'ﾊ', 'ﾟ'}, {'パ'}},
	    {{'ﾋ'}, {'ヒ'}}, {{'ﾋ', 'ﾞ'}, {'ビ'}}, {{'ﾋ', 'ﾟ'}, {'ピ'}},
	    {{'ﾌ'}, {'フ'}}, {{'ﾌ', 'ﾞ'}, {'ブ'}}, {{'ﾌ', 'ﾟ'}, {'プ'}},
	    {{'ﾍ'}, {'ヘ'}}, {{'ﾍ', 'ﾞ'}, {'ベ'}}, {{'ﾍ', 'ﾟ'}, {'ペ'}},
	    {{'ﾎ'}, {'ホ'}}, {{'ﾎ', 'ﾞ'}, {'ボ'}}, {{'ﾎ', 'ﾟ'}, {'ポ'}},
	    {{'ﾏ'}, {'マ'}},
	    {{'ﾐ'}, {'ミ'}},
	    {{'ﾑ'}, {'ム'}},
	    {{'ﾒ'}, {'メ'}},
	    {{'ﾓ'}, {'モ'}},
	    {{'ﾔ'}, {'ヤ'}},
	    {{'ﾕ'}, {'ユ'}},
	    {{'ﾖ'}, {'ヨ'}},
	    {{'ﾗ'}, {'ラ'}},
	    {{'ﾘ'}, {'リ'}},
	    {{'ﾙ'}, {'ル'}},
	    {{'ﾚ'}, {'レ'}},
	    {{'ﾛ'}, {'ロ'}},
	    {{'ﾜ'}, {'ワ'}},
	    {{'ﾝ'}, {'ン'}},
	    {{'ﾞ'}, {'゛'}},
	    {{'ﾟ'}, {'゜'}}
	};
	char[][][] hanzen = (char[][][]) table.clone();
	Arrays.sort( (Object[]) hanzen, HANZEN_COMPARATOR );
	HANZEN_TABLE = hanzen;

	char[][][] zenAdd = new char[][][]
	{
	    {{'ｲ'}, {'ヰ'}},
	    {{'ｴ'}, {'ヱ'}},
	    {{'ｶ'}, {'ヵ'}},
	    {{'ｹ'}, {'ヶ'}}
	};

	char[][][] zenhan = new char[ table.length + zenAdd.length ][][];
	System.arraycopy( table, 0, zenhan, 0, table.length );
	System.arraycopy( zenAdd, 0, zenhan, table.length, zenAdd.length );
	Arrays.sort( (Object[]) zenhan, ZENHAN_COMPARATOR );
	ZENHAN_TABLE = zenhan;
    }

    
    // constructors ----------------------------------------------------------
    /**
     * instance化は行わないのでprivate constructorです。
     */
    private HanZenTable() {}


    // inner classes ---------------------------------------------------------
    /**
     * HankakuComparator と ZenkakuComparatorの共通部分を定義してあります。
     * 双方のclassの差はgetCharArrayで全角、半角のどちらの配列を返すかで決定
     * されるのでこのabstract methodをそれぞれのclassで定義します。
     */
    private static abstract class BaseComparator
	implements Comparator
    {
	// constructors ------------------------------------------------------
	/**
	 * 特に何もしません。
	 */
	BaseComparator() {}


	// Comparator implementation -----------------------------------------
	/**
	 * xとyを比較します。x, y ともにabstract getCharArrayの定義により
	 * charを取り出せる場合に比較が可能です。それ以外の場合には
	 * ClassCastExceptionがthrowされます。
	 */
	public int compare( Object x, 
			    Object y )
	{
	    return compare( getCharArray( x ), getCharArray( y ) );
	}


	// public methods ----------------------------------------------------
	/**
	 * xとyを比較します。
	 */
	public final int compare( char[] xChars,
				  char[] yChars )
	{
	    int max;
	    int xLen = xChars.length;
	    int yLen = yChars.length;

	    if ( xLen < yLen )
		max = xLen;
	    else
		max = yLen;

	    int ret = 0;
	    for ( int i = 0; i < max; i++ ) {
		if ( ( ret = xChars[ i ] - yChars[ i ] ) != 0 )
		    return ret;
	    }
	    return xLen- yLen;
	}


	// abstract methods --------------------------------------------------
	/**
	 * このメソッドで o をchar[]に解釈する部分を定義して下さい。
	 * 入ってくるobjectはchar[]かchar[][]を想定しています。
	 * ( テーブルをsearchしたりsortしたりするときにはchar[][]が来ます )
	 */
	protected abstract char[] getCharArray( Object o );
    }


    /**
     * このclassはequalsとの一貫性を持ちません。HANZEN_TABLEの半角char[]と
     * 全角char[]を変換するためにだけ使用します。
     */
    private static class HankakuComparator
	extends BaseComparator
    {
	// constructors ------------------------------------------------------
	/**
	 * 特に何もしません。
	 */
	HankakuComparator() {}


	// over rides --------------------------------------------------------
	/**
	 * object oからchar[]を取り出し返します。
	 * oがchar[][]の時はHANKAKU_TABLEの一行とみなして
	 * ((char[][]) o)[ IDX_HANKAKU ]を返します。char[]の時はキャストして
	 * そのまま返します( 比較側として解釈 )。
	 */
	protected char[] getCharArray( Object o )
	{
	    if ( o instanceof char[][] )
		return ((char[][]) o)[ IDX_HANKAKU ];
	    else
		return (char[]) o;
	}
    }


    /**
     * このclassはequalsとの一貫性を持ちません。ZENKAKU_TABLEの全角char[]と
     * 半角char[]を変換するためにだけ使用します。
     */
    private static class ZenkakuComparator
	extends BaseComparator
    {
	// constructors ------------------------------------------------------
	/**
	 * 特に何もしません。
	 */
	ZenkakuComparator() {}


	// over rides --------------------------------------------------------
	/**
	 * object oからchar[]を取り出し返します。
	 * oがchar[][]の時はZENKAKU_TABLEの一行とみなして
	 * ((char[][]) o)[ IDX_ZENKAKU ]を返します。char[]の時はキャストして
	 * そのまま返します( 比較側として解釈 )。
	 */
	protected char[] getCharArray( Object o )
	{
	    if ( o instanceof char[][] )
		return ((char[][]) o)[ IDX_ZENKAKU ];
	    else
		return (char[]) o;
	}
    }
}
