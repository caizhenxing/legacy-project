/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * @author yoshikawa_h
 *
 */
public class ShinseishaListForm extends BaseValidatorForm {
	
	BaseSearchForm base = new BaseSearchForm();
	
	/** �����Ҕԍ� */
	//�z��͂P�y�[�W�̍ő�\���������Ƃ�B
	private String[] kenkyuNo = new String[base.getPageSize()];
	
	
	//2005/04/20 �ǉ� ��������-------------------------------------
	//���R ���W�I�{�^���̃`�F�b�N���ʗp�ɒǉ�
	private String selectRadioButton = new String();
	//�ǉ� �����܂�------------------------------------------------

	/**
	 * @return kenkyuNo ��߂��܂��B
	 */
	public String[] getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo ��ݒ�B
	 */
	public void setKenkyuNo(String[] kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	
	//2005/04/20 �ǉ� ��������-------------------------------------
	//���R ���W�I�{�^���̃`�F�b�N���ʗp�ɒǉ�
	/**
	 * @return selectRadioButton ��߂��܂��B
	 */
	public String getSelectRadioButton() {
		return selectRadioButton;
	}

	/**
	 * @param selectRadioButton selectRadioButton��ݒ�B
	 */
	public void setSelectRadioButton(String selectRadioButton) {
		this.selectRadioButton = selectRadioButton;
	}
	//�ǉ� �����܂�------------------------------------------------
}
