/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 劉長宇
 *    Date        : 2007/02/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請データ削除アクションクラス。
 * 指定申請データを削除する。（削除フラグを立てる。）
 * ID RCSfile="$RCSfile: DeleteSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class DeleteSaveAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
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

        //-----簡易申請データの取得
        SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
        
        //サーバサービスの呼び出し（申請データ削除）
        ShinseiDataPk shinseiPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        servise.deleteApplication(container.getUserInfo(),shinseiPk);
        
        //------フォーム情報の削除
        removeFormBean(mapping,request);

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        return forwardSuccess(mapping);
        
    }
    
    
    
}
