/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �\����񌟍��t�H�[��
 * 
 */
public class ShinseiSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ����CD */
	private String jigyoCd;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** �\���Ҏ����i���E�����j */
	private String nameKanjiSei;

	/** �\���Ҏ����i���E�����j */
	private String nameKanjiMei;

	/** �\���Ҏ����i���E���[�}���j */
	private String nameRoSei;

	/** �\���Ҏ����i���E���[�}���j */
	private String nameRoMei;

	/** �\���� */
	private String jokyoId;

	/** �쐬���iFrom�E�N�j */
	private String sakuseiDateFromYear;

	/** �쐬���iFrom�E���j */
	private String sakuseiDateFromMonth;

	/** �쐬���iFrom�E���j */
	private String sakuseiDateFromDay;

	/** �쐬���iTo�E�N�j */
	private String sakuseiDateToYear;

	/** �쐬���iTo�E���j */
	private String sakuseiDateToMonth;

	/** �쐬���iTo�E���j */
	private String sakuseiDateToDay;

	/** �����@�֏��F���iFrom�E�N�j */
	private String shoninDateFromYear;

	/** �����@�֏��F���iFrom�E���j */
	private String shoninDateFromMonth;

	/** �����@�֏��F���iFrom�E���j */
	private String shoninDateFromDay;

	/** �����@�֏��F���iTo�E�N�j */
	private String shoninDateToYear;

	/** �����@�֏��F���iTo�E���j */
	private String shoninDateToMonth;

	/** �����@�֏��F���iTo�E���j */
	private String shoninDateToDay;

	/** �\������ */
	private String hyojiHoshiki;

	/** ���Ɩ����X�g */
	private List jigyoList = new ArrayList();

	/** �\���󋵃��X�g */
	private List jokyoList = new ArrayList();

	/** �\���Ҏ����i���E�t���K�i�j */
	private String nameKanaSei;

	/** �\���Ҏ����i���E�t���K�i�j */
	private String nameKanaMei;

	/** �����Ҕԍ� */
	private String kenkyuNo;

	/** ���ǃR�[�h */
	private String bukyokuCd;

	/** �\���������X�g */
	private List hyojiHoshikiList = new ArrayList();

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseiSearchForm() {
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
		jigyoCd              = "";
		nendo                = "";
		kaisu                = "";
		nameKanjiSei         = "";
		nameKanjiMei         = "";
		nameRoSei            = "";
		nameRoMei            = "";
		jokyoId              = "";
		sakuseiDateFromYear  = "";
		sakuseiDateFromMonth = "";
		sakuseiDateFromDay   = "";
		sakuseiDateToYear    = "";
		sakuseiDateToMonth   = "";
		sakuseiDateToDay     = "";
		shoninDateFromYear   = "";
		shoninDateFromMonth  = "";
		shoninDateFromDay    = "";
		shoninDateToYear     = "";
		shoninDateToMonth    = "";
		shoninDateToDay      = "";
		hyojiHoshiki         = "";
		nameKanaSei     = "";
		nameKanaMei    = "";
		kenkyuNo      = "";
		bukyokuCd         = "";
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
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
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
	public String getNameRoSei() {
		return nameRoSei;
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
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromMonth() {
		return sakuseiDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToDay() {
		return sakuseiDateToDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromYear() {
		return shoninDateFromYear;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromMonth() {
		return shoninDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromDay() {
		return shoninDateFromDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateToYear() {
		return shoninDateToYear;
	}

	/**
	 * @return
	 */
	public String getShoninDateToMonth() {
		return shoninDateToMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateToDay() {
		return shoninDateToDay;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshiki() {
		return hyojiHoshiki;
	}

	/**
	 * @return
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
	 * @return
	 */
	public List getJokyoList() {
		return jokyoList;
	}


	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
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
	public void setNameRoSei(String string) {
		nameRoSei = string;
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
	public void setJokyoId(String string) {
		jokyoId = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromYear(String string) {
		sakuseiDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromMonth(String string) {
		sakuseiDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromDay(String string) {
		sakuseiDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToYear(String string) {
		sakuseiDateToYear = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToMonth(String string) {
		sakuseiDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToDay(String string) {
		sakuseiDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromYear(String string) {
		shoninDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromMonth(String string) {
		shoninDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromDay(String string) {
		shoninDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToYear(String string) {
		shoninDateToYear = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToMonth(String string) {
		shoninDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToDay(String string) {
		shoninDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshiki(String string) {
		hyojiHoshiki = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
	}

	/**
	 * @param list
	 */
	public void setJokyoList(List list) {
		jokyoList = list;
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
	public String getKenkyuNo() {
		return kenkyuNo;
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
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
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
	 * @return
	 */
	public List getHyojiHoshikiList() {
		return hyojiHoshikiList;
	}

	/**
	 * @param list
	 */
	public void setHyojiHoshikiList(List list) {
		hyojiHoshikiList = list;
	}

}
