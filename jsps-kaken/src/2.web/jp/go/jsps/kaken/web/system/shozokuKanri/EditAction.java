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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �����@�֏��X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v���鏊���@�֏����擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * �����@�֏��X�V��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shozokuInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!shozokuForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�Ώۏ����@�֏��̎擾
		ShozokuPk pkInfo = new ShozokuPk();
		//------�L�[���
		String shozokuTantoId = ((ShozokuForm)form).getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuTantoId);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		ShozokuInfo editInfo = 
			getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		
		//------�X�V�Ώۏ����@�֏����A�X�V�o�^�t�H�[�����̍X�V
		ShozokuForm editForm = new ShozokuForm();
		//------�f�[�^���͂�2�y�[�W��
		editForm.setPage(2);
		editForm.setAction(BaseForm.EDIT_ACTION);
	
		try {
			PropertyUtils.copyProperties(editForm, editInfo);

		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
				
		//�L�������̐ݒ�		
		Calendar calendar = Calendar.getInstance();
		if(editInfo.getYukoDate() != null){
			calendar.setTime(editInfo.getYukoDate());
			editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
		}
		
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShozokuInfo(editInfo);
		
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
