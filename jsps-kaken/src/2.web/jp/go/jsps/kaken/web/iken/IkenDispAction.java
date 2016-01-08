/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenDispAction.java
 *    Description : �ӌ����\���A�N�V�����N���X�B
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
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.util.Page;
//import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.model.vo.IkenInfo;

/**
 * @author user1
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
public class IkenDispAction extends BaseAction {

	/* (Javadoc �Ȃ�)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response,
				UserContainer container)
			throws ApplicationException {
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();


		//�ӌ����������t�H�[���̎擾
		IkenForm ikenForm = (IkenForm)form;

		if (log.isDebugEnabled()){
			log.debug("�ӌ����擾�L�[�F" + ikenForm.getSystem_no());
		}

		//�����p�T�[�r�X�̎擾
		ISystemServise servise = getSystemServise(IServiceName.IKEN_MAINTENANCE_SERVICE);

		//�������s
		IkenInfo result = servise.selectIkenDataInfo(
									ikenForm.getSystem_no(),	//�V�X�e����t�ԍ�
									ikenForm.getTaishoID()		//�Ώێ�ID
								);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		//request.setAttribute(IConstants.RESULT_INFO,result);
		request.setAttribute("ikeninfo",result);

		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		// TODO ���������������\�b�h�E�X�^�u
		return forwardSuccess(mapping);
	}

}
