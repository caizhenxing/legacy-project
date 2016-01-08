/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.util;

import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.util.*;

/**
 * 日付のフォーマットを行う。
 * ID RCSfile="$RCSfile: DateFormat.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:59 $"
 */
public class DateFormat {

	/**
	 * 日付のフォーマットを行う。
	 * @param obj
	 * @return
	 */
	public static String format(Date date) {
		if(date == null){
			return "";
		}else{
			return new SimpleDateFormat("yyyy年M月d日").format(date);
		}
	}

    /**
     * 日付のフォーマットを行う。
     * @param obj
     * @param pattern
     * @return
     */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat(pattern).format(date);
		}
	}
    
	/**
	 * 年の文字列表現を取得する。
	 * @param obj
	 * @return
	 */
	public static String getYear(Date date) {
		if(date == null){
			return "";
		}else{
			return new SimpleDateFormat("yyyy").format(date);
		}
	}

	/**
	 * 月の文字列表現を取得する。
	 * @param obj
	 * @return
	 */
	public static String getMonth(Date date) {
		if(date == null){
			return "";
		}else{
			return new SimpleDateFormat("M").format(date);
		}
	}
	
	/**
	 * 日の文字列表現を取得する。
	 * @param obj
	 * @return
	 */
	public static String getDay(Date date) {
		if(date == null){
			return "";
		}else{
			return new SimpleDateFormat("d").format(date);
		}
	}
	
	
	/**
	 * 年（年度）の文字列表現を取得する。
	 * @param date
	 * @return
	 */
	public static String getYearNendo(Date date){
		if(date == null){
			return "";
		}else{
			return new DateUtil(date).getNendo();
		}
	}
	
	//2005/03/31 追加 --------------------------------ここから
	//年齢の計算メソッドの追加
	/**
	 * 基準日での年齢を計算する。
	 * @param birthDay 誕生日
	 * @param baseDate 基準日
	 * @return 年齢
	 */
	public static int getAge(Date birthDay,Date baseDate){
		Calendar cal=GregorianCalendar.getInstance();
		cal.setTime(birthDay);
		int birth[]={cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)};
		cal.setTime(baseDate);
		int base[]={cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)};

		int age = base[0]-birth[0];
		if(base[1]<birth[1]){
			age--;
		} else if(base[1]==birth[1]){
			if(base[2]<birth[2]){
				age--;
			}
		}
		
		return age;
	}
	
	/**
	 * 4月1日時点での年齢を取得<br/>
	 * ただし、4月1日生まれの誕生日は考えない
	 * @param birthDay 誕生日
	 * @return 年齢
	 */
	public static int getAgeOnApril1st(Date birthDay){
		Calendar cal=GregorianCalendar.getInstance();
		cal.set(Calendar.MONTH,Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH,31);
		
		return getAge(birthDay,cal.getTime());
	}
	//2005/03/31 追加 --------------------------------ここまで

// 20050712
	/**
	 * 4月1日時点での年齢を取得<br/>
	 * @param birthDay 誕生日
	 * @return 年齢
	 */
	public static int getAgeOnApril1st(Date birthDay, int nSeireki){
		Calendar cal=GregorianCalendar.getInstance();
		//2005.08.15 iso JAVA上では、4月=3となり、日本の暦とずれるので、
		//Calendarクラスから取得するように修正
//		cal.set(nSeireki, 4, 1);
		cal.set(nSeireki, Calendar.APRIL, 1);
		return getAge(birthDay, cal.getTime());
	}
	/**
	 * 4月2日時点での年齢を取得<br/>
	 * @param birthDay 誕生日
	 * @return 年齢
	 */
	public static int getAgeOnApril2nd(Date birthDay, int nSeireki){
		Calendar cal=GregorianCalendar.getInstance();
		cal.set(nSeireki, 4, 2);
		return getAge(birthDay, cal.getTime());
	}
// Horikoshi

}
