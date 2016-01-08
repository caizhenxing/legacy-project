/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ConvertGaiyoApplicationAction.java
 *    Description : 領域計画書（概要）PDF変換処理
 *
 *    Author      : DIS.zhangt
 *    Date        : 2006/06/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/29    V1.0        DIS.zhangt     新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域計画書概要ファイル変換アクションクラス。
 * 指定システム受付番号の申請書に対して、XML変換、PDF変換要求を投げる。
 * ID RCSfile="$RCSfile: ConvertGaiyoApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class ConvertGaiyoApplicationAction extends BaseAction {

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        //-----応募情報入力フォームの取得
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;

        // サーバサービスの呼び出し（ファイル変換
        RyoikiKeikakushoPk ryoikiKeikakushoPk = new RyoikiKeikakushoPk(
                ryoikiGaiyoForm.getRyoikikeikakushoInfo().getRyoikiSystemNo());

        try {
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .convertGaiyoApplication(container.getUserInfo(),
                            ryoikiKeikakushoPk);
        } catch (ValidationException e) {
            List errorList = e.getErrors();
            for (int i = 0; i < errorList.size(); i++) {
                ErrorInfo errInfo = (ErrorInfo) errorList.get(i);
                errors.add(errInfo.getProperty(), new ActionError(errInfo
                        .getErrorCode(), errInfo.getErrorArgs()));
            }
            // 検証エラー発生時はトークンを再セットし（入力）画面へ遷移させる
            if (!errors.isEmpty()) {
                //トークンをセッションに保存する。
                saveToken(request);
                saveErrors(request, errors);
                return forwardFailure(mapping);
            }
        } catch (ApplicationException e) {
            //エラー発生時はトークンを再セットし（入力）画面へ遷移させる
            //トークンをセッションに保存する。
            saveToken(request);
            saveErrors(request, errors);
            throw e;
        }

        //簡易申請フォーム生成
        RyoikiGaiyoForm gaiyoForm = new RyoikiGaiyoForm();
        gaiyoForm.setRyoikiSystemNo(ryoikiKeikakushoPk.getRyoikiSystemNo()); //システム受付番号セット
        gaiyoForm.setJigyoId(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getJigyoId());//事業ID
        gaiyoForm.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());//仮領域番号

        //リクエストに簡易申請フォームをセットする
        request.setAttribute("ryoikiGaiyoForm", gaiyoForm);
        
        //------フォーム情報の削除
        removeFormBean(mapping, request);

        return forwardSuccess(mapping);
    }
}