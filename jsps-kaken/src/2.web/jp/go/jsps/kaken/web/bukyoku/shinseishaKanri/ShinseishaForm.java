/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �\���҃t�H�[��
 */
public class ShinseishaForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\����ID */
	private String shinseishaId;

	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �����@�֖��i�a���j */
	private String shozokuName;

	/** �����@�֖��i�p���j */
	private String shozokuNameEigo;

	/** �����@�֖��i���́j */
	private String shozokuNameRyaku;

	/** �p�X���[�h */
	private String password;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaMei;
	
	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoSei;

	/** �\���Ҏ����i���[�}��-���j*/
	private String nameRoMei;

	/** ���ǃR�[�h */
	private String bukyokuCd;

	/** ���ǖ� */
	private String bukyokuName;

	/** ���ǖ��i���́j */
	private String bukyokuNameRyaku;
	
	/** ���ǎ�ʑI�����X�g */
	private List shubetuCdList = new ArrayList();

	/** ���ǎ�ʃR�[�h */
	private String bukyokuShubetuCd;

	/** ���ǎ�ʖ� */
	private String bukyokuShubetuName;

	/** �E���I�����X�g */
	private List shokushuCdList = new ArrayList();

	/** �E���R�[�h */
	private String shokushuCd;

	/** �E���i�a���j */
	private String shokushuNameKanji;

	/** �E���i���́j */
	private String shokushuNameRyaku;

	/** �Ȍ������Ҕԍ� */
	private String kenkyuNo;

	/** ����剞��t���O */
	private String hikoboFlg;
	
	/** ���l */
	private String biko;

	/** �L������(�N) */
	private String yukoDateYear;

	/** �L������(��) */
	private String yukoDateMonth;

	/** �L������(��) */
	private String yukoDateDay;

	/** �폜�t���O*/
	private String delFlg;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseishaForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		shinseishaId = "";
		shozokuCd = "";
		shozokuName = "";
		shozokuNameEigo = "";
		shozokuNameRyaku = "";
		password = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameKanaSei = "";
		nameKanaMei = "";
		nameRoSei = "";
		nameRoMei = "";
		bukyokuCd = "";
		bukyokuName = "";
		bukyokuNameRyaku = "";
		bukyokuShubetuCd = "";
		bukyokuShubetuName = "";
		shokushuCd = "";
		shokushuNameKanji = "";
		shokushuNameRyaku = "";
		kenkyuNo = "";
		hikoboFlg = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
		delFlg = "";
	}

	/* 
	 * ���̓`�F�b�N�B
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		//---------------------------------------------

		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------

		/* �����̏�����validation-gyomu.xml�Ŏ��s
		//�����@�փ`�F�b�N
		if(getShozokuCd() != null && getShozokuCd().equals("9999")) {
			if(StringUtil.isBlank(getShozokuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "�����@�֖�(�a��)"));
			}
			if(StringUtil.isBlank(getShozokuNameEigo())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "�����@�֖�(�p��)"));
			}
		}
		
		//���ǖ��`�F�b�N
		if(getBukyokuCd() != null && getBukyokuCd().equals("999")) {
			if(StringUtil.isBlank(getBukyokuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "���ǖ�(�a��)"));
			}
		}

		//���ǎ�ʃ`�F�b�N
		if(getBukyokuShubetuCd() != null && getBukyokuShubetuCd().equals("9")) {
			if(StringUtil.isBlank(getBukyokuShubetuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "���ǎ��"));
			}
		}

		//���t�Ó����`�F�b�N
		if (!StringUtil
			.isDate(
				getYukoDateYear()
					+ "/"
					+ getYukoDateMonth()
					+ "/"
					+ getYukoDateDay())) {

			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2003", "�L������"));
		}
		*/

		return errors;
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
	public String getBukyokuCd() {
		return bukyokuCd;
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
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
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
	public String getShinseishaId() {
		return shinseishaId;
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
	public String getBukyokuShubetuCd() {
		return bukyokuShubetuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuName() {
		return bukyokuShubetuName;
	}

	/**
	 * @return
	 */
	public String getYukoDateDay() {
		return yukoDateDay;
	}

	/**
	 * @return
	 */
	public String getYukoDateMonth() {
		return yukoDateMonth;
	}

	/**
	 * @return
	 */
	public String getYukoDateYear() {
		return yukoDateYear;
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
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
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
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
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
	public void setShinseishaId(String string) {
		shinseishaId = string;
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
	public void setBukyokuShubetuCd(String string) {
		bukyokuShubetuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuName(String string) {
		bukyokuShubetuName = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateDay(String string) {
		yukoDateDay = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateMonth(String string) {
		yukoDateMonth = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateYear(String string) {
		yukoDateYear = string;
	}

	/**
	 * @param list
	 */
	public void setShubetuCdList(List list) {
		shubetuCdList = list;
	}

	/**
	 * @return
	 */
	public List getShubetuCdList() {
		return shubetuCdList;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public List getShokushuCdList() {
		return shokushuCdList;
	}

	/**
	 * @param string
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param list
	 */
	public void setShokushuCdList(List list) {
		shokushuCdList = list;
	}

	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getHikoboFlg() {
		return hikoboFlg;
	}

	/**
	 * @param string
	 */
	public void setHikoboFlg(String string) {
		hikoboFlg = string;
	}

}
