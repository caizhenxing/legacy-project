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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �V�K�o�^���ꂽ�����@�֏��̓��̓`�F�b�N���s���B
 * �����@�֓o�^���l�I�u�W�F�N�g���쐬����B
 * �o�^�m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
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
		
		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//------�V�K�o�^�t�H�[�����̎擾
		ShozokuForm addForm = (ShozokuForm) form;


		//2�d�o�^�`�F�b�N
		//�����@�֒S���ҏ��e�[�u���ɂ��łɏ����@�փR�[�h��
		//���������@�֒S���҂��o�^����Ă��Ȃ����ǂ������m�F
		int count =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				addForm.getShozokuCd());	

		//���łɓo�^����Ă���ꍇ
		if(count > 0){
			String[] error = {"�����@�֒S����"};
			throw new ApplicationException("���łɏ����@�֒S���҂��o�^����Ă��܂��B�����L�[�F�����@�փR�[�h'" 
														+ addForm.getShozokuCd() + "'", 	new ErrorInfo("errors.4007", error));			
		}

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShozokuInfo addInfo = container.getShozokuInfo();
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

		//-----�Z�b�V�����ɏ����@�֏���o�^����B
		container.setShozokuInfo(addInfo);

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
