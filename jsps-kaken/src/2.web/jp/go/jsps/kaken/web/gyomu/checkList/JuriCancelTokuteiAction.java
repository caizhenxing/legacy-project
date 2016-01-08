/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriCancelTokuteiAction.java
 *    Description : �󗝉����m�F�i����j���s���B
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IJigyoKubun;


/**
 * �󗝉����m�F�i����j���s���B
 * 
 * @author masuo_t
 */
public class JuriCancelTokuteiAction extends BaseAction {

	/** ��ID��06(�w�U��)�̂��̂�\�� */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_JYURI	//�w�U��
	};

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
			//���������̎擾
			CheckListForm searchForm = (CheckListForm) form;

			//-------�� VO�Ƀf�[�^���Z�b�g����B
			CheckListSearchInfo searchInfo = new CheckListSearchInfo();

			//���������̐ݒ�
			if (searchForm.getJigyoId() != null
				&& !searchForm.getJigyoId().equals("")) {
				searchInfo.setJigyoCd(searchForm.getJigyoId().substring(2, 7));
// 20050627
				searchInfo.setJigyoId(searchForm.getJigyoId());
// Horikoshi
			}
			if (searchForm.getShozokuCd() != null
				&& !searchForm.getShozokuCd().equals("")) {
				searchInfo.setShozokuCd(searchForm.getShozokuCd());
			}
			if(searchForm.getKaisu() != null
				&& !searchForm.getKaisu().equals("")){
				searchInfo.setKaisu(searchForm.getKaisu());
			}
			
			//��ID��06(�w�U��)�̂��̂�\�� 
			searchInfo.setSearchJokyoId(JIGYO_IDS);
// 20050613 Start
			searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

			//�������s
			Page result =getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE)
					.selectCheckList(container.getUserInfo(),
					searchInfo
// 20050627
					,
					true
// Horikoshi
					);

			//�������ʂ��Z�b�g����B
			request.setAttribute(IConstants.RESULT_INFO, result);

			//�g�[�N�����Z�b�V�����ɕۑ�����B
			saveToken(request);
			return forwardSuccess(mapping);
	}
}