/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchAction.java
 *    Description : 受理登録対象応募情報検索表示アクションクラス
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/05/30
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/05/30    V1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理登録対象応募情報検索表示アクションクラス。
 * 受理登録対象応募情報検索画面を表示する。
 */
public class SearchAction extends BaseAction {

    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // 検索条件があればクリアする。
        removeFormBean(mapping, request);

        // 検索条件をフォームをセットする。
        JuriSearchForm searchForm = new JuriSearchForm();

        // 事業リストの取得（担当する事業区分のみ）
        UserInfo userInfo = container.getUserInfo();
        
//2006/06/06 by jzx update start
        //List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
        List jigyoList = LabelValueManager.getJigyoNameListByJigyoCds2(userInfo);
//2006/06/06 by jzx update end
        
        // ------プルダウンデータセット
        searchForm.setJigyoNmList(jigyoList); // 事業名リスト

        updateFormBean(mapping, request, searchForm);

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}