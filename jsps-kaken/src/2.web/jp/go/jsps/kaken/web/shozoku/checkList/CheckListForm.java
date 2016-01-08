/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;

/**
 * 
 * @author masuo_t
 *
 */
public class CheckListForm extends BaseForm {
	
	/** ���Ƃh�c */
	private String jigyoId;

	/** �`�F�b�N���X�g�� */
	private String edition;

	//	2005/04/11 �ǉ� ��������--------------------------------------------------
	//�L�������̒ǉ�
	/** �w�U�L������ */
	private boolean period;
	//	�ǉ� �����܂�-------------------------------------------------------------
	
// 20050715 �����ґ��݂̒ǉ�
    /** �����ґ��݃t���O */
	private boolean blnKenkyushaExist;
// Horikoshi

	/** ���Ƌ敪 */
	private String jigyoKbn;

    //add start ly 2006/06/01
    /** ����CD */
    private String jigyoCd;
    //add end ly 2006/06/01

    /**
	 * ���Ƌ敪��Ԃ�
	 * @return
	 */
	public String getJigyoKbn(){
		return jigyoKbn;
	}
	
	/**
	 * ���Ƌ敪��ݒ肷��
	 * @param str 
	 */
	public void setJigyoKbn(String str){
		jigyoKbn = str;
	}
	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @return
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param string
	 */
	public void setEdition(String string) {
		edition = string;
	}

	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//���R�@�L�������̒ǉ�
	/**
	 * @return
	 */
	public boolean isPeriod() {
		return period;
	}

	/**
	 * @param b
	 */
	public void setPeriod(boolean b) {
		period = b;
	}
	//	�ǉ� �����܂�-------------------------------------------------------------

// 20050715 �����ґ��݂̒ǉ�
    /**
     * �����ґ��݃t���O���擾
     * @return boolean �����ґ��݃t���O
     */
	public boolean isKenkyushaExist() {
		return blnKenkyushaExist;
	}
    /**
     * �����ґ��݃t���O��ݒ�
     * @param blnExist �����ґ��݃t���O
     */
	public void setKenkyushaExist(boolean blnExist) {
		blnKenkyushaExist = blnExist;
	}
// Horikoshi

    /**
     * ����CD���擾
     * @return String ����CD
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
}