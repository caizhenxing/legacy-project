/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.*;

import jp.go.jsps.kaken.model.vo.*;

/**
 * ���s���[���ɂ��A�p�X���[�h��������������N���X�B
 * ID RCSfile="$RCSfile: RandomPwd.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class RandomPwd {

	/**
	 * �啶���e�[�u��
	 */
	//2004-10-13 ���l�ƍ����������ȕ����͏��O����
	//public static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String UPPER_CASE = "ABCDEFGHJKMNPQRSTUVWXY";

	/**
	 * �������e�[�u��
	 */
	//2004-10-13 ���l�ƍ����������ȕ����͏��O����
	//public static final String LOWER_CASE = "abcedfghijklmnopqrstuvwxyz";
	public static final String LOWER_CASE = "acedfghkmnprstuvwxy";

	/**
	 * �����e�[�u��
	 */
	//2005.11.30 iso ���O�����ǉ�
//	public static final String DIGIT = "0123456789";
	public static final String DIGIT = "23456789";

	/**
	 * �p�X���[�h���f�t�H���g�l
	 */
	private static final int PASSWORD_LENGTH = 5;

	/**
	 * ���s���[���ɂ��A�p�X���[�h��������������
	 * @param isUpperCase		�啶�����܂ށB
	 * @param isLowerCase		���������܂ށB
	 * @param isDigit			�������܂ށB
	 * @param length			�p�X���[�h���B
	 * @return					�����_���ł��낤�p�X���[�h������B
	 */
	public static String generate(
		boolean isUpperCase,
		boolean isLowerCase,
		boolean isDigit,
		int length) {

		//�����`�F�b�N
		int pwdLength = (length < 3 ? PASSWORD_LENGTH : length);
		int needUpperCase = -1, needLowerCase = -1, needDigit = -1;

		//�����_���̍쐬(���ݎ�������Ƃ��邽�߁A������Ǝ~�߂Ă݂��B)
		Random random;
		synchronized (RandomPwd.class) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			random = new Random();
		}

		//�p�X���[�h�Ɏg�p���镶����̑I�ʂƎw�蕶���\���ʒu�̌���
		StringBuffer sbUsableChar = new StringBuffer();
		if (isUpperCase) {
			sbUsableChar.append(UPPER_CASE);
			needUpperCase = random.nextInt(pwdLength);
		}
		if (isLowerCase) {
			sbUsableChar.append(LOWER_CASE);
			do {
				needLowerCase = random.nextInt(pwdLength);
			} while (needLowerCase == needUpperCase);
		}
		if (isDigit) {
			sbUsableChar.append(DIGIT);
			do {
				needDigit = random.nextInt(pwdLength);
			} while (
				(needDigit == needUpperCase) || (needDigit == needLowerCase));
		}
		//�w�肪�����ꍇ�͂��ׂĂ̕�����B
		if (sbUsableChar.length() == 0) {
			sbUsableChar.append(UPPER_CASE);
			sbUsableChar.append(LOWER_CASE);
			sbUsableChar.append(DIGIT);
		}

		//�p�X���[�h�̍쐬
		String usableString = sbUsableChar.toString();
		char[] resBuf = new char[pwdLength];
		for (int i = 0; i < pwdLength; i++) {
			char c;
			for (;;) {
				//�����_���ɂ�蕶���̑I��
				c =	usableString.charAt(
						random.nextInt(usableString.length() - 1));
				if (i == needUpperCase) {
					if (Character.isUpperCase(c)) {
						break;
					}
				} else if (i == needLowerCase) {
					if (Character.isLowerCase(c)) {
						break;
					}

				} else if (i == needDigit) {
					if (Character.isDigit(c)) {
						break;
					}
				} else {
					break;
				}
			}
			resBuf[i] = c;
		}
		return new String(resBuf);
	}
	
	
	
	/**
	 * ���s���[���ɂ��A�p�X���[�h��������������
	 * @param ruleInfo			�p�X���[�h���s���[��
	 * @return					�����_���ł��낤�p�X���[�h������B
	 */
	public static String generate(RuleInfo ruleInfo) {
		//�����l
		boolean isUpperCase = true;	//true:�啶�����܂�
		boolean isLowerCase = true;	//true:���������܂�
		boolean isDigit = true;		//true:�������܂�
		int length = 6;				//�p�X���[�h�̒���
		//���s���[���Ɋ�Â��ĕύX
		if(ruleInfo.getCharChk1() != null && ruleInfo.getCharChk1().equals("1")) {
			isUpperCase = false;
		}
		if(ruleInfo.getCharChk2() != null && ruleInfo.getCharChk2().equals("1")) {
			isDigit = false;
		}
		length = Integer.parseInt(ruleInfo.getMojisuChk());
		if(length > 10 || length < 6){
			length = 6;
		}
		return RandomPwd.generate(isUpperCase, isLowerCase, isDigit, length);
	}
	
	
	
	
	
}
