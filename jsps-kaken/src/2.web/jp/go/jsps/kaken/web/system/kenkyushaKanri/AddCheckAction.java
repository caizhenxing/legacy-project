/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �V�K�o�^���ꂽ�����ҏ��̓��̓`�F�b�N���s���B
 * �����ғo�^���l�I�u�W�F�N�g���쐬����B
 * �o�^�m�F��ʂ�\������B
 * 
 */
public class AddCheckAction extends BaseAction {

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
		KenkyushaForm addForm = (KenkyushaForm)form;

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		KenkyushaInfo addInfo = new KenkyushaInfo();
		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//���N�����iString��Date)
		if(addForm.getBirthYear() != null && !addForm.getBirthYear().equals("")
					&& addForm.getBirthMonth()!= null && !addForm.getBirthMonth().equals("") 
					&& addForm.getBirthDay() != null && !addForm.getBirthDay().equals("")){
			DateUtil dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getBirthYear(),addForm.getBirthMonth(),addForm.getBirthDay());
			addInfo.setBirthday(dateUtil.getCal().getTime());
		}
		//-------��
		
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		try {
			//�T�[�o���̓`�F�b�N
			addInfo = service.validate(container.getUserInfo(),addInfo, IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}

		//-----�Z�b�V�����Ɍ����ҏ���o�^����B
		container.setKenkyushaInfo(addInfo);

		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

		return forwardSuccess(mapping);
	}

}
