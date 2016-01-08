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
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ����ҊǗ����j���[��ʃA�N�V�����N���X�B
 * ����ҊǗ����j���[��ʂɌ����Җ���X�V�����Z�b�g����B
 * 
 * ID RCSfile="$RCSfile: MenuAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:33 $"
 */
public class MenuAction extends BaseAction {

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

		//�����Җ���X�V��
		String updateDate = "";
		
		//�����Җ���X�V�����擾����
		ISystemServise servise = getSystemServise(
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		updateDate = servise.GetKenkyushaMeiboUpdateDate(container.getUserInfo());

		//Null�ȊO���A�t�H�[�}�b�g�ϊ�
		if (!StringUtil.isEscapeBlank(updateDate)){
			updateDate = updateDate.substring(0, 4) + "�N"
						+ updateDate.substring(4, 6) + "��"
						+ updateDate.substring(6) + "�����݂�";
		}

		//���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute("meiboUpdateDate", updateDate);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
