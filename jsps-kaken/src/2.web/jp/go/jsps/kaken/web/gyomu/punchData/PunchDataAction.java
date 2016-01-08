/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : PunchDataAction.java
 *    Description : �p���`�f�[�^�쐬��ʃA�N�V����
 *					DB���쐬��ނ̎擾�A���X�g��ʂ��\������
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/02/16    V1.0                       �V�K�쐬
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.web.gyomu.punchData;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p���`�f�[�^�쐬�O�A�N�V�����N���X�B
 * �p���`�f�[�^�쐬��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: PunchDataAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class PunchDataAction extends BaseAction {


	
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

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//�錾
		PunchDataForm punchdataform = new PunchDataForm();

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		//SearchInfo searchInfo = new SearchInfo();

		List result = null;
		try{
			//�}�X�^�Ǘ������擾
			result = getSystemServise(
					IServiceName.PUNCHDATA_MAINTENANCE_SERVICE).selectList(container.getUserInfo());
	 		punchdataform.setPunchKanriList(result);
		}catch(ApplicationException e){
			//0���̃��X�g�I�u�W�F�N�g�𐶐�
			result = Collections.EMPTY_LIST;
		}
		
		//�Z�b�V�����Ƀt�H�[�����Z�b�g
		updateFormBean(mapping, request, punchdataform);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}