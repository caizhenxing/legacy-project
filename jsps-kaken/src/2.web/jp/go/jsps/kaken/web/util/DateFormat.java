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
 * ���t�̃t�H�[�}�b�g���s���B
 * ID RCSfile="$RCSfile: DateFormat.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:59 $"
 */
public class DateFormat {

	/**
	 * ���t�̃t�H�[�}�b�g���s���B
	 * @param obj
	 * @return
	 */
	public static String format(Date date) {
		if(date == null){
			return "";
		}else{
			return new SimpleDateFormat("yyyy�NM��d��").format(date);
		}
	}

    /**
     * ���t�̃t�H�[�}�b�g���s���B
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
	 * �N�̕�����\�����擾����B
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
	 * ���̕�����\�����擾����B
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
	 * ���̕�����\�����擾����B
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
	 * �N�i�N�x�j�̕�����\�����擾����B
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
	
	//2005/03/31 �ǉ� --------------------------------��������
	//�N��̌v�Z���\�b�h�̒ǉ�
	/**
	 * ����ł̔N����v�Z����B
	 * @param birthDay �a����
	 * @param baseDate ���
	 * @return �N��
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
	 * 4��1�����_�ł̔N����擾<br/>
	 * �������A4��1�����܂�̒a�����͍l���Ȃ�
	 * @param birthDay �a����
	 * @return �N��
	 */
	public static int getAgeOnApril1st(Date birthDay){
		Calendar cal=GregorianCalendar.getInstance();
		cal.set(Calendar.MONTH,Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH,31);
		
		return getAge(birthDay,cal.getTime());
	}
	//2005/03/31 �ǉ� --------------------------------�����܂�

// 20050712
	/**
	 * 4��1�����_�ł̔N����擾<br/>
	 * @param birthDay �a����
	 * @return �N��
	 */
	public static int getAgeOnApril1st(Date birthDay, int nSeireki){
		Calendar cal=GregorianCalendar.getInstance();
		//2005.08.15 iso JAVA��ł́A4��=3�ƂȂ�A���{�̗�Ƃ����̂ŁA
		//Calendar�N���X����擾����悤�ɏC��
//		cal.set(nSeireki, 4, 1);
		cal.set(nSeireki, Calendar.APRIL, 1);
		return getAge(birthDay, cal.getTime());
	}
	/**
	 * 4��2�����_�ł̔N����擾<br/>
	 * @param birthDay �a����
	 * @return �N��
	 */
	public static int getAgeOnApril2nd(Date birthDay, int nSeireki){
		Calendar cal=GregorianCalendar.getInstance();
		cal.set(nSeireki, 4, 2);
		return getAge(birthDay, cal.getTime());
	}
// Horikoshi

}
