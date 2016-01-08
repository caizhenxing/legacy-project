/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : GaiyoUploadFileAction
 *    Description : 添付ファイルア
 *
 *    Author      : 苗
 *    Date        : 2006/06/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/23    v1.0        苗苗　　　　　　　　　　 新規作成　
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 添付ファイルアップロードアクションクラス。
 * 申請添付ファイルをセッションに保持する。
 */
public class GaiyoUploadFileAction extends BaseAction {

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
        
        //-----申請フォームの取得
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        
        //-----UploadFileActionの完了フラグをセットする
        ryoikiGaiyoForm.setUploadActionEnd(true);
        
        //-----申請フォームをセッションに保持。
        updateFormBean(mapping, request, ryoikiGaiyoForm);
        
        return forwardSuccess(mapping);
        
    } 
}