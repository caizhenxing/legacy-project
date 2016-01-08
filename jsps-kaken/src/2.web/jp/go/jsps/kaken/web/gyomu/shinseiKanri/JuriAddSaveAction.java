/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriAddSaveAction.java
 *    Description : �󗝓o�^���l�I�u�W�F�N�g��o�^����
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�o�^�����N���A����B
 * 
 * ID RCSfile="$RCSfile: JuriAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddSaveAction extends BaseAction {

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

		//------�V�K�o�^�t�H�[�����̎擾
		JuriAddForm addForm = (JuriAddForm) form;

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk addPk = new ShinseiDataPk(addForm.getSystemNo());
        String jigyoCd = addForm.getJigyoCd();
		String JuriBiko = addForm.getJuriBiko();
		String juriKekka = addForm.getJuriKekka();
		String seiriNo = addForm.getJuriSeiriNo();
		//-------��
		
		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		if("0".equals(juriKekka)){
			//�󗝂̏ꍇ
			try{
				servise.registShinseiJuri(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);
			}catch(NoDataFoundException ex){
				ActionError error = new ActionError("errors.5051", ex.getCause().getMessage().toString());
				errors.add("�󗝎��s���ɃG���[���������܂����B",error);
			}
		}else if("1".equals(juriKekka)){
			//�s�󗝂̏ꍇ
			servise.registShinseiFujuri(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);			
		}else if("2".equals(juriKekka)){
			//�C���˗��̏ꍇ
			servise.registShinseiShuseiIrai(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);						
		}
		
		if(log.isDebugEnabled()){
			log.debug("�󗝌��ʁ@�o�^��� '"+ addForm);
		}

		//-----��ʑJ�ځi��^�����j-----
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

//		resetToken(request);							//------�g�[�N���̍폜	
		removeFormBean(mapping,request);				//------�t�H�[�����̍폜
		container.setSimpleShinseiDataInfo(null);		//------�Z�b�V�������̍폜
		return forwardSuccess(mapping);
	}
}