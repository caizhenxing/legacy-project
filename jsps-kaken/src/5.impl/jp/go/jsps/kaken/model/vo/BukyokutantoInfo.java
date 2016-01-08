/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;
import java.util.List;


/**
 * ���ǒS���ҏ���ێ�����N���X�B
 * 
 */
public class BukyokutantoInfo extends BukyokutantoPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ���ǒS����ID */
	private String bukyokutantoId;
	
	/** �p�X���[�h */
	private String password;

	/** ���ǃR�[�h */
	private String bukyokuCd;

	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �S���Җ��i���j */
	private String tantoNameSei;

	/** �S���Җ��i���j */
	private String tantoNameMei;
	
	/** �d�b�ԍ� */
	private String bukyokuTel;

	/** FAX�ԍ� */
	private String bukyokuFax;
	
	/** Email */
	private String bukyokuEmail;

	/** ���ۖ� */
	private String bukaName;
	
	/** �f�t�H���g�p�X���[�h */
	private String defaultPassword;

	/** �o�^�ς݃t���O */
	private String registFlg;

	/** �폜�t���O */
	private String delFlg;
	
	/** �S�����ǃt���O */
	private boolean tantoFlg;
	
	/** �L������ */
	private Date yukoDate;
	
	/** �S�����ǃR�[�h */
	private String tantoBukyokuCd;
	
	/** ���l */
	private String biko;
	
	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��̓o�^�A�X�V�A�폜�A�p�X���[�h�ύX�����ǉ��̂���
	
	/** ���ǃR�[�h */
	private List bukyokuList;
	
	/** �W�� */
	private String kakariName;
	
	/** �����@�֖� */
	private String shozokuName;
	
	/** �����@�֖�(�p��) */
	private String shozokuNameEigo;
	
	/** �A�N�V���� */
	private String action;
	
	// �ǉ� �����܂�--------------------------------------------------------
	
	// 2005/04/21 �ǉ���������---------------------------------------------
	// ���R �����@�֒S���҂̍폜�t���O�i���O�C�����Ƀ`�F�b�N�j
	/** �����@�֒S���ҍ폜�t���O */
	private String delFlgShozoku;
	// �ǉ� �����܂�--------------------------------------------------------
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public BukyokutantoInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return bukaName ��߂��܂��B
	 */
	public String getBukaName() {
		return bukaName;
	}
	/**
	 * @param bukaName bukaName ��ݒ�B
	 */
	public void setBukaName(String bukaName) {
		this.bukaName = bukaName;
	}
	/**
	 * @return bukyokuCd ��߂��܂��B
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd ��ݒ�B
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
	/**
	 * @return bukyokuEmail ��߂��܂��B
	 */
	public String getBukyokuEmail() {
		return bukyokuEmail;
	}
	/**
	 * @param bukyokuEmail bukyokuEmail ��ݒ�B
	 */
	public void setBukyokuEmail(String bukyokuEmail) {
		this.bukyokuEmail = bukyokuEmail;
	}
	/**
	 * @return bukyokuFax ��߂��܂��B
	 */
	public String getBukyokuFax() {
		return bukyokuFax;
	}
	/**
	 * @param bukyokuFax bukyokuFax ��ݒ�B
	 */
	public void setBukyokuFax(String bukyokuFax) {
		this.bukyokuFax = bukyokuFax;
	}
	/**
	 * @return bukyokutantoId ��߂��܂��B
	 */
	public String getBukyokutantoId() {
		return bukyokutantoId;
	}
	/**
	 * @param bukyokutantoId bukyokutantoId ��ݒ�B
	 */
	public void setBukyokutantoId(String bukyokutantoId) {
		this.bukyokutantoId = bukyokutantoId;
	}
	/**
	 * @return bukyokuTel ��߂��܂��B
	 */
	public String getBukyokuTel() {
		return bukyokuTel;
	}
	/**
	 * @param bukyokuTel bukyokuTel ��ݒ�B
	 */
	public void setBukyokuTel(String bukyokuTel) {
		this.bukyokuTel = bukyokuTel;
	}
	/**
	 * @return defaultPassword ��߂��܂��B
	 */
	public String getDefaultPassword() {
		return defaultPassword;
	}
	/**
	 * @param defaultPassword defaultPassword ��ݒ�B
	 */
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	/**
	 * @return delFlg ��߂��܂��B
	 */
	public String getDelFlg() {
		return delFlg;
	}
	/**
	 * @param delFlg delFlg ��ݒ�B
	 */
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	/**
	 * @return password ��߂��܂��B
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password password ��ݒ�B
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return registFlg ��߂��܂��B
	 */
	public String getRegistFlg() {
		return registFlg;
	}
	/**
	 * @param registFlg registFlg ��ݒ�B
	 */
	public void setRegistFlg(String registFlg) {
		this.registFlg = registFlg;
	}
	/**
	 * @return shozokuCd ��߂��܂��B
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd ��ݒ�B
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}
	/**
	 * @return tantoNameMei ��߂��܂��B
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}
	/**
	 * @param tantoNameMei tantoNameMei ��ݒ�B
	 */
	public void setTantoNameMei(String tantoNameMei) {
		this.tantoNameMei = tantoNameMei;
	}
	/**
	 * @return tantoNameSei ��߂��܂��B
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}
	/**
	 * @param tantoNameSei tantoNameSei ��ݒ�B
	 */
	public void setTantoNameSei(String tantoNameSei) {
		this.tantoNameSei = tantoNameSei;
	}
	/**
	 * @return tantoFlg ��߂��܂��B
	 */
	public boolean getTantoFlg() {
		return tantoFlg;
	}
	/**
	 * @param tantoFlg tantoFlg ��ݒ�B
	 */
	public void setTantoFlg(boolean tantoFlg) {
		this.tantoFlg = tantoFlg;
	}
	/**
	 * @return yukoDate ��߂��܂��B
	 */
	public Date getYukoDate() {
		return yukoDate;
	}
	/**
	 * @param yukoDate yukoDate ��ݒ�B
	 */
	public void setYukoDate(Date yukoDate) {
		this.yukoDate = yukoDate;
	}
	/**
	 * @return tantoBukyokuCd ��߂��܂��B
	 */
	public String getTantoBukyokuCd() {
		return tantoBukyokuCd;
	}
	/**
	 * @param tantoBukyokuCd tantoBukyokuCd ��ݒ�B
	 */
	public void setTantoBukyokuCd(String tantoBukyokuCd) {
		this.tantoBukyokuCd = tantoBukyokuCd;
	}
	/**
	 * @return biko ��߂��܂��B
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * @param biko biko ��ݒ�B
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}
	
	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��̓o�^�A�X�V�A�폜�A�p�X���[�h�ύX�����ǉ��̂���
	
	/**
	 * @return bukyokuList��߂��܂��B
	 */
	public List getBukyokuList() {
		return bukyokuList;
	}

	/**
	 * @param bukyokuList bukoykuList��ݒ�B
	 */
	public void setBukyokuList(List bukyokuList) {
		this.bukyokuList = bukyokuList;
	}

	/**
	 * @return kakariName��߂��܂��B
	 */
	public String getKakariName() {
		return kakariName;
	}

	/**
	 * @param kakariName kakariName��ݒ�B
	 */
	public void setKakariName(String kakariName) {
		this.kakariName = kakariName;
	}

	/**
	 * @return shozokuName��߂��܂��B
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return shozokuNameEigo��߂��܂��B
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @param shozokuName shozokuName��ݒ�B
	 */
	public void setShozokuName(String shozokuName) {
		this.shozokuName = shozokuName;
	}

	/**
	 * @param shozokuNameEigo shozokuNameEigo��ݒ�B
	 */
	public void setShozokuNameEigo(String shozokuNameEigo) {
		this.shozokuNameEigo = shozokuNameEigo;
	}

	/**
	 * @return action��߂��܂��B
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action action��ݒ�
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	// �ǉ� �����܂�--------------------------------------------------------

	/**
	 * @return delFlgShozoku ��߂��܂��B
	 */
	public String getDelFlgShozoku() {
		return delFlgShozoku;
	}
	/**
	 * @param delFlgShozoku delFlgShozoku ��ݒ�B
	 */
	public void setDelFlgShozoku(String delFlgShozoku) {
		this.delFlgShozoku = delFlgShozoku;
	}
}