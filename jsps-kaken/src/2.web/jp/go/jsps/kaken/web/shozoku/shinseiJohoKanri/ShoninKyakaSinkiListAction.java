/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShoninKyakaSinkiListAction.java
 *    Description : 確認・却下対象応募情報一覧画面を表示する
 *
 *    Author      : DIS LY
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    V1.0        LY              新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 確認・却下対象応募情報一覧アクションクラス。
 * 確認・却下対象応募情報一覧画面を表示する。
 */
public class ShoninKyakaSinkiListAction extends BaseAction {

    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        ShinseiSearchForm searchForm = (ShinseiSearchForm) form;

        //------キャンセル処理-----------------
        String forwardStr = (String) request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            searchForm.setStartPosition(0);
            return mapping.findForward(forwardStr);
        }
        //所属機関情報
        UserInfo userInfo = container.getUserInfo();
        ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
        if (shozokuInfo == null) {
            throw new ApplicationException("所属機関情報を取得できませんでした。", new ErrorInfo(
                    "errors.application"));
        }
        //検索条件をセットする
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());//所属機関コード
        searchInfo.setJokyoId(new String[] {
                StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU });//申請状況ID("03")
        // 現在保留
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);//システム受付番号の昇順("SYSTEM_NO")        

        // 特定領域研究（新規領域）:00022
        searchInfo.setJigyoCd(searchForm.getJigyoCd());

        //ページ制御
        searchInfo.setStartPosition(searchForm.getStartPosition());
        searchInfo.setPageSize(searchForm.getPageSize());
        searchInfo.setMaxSize(searchForm.getMaxSize());
        //サーバサービスの呼び出し（処理状況一覧ページ情報取得）
        ISystemServise servise = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        Page page = null;
        try {
            page = servise.searchConfirmInfo(userInfo, searchInfo);
        }
        catch (NoDataFoundException e) {
            //0件のページオブジェクトを生成
            page = Page.EMPTY_PAGE;
        }
        //検索結果をリクエスト属性にセット
        request.setAttribute(IConstants.RESULT_INFO, page);

        saveToken(request);
        //-----画面遷移（定型処理）-----
        
        return forwardSuccess(mapping);
    }
}