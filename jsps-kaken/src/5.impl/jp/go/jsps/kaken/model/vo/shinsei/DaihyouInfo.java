/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : DaihyouInfo.java
 *    Description : ������\�ҏ���ێ�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * ������\�ҏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: DaihyouInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class DaihyouInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\���Ҏ����i������-���j */
	private String nameKanjiSei;
	
	/** �\���Ҏ����i������-���j */
	private String nameKanjiMei;
	
	/** �\���Ҏ����i�J�i-���j */
	private String nameKanaSei;
	
	/** �\���Ҏ����i�J�i-���j */
	private String nameKanaMei;

	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoSei;
	
	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoMei;
	
	/** �N�� */
	private String nenrei;
	
	/** �\���Ҍ����Ҕԍ� */
	private String kenkyuNo;
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �����@�֖� */
	private String shozokuName;
	
	/** �����@�֖��i���́j */
	private String shozokuNameRyaku;
		
	/** ���ǃR�[�h */
	private String bukyokuCd;
	
	/** ���ǖ� */
	private String bukyokuName;
	
	/** ���ǖ��i���́j */
	private String bukyokuNameRyaku;	
	
	/** �E���R�[�h */
	private String shokushuCd;
	
	/** �E���i�a���j */
	private String shokushuNameKanji;
	
	/** �E���i���́j */
	private String shokushuNameRyaku;
	
	/** �X�֔ԍ� */
	private String zip;
	
	/** �Z�� */
	private String address;
	
	/** TEL */
	private String tel;
	
	/** FAX */
	private String fax;
	
	/** E-mail */
	private String email;
    
//2006/06/30 �c�@�ǉ���������
    /** URL */
    private String url;
//2006/06/30�@�c�@�ǉ������܂�       
	
	/** ���݂̐�� */
	private String senmon;
	
	/** �w�� */
	private String gakui;
	
	/** �G�t�H�[�g */
	private String effort;

	/** �������S */
	private String buntan;
	
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public DaihyouInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * �Z�����擾
	 * @return �Z��
	 */
	public String getAddress() {
		return address;
	}

	/**
     * ���ǃR�[�h���擾
	 * @return ���ǃR�[�h
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
     * ���ǖ����擾
	 * @return ���ǖ�
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
     * ���ǖ��i���́j���擾
	 * @return ���ǖ��i���́j
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
     * �������S���擾
	 * @return �������S
	 */
	public String getBuntan() {
		return buntan;
	}

	/**
     * E-mail���擾
	 * @return E-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
     * FAX���擾
	 * @return FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
     * �w�ʂ��擾
	 * @return �w��
	 */
	public String getGakui() {
		return gakui;
	}

	/**
     * �\���Ҍ����Ҕԍ����擾
	 * @return �\���Ҍ����Ҕԍ�
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
     * �\���Ҏ����i�J�i-���j���擾
	 * @return �\���Ҏ����i�J�i-���j
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
     * �\���Ҏ����i�J�i-���j���擾
	 * @return �\���Ҏ����i�J�i-���j
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
     * �\���Ҏ����i������-���j���擾
	 * @return �\���Ҏ����i������-���j
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
     * �\���Ҏ����i������-���j���擾
	 * @return �\���Ҏ����i������-���j
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
     * �\���Ҏ����i���[�}��-���j���擾
	 * @return �\���Ҏ����i���[�}��-���j
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
     * �\���Ҏ����i���[�}��-���j���擾
	 * @return �\���Ҏ����i���[�}��-���j
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
     * �N����擾
	 * @return �N��
	 */
	public String getNenrei() {
		return nenrei;
	}

	/**
     * ���݂̐����擾
	 * @return ���݂̐��
	 */
	public String getSenmon() {
		return senmon;
	}

	/**
     * �E���R�[�h���擾
	 * @return �E���R�[�h
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
     * �E���i�a���j���擾
	 * @return �E���i�a���j
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
     * �E���i���́j���擾
	 * @return �E���i���́j
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
     * �����@�փR�[�h���擾
	 * @return �����@�փR�[�h
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
     * �����@�֖����擾
	 * @return �����@�֖�
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
     * �����@�֖��i���́j���擾
	 * @return �����@�֖��i���́j
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
     * TEL���擾
	 * @return TEL
	 */
	public String getTel() {
		return tel;
	}

	/**
     * �X�֔ԍ����擾
	 * @return �X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}

	/**
     * �Z����ݒ�
	 * @param string �Z��
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
     * ���ǃR�[�h��ݒ�
	 * @param string ���ǃR�[�h
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
     * ���ǖ���ݒ�
	 * @param string ���ǖ�
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
     * ���ǖ��i���́j��ݒ�
	 * @param string ���ǖ��i���́j
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
	}

	/**
     * �������S��ݒ�
	 * @param string �������S
	 */
	public void setBuntan(String string) {
		buntan = string;
	}

	/**
     * E-mail��ݒ�
	 * @param string E-mail
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
     * FAX��ݒ�
	 * @param string FAX
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
     * �w�ʂ�ݒ�
	 * @param string �w��
	 */
	public void setGakui(String string) {
		gakui = string;
	}

	/**
     * �\���Ҍ����Ҕԍ���ݒ�
	 * @param string �\���Ҍ����Ҕԍ�
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
     * �\���Ҏ����i�J�i-���j��ݒ�
	 * @param string �\���Ҏ����i�J�i-���j
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
     * �\���Ҏ����i�J�i-���j��ݒ�
	 * @param string �\���Ҏ����i�J�i-���j
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
     * �\���Ҏ����i������-���j��ݒ�
	 * @param string �\���Ҏ����i������-���j
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
     * �\���Ҏ����i������-���j��ݒ�
	 * @param string �\���Ҏ����i������-���j
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
     * �\���Ҏ����i���[�}��-���j��ݒ�
	 * @param string �\���Ҏ����i���[�}��-���j
	 */
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
     * �\���Ҏ����i���[�}��-���j��ݒ�
	 * @param string �\���Ҏ����i���[�}��-���j
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
     * �N���ݒ�
	 * @param string �N��
	 */
	public void setNenrei(String string) {
		nenrei = string;
	}

	/**
     * ���݂̐���ݒ�
	 * @param string ���݂̐��
	 */
	public void setSenmon(String string) {
		senmon = string;
	}

	/**
     * �E���R�[�h��ݒ�
	 * @param string �E���R�[�h
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
     * �E���i�a���j��ݒ�
	 * @param string �E���i�a���j
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
     * �E���i���́j��ݒ�
	 * @param string �E���i���́j
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
     * �����@�փR�[�h��ݒ�
	 * @param string �����@�փR�[�h
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
     * �����@�֖���ݒ�
	 * @param string �����@�֖�
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
     * �����@�֖��i���́j��ݒ�
	 * @param string �����@�֖��i���́j
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
     * TEL��ݒ�
	 * @param string TEL
	 */
	public void setTel(String string) {
		tel = string;
	}

	/**
     * �X�֔ԍ���ݒ�
	 * @param string �X�֔ԍ�
	 */
	public void setZip(String string) {
		zip = string;
	}
	
	/**
     * �G�t�H�[�g���擾
	 * @return String �G�t�H�[�g
	 */
	public String getEffort() {
		return effort;
	}
	
	/**
     * �G�t�H�[�g��ݒ�
	 * @param effort �G�t�H�[�g
	 */
	public void setEffort(String effort) {
		this.effort = effort;
	}

    /**
     * URL���擾
     * @return String URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL��ݒ�
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
}