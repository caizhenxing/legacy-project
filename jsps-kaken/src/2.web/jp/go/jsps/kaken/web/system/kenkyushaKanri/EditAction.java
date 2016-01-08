/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �����ҏ��X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v���錤���ҏ����擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * �����ҏ��X�V��ʂ�\������B
 */
public class EditAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.kenkyushaInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!kenkyushaForm.�v���p�e�B��

		//------�X�V�Ώی����ҏ��̎擾
		KenkyushaPk pkInfo = new KenkyushaPk();
		//------�L�[���
		String kenkyuNo = ((KenkyushaForm)form).getKenkyuNo();
		String shozokuCd = ((KenkyushaForm)form).getShozokuCd();
		pkInfo.setKenkyuNo(kenkyuNo);
		pkInfo.setShozokuCd(shozokuCd);
		
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		KenkyushaInfo editInfo = service.selectKenkyushaData(container.getUserInfo(),pkInfo, false);
	
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setKenkyushaInfo(editInfo);
		
		//------�X�V�Ώی����ҏ����A�X�V�o�^�t�H�[�����̍X�V
		KenkyushaForm editForm = new KenkyushaForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------�v���_�E���f�[�^�Z�b�g
		editForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		editForm.setGakuiList(LabelValueManager.getGakuiList());
		editForm.setSeibetsuList(LabelValueManager.getSeibetsuList());
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"kenkyuNo",editInfo.getKenkyuNo());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			BeanUtils.copyProperty(editForm,"gakui", editInfo.getGakui());
			
			//2005/05/25 �ǉ� ��������----------------------------------------------------
			//�����@�֖��A���ǖ��̕\���̂���
			BeanUtils.copyProperty(editForm, "shozokuNameKanji", editInfo.getShozokuNameKanji());
			BeanUtils.copyProperty(editForm, "shozokuNameEigo", editInfo.getShozokuNameEigo());			
			BeanUtils.copyProperty(editForm, "bukyokuName", editInfo.getBukyokuName());	
			//�ǉ� �����܂�---------------------------------------------------------------
			
			//���N�����̐ݒ�		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getBirthday() != null){
				calendar.setTime(editInfo.getBirthday());
				editForm.setBirthYear("" + calendar.get(Calendar.YEAR));
				editForm.setBirthMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setBirthDay("" + calendar.get(Calendar.DATE));
			}
			
			BeanUtils.copyProperty(editForm,"seibetsu",editInfo.getSeibetsu());
			BeanUtils.copyProperty(editForm, "biko", editInfo.getBiko());
			//2006/02/27 �ǉ���������
			BeanUtils.copyProperty(editForm, "ouboShikaku", editInfo.getOuboShikaku());
			//Nae �����܂�
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//------�C���o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);

		return forwardSuccess(mapping);
	}

}
