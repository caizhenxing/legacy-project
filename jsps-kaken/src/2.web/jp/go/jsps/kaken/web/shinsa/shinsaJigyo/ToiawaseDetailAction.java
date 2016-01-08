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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �₢���킹��ڍו\���O�A�N�V�����N���X�B
 * �₢���킹��ڍ׉�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ToiawaseDetailAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ToiawaseDetailAction extends BaseAction {

	/** �₢���킹��X�֔ԍ� */
	private static String GAKUSHIN_TOIAWASE_YUBIN = ApplicationSettings.getString(ISettingKeys.GAKUSHIN_TOIAWASE_YUBIN);

	/** �₢���킹��Z�� */
	private static String GAKUSHIN_TOIAWASE_JUSHO = ApplicationSettings.getString(ISettingKeys.GAKUSHIN_TOIAWASE_JUSHO);

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

		//------�\���Ώێ��ƊǗ����̎擾
		JigyoKanriPk pkInfo = new JigyoKanriPk();
		//------�L�[���
		String jigyoId = ((ShinsaKekkaForm)form).getJigyoId();
		pkInfo.setJigyoId(jigyoId);
		
		//------�L�[�������ɕ\���f�[�^�擾	
		JigyoKanriInfo selectInfo = 
					getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
		
		//------�₢���킹����Z�b�g		
		selectInfo.setToiawaseZip(GAKUSHIN_TOIAWASE_YUBIN);
		selectInfo.setToiawaseJusho(GAKUSHIN_TOIAWASE_JUSHO);		
	
		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,selectInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
