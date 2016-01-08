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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �Ɩ��S���ҏ��폜�O�A�N�V�����N���X�B
 * �폜�ΏۋƖ��S���ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �폜�m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: DeleteAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
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

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�C���o�^�t�H�[�����̎擾
		GyomutantoForm deleteForm = (GyomutantoForm) form;
		
		//------�폜�ΏۋƖ��S���ҏ��̎擾
		GyomutantoPk pkInfo = new GyomutantoPk();
		//------�L�[���
		String gyomutantoId = deleteForm.getGyomutantoId();
		pkInfo.setGyomutantoId(gyomutantoId);

		//------�L�[�������ɍ폜�f�[�^�擾	
		GyomutantoInfo deleteInfo = getSystemServise(IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		//------�S�����ƕ\���p�ɐݒ�
		//deleteInfo.setJigyoNameList(deleteForm.getJigyoNameList());
		deleteInfo.setJigyoNameList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setGyomutantoInfo(deleteInfo);

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
