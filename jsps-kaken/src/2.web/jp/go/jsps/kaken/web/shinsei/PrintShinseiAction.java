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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
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
 * �\�������擾�A�N�V�����N���X�B
 * �\���󋵉�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: PrintShinseiAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */

public class PrintShinseiAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//-----�ȈՐ\���f�[�^�t�H�[���̎擾
		SimpleShinseiDataInfo simpleSinseiDataInfo 
			= getSimpleShinseiDataInfo(container, (SimpleShinseiForm)form);
		
		//-----�Z�b�V�����ɐ\������o�^����B
		container.setSimpleShinseiDataInfo(simpleSinseiDataInfo);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	

	/**
	 * �ȈՐ\���󋵎擾���\�b�h�B
	 * @param container �\���ҏ��
	 * @param form�@�\�������̓t�H�[��
	 * @return SimpleShinseiDataInfo �ȈՐ\���f�[�^���
	 * @throws ApplicationException
	 */
	private SimpleShinseiDataInfo getSimpleShinseiDataInfo
		(UserContainer container, SimpleShinseiForm form)
		throws ApplicationException
	{
		//�V�X�e����tNo���擾����
		String systemNo = form.getSystemNo();
		
		//���ƊǗ���L�[�I�u�W�F�N�g�̐���
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
		
		//DB��背�R�[�h���擾
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			SimpleShinseiDataInfo simpleShinseiDataInfo = servise.selectSimpleShinseiDataInfo(container.getUserInfo(),shinseiDataPk);
		
		//���l
		//2���R�����ʗL�F2���R�����ʔ��l���Z�b�g
		//2���R�����ʖ����A�󗝔��l�L�F�󗝔��l���Z�b�g
		if(simpleShinseiDataInfo.getShinsa2Biko() != null) {
			simpleShinseiDataInfo.setBiko(simpleShinseiDataInfo.getShinsa2Biko());
		}else if(simpleShinseiDataInfo.getJuriKekka() != null){
			simpleShinseiDataInfo.setBiko(simpleShinseiDataInfo.getJuriBiko());
		}
		
		return simpleShinseiDataInfo;
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}