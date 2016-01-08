/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckListSearchForm.java
 *    Description : �`�F�b�N���X�g�����p�t�H�[��
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

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * �`�F�b�N���X�g�����p�t�H�[��
 * 
 * @author masuo_t
 */
public class CheckListSearchForm extends BaseSearchForm {
	
	/** ����ID */
	private String jigyoId;

	/** �w�U�L������ */
	private boolean period;

	/** ������ږ����X�g */
	private List jigyoList = new ArrayList();

	/** �����R�[�h */
	private String shozokuCd;
	
	/** ����CD */
	private String jigyoCd;
	
	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����
	
	/** �󗝏� */
	private String juriJokyo;

	/** �����@�֖� */
	private String shozokuName;
	
	/** ���Ƌ敪 */
	private String jigyoKbn;

	/** �󗝏󋵃��X�g */
	private List juriList = new ArrayList();

	/** �R���X�g���N�^ */
	public CheckListSearchForm(){
		jigyoId = "";
		shozokuCd = "";
		jigyoCd = "";
		juriJokyo = "0";
		shozokuName = "";	
	}
	//�ǉ� �����܂�-------------------------------------------------------------
	
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
     * ������ږ����X�g���擾
	 * @return ������ږ����X�g
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
     * ������ږ����X�g��ݒ�
	 * @param list ������ږ����X�g
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
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
     * ����CD���擾
	 * @return ����CD
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
     * ����CD��ݒ�
	 * @param string ����CD
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����

	/**
     * �󗝏󋵂��擾
	 * @return �󗝏�
	 */
	public String getJuriJokyo() {
		return juriJokyo;
	}

    /**
     * �󗝏󋵂�ݒ�
     * @param string �󗝏�
     */
    public void setJuriJokyo(String string) {
        juriJokyo = string;
    }

	/**
     * �����@�֖����擾
	 * @return �����@�֖�
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
     * �����@�֖���ݒ�
	 * @param string �����@�֖�
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	
	/**
     * �󗝏󋵃��X�g���擾
	 * @return �󗝏󋵃��X�g
	 */
	public List getJuriList() {
		return juriList;
	}
	
	/**
     * �󗝏󋵃��X�g��ݒ�
	 * @param list �󗝏󋵃��X�g
	 */
	public void setJuriList(List list) {
		juriList = list;
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
}