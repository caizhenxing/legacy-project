/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : DownloadGaiyoCoverPdfAction.java
 *    Description : 領域計画書表紙ダウンロードアクション
 *
 *    Author      : DIS.liuyi
 *    Date        : 2006/06/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/26    V1.0        DIS.liuyi      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域計画書表紙ダウンロードアクション
 * ID RCSfile="$RCSfile: DownloadGaiyoCoverPdfAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class DownloadGaiyoCoverPdfAction extends BaseAction {

    /**
     * Actionクラスの主要な機能を実装する。
     * 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;
        //contentTypeをPDFに指定 
        //String contentType = DownloadFileUtil.CONTENT_TYPE_PDF;

        //サーバサービスの呼び出し（表紙PDFファイル取得）
        FileResource fileRes = null;
        try {
            fileRes = getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).getGaiyoCoverPdfFile(
                    container.getUserInfo(), ryoikiGaiyoForm.getRyoikiSystemNo());
        }
        catch (ValidationException e) {
            //サーバーエラーを保存。
            saveServerErrors(request, errors, e);
        }

        //-----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //-----ファイルのダウンロード
        DownloadFileUtil.downloadFile(response, fileRes);
        return forwardSuccess(mapping);
    }
}