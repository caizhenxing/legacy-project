/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KariBangoKyakkaSaveAction.java
 *    Description : 仮領域番号発行却下確認実行アクション
 *
 *    Author      : DIS.lY
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lY         新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 仮領域番号発行却下確認実行アクション
 * ID RCSfile="$RCSfile: KariBangoKyakkaSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class KariBangoKyakkaSaveAction extends BaseAction {

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

        //------却下対象仮領域情報システム番号の取得
        TeisyutuForm teisyutuForm = (TeisyutuForm)form;

        //------却下対象システム番号の取得
        RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
        //------キー情報  
        ryoikiPk.setRyoikiSystemNo(teisyutuForm.getRyoikiSystemNo());
        
        //------キー情報を元に申請情報を更新    kakunin
        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).rejectKariBangoHakko(
                    container.getUserInfo(), ryoikiPk);
        }catch (NoDataFoundException e) {
            errors.add("データ検索中にDBエラーが発生しました。",new ActionError("errors.4004"));
        }
        catch (ApplicationException ex) {
            
            errors.add(ex.getMessage(),new ActionError(ex.getErrorCode(),ex.getMessage()));
            
        }
        //      -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}