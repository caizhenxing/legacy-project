/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : DeleteGaiyoConfirmAction.java
 *    Description : 領域計画書削除確認画面を表示するアクションクラス
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/27    V1.0        DIS.mcj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域計画書削除確認画面を表示するアクションクラス
 */
public class DeleteGaiyoConfirmAction extends BaseAction {

    /**
     * Actionクラスの主要な機能を実装する。
     * 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response, 
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;
        String ryoikiSystemNo = "";
        ryoikiSystemNo = ryoikiGaiyoForm.getRyoikiSystemNo();

        // サーバサービスの呼び出し（研究計画調書一覧データ取得）
        ISystemServise servise = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = new RyoikiKeikakushoInfo();
        try {
            ryoikiKeikakushoInfo = servise.getRyoikiGaiyoDeleteInfo(container.getUserInfo(), ryoikiSystemNo);
        }
        catch (NoDataFoundException e) {
            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.5045","領域計画書削除確認"));
        }
        
        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // 検索結果をリクエスト属性にセット
        container.setRyoikikeikakushoInfo(ryoikiKeikakushoInfo);
        updateFormBean(mapping, request, ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }
}