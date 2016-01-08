/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenSearchForm.java
 *    Description : �ӌ���񌟍��������̓t�H�[���N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * ���ӌ���񌟍��������̓t�H�[���N���X�B
 * ID RCSfile="$RCSfile: IkenSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenSearchForm extends BaseSearchForm {

	/** �\���҃t���O */
	private String shinseisya ;
	
	/** �����@�֒S���� */
	private String syozoku ;
	
	/** ���ǒS���� */
	private String bukyoku ;
	
	/** �R�����@*/
	private String shinsyain ;
	
	/** �쐬��(�J�n)(�N) */
	private String     sakuseiDateFromYear;
	
	/** �쐬��(�J�n)(��) */
	private String 	sakuseiDateFromMonth;

	/** �쐬��(�J�n)(��) */
	private String 	sakuseiDateFromDay;
	
	/** �쐬��(�I��)(�N) */
	private String     sakuseiDateToYear;
	
	/** �쐬��(�I��)(��) */
	private String 	sakuseiDateToMonth;

	/** �쐬��(�I��)(��) */
	private String 	sakuseiDateToDay;

	/** �\������ */
	private String dispmode ;
	
	/**
	 * �R���X�g���N�^�B
	 */
	public IkenSearchForm() {
		super();
		init();
	}

	/**
	 * ����������
	 */
	public void init() {
		shinseisya = "";
		syozoku = "";
		bukyoku = "";
		shinsyain = "";
		sakuseiDateFromYear= "";
		sakuseiDateFromMonth= "";
		sakuseiDateFromDay= "";
		sakuseiDateToYear= "";
		sakuseiDateToMonth= "";
		sakuseiDateToDay= "";
		dispmode = "";
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getShinseisya(){
		return shinseisya;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setShinseisya(String n){
		shinseisya = n;
	}

	/**
	 * �����@�֒S���҃t���O�̎擾
	 * @return
	 */
	public String getSyozoku(){
		return syozoku;
	}
	
	/**
	 * �����@�֒S���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setSyozoku(String n){
		syozoku = n;
	}
	
	/**
	 * ���ǒS���҃t���O�̎擾
	 * @return
	 */
	public String getBukyoku(){
		return bukyoku;
	}
	
	/**
	 * ���ǒS���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setBukyoku(String n){
		bukyoku = n;
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getShinsyain(){
		return shinsyain;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setShinsyain(String n){
		shinsyain = n;
	}
	
	/**
	 * ���e���J�n�N�̎擾
	 * @return
	 */
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
	}
	
	/**
	 * ���e���J�n�N�̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateFromYear(String str) {
		sakuseiDateFromYear = str;
	}

	/**
	 * ���e���J�n���̎擾
	 * @return
	 */
	public String getSakuseiDateFromMonth() {
		return sakuseiDateFromMonth;
	}

	/**
	 * ���e���J�n���̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateFromMonth(String str) {
		sakuseiDateFromMonth = str;
	}
	
	/**
	 * ���e���J�n���̎擾
	 * @return
	 */
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
	}

	/**
	 * ���e���J�n���̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateFromDay(String str) {
		sakuseiDateFromDay = str;
	}
	
	/**
	 * ���e���I���N�̎擾
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
	}

	/**
	 * ���e���I���N�̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateToYear(String str) {
		sakuseiDateToYear = str;
	}

	/**
	 * ���e���I�����̎擾
	 * @return
	 */
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * ���e���I�����̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateToMonth(String str) {
		sakuseiDateToMonth = str;
	}

	/**
	 * ���e���I�����̎擾
	 * @return
	 */
	public String getSakuseiDateToDay() {
		return sakuseiDateToDay;
	}

	/**
	 * ���e���I�����̐ݒ�
	 * @param str
	 */
	public void setSakuseiDateToDay(String str) {
		sakuseiDateToDay = str;
	}

	/**
	 * �\���҃t���O�̎擾
	 * @return
	 */
	public String getDispmode(){
		return dispmode;
	}
	
	/**
	 * �\���҃t���O�̐ݒ�
	 * @param n
	 */
	public void setDispmode(String n){
		dispmode = n;
	}
}
