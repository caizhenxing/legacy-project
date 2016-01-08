/*
 * @(#) $Id: UnicodeCorrector.java,v 1.1 2007/06/28 02:06:44 administrator Exp $
 * $Revision: 1.1 $
 * Copyright (c) 2000 Shin Kinoshita All Rights Reserved.
 */
package jp.go.jsps.kaken.util;

import java.io.*;
import java.util.*;

/**
 * UnicodeCorrector.
 * <p>
 * CorrectOutputStreamWriter�ŗp�����镶����o�b�t�@�␳�N���X�ł��B<br>
 * �o�̓G���R�[�f�B���O�ɂ���ĕ␳���ׂ������R�[�h���قȂ�̂ŁA
 * ���ۂ̕␳�����̓T�u�N���X�ōs���܂��B<br>
 *
 * ����JIS/Shift_JIS�p��UnicodeCorrector�����p�ӂ��Ă��܂��񂪁B
 * </p>
 * @version	1.00
 * @author	Shin
 */
public abstract class UnicodeCorrector {
	
	private static final Hashtable	correctors_ = new Hashtable();
	
	static {
		// x-sjis-cp932���ł��������ϊ����ꂽUnicode�𑼂̃G���R�[�f�B���O��
		// �o�͂���ꍇ�ł�
		correctors_.put("iso2022jp", ToISO2022JPCorrector.class);
		correctors_.put("iso-2022-jp", ToISO2022JPCorrector.class);
		
		// ����"SJIS"�ł�Sun��MS-VM�ł͈قȂ�܂��B
		// Sun-JDK1.1��SJIS��x-sjis-jdk-1.1.7�ŁAMS-VM��x-sjis-cp932�ł��B
		// �������AMS-JDK���̃N���X���C�u����������x-sjis-jdk-1.1.7�����ɂȂ�̂�
		// ���������̂ł��B(�t�@�C�������o�����̂Ƃ��݂̂��������Ȃ�)
		// �����Windows�l�C�e�B�u�ȕϊ��\�Ő������ꂽUnicode��
		// x-sjis-jdk-1.1.7�ŏo�͂��邽�߂̂��̂ł��B
		correctors_.put("sjis", ToISO2022JPCorrector.class);
		
		// MS-VM�Ńt�@�C���o�͂��s���ꍇ�Ɍ���A������łȂ���΂Ȃ�܂���B
		//		correctors_.put("sjis", ToCP932Corrector.class);
		// JDK1.2�����Windows�̃f�t�H���g��"MS932"(x-sjis-cp932����)�ƂȂ�܂����B
		// ����ŁA���̃G���R�[�f�B���O�ŕϊ����ꂽ(������)Unicode��"ms932"
		// �ł̏o�͎��ɂ��������Ȃ��Ă��܂��܂��B
		// �����␳���܂��B
		//correctors_.put("ms932", ToCP932Corrector.class);		//�����N���X����������Ȃ�
		//correctors_.put("shift_jis", ToCP932Corrector.class);	//�����N���X����������Ȃ�
	}
	
	
	
	/**
	 * Create an UnicodeCorrector that uses
	 * the named character encoding.
	 * @param  enc	Name of the encoding to be used
	 * @exception  UnsupportedEncodingException
	 *			   If the named encoding is not supported
	 */
	public static UnicodeCorrector getInstance(String enc)
								throws UnsupportedEncodingException {
		Class				correctorClass =
									(Class)correctors_.get(enc.toLowerCase());
		if (correctorClass == null) {
			throw new UnsupportedEncodingException(enc);
		}
		try {
			return (UnicodeCorrector)correctorClass.newInstance();
		} catch (Exception e) {
			throw new UnsupportedEncodingException(correctorClass +
						" cannot get newInstance.\n" + e.getMessage());
		}
	}
	
	
	
	/**
	 * Unicode�����z��̕␳���s���܂��B
	 * <p>
	 * ����̕��������G���R�[�f�B���O�ŏo�͂��悤�Ƃ����ۂ�
	 * sun.io�R���o�[�^�ł͐���ɕϊ��ł��Ȃ�������␳���܂��B
	 * </p>
	 * @param  cbuf	 Buffer of characters
	 * @param  off	 Offset from which to start writing characters
	 * @param  len	 Number of characters to write
	 * @return Result that corrected.
	 *		   Note:Return array is different from <code>cbuf</code>
	 *				in case of different result size.
	 */
	public char[] correct(char cbuf[], int off, int len) {
		StringBuffer			buf = new StringBuffer();
		char					c;
		for (int i = off; i < len; i++) {
			buf.append(correct(cbuf[i]));
		}
		return new String(buf).toCharArray();
	}
	
	
	
	/**
	 * Unicode������̕␳���s���܂��B
	 * <p>
	 * ����̕��������G���R�[�f�B���O�ŏo�͂��悤�Ƃ����ۂ�
	 * sun.io�R���o�[�^�ł͐���ɕϊ��ł��Ȃ�������␳���܂��B
	 * </p>
	 * @param s
	 * @return
	 */
	public String correct(String s) {
		StringBuffer			buf = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			buf.append(correct(s.charAt(i)));
		}
		return new String(buf);
	}
	
	
	
	/** ���\�I�ɂ����abstract�ɂ���̂͂��߂�������E�E�E */
	public abstract char correct(char c);
	
	
	
	/**
	 * �V����UnicodeCorrector��ǉ����܂��B
	 * <p>
	 * ���̃\�[�X�R�[�h��ς����ɁA���I�ɐV���ȏo�̓G���R�[�f�B���O��
	 * �Ή�����UnicodeCorrector��o�^�������ꍇ�ɗp���܂��B
	 * </p>
	 * @param enc �Ή�����G���R�[�f�B���O��
	 * @param correctorClass UnicodeCorrector�T�u�N���X��Class�I�u�W�F�N�g
	 */
	public static void addCorrector(String enc, Class correctorClass) {
		if (!correctorClass.isInstance(UnicodeCorrector.class)) {
			throw new IllegalArgumentException(
					"Corrector is not UnicodeCorrector type.");
		}
		correctors_.put(enc, correctorClass);
	}
	
	
}
