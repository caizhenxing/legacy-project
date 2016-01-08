/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchSinnkiListAction
 *    Description : 領域情報検索（新規領域）アクションクラス
 *
 *    Author      : 苗
 *    Date        : 2006/07/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　      2006/07/24    v1.0        苗                           新規作成  
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.code.ryoiki;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ICodeMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域情報検索（新規領域）アクションクラス。
 * 領域一覧画面（新規領域）を表示する。
 * 
 */
public class SearchSinnkiListAction extends BaseAction {

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

        Map result = null;
        //検索実行
        try{
            result =
                getSystemServise(
                    IServiceName.CODE_MAINTENANCE_SERVICE).getRyoikiSinnkiInfoList(
                                    container.getUserInfo());
        }catch(NoDataFoundException e){
            //0件のページオブジェクトを生成
            result = new HashMap();
        }
        
        //Mapから情報を取得
        List searchList = (List)result.get(ICodeMaintenance.KEY_SEARCH_LIST);

        //検索結果をリクエスト属性にセット
        request.setAttribute(IConstants.SEARCH_INFO,searchList);    //検索情報

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}