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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ƊǗ���񌟍��A�N�V�����N���X�B
 * ��t�����厖�ƈꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class SearchListAction extends BaseAction {

	/** �����Ώێ���ID */
	
//	2005/04/22 �ύX ��������----------
//	���R:���E�t���O����������Ă��܂����̏C��

	//private final static String[] JIGYO_KUBUN=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO,IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	
	//�w�n�͌���̂�
	private final static String[] JIGYO_KUBUN_KOBO=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	//�w���͔����ƌ���
	private final static String[] JIGYO_KUBUN_HIKOBO=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO,IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	
//	2005/04/22 �ǉ� �����܂�----------
	
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

		//���������̎擾
		BaseSearchForm searchForm = (BaseSearchForm)form;
		//���X�g�̂ݕ\�������邽�߁A�y�[�W������1���ɂ���B
		searchForm.setPageSize(0);

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		//2005/03/26 �C�� ------------------------------------------------��������
		//���R ��Վ��ƂƂ��̑��̈ꗗ��ʁX�̉�ʂƂ��ĕ\�����邽��
		//SearchInfo searchInfo = new SearchInfo();

		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();
		
		//2005/03/24 �C�� ------------------------------------------------�����܂�

		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//�������s
		Page result = null;
		try{
			//2005/03/24 �C�� ------------------------------------------------��������
			//���R ��Վ��ƂƂ��̑��̈ꗗ��ʁX�̉�ʂƂ��ĕ\�����邽��
			//result =
			//		getSystemServise(
			//		IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).searchUketukeJigyo(
			//						container.getUserInfo(),
			//		searchInfo);
			
			UserInfo userInfo = container.getUserInfo();
			
			//�\���҂̏ꍇ�́A����匠�������邩�ǂ����`�F�b�N����B
			if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
				//�\���҂̔���匠��������ꍇ �� ���������������ɂ���
				ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
				if("1".equals(shinseishaInfo.getHikoboFlg())){
					searchInfo.setJigyoKubun(JIGYO_KUBUN_HIKOBO);
				}else{
					//����匠�����Ȃ��ꍇ�������͌��������ɂ��Ȃ�
					searchInfo.setJigyoKubun(JIGYO_KUBUN_KOBO);
				}
			}
			
			result =
					getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).searchUketukeJigyo(
									container.getUserInfo(),searchInfo);
			//2005/03/24 �C�� ------------------------------------------------�����܂�
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
