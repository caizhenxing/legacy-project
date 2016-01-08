/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\�����p���O�A�N�V�����N���X�B
 * �p���Ώې\�������擾�B�Z�b�V�����ɓo�^����B 
 * �p���m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: KyakkaAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:39 $"
 */
public class KyakkaAction extends BaseAction {

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

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�p���Ώې\�����V�X�e���ԍ��̎擾
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;
		
		//------�p���Ώې\���V�X�e���ԍ��̎擾
		ShinseiDataPk pkInfo = new ShinseiDataPk();
		//------�L�[���
		String systemNo = shoninForm.getSystemNo();
		pkInfo.setSystemNo(systemNo);

		//------�L�[�������ɐ\���f�[�^�擾	
		SimpleShinseiDataInfo shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE).selectSimpleShinseiDataInfo(container.getUserInfo(),pkInfo);
		
		//------�p���Ώۏ������N�G�X�g�����ɃZ�b�g
		container.setSimpleShinseiDataInfo(shinseiInfo);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
//		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
