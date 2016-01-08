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
 * 発行ルールにより、パスワードを自動生成するクラス。
 * ID RCSfile="$RCSfile: RandomPwd.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class RandomPwd {

	/**
	 * 大文字テーブル
	 */
	//2004-10-13 数値と混同しそうな文字は除外する
	//public static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String UPPER_CASE = "ABCDEFGHJKMNPQRSTUVWXY";

	/**
	 * 小文字テーブル
	 */
	//2004-10-13 数値と混同しそうな文字は除外する
	//public static final String LOWER_CASE = "abcedfghijklmnopqrstuvwxyz";
	public static final String LOWER_CASE = "acedfghkmnprstuvwxy";

	/**
	 * 数字テーブル
	 */
	//2005.11.30 iso 除外文字追加
//	public static final String DIGIT = "0123456789";
	public static final String DIGIT = "23456789";

	/**
	 * パスワード長デフォルト値
	 */
	private static final int PASSWORD_LENGTH = 5;

	/**
	 * 発行ルールにより、パスワードを自動生成する
	 * @param isUpperCase		大文字を含む。
	 * @param isLowerCase		小文字を含む。
	 * @param isDigit			数字を含む。
	 * @param length			パスワード長。
	 * @return					ランダムであろうパスワード文字列。
	 */
	public static String generate(
		boolean isUpperCase,
		boolean isLowerCase,
		boolean isDigit,
		int length) {

		//長さチェック
		int pwdLength = (length < 3 ? PASSWORD_LENGTH : length);
		int needUpperCase = -1, needLowerCase = -1, needDigit = -1;

		//ランダムの作成(現在時刻を種とするため、ちょっと止めてみた。)
		Random random;
		synchronized (RandomPwd.class) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			random = new Random();
		}

		//パスワードに使用する文字列の選別と指定文字表示位置の決定
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
		//指定が無い場合はすべての文字列。
		if (sbUsableChar.length() == 0) {
			sbUsableChar.append(UPPER_CASE);
			sbUsableChar.append(LOWER_CASE);
			sbUsableChar.append(DIGIT);
		}

		//パスワードの作成
		String usableString = sbUsableChar.toString();
		char[] resBuf = new char[pwdLength];
		for (int i = 0; i < pwdLength; i++) {
			char c;
			for (;;) {
				//ランダムにより文字の選定
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
	 * 発行ルールにより、パスワードを自動生成する
	 * @param ruleInfo			パスワード発行ルール
	 * @return					ランダムであろうパスワード文字列。
	 */
	public static String generate(RuleInfo ruleInfo) {
		//初期値
		boolean isUpperCase = true;	//true:大文字を含む
		boolean isLowerCase = true;	//true:小文字を含む
		boolean isDigit = true;		//true:数字を含む
		int length = 6;				//パスワードの長さ
		//発行ルールに基づいて変更
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
