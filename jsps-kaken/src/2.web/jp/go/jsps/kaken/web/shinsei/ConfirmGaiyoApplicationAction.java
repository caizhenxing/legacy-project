/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ConfirmApplicationAction.java
 *    Description : 領域計画書概要確認処理を行う。確認完了画面表示
 *
 *    Author      : DIS.gongXB
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        DIS.gongXB     新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
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
 * 領域計画書概要確認処理を行う。確認完了画面表示
 * ID RCSfile="$RCSfile: ConfirmGaiyoApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class ConfirmGaiyoApplicationAction extends BaseAction {

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

        //-----簡易申請書入力フォームの取得
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        
        RyoikiKeikakushoPk ryoikikeikakushoPk=new RyoikiKeikakushoPk();
        ryoikikeikakushoPk.setRyoikiSystemNo(ryoikiGaiyoForm.getRyoikiSystemNo());
        
        //サーバサービスの呼び出し（申請書確認完了）
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        try{
            servise.confirmGaiyoComplete(container.getUserInfo(),ryoikikeikakushoPk);
        }catch(NoDataFoundException e){
            errors.add("該当する情報が存在しませんでした。",new ActionError("errors.5002"));
        }
        
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