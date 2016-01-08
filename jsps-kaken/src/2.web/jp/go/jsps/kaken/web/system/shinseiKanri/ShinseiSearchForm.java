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
package jp.go.jsps.kaken.web.system.shinseiKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShinseiSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class ShinseiSearchForm extends BaseSearchForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �\���I�� */
	private String     hyojiSentaku;

	/** ���ƃR�[�h */
	private String     jigyoCd;

	/** �N�x */
	private String     nendo;
	
	/** �� */
	private String     kaisu;

	/** �\����ID */
	private String     shinseishaId;
	
	/** �\���Ҏ����i������-���j */
	private String     nameKanjiSei;
	
	/** �\���Ҏ����i������-���j*/
	private String     nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String     nameKanaSei;
	
	/** �\���Ҏ����i�t���K�i-���j*/
	private String     nameKanaMei;
		
	/** �\���Ҏ����i���[�}��-���j */
	private String     nameRoSei;
	
	/** �\���Ҏ����i���[�}��-���j */
	private String     nameRoMei;

	/** �����@�փR�[�h */
	private String     shozokuCd;

	/** �\���Ҍ����Ҕԍ� */
	private String     kenkyuNo;

	/** �n���̋敪 */
//	private String     keiName;

	/** ���E�̊ϓ_�ԍ� */
//	private String     kantenNo;
				
	/** �\���� */
	private String     jokyoId;
	
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
	
	/** �����@�֏��F��(�J�n)(�N) */
	private String     shoninDateFromYear;
	
	/** �����@�֏��F��(�J�n)(��) */
	private String 	shoninDateFromMonth;

	/** �����@�֏��F��(�J�n)(��) */
	private String 	shoninDateFromDay;
	
	/** �����@�֏��F��(�I��)(�N) */
	private String     shoninDateToYear;
	
	/** �����@�֏��F��(�I��)(��) */
	private String 	shoninDateToMonth;

	/** �����@�֏��F��(�I��)(��) */
	private String 	shoninDateToDay;
	
	/** �\���ԍ� */
	private String     uketukeNo;	

	/** �זڔԍ� */
	private String     bunkaSaimokuCd;	

	/** �폜�t���O */
	private String     delFlg;	
	
	/** �\���I��I�����X�g */
	private List hyojiSentakuList = new ArrayList();
	
	/** ���Ɩ��I�����X�g */
	private List jigyoNameList = new ArrayList();

	/** ���E�̊ϓ_�I�����X�g */
//	private List kantenList = new ArrayList();
		
	/** �\���󋵑I�����X�g */
	private List jokyoList = new ArrayList();

	/** �폜�t���O�I�����X�g */
	private List delFlgList = new ArrayList();
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
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	/**
	 * ����������
	 */
	public void init() {
		hyojiSentaku= "1";	//�f�t�H���g�́A�u1:������ږ��ɕ\���v
		jigyoCd= "";
		nendo= "";
		kaisu= "";
		nameKanjiSei= "";
		nameKanjiMei= "";
		nameKanaSei= "";
		nameKanaMei= "";
		nameRoSei= "";
		nameRoMei= "";
		shozokuCd= "";
		kenkyuNo= "";
//		keiName= "";
//		kantenNo= "";
		jokyoId= "";
		sakuseiDateFromYear= "";
		sakuseiDateFromMonth= "";
		sakuseiDateFromDay= "";
		sakuseiDateToYear= "";
		sakuseiDateToMonth= "";
		sakuseiDateToDay= "";
		shoninDateFromYear= "";
		shoninDateFromMonth= "";
		shoninDateFromDay= "";
		shoninDateToYear= "";
		shoninDateToMonth= "";
		shoninDateToDay= "";
		uketukeNo= "";	
		bunkaSaimokuCd= "";		
		delFlg= "1";	//�f�t�H���g�́A�u1:�폜�f�[�^������ �v
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
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
	public String getHyojiSentaku() {
		return hyojiSentaku;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @return
	 */
	public List getJigyoNameList() {
		return jigyoNameList;
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
	public String getKaisu() {
		return kaisu;
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
	public String getNendo() {
		return nendo;
	}
	
	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setHyojiSentaku(String string) {
		hyojiSentaku = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoNameList(List list) {
		jigyoNameList = list;
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
	public void setKaisu(String string) {
		kaisu = string;
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
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public List getJokyoList() {
		return jokyoList;
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
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
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
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
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
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
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
	public void setSakuseiDateFromMonth(String string) {
		sakuseiDateFromMonth = string;
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
	public void setSakuseiDateToDay(String string) {
		sakuseiDateToDay = string;
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
	public void setSakuseiDateToYear(String string) {
		sakuseiDateToYear = string;
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
	public String getShoninDateFromMonth() {
		return shoninDateFromMonth;
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
	public String getShoninDateToDay() {
		return shoninDateToDay;
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
	public String getShoninDateToYear() {
		return shoninDateToYear;
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
	public void setShoninDateFromMonth(String string) {
		shoninDateFromMonth = string;
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
	public void setShoninDateToDay(String string) {
		shoninDateToDay = string;
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
	public void setShoninDateToYear(String string) {
		shoninDateToYear = string;
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
	 * @return
	 */
	public List getHyojiSentakuList() {
		return hyojiSentakuList;
	}

	/**
	 * @param list
	 */
	public void setHyojiSentakuList(List list) {
		hyojiSentakuList = list;
	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @return
	 */
//	public String getKantenNo() {
//		return kantenNo;
//	}

	/**
	 * @return
	 */
//	public String getKeiName() {
//		return keiName;
//	}

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
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @param string
	 */
//	public void setKantenNo(String string) {
//		kantenNo = string;
//	}

	/**
	 * @param string
	 */
//	public void setKeiName(String string) {
//		keiName = string;
//	}

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
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @return
	 */
//	public List getKantenList() {
//		return kantenList;
//	}

	/**
	 * @param list
	 */
//	public void setKantenList(List list) {
//		kantenList = list;
//	}

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

	/**
	 * @return
	 */
	public List getDelFlgList() {
		return delFlgList;
	}

	/**
	 * @param list
	 */
	public void setDelFlgList(List list) {
		delFlgList = list;
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

}