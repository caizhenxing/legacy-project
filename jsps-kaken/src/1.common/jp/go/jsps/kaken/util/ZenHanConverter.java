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
 * �S�p�J�i�𔼊p�J�i�ɕϊ������������Ԃ����߂�class�ł��B�����A��������2
 * �����ɕ�������܂��B 
 * 
 * ID RCSfile="$RCSfile: ZenHanConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:43 $"
 */
public class ZenHanConverter
{
    // statics ---------------------------------------------------------------
    /** �S�p�J�i��unicode�}�b�v��ł̍ŏ���( ��Ԑ��l�Ƃ��ď����� )�����ł��B*/
    public static final int ZENKANA_FIRST = (int) '�@';

    /** �S�p�J�i��unicode�}�b�v��ł̍ŏ���( ��Ԑ��l�Ƃ��đ傫�� )�����ł��B*/
    public static final int ZENKANA_LAST = (int) '��';


    // constructors ----------------------------------------------------------
    /**
     * ���ɉ������܂���B
     */
    public ZenHanConverter() {}


    // public methods --------------------------------------------------------
    /**
     * val�̒��̑S�p�J�i�𔼊p�J�i�ɕϊ������������Ԃ��܂��B
     */
    public String convert( String val )
    {
	if ( val == null )
	    return null;

	return _convert( val, 0, val.length() );
    }


    // protected methods -----------------------------------------------------
    /**
     * �w�肳�ꂽchr���S�p�J�i�ł����true���������܂��B�������I�[�o�[���C�h
     * ���đS�p�J�i�ȊO��( �ϊ��\�� )�S�p�ɂ�true��Ԃ��悤�ɂ��āA
     * ZenHanTable.ZENHAN_TABLE�ɑΉ��\��t��������΁A�S�Ă̑S�p�����𔼊p��
     * �ϊ��ł��܂��B
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
     * val�̒��̕�������pos�ȍ~�̕�������length�͈̔͂�isZenkaku��true��Ԃ�
     * �����𔼊p�����ɕϊ����ĐV�����������Ԃ��܂��B
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
     * chars�Ŏw�肳�ꂽ�����z���sourceTable�̕\���g�p���Ĕ��p�����֕ϊ�����
     * ���Bchars�ɂ͕ϊ�������1�������w�肵�܂��B�����A�w�肳�ꂽchars���ϊ�
     * table�ŕϊ��o���Ȃ��Ȃ�΂��̂܂ܓ����z���Ԃ��܂��B
     * chars��length�� 0 < length < 2�ł�( �S�p - ���p�ϊ� )�B
     * sourceTable��HanZenTable.ZENHAN_TABLE���Acomparator��
     * HanZenTable.ZENHAN_COMPARATOR���w�肵�܂��B
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
