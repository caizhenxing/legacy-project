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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\���ҏ��X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v����\���ҏ����擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * �\���ҏ��X�V��ʂ�\������B
 * 
 */
public class EditAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!shinseishaForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�Ώې\���ҏ��̎擾
		ShinseishaPk pkInfo = new ShinseishaPk();
		//------�L�[���
		String shinseishaId = ((ShinseishaForm)form).getShinseishaId();
		pkInfo.setShinseishaId(shinseishaId);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		ShinseishaInfo editInfo = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
			
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinseishaInfo(editInfo);
		
		//------�X�V�Ώې\���ҏ����A�X�V�o�^�t�H�[�����̍X�V
		ShinseishaForm editForm = new ShinseishaForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------�v���_�E���f�[�^�Z�b�g
//		editForm.setShubetuCdList(LabelValueManager.getBukyokuShubetuCdList());
		editForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		editForm.setHikoboFlgList(LabelValueManager.getHikoboFlgList());
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"nameRoSei",editInfo.getNameRoSei());
			BeanUtils.copyProperty(editForm,"nameRoMei",editInfo.getNameRoMei());
			BeanUtils.copyProperty(editForm,"kenkyuNo",editInfo.getKenkyuNo());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"shozokuName",editInfo.getShozokuName());
			BeanUtils.copyProperty(editForm,"shozokuNameEigo",editInfo.getShozokuNameEigo());
			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuName",editInfo.getBukyokuName());
			//2006/02/09�@�ǉ� ��������-------------------------------------------------------
			BeanUtils.copyProperty(editForm,"ouboShikaku",editInfo.getOuboshikaku());
			//�ǉ� �����܂�-------------------------------------------------------------------
//			BeanUtils.copyProperty(editForm,"bukyokuShubetuCd",editInfo.getBukyokuShubetuCd());
//			if(editInfo.getBukyokuShubetuCd() != null && editInfo.getBukyokuShubetuCd().equals("9")){
//				BeanUtils.copyProperty(editForm,"bukyokuShubetuName",editInfo.getBukyokuShubetuName());
//			}
			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			//2005/04/25�@�폜 ��������-------------------------------------------------------
			//���R�@�E���擾�̂���
			//if(editInfo.getShokushuCd() != null
			//	&& (editInfo.getShokushuCd().equals("24") || editInfo.getShokushuCd().equals("25"))) {
			//�폜 �����܂�-------------------------------------------------------------------
				BeanUtils.copyProperty(editForm,"shokushuNameKanji",editInfo.getShokushuNameKanji());
			//}
			BeanUtils.copyProperty(editForm,"shokushuNameRyaku",editInfo.getShokushuNameRyaku());
			
			//2005/04/25�@�ǉ� ��������-------------------------------------------------------
			//���R ���N�����ǉ��̂���
			BeanUtils.copyProperty(editForm,"birthday",editInfo.getBirthday());
			//�ǉ� �����܂�-------------------------------------------------------------------
		
			//�L�������̐ݒ�		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}
			
			BeanUtils.copyProperty(editForm,"hikoboFlg",editInfo.getHikoboFlg());
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//------�C���o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
