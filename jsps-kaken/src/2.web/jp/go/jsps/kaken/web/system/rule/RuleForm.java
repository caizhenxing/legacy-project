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
package jp.go.jsps.kaken.web.system.rule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: RuleForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:45 $"
 */
public class RuleForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �Ώێ�ID���X�g */
	private List taishoIdList = new ArrayList();

	/** ���������X�g */
	private List mojisuChkList = new ArrayList();

	/** �啶���E�������̍��݃��X�g */
	private List charChk1List = new ArrayList();

	/** �啶���E�������̍��݃��W�I���X�g */
	private List radioCharChk1List = new ArrayList();

	/** �A���t�@�x�b�g�Ɛ����̍��݃��X�g */
	private List charChk2List = new ArrayList();

	/** �A���t�@�x�b�g�Ɛ����̍��݃��W�I���X�g */
	private List radioCharChk2List = new ArrayList();

	/** �\��1���X�g */
	private List charChk3List = new ArrayList();

	/** �\��1���W�I���X�g */
	private List radioCharChk3List = new ArrayList();

	/** �\��2���X�g */
	private List charChk4List = new ArrayList();

	/** �\��2���W�I���X�g */
	private List radioCharChk4List = new ArrayList();

	/** �\��3���X�g */
	private List charChk5List = new ArrayList();

	/** �\��3���W�I���X�g */
	private List radioCharChk5List = new ArrayList();

	/** ���l���X�g*/
	private List bikoList = new ArrayList();

	/** �L������(�N)���X�g */
	private List yukoDateYearList = new ArrayList();

	/** �L������(��)���X�g */
	private List yukoDateMonthList = new ArrayList();

	/** �L������(��)���X�g */
	private List yukoDateDayList = new ArrayList();

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public RuleForm() {
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
	public List getBikoList() {
		return bikoList;
	}

	/**
	 * @return
	 */
	public List getCharChk1List() {
		return charChk1List;
	}

	/**
	 * @return
	 */
	public List getCharChk2List() {
		return charChk2List;
	}

	/**
	 * @return
	 */
	public List getCharChk3List() {
		return charChk3List;
	}

	/**
	 * @return
	 */
	public List getCharChk4List() {
		return charChk4List;
	}

	/**
	 * @return
	 */
	public List getCharChk5List() {
		return charChk5List;
	}

	/**
	 * @return
	 */
	public List getMojisuChkList() {
		return mojisuChkList;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk1List() {
		return radioCharChk1List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk2List() {
		return radioCharChk2List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk3List() {
		return radioCharChk3List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk4List() {
		return radioCharChk4List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk5List() {
		return radioCharChk5List;
	}

	/**
	 * @return
	 */
	public List getTaishoIdList() {
		return taishoIdList;
	}

	/**
	 * @return
	 */
	public List getYukoDateDayList() {
		return yukoDateDayList;
	}

	/**
	 * @return
	 */
	public List getYukoDateMonthList() {
		return yukoDateMonthList;
	}

	/**
	 * @return
	 */
	public List getYukoDateYearList() {
		return yukoDateYearList;
	}

	/**
	 * @param list
	 */
	public void setBikoList(List list) {
		bikoList = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk1List(List list) {
		charChk1List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk2List(List list) {
		charChk2List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk3List(List list) {
		charChk3List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk4List(List list) {
		charChk4List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk5List(List list) {
		charChk5List = list;
	}

	/**
	 * @param list
	 */
	public void setMojisuChkList(List list) {
		mojisuChkList = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk1List(List list) {
		radioCharChk1List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk2List(List list) {
		radioCharChk2List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk3List(List list) {
		radioCharChk3List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk4List(List list) {
		radioCharChk4List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk5List(List list) {
		radioCharChk5List = list;
	}

	/**
	 * @param list
	 */
	public void setTaishoIdList(List list) {
		taishoIdList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateDayList(List list) {
		yukoDateDayList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateMonthList(List list) {
		yukoDateMonthList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateYearList(List list) {
		yukoDateYearList = list;
	}

}
