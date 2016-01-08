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
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

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
 */
public class KyakkaAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

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
