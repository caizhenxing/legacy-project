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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
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
 * �Ɩ��S���ҏ��X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v����Ɩ��S���ҏ����擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * �Ɩ��S���ҏ��X�V��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class EditAction extends BaseAction {

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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.gyomutantoInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!gymutantoForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�ΏۋƖ��S���ҏ��̎擾
		GyomutantoPk pkInfo = new GyomutantoPk();
		//------�L�[���
		String gyomutantoId = ((GyomutantoForm)form).getGyomutantoId();
		pkInfo.setGyomutantoId(gyomutantoId);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		GyomutantoInfo editInfo = getSystemServise(IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setGyomutantoInfo(editInfo);
		
		//------�X�V�ΏۋƖ��S���ҏ����A�X�V�o�^�t�H�[�����̍X�V
		GyomutantoForm editForm = new GyomutantoForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------�`�F�b�N�{�b�N�X�f�[�^�Z�b�g
		editForm.setJigyoNameList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"bukaName",editInfo.getBukaName());
			BeanUtils.copyProperty(editForm,"kakariName",editInfo.getKakariName());
			BeanUtils.copyProperty(editForm,"biko",editInfo.getBiko());
			BeanUtils.copyProperty(editForm,"valueList",editInfo.getJigyoValues());
			
			//�L�������̐ݒ�		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}
			
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
