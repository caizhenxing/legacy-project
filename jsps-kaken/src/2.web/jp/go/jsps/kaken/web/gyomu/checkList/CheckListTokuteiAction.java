/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckListTokuteiAction.java
 *    Description : チェックリスト(特定)アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/06/06
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/06/06    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

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
 * チェックリスト(特定)アクションクラス。
 * チェックリストの情報を取得する。
 */
public class CheckListTokuteiAction extends BaseAction {

	//2006.11.14 iso 特定のリストのみ状況IDが抜けていたので追加
	//検索申請書状態を指定
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

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		String shozokuCd = checkForm.getShozokuCd();
		if(shozokuCd != null){
			checkInfo.setShozokuCd(shozokuCd);
		}
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

		//2006.11.14 iso 特定のリストのみ状況IDが抜けていたので追加
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		
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