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
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �R������񌟍��t�H�[��
 * 
 * ID RCSfile="$RCSfile: ShinsainSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class ShinsainSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R���������i���j */
	private String nameKanjiSei;

	/** �R���������i���j */
	private String nameKanjiMei;

//	/** �����@�֖� */
//	private String shozokuName;

	/** ���ȍזڃR�[�h */
	private String bunkaSaimokuCd;

//	/** �L�[���[�h */
//	private String keyword;

	/** �����@�֖��i�R�[�h�j*/
	private String shozokuCd;

	/** �S�����Ƌ敪 */
	private String jigyoKubun;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainSearchForm() {
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
		shinsainNo     = "";
		nameKanjiSei        = "";
		nameKanjiMei        = "";
//		shozokuName    = "";
		bunkaSaimokuCd = "";
//		keyword        = "";
		shozokuCd        = "";
		jigyoKubun        = "";
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

/*									
		//���͂��X�y�[�X(�S�p���p��)�ŕ��������l�ɑ΂��ă`�F�b�N���s��
		//���ȍזڃR�[�h�����`�F�b�N
		if(getBunkaSaimokuCd() != null && !getBunkaSaimokuCd().equals("")) {
			ArrayList list = this.separateString(getBunkaSaimokuCd());
			
			int cnt = list.size();
			for(int i=0; i<cnt; i++){
				String cnd = (String)list.get(i);


				if(cnd.length() > 4){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.maxlength", "���ȍזڃR�[�h ��" + (i+1) + "����", "4"));
				}
				if(cnd.length() != 4){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.length", "���ȍזڃR�[�h ��" + (i+1) + "����", "4"));
				}
		
				int lng = cnd.length();

				for(int j=0; j<lng; j++){
					if(!StringUtil.isDigit(cnd.charAt(j))){
						errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError("errors.integer", "���ȍזڃR�[�h ��" + (i+1) + "����"));
						break;
					}
				}
				
			}
		}

		//�L�[���[�h�����`�F�b�N
		if(getKeyword() != null && !getKeyword().equals("")) {
			ArrayList list = this.separateString(getKeyword());

			int cnt = list.size();
			for(int i=0; i<cnt; i++){
				String cnd = (String)list.get(i);
				
				if(cnd.length() > 20){
					errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.maxlength", "�L�[���[�h ��" + (i+1) + "����", "20"));
				}
				
			}
		}

		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------
*/
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
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

//	/**
//	 * @return
//	 */
//	public String getShozokuName() {
//		return shozokuName;
//	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

//	/**
//	 * @return
//	 */
//	public String getKeyword() {
//		return keyword;
//	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
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

//	/**
//	 * @param string
//	 */
//	public void setShozokuName(String string) {
//		shozokuName = string;
//	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setKeyword(String string) {
//		keyword = string;
//	}

//	/* (�� Javadoc)
//	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#separateString(java.lang.String)
//	 */
//	private ArrayList separateString(String str){
//		str = str.trim();
//		ArrayList arrayl = new ArrayList();
//			
//		while(true){
//			int idx_low = str.indexOf(" ");										//���p�X�y�[�X�̃C���f�b�N�X
//			int idx_up = str.indexOf("�@");										//�S�p�X�y�[�X�̃C���f�b�N�X
//				
//			//���p�X�y�[�X�A�S�p�X�y�[�X�Ƃ��ɊY���Ȃ�
//			if(idx_low == -1 && idx_up == -1){
//				if(!str.equals("")){
//					arrayl.add(str);
//				}
//				break;
//			}
//			//�S�p�X�y�[�X���Y���Ȃ�
//			else if(idx_up == -1){
//				String condi = str.substring(0, idx_low);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_low+1);
//			}
//			//���p�X�y�[�X���Y���Ȃ�
//			else if(idx_low == -1){
//				String condi = str.substring(0, idx_up);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_up+1);
//			}
//			//���p�X�y�[�X�A�S�p�X�y�[�X�Ƃ��Y������
//			else{
//				//���p�X�y�[�X����ɊY������
//				if(idx_low < idx_up){
//					String condi = str.substring(0, idx_low);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_low+1);
//				}
//				//�S�p�X�y�[�X����ɊY������
//				else{
//					String condi = str.substring(0, idx_up);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_up+1);
//				}
//			}
//		}
//			
//		return arrayl;
//	}

/**
 * @return
 */
public String getShozokuCd() {
	return shozokuCd;
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
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

}
