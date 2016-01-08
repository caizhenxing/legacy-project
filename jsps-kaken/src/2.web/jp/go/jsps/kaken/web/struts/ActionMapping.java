/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

/**
 * ActionMapping�Ƀ��O�C���`�F�b�N�̗L���̃v���p�e�B��ǉ�����B
 * 
 * ID RCSfile="$RCSfile: ActionMapping.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class ActionMapping extends org.apache.struts.action.ActionMapping {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���O�C���`�F�b�N���s�����ǂ����̑����B*/
	protected boolean logonCheck = true;
	
	/** �G���[���ɕ���{�^����\�����邩�ǂ����̑����B*/
	protected boolean errorClose = false;
	
	/** �󂫃������`�F�b�N���s�����ǂ����̑����B*/
	protected boolean memoryCheck = false;
	
    /** �A�N�V����������*/
    protected String description = "";
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * 	�R���X�g���N�^�B
	 */
	public ActionMapping() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * ���O�C���`�F�b�N���s�����ǂ������擾����B
	 * @return	���O�C���`�F�b�N���s���ꍇ true �ȊO false
	 */
	public boolean isLogonCheck() {
		return logonCheck;
	}

	/**
	 * ���O�C���`�F�b�N���s�����ǂ�����ݒ肷��B
	 * @param b�@���O�C���`�F�b�N���s���ꍇ true �ȊO false
	 */
	public void setLogonCheck(boolean b) {
		logonCheck = b;
	}

	/**
	 * �G���[��ʂŕ���{�^����\�����邩���擾����B
	 * @return	����{�^����\������ꍇ�@true �ȊO false
	 */
	public boolean isErrorClose() {
		return errorClose;
	}

	/**
 	 * �G���[��ʂŕ���{�^����\�����邩��ݒ肷��B
	 * @param b ����{�^����\������ꍇ�@true �ȊO false
	 */
	public void setErrorClose(boolean b) {
		errorClose = b;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @return
	 */
	public boolean isMemoryCheck() {
		return memoryCheck;
	}

	/**
	 * @param b
	 */
	public void setMemoryCheck(boolean b) {
		memoryCheck = b;
	}

}
