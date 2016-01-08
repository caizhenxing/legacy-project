/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * �`�F�b�N�p���[�e�B���e�B�N���X�B
 * ID RCSfile="$RCSfile: StringUtil.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/11 09:36:24 $"
 */
public class StringUtil {
	
	private static final char[] HANKAKU_SUJI = {'0','1','2','3','4','5','6','7','8','9'};
	private static final char[] ZENKAKU_SUJI = {'�O','�P','�Q','�R','�S','�T','�U','�V','�W','�X'};
	private static final String ZENKAKU_KIGO = "�I�h���������f�i�j���{�C�|�D�^�A�B�E";
	static final char ZEN_SPACE = '�@';		//�S�p�X�y�[�X
	
//ADD�@START 2007/07/11 BIS �����_ ������������ʏ�̃J�^�J�i(�@�B�D�F�H�������b������)  �啶�� (�A�C�E�G�I�������c�J�P��)
	private static final char[] KOGAKI_KATAKANA = {'�@','�B','�D','�F','�H','��','��','��','�b','��','��','��'};
	private static final char[] OOMOJI_KATAKANA = {'�A','�C','�E','�G','�I','��','��','��','�c','�J','�P','��'};
	
	/**
	 * �w��̕�����̒�����A������������啶���ɕϊ�����B
	 * ����ȊO�͂��̂܂܁B
	 * @param s
	 * @return
	 */
	public static final String toOomojiDigit(String s){
		if(s == null){
			return null;
		}
		for(int i=0; i<KOGAKI_KATAKANA.length; i++){
			s = s.replace(KOGAKI_KATAKANA[i], OOMOJI_KATAKANA[i]);
		}
		return s;
	}
		
//ADD�@START 2007/07/11 BIS �����_
	
	/**
	 * �p�����[�^�����p�������ǂ����𔻒肷��B
	 * �p�����[�^�����p�p���A���p�L���A���p�J�i�̂����ꂩ�̏ꍇ��
	 * true��Ԃ��B�S�p�������܂܂�Ă���ꍇ��false��Ԃ��B
	 * �p�����[�^��null�̏ꍇ��false��Ԃ��B
	 * @param text ���肷�镶����
	 * @return ���p���������ō\������Ă���Ƃ� true
	 */
	public static final boolean isHankaku(final String text) {
		if (text == null) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!isHankaku(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * �p�����[�^���S�p�������ǂ����𔻒肷��B
	 * �p�����[�^���S�p�����̏ꍇ��true��Ԃ��B
	 * ���p�p���A���p�L���A���p�J�i�̂����ꂩ���܂܂�Ă���ꍇ��false��Ԃ��B
	 * �p�����[�^��null�̏ꍇ��false��Ԃ��B
	 * @param text ���肷�镶����
	 * @return �S�p���������ō\������Ă���Ƃ� true
	 */
	public static final boolean isZenkaku(final String text) {
		if (text == null) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (isHankaku(c)) {
				return false;	//1�����ł����p���܂܂�Ă����ꍇfalse
			}
		}
		return true;
	}
	
	/**
	 * �p�����[�^�����p�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return ���p�����̂Ƃ� true
	 */
	public static final boolean isHankaku(final char c) {
		return isAscii(c) || isKana(c);
	}

	/**
	 * �p�����[�^�����p�p�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �p�����̂Ƃ� true
	 */
	public static final boolean isLetterOrDigit(final char c) {
		return isLetter(c) || isDigit(c);
	}
	
	//2005/04/28 �ǉ� ��������--------------------------------------------
	//���R ���p�p�����̕�����`�F�b�N�̒ǉ�
	/**
	 * �p�����[�^�����p�p�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �p�����̂Ƃ� true
	 */
	public static final boolean isLetterOrDigit(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(!StringUtil.isLetterOrDigit(c[i])){
				return false;
			}			
		}
		return true;
	}
	//�ǉ� �����܂�-------------------------------------------------------

	/**
	 * �p�����[�^�����p�p�����ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �p���̂Ƃ� true
	 */
	public static final boolean isLetter(final char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	/**
	 * �p�����[�^��(�S�p�܂��͔��p)�J�i���ǂ����𔻒肷��B
	 * @param s ���肷�镶��
	 * @return �J�i�̂Ƃ� true
	 */
	public static final boolean isKana(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(isZenkakuKana(c[i]) || isKigo(c[i])){
				continue;
			}			
			return false;
		}
		return true;
	}
	
	/**
	 * �p�����[�^�����p�J�i���ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return ���p�J�i�̂Ƃ� true
	 */
	private static final boolean isKana(final char c) {
		return c >= '\uff61' && c <= '\uff9f'; // � �` �
	}

	/**
	 * �p�����[�^���S�p�J�i���ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �S�p�J�i�̂Ƃ� true
	 */
	public static final boolean isZenkakuKana(final char c) {
        return c == '�[' || (c >= '\u30a1' && c <= '\u30f6') || c == ZEN_SPACE;// � �` �
	}

	/**
	 * �p�����[�^���S�p�L�����ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return�@�L���̎� true
	 */
	private static final boolean isKigo(final char c){
		int index = ZENKAKU_KIGO.indexOf(c);
		return index != -1;
	}
	
	/**
	 * �p�����[�^�����p�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �����̂Ƃ� true
	 */
	public static final boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * �p�����[�^�����p�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return �����̂Ƃ� true
	 */
	public static final boolean isDigit(final String s) {
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++){
			if(!StringUtil.isDigit(c[i])){
				return false;
			}			
		}
		return true;
	}
	
	/**
	 * �p�����[�^�����pASCII�������ǂ����𔻒肷��B
	 * @param c ���肷�镶��
	 * @return ���p�����̂Ƃ� true
	 */
	public static final boolean isAscii(final char c) {
		return c < '\u0080';
	}

	/**
	 * �p�����[�^�������͂��ǂ����𔻒肷��B
	 * @param text ���肷�镶����
	 * @return �����͂ł���Ƃ� true
	 */
	public static final boolean isBlank(final String text) {
		return (text == null || text.length() == 0);
	}

	/**
	 * �p�����[�^��yyyy/MM/dd���ǂ����𔻒肷��B
 	 * @param text ���肷�镶����
	 * @return ���t�Ƃ��Đ�����������ō\������Ă���Ƃ� true
	 */
	public static final boolean isDate(final String text) {
		if (!new DateUtil().setCal(text, "/")) {
			return false;
		}
		return true;
	}
	
	/**
	 * delimiter�ŕ�����𕪊����A������z���Ԃ��B
	 * @param aString			�������镶����	
	 * @param delimiter			��؂蕶��
	 * @return					delimiter�ŕ�����𕪊����A�z��
	 */
	public static String[] delimitedListToStringArray(String aString, String delimiter) {
		if (aString == null)
			return new String[0];
		if (delimiter == null)
			return new String[]{aString};

		List l = new LinkedList();
		int pos = 0;
		int delpos = 0;
		while ((delpos = aString.indexOf(delimiter, pos)) != -1) {
			l.add(aString.substring(pos, delpos));
			pos = delpos + delimiter.length();
		}
		if (pos <= aString.length()) {
			l.add(aString.substring(pos));
		}

		return (String[]) l.toArray(new String[l.size()]);
	}
	
	
	/******************************************************
	 * ������u�����\�b�h<!--.--><br>
	 * �����񓯎m���ċA�I�ɒu������B<br>
	 * @param  str   ������
	 * @param  str1  �u���Ώە�����
	 * @param  str2  �u�����镶����
	******************************************************/
	public static String substrReplace(String str, String str1, String str2) {
		
		int i = str.indexOf(str1);
		
		if (i==-1) {
			return str;
		}else {
			String retStr = new StringBuffer()
							.append(str.substring(0, i))
							.append(str2)
							.append(substrReplace(str.substring(i+str1.length()), str1, str2))
							.toString();
			return retStr;
		}
	}
	
	
	/**
	 * ������z���CSV�`���ŕԂ��B<br>
	 * ������z��[null]�̏ꍇ�́A��O�𔭐�������B<br>
	 * ������z��v�f����[0]��SQL�G�X�P�[�v��false�̏ꍇ�́A��̕������Ԃ��B<br>
	 * ������z��v�f����[0]SQL�G�X�P�[�v��true�̏ꍇ�́A['']��Ԃ��B<br>
	 * @param array   CSV�ɕϊ����镶����v�f
	 * @param escape  ������ɑ΂���SQL�G�X�P�[�v���s���ꍇ��[true]
	 * @throws IllegalArgumentException ������z��null�̏ꍇ
	 * @return
	 */
	public static String changeArray2CSV(String[] array, boolean escape)
		throws IllegalArgumentException
	{
		if(array == null){
			throw new IllegalArgumentException("changeArray2CSV() Error occured. array="+array);
		}else if(array.length == 0){
			if(escape){
				return "''";
			}else{
				return "";
			}
		}
		
		String s = (escape ? EscapeUtil.toSqlString(array[0]) : array[0]);
		StringBuffer buf = new StringBuffer("'" + s + "'");
		for(int i=1; i<array.length; i++){
			String ss = (escape ? EscapeUtil.toSqlString(array[i]) : array[i]);
			buf.append(",'")
			   .append(ss)
			   .append("'");
		}
		return buf.toString();
		
	}
	
	
	
	/**
	 * �����񔽕��q��CSV�`���ŕԂ��B<br>
	 * ������z��v�f����[0]��SQL�G�X�P�[�v��false�̏ꍇ�́A��̕������Ԃ��B<br>
	 * ������z��v�f����[0]SQL�G�X�P�[�v��true�̏ꍇ�́A['']��Ԃ��B<br>
	 * @param iterator   CSV�ɕϊ����镶���񔽕��q
	 * @param escape     ������ɑ΂���SQL�G�X�P�[�v���s���ꍇ��[true]
	 * @throws IllegalArgumentException �����񔽕��q��null�̏ꍇ
	 * @return
	 */
	public static String changeIterator2CSV(Iterator iterator, boolean escape)
		throws IllegalArgumentException
	{
		if(iterator == null){
			throw new IllegalArgumentException("changeIterator2CSV() Error occured. iterator="+iterator);
		}else if(!iterator.hasNext()){
			if(escape){
				return "''";
			}else{
				return "";
			}
		}
		
		String s = (String)iterator.next();
		s = (escape ? EscapeUtil.toSqlString(s) : s);
		
		StringBuffer buf = new StringBuffer("'" + s + "'");
		while(iterator.hasNext()){
			String ss = (String)iterator.next();
			ss = (escape ? EscapeUtil.toSqlString(ss) : ss);
			buf.append(",'")
			   .append(ss)
			   .append("'");
		}
		return buf.toString();
		
	}	
	
	
	/**
	 * �����񂩂琔�l�֕ϊ�����B
	 * ������null�A�܂��͐��l�ł͂Ȃ�������̏ꍇ�́A������[0]��Ԃ��B
	 * @param s
	 * @return
	 */
	public static int parseInt(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return 0;
		}
	}
	
	
	
	/**
	 * �n���ꂽ�I�u�W�F�N�g���`�F�b�N���Anull �������ꍇ�ɂ͋�̕������Ԃ��܂��B
	 * null�ł͂Ȃ������ꍇ�A�n���ꂽ�I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * @param obj
	 * @return
	 */
	public static String defaultString(Object obj){
		return defaultString(obj, "");
	}
	
	
	
	/**
  	 * �n���ꂽ�I�u�W�F�N�g���`�F�b�N���Anull �������ꍇ�ɂ�
  	 * �������̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
  	 * �������̃I�u�W�F�N�g�� null �������ꍇ�� null ��Ԃ��܂��B
	 * @param obj
	 * @param defaultObject
	 * @return
	 */
	public static String defaultString(Object obj, Object defaultObject){
		if(obj == null){
			return (defaultObject == null) ? null : defaultObject.toString();
		}else{
			return obj.toString();
		}
	}	
	
	
	/**
	 * �w��̕�����̒�����A�S�p�����𔼊p�����ɕϊ�����B
	 * ����ȊO�͂��̂܂܁B
	 * @param s
	 * @return
	 */
	public static final String toHankakuDigit(String s){
		if(s == null){
			return null;
		}
		for(int i=0; i<ZENKAKU_SUJI.length; i++){
			s = s.replace(ZENKAKU_SUJI[i], HANKAKU_SUJI[i]);
		}
		return s;
	}
	
	
	
	/**
	 * �w�肳�ꂽ����[?]�}�[�N���R���}�Ō��т��ĕԂ��B
	 * @param count
	 * @return
	 */
	public static String getQuestionMark(int count){
		return getQuestionMark(count, ",");
	}
	
	
	
	/**
	 * �w�肳�ꂽ����[?]�}�[�N��delim�Ō��т��ĕԂ��B
	 * @param count
	 * @param delim ��؂蕶��
	 * @return
	 */
	public static String getQuestionMark(int count, String delim){
		StringBuffer buf = new StringBuffer("?");
		for(int i=1; i<count; i++){
			buf.append(delim + "?");
		}
		return buf.toString();
	}	
	
	/**
	 * �󕶎���ł��邩�`�F�b�N����<br>
	 * �S�p�󔒁A���p�󔒂��󕶎��Ƃ���
	 * @param  str   ������
	 * @return true ������ł�
	*/
	public static boolean isSpaceString(String str) {

		if (str == null || str.length() == 0)
			return true;

		//�󔒂����O��
		String retStr = str.replaceAll("�@", "").trim();
		
		return retStr.length() == 0;
	}
	
	/**
	 * �p�����[�^�̍��E�󔒂������������͂��ǂ����𔻒肷��B
	 * @param text ���肷�镶����
	 * @return �����͂��X�y�[�X�݂̂ł���Ƃ� true
	 */
	public static final boolean isEscapeBlank(final String text) {
		return (text == null || text.trim().length() == 0);
	}
	
	//2005.09.12 iso �g�D�\���z�v�Z�C���̂��ߒǉ�
	/**
	 * �����񂩂琔�l(long�^)�֕ϊ�����B
	 * ������null�A�܂��͐��l�ł͂Ȃ�������̏ꍇ�́A������[0]��Ԃ��B
	 * @param s
	 * @return
	 */
	public static long parseLong(String s){
		try{
			return Long.parseLong(s);
		}catch(Exception e){
			return 0;
		}
	}
	
	//2005.11.02 iso �ǉ�
	/**
	 * �p�����[�^�ɖ����͂����邩�ǂ����𔻒肷��B
	 * @param texts ���肷�镶���z��
	 * @return �����͂ł���Ƃ� true
	 */
	public static final boolean isBlank(final String[] texts) {
		if(texts == null) {
			return false;
		}
		for(int i = 0; i < texts.length; i++) {
			if(isBlank(texts[i])) {
				return true;
			}
		}
		return false;
	}

// 2006/02/10 Start	
	/**
	 * �[����������i�����ŕs���̏ꍇ�́A�擪��"0"��������j
	 *
	 * ������null�̏ꍇ�̓X�y�[�X��ԋp�B
	 * @param str ������
	 * @param len �����񌅐�
	 * @return String�@�ҏW����������
	 */
	public static final String fillLZero(String str, int len){
		if (str == null) {
			str = "";
        } else {
			str = str.trim();
		}
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < len - str.length(); i++) {
        	buffer.append('0');
        }
        buffer.append(str);
        return buffer.toString();	
	}
// �c End
// 2006/06/29�@�c�@�ǉ���������
    /**
     * �p�����[�^�����p��p�������ǂ����𔻒肷��B
     * @param c ���肷�镶��
     * @return �啶�p���̂Ƃ� true
     */
    public static final boolean isCapitalLetter(final char c) {
        return (c >= 'A' && c <= 'Z');
    }
    
    /**
     * �p�����[�^���X�y�[�X�������ǂ����𔻒肷��B
     * @param c ���肷�镶��
     * @return �X�y�[�X�����̂Ƃ� true
     */
    public static final boolean isSpaceLetter(final char c) {
        return (c == ' ');
    }
//�@2006/06/29�@�c�@�ǉ������܂�

// 2006/07/21 dyh add start
    /**
     * null���甼�p������i�P�a�������j�֕ϊ�����B
     * @param str
     * @param len
     * @return String
     */
    public static final String convertNullToHanSpace(String str){
        if(isBlank(str)){
            return " ";
        }
        return str;
    }
}