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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *  �R��������U�茋�ʏC���O�A�N�V�����N���X�B
 * �L�[�Ɉ�v����R��������U������擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 *  �R��������U�茋�ʏC����ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: WarifuriEditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriEditAction extends BaseAction {

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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.warifuriInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!warifuriForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�Ώۊ���U�茋�ʏ��̎擾
		ShinsaKekkaPk pkInfo = new ShinsaKekkaPk();
		
		//------�t�H�[�����̎擾
		WarifuriForm editForm = (WarifuriForm) form;

		//------�L�[���
		pkInfo.setSystemNo(editForm.getSystemNo());			//�V�X�e���ԍ�
		pkInfo.setShinsainNo(editForm.getShinsainNo());		//�R�����ԍ�
		pkInfo.setJigyoKubun(editForm.getJigyoKubun());		//���Ƌ敪
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		WarifuriInfo editInfo =
				 getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE).select(
				 													container.getUserInfo(),
				 													pkInfo);

		//------�X�V�Ώې\���ҏ����A�X�V�o�^�t�H�[�����̍X�V
//		WarifuriForm editForm = new WarifuriForm();
//		editForm.setAction(BaseForm.EDIT_ACTION);

		try {
			BeanUtils.copyProperties(editForm, editInfo);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		//�R�����ԍ��i�C���O�j�ɐR�����ԍ����Z�b�g
		editForm.setOldShinsainNo(editInfo.getShinsainNo());
		
		//�R�������i����-���j��NULL�̏ꍇ�́A�R�����ԍ������Z�b�g
		if(editInfo.getShinsainNameKanjiSei() == null){
			editForm.setShinsainNo("");
		}

		//------�C���o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);

		//-----�Z�b�V�����Ɋ���U�茋�ʏ���o�^����B
		container.setWarifuriInfo(editInfo);
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
