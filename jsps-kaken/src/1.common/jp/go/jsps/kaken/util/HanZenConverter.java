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
 * ���p�J�i��S�p�J�i�ɕϊ������������Ԃ����߂�class�ł��B�����A��������1 �����ɂ܂Ƃ߂��܂��B
 * 
 * ID RCSfile="$RCSfile: HanZenConverter.java,v $" Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class HanZenConverter {
	// statics ---------------------------------------------------------------
	/** ���p�J�i��unicode�}�b�v��ł̍ŏ���( ��Ԑ��l�Ƃ��ď����� )�����ł��B */
	public static final int HANKANA_FIRST = 0xff61;

	/** ���p�J�i��unicode�}�b�v��ł̍Ō��( ��Ԑ��l�Ƃ��đ傫�� )�����ł��B */
	public static final int HANKANA_LAST = 0xff9f;

	//���pASCII�����ɑΉ�����S�p����
	static final String ZENKAKU = "�@�I�h���������f�i�j���{�C�|�D�^�O�P�Q�R�S�T�U�V�W�X�F�G�������H��"
							+ "�`�a�b�c�d�e�f�g�h�i�j�k�l�m�n�o�p�q�r�s�t�u�v�w�x�y�k���l�O�Q�M"
							+ "�����������������������������������������������������o�b�p�`�E";
	//���pASCII�����̍ŏ��̕���
	static final int ASCII_CODE_START = 32;
	//���pASCII�����̍Ō�̕���
	static final int ASCII_CODE_END = 127;
	
	// constructors ----------------------------------------------------------
	/**
	 * ���ɉ������܂���B
	 */
	public HanZenConverter() {
	}

	// public methods --------------------------------------------------------
	/**
	 * val�̒��̔��p�J�i��S�p�J�i�ɕϊ������������Ԃ��܂��B
	 */
	public String convert(String val) {
		if (val == null)
			return null;

		return _convert(val, 0, val.length());
	}

	/**
	 * val�̒��̔��p������S�p�ɕϊ������������Ԃ��܂��B
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
			
			//���pASCII�������ł��邩�`�F�b�N
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
				if (nextChar == '�' || nextChar == '�') {
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
	 * �w�肳�ꂽchr�����p�J�i�ł����true���������܂��B�������I�[�o�[���C�h ���Ĕ��p�J�i�ȊO�̔��p�ɂ�true��Ԃ��悤�ɂ��āA
	 * HanZenTable.HANZEN_TABLE�ɑΉ��\��t��������΁A�S�Ă̔��p������S�p�� �ϊ��ł��܂��B
	 */
	protected boolean isHankaku(char chr) {
		return (HANKANA_FIRST <= chr && chr <= HANKANA_LAST);
	}

	// private methods -------------------------------------------------------
	/**
	 * main�̔z���mainPos�̈ʒu����parts�̒l���㏑�����܂��B
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
	 * val�̒��̕�������pos�ȍ~�̕�������length�͈̔͂�isHankaku��true��Ԃ� ������S�p�����ɕϊ����ĐV�����������Ԃ��܂��B
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
				if (nextChar == '�' || nextChar == '�') {
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
	 * chars�Ŏw�肳�ꂽ�����z���sourceTable�̕\���g�p���đS�p�����֕ϊ����� ���Bchars�ɂ�1 part�̕�����(
	 * �ʏ��length == 1, �����A�������Ȃ�� length == 2�ɂȂ�܂��B )���w�肵�܂��B�����A�w�肳�ꂽchars���ϊ�table
	 * �ŕϊ��o���Ȃ��Ȃ�΂��̂܂ܓ����z���Ԃ��܂��B
	 * <p> ( ex ) �ނ͕ϊ��o���Ȃ��̂Ŋe�����ɕ����ĕϊ����܂��B
	 * </p>
	 * chars��length�� 0 < length < 3�ł��B
	 * sourceTable��HanZenTable.HANZEN_TABLE���Acomparator��
	 * HanZenTable.HANZEN_COMPARATOR���w�肵�܂��B
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
