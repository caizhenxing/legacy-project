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
 * ���p�S�p�̃J�i���݂̔�r�e�[�u����char�̔z��( char[][][] )�̌`�Œ񋟂��܂��B
 * 1�����ڂ͔��p/�S�p�̊i�[���ꂽ�s�A2�����ڂ͔��p/�S�p�̕ʁA3�����ڂ͔��p����
 * 2����������( �����A������ )���߂ɔz��ɂȂ��Ă��܂��B
 * 
 * ID RCSfile="$RCSfile: HanZenTable.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class HanZenTable
{
    // statics ---------------------------------------------------------------
    /** ��r�e�[�u����2�����ڂŎg�p���锼�p������\���萔�ł��B
     * HANZEN_TABLE[ row number ][ IDX_HANKAKU ] �̂悤�Ɏg�p���܂��B*/
    public static final int IDX_HANKAKU = 0;

    /** ��r�e�[�u����2�����ڂŎg�p����S�p������\���萔�ł��B
     * HANZEN_TABLE[ row number ][ IDX_ZENKAKU ] �̂悤�Ɏg�p���܂��B*/
    public static final int IDX_ZENKAKU = 1;

    /** ���p�������L�̒萔�ŁA�����ȊO�̕�����\���܂�( �܂�ꕶ���� )�B
	HANZEN_TABLE[ row number ][ HANKAKU ][ IDX_HAN_BASE ]�̂悤�Ɏg�p����
	���B*/
    public static final int IDX_HAN_BASE = 0;

    /** ���p�������L�̒萔�ŁA����������\���܂�( �܂�񕶎��� )�B
	HANZEN_TABLE[ row number ][ HANKAKU ][ IDX_HAN_DAKUON ]�̂悤�Ɏg�p����
	���B���݂��Ȃ��ꍇ������̂Œ��ӂ��K�v�ł��B*/
    public static final int IDX_HAN_DAKUON = 1;

    /** ���p - �S�p�ϊ��Ɏg�p����A���בւ��Asearch�p�̔�rclass�ł��B*/
    public static final Comparator HANZEN_COMPARATOR
	= new HankakuComparator();

    /** �S�p - ���p�ϊ��Ɏg�p����A���בւ��Asearch�p�̔�rclass�ł��B*/
    public static final Comparator ZENHAN_COMPARATOR
	= new ZenkakuComparator();


    /** ���p - �S�p�ϊ��p�e�[�u���ł��B*/
    protected static final char[][][] HANZEN_TABLE;

    /** �S�p - ���p�ϊ��p�e�[�u���ł��B*/
    protected static final char[][][] ZENHAN_TABLE;
    static {
	char[][][] table = new char[][][] 
	{
	    {{'�'}, {'�B'}},
	    {{'�'}, {'�u'}},
	    {{'�'}, {'�v'}},
	    {{'�'}, {'�A'}},
	    {{'�'}, {'�E'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'�@'}},
	    {{'�'}, {'�B'}},
	    {{'�'}, {'�D'}},
	    {{'�'}, {'�F'}},
	    {{'�'}, {'�H'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'�b'}},
	    {{'�'}, {'�['}},
	    {{'�'}, {'�A'}},
	    {{'�'}, {'�C'}},
	    {{'�'}, {'�E'}}, {{'�', '�'}, {'��'}},
	    {{'�'}, {'�G'}},
	    {{'�'}, {'�I'}},
	    {{'�'}, {'�J'}}, {{'�', '�'}, {'�K'}},
	    {{'�'}, {'�L'}}, {{'�', '�'}, {'�M'}},
	    {{'�'}, {'�N'}}, {{'�', '�'}, {'�O'}},
	    {{'�'}, {'�P'}}, {{'�', '�'}, {'�Q'}},
	    {{'�'}, {'�R'}}, {{'�', '�'}, {'�S'}},
	    {{'�'}, {'�T'}}, {{'�', '�'}, {'�U'}},
	    {{'�'}, {'�V'}}, {{'�', '�'}, {'�W'}},
	    {{'�'}, {'�X'}}, {{'�', '�'}, {'�Y'}},
	    {{'�'}, {'�Z'}}, {{'�', '�'}, {'�['}},
	    {{'�'}, {'�\'}}, {{'�', '�'}, {'�]'}},
	    {{'�'}, {'�^'}}, {{'�', '�'}, {'�_'}},
	    {{'�'}, {'�`'}}, {{'�', '�'}, {'�a'}},
	    {{'�'}, {'�c'}}, {{'�', '�'}, {'�d'}},
	    {{'�'}, {'�e'}}, {{'�', '�'}, {'�f'}},
	    {{'�'}, {'�g'}}, {{'�', '�'}, {'�h'}},
	    {{'�'}, {'�i'}},
	    {{'�'}, {'�j'}},
	    {{'�'}, {'�k'}},
	    {{'�'}, {'�l'}},
	    {{'�'}, {'�m'}},
	    {{'�'}, {'�n'}}, {{'�', '�'}, {'�o'}}, {{'�', '�'}, {'�p'}},
	    {{'�'}, {'�q'}}, {{'�', '�'}, {'�r'}}, {{'�', '�'}, {'�s'}},
	    {{'�'}, {'�t'}}, {{'�', '�'}, {'�u'}}, {{'�', '�'}, {'�v'}},
	    {{'�'}, {'�w'}}, {{'�', '�'}, {'�x'}}, {{'�', '�'}, {'�y'}},
	    {{'�'}, {'�z'}}, {{'�', '�'}, {'�{'}}, {{'�', '�'}, {'�|'}},
	    {{'�'}, {'�}'}},
	    {{'�'}, {'�~'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'�J'}},
	    {{'�'}, {'�K'}}
	};
	char[][][] hanzen = (char[][][]) table.clone();
	Arrays.sort( (Object[]) hanzen, HANZEN_COMPARATOR );
	HANZEN_TABLE = hanzen;

	char[][][] zenAdd = new char[][][]
	{
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}},
	    {{'�'}, {'��'}}
	};

	char[][][] zenhan = new char[ table.length + zenAdd.length ][][];
	System.arraycopy( table, 0, zenhan, 0, table.length );
	System.arraycopy( zenAdd, 0, zenhan, table.length, zenAdd.length );
	Arrays.sort( (Object[]) zenhan, ZENHAN_COMPARATOR );
	ZENHAN_TABLE = zenhan;
    }

    
    // constructors ----------------------------------------------------------
    /**
     * instance���͍s��Ȃ��̂�private constructor�ł��B
     */
    private HanZenTable() {}


    // inner classes ---------------------------------------------------------
    /**
     * HankakuComparator �� ZenkakuComparator�̋��ʕ������`���Ă���܂��B
     * �o����class�̍���getCharArray�őS�p�A���p�̂ǂ���̔z���Ԃ����Ō���
     * �����̂ł���abstract method�����ꂼ���class�Œ�`���܂��B
     */
    private static abstract class BaseComparator
	implements Comparator
    {
	// constructors ------------------------------------------------------
	/**
	 * ���ɉ������܂���B
	 */
	BaseComparator() {}


	// Comparator implementation -----------------------------------------
	/**
	 * x��y���r���܂��Bx, y �Ƃ���abstract getCharArray�̒�`�ɂ��
	 * char�����o����ꍇ�ɔ�r���\�ł��B����ȊO�̏ꍇ�ɂ�
	 * ClassCastException��throw����܂��B
	 */
	public int compare( Object x, 
			    Object y )
	{
	    return compare( getCharArray( x ), getCharArray( y ) );
	}


	// public methods ----------------------------------------------------
	/**
	 * x��y���r���܂��B
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
	 * ���̃��\�b�h�� o ��char[]�ɉ��߂��镔�����`���ĉ������B
	 * �����Ă���object��char[]��char[][]��z�肵�Ă��܂��B
	 * ( �e�[�u����search������sort�����肷��Ƃ��ɂ�char[][]�����܂� )
	 */
	protected abstract char[] getCharArray( Object o );
    }


    /**
     * ����class��equals�Ƃ̈�ѐ��������܂���BHANZEN_TABLE�̔��pchar[]��
     * �S�pchar[]��ϊ����邽�߂ɂ����g�p���܂��B
     */
    private static class HankakuComparator
	extends BaseComparator
    {
	// constructors ------------------------------------------------------
	/**
	 * ���ɉ������܂���B
	 */
	HankakuComparator() {}


	// over rides --------------------------------------------------------
	/**
	 * object o����char[]�����o���Ԃ��܂��B
	 * o��char[][]�̎���HANKAKU_TABLE�̈�s�Ƃ݂Ȃ���
	 * ((char[][]) o)[ IDX_HANKAKU ]��Ԃ��܂��Bchar[]�̎��̓L���X�g����
	 * ���̂܂ܕԂ��܂�( ��r���Ƃ��ĉ��� )�B
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
     * ����class��equals�Ƃ̈�ѐ��������܂���BZENKAKU_TABLE�̑S�pchar[]��
     * ���pchar[]��ϊ����邽�߂ɂ����g�p���܂��B
     */
    private static class ZenkakuComparator
	extends BaseComparator
    {
	// constructors ------------------------------------------------------
	/**
	 * ���ɉ������܂���B
	 */
	ZenkakuComparator() {}


	// over rides --------------------------------------------------------
	/**
	 * object o����char[]�����o���Ԃ��܂��B
	 * o��char[][]�̎���ZENKAKU_TABLE�̈�s�Ƃ݂Ȃ���
	 * ((char[][]) o)[ IDX_ZENKAKU ]��Ԃ��܂��Bchar[]�̎��̓L���X�g����
	 * ���̂܂ܕԂ��܂�( ��r���Ƃ��ĉ��� )�B
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
