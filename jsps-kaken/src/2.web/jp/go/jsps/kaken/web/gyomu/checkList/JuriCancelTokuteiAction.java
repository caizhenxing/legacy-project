/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriCancelTokuteiAction.java
 *    Description : 受理解除確認（特定）を行う。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
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
 * 受理解除確認（特定）を行う。
 * 
 * @author masuo_t
 */
public class JuriCancelTokuteiAction extends BaseAction {

	/** 状況IDが06(学振受理)のものを表示 */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_JYURI	//学振受理
	};

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
			//検索条件の取得
			CheckListForm searchForm = (CheckListForm) form;

			//-------▼ VOにデータをセットする。
			CheckListSearchInfo searchInfo = new CheckListSearchInfo();

			//検索条件の設定
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
			
			//状況IDが06(学振受理)のものを表示 
			searchInfo.setSearchJokyoId(JIGYO_IDS);
// 20050613 Start
			searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

			//検索実行
			Page result =getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE)
					.selectCheckList(container.getUserInfo(),
					searchInfo
// 20050627
					,
					true
// Horikoshi
					);

			//検索結果をセットする。
			request.setAttribute(IConstants.RESULT_INFO, result);

			//トークンをセッションに保存する。
			saveToken(request);
			return forwardSuccess(mapping);
	}
}