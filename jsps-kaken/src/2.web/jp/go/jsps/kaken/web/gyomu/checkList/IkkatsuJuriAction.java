/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriAction.java
 *    Description : �ꊇ�󗝊m�F�A�N�V�����B
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
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.gyomu.checkList.CheckListSearchForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ�󗝊m�F�A�N�V����
 * 
 * @author masuo_t
 */
public class IkkatsuJuriAction extends BaseAction {

	/** ��ID��04(�w�U��t��)�̂��̂�\�� */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU		//�w�U��t��
	};

	public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

		//���������̎擾
		CheckListInfo checkInfo = container.getCheckListInfo();

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();

		//���������̐ݒ�
		if(checkInfo.getJigyoCd() != null && !checkInfo.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(checkInfo.getJigyoCd());
		}
		if(checkInfo.getShozokuCd() != null && !checkInfo.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(checkInfo.getShozokuCd());
		}
		//2005.11.18 iso �����@�֖��������Ă����̂Œǉ�
		if(!StringUtil.isBlank(checkInfo.getShozokuName())){
			searchInfo.setShozokuName(checkInfo.getShozokuName());
		}
		//20060310  add
		CheckListSearchForm checkForm = (CheckListSearchForm) request
			.getSession().getAttribute("checkListSearchForm");
	    String jokyoKubun = checkForm.getJuriJokyo();
		//�󗝏󋵂��󗝍ςݖ��͊m������ł���ꍇ�A�󗝉\�̃f�[�^���Ȃ��ׁA�G���[���b�Z�[�W��\��
		if(jokyoKubun.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU) ||
			jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			throw new NoDataFoundException("�Y���f�[�^�͂���܂���B");
		}
		//20060310  end
	
		//��ID��04(�w�U��t��)�̂��̂�\�� 
		searchInfo.setSearchJokyoId(JIGYO_IDS);
			
		//20060216   dhy  start
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
		//20060216   end

// 20050629 NoDataFoundException��throw����悤�ύX
		Page result = null;
		try{
			//�������s
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					searchInfo,
					false);
		}catch(NoDataFoundException e){
			throw new NoDataFoundException("�Y���f�[�^�͂���܂���B");
		}
// Horikoshi
			
		//�������ʂ��Z�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}