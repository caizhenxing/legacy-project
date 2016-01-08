/*
 * @(#) $Id: ToISO2022JPCorrector.java,v 1.1 2007/06/28 02:06:44 administrator Exp $
 * $Revision: 1.1 $
 * Copyright (c) 2000 Shin Kinoshita All Rights Reserved.
 */
package jp.go.jsps.kaken.util;

/**
 * ToISO2022JPCorrector.
 * <p>
 * CorrectOutputStreamWriter�ŗp�����镶����o�b�t�@�␳�N���X�ł��B<br>
 * ���Ԉ�m�����JavaHouse-Brewers���e�L��[14452]��Cp932�N���X����
 * �ϊ��\���Q�l�ɂ����Ă��������Ă��܂��B<br>
 * </p>
 * @version 1.00
 * @author Shin
 */
public class ToISO2022JPCorrector extends UnicodeCorrector {
	/**
	 * Unicode�����z��̕␳���s���܂��B
	 * <p>
	 * ����̕�����"iso-2022-jp""SJIS"(jdk1.1.7)�G���R�[�f�B���O��
	 * �o�͂��悤�Ƃ����ۂ�sun.io��com.ms�R���o�[�^�ł�
	 * ����ɕϊ��ł��Ȃ�������␳���܂��B
	 * </p>
	 * @param  c	 source character
	 * @return Result character that corrected.
	 */
	public char correct(char c) {
		switch (c) {
			// iso-2022-jp�R���o�[�^�����������߂��Ă���悤��
//			case 0xff3c:		// FULLWIDTH REVERSE SOLIDUS ->
//				return 0x005c;	// REVERSE SOLIDUS
			case 0xff5e:		// FULLWIDTH TILDE ->
				return 0x301c;	// WAVE DASH
			case 0x2225:		// PARALLEL TO ->
				return 0x2016;	// DOUBLE VERTICAL LINE
			case 0xff0d:		// FULLWIDTH HYPHEN-MINUS ->
				return 0x2212;	// MINUS SIGN
			case 0xffe0:		// FULLWIDTH CENT SIGN ->
				return 0x00a2;	// CENT SIGN
			case 0xffe1:		// FULLWIDTH POUND SIGN ->
				return 0x00a3;	// POUND SIGN
			case 0xffe2:		// FULLWIDTH NOT SIGN ->
				return 0x00ac;	// NOT SIGN
		}
		return c;
	}
}
