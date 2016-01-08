/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : OuboKyakkaSaveAction.java
 *    Description : 応募情報却下処理
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    v1.0        苗苗                        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募情報却下アクションクラス。
 * 却下対象申請情報を更新する。 
 * 
 */
public class OuboKyakkaSaveAction extends BaseAction {
    
    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        //------却下対象申請情報システム番号の取得
        RyoikiGaiyoForm shoninForm = (RyoikiGaiyoForm)form;
        
        //------却下対象申請システム番号の取得
        ShinseiDataPk pkInfo = new ShinseiDataPk();
        //------キー情報
        String systemNo = shoninForm.getSystemNo();
        pkInfo.setSystemNo(systemNo);

        //------キー情報を元に申請情報を更新
        try{
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .rejectApplicationForTokuteiSinnki(container.getUserInfo(), pkInfo);
        }catch(NoDataFoundException e){
            errors.add(e.getMessage(),new ActionError(e.getErrorCode(), e.getErrorArgs()));
        }

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }

}