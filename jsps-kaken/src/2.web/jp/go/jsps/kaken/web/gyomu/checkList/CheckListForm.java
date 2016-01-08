/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckListForm.java
 *    Description : �`�F�b�N���X�g�p�t�H�[���B
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;


/**
 * �`�F�b�N���X�g�p�t�H�[��
 * 
 * @author masuo_t
 */
public class CheckListForm extends BaseForm {
	
	/** ����ID */
	private String jigyoId;

	/** �`�F�b�N���X�g�� */
	private String edition;

	/** �w�U�L������ */
	private boolean period;

	/** �����R�[�h */
	private String shozokuCd;
	
	/** �� */
	private String kaisu;

// 20050719
    /** ��ID */
	private String jokyoId;
// Horikoshi

/************************************************************************************/

	/**
     * ����ID���擾
	 * @return ����ID
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * ����ID��ݒ�
	 * @param string ����ID
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
     * �`�F�b�N���X�g�ł��擾
	 * @return �`�F�b�N���X�g��
	 */
	public String getEdition() {
		return edition;
	}

	/**
     * �`�F�b�N���X�g�ł�ݒ�
	 * @param string �`�F�b�N���X�g��
	 */
	public void setEdition(String string) {
		edition = string;
	}

	/**
     * �w�U�L���������擾
	 * @return �w�U�L������
	 */
	public boolean isPeriod() {
		return period;
	}

	/**
     * �w�U�L��������ݒ�
	 * @param b �w�U�L������
	 */
	public void setPeriod(boolean b) {
		period = b;
	}

	/**
     * �����R�[�h���擾
	 * @return �����R�[�h
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
     * �����R�[�h��ݒ�
	 * @param string �����R�[�h
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
     * �񐔂��擾
	 * @return ��
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
     * �񐔂�ݒ�
	 * @param string ��
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

// 20050719
    /**
     * ��ID���擾
     * @return ��ID
     */
	public String getJokyoId() {
		return jokyoId;
	}

    /**
     * ��ID��ݒ�
     * @param jokyoId ��ID
     */
	public void setJokyoId(String jokyoId) {
		this.jokyoId = jokyoId;
	}
// Horikoshi
}