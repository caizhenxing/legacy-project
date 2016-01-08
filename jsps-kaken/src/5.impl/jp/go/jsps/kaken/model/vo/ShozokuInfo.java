/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * �����@�֏���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShozokuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShozokuInfo extends ShozokuPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �@�֎�ʃR�[�h */
	private String shubetuCd;

	/** �����@�֖�(�a��) */
	private String shozokuName;
	
	/** �����@�֖�(����) */
	private String shozokuRyakusho;

	/** �����@�֖�(�p��) */
	private String shozokuNameEigo;

	/** �p�X���[�h */
	private String password;

	/** �ӔC�ҁi���j */
	private String sekininshaNameSei;

	/** �ӔC�ҁi���j */
	private String sekininshaNameMei;

	/** �ӔC�Җ�E */
	private String sekininshaYaku;

	/** �ӔC�ғd�b�ԍ� */
	private String sekininshaTel;

	/** �S�����ǖ� */
	private String bukyokuName;

	/** �S���ۖ� */
	private String kaName;

	/** �S���W�� */
	private String kakariName;

	/** �S���Җ��i���j */
	private String tantoNameSei;

	/** �S���Җ��i���j */
	private String tantoNameMei;

	/** �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j */
	private String tantoTel;

	/** �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j */
	private String tantoFax;
	
	/** �S���ҕ��Ǐ��ݒn�iEmail�j */
	private String tantoEmail;
	
	/** �S���ҕ��Ǐ��ݒn�iEmail2�j */
	private String tantoEmail2;

	/** �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j */
	private String tantoZip;

	/** �S���ҕ��Ǐ��ݒn�i�Z���j */
	private String tantoAddress;

	/** �F�؃L�[���s�t���O */
	private String ninshokeyFlg;
	
	/** ���l */
	private String biko;

	/** �L������ */
	private Date yukoDate;

	//2005.08.10 iso ID���s���t��ǉ�
	/** �L������ */
	private Date idDate;
	
// 2005/04/20 �ǉ� ��������-------------------------------
// ���R �u���ǒS���Ґl���v�ǉ�
	/** ���ǒS���Ґl�� */
	private String bukyokuNum;
// �ǉ� �����܂�------------------------------------------
	
	/** �폜�t���O */
	private String delFlg;
	
//	 2005/04/20 �ǉ� ��������-------------------------------
//	 ���R �u�S���t���O�v�ǉ�
	/** �S���t���O
	 * �V�X�e���Ǘ��ҋ@�\�̏����@�֏�񌟍��Ŏg�p�B
	 * �����@�ցF0�@���ǁF1
	 */
	private String tantoFlg;
//	 �ǉ� �����܂�------------------------------------------
	
//	 2005/04/21 �ǉ� ��������-------------------------------
//	 ���R �X�V�����p�A�ǉ����ǒS���Ґl��
		/** �ǉ����ǒS���Ґl�� */
		private int addBukyokuNum;
//	 �ǉ� �����܂�------------------------------------------
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShozokuInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getKakariName() {
		return kakariName;
	}

	/**
	 * @return
	 */
	public String getKaName() {
		return kaName;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getSekininshaNameMei() {
		return sekininshaNameMei;
	}

	/**
	 * @return
	 */
	public String getSekininshaNameSei() {
		return sekininshaNameSei;
	}

	/**
	 * @return
	 */
	public String getSekininshaTel() {
		return sekininshaTel;
	}

	/**
	 * @return
	 */
	public String getSekininshaYaku() {
		return sekininshaYaku;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public String getTantoAddress() {
		return tantoAddress;
	}

	/**
	 * @return
	 */
	public String getTantoEmail() {
		return tantoEmail;
	}

	/**
	 * @return
	 */
	public String getTantoFax() {
		return tantoFax;
	}

	/**
	 * @return
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @return
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}

	/**
	 * @return
	 */
	public String getTantoTel() {
		return tantoTel;
	}

	/**
	 * @return
	 */
	public String getTantoZip() {
		return tantoZip;
	}

	/**
	 * @return
	 */
	public Date getYukoDate() {
		return yukoDate;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setKakariName(String string) {
		kakariName = string;
	}

	/**
	 * @param string
	 */
	public void setKaName(String string) {
		kaName = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaNameMei(String string) {
		sekininshaNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaNameSei(String string) {
		sekininshaNameSei = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaTel(String string) {
		sekininshaTel = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaYaku(String string) {
		sekininshaYaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param string
	 */
	public void setTantoAddress(String string) {
		tantoAddress = string;
	}

	/**
	 * @param string
	 */
	public void setTantoEmail(String string) {
		tantoEmail = string;
	}

	/**
	 * @param string
	 */
	public void setTantoFax(String string) {
		tantoFax = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameSei(String string) {
		tantoNameSei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoTel(String string) {
		tantoTel = string;
	}

	/**
	 * @param string
	 */
	public void setTantoZip(String string) {
		tantoZip = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDate(Date date) {
		yukoDate = date;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param date
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}


	/**
	 * @return
	 */
	public String getShubetuCd() {
		return shubetuCd;
	}

	/**
	 * @param string
	 */
	public void setShubetuCd(String string) {
		shubetuCd = string;
	}

	/**
	 * @return
	 */
	public String getNinshokeyFlg() {
		return ninshokeyFlg;
	}

	/**
	 * @return
	 */
	public String getTantoEmail2() {
		return tantoEmail2;
	}

	/**
	 * @param string
	 */
	public void setNinshokeyFlg(String string) {
		ninshokeyFlg = string;
	}

	/**
	 * @param string
	 */
	public void setTantoEmail2(String string) {
		tantoEmail2 = string;
	}

	/**
	 * @return
	 */
	public String getShozokuRyakusho() {
		return shozokuRyakusho;
	}

	/**
	 * @param string
	 */
	public void setShozokuRyakusho(String string) {
		shozokuRyakusho = string;
	}
	
	/**
	 * @return bukyokuNum ��߂��܂��B
	 */
	public String getBukyokuNum() {
		return bukyokuNum;
	}
	/**
	 * @param bukyokuNum bukyokuNum ��ݒ�B
	 */
	public void setBukyokuNum(String bukyokuNum) {
		this.bukyokuNum = bukyokuNum;
	}
	
	/**
	 * @return shubetsuNo ��߂��܂��B
	 */
	public String getTantoFlg() {
		return tantoFlg;
	}
	/**
	 * @param shubetsuNo shubetsuNo ��ݒ�B
	 */
	public void setTantoFlg(String shubetsuNo) {
		this.tantoFlg = shubetsuNo;
	}
	
	/**
	 * @return addBukyokuNum ��߂��܂��B
	 */
	public int getAddBukyokuNum() {
		return addBukyokuNum;
	}
	/**
	 * @param addBukyokuNum addBukyokuNum ��ݒ�B
	 */
	public void setAddBukyokuNum(int addBukyokuNum) {
		this.addBukyokuNum = addBukyokuNum;
	}
	/**
	 * @return
	 */
	public Date getIdDate() {
		return idDate;
	}

	/**
	 * @param date
	 */
	public void setIdDate(Date date) {
		idDate = date;
	}

}
