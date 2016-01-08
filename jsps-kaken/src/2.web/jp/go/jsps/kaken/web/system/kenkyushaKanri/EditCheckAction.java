/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �X�V���ꂽ�����ҏ��̓��̓`�F�b�N���s���B
 * �����ғo�^���l�I�u�W�F�N�g���쐬����B
 * �C���m�F��ʂ�\������B 
 */
public class EditCheckAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�C���o�^�t�H�[�����̎擾
		KenkyushaForm editForm = (KenkyushaForm) form;

		//------�Z�b�V�������X�V�Ώۏ��̎擾
		KenkyushaInfo editInfo = new KenkyushaInfo();

		//VO�Ƀf�[�^���Z�b�g����B
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setKenkyuNo(editForm.getKenkyuNo());
		editInfo.setShozokuCd(editForm.getShozokuCd());
		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setSeibetsu(editForm.getSeibetsu());
		editInfo.setBiko(editForm.getBiko());
		editInfo.setGakui(editForm.getGakui());

		//���N�����iString��Date)
		if(editForm.getBirthYear() != null && !editForm.getBirthYear().equals("")
				&& editForm.getBirthMonth()!= null && !editForm.getBirthMonth().equals("") 
				&& editForm.getBirthDay() != null && !editForm.getBirthDay().equals("")){
			DateUtil dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getBirthYear(),editForm.getBirthMonth(),editForm.getBirthDay());
			editInfo.setBirthday(dateUtil.getCal().getTime());
		}
		//-------��
	
		//�ǉ���������@2006/02/27
		editInfo.setOuboShikaku(editForm.getOuboShikaku());
		//�ǉ������܂Ł@�c
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		try {
			//�T�[�o���̓`�F�b�N
			editInfo =service.validate(
					container.getUserInfo(),
					editInfo, IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}
		
		//2005/05/25 �ǉ� ��������-----------------------------------
		//���R �ύX�������ǃR�[�h�̕��ǖ���form�ɕێ����邽��
		editForm.setBukyokuName(editInfo.getBukyokuName());
		//�ǉ� �����܂�----------------------------------------------

		//-----�Z�b�V�����Ɍ����ҏ���o�^����B
		container.setKenkyushaInfo(editInfo);

		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

		return forwardSuccess(mapping);
	}

}
