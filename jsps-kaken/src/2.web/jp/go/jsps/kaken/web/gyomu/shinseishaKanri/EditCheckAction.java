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
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �X�V���ꂽ�\���ҏ��̓��̓`�F�b�N���s���B
 * �\���ғo�^���l�I�u�W�F�N�g���쐬����B
 * �C���m�F��ʂ�\������B 
 * 
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
		ShinseishaForm editForm = (ShinseishaForm) form;

		//------�Z�b�V�������X�V�Ώۏ��̎擾
		ShinseishaInfo editInfo = container.getShinseishaInfo();
		
		//2005/04/25 �폜 ��������-------------------------------------
		//�f�[�^�����x���\�L�̂���
		/*
		//VO�Ƀf�[�^���Z�b�g����B
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setNameRoSei(editForm.getNameRoSei());
		editInfo.setNameRoMei(editForm.getNameRoMei());
		editInfo.setKenkyuNo(editForm.getKenkyuNo());
		editInfo.setShozokuCd(editForm.getShozokuCd());
		editInfo.setShozokuName(editForm.getShozokuName());
		editInfo.setShozokuNameEigo(editForm.getShozokuNameEigo());
		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setBukyokuName(editForm.getBukyokuName());
//		editInfo.setBukyokuShubetuCd(editForm.getBukyokuShubetuCd());
//		editInfo.setBukyokuShubetuName(editForm.getBukyokuShubetuName());
		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setShokushuNameKanji(editForm.getShokushuNameKanji());
		editInfo.setBiko(editForm.getBiko());
		*/
		//�폜 �����܂�-----------------------------------------------
		//�L�������iString��Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------��
				
		editInfo.setHikoboFlg(editForm.getHikoboFlg());
		//20060217  dhy   start
		editInfo.setOuboshikaku(editForm.getOuboShikaku());
        //20060217   end
		try {
			//�T�[�o���̓`�F�b�N
			editInfo =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}

		//-----�Z�b�V�����ɐ\���ҏ���o�^����B
		container.setShinseishaInfo(editInfo);

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
