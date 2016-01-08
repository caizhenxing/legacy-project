/*
 * @(#) $Id: NullObjectCorrector.java,v 1.1 2007/06/28 02:06:43 administrator Exp $
 * $Revision: 1.1 $
 * Copyright (c) 2000 Shin Kinoshita All Rights Reserved.
 */
package jp.go.jsps.kaken.util;

/**
 * NullObjectCorrector.
 * <p>
 * NullObject�B�����������Ȃ��B
 * </p>
 * @version 1.00
 * @author Shin
 */
public class NullObjectCorrector extends UnicodeCorrector {
	/**
	 * ���������̂܂ܕԂ��B
	 * @param  c	 source character
	 * @return Result character that corrected.
	 */
	public char correct(char c) {
		return c;
	}
}
