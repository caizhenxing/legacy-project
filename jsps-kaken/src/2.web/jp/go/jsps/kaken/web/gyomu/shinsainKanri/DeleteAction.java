/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �R�������폜�O�A�N�V�����N���X�B
 * �폜�ΏېR���������擾�B�Z�b�V�����ɓo�^����B 
 * �폜�m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: DeleteAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class DeleteAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinsainInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�C���o�^�t�H�[�����̎擾
		ShinsainForm deleteForm = (ShinsainForm) form;
		
		//------�폜�Ώې\���ҏ��̎擾
		ShinsainPk pkInfo = new ShinsainPk();
		//------�L�[���
		String shinsainNo = deleteForm.getShinsainNo();
		String jigyoKubun = deleteForm.getJigyoKubun();
		pkInfo.setShinsainNo(shinsainNo);
		pkInfo.setJigyoKubun(jigyoKubun);

		//------�L�[�������ɍ폜�f�[�^�擾	
		ShinsainInfo deleteInfo = getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

//		//�V�K�E�p��
//		if(deleteInfo.getSinkiKeizokuFlg() != null) {
//			deleteInfo.setSinkiKeizokuHyoji(LabelValueManager.getSinkiKeizokuFlgList(deleteInfo.getSinkiKeizokuFlg()));
//		}
//		//�Ӌ�
//		if(deleteInfo.getShakin() != null) {
//			deleteInfo.setShakinHyoji(LabelValueManager.getShakinList(deleteInfo.getShakin()));
//		}
		
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinsainInfo(deleteInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
