/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

import java.io.*;
import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.collections.*;

/**
 * �ݒ�t�@�C���N���X�B���\�[�X�o���h�����ݒ�����擾����B
 * 
 * ID RCSfile="$RCSfile: ApplicationSettings.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class ApplicationSettings {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���\�[�X�o���h�� */
	private static final ResourceBundle RESOURCE_BUNDLE =
		ResourceBundle.getBundle(ISettingKeys.BUNDLE_NAME);
	
	/** ���I���ڃp�����[�^����List */
	private static FastArrayList paramList    = null;
	
	/** ���I���ڂ̒l��List */
	private static FastArrayList variableList = null;
	
	
	//---------------------------------------------------------------------
	// Static initialize
	//---------------------------------------------------------------------
	static{
		paramList    = new FastArrayList();
		variableList = new FastArrayList();
		for(int i=0;;i++){
			try{
				//���I���ڃp�����[�^��
				String keyParam   = ISettingKeys.VARIABLE_PARAM + "[" + i + "]";
				String valueParam = RESOURCE_BUNDLE.getString(keyParam); 
				//���I���ڂ̒l���擾
				String keyValue   = ISettingKeys.VARIABLE_VALUE + "[" + i + "]";
				String valueValue = RESOURCE_BUNDLE.getString(keyValue);
				//���I���ڂ̃p�����[�^���ƒl���Z�b�g�i�����Z�b�g����Ă���΁j
				paramList.add(valueParam);
				variableList.add(StringUtil.defaultString(valueValue));
			}catch(MissingResourceException e){
				break;
			}
		}
		paramList.setFast(true);
		variableList.setFast(true);	
	}	
	
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �f�t�H���g�R���X�g���N�^�B
	 */
	private ApplicationSettings() {
	}
	
	
	/**
	 * ���\�[�X�o���h������w��̃L�[�̒l���擾����B
	 * ���I���ځuVARIABLE_PARAM�v�uVARIABLE_VALUE�v�����݂����ꍇ�͒u������B
	 * @param key
	 * @return
	 */
	private static String getBundleString(String key){
		String value = RESOURCE_BUNDLE.getString(key);
		for(int i=0; i<paramList.size(); i++){
			String param    = (String)paramList.get(i);					//�ϊ��Ώە�����
			String variable = (String)variableList.get(i);				//�ϊ��㕶����
			value = StringUtil.substrReplace(value, param, variable);	//�ϊ�����
		}
		return value;
	}
	
	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * �L�[�ɊY�����郁�b�Z�[�W���擾����B
	 * @param key	�L�[
	 * @return	���b�Z�[�W
	 */
	public static String getString(String key) throws SystemException {
		return getString(key, true);
	}
	
	/**
	 * �L�[�ɊY�����郁�b�Z�[�W���擾����B
	 * check��[true] �̏ꍇ�A�L�[�ɊY������l�����݂��Ȃ��������O��Ԃ��B
	 * check��[false]�̏ꍇ�A�L�[�ɊY������l�����݂��Ȃ�������null��Ԃ��B
	 * @param key		�L�[
	 * @param check	�L�[�ɊY������l�����݂��邩�̃`�F�b�N�t���O
	 * @return			���b�Z�[�W
	 */
	public static String getString(String key, boolean check) throws SystemException {
		try {
			return getBundleString(key);
		} catch (MissingResourceException e) {
			if(check){
				throw new SystemException(
					"�uApplicationSettings.properties�v�ɕϐ��u" + key + "�v�����݂��܂���B",
					e);
			}else{
				return null;
			}
		}
	}

	/**
	 * ���b�Z�[�W�L�[�ɊY������l�𐔒l�Ŏ擾����B
	 * @param key	�L�[		
	 * @return		�l
	 */
	public static Integer getInteger(String key) {
		try {
			return new Integer(getBundleString(key));
		} catch (NumberFormatException e) {
			throw new SystemException(
				"�uApplicationSettings.properties�v�̕ϐ��u" + key + "�v���s���ł��B",
				e);
		}
	}
	
	/**
	 * ���b�Z�[�W�L�[�ɊY������l�𐔒l�Ŏ擾����B
	 * @param key	�L�[		
	 * @return		�l
	 */
	public static int getInt(String key){
		return getInteger(key).intValue();
	}
	
	/**
	 * ���b�Z�[�W�L�[�ɊY������l��boolean�Ŏ擾����B
	 * @param key	�L�[		
	 * @return		�l
	 */
	public static boolean getBoolean(String key) {
		return new Boolean(getBundleString(key)).booleanValue();
	}
	
	/**
	 * ���b�Z�[�W�L�[�ɊY������t�@�C���I�u�W�F�N�g���擾����B
	 * @param key	�L�[
	 * @return	�t�@�C���I�u�W�F�N�g
	 */
	public static File getFile(String key) throws SystemException {
		try {
			String value = getBundleString(key);
			return new File(value);
		} catch (MissingResourceException e) {
			throw new SystemException(
				"�uApplicationSettings.properties�v�ɕϐ��u" + key + "�v�����݂��܂���B",
				e);
		}
	}
	
	/**
	 * ���b�Z�[�W�L�[�ɊY������l���擾����B
	 * @param key	�J���}������Ŏw�肳�ꂽ�L�[
	 * @return		������z��
	 */
	public static String[] getStrings(String key) {
		List list = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(getString(key), ",");
		while (tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	
	
	
}
