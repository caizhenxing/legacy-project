/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriCancelAction.java
 *    Description : 受理解除画面を表示するアクションクラス
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理解除アクションクラス。 
 * 受理解除画面を表示する。 
 * ID RCSfile=JuriCancelAction.java
 * Revision="$Revision: 1.7 
 * Date: 2006/06/16 06:34:42 $"
 */
public class JuriCancelAction extends BaseAction {

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

        // 検索条件の取得
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form;

        // -------▼ VOにデータをセットする。
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk(juriGaiyoForm.getSystemNo());
        UserInfo userInfo = container.getUserInfo();

        // 検索実行
        RyoikiKeikakushoInfo selectInfo = new RyoikiKeikakushoInfo();
        try {
            selectInfo = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectRyoikikeikakushoInfo(userInfo, pkInfo);

        }catch (NoDataFoundException ex) {
            errors.add("該当データはありません", new ActionError("errors.5002"));
        }catch (DataAccessException ex) {
            ActionError error = new ActionError("errors.4002");
            errors.add("受理解除で領域計画書概要テーブルの検索中に例外が発生しました。", error);
        }

        // -----セッションに申請データ情報（受理解除情報）をセットする。
        container.setRyoikikeikakushoInfo(selectInfo);

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}