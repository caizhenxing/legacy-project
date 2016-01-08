/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShinsainForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class ShinsainForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R����ID */
	private String shinsainId;

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R���������i����-���j */
	private String nameKanjiSei;

	/** �R���������i����-���j */
	private String nameKanjiMei;

	/** �R���������i�J�i-���j */
	private String nameKanaSei;

	/** �R���������i�J�i-���j*/
	private String nameKanaMei;

	/** �����@�֖��i�R�[�h�j*/
	private String shozokuCd;

	/** �����@�֖��i�a���j*/
	private String shozokuName;

//	/** ���ǖ��i�R�[�h�j*/
//	private String bukyokuCd;

	/** ���ǖ��i�a���j*/
	private String bukyokuName;

//	/** �E��R�[�h */
//	private String shokushuCd;

	/** �E�햼�� */
	private String shokushuName;

//	/** �n�� */
//	private String keiCd;
//
//	/** �R���s�� */
//	private String shinsaKahi;

	/** ���t��i�X�֔ԍ��j */
	private String sofuZip;

	/** ���t��i�Z���j */
	private String sofuZipaddress;

	/** ���t��iEmail�j */
	private String sofuZipemail;

//	/** ���t��iEmail2�j */
//	private String sofuZipemail2;

	/** �d�b�ԍ� */
	private String shozokuTel;

//	/** ����d�b�ԍ� */
//	private String jitakuTel;

//	/** �V�K�E�p�� */
//	private String sinkiKeizokuFlg;
//
//	/** �V�K�E�p��(�\���p) */
//	private String sinkiKeizokuHyoji;
//
//	/** �Ϗ��J�n��(�N) */
//	private String kizokuStartYear;
//
//	/** �Ϗ��J�n��(��) */
//	private String kizokuStartMonth;
//
//	/** �Ϗ��J�n��(��) */
//	private String kizokuStartDay;
//
//	/** �Ϗ��I����(�N) */
//	private String kizokuEndYear;
//
//	/** �Ϗ��I����(��) */
//	private String kizokuEndMonth;
//
//	/** �Ϗ��I����(��) */
//	private String kizokuEndDay;
//
//	/** �Ӌ� */
//	private String shakin;
//
//	/** �Ӌ�(�\���p) */
//	private String shakinHyoji;

	/** URL */
	private String url;

//	/** ���ȍזڃR�[�h�iA�j */
//	private String levelA1;
//
//	/** ���ȍזڃR�[�h�iB1-1�j */
//	private String levelB11;
//
//	/** ���ȍזڃR�[�h�iB1-2�j */
//	private String levelB12;
//
//	/** ���ȍזڃR�[�h�iB1-3�j */
//	private String levelB13;
//
//	/** ���ȍזڃR�[�h�iB2-1�j */
//	private String levelB21;
//
//	/** ���ȍזڃR�[�h�iB2-2�j */
//	private String levelB22;
//
//	/** ���ȍזڃR�[�h�iB2-3�j */
//	private String levelB23;
//
//	/** ��啪��̃L�[���[�h(1) */
//	private String key1;
//
//	/** ��啪��̃L�[���[�h(2) */
//	private String key2;
//
//	/** ��啪��̃L�[���[�h(3) */
//	private String key3;
//
//	/** ��啪��̃L�[���[�h(4) */
//	private String key4;
//
//	/** ��啪��̃L�[���[�h(5) */
//	private String key5;
//
//	/** ��啪��̃L�[���[�h(6) */
//	private String key6;
//
//	/** ��啪��̃L�[���[�h(7) */
//	private String key7;

	/** �p�X���[�h */
	private String password;

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

//	/** �V�K�E�p���I�����X�g */
//	private List sinkiKeizokuFlgList = new ArrayList();
//
//	/** �Ӌ��I�����X�g */
//	private List shakinList = new ArrayList();

	/** FAX�ԍ� */
	private String shozokuFax;

	/** �S�����Ƌ敪 */
	private String jigyoKubun;

	/** ��� */
	private String senmon;

	/** �X�V��(�N) */
	private String koshinDateYear;

	/** �X�V��(��) */
	private String koshinDateMonth;

	/** �X�V��(��) */
	private String koshinDateDay;

	// 2006/10/24 �Ո� �ǉ���������
	// �����v�撲���_�E�����[�h�t���O
	private String downloadFlag = "0";

	// 2006/10/24 �Ո� �ǉ������܂�

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		shinsainId = "";
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		shozokuName = "";
//		bukyokuCd = "";
		bukyokuName = "";
//		shokushuCd = "";
		shokushuName = "";
//		keiCd = "";
//		shinsaKahi = "";
		sofuZip = "";
		sofuZipaddress = "";
		sofuZipemail = "";
//		sofuZipemail2 = "";
		shozokuTel = "";
//		jitakuTel = "";
//		sinkiKeizokuFlg = "";
//		sinkiKeizokuHyoji = "";
//		kizokuStartYear = "";
//		kizokuStartMonth = "";
//		kizokuStartDay = "";
//		kizokuEndYear = "";
//		kizokuEndMonth = "";
//		kizokuEndDay = "";
//		shakin = "";
//		shakinHyoji = "";
		url = "";
//		levelA1 = "";
//		levelB11 = "";
//		levelB12 = "";
//		levelB13 = "";
//		levelB21 = "";
//		levelB22 = "";
//		levelB23 = "";
//		key1 = "";
//		key2 = "";
//		key3 = "";
//		key4 = "";
//		key5 = "";
//		key6 = "";
//		key7 = "";
		password = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
		delFlg = "";
		shozokuFax = "";
		jigyoKubun = "";
		senmon = "";
		koshinDateYear = "";
		koshinDateMonth = "";
		koshinDateDay = "";
	}

	/* 
	 * ���̓`�F�b�N�B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
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
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getKoshinDateDay() {
		return koshinDateDay;
	}

	/**
	 * @return
	 */
	public String getKoshinDateMonth() {
		return koshinDateMonth;
	}

	/**
	 * @return
	 */
	public String getKoshinDateYear() {
		return koshinDateYear;
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
	public String getNameKanjiMei() {
		return nameKanjiMei;
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
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getSenmon() {
		return senmon;
	}

	/**
	 * @return
	 */
	public String getShinsainId() {
		return shinsainId;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getShokushuName() {
		return shokushuName;
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
	public String getShozokuFax() {
		return shozokuFax;
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
	public String getShozokuTel() {
		return shozokuTel;
	}

	/**
	 * @return
	 */
	public String getSofuZip() {
		return sofuZip;
	}

	/**
	 * @return
	 */
	public String getSofuZipaddress() {
		return sofuZipaddress;
	}

	/**
	 * @return
	 */
	public String getSofuZipemail() {
		return sofuZipemail;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
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
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateDay(String string) {
		koshinDateDay = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateMonth(String string) {
		koshinDateMonth = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateYear(String string) {
		koshinDateYear = string;
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
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
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
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setSenmon(String string) {
		senmon = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainId(String string) {
		shinsainId = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuName(String string) {
		shokushuName = string;
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
	public void setShozokuFax(String string) {
		shozokuFax = string;
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
	public void setShozokuTel(String string) {
		shozokuTel = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZip(String string) {
		sofuZip = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZipaddress(String string) {
		sofuZipaddress = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZipemail(String string) {
		sofuZipemail = string;
	}

	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
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

	public String getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
}
