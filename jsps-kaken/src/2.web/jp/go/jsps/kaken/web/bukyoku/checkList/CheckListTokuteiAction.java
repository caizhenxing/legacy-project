/*
 * Created on 2005/06/03
 *
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト（特定領域）アクションクラス。
 * チェックリストの情報を取得する。
 */
public class CheckListTokuteiAction extends BaseAction {

	//2005.12.19 iso チェックリストの件数修正
	/** 所属機関受付中(状況ID:03)以降の状況ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
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

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

		//2005.12.19 iso チェックリストの件数修正
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//2005/05/19 追加 ここから--------------------------------------------------
		//理由　チェックリスト画面のタイトル情報取得のため
		
		//タイトル表示情報の取得
		Page titleResult = 
					 getSystemServise(
						  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTitle(checkInfo);
		//タイトル表示情報をフォームにセットする
		request.setAttribute(IConstants.TITLE_INFO, titleResult);
		
		//追加 ここまで-------------------------------------------------------------		
		
		Page result = null;
		
		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		try{
			//出力
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
					container.getUserInfo(),
					checkInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------

		//表示条件をフォームにセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}

}
