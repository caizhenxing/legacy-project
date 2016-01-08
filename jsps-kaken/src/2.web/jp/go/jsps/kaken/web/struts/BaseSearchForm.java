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
package jp.go.jsps.kaken.web.struts;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.ActionMapping;

/**
 * �����t�H�[���̊�{�ƂȂ�N���X�B
 * 
 * ID RCSfile="$RCSfile: BaseSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class BaseSearchForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ���O
	 */
	protected static Log log = LogFactory.getLog(BaseSearchForm.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\���J�n�s�ԍ� */
	private int startPosition;
	
	/** 1 �y�[�W������̕\������ */
	private int pageSize = ApplicationSettings.getInt(ISettingKeys.PAGE_SIZE);
	
	/** �f�[�^����MAX�l */
	private int maxSize = ApplicationSettings.getInt(ISettingKeys.MAX_RECORD_COUNT);
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public BaseSearchForm() {
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
		startPosition = 0;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * @param i
	 */
	public void setStartPosition(int i) {
		startPosition = i;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param i
	 */
	public void setMaxSize(int i) {
		maxSize = i;
	}

}
