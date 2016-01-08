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
package jp.go.jsps.kaken.web.gyomu.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �V�K�o�^���ꂽ�\���ҏ��̓��̓`�F�b�N���s���B
 * �\���ғo�^���l�I�u�W�F�N�g���쐬����B
 * �o�^�m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:23 $"
 */
public class AddCheckAction extends BaseAction {

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

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�V�K�o�^�t�H�[�����̎擾
		ShinseishaForm addForm = (ShinseishaForm) form;

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseishaInfo addInfo = new ShinseishaInfo();
		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//�L�������iString��Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(addForm.getYukoDateYear(),addForm.getYukoDateMonth(),addForm.getYukoDateDay());
		addInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------��
		
		try {
			//�T�[�o���̓`�F�b�N
			addInfo =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					addInfo,
					IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}

		//-----�Z�b�V�����ɐ\���ҏ���o�^����B
		container.setShinseishaInfo(addInfo);

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
