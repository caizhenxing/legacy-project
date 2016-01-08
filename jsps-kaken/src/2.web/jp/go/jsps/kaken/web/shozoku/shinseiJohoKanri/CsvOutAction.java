/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CsvOutAction
 *    Description : CSV出力(応募情報一覧)アクションクラス
 *
 *    Author      : dis.liuyi
 *    Date        : 2006/07/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/07/03    v1.0        dis.liuyi      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力(応募情報一覧)アクションクラス
 */
public class CsvOutAction extends BaseAction {
    
    /**
     * CSVファイル名の接頭辞。
     */
    public static final String filename = "OUBODATA";  //TODO

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
        
        //検索条件の取得
        ShinseiSearchInfo searchInfo = container.getShinseiSearchInfo();
        
//2007/03/23  張志男　削除ここから
        //searchInfo.clrOrder();
//2007/03/23　張志男　削除ここまで
       
        //検索実行
        List result =
            getSystemServise(
                IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchShozokuCsvData(
                container.getUserInfo(),
                searchInfo);
        
        DownloadFileUtil.downloadCSV(request, response, result, filename);

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}