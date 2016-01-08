/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchAction.java
 *    Description : 提出書類検索表示アクションクラス
 *
 *    Author      : DIS.lwj
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.lwj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 提出書類検索表示アクションクラス。
 * 提出書類検索画面を表示する。
 */
public class SearchAction extends BaseAction {

    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrorsの宣言（定型処理）-----
        //ActionErrors errors = new ActionErrors();
        
        // 検索条件があればクリアする。
        removeFormBean(mapping, request);

        // 検索条件をフォームをセットする。
        TeishutsuShoruiSearchForm searchForm = new TeishutsuShoruiSearchForm();

        // ------プルダウンデータセット
        List result = LabelValueManager.getJigyoNameListByJigyoCds2(container.getUserInfo(),
                IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);               
        searchForm.setJigyoList(result);
        //受理状況のリストをセットする。
        searchForm.setJuriList(LabelValueManager.getJuriJokyoList2());
        
        HttpSession session=request.getSession(true);
        session.setAttribute(IConstants.RESULT_INFO, result);

        updateFormBean(mapping, request, searchForm);
        
        return forwardSuccess(mapping);
    }
}