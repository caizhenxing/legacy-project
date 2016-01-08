/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchUnregistAction.java
 *    Description : 未登録申請者情報検索前アクションクラス
 *
 *    Author      : 
 *    Date        : 2005/03/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/25    V1.0                       新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 未登録申請者情報検索アクションクラス。
 * 未登録申請者情報一覧画面を表示する。
 *
 * @author yoshikawa_h
 */
public class SearchUnregistListAction extends BaseAction {

    /* 
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

        //------キャンセル時        
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }

        //検索条件の取得
        ShinseishaSearchForm searchForm = (ShinseishaSearchForm)form;

        //-------▼ VOにデータをセットする。
        ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
        try {
            PropertyUtils.copyProperties(searchInfo, searchForm);
        } catch (Exception e) {
            log.error(e);
            throw new SystemException("プロパティの設定に失敗しました。", e);
        }

        searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

// 2005/03/31 削除 ここから--------------------------------------------
// 理由 不要な条件のため    
//        searchInfo.setShozokuName(container.getUserInfo().getShozokuInfo().getShozokuName());
// 削除 ここまで-------------------------------------------------------

        //検索実行
        Page result =
            getSystemServise(
                IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
                container.getUserInfo(),
                searchInfo);

        //登録結果をリクエスト属性にセット
        request.setAttribute(IConstants.RESULT_INFO,result);

        //トークンをセッションに保存する。
        saveToken(request);

        //2005/04/20 追加 ここから------------------------------------
        //検索条件保持のため
        container.setShinseishaSearchInfo(searchInfo);
        //追加 ここまで-----------------------------------------------

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }
}