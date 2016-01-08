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
package jp.go.jsps.kaken.web.bukyoku.checkList;

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
 * チェックリスト（基盤）CSV出力アクションクラス。
 * 
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "CHECKLIST";

	//2005.12.19 iso チェックリストの件数修正
	/** 所属機関受付中(状況ID:03)以降の状況ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中
			StatusCode.STATUS_GAKUSIN_SHORITYU,				//学振受付中
			StatusCode.STATUS_GAKUSIN_JYURI,				//学振受理
			StatusCode.STATUS_GAKUSIN_FUJYURI,				//学振不受理
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,	//審査員割り振り処理後
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,		//割り振りチェック完了
			StatusCode.STATUS_1st_SHINSATYU,				//一次審査中
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//一次審査：判定
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//二次審査完了
	};
	
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		searchInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());

		//2005.12.19 iso チェックリストの件数修正
		searchInfo.setSearchJokyoId(JIGYO_IDS);
		
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(searchInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
// 20050616
		searchInfo.setJigyoKubun(checkForm.getJigyoKbn().trim());
	//	searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
// Horikoshi

		//検索実行
		List result =
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).searchCsvData(
				container.getUserInfo(),
				searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
