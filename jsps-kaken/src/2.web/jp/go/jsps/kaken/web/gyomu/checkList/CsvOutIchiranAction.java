/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CsvOutIchiranAction.java
 *    Description : CSV�o�́i�ꗗ�j�A�N�V�����N���X�B
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
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV�o�̓A�N�V�����N���X�B
 */
public class CsvOutIchiranAction extends BaseAction {
	
	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	public static final String filename = "LIST_CHECKLIST";

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

		CheckListSearchInfo searchInfo = container.getCheckListSearchInfo();

// 20050622
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
//		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
// Horikoshi

		//�������s
		List result =
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).searchCsvDataIchiran(
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