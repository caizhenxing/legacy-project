/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : CreatePunchAction.java
 *    Description : �p���`�f�[�^�쐬�A�N�V����
 *
 *    Author      : Yuji Kainuma
 *    Date        : 2004/11/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p���`�f�[�^�쐬�A�N�V�����N���X�B
 * �p���`�f�[�^�����A�ADB���X�V����B
 * 
 * ID RCSfile="$RCSfile: CreatePunchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class CreatePunchAction extends BaseAction{

/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException
	{
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
			
		//�錾
		PunchDataForm punchDataForm = (PunchDataForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		PunchDataKanriInfo punchDataKanriInfo = new PunchDataKanriInfo();
		try {
			PropertyUtils.copyProperties(punchDataKanriInfo, punchDataForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		//-------��
		
		//------�Z�b�V�������V�K�o�^���̎擾
		//PunchDataKanriInfo addInfo = container.getPunchDataKanriInfo();
		
		//�T�[�r�X�̎擾
		ISystemServise service = getSystemServise(IServiceName.PUNCHDATA_MAINTENANCE_SERVICE);

		//�T�[�r�X�@�\�̌Ăяo��
		punchDataKanriInfo = service.getPunchData(container.getUserInfo(), punchDataForm.getPunchShubetu());
		
		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO, punchDataKanriInfo);
		
		//-----�Z�b�V�����Ƀp���`�f�[�^�Ǘ�����o�^����B
		container.setPunchDataKanriInfo(punchDataKanriInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);

	}
	
}
