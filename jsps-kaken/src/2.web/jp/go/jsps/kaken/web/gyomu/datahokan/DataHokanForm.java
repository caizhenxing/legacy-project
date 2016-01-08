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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �f�[�^�ۊǃt�H�[���B
 * 
 * ID RCSfile="$RCSfile: DataHokanForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class DataHokanForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** ���ƃ��X�g */
	private List jigyoList;
	
	/** ����CD */
	private String jigyoCd;
	
	/** ����ID */
	private String jigyoId;
	
	/** ���Ɩ� */
	private String jigyoName;
	
	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;
	
	/** �L�������i�N�j */
	private String yukoKigenYear;
	
	/** �L�������i���j */
	private String yukoKigenMonth;
	
	/** �L�������i���j */
	private String yukoKigenDate;
	
	/** �\���Җ��i���j */
	private String shinseishaNameKanjiSei;
	
	/** �\���Җ��i���j */
	private String shinseishaNameKanjiMei;
	
	/** �\���Ҏ����i�t���K�i-���j */
	private String shinseishaNameKanaSei;
	
	/** �\���Ҏ����i�t���K�i-���j*/
	private String shinseishaNameKanaMei;
	
	/** �\���Җ��i���[�}���F���j */
	private String shinseishaNameRoSei;
	
	/** �\���Җ��i���[�}���F���j */
	private String shinseishaNameRoMei;
		
	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �\���Ҍ����Ҕԍ� */
	private String kenkyuNo;
	
	/** �\���ԍ� */
	private String uketukeNo;
	
	/** �זڔԍ� */
	private String bunkaSaimokuCd;	
	
	/** �ۊǏ������� */
	private int shoriKensu;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public DataHokanForm() {
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
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
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
	public String getJigyoCd() {
		return jigyoCd;
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
	public String getKaisu() {
		return kaisu;
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
	public String getShinseishaNameKanjiMei() {
		return shinseishaNameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanjiSei() {
		return shinseishaNameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameRoMei() {
		return shinseishaNameRoMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameRoSei() {
		return shinseishaNameRoSei;
	}

	/**
	 * @return
	 */
	public int getShoriKensu() {
		return shoriKensu;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @return
	 */
	public String getYukoKigenDate() {
		return yukoKigenDate;
	}

	/**
	 * @return
	 */
	public String getYukoKigenMonth() {
		return yukoKigenMonth;
	}

	/**
	 * @return
	 */
	public String getYukoKigenYear() {
		return yukoKigenYear;
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
	public void setJigyoList(List list) {
		jigyoList = list;
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
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanjiMei(String string) {
		shinseishaNameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanjiSei(String string) {
		shinseishaNameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameRoMei(String string) {
		shinseishaNameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameRoSei(String string) {
		shinseishaNameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenDate(String string) {
		yukoKigenDate = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenMonth(String string) {
		yukoKigenMonth = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenYear(String string) {
		yukoKigenYear = string;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param i
	 */
	public void setShoriKensu(int i) {
		shoriKensu = i;
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
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
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
	public String getShinseishaNameKanaMei() {
		return shinseishaNameKanaMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanaSei() {
		return shinseishaNameKanaSei;
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
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanaMei(String string) {
		shinseishaNameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanaSei(String string) {
		shinseishaNameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

}
