/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 張志男
 *    Date        : 2006/10/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 利害関係入力完了を表示する。 
 * 
 * ID RCSfile="$RCSfile: RiekiSohanCheckSaveAction.java,v $"
 * Revision="$Revision: 1.1 $" 
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RiekiSohanCheckSaveAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        // -----取得したトークンが無効であるとき
        if (!isTokenValid(request)) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "error.transaction.token"));
            saveErrors(request, errors);
            return forwardTokenError(mapping);
        }

        // ------キー情報
        String jigyoId = ((ShinsaKekkaSearchForm) form).getJigyoId(); // 事業ID

        //------キー情報を元に更新データ取得
        getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE)
                .updateRiekiSohanComplete(container.getUserInfo(), jigyoId);

        // ------トークンの削除
        resetToken(request);

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}