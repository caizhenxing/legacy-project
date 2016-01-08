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

import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.util.StringUtil;

/**
 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ�����N���X�B
 * ID RCSfile="$RCSfile: Escape.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/06/29 09:40:13 $"
 */
public class Escape {

	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * @param aObject
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getText(Object aObject) {
		if (aObject == null)
			return "";
		if (aObject instanceof String) {
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}

	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * @param aObject
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getNullToSpace(Object aObject) {
		if (aObject == null)
			return " ";
		if (aObject instanceof String) {
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}
	
    /**
     * URI������̎w�蕶����ϊ����郁�\�b�h
     * @param aUrl
     * @param fromString
     * @param toString
     * @return
     */
    public static String replace(String aUrl,String fromString,String toString) {
        return aUrl.replaceFirst(fromString,toString);  
    }

	
	/**
	 * ������𐔒l�`���ɕϊ����郁�\�b�h
	 * ���l�ɕϊ��ł��Ȃ��ꍇ��0��߂��B
	 * @param s
	 * @return
	 */
	public static int getIntValue(String s){
		int retValue = 0;
		try{
			if(s != null){
				retValue = Integer.parseInt(s);
			}
		}catch(NumberFormatException e){
			//�������Ȃ�
		}
		return retValue;
	}
	

	//2005.11.01 iso �ǉ�
	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * ��̏ꍇ�A�x�����b�Z�[�W�ɕϊ��B
	 * @param aObject
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getLabel(Object aObject) {
		return getLabel(aObject, "<font color=\"#ff0000\"><b>�����͂ł�</b></font>");
	}

	//2005.11.01 iso �ǉ�
	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * ��̏ꍇ�A�x�����b�Z�[�W�ɕϊ��B
	 * @param aObject
	 * @param message
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getLabel(Object aObject, String message) {
		if (aObject == null) {
			return "";
		}
		if (aObject instanceof String) {
			if(aObject.toString().equals("")) {
				return message;
			}
			return org.apache.velocity.anakia.Escape.getText((String) aObject);
		}
		return aObject;
	}

	//2005.11.11 iso �ǉ�
	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * List�ɑ�3�����̒l���܂܂�Ă��Ă�(���̑��̎�)�ŋ�̏ꍇ�A�x�����b�Z�[�W�ɕϊ��B
	 * @param ���X�g
	 * @param aObject
	 * @param ���̑��̒l
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getText(List labelList, Object aObject, String other) {
		return getText(labelList, aObject, other, "<font color=\"#ff0000\"><b>�����͂ł�</b></font>");
	}

	//2005.11.11 iso �ǉ�
	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * List�ɑ�3�����̒l���܂܂�Ă��Ă�(���̑��̎�)�ŋ�̏ꍇ�A�x�����b�Z�[�W�ɕϊ��B
	 * @param ���X�g
	 * @param aObject
	 * @param message
	 * @param ���̑��̒l
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getText(List labelList, Object aObject, String other, String message) {
		if(labelList.isEmpty() || StringUtil.isBlank(other)) {
			return "";
		}
		for(Iterator ite = labelList.iterator(); ite.hasNext();) {
			String labelValue = ite.next().toString();
			if(labelValue.startsWith(other)) {
				return getLabel(aObject, message);
			}
		}
		return getText(aObject);
	}
	
	/**
	 * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
	 * �R���ӌ��\����p�A�ő�150�����܂�
	 * @param aObject
	 * @return	�ϊ���I�u�W�F�N�g
	 */
	public static Object getText150(Object aObject) {
		if (aObject == null)
			return "";
		if (aObject instanceof String) {
			String str = org.apache.velocity.anakia.Escape.getText((String) aObject);
			if (str.length() > 150){
				str = str.substring(0,150) + "�`���";
			}
			return str;
		}
		return aObject;
	}
	
	/**
	 * �p�����[�^�̍��E��(�S�p�Ɣ��p����)�������������͂��ǂ����𔻒肷��B
	 * @param text ���肷�镶����
	 * @return �����͂��X�y�[�X�݂̂ł���Ƃ� true
	 */
	public static final boolean isBlank(final String text) {
		return (text == null || text.length() == 0);
	}

}
