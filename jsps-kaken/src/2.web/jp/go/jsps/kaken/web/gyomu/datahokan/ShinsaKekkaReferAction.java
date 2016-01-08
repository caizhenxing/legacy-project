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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IDataHokanMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʎQ�Ɖ�ʐ����A�N�V�����N���X�B
 * ID RCSfile="$RCSfile: ShinsaKekkaReferAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class ShinsaKekkaReferAction extends BaseAction {

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
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//-----�ȈՐ\�������̓t�H�[���̎擾
		SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk searchPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());		
					
		//�������s
		Map result =
			getSystemServise(
				IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).getShinsaKekkaBoth(
				container.getUserInfo(),
				searchPk);
		
		//-----Map�̒l���擾����B
		ShinsaKekkaReferenceInfo shinsaKekka1stInfo = 
				(ShinsaKekkaReferenceInfo)result.get(IDataHokanMaintenance.KEY_SHINSAKEKKA_1ST);	//1���R�����ʏ��
		ShinsaKekka2ndInfo shinsaKekka2ndInfo = 
				(ShinsaKekka2ndInfo)result.get(IDataHokanMaintenance.KEY_SHINSAKEKKA_2ND);			//2���R�����ʏ��
		
		//-----�Z�b�V������1���R�����ʏ��A2���R�����ʏ����Z�b�g����B
		container.setShinsaKekkaReferenceInfo(shinsaKekka1stInfo);
		container.setShinsaKekka2ndInfo(shinsaKekka2ndInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
