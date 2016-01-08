/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �f�[�^�ۊǉ�ʃA�N�V�����N���X�B
 * �f�[�^�ۊǑΏێ��Ƃ��w�肷���ʂ�\������B
 */
public class DataHokanAction extends BaseAction {

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
		
		//���ƃ��X�g�̎擾�i�S�����鎖�Ƌ敪�̂݁j
		UserInfo userInfo = container.getUserInfo();
		
		List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
		
		//�t�H�[������
		DataHokanForm dataHokanForm = new DataHokanForm();
		dataHokanForm.setJigyoList(jigyoList);
		
		//-----�t�H�[�������N�G�X�g�ɃZ�b�g
		updateFormBean(mapping, request, dataHokanForm);	
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
