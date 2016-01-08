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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 2���R�����ʓo�^�A�N�V�����N���X�B
 * 2���R�����ʓo�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka2ndAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class ShinsaKekka2ndAddAction extends BaseAction {

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

		//���������̎擾
		ShinsaKekka2ndForm addForm = (ShinsaKekka2ndForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk searchPk = new ShinseiDataPk(addForm.getSystemNo());		
					
		//�������s
		Map result =
			getSystemServise(
				IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).getShinsaKekkaBoth(
				container.getUserInfo(),
				searchPk);
		
		//-----Map�̒l���擾����B
		ShinsaKekkaReferenceInfo shinsaKekka1stInfo = 
				(ShinsaKekkaReferenceInfo)result.get(IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_1ST);	//1���R�����ʏ��
		ShinsaKekka2ndInfo shinsaKekka2ndInfo = 
				(ShinsaKekka2ndInfo)result.get(IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_2ND);		//2���R�����ʏ��
		
		//-----�Z�b�V������1���R�����ʏ��A2���R�����ʏ����Z�b�g����B
		container.setShinsaKekkaReferenceInfo(shinsaKekka1stInfo);
		container.setShinsaKekka2ndInfo(shinsaKekka2ndInfo);

		//------�v���_�E���f�[�^�Z�b�g
		//2���R�����ʏ��
		addForm.setKekka2List(LabelValueManager.getShinsaKekka2ndList());

		//-----�t�H�[����2���R�����ʏ����Z�b�g����B
		addForm.setKekka2(shinsaKekka2ndInfo.getKekka2());			//2���R������
		addForm.setSouKehi(shinsaKekka2ndInfo.getSouKehi());		//���o��
		addForm.setShonenKehi(shinsaKekka2ndInfo.getShonenKehi());	//���N�x�o��		
		addForm.setShinsa2Biko(shinsaKekka2ndInfo.getShinsa2Biko());//�Ɩ��S���ҋL����
		
		//------�g�[�N���̕ۑ�
		saveToken(request);
		
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, addForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
