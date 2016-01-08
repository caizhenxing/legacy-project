/*
 * Created on 2005/03/30
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Collection;

/**
 * �`�F�b�N���X�g��������ێ�����N���X
 * @author masuo_t
 */
public class CheckListSearchInfo extends SearchInfo {

	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//���R ��ID��03,04�ȊO�̏ꍇ�ɑΉ����邽��
	
//	/** ��ID�F�쐬�� */
//	public static final String SAKUSEITYU = "01";
//	/** ��ID�F�\�������m�F */
//	public static final String MIKAKUNIN = "02";
//	/** ��ID�F�����@�֎�t�� */
//	public static final String SHOZOKU_UKETUKE = "03";
//	/** ��ID�F�w�U��t�� */
//	public  static final String GAKUSIN_UKETUKE = "04";
//	/** ��ID�F�����@�֋p�� */
//	public static final String SHOZOKU_KYAKKA = "05";
//	/** ��ID�F�w�U�� */
//	public static final String GAKUSIN_JYURI = "06";
//	/** ��ID�F�w�U�s�� */
//	public static final String GAKUSIN_HUJYURI = "07";
//	/** ��ID�F�R��������U�菈���� */
//	public static final String SHINSAIN_WARIHURI = "08";
//	/** ��ID�F����U��`�F�b�N���� */
//	public static final String WARIHURI_CHECK = "09";
//	/** ��ID�F�ꎟ�R���� */
//	public static final String ITIJI_SINSA ="10";
//	/** ��ID�F�ꎟ�R��:���� */
//	public static final String ITIJI_HANTEI ="11";
//	/** ��ID�F�񎟐R������ */
//	public static final String NIJI_SINSA ="12";
//// 2006/06/15 dyh add start
//    /** ��ID�F�̈��\�Ҋm�F�� */
//    public static final String RYOUIKIDAIHYOU_KAKUNIN ="21";
//    /** ��ID�F�̈��\�ҋp�� */
//    public static final String RYOUIKIDAIHYOU_KYAKKA ="22";
//    /** ��ID�F�̈��\�Ҋm��ς� */
//    public static final String RYOUIKIDAIHYOU_KAKUTEIZUMI ="23";
//    /** ��ID�F�̈��\�ҏ��������@�֎�t�� */
//    public static final String RYOUIKISHOZOKU_UKETUKE ="24";
//// 2006/06/15 dyh add end

	//	�ǉ� �����܂�-------------------------------------------------------------

	/** ����CD */
	private String shozokuCd;
	
	/** ����ID */
	private String jigyoId;
	
	/** �`�F�b�N���X�g�� */
	private String edition;
	
	/** �X�V�O��ID */
	private String jokyoId;
	
	/** �X�V�p��ID */
	private String changeJokyoId;
	
	/** �����p��ID */
	private String[] searchJokyoId;

	//2005/04/12 �ǉ� ��������---------------------------------------------------
	//����CD�̒ǉ�
	
	/** ����CD */	
	private String jigyoCd;
    
    /** ���ƃR�[�h�i�����j*/
    private Collection tantoJigyoCd;
	
	//�ǉ� �����܂�--------------------------------------------------------------
	
	//2005/04/14 �ǉ� ��������---------------------------------------------------
	//�񐔂̒ǉ�
	/** �� */
	private String kaisu;
	//�ǉ� �����܂�--------------------------------------------------------------
	
	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����
	
	/** �m������t���O */
	private String cancellationFlag;
	
	/** �����@�֖� */
	private String shozokuName;
	
// 20050606 Start ���������Ɏ��Ƌ敪��ǉ�
	/** ���Ƌ敪 */
	private String JigyoKubun;
// Horikoshi

// �󗝁A�s�󗝃R�����g�ǉ�
	private String juriComment;
// Horikoshi

	//�ǉ� �����܂�-------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
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

	/**
	 * @return
	 */
	public String getChangeJokyoId() {
		return changeJokyoId;
	}

	/**
	 * @return
	 */
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @param string
	 */
	public void setChangeJokyoId(String string) {
		changeJokyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJokyoId(String string) {
		jokyoId = string;
	}

	/**
	 * @return
	 */
	public String[] getSearchJokyoId() {
		return searchJokyoId;
	}

	/**
	 * @param strings
	 */
	public void setSearchJokyoId(String[] strings) {
		searchJokyoId = strings;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	//2005/04/21 �ǉ� ��������--------------------------------------------------
	//���R ���������ɏ����@�֖��Ǝ󗝏󋵂��ǉ����ꂽ����

	/**
	 * @return
	 */
	public String getCancellationFlag() {
		return cancellationFlag;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @param string
	 */
	public void setCancellationFlag(String string) {
		cancellationFlag = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	//�ǉ� �����܂�-------------------------------------------------------------
	
// 20050606 Start ���������Ɏ��Ƌ敪��ǉ�
	/**
	 * @return jigyoKubun ��߂��܂��B
	 */
	public String getJigyoKubun() {return JigyoKubun;}
	/**
	 * @param jigyoKubun ��ݒ�B
	 */
	public void setJigyoKubun(String jigyoKubun) {JigyoKubun = jigyoKubun;}
// Horikoshi

	public String getJuriComment() {
		return juriComment;
	}
	public void setJuriComment(String juriComment) {
		this.juriComment = juriComment;
	}

    /**
     * @return Returns the tantoJigyoCd.
     */
    public Collection getTantoJigyoCd() {
        return tantoJigyoCd;
    }

    /**
     * @param tantoJigyoCd The tantoJigyoCd to set.
     */
    public void setTantoJigyoCd(Collection tantoJigyoCd) {
        this.tantoJigyoCd = tantoJigyoCd;
    }
}