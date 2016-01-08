/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募書類の提出ダウンロードアクションクラス。
 * 
 */
public class OuboDownloadAction extends BaseAction {

	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "kibandata.csv";
	
	//状況IDが所属機関受付中以降のものについて表示するよう修正

	/** 所属機関受付中(状況ID:03)以降の状況ID */
	private static String[] JIGYO_IDS = new String[]{
			//StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
			//StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,			//所属機関却下
			StatusCode.STATUS_GAKUSIN_JYURI,			//学振受理
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//学振不受理
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//審査員割り振り処理後
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,			//割り振りチェック完了
			StatusCode.STATUS_1st_SHINSATYU,			//一次審査中
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//一次審査：判定
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//二次審査完了
	};

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

		// 検索条件に事業区分を追加
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn());

		//状況IDが所属機関受付中以降のものについて表示するよう修正
		checkInfo.setSearchJokyoId(JIGYO_IDS);

		//-----ビジネスロジック実行-----
		FileResource fileRes =
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).createOuboTeishutusho(
					container.getUserInfo(),
					checkInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//-----ファイルのダウンロード
		DownloadFileUtil.downloadFile(response, fileRes);	

		return forwardSuccess(mapping);
	}

}
