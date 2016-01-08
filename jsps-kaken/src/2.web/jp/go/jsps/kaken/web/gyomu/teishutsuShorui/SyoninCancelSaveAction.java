/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SyoninCancelSaveAction.java
 *    Description : 承認解除(提出書類)を実行アクション
 *    Author      : mcj
 *    Date        : 2006/06/13
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.mcj        新規作成   
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 承認解除(提出書類)を実行アクション
 * ID RCSfile="$RCSfile: SyoninCancelSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class SyoninCancelSaveAction extends BaseAction {

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

        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // 検索条件を設定
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form; // フォーム取得
        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setJokyoIds(container.getTeishutsuShoruiSearchInfo().getSearchJokyoId());
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk(juriGaiyoForm.getSystemNo());

        try {
            ISystemServise servise = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
            servise.cancelTeisyutusyoSyonin(container.getUserInfo(), pkInfo,ryoikiInfo);
        }
        catch (NoDataFoundException ne) {
            ActionError error = new ActionError("errors.4002");
            errors.add("承認解除で領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。", error);
        }
        catch (DataAccessException de) {
            ActionError error = new ActionError("errors.4000");
            errors.add("承認解除で領域計画書概要テーブルの検索中に例外が発生しました。", error);
        }
        
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}