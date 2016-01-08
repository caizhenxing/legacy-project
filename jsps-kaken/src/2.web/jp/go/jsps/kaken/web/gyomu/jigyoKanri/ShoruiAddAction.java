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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IJigyoKanriMaintenance;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ޓo�^�O�A�N�V�����N���X�B
 * �t�H�[���ɏ��ޓo�^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * ���ޓo�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShoruiAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiAddAction extends BaseAction {

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
			
			//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shoruiKanriInfo.�v���p�e�B��
			//## �o�^�Ώۃv���p�e�B 				$!shoruiKanriForm.�v���p�e�B��
			//##

			//-----ActionErrors�̐錾�i��^�����j-----
			ActionErrors errors = new ActionErrors();

			//------�V�K�o�^�t�H�[�����̍쐬
			ShoruiKanriForm addForm = new ShoruiKanriForm();
			
			//------�X�V���[�h
			addForm.setAction(BaseForm.ADD_ACTION);

			//------���ޓo�^�Ώێ��ƊǗ����̍쐬
			ShoruiKanriInfo addInfo = new ShoruiKanriInfo();
			//------�L�[���
			String jigyoId = ((ShoruiKanriForm)form).getJigyoId();
			addInfo.setJigyoId(jigyoId);
		
			//------�L�[�������Ɏ��ƃf�[�^�擾	
			//DB�o�^
			ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			Map map = servise.select(container.getUserInfo(),addInfo);
			
			addInfo = (ShoruiKanriInfo)map.get(IJigyoKanriMaintenance.KEY_JIGYOKANRI_INFO);
			List  result = (List)map.get(IJigyoKanriMaintenance.KEY_SHORUIKANRI_LIST);
						
			//------���W�I�{�^���f�[�^�Z�b�g
			//�Ώ�
			addForm.setTaishoIdList(LabelValueManager.getTaishoIdList());			
			
			//------�o�^�Ώۏ����Z�b�V�����ɓo�^�B
			container.setShoruiKanriInfo(addInfo);			

			//------���ފǗ���񃊃X�g���Z�b�V�����ɓo�^�B
			container.setShoruiKanriList(result);
		
			//------�V�K�o�^�t�H�[���ɃZ�b�g����B
			updateFormBean(mapping, request, addForm);
			
			//�g�[�N�����Z�b�V�����ɕۑ�����B
			saveToken(request);

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
		}
}
