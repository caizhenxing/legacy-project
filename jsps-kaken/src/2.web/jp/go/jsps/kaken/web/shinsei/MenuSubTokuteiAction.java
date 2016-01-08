/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KariryoikiMenuAction.java
 *    Description : 仮領域番号発行・領域計画書概要作成・領域内研究計画調書確定（特定領域研究・新規領域）アクションクラス
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 仮領域番号発行・領域計画書概要作成・領域内研究計画調書確定（特定領域研究・新規領域）アクションクラス
 * @author DIS.dyh
 */
public class MenuSubTokuteiAction extends BaseAction {

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

        // サーバサービスの呼び出し（研究計画調書一覧データ取得）
        ISystemServise servise = getSystemServise(
                        IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        boolean isExist = servise.isExistRyoikiGaiyoInfo(container.getUserInfo());
        container.getUserInfo().getShinseishaInfo().setRyoikiGaiyoFlg(isExist);

        // 検索結果をリクエスト属性にセット
        return forwardSuccess(mapping);
    }
}