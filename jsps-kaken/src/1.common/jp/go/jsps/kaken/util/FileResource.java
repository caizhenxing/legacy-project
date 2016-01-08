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

import java.io.File;

/**
 * �t�@�C���̑���M�Ŏg�p���郊�\�[�X�N���X�B
 * 
 * ID RCSfile="$RCSfile: FileResource.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class FileResource implements java.io.Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------


	/** �V�X�e���Ɉˑ�����p�X��؂蕶�� */
	public static final char pathSeparatorChar = File.pathSeparatorChar;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	private String path; // �p�X
	private byte[] binary; // �o�C�i��
	private long time; // �ŐV�̕ύX���ꂽ����

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * FileResource���쐬����B
	 */
	public FileResource() {
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * �p�X��Ԃ��B
	 * @return �p�X
	 */
	public String getPath() {
		return path;
	}

	/**
	 * �p�X��ݒ肷��B
	 * @param path �p�X
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * ���̒��ۃp�X���������t�@�C���܂��̓f�B���N�g���̖��O��Ԃ��B
	 * @return �t�@�C�����B
	 */
	public String getName() {
		return new File(path).getName();
	}

	/**
	 * �o�C�i����Ԃ��B
	 * @return �o�C�i��
	 */
	public byte[] getBinary() {
		return binary;
	}

	/**
	 * �o�C�i����ݒ肷��B
	 * @param binary �o�C�i��
	 */
	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

	/**
	 * �t�@�C�����Ō�ɕύX���ꂽ������Ԃ��B
	 * @return �ŐV�̕ύX���ꂽ����
	 */
	public long lastModified() {
		return time;
	}

	/**
	 *	�t�@�C�����ύX���ꂽ������ݒ肷��B
	 *	@param time �ŐV�̕ύX���ꂽ����
	 */
	public void setLastModified(long time) {
		this.time = time;
	}

}