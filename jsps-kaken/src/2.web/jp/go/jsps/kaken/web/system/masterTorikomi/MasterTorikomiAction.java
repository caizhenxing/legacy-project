/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\���ҏ�񌟍��O�A�N�V�����N���X�B
 * �\���ҏ�񌟍���ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: MasterTorikomiAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class MasterTorikomiAction extends BaseAction {

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
		MasterTorikomiForm searchForm = new MasterTorikomiForm();

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		SearchInfo searchInfo = new SearchInfo();

		//�}�X�^�Ǘ������擾
		List result =
					getSystemServise(
					IServiceName.SYSTEM_MAINTENANCE_SERVICE).selectList(container.getUserInfo());
		searchForm.setMasterKanriList(result);

		//�R���{�f�[�^���擾
		//�}�X�^��ʃR���{�f�[�^
		searchForm.setShubetuComboList(LabelValueManager.getLavelValueList(result, "MASTER_NAME", "MASTER_SHUBETU"));
		//�V�K�E�X�V�t���O�f�[�^
		searchForm.setShinkiKoshinList(LabelValueManager.getShinkiKoshinFlgList());

		//�����������t�H�[�����Z�b�g����B
		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
