/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriAction.java
 *    Description : 一括受理確認アクション
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/04/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/21    V1.0                       新規作成
 *    2006/06/06    V1.1        DIS.LiWanJun   修正（画面でラジオを追加するから）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理確認アクション
 * @author yoshikawa_h
 */
public class IkkatsuJuriAction extends BaseAction {

    public ActionForward doMainProcessing(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        UserContainer container)
        throws ApplicationException {

        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();
        
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        //------キャンセル処理-----------------
        String forwardStr = (String)request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            removeFormBean(mapping,request);
            return mapping.findForward(forwardStr);
        }
//2006/06/06 苗 李万軍　追加ここから
        //------システム番号の取得
        JuriListForm listForm = (JuriListForm)form;
        String[] sysNos =listForm.getSystemNos();

        //画面でラジオを選択しないの場合
        String radioButton = listForm.getSelectRadioBn();
        if(radioButton == null || radioButton.equals("false")){
            //エラーメッセージ
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.requiredSelect","受理する応募情報"));
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        //ページ制御
        searchInfo.setPageSize(0);
        searchInfo.setMaxSize(0);
        searchInfo.setStartPosition(0);
        
        //ソート順をセット
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);   //--事業ID順
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO); //--申請番号順
        
        Page result = null;
        try{
            result = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                .getShinseiJuriAll(container.getUserInfo(),searchInfo,sysNos);
        }catch(NoDataFoundException e){
            //0件のページオブジェクトを生成
            result = Page.EMPTY_PAGE;
            errors.add("",new ActionError("errors.5002"));
        }
        request.setAttribute(IConstants.RESULT_INFO,result);
//2006/06/06 苗 李万軍 追加ここまで

//2007/03/23 劉長宇　ここから
        updateFormBean(mapping, request.getSession(), listForm);
//2007/03/23 劉長宇　ここまで
        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //トークンをセッションに保存する。
        saveToken(request);
        return forwardSuccess(mapping);
    }
}