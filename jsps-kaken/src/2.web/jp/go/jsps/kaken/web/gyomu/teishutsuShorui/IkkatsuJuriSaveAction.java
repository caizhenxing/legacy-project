/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : 一括受理(提出書類一覧)を実行アクションクラス。
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.mcj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理(提出書類一覧)を実行アクションクラス。
 * ID RCSfile="$RCSfile: IkkatsuJuriSaveAction.java,v $"
 * Revision="$Revision: 1.1 $" Date="$Date: 2007/06/28 02:06:56 $"
 */
public class IkkatsuJuriSaveAction extends BaseAction {

    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // 検索条件の取得
        TeishutsuShoruiSearchInfo searchInfo = container.getTeishutsuShoruiSearchInfo();

        try {
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).
            executeIkkatuJuri(container.getUserInfo(), searchInfo);
        }
        catch (DataAccessException ex) {
            errors.add("一括受理で領域計画書概要テーブルの検索中に例外が発生しました。", new ActionError("errors.5002"));
        }
        catch (ApplicationException ex) {
            errors.add("一括受理でエラーが発生しました。", new ActionError("errors.4002"));
        }
        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}