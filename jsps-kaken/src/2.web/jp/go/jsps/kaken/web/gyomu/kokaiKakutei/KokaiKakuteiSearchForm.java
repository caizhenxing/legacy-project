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
package jp.go.jsps.kaken.web.gyomu.kokaiKakutei;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: KokaiKakuteiSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class KokaiKakuteiSearchForm extends BaseSearchForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** ����ID */
	private List     jigyoIds;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KokaiKakuteiSearchForm() {
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
//		init();
	}

	/**
	 * ����������
	 */
	public void init() {
		jigyoIds = new Vector();
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
	 * �L�[�Ɉ�v���鎖��ID��ݒ肷��B
	 * @param key		�L�[
	 * @param value		����ID
	 */
	public void setJigyoId(int key, Object value) {
		jigyoIds.add(value);
	 }

	/**
	 * �L�[�Ɉ�v���鎖��ID���擾����B
	 * @param key		�L�[
	 * @return	����ID
	 */
	public Object getJigyoId(int key) {
		 return jigyoIds.get(key);
	 }

	/**
	 * @return
	 */
	public List getJigyoIds() {
		return jigyoIds;
	}

	/**
	 * @param list
	 */
	public void setJigyoIds(List list) {
		jigyoIds = list;
	}

}