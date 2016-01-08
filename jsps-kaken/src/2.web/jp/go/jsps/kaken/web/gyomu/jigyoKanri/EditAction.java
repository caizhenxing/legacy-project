/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : EditAction.java
 *    Description : ���ƊǗ����X�V�O�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *    2006/06/13    V1.0        DIS.liYH       �C��
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ƊǗ����X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v���鎖�ƊǗ������擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * ���ƊǗ����X�V��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:05 $"
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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.jigyoKanriInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!jigyoKanriForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�Ώێ��ƊǗ����̎擾
		JigyoKanriPk pkInfo = new JigyoKanriPk();
		//------�L�[���
		String jigyoId = ((JigyoKanriForm)form).getJigyoId();
		pkInfo.setJigyoId(jigyoId);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		JigyoKanriInfo editInfo = getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setJigyoKanriInfo(editInfo);
		
		//------�X�V�Ώێ��ƊǗ������A�X�V�o�^�t�H�[�����̍X�V
		JigyoKanriForm editForm = new JigyoKanriForm();
		editForm.setAction(BaseForm.EDIT_ACTION);
	
		try {
			PropertyUtils.copyProperties(editForm, editInfo);
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
			
		//------���W�I�{�^���f�[�^�Z�b�g
		//�]���p�t�@�C���L���i�Ȃ��^����j
		editForm.setFlgList(LabelValueManager.getFlgList());

		//------���t�f�[�^�Z�b�g
		//�w�U��t���ԁi�J�n�j		
		Calendar calendar = Calendar.getInstance();
		if(editInfo.getUketukekikanStart() != null){
			calendar.setTime(editInfo.getUketukekikanStart());
			editForm.setUketukekikanStartYear("" + calendar.get(Calendar.YEAR));
			editForm.setUketukekikanStartMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setUketukekikanStartDay("" + calendar.get(Calendar.DATE));
		}		
		//�w�U��t���ԁi�I���j
		calendar = Calendar.getInstance();	
		if(editInfo.getUketukekikanEnd() != null){
			calendar.setTime(editInfo.getUketukekikanEnd());
			editForm.setUketukekikanEndYear("" + calendar.get(Calendar.YEAR));
			editForm.setUketukekikanEndMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setUketukekikanEndDay("" + calendar.get(Calendar.DATE));
		}
		//�����Җ���o�^�ŏI���ؓ�
		calendar = Calendar.getInstance();
		if(editInfo.getMeiboDate() != null){
			calendar.setTime(editInfo.getMeiboDate());
			editForm.setMeiboDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setMeiboDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setMeiboDateDay("" + calendar.get(Calendar.DATE));
		}
		
		//�@2006/06/13�@�ǉ��@���`�؁@��������
		//���̈�ԍ����s���ؓ�
		calendar = Calendar.getInstance();
		if (editInfo.getKariryoikiNoEndDate() != null) {
			calendar.setTime(editInfo.getKariryoikiNoEndDate());
			editForm.setKariryoikiNoEndDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setKariryoikiNoEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setKariryoikiNoEndDateDay("" + calendar.get(Calendar.DATE));
		}
		//�@2006/06/13�@�ǉ��@���`�؁@�����܂�
//�@2006/07/10�@�ǉ��@���`�؁@��������
        //�̈��\�Ҋm����ؓ�
        calendar = Calendar.getInstance();
        if (editInfo.getRyoikiEndDate() != null) {
            calendar.setTime(editInfo.getRyoikiEndDate());
            editForm.setRyoikiEndDateYear("" + calendar.get(Calendar.YEAR));
            editForm.setRyoikiEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
            editForm.setRyoikiEndDateDay("" + calendar.get(Calendar.DATE));
        }
//�@2006/07/10�@�ǉ��@���`�؁@�����܂�
		
//�@2006/10/24�@�ǉ��@�Ո��@��������
        //���Q�֌W���͒��ؓ�
        calendar = Calendar.getInstance();
        if (editInfo.getRigaiEndDate() != null) {
            calendar.setTime(editInfo.getRigaiEndDate());
            editForm.setRigaiEndDateYear("" + calendar.get(Calendar.YEAR));
            editForm.setRigaiEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
            editForm.setRigaiEndDateDay("" + calendar.get(Calendar.DATE));
        }
//�@2006/10/24�@�ǉ��@�Ո��@�����܂�
		
		//�R������
		calendar = Calendar.getInstance();
		if(editInfo.getShinsaKigen() != null){
			calendar.setTime(editInfo.getShinsaKigen());
			editForm.setShinsaKigenYear("" + calendar.get(Calendar.YEAR));
			editForm.setShinsaKigenMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setShinsaKigenDay("" + calendar.get(Calendar.DATE));
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