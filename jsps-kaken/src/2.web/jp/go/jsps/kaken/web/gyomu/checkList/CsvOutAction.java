/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CsvOutAction.java
 *    Description : CSV�o�̓A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV�o�̓A�N�V�����N���X�B
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	public static final String filename = "CHECKLIST";

	//�\���󋵂h�c���w��
	private static String[] JIGYO_IDS = new String[]{
		StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//�����@�֎�t��
		StatusCode.STATUS_GAKUSIN_SHORITYU,				//�w�U��t��
		StatusCode.STATUS_GAKUSIN_JYURI,				//�w�U��
		StatusCode.STATUS_GAKUSIN_FUJYURI,				//�w�U�s��
		StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,	//�R��������U�菈����
		StatusCode.STATUS_WARIFURI_CHECK_KANRYO,		//����U��`�F�b�N����
		StatusCode.STATUS_1st_SHINSATYU,				//�ꎟ�R����
		StatusCode.STATUS_1st_SHINSA_KANRYO,			//�ꎟ�R���F����
		StatusCode.STATUS_2nd_SHINSA_KANRYO				//�񎟐R������
    };
	
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

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		String shozokuCd = searchInfo.getShozokuCd();
		if(shozokuCd != null){
			searchInfo.setShozokuCd(shozokuCd);
		}
		
// 20050622
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
	//	searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);

		//20061114 �`�F�b�N���X�g�̌����C��
		searchInfo.setSearchJokyoId(JIGYO_IDS);
		
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(searchInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//�������s
		List result =
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).searchCsvData(
				container.getUserInfo(),
				searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}