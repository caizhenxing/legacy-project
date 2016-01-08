/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenForm.java
 *    Description : ���ӌ����v�]�����̓t�H�[���N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * ���ӌ����v�]�����̓t�H�[���N���X�B
 * ID RCSfile="$RCSfile: IkenForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * ���ӌ����v�]�B
	 */
	private String ikenInfo;
	
	/**
	 * �Ώێ�ID
	 * 1:�\���ҁA2:�����@�֒S���ҁA4:�R�����A5:���ۃZ���^�[�������A6:���ǒS����
	 */
	private String taishoID;

	/** �V�X�e����t�ԍ� */
	private String system_no;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public IkenForm() {
		super();
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
		
		this.ikenInfo = "";
		this.taishoID = "";
		this.system_no = "";
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//�X�[�p�[�N���X�̏������Ăяo���B 
		ActionErrors errors = super.validate(mapping, request);
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getIkenInfo() {
		return ikenInfo;
	}

	/**
	 * @param string
	 */
	public void setIkenInfo(String string) {
		ikenInfo = string;
	}

	/**
	 * �Ώێ�ID���擾����
	 * @return�@�Ώێ�ID
	 */
	public String getTaishoID(){
		return taishoID;
	}
	
	/**
	 * �Ώێ�ID��ݒ肷��
	 * @param s
	 */
	public void setTaishoID(String s){
		taishoID = s;
	}

	/**
	 * �V�X�e����t�ԍ��̎擾
	 * @return
	 */
	public String getSystem_no(){
		return system_no;
	}
	
	/**
	 * �V�X�e����t�ԍ��̐ݒ�
	 * @param str
	 */
	public void setSystem_no(String str){
		system_no = str;
	}
}
