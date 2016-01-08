/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.util.StringUtil;

/**
 * 
 * ������̘_���t�H�[�}�b�g���`�F�b�N����N���X�B
 * 
 * ID RCSfile="$RCSfile: DateUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class DateUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------


	//2005/04/19 �C�� -------------------------------------------------------------��������
	//�e�N���̋L����S�p�ŕ\�����Ȃ���΂Ȃ�Ȃ�����
	// ��ϊ��e�[�u��(������ő召��r�ł���悤�ɔN������10���Œ�Ƃ���)
	//private static final String rekiTbl[][] =
	//	{ { "1868/09/08", "1912/07/29", "M" }, // �����J�n�N�����A�ŏI�N�����A�����L��
	//	{
	//		"1912/07/30", "1926/12/24", "T" }, // �吳�J�n�N�����A�ŏI�N�����A�吳�L��
	//	{
	//		"1926/12/25", "1989/01/07", "S" }, // ���a�J�n�N�����A�ŏI�N�����A���a�L��
	//	{
	//		"1989/01/08", "9999/12/31", "H" } // �����J�n�N�����A�ŏI�N�A�����L��
	//};

	// ��ϊ��e�[�u��(������ő召��r�ł���悤�ɔN������10���Œ�Ƃ���)
	private static final String rekiTbl[][] =
		{ { "1868/09/08", "1912/07/29", "M", "�l" }, // �����J�n�N�����A�ŏI�N�����A�����L���A�����L��(�S�p)
		{
			"1912/07/30", "1926/12/24", "T", "�s" }, // �吳�J�n�N�����A�ŏI�N�����A�吳�L���A�吳�L��(�S�p)
		{
			"1926/12/25", "1989/01/07", "S", "�r" }, // ���a�J�n�N�����A�ŏI�N�����A���a�L���A���a�L��(�S�p)
		{
			"1989/01/08", "9999/12/31", "H", "�g" } // �����J�n�N�����A�ŏI�N�A�����L���A�����L��(�S�p)
	};
	//2005/04/19 �C�� -------------------------------------------------------------�����܂�

// 20050713
	private static final String WarekiTbl[][] =
	{ { "1868/09/08", "1912/07/29", "M", "����" }, // �����J�n�N�����A�ŏI�N�����A�����L���A�����L��(�S�p)
	{
		"1912/07/30", "1926/12/24", "T", "�吳" }, // �吳�J�n�N�����A�ŏI�N�����A�吳�L���A�吳�L��(�S�p)
	{
		"1926/12/25", "1989/01/07", "S", "���a" }, // ���a�J�n�N�����A�ŏI�N�����A���a�L���A���a�L��(�S�p)
	{
		"1989/01/08", "9999/12/31", "H", "����" } // �����J�n�N�����A�ŏI�N�A�����L���A�����L��(�S�p)
};
// Horikoshi

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	// ��J�����_�[
	private Calendar cal;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 */
	public DateUtil() {
		cal = Calendar.getInstance(); // ��J�����_�[�ɍ������Z�b�g
	}
	
	/**
	 * �R���X�g���N�^
	 * @param date
	 */
	public DateUtil(Date date) {
		cal = Calendar.getInstance();
		cal.setTime(date); // ��J�����_�[�ɂ��Z�b�g
	}
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * ��J�����_�[�ɔN�������Z�b�g����
	 * @param c �N�������Z�b�g���ꂽ�J�����_�[
	 * 
	 * @return  true �������N����
	 *          false ������N����
	 */
	public boolean setCal(Calendar c) {
		if (c == null) {
			return false;
		}
		cal.set(
			c.get(Calendar.YEAR),
			c.get(Calendar.MONTH),
			c.get(Calendar.DAY_OF_MONTH));
		return true;
	}

	/**
	 * ��J�����_�[�ɔN�������Z�b�g����
	 * @param yyyy  ����N������
	 * @param mm    ���������
	 * @param dd    �����������
	 * @return      true �������N����
	 *              false ������N����
	 */
	public boolean setCal(String yyyy, String mm, String dd) {
		// �N�����̐��������`�F�b�N
		if (checkDate(yyyy, mm, dd) == false) {
			return false;
		}
		try {
			// �N��������J�����_�[�փZ�b�g
			cal.set(
				Integer.parseInt(yyyy),
				Integer.parseInt(mm) - 1,
				Integer.parseInt(dd));
			return true;
		} catch (NumberFormatException e) { // �����łȂ���΃G���[
			return false;
		}
	}

	/**
	 * ��J�����_�[�ɔN�������Z�b�g����
	 * @param yyyy ����N���l
	 * @param mm   ������l(1�`12)
	 * @param dd   ��������l(1�`31)
	 * @return     true �������N����
	 *             false ������N����
	 */
	public boolean setCal(int yyyy, int mm, int dd) {
		// �N�����̐��������`�F�b�N
		if (checkDate(yyyy, mm, dd) == false) {
			return false;
		}
		try {
			// �N��������J�����_�[�փZ�b�g
			cal.set(yyyy, mm - 1, dd);
			return true;
		} catch (NumberFormatException e) { // �����łȂ���΃G���[
			return false;
		}
	}

	/**
	 * ��J�����_�[�ɔN��1��1�����Z�b�g����
	 * @param yyyy ����N���l
	 * @return     true �������N
	 *             false ������N
	 */
	public boolean setCal(int yyyy) {
		// �N�̐��������`�F�b�N
		if (checkDate(yyyy, 1, 1) == false) {
			return false;
		}
		try {
			// �N+1��1������J�����_�[�փZ�b�g
			cal.set(yyyy, 0, 1);
			return true;
		} catch (NumberFormatException e) { // �����łȂ���΃G���[
			return false;
		}
	}

	/**
	 * ��J�����_�[�ɔN�������Z�b�g����
	 *
	 * @param date ����N����(�N�����̊Ԃ̓f���~�^�ŋ�؂��Ă��邱��)
	 * @param dim  �f���~�^
	 * @return     true �������N����
	 *             false ������N����
	 */
	public boolean setCal(String date, String dim) {
		// �N�����̐��������`�F�b�N
		if (checkDate(date) == false) {
			return false;
		}
		// �f���~�^��/�Ƃ��ăp�����[�^�N�����𕪊����܂�
		StringTokenizer st = new StringTokenizer(date, dim);
		String d[] = new String[3];
		if (st.countTokens() > d.length) { // �g�[�N�������߂���ꍇ�̓G���[
			return false;
		}
		int i = 0;
		while (st.hasMoreTokens()) {
			d[i] = st.nextToken();
			i++;
		}
		try {
			// ��J�����_�[�ɔN�������Z�b�g���܂�
			cal.set(
				Integer.parseInt(d[0]),
				Integer.parseInt(d[1]) - 1,
				Integer.parseInt(d[2]));
			return true;
		} catch (NumberFormatException e) { // �����łȂ���΃G���[
			return false;
		}
	}

	/**
	 * ��J�����_�[�ɘa��ŔN�������Z�b�g����
	 * @param jpy �a��N�����i�N�����̓f���~�^�ŋ�؂��Ă��邱�Ɓj
	 *             �a��N�����͖���33�N1��1���ȍ~�ł��邱��
	 * @param dim  �f���~�^
	 * @return     true �������N����
	 *             false ������N����
	 */
	public boolean setCalJpy(String jpy, String dim) {
		// �f���~�^��/�Ƃ��ăp�����[�^�N�����𕪊����܂�
		StringTokenizer st = new StringTokenizer(jpy.trim(), dim);
		String d[] = new String[3];
		if (st.countTokens() > d.length) { // �g�[�N�������߂���ꍇ�̓G���[
			return false;
		}

		// �N�������g�[�N���Ƃ��Ď��o��
		int i = 0;
		while (st.hasMoreTokens()) {
			d[i] = st.nextToken();
			i++;
		}

		// ���������N���������ꂼ�ꐮ�������܂�
		int jpyy;
		int mm;
		int dd;
		try {
			jpyy = Integer.parseInt(d[0].substring(1));
			mm = Integer.parseInt(d[1]);
			dd = Integer.parseInt(d[2]);
		} catch (NumberFormatException e) { // �����łȂ���΃G���[
			return false;
		}

		// �p�����[�^�̘a��N�̌��������ŗ�ϊ��e�[�u��������
		int j = 0;
		for (j = 0; j < rekiTbl.length; j++) {
			// �������}�b�`������r���S�I
			if (d[0].startsWith(rekiTbl[j][2])) {
				break;
			}
		}

		// ����N�����̎擾
		try {
			// ��e�[�u���ɊY�����R�[�h����������
			if (j < rekiTbl.length) {
				// �a��N��0�ȉ��Ȃ�G���[
				if (jpyy <= 0) {
					return false;
				}
				// ����N���v�Z���ċ��߂܂�
				int ad =
					Integer.parseInt(rekiTbl[j][0].substring(0, 4)) + jpyy - 1;
				// ����N��1900�N�����Ȃ�a��ϊ��G���[
				if (ad < 1900) {
					return false;
				}
				// ����N�����̐��������`�F�b�N���܂�
				if (checkDate(ad, mm, dd)) {
					// ��J�����_�[�ɔN�������Z�b�g���܂�
					cal.set(ad, mm - 1, dd);
					return true;
				} else {
					return false;
				}
			} else {
				// �Y�����Ȃ���΃G���[
				return false;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return false;
		}
	}

	/**
	 * ��J�����_�[��Calendar�ϐ��Ƃ��ĕԂ����\�b�h
	 * 
	 * @return ��J�����_�[��Calendar�ϐ��Ƃ��ĕԂ�
	 */
	public Calendar getCal() {
		return cal;
	}

	/**
	 * ��J�����_�[��a�����Ƃ��Č��ݔN����Z�o���郁�\�b�h
	 * 
	 * @return ���ݔN��𐮐��Ƃ��ĕԂ��B
	 *         �G���[�̏ꍇ��-1��Ԃ��B
	 */
	public int getAge() {

		// �����݂̃J�����_�[���쐬
		Calendar now = Calendar.getInstance();

		// ���ݔN-�a���N=�T�Z�N��
		int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);

		// ���N�̒a���������Ă��Ȃ����
		if (now.get(Calendar.MONTH) < cal.get(Calendar.MONTH)) {
			age--; //�T�Z�N���P�N����
			// �����a������
		} else if (now.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
			// �܂��a���������Ă��Ȃ����
			if (now.get(Calendar.DAY_OF_MONTH)
				< cal.get(Calendar.DAY_OF_MONTH)) {
				age--; //�T�Z�N���P�N����
			}
		}
		// �a�������������傫����΃G���[�Ƃ���
		if (age < 0) {
			return -1; // �G���[
		} else {
			return age; // �N���Ԃ�
		}
	}

	/**
	 * ��J�����_�[�̔N�����Ǝw�肳�ꂽDateUtil�̔N�����Ƃ̓��������Z�o����N���X
	 *
	 * @param date
	 * @return �������𐮐��Ƃ��ĕԂ��B
	 */
	public int getElapse(DateUtil date) {

		// �^�[�Q�b�g�̃J�����_�[���擾
		Calendar d = date.getCal();

		// ���݂܂ł̍����~���b�����߂�
		long ed = d.getTimeInMillis() - cal.getTimeInMillis();

		//    �����~���b������֊��Z
		int elapse = (int) (ed / 1000 / 60 / 60 / 24);

		return elapse; // ��������Ԃ�
	}
	
	
	/**
	 * ��J�����_�[�̔N���Ǝw�肳�ꂽDateUtil�̔N���Ƃ̌��������Z�o���郁�\�b�h
	 * �w�肳�ꂽDateUtil�̓��t�Ɗ�J�����_�[�̓��t�𔻒f���A�w�肳�ꂽ���t�̕���
	 * �傫���ꍇ�i�����ꍇ���܂ށj�́A�P�����Ƃ��Ĕ��f����B<br>
	 * <ul>
	 * <li>2005/4/1 �` 2007/3/31���u2�N�i24�����j�v</li>
	 * <li>2005/4/1 �` 2007/4/1 ���u2�N��1�����i25�����j�v</li>
	 * <li>2005/4/15�` 2007/9/30���u2�N��6�����i30�����j�v</li>
	 * <li>2005/2/28�` 2005/3/28���u0�N��2�����i2�����j�v</li>
	 * </ul>
	 * target�̕����̂������ꍇ�̓}�C�i�X�ŕԂ�B
	 * @param target
	 * @return �������𐮐��Ƃ��ĕԂ��B
	 */
	public int getElapseMonth(DateUtil target){
		
		//��J�����_�[�̔N,�����擾����
		int myYYYY = cal.get(Calendar.YEAR);
		int myMM   = cal.get(Calendar.MONTH) + 1;
		int myDD   = cal.get(Calendar.DATE);
		
		//�^�[�Q�b�g�J�����_�[�̔N,�����擾����
		Calendar targetCal = target.getCal();
		int targetYYYY = targetCal.get(Calendar.YEAR);
		int targetMM   = targetCal.get(Calendar.MONTH) + 1;
		int targetDD   = targetCal.get(Calendar.DATE);
		
		//�����̍������v�Z����
		int yearDiff  = targetYYYY - myYYYY;
		int monthDiff = (targetMM + yearDiff*12) - myMM;
		
		//�w�肳�ꂽ���t�̕����傫���ꍇ�i�����ꍇ���܂ށj�̓v���X�P
		if(targetDD>=myDD){
			monthDiff++;
		}
		
		return monthDiff;
		
	}
	
	
	/**
	 * ��J�����_�[�Ɏw����������Z���郁�\�b�h
	 *
	 * @param day  �w�����(-�͉ߋ��A+�͖���������)
	 *
	 */
	public void addDate(int day) {
		// ��J�����_�[�Ɏw����������Z�����N���������߂�
		cal.add(Calendar.DATE, day);
	}

	/**
	 * �a��N�����̕������Ԃ����\�b�h
	 *
	 * @param dim �N��������؂�f���~�^
	 * @return �a��N������$YY?MM?DD�`���̕�����ŕԂ��i��> H15/1/1�j
	 *         $�͖���=M�A�吳=T�A���a=S�A����=H�Ō�����\�����̂Ƃ���
	 *         �G���[�̏ꍇ��NULL��Ԃ�
	 *
	 */
	public String getJpyString(String dim) {

		// ��J�����_�[�̔N���������ꂼ�ꐮ�������܂�
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900�N�ȑO�ł���΃G���[�Ƃ��܂�
		if (yyyy < 1900) {
			return null;
		}

		// �P�O���Œ��YYYY/MM/DD�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < rekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;
				// �a��+�a��N��Ԃ��܂�
				return rekiTbl[i][2]
					+ String.valueOf(jpy)
					+ dim
					+ new DecimalFormat("00").format(mm)
					+ dim
					+ new DecimalFormat("00").format(dd);
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}

	//2005/04/19 �ǉ�---------------------------------------------------��������
	//���R �a��a������\������K�v�����邽��
	/**
	 * �a��N���̕������Ԃ����\�b�h
	 *
	 * @param date �ϊ��ΏۂƂȂ���t
	 * @return ex)"�r�D62�N 3��"
	 *
	 */
	public String getJpyString(Date date) {

		cal.setTime(date);
		// ��J�����_�[�̔N���������ꂼ�ꐮ�������܂�
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900�N�ȑO�ł���΃G���[�Ƃ��܂�
		if (yyyy < 1900) {
			return null;
		}

		// �P�O���Œ��YYYY/MM/DD�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < rekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;
				// �a��+�a��N��Ԃ��܂�
				return rekiTbl[i][3]
					+ "�D"
					+ String.valueOf(jpy)
					+ "�N"
					+ new DecimalFormat("00").format(mm)
					+ "��";
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}
	//2005/04/19 �ǉ�---------------------------------------------------�����܂�

	/**
	 * ����N�����̕������Ԃ����\�b�h
	 * 
	 * @param dim �N��������؂�f���~�^
	 * @return ����N�������f���~�^�ŋ�؂����`���̕�����ŕԂ��i��> 2003/10/1�j
	 */
	public String getAdString(String dim) {
		// �f���~�^�ŋ�؂���YYYY?MM?DD�`���̕������Ԃ�
		return String.valueOf(cal.get(Calendar.YEAR))
			+ dim
			+ String.valueOf(cal.get(Calendar.MONTH) + 1)
			+ dim
			+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * ����N�����̔N�i���Q���j������Ԃ��B
	 * @return
	 */
	public String getYearYY() {
		int yyyy = cal.get(Calendar.YEAR) % 100;
		String yy = new DecimalFormat("00").format(yyyy);
		return yy;
	}
	
	/**
	 * ����N�����̔N�i��4���j������Ԃ��B
	 * @return
	 */
	public String getYearYYYY() {
		int yyyy = cal.get(Calendar.YEAR);
		String YYYY = new DecimalFormat("0000").format(yyyy);
		return YYYY;
	}
	
	/**
	 * ��J�����_�[�̓��������A�N�����Ɋۂ߂�Date�I�u�W�F�N�g�ŕԂ��B
	 * @return
	 */
	public Date getDateYYYYMMDD(){
		
		// ��J�����_�[�̔N���������ꂼ�ꐮ�������܂�
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// �P�O���Œ��YYYY/MM/DD�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);
		
		//��������N�����Ɋۂ߂�
		Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			date = sdf.parse(ady);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;

	}	
	
	
	/**
	 * �N�x��Ԃ��B
	 * @return �N�x
	 */
	public String getNendo() {

		// ��J�����_�[�̔N���������ꂼ�ꐮ�������܂�
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900�N�ȑO�ł���΃G���[�Ƃ��܂�
		if (yyyy < 1900) {
			return null;
		}

		// �P�O���Œ��YYYY/MM/DD�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < rekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;

				//4�������ĔN�x��I������B				
				if (cal.get(Calendar.MONTH) < Calendar.APRIL) {
					jpy = jpy - 1;
				}

				// �N�x��Ԃ��܂��B
				return new DecimalFormat("00").format(jpy);
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}

	/**
	 * ���x���擾���郁�\�b�h
	 * 
	 * @return ���x�𕶎���ŕԂ�
	 *         �G���[�̏ꍇ��NULL��Ԃ�
	 */
	public String getEto() {
		// �\��x������e�[�u��
		String etoTbl[] =
			{ "�q", "�N", "��", "�K", "�C", "��", "��", "��", "�\", "��", "��", "��" };

		// �N�𐮐������܂�
		int yyyy = cal.get(Calendar.YEAR);
		int i = (yyyy + 8) % 12;
		return etoTbl[i];
	}

	/**
	 * �����񂪑��݂���N����(YYYY/MM/DD)�ł��邩�ǂ������`�F�b�N����
	 * �召�̌��A�[�N�Ȃǂ��`�F�b�N����
	 * 
	 * @param date �`�F�b�N���鐼��N����������
	 *             �N�ƌ��Ɠ��͕K���X���b�V���ŋ�؂��Ă��邱�ƁB
	 * @return �`�F�b�N���� true�F������ false�F���
	 */
	public static boolean checkDate(String date) {
		// �O��̋󔒂�����
		String d = new String(date.trim());
		// ���t�t�H�[�}�b�^�[���`
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		// ��̓��x���������ɂ���
		df.setLenient(false);
		try {
			df.parse(d); // ���͔N���������
		} catch (ParseException e) {
			return false; // �G���[
		}
		return true;
	}

	/**
	 * �����񂪑��݂���N(YYYY),��(MM),��(DD)�ł��邩�ǂ������`�F�b�N����
	 * �召�̌��A�[�N�Ȃǂ��`�F�b�N����
	 * 
	 * @param yyyy �`�F�b�N���鐼��N������
	 * @param mm   �`�F�b�N���錎������
	 * @param dd   �`�F�b�N�����������
	 * @return �`�F�b�N���� true�F������ false�F���
	 */
	private boolean checkDate(String yyyy, String mm, String dd) {
		return checkDate(yyyy + "/" + mm + "/" + dd);
	}

	/**
	 * ���l�����݂���N(YYYY),��(MM),��(DD)�ł��邩�ǂ������`�F�b�N����
	 * �召�̌��A�[�N�Ȃǂ��`�F�b�N����
	 * 
	 * @param yyyy �`�F�b�N���鐼��N���l
	 * @param mm   �`�F�b�N���錎���l
	 * @param dd   �`�F�b�N��������l
	 * @return �`�F�b�N���� true�F������ false�F���
	 */
	private boolean checkDate(int yyyy, int mm, int dd) {
		// �N�����̐��������`�F�b�N
		return checkDate(
			String.valueOf(yyyy)
				+ "/"
				+ String.valueOf(mm)
				+ "/"
				+ String.valueOf(dd));
	}
	
	
	
	/**
	 * �a��N�x���琼��N�x�i���Q�P�^�j�֕ϊ�����B
	 * @param warekiNendo
	 * @return
	 */
	public static String changeWareki2Seireki(String warekiNendo){
		
		String nendo = new DecimalFormat("00")
							.format(Integer.parseInt(warekiNendo));
		
		StringBuffer buffer = new StringBuffer()
							  .append("H")			//����
							  .append(nendo)		//�N�x
							  .append("/01/01");	//���Ƃ��ĔN�����Z�b�g
		
		DateUtil nendoUtil = new DateUtil();
		nendoUtil.setCalJpy(buffer.toString() ,"/");
		
		//�N�x�𐼗�N�x�i2���j�ɕϊ�
		return nendoUtil.getYearYY();
		
	}
	
	/**
	 * �a��N�x���琼��N�x�i��4�P�^�j�֕ϊ�����B
	 * @param warekiNendo
	 * @return
	 */
	public static String changeWareki4Seireki(String warekiNendo){
		
		String nendo = new DecimalFormat("00")
							.format(Integer.parseInt(warekiNendo));
		
		StringBuffer buffer = new StringBuffer()
							  .append("H")			//����
							  .append(nendo)		//�N�x
							  .append("/01/01");	//���Ƃ��ĔN�����Z�b�g
		
		DateUtil nendoUtil = new DateUtil();
		nendoUtil.setCalJpy(buffer.toString() ,"/");
		
		//�N�x�𐼗�N�x�i2���j�ɕϊ�
		return nendoUtil.getYearYYYY();
		
	}
	
// 20050713
	
	/**
	 * �a��N���̕������Ԃ����\�b�h
	 *
	 * @param chYear �ϊ��ΏۂƂȂ���t�̔N
	 * @param chMonth �ϊ��ΏۂƂȂ���t�̌�
	 * @param chDay �ϊ��ΏۂƂȂ���t�̓�
	 * @return ex)"����XX�NXX��XX��"
	 *
	 */
	public String getJpyString(String chYear, String chMonth, String chDay) {

		int nYear;
		int nMonth;
		int nDay;

		if(chYear != null && chYear.length() > 0 && 
			chMonth != null && chMonth.length() > 0 &&
			chDay != null && chDay.length() > 0){

			nYear = StringUtil.parseInt(chYear);
			nMonth = StringUtil.parseInt(chMonth);
			nDay = StringUtil.parseInt(chDay);

		}
		else{
			return null;
		}

		try {
			return getJpyString(nYear,nMonth,nDay);
		}catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;}
	}

	/**
	 * �a��N���̕������Ԃ����\�b�h
	 *
	 * @param nYear �ϊ��ΏۂƂȂ���t�̔N(int)
	 * @param nMonth �ϊ��ΏۂƂȂ���t�̌�(int)
	 * @param nDay �ϊ��ΏۂƂȂ���t�̓�(int)
	 * @return ex)"����XX�NXX��XX��"
	 *
	 */

	public String getJpyString(int nYear, int nMonth, int nDay) {

		// �P�O���Œ��YYYY/MM/DD�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(nYear)
				+ "/"
				+ new DecimalFormat("00").format(nMonth)
				+ "/"
				+ new DecimalFormat("00").format(nDay);

		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < WarekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// �a��+�a��N+��+����Ԃ��܂�
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "�N"
					+ new DecimalFormat("").format(nMonth)
					+ "��"
					+ new DecimalFormat("").format(nDay)
					+ "��";
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}

// Horikoshi

//	2006/02/08 add start by miaomiao
	/**
	 * �a��N���̕������Ԃ����\�b�h
	 *
	 * @param chYear �ϊ��ΏۂƂȂ���t�̔N(String)
	 * @param chMonth �ϊ��ΏۂƂȂ���t�̌�(String)
	 * @return ex)"����XX�NXX��"
	 *
	 */
	public String getJpyString(String chYear, String chMonth) {

		int nYear;
		int nMonth;

		if(chYear != null && chYear.length() > 0 && 
			chMonth != null && chMonth.length() > 0 ){

			nYear = StringUtil.parseInt(chYear);
			nMonth = StringUtil.parseInt(chMonth);
		}
		else{
			return null;
		}

		try {
			return getJpyString(nYear,nMonth);
		}catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;}
	}

	/**
	 * �a��N���̕������Ԃ����\�b�h
	 *
	 * @param nYear �ϊ��ΏۂƂȂ���t�̔N(int)
	 * @param nMonth �ϊ��ΏۂƂȂ���t�̌�(int)
	 * @return ex)"����XX�NXX��"
	 *
	 */
	public String getJpyString(int nYear, int nMonth) {

		// 7���Œ��YYYY/MM�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(nYear)
				+ "/"
				+ new DecimalFormat("00").format(nMonth);

		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < WarekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// �a��+�a��N+��+����Ԃ��܂�
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "�N"
					+ new DecimalFormat("").format(nMonth)
					+ "��";
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}

	/**
	 * �a��N�x�̕������Ԃ����\�b�h
	 *
	 * @param chYear �ϊ��ΏۂƂȂ���t�̔N(String)
	 * @return ex)"����XX�N"
	 *
	 */
	public String getJpyNedo(String chYear) {

		int nYear;

		if(chYear != null && chYear.length() > 0){			
			nYear = StringUtil.parseInt(chYear);
		}else{
			return null;
		}

		try {
			return getJpyString(nYear);
		}catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;}
	}

	/**
	 * �a��N�̕�����(�a��+�a��N)��Ԃ����\�b�h
	 *
	 * @param nYear �ϊ��ΏۂƂȂ���t�̔N(int)
	 * @return ex)"����XX�N"
	 */
	public String getJpyString(int nYear) {

		// 4���Œ��YYYY�`���ɕϊ�
		String ady =
			new DecimalFormat("0000").format(nYear);
		// ��ϊ��e�[�u�����T�[�`
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// ���Y����J�n�N�ȏ�ōŏI�N�ȉ��Ȃ�q�b�g�I
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// ��ϊ��e�[�u���ɊY�����R�[�h����������
			if (i < WarekiTbl.length) {
				// �a��N���v�Z���ċ��߂܂�
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// �a��+�a��N��Ԃ��܂�
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "�N";
			} else {
				// �Y�����Ȃ���΃G���[
				return null;
			}
		} catch (NumberFormatException e) { // �p�����[�^�������łȂ���΃G���[
			return null;
		}
	}
	
//2005/01/16 add end by miaomiao

   /**
	* HTTP�p�̓��t�t�H�[�}�b�g��Ԃ��B�iRFC 1123�j
	* @return String
	*/
   public String getHTTPDate() {
	   String pattern = "E, dd MMM yyyy hh:mm:ss zzz";
	   SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
	   sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	   return sdf.format(cal.getTime());
   }
}