/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/20
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
 * �\���̃t�H�[�}�b�g���s���B
 * ID RCSfile="$RCSfile: ShinseiFormat.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:59 $"
 */
public class ShinseiFormat {

	private static final String HYPHEN = "-";

	/**
	 * ����ID����\����ʂ��擾����.<br/>
	 * 
	 * �\����ʂ̒�`�͈ȉ��̒ʂ�B<br/>
	 * <table bgcolor="#000000">
	 * <tr bgcolor="#333333"><td><div style="color:white"><b>�\�����</b></div></td><td><div style="color:white"><b>����</b></div></td></tr>
	 * <tr bgcolor="#ffffff"><td>01</td><td>���ʐ��i����</td></tr>
	 * <tr bgcolor="#ffffff"><td>03</td><td>��Ռ���(S)</td></tr>
	 * <tr bgcolor="#ffffff"><td>04_1</td><td>��Ռ���(A)(���)</td></tr>
	 * <tr bgcolor="#ffffff"><td>04_3</td><td>��Ռ���(A)(�C�O�w�p����)</td></tr>
	 * <tr bgcolor="#ffffff"><td>05_1</td><td>��Ռ���(B)(���)</td></tr>
	 * <tr bgcolor="#ffffff"><td>05_3</td><td>��Ռ���(B)(�C�O�w�p����)</td></tr>
	 * <tr bgcolor="#ffffff"><td>06_1</td><td>��Ռ���(C)(���)</td></tr>
	 * <tr bgcolor="#ffffff"><td>06_2</td><td>��Ռ���(C)(��撲��)</td></tr>
	 * <tr bgcolor="#ffffff"><td>11</td><td>�G�茤��</td></tr>
	 * <tr bgcolor="#ffffff"><td>12</td><td>��茤��(A)</td></tr>
	 * <tr bgcolor="#ffffff"><td>13</td><td>��茤��(B)</td></tr>
	 * <tr bgcolor="#ffffff"><td>52</td><td>�w�p�n��������i���E���E��W���j</td></tr>
	 * </table>
	 * 
	 * @param jigyo_id
	 * @return �\�����
	 */
	public static String getShinseiShubetu(String jigyo_id) {
		//2005/03/24 �C�� ------------------------------------------------��������
		//���R ��Վ��Ƃ��ǉ����ꂽ����
		//		return jigyo_id.substring(4, 6);

		String shinseiShubetu = jigyo_id.substring(4, 6);
        String betu = jigyo_id.substring(6, 7);

// 2006/06/29 dyh update start
//		//�敪��04�A05�A06�̏ꍇ�i��Ո�ʁA�C�O�A���j�͎}�Ԃ�t�����ĕԋp
//		if (shinseiShubetu.equals("04") || shinseiShubetu.equals("05")
//                || shinseiShubetu.equals("06")) {
//            return shinseiShubetu + "_" + jigyo_id.substring(6, 7);
        // �敪��02�A04�A05�A06�̏ꍇ�i����̈挤���A��Ո�ʁA�C�O�A���j�͎}�Ԃ�t�����ĕԋp
        if (shinseiShubetu.equals("02") || shinseiShubetu.equals("04")
                || shinseiShubetu.equals("05") || shinseiShubetu.equals("06")) {
            return shinseiShubetu + "_" + betu;
// 2006/06/29 dyh update end
        }
		
// 2006/02/14 Start 
// ���R�@  ���ʌ������i���ǉ�����		
		//�敪��15�̏ꍇ�i���ʌ������i��j�͎}�Ԃ�t�����ĕԋp
		if (shinseiShubetu.equals("15")) {
			if (betu.equals("2") || betu.equals("3") || betu.equals("4")) {
				return shinseiShubetu + "_1";
			}
			if (betu.equals("5") || betu.equals("6")) {
				return shinseiShubetu + "_2";
			}
		}
// Nae End		

// 2006/06/29 dyh add start
// ���R:����̈挤���i�V�K�̈�j�A����̈挤���i�V�K�̈�j�̈�v�揑�T�v��ǉ�����
        // �敪��02�̏ꍇ�i����̈挤���i�V�K�̈�j�A����̈挤���i�V�K�̈�j�̈�v�揑�T�v�j�͎}�Ԃ�t�����ĕԋp
        if("02".equals(shinseiShubetu)){
            return shinseiShubetu + "_" + betu;
        }
// 2006/06/29 dyh add end
        
//2007/02/03 �c�@�ǉ���������@���S�ǉ��̂ŁA �敪��12�̏ꍇ�i���A�Ǝ��S�j�͎}�Ԃ�t�����ĕԋp
        if("12".equals(shinseiShubetu)){
            if (betu.equals("0")) {
                return shinseiShubetu + "_" + betu;
            } else {
                return shinseiShubetu;
            }
        }
//2007/02/03 �c�@�ǉ������܂�        

		return shinseiShubetu;
		//2005/03/24 �C�� ------------------------------------------------�����܂�
	}

	/**
	 * ������N�����ɕϊ�(X������Y�NZ����)����B
	 * ���������l�^�ɕϊ��ł��Ȃ��ꍇ�͋󕶎���Ԃ��B
	 * @param org_tuki
	 * @return
	 */
	public static String getYear(String org_tuki) {
		try{
			return getYear(Integer.parseInt(org_tuki));
		}catch(NumberFormatException e){
			return "";
		}
	}
	
	/**
	 * ������N�����ɕϊ�(X������Y�NZ����)����B
	 * @param org_tuki
	 * @return
	 */
	public static String getYear(int org_tuki) {
		String ret_str = "";

		int nen = org_tuki / 12;
		if (nen != 0) {
			ret_str = nen + "�N";
		}

		int tuki = org_tuki % 12;
		if (tuki != 0) {
			ret_str = ret_str + tuki + "����";
		}

		return ret_str;
	}

	/**
	 * ���X�g�̒��Ɉ�v����l�����邩�𔻒肷��B
	 * @param list
     * @param value
	 * @return boolean
	 */
	public static boolean checkInList(List list, String value) {
		return list.contains(value);
	}

	/**
	 * �����A�U�艼����2�̕������A������B
     * aString�AbString�̗����������Ă���Ƃ��̂ݔ��p�X�y�[�X���Ԃɓ����B
	 * @param aString  
	 * @param bString
	 * @return
	 */
	public static String concat(String aString, String bString) {
		if (StringUtil.isBlank(aString) || StringUtil.isBlank(bString)) {
			return (aString == null ? "" : aString)
				+ (bString == null ? "" : bString);
		} else {
			return aString + " " + bString;
		}
	}

	/**
	 * ���l�������3����؂�̕�����ɕϊ�����B
	 * ���l�ɕϊ��ł��Ȃ��ꍇ�͕���������̂܂ܕԂ��B
	 * ���l�͈̔͂�Long�^�܂őΉ��B
	 * ex.�u9999999999�v���u9,999,999,999�v
	 * @param str
	 * @return
	 */
	public static String numericFormat(String str){
		
		if(str == null || str.length() == 0){
			return str;
		}
		
		DecimalFormat df = new DecimalFormat("#,###");
		try{
			return df.format(new Long(str));
		}catch(Exception e){
			//e.printStackTrace();
			return str;
		}
	}

	/**
	 * �������̃��X�g����A�������Ŏw�肵���C���f�b�N�X�̒l��Ԃ��B
	 * �C���f�b�N�X�l���s���ȏꍇ�͋󕶎���Ԃ��B
	 * @param index
	 * @param list
	 * @return
	 */
	public static String getTextFromList(int index, List list){
		try{
			return (String)list.get(index);
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}
	}	

	/**
	 * �������̃��X�g����A�������Ŏw�肵���C���f�b�N�X�̒l��Ԃ��B
	 * �C���f�b�N�X�l���s���ȏꍇ�͋󕶎���Ԃ��B
	 * @param strIndex
	 * @param list
	 * @return
	 */
	public static String getTextFromList(String strIndex, List list){
		try{
			return (String)list.get(Integer.parseInt(strIndex));
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}
	}	
	
	/**
	 * ��������Ȃ�u-�v��Ԃ��B
	 * @param s
	 * @return
	 */
	public static String getHyphen(String s) {
		if(s == null || "".equals(s)) {
			return HYPHEN;
		} else {
			return s;
		}
	}
}