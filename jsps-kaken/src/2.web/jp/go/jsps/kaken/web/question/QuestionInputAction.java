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
package jp.go.jsps.kaken.web.question;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �A���P�[�g���͉�ʕ\���O�A�N�V�����N���X�B
 * �A���P�[�g���͉�ʂ�\������B
 * 
 */
public class QuestionInputAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
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

		//------�L�[���
		QuestionForm addForm = new QuestionForm();

		//------���W�I�{�^���A�v���_�E���f�[�^�Z�b�g
		//2005.10.28
		String[] labelNames = {ILabelKubun.BENRI1, ILabelKubun.RIKAI1, ILabelKubun.RIKAI2,
							   ILabelKubun.RIKAI3, ILabelKubun.YONDA1, ILabelKubun.BENRI2,
							   ILabelKubun.OS, ILabelKubun.WEB, ILabelKubun.KEISIKI,
							   ILabelKubun.RIYOUTIME, ILabelKubun.TOIAWASE1,
							   ILabelKubun.CALLRIYOU, ILabelKubun.CALLRIKAI,
							   ILabelKubun.OUBO_TOI, ILabelKubun.BUKYOKU_TOI
							   };
		HashMap labelMap = (HashMap)LabelValueManager.getLabelMap(labelNames);
		
		//R1
		addForm.setBenri1List((List)labelMap.get(ILabelKubun.BENRI1));
		addForm.setRikai1List((List)labelMap.get(ILabelKubun.RIKAI1));
		addForm.setRikai2List((List)labelMap.get(ILabelKubun.RIKAI2));
		addForm.setYonda1List((List)labelMap.get(ILabelKubun.YONDA1));
		addForm.setRikai3List((List)labelMap.get(ILabelKubun.RIKAI3));
		addForm.setBenri2List((List)labelMap.get(ILabelKubun.BENRI2));
		addForm.setCallriyouList((List)labelMap.get(ILabelKubun.CALLRIYOU));
		addForm.setCallrikaiList((List)labelMap.get(ILabelKubun.CALLRIKAI));
		
		addForm.setOsList((List)labelMap.get(ILabelKubun.OS));
		addForm.setWebList((List)labelMap.get(ILabelKubun.WEB));
		addForm.setKeisikiList((List)labelMap.get(ILabelKubun.KEISIKI));
		addForm.setRiyoutimeList((List)labelMap.get(ILabelKubun.RIYOUTIME));
		addForm.setToiawase1List((List)labelMap.get(ILabelKubun.TOIAWASE1));

		//����҂���̖₢���킹�̓��e���X�g���Z�b�g
		addForm.setOuboToiList((List)labelMap.get(ILabelKubun.OUBO_TOI));
		//���ǒS���҂���̖₢���킹�̓��e���X�g���Z�b�g
		addForm.setBukyokuToiList((List)labelMap.get(ILabelKubun.BUKYOKU_TOI));

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