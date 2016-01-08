/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckListForm.java
 *    Description : �󗝓o�^�Ώۉ�����ꗗ�p�t�H�[��
 *
 *    Author      : masuo_t
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    V1.0                       �V�K�쐬
 *    2006/06/06    V1.1        DIS.GongXB     �C���i����CD��ǉ��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;

/**
 * �`�F�b�N���X�g�ꗗ�t�H�[���B
 * @author masuo_t
 */
public class CheckListForm extends BaseForm {
	
	/** ����CD */
	private String jigyoId;

	/** �`�F�b�N���X�g�� */
	private String edition;
	
    /** ���Ƌ敪 */
	private String jigyoKbn;
    
//2006/06/02 gongXiuBin �Y����������
    /** ����CD */
    private String jigyoCd;
//2006/06/02 gongXiuBin �Y�������܂�

    /**
     * ����CD���擾
	 * @return ����CD
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * ����CD��ݒ�
	 * @param string ����CD
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
     * ���Ƌ敪���擾
     * @return ���Ƌ敪
     */
	public String getJigyoKbn() {
		return jigyoKbn;
	}

    /**
     * ���Ƌ敪��ݒ�
     * @param jigyoKbn ���Ƌ敪
     */
	public void setJigyoKbn(String jigyoKbn) {
		this.jigyoKbn = jigyoKbn;
	}

//2006/06/02 gongXiuBin �Y����������
    /**
     * ����CD���擾
     * @return ����CD
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ����CD��ݒ�
     * @param jigyoCd ����CD
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }
//2006/06/02 gongXiuBin �Y�������܂�
}