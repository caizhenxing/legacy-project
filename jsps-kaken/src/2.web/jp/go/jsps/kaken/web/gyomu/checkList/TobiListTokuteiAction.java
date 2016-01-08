/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TobiListTokuteiAction.java
 *    Description : チェックリストの飛び番号リスト(特定)を取得するクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    v1.0        Admin          新規作成
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
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリストの飛び番号リストを取得するクラス。
 * チェックリスト管理サービスで飛び番号取得用のデータを取得する。
 * 
 * @author masuo_t
 */
public class TobiListTokuteiAction extends BaseAction {
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		CheckListForm checkForm = (CheckListForm)form;	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		String shozokuCd = checkForm.getShozokuCd();
		if(shozokuCd != null){
			checkInfo.setShozokuCd(shozokuCd);
		}
		
// 20050621
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi
		
		//2005/05/19 変更 ここから------------------------------------------------
		//理由 タイトル情報の取得方法の変更のため
		//タイトル表示情報の取得
		Page titleResult = 
		//			  getSystemServise(
		//				  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
		//				  container.getUserInfo(),
		//				  checkInfo);
					  getSystemServise(
						  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTitle(checkInfo);
		//タイトル表示情報をフォームにセットする
		request.setAttribute(IConstants.TITLE_INFO, titleResult);
		//変更 ここまで-----------------------------------------------------------
		
		Page result = null;
		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		try{			
		//データ取得
			result = getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTobiList(
				container.getUserInfo(),
				checkInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------	
			
		//検索結果をフォームにセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		return forwardSuccess(mapping);
	}
}