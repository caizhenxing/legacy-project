/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 *
 */
public class TobiListAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start 検索条件を追加
//update start dyh 2006/2/8
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());//事業区分
//update end dyh 2006/2/8
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)form;	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//2005/04/13 追加 ここから------------------------------------------------------
		//理由　飛び番号リスト画面のタイトル情報取得のため
		
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
		
		//追加 ここまで-----------------------------------------------------------------


		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{	
		//飛び番号の取得
			result = getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTobiList(
				container.getUserInfo(),
				checkInfo);	
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------	
		
		//結果をフォームにセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}

}
