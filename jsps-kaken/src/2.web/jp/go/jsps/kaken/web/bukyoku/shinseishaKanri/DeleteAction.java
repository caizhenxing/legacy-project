/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/04/11
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\���ҏ��폜�O�A�N�V�����N���X�B
 * �폜�Ώې\���ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �폜�m�F��ʂ�\������B
 * 
 */
public class DeleteAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------�C���o�^�t�H�[�����̎擾
		ShinseishaForm deleteForm = (ShinseishaForm) form;
		
		//------�폜�Ώې\���ҏ��̎擾
		ShinseishaPk pkInfo = new ShinseishaPk();
		//------�L�[���
		String shinseishaId = deleteForm.getShinseishaId();
		pkInfo.setShinseishaId(shinseishaId);

		//------�L�[�������ɍ폜�f�[�^�擾	
		ShinseishaInfo deleteInfo = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinseishaInfo(deleteInfo);
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
